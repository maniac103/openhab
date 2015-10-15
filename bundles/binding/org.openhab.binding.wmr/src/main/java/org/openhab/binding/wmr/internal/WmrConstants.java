/**
 * Copyright (c) 2010-2015, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.wmr.internal;

/**
* @author Christian Sowada
* @since 1.8.0
*/
public class WmrConstants {

	/** telegram type anemometer */
	public static final int TYPE_ANEMOMETER = (byte)0x48;
	
	/** telegram type barometer */
	public static final int TYPE_BAROMETER = (byte)0x46;

	/** telegram type rainfall */
	public static final int TYPE_RAINFALL = (byte)0x41;
	
	/** telegram type thermometer */
	public static final int TYPE_THERMOMETER = (byte)0x42;
	
	/** weather station hid vendor id */
	public static final int HID_VENDOR = 0x0FDE;
	
	/** weather station hid product id */
	public static final int HID_PRODUCT = 0xCA01;
	
	/** Weather station data request command */
	public static final byte[] HID_CMD_REQUEST = {
		(byte) 0x00, (byte) 0x01, (byte) 0xD0,
		(byte) 0x08, (byte) 0x01, (byte) 0x00,
		(byte) 0x00, (byte) 0x00, (byte) 0x00
	};
	
	/** Weather station initialisation command */
	public static final byte[] HID_CMD_INIT = {
		(byte) 0x00, (byte) 0x20, (byte) 0x00,
		(byte) 0x08, (byte) 0x01, (byte) 0x00,
		(byte) 0x00, (byte) 0x00, (byte) 0x00
	};
	
	/**
	 * Wind direction enum
	 */
	public static enum WIND_DIR {
		N, NNE, NE , ENE, E, ESE, SE, SSE, 
		S ,SSW, SW, WSW, W, WNW, NW, NNW
	}
	
}
