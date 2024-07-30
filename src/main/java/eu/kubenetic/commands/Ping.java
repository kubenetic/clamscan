package eu.kubenetic.commands;

import eu.kubenetic.ClamDControl;
import eu.kubenetic.ClamDClient;
import eu.kubenetic.exceptions.MissingPropertyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

public class Ping {

    private final static Logger LOGGER = LoggerFactory.getLogger(Ping.class);
    private final static int RESPONSE_LENGHT = 4;

    public static boolean execute(InetSocketAddress addr, int connectionTimeout, int readTimeout) {
        byte[] inputPayload = new byte[RESPONSE_LENGHT];

        try (Socket socket = new Socket()) {
            socket.connect(addr, connectionTimeout);
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
