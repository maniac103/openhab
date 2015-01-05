package org.openhab.binding.jeelink.internal;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.openhab.io.transport.cul.CULDeviceException;
import org.openhab.io.transport.cul.CULHandler;
import org.openhab.io.transport.cul.CULListener;
import org.openhab.io.transport.cul.CULManager;
import org.openhab.io.transport.cul.CULMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JeeLinkConnector implements CULListener {

	private static final Logger logger = LoggerFactory
			.getLogger(JeeLinkConnector.class);

	private CULHandler cul;

	private String deviceName;

	private Map<String, NumberAverage> avarage = new HashMap<String, NumberAverage>();

	private List<JeeLinkReceiveDataListener> eventListener = new LinkedList<JeeLinkReceiveDataListener>();
	
	private void getCULHandler() {
		try {
			logger.debug("Opening CUL device on " + deviceName);
			Map<String,Integer> options = new HashMap<String,Integer>();
			options.put("baudrate", 57600);
			options.put("parity", 0); //parity none
			cul = CULManager.getOpenCULHandler(deviceName, CULMode.SLOW_RF,options);
			cul.registerListener(this);
		} catch (CULDeviceException e) {
			logger.error("Can't open cul device", e);
			cul = null;
		}
	}

	public void open(String deviceName) {
		if (cul != null) {
			close();
		}
		this.deviceName = deviceName;
		getCULHandler();
	}

	public void close() {
		logger.debug("Deactivating JeeLink connector");
		cul.unregisterListener(this);
		CULManager.close(cul);
	}

	private void fireSensorDataEvent(JeeLinkSensorData data) {
		for (JeeLinkReceiveDataListener listener : eventListener) {
			listener.receiveSensorData(data);
		}
	}
	
	public void addSensorDataEventListener(JeeLinkReceiveDataListener listener) {
		eventListener.add(listener);
	}
	
	public void removeSensorDataEventListener(JeeLinkReceiveDataListener listener) {
		eventListener.remove(listener);
	}
	
	private NumberAverage getAverage(String key, int size, int scale) {
		if(!avarage.containsKey(key)) {
			avarage.put(key, new NumberAverage(3, scale));
		}
		
		return avarage.get(key);
	}
	
	private void handleReceivedMessage(String message) {
		String parts[] = message.split(" ");
		String id = parts[2];
		int a = new Integer(parts[4]);
		int b = new Integer(parts[5]);
		int c = new Integer(parts[3]);
		double temperature = (a * 256 + b - 1000) * 0.1;
		temperature = Math.round(10.0 * temperature) / 10.0;
		int humidity = Integer.parseInt(parts[6]) & 0x7f;
		int batteryLow = (Integer.parseInt(parts[6]) & 0x80) >> 7;
		int batteryNew = (c & 0x80) >> 7;
//		int type = (c & 0x70) >> 7;
//		int channel = c & 0x0F;
		
		// Average over last 15 values, two digits
		BigDecimal temp = getAverage(id + ".temp", 15, 2).
				add(BigDecimal.valueOf(temperature));
		
		// Average over last 15 values, one digit
		BigDecimal hum = getAverage(id + ".hum", 15, 1).
				add(BigDecimal.valueOf(humidity));
		
		JeeLinkSensorData data = new JeeLinkSensorData(id, temp, 
				hum, batteryLow == 1, batteryNew == 1);
		
		// No fire all registered event listeners
		fireSensorDataEvent(data);
	}

	public void dataReceived(String data) {
		// It is possible that we see here messages of other protocols
		if (data.startsWith("OK")) {
			logger.debug("Received JeeLink message: " + data);
			handleReceivedMessage(data);
		}
	}

	public void error(Exception e) {
		logger.error("Error while communicating with CUL", e);
	}

}
