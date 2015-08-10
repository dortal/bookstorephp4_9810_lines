package com.accor.asa.commun.services.mail;

/**
 * MailSender qui se content d'utiliser la classe SendMail.
 * @see SendMail#sendMail
 * @author <a href="mailto:LEHYARIC_Gildas@accor-hotels.com">gle</a>
 * @version $Id: SimpleMailSender.java,v 1.4 2006/02/08 19:41:43 dgo Exp $
 */
class SimpleMailSender implements MailSender {

    public void send(Email email) throws MailException {
        SendMail.sendMail(email);
    }

   public void reload() throws Exception {

   }

}
