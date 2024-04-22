package eu.kubenetic.commands;

import eu.kubenetic.exceptions.MissingPropertyException;

import java.net.InetSocketAddress;
import java.util.Objects;

public abstract class Command {

    protected static final byte[] CLOSE_SEQUENCE = new byte[]{0, 0, 0, 0};
    protected static final int DEFAULT_BUFFER_SIZE = 2048;

    protected final String host;
    protected final Integer port;
    protected final int connectionTimeout;
    protected final int readTimeout;
    protected final int bufferSize;

    public Command(String host, Integer port) throws MissingPropertyException {
        this(host, port, 5000, 5000, DEFAULT_BUFFER_SIZE);
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

    protected InetSocketAddress getInetSocketAddress() {
        return new InetSocketAddress(host, port);
    }
}
