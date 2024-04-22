package eu.kubenetic.commands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VersionTest {

    private static final String CURRENT_VERSION = "ClamAV 1.1.3/27252/Sun Apr 21 08:23:14 2024";
    private final Version command;

    VersionTest() {
        this.command = new Version("localhost", 3310);
    }

    @Test
    void testVersion() {
        String actual = command.execute();
        assertEquals(CURRENT_VERSION, actual);
    }
}