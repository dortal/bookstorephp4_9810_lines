package com.accor.asa.commun.services.mail;

/**
 * MailSender est utilisé pour définr qu'un object peu envoyer un email.<br>
 * Pour propser d'autre methodes d'envoi, il est BEAUCOUP plus interessant d'etendre MailSenderProxy.
 * @see MailSenderProxy
 * @author <a href="mailto:LEHYARIC_Gildas@accor-hotels.com">gle</a>
 * @version $Id: MailSender.java,v 1.4 2006/02/08 19:39:12 dgo Exp $
 */

public interface MailSender {

    /**
     * Envoie l'email <code>email</code>
     * @param email email à envoyer
     * @throws MailException
     */
    void send(Email email) throws MailException;

    public void reload() throws Exception;
    // ne pas rajouter de méthode d'envoi ici, mais plutot dans MailSenderProxy

}