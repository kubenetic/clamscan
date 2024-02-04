package eu.kubenetic.commands;

import eu.kubenetic.ClamDControl;
import eu.kubenetic.exceptions.MissingPropertyException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Version extends Command {
    public Version(String host, Integer port) throws MissingPropertyException {
        super(host, port);
    }

    public Version(String host, Integer port, int connectionTimeout, int readTimeout, int bufferSize)
            throws MissingPropertyException {
        super(host, port, connectionTimeout, readTimeout, bufferSize);
    }

    public String execute() {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), connectionTimeout);
            socket.setSoTimeout(readTimeout);

            try (OutputStream outputStream = socket.getOutputStream();
                 BufferedInputStream inputStream = new BufferedInputStream(socket.getInputStream())) {
                outputStream.write(ClamDControl.VERSION.getCommandAsBytes());
                outputStream.flush();

                return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8).trim();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
