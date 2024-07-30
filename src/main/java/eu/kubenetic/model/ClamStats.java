package eu.kubenetic.model;

public record ClamStats(
        int pools,
        String state,
        int queueLength,
        ThreadStats threadStats,
        MemoryStats memoryStats) {
}
