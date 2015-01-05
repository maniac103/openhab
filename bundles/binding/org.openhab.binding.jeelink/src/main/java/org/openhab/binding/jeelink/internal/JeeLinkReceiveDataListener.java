package org.openhab.binding.jeelink.internal;

import java.util.EventListener;


public interface JeeLinkReceiveDataListener extends EventListener {

	public void receiveSensorData(JeeLinkSensorData data);
	
}
