package eu.kubenetic.utils;

import eu.kubenetic.exceptions.ClamDException;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InstreamParser {

    public static Optional<String> parse(String response) throws ClamDException, IOException {
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
