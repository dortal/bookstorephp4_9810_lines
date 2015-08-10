package com.accor.asa.commun.process.pool;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.services.mail.Body;
import com.accor.asa.commun.services.mail.Email;
import com.accor.asa.commun.services.mail.MailSenderFactory;
import com.accor.asa.commun.services.mail.MalformedInternetAddressException;
import com.accor.asa.commun.utiles.FilesPropertiesCache;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 5 oct. 2006
 * Time: 16:36:41
 */
public class GlobalPoolable {

    /**
     * La liste des pools
     */
    private static final Map<String, GlobalPool> _pools = new HashMap<String, GlobalPool>();
    /**
     * Un map pour stocker le time d'envoi de mail de supervision
     */
    public static final Map<String, Long> delaiRelanceMail = new HashMap<String, Long>();

    /**
     * see name
     */
    protected GlobalPoolable() {
        // rien
    }

    /**
     * Method initPool
     * see name
     */
    protected static synchronized void initPool(final String poolName) {
        try {
            GlobalPoolConfig  gpconfig = new GlobalPoolConfig(poolName);
            String strLog = new StringBuffer(">>> init pool: ").append(poolName).append(" avec les paramètres: ").append(gpconfig).toString();
            if (LogCommun.isTechniqueDebug()) LogCommun.debug("DonneeRefGeneriqueFacadePoolable","initPool",strLog);
            _pools.put(poolName, new GlobalPool(GlobalPoolUtil.getClassPooled(poolName),gpconfig));
        } catch (Exception e) {
            String msgerr = new StringBuffer("Pb while initializing the pool :").append(poolName).toString();
            LogCommun.major("COMMUN","GlobalPoolable", "initPool",msgerr, e);
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Envoie d'un mail en cas de bloccage suivi
     * d'une initialisation d'un pool
     * @param poolJdbcName
     * @throws Exception
     */
    private void envoyerMessage(String poolName, String typeMail)  throws Exception {
        long startsAgain = Long.parseLong(FilesPropertiesCache.getInstance().getValue(GlobalPoolUtil.CONF_NAME,"mail.startsAgain"));
        Long lastSendMail = (Long)delaiRelanceMail.get(poolName);
        if ( lastSendMail==null ||
                (System.currentTimeMillis()-lastSendMail.longValue()>startsAgain) ) {
            delaiRelanceMail.put(poolName, new Long(System.currentTimeMillis()));
            String mailAdress, mailSubject, mailBody;
            mailAdress = FilesPropertiesCache.getInstance().getValue(GlobalPoolUtil.CONF_NAME,"mail.adress");
            mailSubject= FilesPropertiesCache.getInstance().getValue(GlobalPoolUtil.CONF_NAME,"mail.subject");
            mailBody   = FilesPropertiesCache.getInstance().getValue(GlobalPoolUtil.CONF_NAME,"mail.body."+typeMail);
            StringBuffer corps = new StringBuffer(mailBody);
            corps.append("\nNom  = ").append(poolName);
            Email email = new Email(Integer.MAX_VALUE);
            try {
                email.setFrom(mailAdress, mailAdress);
                email.addTo(mailAdress, mailAdress);
            } catch (MalformedInternetAddressException e) {
                throw e;
            }
            email.setSubject(mailSubject);
            email.addBody("<pre>"+corps+"</pre>", Body.TEXT_HTML, Body.ISO_8859_1);
            MailSenderFactory.getInstance().send(email);
        }
    }
    /**
     * recuperation d'un ObjectPooled dans le pool
     *
     * @return
     * @throws com.accor.asa.commun.process.pool.GlobalPoolException
     */
    protected synchronized Object getObjectPooled(final String poolName) throws GlobalPoolException {
        try {
            GlobalPool pool = (GlobalPool)_pools.get(poolName);
            Object obj      = null;
            boolean isretryGetObjectPoolActive = "1".equals(FilesPropertiesCache.getInstance().getValue(GlobalPoolUtil.CONF_NAME,"retryGetObjectPool.active"));
            if (isretryGetObjectPoolActive) {
                int retryGetObjectPoolMax = FilesPropertiesCache.getInstance().getIntValue(GlobalPoolUtil.CONF_NAME,"retryGetObjectPool.max");
                int nbEssais        = 0;
                boolean isGetOk     = false;
                // Essaye un maximum de fois
                while (nbEssais<retryGetObjectPoolMax && !isGetOk) {
                    try {
                        obj = pool.borrowObject();
                        isGetOk = true;
                    } catch (NoSuchElementException  e) {
                        nbEssais++;
                        LogCommun.major("COMMUN","GlobalPoolable","getObjectPooled",">>> Try getObject="+nbEssais);
                        if (nbEssais == retryGetObjectPoolMax) {
                            // Toujours rien
                            boolean isInitObjectPoolActive = "1".equals(FilesPropertiesCache.getInstance().getValue(GlobalPoolUtil.CONF_NAME,"initObjectPool.active"));
                            if (isInitObjectPoolActive) {
                                try {
                                    // Initialisation du pool
                                    ((GlobalPool)_pools.get(poolName)).clear();
                                    // Retourne l'objet
                                    obj = pool.borrowObject();
                                    //Envoie de email
                                    envoyerMessage(poolName, "init");
                                } catch (Exception ex) {
                                    //Envoie de email
                                    envoyerMessage(poolName, "error");
                                    throw e;
                                }
                            } else {
                                throw e;
                            }
                        }
                    }
                }
            } else {
                obj = pool.borrowObject();
            }
            // Verifier si le pool est dans l'etat critique
            boolean alertGetObjectPoolActive = "1".equals(FilesPropertiesCache.getInstance().getValue(GlobalPoolUtil.CONF_NAME,"alertGetObjectPool.active"));
            if (alertGetObjectPoolActive) {
                int nbObjActive = getNumActiveInPool(poolName);
                int nbObjMax    = GlobalPoolUtil.getMaxActive(poolName);
                int nbObjDif    = FilesPropertiesCache.getInstance().getIntValue(GlobalPoolUtil.CONF_NAME,"alertGetObjectPool.min");
                if (nbObjMax - nbObjActive <= nbObjDif)
                    envoyerMessage(poolName, "alert");
            }
            return obj;
        } catch (NoSuchElementException e) {
            String msg = "plus d'objets dispos dans le pool";
            LogCommun.minor("COMMUN", "GlobalPoolConfig", "getObjectPooled", msg, e);
            throw new GlobalPoolException(e);
        } catch (Exception e) {
            String msg = "Pb while getting Object from Pool";
            LogCommun.minor("COMMUN", "GlobalPoolable", "getObjectPooled", msg, e);
            throw new GlobalPoolException(msg);
        }
    }

    /**
     * Method returnObjectToPool
     *
     * @param ptc see name
     */
    protected synchronized void returnObjectToPool(final String poolName, final Object obj) {
        try {
            if (obj != null)
                ((GlobalPool)_pools.get(poolName)).returnObject(obj);
        } catch (Exception e) {
            String msg = "Pb while returning object to Pool";
            LogCommun.minor("COMMUN", "GlobalPoolable", "returnObjectToPool", msg, e);
            throw new RuntimeException(msg);
        }
    }

    /**
     * Method getNumActiveInPool
     *
     * @return int see name
     */
    public static int getNumActiveInPool(final String poolName) {
        try {
            return ((GlobalPool)_pools.get(poolName)).getNumActive();
        } catch (NullPointerException e) {
            return 0;
        }
    }

    /**
     * Method getNumIdleInPool
     *
     * @return int see name
     */
    public static int getNumIdleInPool(final String poolName) {
        try {
            return ((GlobalPool)_pools.get(poolName)).getNumIdle();
        } catch (NullPointerException e) {
            return 0;
        }
    }

    /**
     * Clear le Pool
     */
    public static void clear(final String poolName) {
        try {
            ((GlobalPool)_pools.get(poolName)).clear();
        } catch (Exception e) {
            String msg = "Pb while clear the  Pool";
            LogCommun.minor("COMMUN", "GlobalPoolable", "clear", msg, e);
            throw new RuntimeException(msg);
        }
    }

    /**
     * Reinitialise le Pool
     */
    public static void reload(final String poolName) {
        initPool(poolName);
    }

}
