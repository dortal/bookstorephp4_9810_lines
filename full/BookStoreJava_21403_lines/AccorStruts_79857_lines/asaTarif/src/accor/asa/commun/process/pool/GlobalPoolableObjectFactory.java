package com.accor.asa.commun.process.pool;

import org.apache.commons.pool.BasePoolableObjectFactory;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 14 juin 2005
 * Time: 16:46:23
 * To change this template use File | Settings | File Templates.
 */
public class GlobalPoolableObjectFactory  extends BasePoolableObjectFactory {
    private Class<?> classe;
    public GlobalPoolableObjectFactory(Class<?> classe) {
        super();
        this.classe = classe;
    }
    /**
     * @see org.apache.commons.pool.PoolableObjectFactory#makeObject()
     */
    public Object makeObject() throws Exception {
        //return new GlobalPooled();
        return  classe.newInstance();
    }

	/**
	 * @see org.apache.commons.pool.PoolableObjectFactory#activateObject(java.lang.Object)
	 */
	public void activateObject(final Object obj) throws Exception {
		super.activateObject(obj);
	}
	/**
	 * @see org.apache.commons.pool.PoolableObjectFactory#destroyObject(java.lang.Object)
	 */
	public void destroyObject(final Object obj) throws Exception {
		super.destroyObject(obj);
	}
	/**
	 * @see org.apache.commons.pool.PoolableObjectFactory#passivateObject(java.lang.Object)
	 */
	public void passivateObject(final Object obj) throws Exception {
		super.passivateObject(obj);
	}
}
