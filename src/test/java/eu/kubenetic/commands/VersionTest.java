package eu.kubenetic.commands;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;

public class VersionTest extends IntegrationEnvironmentBase {

    private static final String CURRENT_VERSION = "ClamAV 1.1.3";
    private final Version command;

    VersionTest() {
        this.command = new Version("localhost", super.clamavPort);
    }

    @Test
    void testVersion() {
        String actual = command.execute();
        assertThat(actual, startsWith(CURRENT_VERSION));
    }
}