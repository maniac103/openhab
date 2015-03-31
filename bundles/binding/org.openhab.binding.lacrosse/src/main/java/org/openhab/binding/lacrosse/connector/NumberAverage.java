/**
* Copyright (c) 2010-2015, openHAB.org and others.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*/
package org.openhab.binding.lacrosse.connector;

import java.math.BigDecimal;

/**
 * A class to compute average values over n values.
 * 
 * @author Christian Sowada
 * @since 1.7.0
 */
public class NumberAverage {

	private int size;
	private int pos;
	private BigDecimal[] values;
	private int scale;
	
	/**
	 * Constructor
	 * @param size The number of values to compute an average
	 * @param scale The BigInteger scale
	 */
	public NumberAverage(int size, int scale) {
		this.size = size;
		this.scale = scale;
		this.pos = 0;
		values = new BigDecimal[size];
	}
	
	/**
	 * Add a new value to the rolling average buffer.
	 * @param n The new value
	 * @return The current average value
	 */
	public BigDecimal add(BigDecimal n) {
		
		// rolling
		if(pos == this.size) {
			pos = 0;
		}

		values[pos++] = n;
		return get();
	}
	
	/**
	 * Returns the current average value
	 * @return The value
	 */
	public BigDecimal get() {
		BigDecimal b = new BigDecimal(0);
		int elems = 0;
		for (BigDecimal val : values) {
			if(val != null) {
				elems++;
				b = b.add(val);
			}
		}
		
		return b.divide(BigDecimal.valueOf(elems), scale, BigDecimal.ROUND_HALF_UP);
	}
	
}
