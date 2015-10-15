package org.openhab.binding.wmr.internal;

import java.util.Map;

/**
 * @author Christian Sowada
 * @since 1.8.0
 */
public interface IWmrListener {

	/**
	 * Called if a data received from weather station
	 * @param data The data as map
	 */
	public void dataReceived(Map<String, Object> data);
	
}
