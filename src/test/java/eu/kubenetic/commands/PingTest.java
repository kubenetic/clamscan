package eu.kubenetic.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PingTest extends IntegrationEnvironmentBase {

    private final Ping command;

    PingTest() {
        this.command = new Ping("localhost", super.clamavPort);
    }

    @Test
    public void testPing() {
        assertTrue(command.execute());
    }
}