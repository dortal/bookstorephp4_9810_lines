package com.accor.asa.commun.process.pool;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 14 juin 2005
 * Time: 17:25:36
 * To change this template use File | Settings | File Templates.
 */
public class GlobalPoolException extends Exception {
    private String urlForDebug = null;
    /**
     * Constructeur
     */
    public GlobalPoolException() {
        super();
    }

    /**
     * Constructeur
     * @param message
     */
    public GlobalPoolException(String message) {
        this(message,null);
    }

    /**
     * Constructeur
     * @param exc
     */
    public GlobalPoolException(Exception exc)  {
          this(exc,null);
    }

    /**
     * Constructeur
     * @param message
     * @param urlForDebug
     */
    public GlobalPoolException(String message, String urlForDebug) {
        super(message);
        this.urlForDebug = urlForDebug;
    }

    /**
     * Constructeur
     * @param exc
     * @param urlForDebug
     */
    public GlobalPoolException(Exception exc, String urlForDebug)  {
          this(exc.getMessage(),urlForDebug);
    }
    /**
     * @return URL pour tester le TC (en principe celle qui cause l'erreur détectée)
     */
    public String getUrlForDebug( ) {
        return urlForDebug;
    }
}
