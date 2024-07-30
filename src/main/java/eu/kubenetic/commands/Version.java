package eu.kubenetic.commands;

import eu.kubenetic.ClamDControl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Version {

    public static String execute(InetSocketAddress addr, int connectionTimeout, int readTimeout) {
        try (Socket socket = new Socket()) {
            socket.connect(addr, connectionTimeout);
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
