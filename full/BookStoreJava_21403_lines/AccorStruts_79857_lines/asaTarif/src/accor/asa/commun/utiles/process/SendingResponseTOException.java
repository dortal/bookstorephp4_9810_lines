package com.accor.asa.commun.utiles.process;

/**
 * Created on 3 nov. 2005
 * Exception pour TO de temps de réponse dépassé
 * @author <a href="mailto:laurent.frobert@consulting-for.accor.com">lfrobert</a>
 * @version $Id: SendingResponseTOException.java 15024 2008-07-17 15:40:58Z fchivaux $
 */
@SuppressWarnings("serial")
public class SendingResponseTOException extends Exception {

	/**
	 * 
	 */
	public SendingResponseTOException() {
		super();
	}

	/**
	 * @param message
	 */
	public SendingResponseTOException(final String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public SendingResponseTOException(final Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public SendingResponseTOException(final String message,final Throwable cause) {
		super(message, cause);
	}

}
