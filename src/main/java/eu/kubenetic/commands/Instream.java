package eu.kubenetic.commands;

import eu.kubenetic.ClamDControl;
import eu.kubenetic.exceptions.ClamDException;
import eu.kubenetic.exceptions.MissingPropertyException;
import eu.kubenetic.exceptions.ScanException;
import eu.kubenetic.exceptions.SizeLimitExceededException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Instream extends Command {

    public Instream(String host, Integer port) throws MissingPropertyException {
        super(host, port);
    }

    public Instream(String host, Integer port, int connectionTimeout, int readTimeout, int bufferSize) throws MissingPropertyException {
        super(host, port, connectionTimeout, readTimeout, bufferSize);
    }

    public Optional<String> execute(InputStream dataStream) throws SizeLimitExceededException, ScanException, ClamDException {
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
                        if (reply.contains("INSTREAM size limit exceeded")) {
                            throw new SizeLimitExceededException();
                        }

                        throw new ScanException("Error occured during the scan process. " + reply);
                    }

                    readBytes = dataStream.read(buffer);
                }

                clamOutStream.write(CLOSE_SEQUENCE);
                clamOutStream.flush();

                return parseClamDResponse(clamInStream);
            }
        } catch (IOException e) {
            throw new ClamDException("Communication error occured during the scanning process", e);
        }
    }

    public Optional<String> parseClamDResponse(InputStream inputStream) throws ClamDException, IOException {
        String response = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8).trim();

        if (response.endsWith("OK")) {
            // State is OK. We're safe
            return Optional.empty();
        } else {
            // State is FOUND, there is some malware.
            Pattern pattern = Pattern.compile("stream:\\s([^\\s]+)?\\s?(OK|FOUND)", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(response);

            if (matcher.find()) {
                String malwareName = matcher.group(1);
                return Optional.of(malwareName);
            } else {
                throw new ClamDException("Malware name not found in the ClamD response");
            }
        }
    }
}
