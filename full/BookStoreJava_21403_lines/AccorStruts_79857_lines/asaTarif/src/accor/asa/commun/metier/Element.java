package com.accor.asa.commun.metier;

import java.io.Serializable;

/**
 * Title:        Projet Asa
 * Description:  Classe générique permettant de manipuler des informations binaires de type Code-Libelle.
 * Par exemple : CodePays / NomPays, CodeLangue / LibelleLangue, ...
 * Copyright:    Copyright (c) 2001
 * Company:      Valtech
 * @author David Dreistadt
 * @version 1.0
 */

@SuppressWarnings("serial")
public class Element implements Serializable {
  protected String code = null;
  protected String libelle = null;

  public Element() {
  }

  public Element(String code, String libelle) {
    this.code = code;
    this.libelle = libelle;
  }

  public String getCode () {
    return code;
  }

  public void setCode (String newValue) {
    this.code = newValue;
  }

  public String getLibelle () {
    return libelle;
  }

  public void setLibelle (String newValue) {
    this.libelle = newValue;
  }

  /**
   * Comparer l'objet
   * @param o
   * @return
   */
  public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Element element = (Element) o;
      return code.equals(element.code);
  }

  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append( "[code=" ).append( code ).append( "]," );
    sb.append( "[libelle=" ).append( libelle ).append( "]," );
	return sb.toString();
  }
}