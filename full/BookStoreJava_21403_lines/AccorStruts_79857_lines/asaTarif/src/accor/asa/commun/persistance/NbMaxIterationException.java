/*
 * Created on 13 juin 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.accor.asa.commun.persistance;

/**
 * Exception levée dès lors que l'on depasse le nb max d'iterations autorisé pour une boucle recursive
 * Rappel : limite TC = 200
 */
public class NbMaxIterationException extends Exception {

  public NbMaxIterationException(String message) {
    super(message);
  }

  public NbMaxIterationException(Exception ex) {
    super(ex.getMessage());
  }

}
