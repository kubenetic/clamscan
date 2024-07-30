package eu.kubenetic.commands;

import eu.kubenetic.ClamDControl;
import eu.kubenetic.ClamDClient;
import eu.kubenetic.exceptions.MissingPropertyException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Reload the virus databases.
 */
public class Reload {

    public static boolean execute(InetSocketAddress addr, int connectionTimeout, int readTimeout) {
        try (Socket socket = new Socket()) {
            socket.connect(addr, connectionTimeout);
            socket.setSoTimeout(readTimeout);

            try (InputStream inputStream = socket.getInputStream();
                 OutputStream outputStream = socket.getOutputStream()) {
                outputStream.write(ClamDControl.RELOAD.getCommandAsBytes());
                outputStream.flush();

                return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8).trim().equals("RELOADING");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
