package eu.kubenetic.model;

import java.io.Serializable;

public class ThreadStats implements Serializable {
    private int live;
    private int idle;
    private int max;
    private int idleTimeout;

    public ThreadStats() {
    }

    public ThreadStats(int live, int idle, int max, int idleTimeout) {
        this.live = live;
        this.idle = idle;
        this.max = max;
        this.idleTimeout = idleTimeout;
    }

    public int getLive() {
        return live;
    }

    public void setLive(int live) {
        this.live = live;
    }

    public int getIdle() {
        return idle;
    }

    public void setIdle(int idle) {
        this.idle = idle;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getIdleTimeout() {
        return idleTimeout;
    }

    public void setIdleTimeout(int idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    @Override
    public String toString() {
        return "ThreadStats{" +
                "live=" + live +
                ", idle=" + idle +
                ", max=" + max +
                ", idleTimeout=" + idleTimeout +
                '}';
    }
}
