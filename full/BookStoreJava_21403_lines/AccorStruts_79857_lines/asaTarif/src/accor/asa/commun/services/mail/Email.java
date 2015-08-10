package com.accor.asa.commun.services.mail;


import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Définit un Email.
 * @author <a href="mailto:LEHYARIC_Gildas@accor-hotels.com">gle</a>
 * @version $Id: Email.java,v 1.7 2006/02/10 16:58:31 dgo Exp $
 */
public class Email implements Serializable, Cloneable, Comparable<Email> {

    //private final static transient Log log = LogFactory.getLog(Email.class);

    private EmailAddress from;
    private Set<EmailAddress> to = new HashSet<EmailAddress>();
    private Set<EmailAddress> cc = new HashSet<EmailAddress>();
    private Set<EmailAddress> bcc = new HashSet<EmailAddress>();
    private EmailAddress reply;
    private String subject;
    private String subjectEncoding = Body.ISO_8859_1;
    private List<Body> bodies = new ArrayList<Body>();
    private int priority;
    private long dateCreatedMilli;

   /**
    * Ancien consstructeur par défaut, ne doit plus être utilisé
    * La classe MailGenerator se charge de donner une priorité par défaut
    * @deprecated
     */
    public Email(){
      priority = Integer.MAX_VALUE;
      dateCreatedMilli = Calendar.getInstance().getTimeInMillis();
    }

   /**
    * constructeur avec passage de la priorité du mail
    * une priorité à 0 equivaut a Integer.MAX_VALUE
     * @param pri
    */
   public Email(int pri){
      priority = pri;
      dateCreatedMilli = Calendar.getInstance().getTimeInMillis();
    }

   /**
    * renvoi la priorité d'un mail
     * @return la priorité du mail
    */
   public int getPriority(){
      return priority;
    }

   /**
    * renvoi la date de création d'un mail en millisecondes
    * @return la date de création
    */
    public long getDateCreated(){
       return dateCreatedMilli;
    }

    /**
     * Renvoie l'expediteur
     * @return l'adresse de l'expediteur
     */
    public EmailAddress getFrom() {
        return from;
    }

    /**
     * Spécifie l'expéditeur
     * @param from l'expediteur
     */
    public void setFrom(EmailAddress from) {
        this.from = from;
    }

    /**
     * Spécifie l'expéditeur avec l'adresse email passée en parametre
     * @param email l'adresse de l'expediteur
     * @throws MalformedInternetAddressException si l'adresse n'est pas conforme
     */
    public void setFrom(String email) throws MalformedInternetAddressException {
        setFrom(email, email);
    }

    /**
     * Spécifie l'expéditeur avec l'adresse email passée en parametre
     * @param email l'adresse de l'expéditeur
     * @param alias le nom de l'expéditeur
     * @throws MalformedInternetAddressException si l'adresse n'est pas conforme
     */

    public void setFrom(String email, String alias) throws MalformedInternetAddressException {
        setFrom(new EmailAddress(email, alias));
    }

    /**
     * Renvoie la liste des destinataires
     * @return la liste des destinataires
     * @see EmailAddress
     */
    public Set<EmailAddress> getTo() {
        return to;
    }

    /**
     * Ajoute un destinataire.
     * @param to un destinataire
     */
    public void addTo(EmailAddress to) {
        this.to.add(to);
    }

    /**
     * Ajoute plusieurs destinataires.
     * @param to une liste de destinataires
     * @throws IllegalArgumentException si un element de la collection n'est pas une instance de EmailAddress
     */
    public void addTo(Collection<EmailAddress> to) {
        this.to.addAll(to);
    }

    /**
     * Ajoute un destinataire.
     * @param email email du destinataire
     * @throws MalformedInternetAddressException si l'email n'est pas conforme
     */
    public void addTo(String email) throws MalformedInternetAddressException {
        addTo(email, email);
    }

    /**
     * Ajoute un destinataire
     * @param email email du destinataire
     * @param alias nom du destinataire
     * @throws MalformedInternetAddressException si l'email n'est pas conforme
     */
    public void addTo(String email, String alias) throws MalformedInternetAddressException {
        addTo(new EmailAddress(email, alias));
    }

    /**
     * Renovie la liste des destinataires en copie.
     * @return la liste des destinataires en copie
     */
    public Set<EmailAddress> getCc() {
        return cc;
    }

    /**
     * Ajoute un destinataire en copie
     * @param cc un destinataire en copie
     */
    public void addCc(EmailAddress cc) {
        this.cc.add(cc);
    }

    /**
     * Ajoute plusieurs destinataires en copie.
     * @param cc une liste de destinataires
     * @throws IllegalArgumentException si un element de la collection n'est pas une instance de EmailAddress
     */
    public void addCc(Collection<EmailAddress> cc) {
        this.cc.addAll(cc);
    }

    /**
     * Ajoute un destinataire en copie.
     * @param email email du destinataire
     * @throws MalformedInternetAddressException si l'email n'est pas conforme
     */
    public void addCc(String email) throws MalformedInternetAddressException {
        addCc(email, email);
    }

    /**
     * Ajoute un destinataire en copie.
     * @param email email du destinataire
     * @param alias nom du destinataire
     * @throws MalformedInternetAddressException si l'email n'est pas conforme
     */
    public void addCc(String email, String alias) throws MalformedInternetAddressException {
        addCc(new EmailAddress(email, alias));
    }

    /**
     * Renvoie la liste des destinataire en copie cachée.
     * @return la liste des destinataire en copie cachée
     */
    public Set<EmailAddress> getBcc() {
        return bcc;
    }

    /**
     * Ajoute un destinataire en copie cachée.
     * @param bcc un destinataire en copie cachée
     */
    public void addBcc(EmailAddress bcc) {
        this.bcc.add(bcc);
    }

    /**
     * Ajoute plusieurs destinataires en copie cachée
     * @param bcc liste des destinataires
     * @throws IllegalArgumentException  si un element de la collection n'est pas une instance de EmailAddress
     */
    public void addBcc(Collection<EmailAddress> bcc) {
        this.bcc.addAll(bcc);
    }

    /**
     * Ajoute un destinataire en copie cachée.
     * @param email email du destinataire
     * @throws MalformedInternetAddressException si l'email n'est pas conforme
     */
    public void addBcc(String email) throws MalformedInternetAddressException {
        addBcc(email, email);
    }

    /**
     * Ajoute un destinataire en copie cachée.
     * @param email email du destinataire
     * @param alias nom du destinataire
     * @throws MalformedInternetAddressException si l'email n'est pas conforme
     */

    public void addBcc(String email, String alias) throws MalformedInternetAddressException {
        addBcc(new EmailAddress(email, alias));
    }

    /**
     * Renvoie l'adresse de réponse
     * @return l'adresse de réponse
     */
    public EmailAddress getReply() {
        return reply;
    }

    /**
     * Spécifie l'adresse de réponse.
     * @param reply l'adresse de réponse
     */
    public void setReply(EmailAddress reply) {
        this.reply = reply;
    }

    /**
     * Spécifie l'adresse de réponse.
     * @param email email pour l'adresse de réponse
     * @throws MalformedInternetAddressException si l'email n'est pas conforme
     */
    public void setReply(String email) throws MalformedInternetAddressException {
        setReply(email, email);
    }

    /**
     * Spécifie l'adresse de réponse.
     * @param email email pour l'adresse de réponse
     * @param alias nom pour l'adresse de réponse
     * @throws MalformedInternetAddressException si l'email n'est pas conforme
     */

    public void setReply(String email, String alias) throws MalformedInternetAddressException {
        setReply(new EmailAddress(email, alias));
    }

    /**
     * Renvoie le sujet du mail.
     * @return le sujet du mail
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Spécifie le sujet du mail.
     * L'encoding par defaut est {@link Body#ISO_8859_1}
     * @param subject le sujet du mail
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Spécifie l'encoding pour le sujet du mail.
     * @param subjectEncoding l'encoding pour le sujet du mail.
     */

    public void setSubject(String subject, String subjectEncoding) {
        this.setSubject(subject);
        this.subjectEncoding = subjectEncoding;
    }

    /**
     * Renvoie l'encoding pour le sujet du mail.
     * Par defaut {@link Body#ISO_8859_1}
     * @return l'encoding pour le sujet du mail.
     */
    public String getSubjectEncoding() {
        return subjectEncoding;
    }

    /**
     * Renvoie la liste des corps de l'email
     * @return la liste des corps de l'email
     */
    public List<Body> getBodies() {
        return bodies;
    }

    /**
     * Ajoute un corps pour l'email
     * @param body un corps
     */
    public void addBody(Body body) {
        this.bodies.add(body);
    }

    /**
     * Ajoute plusieurs corps de mail
     * @param bodies une liste de corps
     * @see Body
     */
    public void addBodies(Collection<Body> bodies) {
        this.bodies.addAll(bodies);
    }

    /**
     * Ajoute un corps de mail
     * @param body le texte
     * @param mimeType le type mime {@link Body#TEXT_HTML} ou {@link Body#TEXT_PLAIN}
     * @param encoding l'encoding {@link Body#ISO_8859_1} ou {@link Body#UTF_8}
     * @throws MailException
     */
    public void addBody(String body, String mimeType, String encoding) throws MailException {
        this.bodies.add(new Body(body, mimeType, encoding));
    }

    public boolean equals(Email email) {
        if (this == email)
            return true;

        if (bcc != null ? !bcc.equals(email.bcc) : email.bcc != null)
            return false;
        if (bodies != null ? !bodies.equals(email.bodies) : email.bodies != null)
            return false;
        if (cc != null ? !cc.equals(email.cc) : email.cc != null)
            return false;
        if (from != null ? !from.equals(email.from) : email.from != null)
            return false;
        if (reply != null ? !reply.equals(email.reply) : email.reply != null)
            return false;
        if (subject != null ? !subject.equals(email.subject) : email.subject != null)
            return false;
        if (subjectEncoding != null ? !subjectEncoding.equals(email.subjectEncoding) : email.subjectEncoding != null)
            return false;
        if (to != null ? !to.equals(email.to) : email.to != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (from != null ? from.hashCode() : 0);
        result = 29 * result + (to != null ? to.hashCode() : 0);
        result = 29 * result + (cc != null ? cc.hashCode() : 0);
        result = 29 * result + (bcc != null ? bcc.hashCode() : 0);
        result = 29 * result + (reply != null ? reply.hashCode() : 0);
        result = 29 * result + (subject != null ? subject.hashCode() : 0);
        result = 29 * result + (subjectEncoding != null ? subjectEncoding.hashCode() : 0);
        result = 29 * result + (bodies != null ? bodies.hashCode() : 0);
        return result;
    }

    public Object clone() {
        try {
            Email o = (Email) super.clone();
            o.from = (EmailAddress) this.from.clone();

            o.cc = (Set<EmailAddress>) ((HashSet<EmailAddress>) (this.cc)).clone();
            o.to = (Set<EmailAddress>) ((HashSet<EmailAddress>) (this.to)).clone();
            o.bcc = (Set<EmailAddress>) ((HashSet<EmailAddress>) (this.bcc)).clone();
            o.reply = (EmailAddress) this.reply.clone();

            o.subject = this.subject;
            o.subjectEncoding = this.subjectEncoding;

            o.bodies = (List<Body>) ((HashSet<Body>) (this.bodies)).clone();
            return o;
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
            // ne devrait jamais arriver tant qu'on implemente Cloneable.
        }
    }

   /**
    * Comparateur naturel
    * @param other : le mail auquel on veut comparer le mail en cours
    * @return  resultat de la comparaison : -1, 0 ou 1
    */
   public int compareTo(Email other) {
      int res = 0;
      try {
         // les priorités marchent en mode inverse : 1 est plus fort que 9 ...
         int pri1 = this.getPriority();
         int pri2 = ((Email)other).getPriority();
         if (pri1>pri2) return -1;
         if (pri1<pri2) return 1;
         // priorité identiques ? le mail le plus ancien à la priorité
         long dateCreation1 = this.getDateCreated();
         long dateCreation2 = ((Email)other).getDateCreated();
         res = (dateCreation1<dateCreation2?1:-1);
      } catch (Exception ex){
         return 0;
      }
      return res;
   }

   /**
    * a des fins de debug, pas trop fiable du tout, ne pas utiliser pour autre chose que de l'info !!!
    * @return  taille du mail en byte (maisl taille sérialisée, donc pas représentative a 100% de ce qu'il y a en mémoire ...)
    */
   public int getMemorySize (){
      int result;
      try {
         ByteArrayOutputStream out = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(out);
         oos.writeObject(this);
         oos.close();
         out.close();
         result = out.size();
      } catch (Exception ex){
        result = 0;
      }
      return result;
   }
}
