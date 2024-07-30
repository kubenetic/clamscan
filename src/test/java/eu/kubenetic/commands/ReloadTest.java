package eu.kubenetic.commands;

import eu.kubenetic.ClamDClient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ReloadTest extends IntegrationEnvironmentBase {

    private final ClamDClient client;

    public ReloadTest() {
        super();
        this.client = new ClamDClient("localhost", super.clamavPort);
    }

    @Test
    void testReload() {
        assertTrue(client.cmdReload());
    }
}