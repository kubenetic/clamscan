package eu.kubenetic;

import eu.kubenetic.commands.*;
import eu.kubenetic.exceptions.MissingPropertyException;
import eu.kubenetic.model.VersionInfo;
import eu.kubenetic.model.ClamStats;
import eu.kubenetic.utils.InstreamParser;
import eu.kubenetic.utils.VersionParser;
import eu.kubenetic.utils.StatsParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Objects;
import java.util.Optional;

public class ClamDClient {

    private static final int DEFAULT_BUFFER_SIZE = 2048;
    private static final int DEFAULT_CONNECTION_TIMEOUT = 5000;


    private final String host;
    private final Integer port;
    private final int connectionTimeout;
    private final int readTimeout;
    private final int bufferSize;

    public ClamDClient(String host, Integer port) throws MissingPropertyException {
        this(host, port, 5000, 5000, DEFAULT_BUFFER_SIZE);
    }

    public ClamDClient(String host, Integer port, int connectionTimeout, int readTimeout, int bufferSize)
            throws MissingPropertyException {
        if (Objects.isNull(host) || host.isBlank()) {
            throw new MissingPropertyException("CalmD host wasn't provided");
        }
        this.host = host;

        if (Objects.isNull(port)) {
            throw new MissingPropertyException("ClamD port wasn't provided");
        }
        this.port = port;

        if (connectionTimeout < 0) {
            throw new IllegalArgumentException("Negative 'connectionTimeout' value does not make sense.");
        }
        this.connectionTimeout = connectionTimeout;

        if (readTimeout < 0) {
            throw new IllegalArgumentException("Negative 'readTimeout' value does not make sense.");
        }
        this.readTimeout = readTimeout;

        if (bufferSize < 0) {
            throw new IllegalArgumentException("Negative 'bufferSize' value does not make sense.");
        }
        this.bufferSize = bufferSize;
    }

    private InetSocketAddress getInetSocketAddress() {
        return new InetSocketAddress(host, port);
    }

    public Optional<String> cmdInStream(InputStream inputStream) throws IOException {
        String response = Instream.execute(getInetSocketAddress(), connectionTimeout, readTimeout, inputStream, bufferSize);
        return InstreamParser.parse(response);
    }

    public boolean cmdPing() {
        return Ping.execute(getInetSocketAddress(), connectionTimeout, readTimeout);
    }

    public boolean cmdReload() {
        return Reload.execute(getInetSocketAddress(), connectionTimeout, readTimeout);
    }

    // TODO: check if the connection closed properly and return false if not
    public void cmdShutdown() {
        Shutdown.execute(getInetSocketAddress(), connectionTimeout, readTimeout);
    }

    public ClamStats cmdStats() {
        String response = Stats.execute(getInetSocketAddress(), connectionTimeout, readTimeout);
        return StatsParser.parse(response);
    }

    public VersionInfo cmdVersion() {
        String response = Version.execute(getInetSocketAddress(), connectionTimeout, readTimeout);
        return VersionParser.parse(response);
    }


}
