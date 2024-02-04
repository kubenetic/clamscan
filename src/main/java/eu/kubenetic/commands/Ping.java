package eu.kubenetic.commands;

import eu.kubenetic.ClamDControl;
import eu.kubenetic.exceptions.MissingPropertyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

public class Ping extends Command {

    private final static Logger LOGGER = LoggerFactory.getLogger(Ping.class);
    private final static int RESPONSE_LENGHT = 4;

    public Ping(String host, Integer port) throws MissingPropertyException {
        super(host, port);
    }

    public Ping(String host, Integer port, int connectionTimeout, int readTimeout, int bufferSize)
            throws MissingPropertyException {
        super(host, port, connectionTimeout, readTimeout, bufferSize);
    }

    public boolean execute() {
        byte[] inputPayload = new byte[RESPONSE_LENGHT];

        try (Socket socket = new Socket()) {
            socket.connect(super.getInetSocketAddress(), connectionTimeout);
            socket.setSoTimeout(readTimeout);

            try (OutputStream outputStream = socket.getOutputStream();
                 InputStream inputStream = socket.getInputStream()) {
                outputStream.write(ClamDControl.PING.getCommandAsBytes());
                outputStream.flush();

                inputStream.read(inputPayload, 0, RESPONSE_LENGHT);
                return Arrays.equals(inputPayload, "PONG".getBytes());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
