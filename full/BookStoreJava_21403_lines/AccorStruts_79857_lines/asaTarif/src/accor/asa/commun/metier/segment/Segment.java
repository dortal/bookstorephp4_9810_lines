package com.accor.asa.commun.metier.segment;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;

@SuppressWarnings("serial")
public class Segment implements Serializable {

	private String code;
	private String nom;
    private String codeTypeSegment;

    /**
     * Constructeur
     */
    public Segment() {
    }

    /**
     * Constructeur
     * @param code
     * @param nom
     * @param codeTypeSegment
     */
    public Segment(String code, String nom, String codeTypeSegment) {
        this.code = code;
        this.nom = nom;
        this.codeTypeSegment = codeTypeSegment;
    }

    // ================ GETTER AND SETTER ======================

    public String getCode() {
		return code;
	}

    public void setCode(String code) {
		this.code = code;
	}

	public String getNom() {
		return nom;
	}

    public void setNom(String nom) {
		this.nom = nom;
	}


    public String getCodeTypeSegment() {
        return codeTypeSegment;
    }

    public void setCodeTypeSegment(String codeTypeSegment) {
        this.codeTypeSegment = codeTypeSegment;
    }

    public static String getNom( String code, List<Segment> segments ) {
		Segment segment;
		if( segments != null ) {
			for( int i=0, size=segments.size(); i<size; i++ ) {
				segment = segments.get(i);
				if( StringUtils.equals( segment.getCode(), code ) )
					return segment.getNom();
			}
		}
		return null;
	}

    /**
     * To String
     * @return
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[code=").append(code).append("]  ");
        sb.append("[nom=").append(nom).append("]  ");
        sb.append("[codeTypeSegment=").append(codeTypeSegment).append("]  ");
        return sb.toString();
    }
}
