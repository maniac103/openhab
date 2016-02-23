/**
 * Copyright (c) 2010-2015, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.wmr.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Danny Baumann
 * @since 1.8.0
 */
public class TCPConnector extends HIDConnector {

    private static final Logger logger = LoggerFactory.getLogger(TCPConnector.class);

    private Socket socket;
    private InputStream in;
    private final String host;
    private final int port;

    /**
     * Constructor to init connector
     */
    public TCPConnector(String host, int port) {
        super();
        this.setDaemon(true);
        this.setName("WMR TCP Connection");
        this.host = host;
        this.port = port;
    }

    /**
     * Close the HID connection
     *
     * @throws IOException
     */
    @Override
    protected void close() throws IOException {
        logger.debug("Close WMR connection ...");

        if (in != null) {
            in.close();
            in = null;
        }
        if (socket != null) {
            socket.close();
            socket = null;
        }
    }

    /**
     * Open the HID connection
     *
     * @throws IOException
     */
    @Override
    protected void connect() throws IOException {
        socket = new Socket(host, port);
        in = socket.getInputStream();

        logger.info("Connected to WMR device (" + host + ":" + port + ")...");
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Thread#run()
     */
    @Override
    public void run() {
        byte[] responseBuffer = new byte[255];

        while (!isInterrupted()) {
            try {
                connect();

                int len;
                do {
                    len = in.read(responseBuffer, 1, responseBuffer.length - 1);
                    if (len > 0) {
                        responseBuffer[0] = (byte) len;
                        parseResponse(len, responseBuffer);
                    }
                } while (!isInterrupted() && len >= 0);
            } catch (InterruptedIOException e) {
                logger.error("Interrupted via InterruptedIOException");
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                logger.error("Reading from network failed", e);
            } finally {
                try {
                    close();
                } catch (IOException e) {
                    logger.error("Closing socket failed", e);
                }
            }
        }
    }
}
