package com.accor.asa.commun.utiles.process;

/**
 * Created on 3 nov. 2005
 * Time Out de connection dépassé
 * @author <a href="mailto:laurent.frobert@consulting-for.accor.com">lfrobert</a>
 * @version $Id: SendingConnectionTOException.java 15024 2008-07-17 15:40:58Z fchivaux $
 */
@SuppressWarnings("serial")
public class SendingConnectionTOException extends Exception {
    /**
    *
    */
   public SendingConnectionTOException() {
       super();
   }

   /**
    * @param message
    */
   public SendingConnectionTOException(final String message) {
       super(message);
   }

   /**
    * @param cause
    */
   public SendingConnectionTOException(final Throwable cause) {
       super(cause);
   }

   /**
    * @param message
    * @param cause
    */
   public SendingConnectionTOException(final String message, final Throwable cause) {
       super(message, cause);
   }

}
