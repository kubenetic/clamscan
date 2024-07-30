package eu.kubenetic.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import eu.kubenetic.model.VersionInfo;

public class VersionParser {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("EEE MMM  dd HH:mm:ss yyyy");

    public static VersionInfo parse(String versionString) {
        String[] parts = versionString.split("/");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid version string format");
        }

        String version = parts[0];
        String databaseVersion = parts[1];
        LocalDateTime timestamp = LocalDateTime.parse(parts[2], DATE_TIME_FORMATTER);

        return new VersionInfo(version, databaseVersion, timestamp);
    }
}