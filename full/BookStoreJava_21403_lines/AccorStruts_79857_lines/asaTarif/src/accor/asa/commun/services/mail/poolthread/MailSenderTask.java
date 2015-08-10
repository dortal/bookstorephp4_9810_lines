package com.accor.asa.commun.services.mail.poolthread;

import java.util.Date;

import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.services.mail.Email;
import com.accor.asa.commun.services.mail.MailException;
import com.accor.asa.commun.services.mail.SendMail;

/**
 * Created by IntelliJ IDEA.
 * User: dgonneau
 * Date: 31 janv. 2006
 * Time: 16:03:02
 */
public class MailSenderTask implements Runnable,Comparable<MailSenderTask> {

   private Email _mail;

   MailSenderTask(Email mail){
      _mail = mail;
   }

   public Email getMail(){
      return _mail;
   }

   public void run() {
      try {
         if (LogCommun.isTechniqueDebug()) {
            String sPriority = String.valueOf(_mail.getPriority());
            if (_mail.getPriority() == Integer.MAX_VALUE) sPriority = "basse";
            LogCommun.debug("MailSenderTask","MailSenderTask","Demarrage envoi d'un mail de priorite "+sPriority+" a "+new Date().toString());
         }
         SendMail.sendMail(_mail);
      } catch (MailException me) {
         if (LogCommun.isTechniqueDebug())
            LogCommun.debug("MailSenderTask","MailSenderTask","MailSenderTask failed, "+ me.getMessage());
      }
   }

    public int compareTo(MailSenderTask other) {
      // un mail de priorité 1 est plus important qu'un mail de priorité 2
      // mais dans la priority queue, les elements les plus faibles sont placés en tête de la queue.
      // on inverse donc ici le compareTo !!!! (afin de laisser un comparateur naturel logique dans la classe email)
      return other.getMail().compareTo(_mail);
    }
}
