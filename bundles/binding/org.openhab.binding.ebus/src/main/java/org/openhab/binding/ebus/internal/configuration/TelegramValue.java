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

import javax.script.CompiledScript;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * eBus binding implementation.
 * 
 * @author Christian Sowada
 * @since 1.8.0
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class TelegramValue {

	private Integer bit;
	private CompiledScript csript;
	private String debug;
	private Float factor;
	private String label;
	private Map<String, String> mapping;
	private Float max;
	private Float min;
	private Integer pos;
	private Float replaceValue;
	private String script;
	private String step;
	private String type;
	private String typeHint;

	/**
	 * @return
	 */
	public Integer getBit() {
		return bit;
	}

	/**
	 * @return
	 */
	public CompiledScript getCsript() {
		return csript;
	}

	/**
	 * @return
	 */
	public String getDebug() {
		return debug;
	}

	/**
	 * @return
	 */
	public Float getFactor() {
		return factor;
	}

	/**
	 * @return
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return
	 */
	public Map<String, String> getMapping() {
		return mapping;
	}

	/**
	 * @return
	 */
	public Float getMax() {
		return max;
	}

	/**
	 * @return
	 */
	public Float getMin() {
		return min;
	}

	/**
	 * @return
	 */
	public Integer getPos() {
		return pos;
	}

	/**
	 * @return
	 */
	public Float getReplaceValue() {
		return replaceValue;
	}

	/**
	 * @return
	 */
	public String getScript() {
		return script;
	}

	/**
	 * @return
	 */
	public String getStep() {
		return step;
	}

	/**
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return
	 */
	public String getTypeHint() {
		return typeHint;
	}

	/**
	 * @param bit
	 */
	public void setBit(Integer bit) {
		this.bit = bit;
	}

	/**
	 * @param csript
	 */
	public void setCsript(CompiledScript csript) {
		this.csript = csript;
	}

	/**
	 * @param debug
	 */
	public void setDebug(String debug) {
		this.debug = debug;
	}

	/**
	 * @param factor
	 */
	public void setFactor(Float factor) {
		this.factor = factor;
	}

	/**
	 * @param label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @param mapping
	 */
	public void setMapping(Map<String, String> mapping) {
		this.mapping = mapping;
	}

	/**
	 * @param max
	 */
	public void setMax(Float max) {
		this.max = max;
	}

	/**
	 * @param min
	 */
	public void setMin(Float min) {
		this.min = min;
	}

	/**
	 * @param pos
	 */
	public void setPos(Integer pos) {
		this.pos = pos;
	}

	/**
	 * @param replaceValue
	 */
	public void setReplaceValue(Float replaceValue) {
		this.replaceValue = replaceValue;
	}

	/**
	 * @param script
	 */
	public void setScript(String script) {
		this.script = script;
	}

	/**
	 * @param step
	 */
	public void setStep(String step) {
		this.step = step;
	}

	/**
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @param typeHint
	 */
	@JsonProperty("type_hint")
	public void setTypeHint(String typeHint) {
		this.typeHint = typeHint;
	}
	
}
