package com.accor.asa.commun.services.mail;

import javax.mail.internet.InternetAddress;

import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.utiles.FilesPropertiesCache;

/**
 * Une adresse email.
 * C'est à dire un nom (toto) et une adresse mail valide (toto@toto.com).
 * @author <a href="mailto:LEHYARIC_Gildas@accor-hotels.com">gle</a>
 * @version $Id: EmailAddress.java,v 1.3 2005/06/08 12:00:08 gle Exp $
 */
public class EmailAddress implements java.io.Serializable, Cloneable {

    private String email = "";
    private String alias = "";
    private transient InternetAddress internetAddress = null; // non serializable

    private static transient EmailAddressValidator validator = getValidator();

    private  static EmailAddressValidator getValidator() {
        final String validatorfqcn = FilesPropertiesCache.getInstance().getValue("mail","mail.emailvalidator");

        try {
            return (EmailAddressValidator)(Class.forName(validatorfqcn).newInstance());
        } catch (InstantiationException e) {
            LogCommun.minor("COMMUN","EmailAddress","getValidator","EmailAddressValidator Instanciation Pb ",e);
        } catch (IllegalAccessException e) {
            LogCommun.minor("COMMUN","EmailAddress","getValidator","EmailAddressValidator Instanciation Pb ",e);
        } catch (ClassNotFoundException e) {
            LogCommun.minor("COMMUN","EmailAddress","getValidator","EmailAddressValidator Instanciation Pb ",e);
        }

        LogCommun.minor("COMMUN","EmailAddress","getValidator","returning RegExpAddressValidator as default EmailValidator instance");
        return new MailValidator();
    }

    /**
     * Construit l'objet avec l'adresse passée en paramètre.
     * @param email
     * @throws MalformedInternetAddressException si l'adresse n'est pas valide.
     */
    public EmailAddress(String email) throws MalformedInternetAddressException {
        this(email, email);
    }

    /**
     * Construit l'objet avec l'adresse et le nom passée en paramètre.
     * @param email
     * @param alias
     * @throws MalformedInternetAddressException si l'adresse n'est pas valide
     */
    public EmailAddress(String email, String alias) throws MalformedInternetAddressException {
        this.email = (null != email) ? email.trim() : "";
        this.alias = (null != alias) ? alias.trim() : "";

        if (!validator.validate(this.email))
            throw new MalformedInternetAddressException("<" + this.email + "> malformed");
        try {
            internetAddress = new InternetAddress(this.email, this.alias);
        } catch (java.io.UnsupportedEncodingException uee) {
            throw new MalformedInternetAddressException(uee.getMessage());
        }

    }

    /**
     * Renvoie l'adresse au format exploitable diractement par l'api JavaMail
     * @return l'instance InternetAddress correspondante
     * @see InternetAddress
     */
    public InternetAddress getInternetAddress() {
        return internetAddress;
    }

    /**
     * Renvoie l'adresse email
     * @return l'adresse email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Renvoie l'alias pour l'adresse email (le nom).
     * @return l'alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Representation lisble
     * @return l'adress au format <code>alias&lt;add@ress.email&gt;</code>
     */
    public String toString() {
        return this.alias + "<" + this.email + ">";
    }

    /**
     * Teste l'egalité d'objets <code>email</code>.
     * attention "Toto &lt;toto@toto.com&gt;" et "toto &lt;TOTO@toto.COM&gt;" sont considérés comme egaux.
     * @param o instance à comparer
     * @return true s'il y a egalité, les tests sont cases insensitive.
     *
     */
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof EmailAddress))
            return false;

        final EmailAddress emailAddress = (EmailAddress) o;

        if (!alias.equalsIgnoreCase(emailAddress.alias))
            return false;
        if (!email.equalsIgnoreCase(emailAddress.email))
            return false;

        return true;
    }

    /**
     * Definit le hashcode en prenant tenant copte du caractère "case insensitive"
     * @return le hashCode de l'instance.
     */
    public int hashCode() {
        int result;
        result = email.toLowerCase().hashCode();
        result = 29 * result + alias.toLowerCase().hashCode();
        return result;
    }

    /**
     * Clone l'instance.
     * @return un clone
     */
    public Object clone() {
        try {
            EmailAddress o = (EmailAddress) super.clone();
            o.alias = this.alias;
            o.email = this.email;

            try {
                o.internetAddress = new InternetAddress(this.email, this.alias);
            } catch (java.io.UnsupportedEncodingException uee) {
                throw new InternalError("erreur de clonage EmailAddress; " + uee.getMessage());
                // ne devrait pas arriver
            }
            return o;
        } catch (CloneNotSupportedException e) {
            throw new InternalError("erreur de clonage EmailAddress; " + e.getMessage());
            // ne devrait jamais arriver tant qu'on implemente Cloneable.
        }
    }
}