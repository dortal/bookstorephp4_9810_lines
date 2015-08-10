package com.accor.asa.commun.utiles.process;

/**
 * Created on 3 nov. 2005
 * Exception d'envoi
 * @author <a href="mailto:laurent.frobert@consulting-for.accor.com">lfrobert</a>
 * @version $Id: MessageSenderException.java 15024 2008-07-17 15:40:58Z fchivaux $
 */
@SuppressWarnings("serial")
public class MessageSenderException extends Exception {
    /**
     *  see name
     */
    public MessageSenderException() {
        super();
    }

    /**
     * @param message see name
     */
    public MessageSenderException(final String message) {
        super(message);
    }

    /**
     * @param cause see name
     */
    public MessageSenderException(final Throwable cause) {
        super(cause);
    }

    /**
     * @param message msg
     * @param cause see name
     */
    public MessageSenderException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
