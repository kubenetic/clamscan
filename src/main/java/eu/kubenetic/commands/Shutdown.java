package eu.kubenetic.commands;

import eu.kubenetic.ClamDControl;
import eu.kubenetic.exceptions.MissingPropertyException;
import eu.kubenetic.model.ClamStats;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Shutdown extends Command {

    public Shutdown(String host, Integer port) throws MissingPropertyException {
        super(host, port);
    }

    public Shutdown(String host, Integer port, int connectionTimeout, int readTimeout, int bufferSize) throws MissingPropertyException {
        super(host, port, connectionTimeout, readTimeout, bufferSize);
    }

    public ClamStats execute() {
        // TODO: https://clamav-users.clamav.narkive.com/NquPSW8R/can-t-unlink-the-pid-file-var-run-clamd-pid
        try (Socket socket = new Socket()) {
            socket.connect(getInetSocketAddress(), connectionTimeout);
            socket.setSoTimeout(readTimeout);

            try (InputStream inputStream = socket.getInputStream();
                 OutputStream outputStream = socket.getOutputStream()) {
                outputStream.write(ClamDControl.SHUTDOWN.getCommandAsBytes());
                outputStream.flush();

                String response = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                return ClamStats.parse(response);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
