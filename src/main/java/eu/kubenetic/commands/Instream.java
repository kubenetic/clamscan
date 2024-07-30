package eu.kubenetic.commands;

import eu.kubenetic.ClamDControl;
import eu.kubenetic.exceptions.ClamDException;
import eu.kubenetic.exceptions.ScanException;
import eu.kubenetic.exceptions.SizeLimitExceededException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class Instream {

    private static final byte[] CLOSE_SEQUENCE = new byte[]{0, 0, 0, 0};

    public static String execute(InetSocketAddress addr, int connectionTimeout, int readTimeout, InputStream dataStream, int bufferSize) throws SizeLimitExceededException, ScanException, ClamDException {
        try (Socket socket = new Socket()) {
            socket.connect(addr, connectionTimeout);
            socket.setSoTimeout(readTimeout);

            try (InputStream clamInStream = socket.getInputStream();
                 OutputStream clamOutStream = socket.getOutputStream()) {
                clamOutStream.write(ClamDControl.INSTREAM.getCommandAsBytes());
                clamOutStream.flush();

                byte[] buffer = new byte[bufferSize];
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
                        if (reply.contains("INSTREAM size limit exceeded")) {
                            throw new SizeLimitExceededException();
                        }

                        throw new ScanException("Error occured during the scan process. " + reply);
                    }

                    readBytes = dataStream.read(buffer);
                }

                clamOutStream.write(CLOSE_SEQUENCE);
                clamOutStream.flush();

                return new String(clamInStream.readAllBytes(), StandardCharsets.UTF_8).trim();
            }
        } catch (IOException e) {
            throw new ClamDException("Communication error occurred during the scanning process", e);
        }
    }

}
