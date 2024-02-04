package eu.kubenetic.commands;

import eu.kubenetic.ClamDControl;
import eu.kubenetic.exceptions.MissingPropertyException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class Instream extends Command {

    public Instream(String host, Integer port) throws MissingPropertyException {
        super(host, port);
    }

    public Instream(String host, Integer port, int connectionTimeout, int readTimeout, int bufferSize) throws MissingPropertyException {
        super(host, port, connectionTimeout, readTimeout, bufferSize);
    }

    public String execute(InputStream dataStream) {
        try (Socket socket = new Socket()) {
            socket.connect(super.getInetSocketAddress(), connectionTimeout);
            socket.setSoTimeout(readTimeout);

            try (InputStream clamInStream = socket.getInputStream();
                 OutputStream clamOutStream = socket.getOutputStream()) {
                clamOutStream.write(ClamDControl.INSTREAM.getCommandAsBytes());
                clamOutStream.flush();

                byte[] buffer = new byte[super.bufferSize];
                int readBytes = dataStream.read(buffer);
                while (readBytes >= 0) {
                    byte[] payloadSize = ByteBuffer
                            .allocate(4)
                            .order(ByteOrder.BIG_ENDIAN)
                            .putInt(readBytes)
                            .array();
                    clamOutStream.write(payloadSize);
                    clamOutStream.write(buffer, 0, readBytes);

                    if (clamInStream.available() > 0) {
                        String reply = new String(clamInStream.readAllBytes(), StandardCharsets.UTF_8).trim();
                        throw new RuntimeException("Scan aborted. Reply from the server: " + reply);
                    }

                    readBytes = dataStream.read(buffer);
                }

                clamOutStream.write(CLOSE_SEQUENCE);
                clamOutStream.flush();

                return new String(clamInStream.readAllBytes(), StandardCharsets.UTF_8).trim();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
