package eu.kubenetic.commands;

import org.junit.jupiter.api.Test;

class StatsTest extends IntegrationEnvironmentBase {

    private final Stats command;

    StatsTest() {
        this.command = new Stats("localhost", super.clamavPort);
    }

    @Test
    public void testStats() {
        System.out.println(command.execute());
    }
}