/**
 * Copyright (c) 2010-2015, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.ebus.internal.configuration;

import java.util.Map;
import java.util.regex.Pattern;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * eBus binding implementation.
 * 
 * @author Christian Sowada
 * @since 1.8.0
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class TelegramConfiguration {

	private Pattern cfilter;
	private String clazz;
	private String command;
	private String comment;
	private Map<String, TelegramValue> computedValues;
	private String data;
	private Integer debug;
	private String device;
	private String dst;
	private String filter;

	private String id;
	private Map<String, TelegramValue> values;

	/**
	 * @return
	 */
	@JsonProperty("class")
	public String getClazz() {
		return clazz;
	}

	/**
	 * @return
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * @return
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @return
	 */
	public Map<String, TelegramValue> getComputedValues() {
		return computedValues;
	}

	/**
	 * @return
	 */
	public String getData() {
		return data;
	}

	/**
	 * @return
	 */
	public Integer getDebug() {
		return debug;
	}

	/**
	 * @return
	 */
	public String getDevice() {
		return device;
	}

	/**
	 * @return
	 */
	public String getDst() {
		return dst;
	}

	/**
	 * @return
	 */
	public String getFilter() {
		return filter;
	}

	/**
	 * @return
	 */
	public Pattern getFilterPattern() {
		return cfilter;
	}

	/**
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return
	 */
	public Map<String, TelegramValue> getValues() {
		return values;
	}

	/**
	 * @param clazz
	 */
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	/**
	 * @param command
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * @param comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @param computedValues
	 */
	@JsonProperty("computed_values")
	public void setComputedValues(Map<String, TelegramValue> computedValues) {
		this.computedValues = computedValues;
	}

	/**
	 * @param data
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * @param debug
	 */
	public void setDebug(Integer debug) {
		this.debug = debug;
	}

	/**
	 * @param device
	 */
	public void setDevice(String device) {
		this.device = device;
	}

	/**
	 * @param dst
	 */
	public void setDst(String dst) {
		this.dst = dst;
	}

	/**
	 * @param filter
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}

	/**
	 * @param cfilter
	 */
	public void setFilterPattern(Pattern cfilter) {
		this.cfilter = cfilter;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param values
	 */
	public void setValues(Map<String, TelegramValue> values) {
		this.values = values;
	}
}
