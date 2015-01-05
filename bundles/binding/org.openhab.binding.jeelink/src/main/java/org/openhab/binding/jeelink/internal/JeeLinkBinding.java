/**
 * Copyright (c) 2010-2014, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.jeelink.internal;

import java.util.Dictionary;

import org.apache.commons.lang.StringUtils;
import org.openhab.binding.jeelink.JeeLinkBindingConfig;
import org.openhab.binding.jeelink.JeeLinkBindingProvider;
import org.openhab.core.binding.AbstractBinding;
import org.openhab.core.items.ItemRegistry;
import org.openhab.core.library.types.DecimalType;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.types.State;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class implements the communcation between openHAB and JeeLink devices.
 * Via RF received updates are received directly, there is no polling.
 * 
 * @author Till Klocke
 * @since 1.4.0
 */
public class JeeLinkBinding extends
AbstractBinding<JeeLinkBindingProvider> implements
ManagedService, JeeLinkReceiveDataListener {

	private static final Logger logger = LoggerFactory
			.getLogger(JeeLinkBinding.class);

	private final static String KEY_DEVICE_NAME = "device";
	@SuppressWarnings("unused")
	private ItemRegistry itemRegistry;

	public void setItemRegistry(ItemRegistry itemRegistry) {
		this.itemRegistry = itemRegistry;
	}

	public void unsetItemRegistry(ItemRegistry itemRegistry) {
		this.itemRegistry = null;
	}


	private JeeLinkConnector connector;

	/**
	 * the refresh interval which is used to poll values from the JeeLink server
	 * (optional, defaults to 60000ms)
	 */
	private long refreshInterval = 60000;

	public JeeLinkBinding() {
	}

	public void activate() {
		logger.debug("Activating JeeLink binding");
	}

	public void deactivate() {
		logger.debug("Deactivating JeeLink binding");
		if(connector != null) {
			connector.close();
		}
	}

	/**
	 * @{inheritDoc
	 */

	public void updated(Dictionary<String, ?> config)
			throws ConfigurationException {
		logger.debug("Received new config");
		if (config != null) {

			// to override the default refresh interval one has to add a
			// parameter to openhab.cfg like
			// <bindingName>:refresh=<intervalInMs>
			String refreshIntervalString = (String) config.get("refresh");
			if (StringUtils.isNotBlank(refreshIntervalString)) {
				refreshInterval = Long.parseLong(refreshIntervalString);
			}
			String deviceName = (String) config.get(KEY_DEVICE_NAME);
			if (StringUtils.isEmpty(deviceName)) {
				logger.error("No device name configured");
				throw new ConfigurationException(KEY_DEVICE_NAME,
						"The device name can't be empty");
			} else {
				if(connector != null) {
					connector.close();
					connector = null;
				}

				connector = new JeeLinkConnector();
				connector.addSensorDataEventListener(this);
				
				connector.open(deviceName);
			}

			// read further config parameters here ...

		}
	}

	public void receiveSensorData(JeeLinkSensorData data) {

		JeeLinkBindingConfig configHum = null;
		for (JeeLinkBindingProvider provider : providers) {
			configHum = provider.getConfigForAddress(data.getId() + ";H");
			if (configHum != null) {
				break;
			}
		}

		if (configHum != null && needsUpate(configHum) && configHum.getItem() != null) {
			State state = new DecimalType(data.getHumidity());
			eventPublisher.postUpdate(configHum.getItem().getName(), state);
			configHum.setTimestamp(System.currentTimeMillis());
		}

		JeeLinkBindingConfig configTemp = null;
		for (JeeLinkBindingProvider provider : providers) {
			configTemp = provider.getConfigForAddress(data.getId() + ";T");
			if (configTemp != null) {
				break;
			}
		}
		if (configTemp != null && needsUpate(configTemp) && configTemp.getItem() != null) {
			State state = new DecimalType(data.getTemperature());
			eventPublisher.postUpdate(configTemp.getItem().getName(), state);
			configTemp.setTimestamp(System.currentTimeMillis());
		}

		JeeLinkBindingConfig configBat = null;
		for (JeeLinkBindingProvider provider : providers) {
			configBat = provider.getConfigForAddress(data.getId() + ";B");
			if (configBat != null) {
				break;
			}
		}

		if (configBat != null && needsUpate(configBat) && configBat.getItem() != null) {
			State state = getOnOff(data.isLowBattery());
			eventPublisher.postUpdate(configBat.getItem().getName(), state);
			configBat.setTimestamp(System.currentTimeMillis());
		}

		JeeLinkBindingConfig configBatNew = null;
		for (JeeLinkBindingProvider provider : providers) {
			configBatNew = provider.getConfigForAddress(data.getId() + ";N");
			if (configBatNew != null) {
				break;
			}
		}

		if (configBatNew != null && needsUpate(configBatNew) && configBatNew.getItem() != null) {
			State state = getOnOff(data.isNewBattery());
			eventPublisher.postUpdate(configBatNew.getItem().getName(), state);
			configBatNew.setTimestamp(System.currentTimeMillis());
		}


		if (configTemp == null) {
			logger.info("Received message for unknown device " + data.getId());
		}
	}

	private boolean needsUpate(JeeLinkBindingConfig config)	{
		return System.currentTimeMillis() > config.getTimestamp() + refreshInterval;
	}

	/**
	 * Converts boolean to a valid OnOffType
	 * @param state
	 * @return
	 */
	private OnOffType getOnOff(boolean state) {
		return state ? OnOffType.ON : OnOffType.OFF;
	}
}
