package com.accor.asa.commun.services.mail;

/**
 * Exception generique levée lors de probleme d'envoie ou de traitement d'email.
 * @author <a href="mailto:LEHYARIC_Gildas@accor-hotels.com">gle</a>
 * @version $Id: MailException.java,v 1.3 2005/06/08 12:00:08 gle Exp $
 */
public class MailException extends Exception {

    public MailException() {
        super();
    }

    public MailException(String msg) {
        super(msg);
    }

}