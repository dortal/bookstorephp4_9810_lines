package com.accor.asa.commun;

/**
 * classe générique d'exception avec message spécifique
 * <br>
 * Crée afin d'éviter d'ête "pollué" par Weblogic 6 qui modifie de façon 
 * intempestive le message (en faisant un setMessage()), notamment en lui
 * collant la stack au derrière.
 */
@SuppressWarnings("serial")
public class SafeMessageException extends Exception {

    /**
     * message propriétaire de l'erreur, garanti non modifié depuis sa création
     */
    private String m_messageOrigine = null;
    
    public SafeMessageException( ) {
        super();
    }
    
    public SafeMessageException( String message ) {
        super(message);
        m_messageOrigine = message;
    }
    
    /**
     * @param orgMessage si vrai, on retourne le message tel que fourni à sa création
     */
    public String getMessage(boolean orgMessage) {
        if (orgMessage) 
            return m_messageOrigine;
        else
            return super.getMessage();
    }

}
