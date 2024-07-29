package eu.kubenetic.commands;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class IntegrationEnvironmentBase {

    protected final int clamavPort;

    public IntegrationEnvironmentBase() {
        this.clamavPort = clamavContainer.getFirstMappedPort();
    }

    static GenericContainer clamavContainer = new GenericContainer(
            DockerImageName.parse("docker.io/clamav/clamav:1.1.3"))
            .withExposedPorts(3310)
            .waitingFor(new LogMessageWaitStrategy()
                    .withRegEx(".*clamd started.*")
                    .withStartupTimeout(Duration.of(60, ChronoUnit.SECONDS)));

    @BeforeAll
    static void setUp() {
        clamavContainer.start();
    }

    @AfterAll
    static void tearDown() {
        clamavContainer.stop();
    }
}
