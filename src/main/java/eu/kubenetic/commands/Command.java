package eu.kubenetic.commands;

import eu.kubenetic.exceptions.MissingPropertyException;

import java.util.Objects;

public abstract class Command {

    protected final static byte[] CLOSE_SEQUENCE = new byte[]{0, 0, 0, 0};

    protected final String host;
    protected final Integer port;

    /**
     * Connection timeout in milliseconds
     */
    protected final int connectionTimeout;

    /**
     * Read timeout in milliseconds
     */
    protected final int readTimeout;
    protected final int bufferSize;

    public Command(String host, Integer port) throws MissingPropertyException {
        this(host, port, 5000, 5000, 2048);
    }

    public Command(String host, Integer port, int connectionTimeout, int readTimeout, int bufferSize)
            throws MissingPropertyException {
        if (Objects.isNull(host) || host.isBlank()) {
            throw new MissingPropertyException("CalmD host wasn't provided");
        }
        this.host = host;

        if (Objects.isNull(port)) {
            throw new MissingPropertyException("ClamD port wasn't provided");
        }
        this.port = port;

        this.connectionTimeout = connectionTimeout;
        this.readTimeout = readTimeout;
        this.bufferSize = bufferSize;
    }
}
