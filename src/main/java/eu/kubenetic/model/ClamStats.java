package eu.kubenetic.model;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClamStats implements Serializable{
    private int pools;
    private String state;
    private int queueLength;
    private ThreadStats threadStats;
    private MemoryStats memoryStats;

    private ClamStats() {

    }

    public int getPools() {
        return pools;
    }

    public void setPools(int pools) {
        this.pools = pools;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getQueueLength() {
        return queueLength;
    }

    public void setQueueLength(int queueLength) {
        this.queueLength = queueLength;
    }

    public ThreadStats getThreadStats() {
        return threadStats;
    }

    public void setThreadStats(ThreadStats threadStats) {
        this.threadStats = threadStats;
    }

    public MemoryStats getMemoryStats() {
        return memoryStats;
    }

    public void setMemoryStats(MemoryStats memoryStats) {
        this.memoryStats = memoryStats;
    }

    public static ClamStats parse(String inputText) {
        ClamStats clamStats = new ClamStats();

        // Define patterns for different sections
        Pattern poolsPattern = Pattern.compile("POOLS: (\\d+)");
        Pattern statePattern = Pattern.compile("STATE: (.+)");
        Pattern threadsPattern = Pattern.compile("THREADS: live (\\d+)  idle (\\d+) max (\\d+) idle-timeout (\\d+)");
        Pattern queuePattern = Pattern.compile("QUEUE: (\\d+) items[\\s\\S]*STATS (\\d+\\.\\d+)");
        Pattern memStatsPattern = Pattern.compile("MEMSTATS: heap (.+) mmap (.+) used (.+) free (.+) releasable (.+) pools (\\d+) pools_used (.+) pools_total (.+)");

        // Match patterns against the input text
        Matcher poolsMatcher = poolsPattern.matcher(inputText);
        Matcher stateMatcher = statePattern.matcher(inputText);
        Matcher threadsMatcher = threadsPattern.matcher(inputText);
        Matcher queueMatcher = queuePattern.matcher(inputText);
        Matcher memStatsMatcher = memStatsPattern.matcher(inputText);

        // Extract information
        if (poolsMatcher.find()) {
            int pools = Integer.parseInt(poolsMatcher.group(1));
            clamStats.setPools(pools);
        }

        if (stateMatcher.find()) {
            String state = stateMatcher.group(1);
            clamStats.setState(state);
        }

        if (threadsMatcher.find()) {
            int liveThreads = Integer.parseInt(threadsMatcher.group(1));
            int idleThreads = Integer.parseInt(threadsMatcher.group(2));
            int maxThreads = Integer.parseInt(threadsMatcher.group(3));
            int idleTimeout = Integer.parseInt(threadsMatcher.group(4));

            ThreadStats stats = new ThreadStats(liveThreads, idleThreads, maxThreads, idleTimeout);
            clamStats.setThreadStats(stats);
        }

        if (queueMatcher.find()) {
            int queueItems = Integer.parseInt(queueMatcher.group(1));
            //double stats = Double.parseDouble(queueMatcher.group(2));
            clamStats.setQueueLength(queueItems);
        }

        if (memStatsMatcher.find()) {
            String heap = memStatsMatcher.group(1);
            String mmap = memStatsMatcher.group(2);
            String used = memStatsMatcher.group(3);
            String free = memStatsMatcher.group(4);
            String releasable = memStatsMatcher.group(5);
            String pools = memStatsMatcher.group(6);
            String poolsUsedValue = memStatsMatcher.group(7);
            String poolsTotalValue = memStatsMatcher.group(8);

            MemoryStats stats = new MemoryStats(heap, mmap, used, free, releasable, pools, poolsUsedValue, poolsTotalValue);
            clamStats.setMemoryStats(stats);
        }

        return clamStats;
    }

    @Override
    public String toString() {
        return "ClamStats{" +
                "pools=" + pools +
                ", state='" + state + '\'' +
                ", queueLength=" + queueLength +
                ", threadStats=" + threadStats +
                ", memoryStats=" + memoryStats +
                '}';
    }
}
