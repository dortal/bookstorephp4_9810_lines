package com.accor.asa.commun.services.mail;

import java.io.Serializable;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;

/**
 * Corps d'un email.
 * Il s'agit prinsipalement d'un texte, de son encodage et de son type mime
 * @author <a href="mailto:LEHYARIC_Gildas@accor-hotels.com">gle</a>
 * @version $Id: Body.java,v 1.3 2005/06/08 12:00:08 gle Exp $
 */
public final class Body implements Serializable, Cloneable {

    public final static String TEXT_PLAIN = "text/plain";
    public final static String TEXT_HTML = "text/html";
    public final static String ISO_8859_1 = "iso-8859-1";
    public final static String UTF_8 = "utf-8";

    private String body;
    private String mimeType;
    private String encoding;
    private transient MimeBodyPart mbp; // non serializable

    public Body(final String body, final String mimeType, final String encoding) throws MailException {
        if (null == body || body.trim().length() == 0)
            throw new IllegalArgumentException("Body cannot be null or empty");
        this.body = body;

        if (!mimeType.equals(TEXT_PLAIN) && !mimeType.equals(TEXT_HTML))
            throw new IllegalArgumentException("mimeType is <" + mimeType + ">, must be Body.TEXT_PLAIN or Body.TEXT_HTML");
        this.mimeType = mimeType;

        if (!encoding.equals(ISO_8859_1) && !encoding.equals(UTF_8))
            throw new IllegalArgumentException("encoding <" + encoding + ">, must be Body.ISO_8859_1 or Body.UTF_8");
        this.encoding = encoding;

        mbp = new MimeBodyPart();
        try {
            mbp.setContent(body, mimeType + "; charset=" + encoding);
        } catch (MessagingException e) {
            throw new MailException("Error while assembling parameters, please verify encoding");
        }

    }

    public String getBody() {
        return body;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getEncoding() {
        return encoding;
    }

    public MimeBodyPart getMimeBodyPart() {
        return mbp;
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Body))
            return false;

        final Body body1 = (Body) o;

        if (body != null ? !body.equals(body1.body) : body1.body != null)
            return false;
        if (encoding != null ? !encoding.equals(body1.encoding) : body1.encoding != null)
            return false;
        if (mimeType != null ? !mimeType.equals(body1.mimeType) : body1.mimeType != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (body != null ? body.hashCode() : 0);
        result = 29 * result + (mimeType != null ? mimeType.hashCode() : 0);
        result = 29 * result + (encoding != null ? encoding.hashCode() : 0);
        return result;
    }

    /**
     * Clone l'instance.
     * @return un clone
     */
    public Object clone() {
        try {
            Body o = (Body) super.clone();
            o.body = this.body;
            o.encoding = this.encoding;
            o.mimeType = this.mimeType;

            o.mbp = new MimeBodyPart();

            try {
                o.mbp.setContent(this.body, this.mimeType + "; charset=" + this.encoding);
            } catch (MessagingException e) {
                throw new InternalError("Error while while cloning Body : " + e.getMessage());
                //ne devrait pas arriver puisque l'object à cloner existe.
            }
            return o;
        } catch (CloneNotSupportedException e) {
            throw new InternalError("Error while while cloning Body : " + e.getMessage());
            // ne devrait jamais arriver tant qu'on implemente Cloneable.
        }
    }
}
