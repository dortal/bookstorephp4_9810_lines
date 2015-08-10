package com.accor.asa.commun.services.mail;

/**
 * Exception levée lorsqu'une adresse email apparait comme non valide.
 * @see EmailAddress#getValidator() 
 * @author <a href="mailto:LEHYARIC_Gildas@accor-hotels.com">gle</a>
 * @version $Id: MalformedInternetAddressException.java,v 1.3 2005/06/08 12:00:08 gle Exp $
 */
public class MalformedInternetAddressException extends MailException {

    public MalformedInternetAddressException() {
        super();
    }

    public MalformedInternetAddressException(String msg) {
        super(msg);
    }

}