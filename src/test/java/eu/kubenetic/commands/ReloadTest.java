package eu.kubenetic.commands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ReloadTest {

    private final Reload command;

    ReloadTest() {
        this.command = new Reload("localhost", 3310);
    }

    @Test
    void testReload() {
        assertTrue(command.execute());
    }
}