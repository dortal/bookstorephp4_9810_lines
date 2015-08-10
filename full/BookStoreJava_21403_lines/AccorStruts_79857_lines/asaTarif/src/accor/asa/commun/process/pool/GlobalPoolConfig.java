package com.accor.asa.commun.process.pool;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 3 nov. 2005
 * Time: 18:21:40
 * To change this template use File | Settings | File Templates.
 */
public class GlobalPoolConfig {
    /*** les atribus de GlobalPoolConfig */
    private String  name;
    private int     maxActive;
    private int     minIdle;
    private int     maxIdle;
    private byte    whenExhaustedAction;
    private long    timeMaxWait;
    private long    timeBetweenEvictionRuns;
    private long    timeMinEvictableIdle;

    /**
     * Constructeur
     */
    public GlobalPoolConfig(final String poolName) {
        this.name					    =poolName;
        this.maxActive					=GlobalPoolUtil.getMaxActive(poolName);
        this.minIdle					=GlobalPoolUtil.getMinIdle(poolName);
        this.maxIdle					=GlobalPoolUtil.getMaxIdle(poolName);
        this.whenExhaustedAction		=GlobalPoolUtil.getWhenExhaustedAction(poolName);
        this.timeMaxWait				=GlobalPoolUtil.getTimeMaxWait(poolName);
        this.timeBetweenEvictionRuns	=GlobalPoolUtil.getTimeBetweenEvictionRuns(poolName);
        this.timeMinEvictableIdle		=GlobalPoolUtil.getTimeMinEvictableIdle(poolName);
    }
    //================================== GETER ====================================

    public String getName() {
        return name;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public byte getWhenExhaustedAction() {
        return whenExhaustedAction;
    }

    public long getTimeMaxWait() {
        return timeMaxWait;
    }

    public long getTimeBetweenEvictionRuns() {
        return timeBetweenEvictionRuns;
    }

    public long getTimeMinEvictableIdle() {
        return timeMinEvictableIdle;
    }

    /**
     * To String
     * @return
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(" [name ").append(name);
        sb.append(" maxActive=").append(maxActive);
        sb.append(" minIdle=").append(minIdle);
        sb.append(" maxIdle=").append(maxIdle);
        sb.append(" whenExhaustedAction=").append(whenExhaustedAction);
        sb.append(" timeMaxWait=").append(timeMaxWait);
        sb.append(" timeBetweenEvictionRuns=").append(timeBetweenEvictionRuns);
        sb.append(" timeMinEvictableIdle=").append(timeMinEvictableIdle).append("]");
        return  sb.toString();
    }
}
