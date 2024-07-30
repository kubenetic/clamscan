package eu.kubenetic.commands;

import eu.kubenetic.ClamDClient;
import eu.kubenetic.model.VersionInfo;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class VersionTest extends IntegrationEnvironmentBase {

    private static final String CURRENT_VERSION = "ClamAV 1.1.3";

    private final ClamDClient client;

    public VersionTest() {
        super();
        client = new ClamDClient("localhost", super.clamavPort);
    }

    @Test
    void testVersion() {
        VersionInfo versionInfo = client.cmdVersion();
        assertThat(versionInfo.version(), equalTo(CURRENT_VERSION));
    }
}