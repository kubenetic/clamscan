package eu.kubenetic.commands;

import eu.kubenetic.ClamDControl;
import eu.kubenetic.exceptions.MissingPropertyException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Reload the virus databases.
 */
public class Reload extends Command {

    public Reload(String host, Integer port) throws MissingPropertyException {
        super(host, port);
    }

    public Reload(String host, Integer port, int connectionTimeout, int readTimeout, int bufferSize) throws MissingPropertyException {
        super(host, port, connectionTimeout, readTimeout, bufferSize);
    }

    public boolean execute() {
        try (Socket socket = new Socket()) {
            socket.connect(getInetSocketAddress(), connectionTimeout);
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
