package eu.kubenetic.commands;

import org.junit.jupiter.api.Test;

class ShutdownTest extends IntegrationEnvironmentBase {

    private final Shutdown command;

    ShutdownTest() {
        this.command = new Shutdown("localhost", super.clamavPort);
    }

    @Test
    void testShutdown() {
        System.out.println(command.execute());
    }

}