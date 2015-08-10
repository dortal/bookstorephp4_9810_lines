package com.accor.asa.rate.model;

import java.util.Date;

import com.accor.asa.commun.metier.AsaDate;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class SpecialRateBean extends RateBean implements SpecialRate{

	private Integer codeOffreSpeciale;
	private Date dateDebut;
	private Date dateFin;
	private boolean obligatoire;
	private Double prix;
	private Integer idUnitePrix;
	private Integer idTypePrix;
	private String codeDevise;
	private boolean tousMarches;
	private String codePays1;
	private String codePays2;
	private String codePays3;
	private String codePays4;
	private String codePays5;
	private String codeContinent1;
	private String codeContinent2;
	private String codeContinent3;
	
	private String commentaire;
	
	
	public Integer getCodeOffreSpeciale() {
		return codeOffreSpeciale;
	}

	public void setCodeOffreSpeciale(Integer codeOffreSpeciale) {
		this.codeOffreSpeciale = codeOffreSpeciale;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public boolean isObligatoire() {
		return obligatoire;
	}

	public void setObligatoire(boolean obligatoire) {
		this.obligatoire = obligatoire;
	}

	public Double getPrix() {
		return prix;
	}

	public void setPrix(Double prix) {
		this.prix = prix;
	}

	public Integer getIdUnitePrix() {
		return idUnitePrix;
	}

	public void setIdUnitePrix(Integer idUnitePrix) {
		this.idUnitePrix = idUnitePrix;
	}

	public Integer getIdTypePrix() {
		return idTypePrix;
	}

	public void setIdTypePrix(Integer idTypePrix) {
		this.idTypePrix = idTypePrix;
	}

	public String getCodeDevise() {
		return codeDevise;
	}

	public void setCodeDevise(String codeDevise) {
		this.codeDevise = codeDevise;
	}

	public boolean isTousMarches() {
		return tousMarches;
	}

	public void setTousMarches(boolean tousMarches) {
		this.tousMarches = tousMarches;
	}

	public String getCodePays1() {
		return codePays1;
	}

	public void setCodePays1(String codePays1) {
		this.codePays1 = processEmptyString(codePays1);
	}

	public String getCodePays2() {
		return codePays2;
	}

	public void setCodePays2(String codePays2) {
		this.codePays2 = processEmptyString(codePays2);
	}

	public String getCodePays3() {
		return codePays3;
	}

	public void setCodePays3(String codePays3) {
		this.codePays3 = processEmptyString(codePays3);
	}

	public String getCodePays4() {
		return codePays4;
	}

	public void setCodePays4(String codePays4) {
		this.codePays4 = processEmptyString(codePays4);
	}

	public String getCodePays5() {
		return codePays5;
	}

	public void setCodePays5(String codePays5) {
		this.codePays5 = processEmptyString(codePays5);
	}

	public String getCodeContinent1() {
		return codeContinent1;
	}

	public void setCodeContinent1(String codeContinent1) {
		this.codeContinent1 = processEmptyString(codeContinent1);
	}
	public String getCodeContinent2() {
		return codeContinent2;
	}

	public void setCodeContinent2(String codeContinent2) {
		this.codeContinent2 = processEmptyString(codeContinent2);
	}

	public String getCodeContinent3() {
		return codeContinent3;
	}

	public void setCodeContinent3(String codeContinent3) {
		this.codeContinent3 = processEmptyString(codeContinent3);
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	private String processEmptyString(String code)
	{
		if(code != null && code.trim().equals(""))
			return null;
		return code;
	}

    //=========================================================================================
    //=============                       OTHER                  ==============================
    //=========================================================================================

    @Override
    public String generateBeanKey() {
        StringBuffer bf = new StringBuffer(String.valueOf(getIdGrille()));
        bf.append("_").append(getCodeOffreSpeciale())
        .append("_").append(new AsaDate(getDateDebut()));
        return bf.toString();
    }

    /**
     * To String
     *
     * @return
     */
    public String toString() {
        StringBuffer sb = new StringBuffer("(").append(key).append(")");
        sb.append("_").append(idGrille).
                append("_").append(codeOffreSpeciale).
                append("_").append(new AsaDate(dateDebut)).
                append("_").append(new AsaDate(dateFin)).
                append("_").append(obligatoire).
                append("_").append(prix).
                append("_").append(idUnitePrix).
                append("_").append(idTypePrix).
                append("_").append(codeDevise).
                append("_").append(tousMarches).
                append("_").append(codePays1).
                append("_").append(codePays2).
                append("_").append(codePays3).
                append("_").append(codePays4).
                append("_").append(codePays5).
                append("_").append(codeContinent1).
                append("_").append(codeContinent2).
                append("_").append(codeContinent3);
        return sb.toString();
    }


}
