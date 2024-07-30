package eu.kubenetic.commands;

import eu.kubenetic.ClamDClient;
import eu.kubenetic.model.ClamStats;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

class StatsTest extends IntegrationEnvironmentBase {

    private final ClamDClient client;

    public StatsTest() {
        super();
        this.client = new ClamDClient("localhost", super.clamavPort);
    }

    public void testStats() {
        ClamStats stats = client.cmdStats();
        assertThat(stats.pools(), equalTo(1));
        assertThat(stats.state(), equalTo("VALID PRIMARY"));
    }
}