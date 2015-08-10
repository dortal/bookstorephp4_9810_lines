package com.accor.asa.commun.process.pool;

import org.apache.commons.pool.impl.GenericObjectPool;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 14 juin 2005
 * Time: 16:46:56
 * To change this template use File | Settings | File Templates.
 */
public class GlobalPool  extends GenericObjectPool {

    /**
    * Definition du pool.
     * @param classe
     * @param gpconfig Config du pool
     */
    public GlobalPool(final Class<?> classe, final GlobalPoolConfig  gpconfig) {
        super(new GlobalPoolableObjectFactory(classe), gpconfig.getMaxActive());
        this.setMinIdle(gpconfig.getMinIdle());
        this.setMaxIdle(gpconfig.getMaxIdle());
        this.setWhenExhaustedAction(gpconfig.getWhenExhaustedAction());
        this.setMaxWait(gpconfig.getTimeMaxWait());
        this.setTimeBetweenEvictionRunsMillis(gpconfig.getTimeBetweenEvictionRuns());
        this.setMinEvictableIdleTimeMillis(gpconfig.getTimeMinEvictableIdle());
    }

	/**
	 * @see org.apache.commons.pool.ObjectPool#addObject()
	 */
	public void addObject() throws Exception {
		super.addObject();
	}
	/**
	 * @see org.apache.commons.pool.ObjectPool#borrowObject()
	 */
	public Object borrowObject() throws Exception {
		return super.borrowObject();
	}
	/**
	 * @see org.apache.commons.pool.ObjectPool#invalidateObject(java.lang.Object)
	 */
	public void invalidateObject(final Object obj) throws Exception {
		super.invalidateObject(obj);
	}
	/**
	 * @see org.apache.commons.pool.ObjectPool#returnObject(java.lang.Object)
	 */
	public void returnObject(final Object obj) throws Exception {
		super.returnObject(obj);
	}
}
