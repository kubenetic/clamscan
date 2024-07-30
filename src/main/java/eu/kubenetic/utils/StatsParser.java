package eu.kubenetic.utils;

import eu.kubenetic.model.ClamStats;
import eu.kubenetic.model.MemoryStats;
import eu.kubenetic.model.ThreadStats;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StatsParser {

    public static ClamStats parse(String inputText) {
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
        int pools = 0;
        if (poolsMatcher.find()) {
            pools = Integer.parseInt(poolsMatcher.group(1));
        }

        String state = "";
        if (stateMatcher.find()) {
            state = stateMatcher.group(1);
        }

        ThreadStats threadStats = null;
        if (threadsMatcher.find()) {
            int liveThreads = Integer.parseInt(threadsMatcher.group(1));
            int idleThreads = Integer.parseInt(threadsMatcher.group(2));
            int maxThreads = Integer.parseInt(threadsMatcher.group(3));
            int idleTimeout = Integer.parseInt(threadsMatcher.group(4));

            threadStats = new ThreadStats(liveThreads, idleThreads, maxThreads, idleTimeout);
        }

        int queueItems = 0;
        if (queueMatcher.find()) {
            queueItems = Integer.parseInt(queueMatcher.group(1));
        }

        MemoryStats memoryStats = null;
        if (memStatsMatcher.find()) {
            String heap = memStatsMatcher.group(1);
            String mmap = memStatsMatcher.group(2);
            String used = memStatsMatcher.group(3);
            String free = memStatsMatcher.group(4);
            String releasable = memStatsMatcher.group(5);
            String memPool = memStatsMatcher.group(6);
            String poolsUsedValue = memStatsMatcher.group(7);
            String poolsTotalValue = memStatsMatcher.group(8);

            memoryStats = new MemoryStats(heap, mmap, used, free, releasable, memPool, poolsUsedValue, poolsTotalValue);
        }

        return new ClamStats(pools, state, queueItems, threadStats, memoryStats);
    }
}
