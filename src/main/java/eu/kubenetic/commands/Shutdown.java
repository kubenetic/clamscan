package eu.kubenetic.commands;

import eu.kubenetic.ClamDControl;
import eu.kubenetic.ClamDClient;
import eu.kubenetic.exceptions.MissingPropertyException;
import eu.kubenetic.model.ClamStats;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Shutdown {

    public static void execute(InetSocketAddress addr, int connectionTimeout, int readTimeout) {
        // TODO: https://clamav-users.clamav.narkive.com/NquPSW8R/can-t-unlink-the-pid-file-var-run-clamd-pid
        try (Socket socket = new Socket()) {
            socket.connect(addr, connectionTimeout);
            socket.setSoTimeout(readTimeout);

            try (OutputStream outputStream = socket.getOutputStream()) {
                outputStream.write(ClamDControl.SHUTDOWN.getCommandAsBytes());
                outputStream.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
