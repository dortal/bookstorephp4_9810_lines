/**
 * 
 */
package com.accor.asa.commun.metier.enumtypeoffre;

/**
 * @author JLATINUS
 *  <Anomalie 5255> Sélection de "Groupes loisir ponctuels". lors d'un création de dossier de type : "Offre Ponctuel Loisir"
 * Code de type offre Groupe Ponctuelle Loisir
 */
public enum ETypeOffre {
	GROUPE_PONCTUELLLE_LOISIR("051");
	
	private String value;

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	private ETypeOffre(String value) {
		this.value = value;
	}
	
}
