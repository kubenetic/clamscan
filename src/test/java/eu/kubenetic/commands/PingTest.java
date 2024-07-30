package eu.kubenetic.commands;

import eu.kubenetic.ClamDClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PingTest extends IntegrationEnvironmentBase {

    private final ClamDClient client;

    PingTest() {
        super();
        client = new ClamDClient("localhost", super.clamavPort);
    }

    @Test
    public void testPing() {
        assertTrue(client.cmdPing());
    }
}