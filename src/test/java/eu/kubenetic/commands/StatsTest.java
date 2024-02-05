package eu.kubenetic.commands;

import org.junit.jupiter.api.Test;

class StatsTest {

    private final Stats command;

    StatsTest() {
        this.command = new Stats("localhost", 3310);
    }

    @Test
    public void testStats() {
        System.out.println(command.execute());
    }
}