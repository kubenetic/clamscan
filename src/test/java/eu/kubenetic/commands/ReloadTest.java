package eu.kubenetic.commands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ReloadTest extends IntegrationEnvironmentBase {

    private final Reload command;

    ReloadTest() {
        this.command = new Reload("localhost", super.clamavPort);
    }

    @Test
    void testReload() {
        assertTrue(command.execute());
    }
}