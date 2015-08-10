package com.accor.asa.commun.services.mail;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.utiles.FilesPropertiesCache;

/**
 * Classe utilitaire permettant d'envoyer un email.
 * Ne pas utiliser directement (risque de surcharge du serveur de mail)
 * @see MailSenderFactory
 * @author <a href="mailto:LEHYARIC_Gildas@accor-hotels.com">gle</a>
 * @version $Id: SendMail.java,v 1.5 2006/02/07 17:40:05 dgo Exp $
 */
public class SendMail {

    static Properties mailProperties;

    static {
        initSendMail();
    }

    private synchronized static void initSendMail() {
        LogCommun.debug("SendMail","initSendMail","init sendMail");
        FilesPropertiesCache.getInstance().reloadProperties("mail");
        mailProperties = new Properties();
        mailProperties.put("mail.host", FilesPropertiesCache.getInstance().getValue("mail", "mail.sendmail.smtp"));
        mailProperties.put("mail.debug", FilesPropertiesCache.getInstance().getValue("mail", "mail.sendmail.debug"));
        mailProperties.put("mail.smtp.connectiontimeout", FilesPropertiesCache.getInstance().getValue("mail", "mail.sendmail.smtp.connectiontimeout"));
        mailProperties.put("mail.smtp.timeout", FilesPropertiesCache.getInstance().getValue("mail", "mail.sendmail.smtp.timeout"));
    }

    public static void sendMail(Email email) throws MailException {

      try {
         Session session = Session.getInstance(mailProperties, null);
         /*
         // initialisation avec la reference au ressource factory definie dans le web.xml et qui fait référence au serveur mail par JNDI dans le sun-web.xml
         try {
             if (LogCommun.isDebug) LogCommun.debug("SendMail","initSendMail","Getting SessionMail from JNDI");
             Context initCtx = new InitialContext();
             session = (Session) initCtx.lookup("java:comp/env/mail/AsaSession");
         } catch (NamingException ne) {
             LogCommun.major("COMMUN","SendMail","sendMail","Impossible de recuperer une session mail dans le jndi, utilisation du mail.properties", ne);
             session = Session.getInstance(mailProperties, null);
         }
         */
         // construction du mail
         MimeMessage msg = new MimeMessage(session);
         msg.setFrom(email.getFrom().getInternetAddress());

         if (null != email.getReply())
             msg.setReplyTo(new InternetAddress[]{email.getReply().getInternetAddress()});

         for (Iterator<EmailAddress> i = email.getTo().iterator(); i.hasNext();)
             msg.addRecipient(Message.RecipientType.TO, i.next().getInternetAddress());

         for (Iterator<EmailAddress> i = email.getCc().iterator(); i.hasNext();)
             msg.addRecipient(Message.RecipientType.CC, i.next().getInternetAddress());

         for (Iterator<EmailAddress> i = email.getBcc().iterator(); i.hasNext();)
             msg.addRecipient(Message.RecipientType.BCC, i.next().getInternetAddress());

         msg.setSubject(email.getSubject(), email.getSubjectEncoding());

         if (email.getBodies().size()>1){
             MimeMultipart mmpContent = new MimeMultipart("alternative");
             for (Iterator<Body> i = email.getBodies().iterator(); i.hasNext();) {
                 if (LogCommun.isTechniqueDebug()) LogCommun.debug("SendMail","sendMail","adding body part");
                 mmpContent.addBodyPart(i.next().getMimeBodyPart());
             }
             msg.setContent(mmpContent);
         } else {
             Body b = (Body)email.getBodies().get(0);
             msg.setText(b.getBody(),b.getEncoding());
             msg.setHeader("Content-Type", b.getMimeType());
         }

         msg.setSentDate(new Date());

         if (LogCommun.isTechniqueDebug()) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            msg.writeTo(baos);
            LogCommun.debug("SendMail","sendMail", baos.toString());
            LogCommun.debug("SendMail","sendMail", "send at "+new Date().toString());
            LogCommun.debug("SendMail","sendMail", "timeout will wait "+session.getProperty("mail.smtp.timeout")+ " milliseconds.");
            LogCommun.debug("SendMail","sendMail", "connectiontimeout will wait "+session.getProperty("mail.smtp.connectiontimeout")+ " milliseconds.");
         }

         Transport.send(msg);
         if (LogCommun.isTechniqueDebug())  LogCommun.debug("SendMail","sendMail", "send complete at "+new Date().toString());

      } catch (SendFailedException sfe) {
         LogCommun.major("COMMUN","SendMail","sendMail","Impossible d'envoyer le message au serveur.",sfe);
         throw new MailException("Probleme d'envoi de mail :" + sfe.getMessage());
      } catch (Exception e) {
         LogCommun.major("COMMUN","SendMail","sendMail","Probleme d'envoi de mail", e);
         throw new MailException("Probleme d'envoi de mail :" + e.getMessage());
      }
   }

    public static synchronized void reload() throws Exception {
        synchronized(mailProperties){
            initSendMail();
        }
    }

}
