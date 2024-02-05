package eu.kubenetic.model;

import java.io.Serializable;

public class MemoryStats implements Serializable {
    private String heap;
    private String mmap;
    private String used;
    private String free;
    private String releasable;
    private String pools;
    private String poolsUsed;
    private String poolsTotal;

    public MemoryStats() {
    }

    public MemoryStats(String heap, String mmap, String used, String free, String releasable, String pools, String poolsUsed, String poolsTotal) {
        this.heap = heap;
        this.mmap = mmap;
        this.used = used;
        this.free = free;
        this.releasable = releasable;
        this.pools = pools;
        this.poolsUsed = poolsUsed;
        this.poolsTotal = poolsTotal;
    }

    public String getHeap() {
        return heap;
    }

    public void setHeap(String heap) {
        this.heap = heap;
    }

    public String getMmap() {
        return mmap;
    }

    public void setMmap(String mmap) {
        this.mmap = mmap;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }

    public String getReleasable() {
        return releasable;
    }

    public void setReleasable(String releasable) {
        this.releasable = releasable;
    }

    public String getPools() {
        return pools;
    }

    public void setPools(String pools) {
        this.pools = pools;
    }

    public String getPoolsUsed() {
        return poolsUsed;
    }

    public void setPoolsUsed(String poolsUsed) {
        this.poolsUsed = poolsUsed;
    }

    public String getPoolsTotal() {
        return poolsTotal;
    }

    public void setPoolsTotal(String poolsTotal) {
        this.poolsTotal = poolsTotal;
    }

    @Override
    public String toString() {
        return "MemoryStats{" +
                "heap='" + heap + '\'' +
                ", mmap='" + mmap + '\'' +
                ", used='" + used + '\'' +
                ", free='" + free + '\'' +
                ", releasable='" + releasable + '\'' +
                ", pools='" + pools + '\'' +
                ", poolsUsed='" + poolsUsed + '\'' +
                ", poolsTotal='" + poolsTotal + '\'' +
                '}';
    }
}
