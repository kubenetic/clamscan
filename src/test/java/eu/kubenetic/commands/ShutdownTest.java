package eu.kubenetic.commands;

import org.junit.jupiter.api.Test;

class ShutdownTest {

    private final Shutdown command;

    ShutdownTest() {
        this.command = new Shutdown("localhost", 3310);
    }

    @Test
    void testShutdown() {
        System.out.println(command.execute());
    }

}