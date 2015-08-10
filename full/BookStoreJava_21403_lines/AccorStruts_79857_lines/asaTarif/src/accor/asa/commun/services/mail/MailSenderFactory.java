package com.accor.asa.commun.services.mail;

import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.services.mail.poolthread.ThreadPoolMailSender;
import com.accor.asa.commun.utiles.FilesPropertiesCache;

/**
 * Factory utilisée pour recuper un MailSender.
 * L'implementation par defaut est définie dans un fichier de propriété.
 * <p>
 * Utilisation directe du MailSender :
 * <pre>
 *      Email email = new Email();
 *      email.setFrom("toto@toto.com");
 *      email.addTo("titi@titi.com");
 *      ...
 *      MailSender ms;
 *      try {
 *          ms = MailSenderFactory.getInstance();
 *          ms.send(e);
 *      } catch (MailException me){
 *          ...
 *      }
 *  </pre>
 * <p>
 * Utilisation avec le Proxy :
 * <pre>
 *      MailSender ms;
 *      try {
 *          ms = MailSenderFactory.getInstance();
 *          new MailSenderProxy(ms).send(new EmailAddress("toto@toto.com"),
 *                                       new EmailAddress("titi@titi.com"),
 *                                       "Hello World",
 *                                       "What a wonderful life"
 *                                       );
 *      } catch (MailException me){
 *          ...
 *      }
 *  </pre>
 *
 * @author <a href="mailto:LEHYARIC_Gildas@accor-hotels.com">gle</a>
 * @version $Id: MailSenderFactory.java,v 1.4 2006/02/07 17:39:00 dgo Exp $
 */

public class MailSenderFactory {

    public final static int SIMPLE      = 1;
    public final static int THREADPOOL  = 2;

    private static MailSender instance;

    private static synchronized MailSender initDefaultInstance() throws MailException {
        if(LogCommun.isTechniqueDebug()) LogCommun.debug("MailSenderFactory","MailSender","M_initDefaultInstance");
        FilesPropertiesCache.getInstance().reloadProperties("mail");
        Class<?> mailSender;
        try {
            mailSender = Class.forName(FilesPropertiesCache.getInstance().getValue("mail", "mail.factory.mailsender.fqcn"));
            if(LogCommun.isTechniqueDebug()) LogCommun.debug("MailSenderFactory","MailSender",(new StringBuffer("mailSender using ").append(mailSender.getName())).toString());
        } catch (ClassNotFoundException e) {
            LogCommun.major("COMMUN","MailSenderFactory","MailSender","Pb while getting default MailSender class", e);
            throw new MailException("Pb while getting default MailSender class");
        }

        try {
            return (MailSender) (mailSender.newInstance());
        } catch (InstantiationException e) {
            LogCommun.major("COMMUN","MailSenderFactory","MailSender","Pb while instantiating default MailSender class", e);
            throw new MailException("Pb while instantiating default MailSender class");
        } catch (IllegalAccessException e) {
            LogCommun.major("COMMUN","MailSenderFactory","MailSender","Pb while instantiating default MailSender class", e);
            throw new MailException("Pb while instantiating default MailSender class");
        }
    }

    public static MailSender getInstance() throws MailException {
        if (null == instance)
            instance = initDefaultInstance();
        if(LogCommun.isTechniqueDebug()) LogCommun.debug("MailSenderFactory","MailSender",(new StringBuffer("getInstance return ").append(instance.getClass().getName())).toString());
        return instance;
    }

    /**
     * Pour tests uniquement.
     * Cette methode crée une nouvelle instance de MailSender à chaque appel
     * @param type
     * @return l'instance
     * @throws MailException
     */
    public static MailSender getInstance(int type) throws MailException {
        switch (type) {
           case SIMPLE:
                return new SimpleMailSender();
           case THREADPOOL:
               return new ThreadPoolMailSender();
            default :
                return getInstance();
        }
    }

    public static synchronized void reload() throws Exception {
        synchronized (instance) {
            instance = initDefaultInstance();
        }
    }

}
