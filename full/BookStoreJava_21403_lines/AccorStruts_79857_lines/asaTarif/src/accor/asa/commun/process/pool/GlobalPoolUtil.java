package com.accor.asa.commun.process.pool;

import java.util.NoSuchElementException;

import org.apache.commons.pool.impl.GenericObjectPool;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.utiles.FilesPropertiesCache;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 5 oct. 2006
 * Time: 16:28:28
 * To change this template use File | Settings | File Templates.
 */
public class GlobalPoolUtil {
    /** nom de la conf pour accomons-prefs */
    public static final String CONF_NAME                = "dataPool";
    /*** val pour block: attend max wait avant de jeter */
    public static final String BLOCK_VALUE              = "BLOCK";
    /*** val pour fail: jeter directement */
    public static final String FAIL_VALUE               = "FAIL";
    /*** val pour grow: augmente la taille du pool */
    public static final String GROW_VALUE               = "GROW";

    /*** Cle utilisées dans  dataPool.properties */
    public static final String KEY_DEFAULT                      = "default";
    public static final String KEY_POOLABLE_NAME                = ".class.poolableName";
    public static final String KEY_POOLED_NAME                  = ".class.pooledName";
    public static final String KEY_MAX_ACTIVE                   = ".pool.maxActive";
    public static final String KEY_MIN_IDLE                     = ".pool.minIdle";
    public static final String KEY_MAX_IDLE                     = ".pool.maxIdle";
    public static final String KEY_WHEN_EXHAUSTED_ACTION        = ".pool.whenExhaustedAction";
    public static final String KEY_TIME_MAX_WAIT                = ".pool.timeMaxWait";
    public static final String KEY_TIME_BETWEEN_EVICTION_RUNS   = ".pool.timeBetweenEvictionRuns";
    public static final String KEY_TIME_MIN_EVICTABLE_IDLE      = ".pool.timeMinEvictableIdle";

    //================================== UTILS ====================================
    /**
     * Max active dans le pool
     * @param name
     * @return
     */
    public static int getMaxActive(final String name) {
        int value;
        String confKey = new StringBuffer(name).append(KEY_MAX_ACTIVE).toString();
        try {
            value = Integer.parseInt(FilesPropertiesCache.getInstance().getValue(CONF_NAME,confKey));
        } catch (NumberFormatException e) {
            LogCommun.major("COMMUN","GlobalPoolConfig","getMaxActive","format de " + confKey + " incorrecte", e);
            value = Integer.MIN_VALUE;
        } catch (Exception e) {
            LogCommun.major("COMMUN","GlobalPoolConfig","getMaxActive","erreur recup de " + confKey, e);
            value = Integer.MIN_VALUE;
        }
        if (value == Integer.MIN_VALUE) {
            String defaultConfKey = new StringBuffer(KEY_DEFAULT).append(KEY_MAX_ACTIVE).toString();
            LogCommun.minor("COMMUN","GlobalPoolConfig","getMaxActive","props " + confKey + " non trouve, utilisation default " + defaultConfKey);
            try {
                value = Integer.parseInt(FilesPropertiesCache.getInstance().getValue(CONF_NAME,defaultConfKey));
            } catch (NumberFormatException e) {
                LogCommun.major("COMMUN","GlobalPoolConfig","getMaxActive","format de " + defaultConfKey + " incorrecte", e);
                value = Integer.MIN_VALUE;
            } catch (Exception e) {
                LogCommun.major("COMMUN","GlobalPoolConfig","getMaxActive","erreur recup de " + defaultConfKey, e);
                value = Integer.MIN_VALUE;
            }
            if (value == Integer.MIN_VALUE) {
                LogCommun.minor("COMMUN","GlobalPoolConfig","getMaxActive","props " + defaultConfKey + " non trouve, utilisation default <<GenericObjectPool>>");
                value = GenericObjectPool.DEFAULT_MAX_ACTIVE;
            }
        }
        return value;
    }

    /**
     * Min idle dans le pool
     * @param name
     * @return
     */
    public static int getMinIdle(final String name) {
        int value;
        String confKey = new StringBuffer(name).append(KEY_MIN_IDLE).toString();
        try {
            value = Integer.parseInt(FilesPropertiesCache.getInstance().getValue(CONF_NAME,confKey));
        } catch (NumberFormatException e) {
            LogCommun.major("COMMUN","GlobalPoolConfig","getMinIdle","format de " + confKey + " incorrecte", e);
            value = Integer.MIN_VALUE;
        } catch (Exception e) {
            LogCommun.major("COMMUN","GlobalPoolConfig","getMinIdle","erreur recup de " + confKey, e);
            value = Integer.MIN_VALUE;
        }
        if (value == Integer.MIN_VALUE) {
            String defaultConfKey = new StringBuffer(KEY_DEFAULT).append(KEY_MIN_IDLE).toString();
            LogCommun.minor("COMMUN","GlobalPoolConfig","getMinIdle","props " + confKey + " non trouve, utilisation default " + defaultConfKey);
            try {
                value = Integer.parseInt(FilesPropertiesCache.getInstance().getValue(CONF_NAME,defaultConfKey));
            } catch (NumberFormatException e) {
                LogCommun.major("COMMUN","GlobalPoolConfig","getMinIdle","format de " + defaultConfKey + " incorrecte", e);
                value = Integer.MIN_VALUE;
            } catch (Exception e) {
                LogCommun.major("COMMUN","GlobalPoolConfig","getMinIdle","erreur recup de " + defaultConfKey, e);
                value = Integer.MIN_VALUE;
            }
            if (value == Integer.MIN_VALUE) {
                LogCommun.minor("COMMUN","GlobalPoolConfig","getMinIdle","props " + defaultConfKey + " non trouve, utilisation default <<GenericObjectPool>>");
                value = GenericObjectPool.DEFAULT_MIN_IDLE;
            }
        }
        return value;
    }

    /**
     * Max idle dans le pool
     * @param name
     * @return
     */
    public static int getMaxIdle(final String name) {
        int value;
        String confKey = new StringBuffer(name).append(KEY_MAX_IDLE).toString();
        try {
            value = Integer.parseInt(FilesPropertiesCache.getInstance().getValue(CONF_NAME,confKey));
        } catch (NumberFormatException e) {
            LogCommun.major("COMMUN","GlobalPoolConfig","getMaxIdle","format de " + confKey + " incorrecte", e);
            value = Integer.MIN_VALUE;
        } catch (Exception e) {
            LogCommun.major("COMMUN","GlobalPoolConfig","getMaxIdle","erreur recup de " + confKey, e);
            value = Integer.MIN_VALUE;
        }
        if (value == Integer.MIN_VALUE) {
            String defaultConfKey = new StringBuffer(KEY_DEFAULT).append(KEY_MAX_IDLE).toString();
            LogCommun.minor("COMMUN","GlobalPoolConfig","getMaxIdle","props " + confKey + " non trouve, utilisation default " + defaultConfKey);
            try {
                value = Integer.parseInt(FilesPropertiesCache.getInstance().getValue(CONF_NAME,defaultConfKey));
            } catch (NumberFormatException e) {
                LogCommun.major("COMMUN","GlobalPoolConfig","getMaxIdle","format de " + defaultConfKey + " incorrecte", e);
                value = Integer.MIN_VALUE;
            } catch (Exception e) {
                LogCommun.major("COMMUN","GlobalPoolConfig","getMaxIdle","erreur recup de " + defaultConfKey, e);
                value = Integer.MIN_VALUE;
            }
            if (value == Integer.MIN_VALUE) {
                LogCommun.minor("COMMUN","GlobalPoolConfig","getMaxIdle","props " + defaultConfKey + " non trouve, utilisation default <<GenericObjectPool>>");
                value = GenericObjectPool.DEFAULT_MAX_IDLE;
            }
        }
        return value;
    }
    /**
     * Action quand pool plein
     * BLOCK : attend max wait avant de jeter
     * FAIL : jete directement
     * GROW : augemente
     * @param name
     * @return
     */
    public static byte getWhenExhaustedAction(final String name) {
        String value;
        String confKey = new StringBuffer(name).append(KEY_WHEN_EXHAUSTED_ACTION).toString();
        try {
            value = FilesPropertiesCache.getInstance().getValue(CONF_NAME,confKey);
        } catch (Exception e) {
            LogCommun.major("COMMUN","GlobalPoolConfig","getWhenExhaustedAction","erreur recup de " + confKey, e);
            value = "";
        }
        if (!BLOCK_VALUE.equalsIgnoreCase(value) && !FAIL_VALUE.equalsIgnoreCase(value) && !GROW_VALUE.equalsIgnoreCase(value)) {
            String defaultConfKey = new StringBuffer(KEY_DEFAULT).append(KEY_WHEN_EXHAUSTED_ACTION).toString();
            LogCommun.minor("COMMUN","GlobalPoolConfig","getWhenExhaustedAction","props " + confKey + " non trouve, utilisation default " + defaultConfKey);
            try {
                value = FilesPropertiesCache.getInstance().getValue(CONF_NAME,defaultConfKey);
            } catch (Exception e) {
                LogCommun.major("COMMUN","GlobalPoolConfig","getWhenExhaustedAction","erreur recup de " + defaultConfKey, e);
                value = "";
            }
        }
        if (value.equalsIgnoreCase(BLOCK_VALUE)) {
            if (LogCommun.isTechniqueDebug()) LogCommun.debug("GlobalPoolConfig","getWhenExhaustedAction","whenExhaustedAction init avec BLOCK");
            return GenericObjectPool.WHEN_EXHAUSTED_BLOCK;
        } else if (value.equalsIgnoreCase(FAIL_VALUE)) {
            if (LogCommun.isTechniqueDebug()) LogCommun.debug("GlobalPoolConfig","getWhenExhaustedAction","whenExhaustedAction init avec FAIL");
            return GenericObjectPool.WHEN_EXHAUSTED_FAIL;
        } else  if (value.equalsIgnoreCase(GROW_VALUE)) {
            if (LogCommun.isTechniqueDebug()) LogCommun.debug("GlobalPoolConfig","getWhenExhaustedAction","whenExhaustedAction init avec GROW");
            return GenericObjectPool.WHEN_EXHAUSTED_GROW;
        } else  {
            LogCommun.minor("COMMUN","GlobalPoolConfig","getWhenExhaustedAction","props defaultConfKey non trouve, utilisation default <<GenericObjectPool>>");
            return GenericObjectPool.DEFAULT_WHEN_EXHAUSTED_ACTION;
        }
    }

    /**
     * Le temps d'attente quand le pool est plein
     * @param name
     * @return
     */
    public static long getTimeMaxWait(final String name) {
        long value;
        String confKey = new StringBuffer(name).append(KEY_TIME_MAX_WAIT).toString();
        try {
            value = Long.parseLong(FilesPropertiesCache.getInstance().getValue(CONF_NAME,confKey));
        } catch (NumberFormatException e) {
            LogCommun.major("COMMUN","GlobalPoolConfig","getTimeMaxWait","format de " + confKey + " incorrecte", e);
            value = Long.MIN_VALUE;
        } catch (Exception e) {
            LogCommun.major("COMMUN","GlobalPoolConfig","getTimeMaxWait","erreur recup de " + confKey, e);
            value = Long.MIN_VALUE;
        }
        if (value == Long.MIN_VALUE) {
            String defaultConfKey = new StringBuffer(KEY_DEFAULT).append(KEY_TIME_MAX_WAIT).toString();
            LogCommun.minor("COMMUN","GlobalPoolConfig","getTimeMaxWait","props " + confKey + " non trouve, utilisation default " + defaultConfKey);
            try {
                value = Long.parseLong(FilesPropertiesCache.getInstance().getValue(CONF_NAME,defaultConfKey));
            } catch (NumberFormatException e) {
                LogCommun.major("COMMUN","GlobalPoolConfig","getTimeMaxWait","format de " + defaultConfKey + " incorrecte", e);
                value = Long.MIN_VALUE;
            } catch (Exception e) {
                LogCommun.major("COMMUN","GlobalPoolConfig","getTimeMaxWait","erreur recup de " + defaultConfKey, e);
                value = Long.MIN_VALUE;
            }
            if (value == Long.MIN_VALUE) {
                LogCommun.minor("COMMUN","GlobalPoolConfig","getTimeMaxWait","props " + defaultConfKey + " non trouve, utilisation default <<GenericObjectPool>>");
                value = GenericObjectPool.DEFAULT_MAX_WAIT;
            }
        }
        return value;
    }

    /**
     * Le temps entre le idleObject et runObject
     * @param name
     * @return
     */
    public static long getTimeBetweenEvictionRuns(final String name) {
        long value;
        String confKey = new StringBuffer(name).append(KEY_TIME_BETWEEN_EVICTION_RUNS).toString();
        try {
            value = Long.parseLong(FilesPropertiesCache.getInstance().getValue(CONF_NAME,confKey));
        } catch (NumberFormatException e) {
            LogCommun.major("COMMUN","GlobalPoolConfig","getTimeBetweenEvictionRuns","format de " + confKey + " incorrecte", e);
            value = Long.MIN_VALUE;
        } catch (Exception e) {
            LogCommun.major("COMMUN","GlobalPoolConfig","getTimeBetweenEvictionRuns","erreur recup de " + confKey, e);
            value = Long.MIN_VALUE;
        }
        if (value == Long.MIN_VALUE) {
            String defaultConfKey = new StringBuffer(KEY_DEFAULT).append(KEY_TIME_BETWEEN_EVICTION_RUNS).toString();
            LogCommun.minor("COMMUN","GlobalPoolConfig","getTimeBetweenEvictionRuns","props " + confKey + " non trouve, utilisation default " + defaultConfKey);
            try {
                value = Long.parseLong(FilesPropertiesCache.getInstance().getValue(CONF_NAME,defaultConfKey));
            } catch (NumberFormatException e) {
                LogCommun.major("COMMUN","GlobalPoolConfig","getTimeBetweenEvictionRuns","format de " + defaultConfKey + " incorrecte", e);
                value = Long.MIN_VALUE;
            } catch (Exception e) {
                LogCommun.major("COMMUN","GlobalPoolConfig","getTimeBetweenEvictionRuns","erreur recup de " + defaultConfKey, e);
                value = Long.MIN_VALUE;
            }
            if (value == Long.MIN_VALUE) {
                LogCommun.minor("COMMUN","GlobalPoolConfig","getTimeBetweenEvictionRuns","props " + defaultConfKey + " non trouve, utilisation default <<GenericObjectPool>>");
                value = GenericObjectPool.DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS;
            }
        }
        return value;
    }
    /**
     * Le temps d'attente d'un objet en Idle
     * @param name
     * @return
     */
    public static long getTimeMinEvictableIdle(final String name) {
        long value;
        String confKey = new StringBuffer(name).append(KEY_TIME_MIN_EVICTABLE_IDLE).toString();
        try {
            value = Long.parseLong(FilesPropertiesCache.getInstance().getValue(CONF_NAME,confKey));
        } catch (NumberFormatException e) {
            LogCommun.major("COMMUN","GlobalPoolConfig","getTimeMinEvictableIdle","format de " + confKey + " incorrecte", e);
            value = Long.MIN_VALUE;
        } catch (Exception e) {
            LogCommun.major("COMMUN","GlobalPoolConfig","getTimeMinEvictableIdle","erreur recup de " + confKey, e);
            value = Long.MIN_VALUE;
        }
        if (value == Long.MIN_VALUE) {
            String defaultConfKey = new StringBuffer(KEY_DEFAULT).append(KEY_TIME_MIN_EVICTABLE_IDLE).toString();
            LogCommun.minor("COMMUN","GlobalPoolConfig","getTimeMinEvictableIdle","props " + confKey + " non trouve, utilisation default " + defaultConfKey);
            try {
                value = Long.parseLong(FilesPropertiesCache.getInstance().getValue(CONF_NAME,defaultConfKey));
            } catch (NumberFormatException e) {
                LogCommun.major("COMMUN","GlobalPoolConfig","getTimeMinEvictableIdle","format de " + defaultConfKey + " incorrecte", e);
                value = Long.MIN_VALUE;
            } catch (Exception e) {
                LogCommun.major("COMMUN","GlobalPoolConfig","getTimeMinEvictableIdle","erreur recup de " + defaultConfKey, e);
                value = Long.MIN_VALUE;
            }
            if (value == Long.MIN_VALUE) {
                LogCommun.minor("COMMUN","GlobalPoolConfig","getTimeMinEvictableIdle","props " + defaultConfKey + " non trouve, utilisation default <<GenericObjectPool>>");
                value = GenericObjectPool.DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS;
            }
        }
        return value;
    }
    //================================== STATIC  ====================================
    /**
     * Retourne la classe Poolable
     * @param name
     * @return
     */
    public static Class<?> getClassPoolable(final String name) throws TechnicalException {
        try {
            String keyClass     = new StringBuffer(name).append(KEY_POOLABLE_NAME).toString();
            String nameClass    = FilesPropertiesCache.getInstance().getValue(CONF_NAME,keyClass);
            return Class.forName(nameClass);
        } catch (ClassNotFoundException e) {
            String msgerr = new StringBuffer("Pb while getting the poolable class :").append(name).toString();
            LogCommun.major("COMMUN","GlobalPoolConfig", "getClassPoolable",msgerr, e);
            throw new TechnicalException(e);
        }
    }
    /**
     * Retourne la classe Poolable
     * @param name
     * @return
     */
    public static Class<?> getClassPooled(final String name)  throws TechnicalException {
        try {
            String keyClass     = new StringBuffer(name).append(KEY_POOLED_NAME).toString();
            String nameClass    =FilesPropertiesCache.getInstance().getValue(CONF_NAME,keyClass);
            return Class.forName(nameClass);
        } catch (ClassNotFoundException e) {
            String msgerr = new StringBuffer("Pb while getting the pooled class :").append(name).toString();
            LogCommun.major("COMMUN","GlobalPoolConfig", "getClassPooled",msgerr, e);
            throw new TechnicalException(e);
        }
    }

    /**
     * Method initPool
     * see name
     */
    public static GlobalPool initPool(String poolName)  {
        try {
            GlobalPoolConfig  gpconfig = new GlobalPoolConfig(poolName);
            String strLog = new StringBuffer(">>> init pool: ").append(poolName).append(" avec les paramètres: ").append(gpconfig).toString();
            if (LogCommun.isTechniqueDebug()) LogCommun.debug("DonneeRefGeneriqueFacadePoolable","initPool",strLog);
            return  new GlobalPool(getClassPooled(poolName),gpconfig);
        } catch (TechnicalException e) {
            String msgerr = new StringBuffer("Pb while initializing the pool :").append(poolName).toString();
            LogCommun.major("COMMUN","GlobalPoolConfig", "initPool",msgerr, e);
            return null;
        }
    }
    /**
     * Recuperation d'un ObjectPooled dans le pool
     * @return
     * @throws GlobalPoolException
     */
    public static Object getObjectPooled(GlobalPool pool) throws GlobalPoolException {
        try {
            return pool.borrowObject();
        } catch (NoSuchElementException e) {
            String msg = "plus d'objets dispos dans le pool";
            LogCommun.minor("COMMUN", "GlobalPoolConfig", "getObjectPooled", msg, e);
            throw new GlobalPoolException(e);
        } catch (Exception e) {
            String msg = "Pb while getting Object from Pool";
            LogCommun.minor("COMMUN", "GlobalPoolConfig", "getObjectPooled", msg, e);
            throw new GlobalPoolException(msg);
        }
    }
    /**
     * Method returnObjectToPool
     * @param ptc see name
     */
    public static void returnObjectToPool(GlobalPool pool, Object ptc) {
        try {
            pool.returnObject(ptc);
        } catch (Exception e) {
            String msg = "Pb while returning object to Pool";
            LogCommun.minor("COMMUN", "GlobalPoolConfig", "returnObjectToPool", msg, e);
            throw new RuntimeException(msg);
        }
    }

}
