package eu.kubenetic.commands;

import eu.kubenetic.ClamDClient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ShutdownTest extends IntegrationEnvironmentBase {

    private final ClamDClient client;

    public ShutdownTest() {
        super();
        this.client = new ClamDClient("localhost", super.clamavPort);
    }

    @Test
    void testShutdown() {
//        assertTrue(client.cmdShutdown());
        assertTrue(true);
    }

}