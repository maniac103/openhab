/**
 * Copyright (c) 2010-2015, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.lacrosse.connector;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.TooManyListenersException;

import org.openhab.binding.lacrosse.LaCrosseBinding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The connector to communicate with the JeeNode/Arduino+RFM12 device via
 * serial connection.
 * 
 * @author Christian Sowada
 * @since 1.7.0
 */
public class LaCrosseConnector {

	private static final Logger logger = LoggerFactory
			.getLogger(LaCrosseConnector.class);

	private BufferedReader input;

	private SerialPort serialPort;

	private String port;

	private Map<String, NumberAverage> avarage = new HashMap<String, NumberAverage>();

	private LaCrosseBinding binding;

	/**
	 * Constructor
	 * @param binding
	 */
	public LaCrosseConnector(LaCrosseBinding binding) {
		this.binding = binding;
	}

	/**
	 * Is serial connection open
	 * @return
	 */
	public boolean isOpen() {
		return (serialPort != null);
	}

	/**
	 * close serial connection
	 */
	public void close() {
		if(serialPort != null) {
			serialPort.close();
		}
	}

	/**
	 * Open and initialize a serial port.
	 * 
	 * @param portName e.g. /dev/ttyS0
	 * @param listener the listener which is informed after a
	 * successful response read
	 * @throws InitializationException
	 */
	public void open(String portName)  {

		logger.info("Open LaCrosse connection");

		port = portName;
		CommPortIdentifier portIdentifier;

		@SuppressWarnings("rawtypes")
		Enumeration portList0 = CommPortIdentifier.getPortIdentifiers();
		StringBuilder sb0 = new StringBuilder();
		while (portList0.hasMoreElements()) {
			CommPortIdentifier id = (CommPortIdentifier) portList0
					.nextElement();
			if (id.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				sb0.append(id.getName() + "\n");
			}
		}

		try {
			portIdentifier = CommPortIdentifier.getPortIdentifier(port);
			try {
				serialPort = (SerialPort) portIdentifier.open("openhab", 3000);
				serialPort.setSerialPortParams(57600, SerialPort.DATABITS_8,
						SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

				input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));

				serialPort.addEventListener(new SerialPortEventListener() {
					@Override
					public void serialEvent(SerialPortEvent event) {

						if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
							try {
								String inputLine=input.readLine();

								if(inputLine.startsWith("[") && inputLine.endsWith("]")) {
									logger.debug("Use HW Version: " + inputLine);
								}

								// ignore comment lines
								if(inputLine.startsWith("OK")) {
									logger.trace("Received data: {}", inputLine);

									String[] parts = inputLine.substring(5).split(" ");
									if(parts.length == 5) {
										int addr = Integer.parseInt(parts[0]);
										int c = Integer.parseInt(parts[1]);

										int battery_new = (c & 0x80) >> 7;
										// int type = (c & 0x70) >> 7;
										// int channel = c & 0x0F;

										float temperature = (float)(Integer.parseInt(parts[2]) * 256 + Integer.parseInt(parts[3]) - 1000) / 10;
										int humidity = Integer.parseInt(parts[4]) & 0x7f;
										int battery_low = (Integer.parseInt(parts[4]) & 0x80) >> 7;

										boolean batteryLow = battery_low == 1;
										boolean batteryNew = battery_new == 1;

										BigDecimal temp = getAverage(addr + ".temp", 15, 2).
												add(BigDecimal.valueOf(temperature));

										BigDecimal hum = getAverage(addr + ".hum", 15, 1).
												add(BigDecimal.valueOf(humidity));

										onDataReceived(addr, temp, hum, batteryNew, batteryLow);
									}
								}
							} catch (Exception e) {
								logger.error("LaCrosse error", e);
							}
						}
					}
				});

				serialPort.notifyOnDataAvailable(true);


			} catch (PortInUseException e) {
				logger.error("", e);
			} catch (UnsupportedCommOperationException e) {
				logger.error("", e);
			} catch (IOException e) {
				logger.error("", e);
			} catch (TooManyListenersException e) {
				logger.error("", e);
			}

		} catch (NoSuchPortException e) {
			StringBuilder sb = new StringBuilder();

			@SuppressWarnings("rawtypes")
			Enumeration portList = CommPortIdentifier.getPortIdentifiers();
			while (portList.hasMoreElements()) {
				CommPortIdentifier id = (CommPortIdentifier) portList
						.nextElement();
				if (id.getPortType() == CommPortIdentifier.PORT_SERIAL) {
					sb.append(id.getName() + "\n");
				}
			}

			logger.warn(("Serial port '" + port
					+ "' could not be found. Available ports are:\n"
					+ sb.toString()));

			throw new RuntimeException("Serial port '" + port
					+ "' could not be found. Available ports are:\n"
					+ sb.toString());
		}
	}

	/**
	 * @param key
	 * @param size
	 * @param scale
	 * @return
	 */
	private NumberAverage getAverage(String key, int size, int scale) {
		if(!avarage.containsKey(key)) {
			avarage.put(key, new NumberAverage(3, scale));
		}

		return avarage.get(key);
	}

	/**
	 * Called if a valid data received from a sensor.
	 * @param address The address of sensor
	 * @param temperature The temperature in celsius
	 * @param humidity The relative humidity
	 * @param batteryNew A flag if the sensor has a new battery
	 * @param batteryWeak A flag if the sensor has a weak battery
	 */
	public void onDataReceived(int address, BigDecimal temperature, BigDecimal humidity, boolean batteryNew, boolean batteryWeak) {
		binding.postSensorData(address, temperature, humidity, batteryNew, batteryWeak);
	}

}