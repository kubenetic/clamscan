package eu.kubenetic.model;

import java.time.LocalDateTime;

public record VersionInfo (
    String version,
    String databaseVersion,
    LocalDateTime timestamp
) {
}
