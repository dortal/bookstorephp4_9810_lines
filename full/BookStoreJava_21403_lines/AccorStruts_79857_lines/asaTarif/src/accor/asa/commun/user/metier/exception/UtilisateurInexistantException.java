package com.accor.asa.commun.user.metier.exception;

/**
 * Exception renvoyée si l'utilisateur est inconnu du systême
 * @author	David Dreistadt
 */
@SuppressWarnings("serial")
public class UtilisateurInexistantException extends Exception {
/**
 * UtilisateurInexistantException constructor comment.
 * @param s java.lang.String
 */
public UtilisateurInexistantException(String s) {
	super(s);
}
}