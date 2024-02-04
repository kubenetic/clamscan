package eu.kubenetic.commands;

import org.junit.jupiter.api.Test;

class VersionTest {

    private final Version command;

    VersionTest() {
        this.command = new Version("localhost", 3310);
    }

    @Test
    public void testVersion() {
        System.out.println(command.execute());
    }
}