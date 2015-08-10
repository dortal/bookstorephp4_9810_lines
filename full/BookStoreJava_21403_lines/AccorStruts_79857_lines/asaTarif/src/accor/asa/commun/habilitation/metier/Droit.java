package com.accor.asa.commun.habilitation.metier;

import java.io.Serializable;

import com.accor.asa.commun.log.LogCommun;

/**
 *
 * @pattern	Flyweight, valeurs : 'lecture', 'ecriture' et 'interdit'.
 * @author	Jerome Pietri
 */
@SuppressWarnings("serial")
public class Droit implements Serializable, Comparable<Droit> {

	private static final char _codeEcriture = 'W';
	private static final char _codeLecture = 'R';
	private static final char _codeInterdit = 'I';
	private static final String _ecriture = "ReadWrite";
	private static final String _lecture = "ReadOnly";
	private static final String _interdit = "NoAccess";
	private static final Integer poidsEcriture = new Integer(2);
	private static final Integer poidsLecture = new Integer(1);
	private static final Integer poidsInterdit = new Integer(0);
	public static final Droit ECRITURE = new Droit(_codeEcriture, _ecriture, poidsEcriture);
	public static final Droit LECTURE = new Droit(_codeLecture, _lecture, poidsLecture);
	public static final Droit INTERDIT = new Droit(_codeInterdit, _interdit, poidsInterdit);

	private char code = _codeInterdit;
	private String type = null;
	private Integer poids = null;

	private Droit(char code, String unType, Integer unPoids)
	{
		this.code = code;
		type = unType;
		poids = unPoids;
	}

	/**
	 * @param codeDroit "RW", "R" ou ("I", "", null)
	 */
	public static Droit getDroitForCode( String codeDroit ) {
		if (codeDroit == null)
			return Droit.INTERDIT;
		if (codeDroit.equals(""))
			return Droit.INTERDIT;
		if (codeDroit.equalsIgnoreCase("i"))
			return Droit.INTERDIT;
		if (codeDroit.equalsIgnoreCase("r"))
			return Droit.LECTURE;
		if (codeDroit.equalsIgnoreCase("w"))
			return Droit.ECRITURE;
		LogCommun.warn("weblogic","Droit","getDroitForCode","pas de droit pour le code " + codeDroit);
		return Droit.INTERDIT;
	}

	/**
	 *
	 * @return java.lang.String
	 */
	public String toString() {
		return type;
	}

    public int compare(Droit droit) {
        return this.poids.compareTo(droit.poids);
    }
    
    /**
     * @see java.lang.Comparable#compareTo(Object)
     */
    public int compareTo(Droit d) {
        if( d == null )
            return -1;
        return compare(d);
    }

    /**
	* @see java.lang.Object#equals(Object)
	*/
	public boolean equals(Object obj) {
		if (!( obj instanceof Droit ))
			return false;
		Droit d2 = (Droit)obj;
		return d2.type.equals(type);
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return type.hashCode();
	}

	public Integer getPoids() {
		return poids;
	}

	public void setPoids(Integer poids) {
		this.poids = poids;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public char getCode () {
		return this.code;
	}

	public void setCode (char code) {
		this.code = code;
	}

}