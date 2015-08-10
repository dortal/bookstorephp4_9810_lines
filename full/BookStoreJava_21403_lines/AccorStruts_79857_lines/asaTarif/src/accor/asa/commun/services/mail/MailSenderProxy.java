package com.accor.asa.commun.services.mail;

import java.util.HashSet;
import java.util.Set;

/**
 * Classe proposant plusieurs raccourcis puor envoyer un email.
 * Utilise une instance de MailSender.
 * @see MailSender
 * @see MailSenderFactory
 * @author <a href="mailto:LEHYARIC_Gildas@accor-hotels.com">gle</a>
 * @version $Id: MailSenderProxy.java,v 1.3 2005/06/08 12:00:08 gle Exp $
 */
public class MailSenderProxy {

    private final MailSender ms;

    /**
     * Initialise un proxy avec un MailSender.
     * @param ms MailSender à utiliser
     */
    public MailSenderProxy(MailSender ms) {
        this.ms = ms;
    }

    /**
     * Envoie un mail avec les parametres suivants.
     * type mime et encogade par defaut {@link Body#TEXT_PLAIN} et {@link Body#ISO_8859_1}
     * @param from de
     * @param to à
     * @param subject sujet
     * @param body corps
     * @throws MailException
     */
    public void send(EmailAddress from, EmailAddress to, String subject, String body) throws MailException {
        Set<EmailAddress> tol = new HashSet<EmailAddress>();
        tol.add(to);
        send(from, tol, null, null, null, subject, body, Body.TEXT_PLAIN, Body.ISO_8859_1);
    }

    /**
     * Envoie un mail avec les paramètres suivants.
     * @param from de
     * @param to à
     * @param cc copie
     * @param bcc copie cachée
     * @param reply répondre à
     * @param subject sujet
     * @param body coprs
     * @param mimeType type mime
     * @param encoding encodage
     * @throws MailException
     */
    public void send(EmailAddress from, Set<EmailAddress> to, Set<EmailAddress> cc, Set<EmailAddress> bcc, EmailAddress reply, String subject, String body, String mimeType, String encoding) throws MailException {
        if (null == cc)
            cc = new HashSet<EmailAddress>();
        if (null == bcc)
            bcc = new HashSet<EmailAddress>();
        checkParameters(from, to, cc, bcc, reply, subject, body, mimeType, encoding);

        Email e = new Email();
        e.setFrom(from);
        e.addTo(to);
        e.addCc(cc);
        e.addBcc(bcc);
        e.setSubject(subject,encoding);
        e.setReply(reply);
        e.addBody(body, mimeType, encoding);
        ms.send(e);
    }

    /**
     * Envoi un email
     * @param email l'email à envoyer
     * @throws MailException
     */
    public void send(Email email) throws MailException {
        ms.send(email);
    }

    /**
     * verifie les parametres d'envoi de mail.
     * @TODO : a completer
     * @param from de
     * @param to à
     * @param cc copie
     * @param bcc copie cachée
     * @param reply repondre à
     * @param subject sujet
     * @param body corps
     * @param mimeType type mime
     * @param encoding encodage
     * @throws MailException
     */
    private void checkParameters(EmailAddress from, Set<EmailAddress> to, Set<EmailAddress> cc, Set<EmailAddress> bcc, EmailAddress reply, String subject, String body, String mimeType, String encoding) throws MailException {
        if (null == from) {
            throw new MailException("from cannot be null");
        } else {
            if ((null == to || to.size() == 0) && (null == cc || cc.size() == 0) && (null == bcc || bcc.size() == 0))
                throw new MailException("must provide at least one recipient");
        }

    }

}
