package com.accor.asa.rate.model;

import java.util.Date;
import java.util.HashMap;

import com.accor.asa.commun.metier.AsaDate;

public class BusinessRateBean extends RateBean {

	
	private String codeRateLevel;
	private String[] codesProduit;
	private String codeMealPlan;
	private String codePeriode;
	private Integer idDivSemaine;
	private Date dateDebut;
	private Date dateFin;
	private Integer idDureeSejour;
	private String libelleSalon;
	private String codePetitDej;
	private Double prixPdj;
	private String codeDevise;
	private Double valueCommission;
	private String uniteCommission;
	private Boolean lunWe;
	private Boolean marWe;
	private Boolean merWe;
	private Boolean jeuWe;
	private Boolean venWe;
	private Boolean samWe;
	private Boolean dimWe;

	private Boolean openNewContrat;
	private Boolean blackOutDates;
	private Integer nbreNuitsMin;
	private Integer nbreNuitsMax;
	
	private HashMap<Integer, Double> prices = new HashMap<Integer, Double>();

	public BusinessRateBean() {
		super();
		lunWe=false;
		marWe=false;
		merWe=false;
		jeuWe=false;
		venWe=false;
		samWe=false;
		dimWe=false;
		openNewContrat=false;
		blackOutDates=false;
	}

	public String getCodeRateLevel() {
		return codeRateLevel;
	}

	public void setCodeRateLevel(String codeRateLevel) {
		this.codeRateLevel = codeRateLevel;
	}

	public String[] getCodesProduit() {
		return codesProduit;
	}

	
	public void setCodesProduit(String[] codeProduit) {
		this.codesProduit = codeProduit;
	}

	public String getCodeProduit() {
		if (codesProduit != null && codesProduit.length > 0)
			return codesProduit[0];
		else
			return null;
	}

	public void setCodeProduit(String unCodeProduit) {
		if (codesProduit == null)
			codesProduit = new String[1];
		codesProduit[0] = unCodeProduit;
	}

    public int getNbreProduits() {
        return (codesProduit!=null)?codesProduit.length:0;
    }

    public String getCodeMealPlan() {
		return codeMealPlan;
	}

	public void setCodeMealPlan(String codeMealPlan) {
		this.codeMealPlan = codeMealPlan;
	}

	public String getCodePeriode() {
		return codePeriode;
	}

	public void setCodePeriode(String codePeriode) {
		this.codePeriode = codePeriode;
	}

	public Integer getIdDivSemaine() {
		return idDivSemaine;
	}

	public void setIdDivSemaine(Integer idDivSemaine) {
		this.idDivSemaine = idDivSemaine;
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

	public Integer getIdDureeSejour() {
		return idDureeSejour;
	}

	public void setIdDureeSejour(Integer idDureeSejour) {
		this.idDureeSejour = idDureeSejour;
	}

	public String getLibelleSalon() {
		return libelleSalon;
	}

	public void setLibelleSalon(String libelleSalon) {
		this.libelleSalon = libelleSalon;
	}

	public String getCodePetitDej() {
		return codePetitDej;
	}

	public void setCodePetitDej(String codePetitDej) {
		this.codePetitDej = codePetitDej;
	}

	public Double getPrixPdj() {
		return prixPdj;
	}

	public void setPrixPdj(Double prixPdj) {
		this.prixPdj = prixPdj;
	}

	public String getCodeDevise() {
		return codeDevise;
	}

	public void setCodeDevise(String codeDevise) {
		this.codeDevise = codeDevise;
	}

	public Double getValueCommission() {
		return (valueCommission == null ? (double) 0 : valueCommission);
	}

	public void setValueCommission(Double valueCommission) {
		this.valueCommission = valueCommission;
	}

	public String getUniteCommission() {
		return uniteCommission;
	}

	public void setUniteCommission(String uniteCommission) {
		this.uniteCommission = uniteCommission;
	}

	public Boolean getLunWe() {
		return lunWe;
	}

	public void setLunWe(Boolean lunWe) {
		this.lunWe = lunWe;
	}

	public Boolean getMarWe() {
		return marWe;
	}

	public void setMarWe(Boolean marWe) {
		this.marWe = marWe;
	}

	public Boolean getMerWe() {
		return merWe;
	}

	public void setMerWe(Boolean merWe) {
		this.merWe = merWe;
	}

	public Boolean getJeuWe() {
		return jeuWe;
	}

	public void setJeuWe(Boolean jeuWe) {
		this.jeuWe = jeuWe;
	}

	public Boolean getVenWe() {
		return venWe;
	}

	public void setVenWe(Boolean venWe) {
		this.venWe = venWe;
	}

	public Boolean getSamWe() {
		return samWe;
	}

	public void setSamWe(Boolean samWe) {
		this.samWe = samWe;
	}

	public Boolean getDimWe() {
		return dimWe;
	}

	public void setDimWe(Boolean dimWe) {
		this.dimWe = dimWe;
	}

	public Boolean getOpenNewContrat() {
		return openNewContrat;
	}

	public void setOpenNewContrat(Boolean openNewContrat) {
		this.openNewContrat = openNewContrat;
	}

	public Boolean getBlackOutDates() {
		return blackOutDates;
	}

	public void setBlackOutDates(Boolean blackOutDates) {
		this.blackOutDates = blackOutDates;
	}

	public Integer getNbreNuitsMin() {
		return nbreNuitsMin;
	}

	public void setNbreNuitsMin(Integer nbreNuitsMin) {
		this.nbreNuitsMin = nbreNuitsMin;
	}

	public Integer getNbreNuitsMax() {
		return nbreNuitsMax;
	}

	public void setNbreNuitsMax(Integer nbreNuitsMax) {
		this.nbreNuitsMax = nbreNuitsMax;
	}


	public Double getPriceForPax(int nbPax) {
		return prices.get(nbPax);
	}

	public void setPriceForPax(int nbPax, Double price) {
		prices.put(nbPax, price);
	}

    //=========================================================================================
    //=============                       OTHER                  ==============================
    //=========================================================================================

    public String generateBeanKey() {
		StringBuffer sb = new StringBuffer(String.valueOf(getIdGrille()));
		sb.append("_").append(getCodeRateLevel()).
                append("_").append(getCodeProduit()).
                append("_").append(getCodeMealPlan()).
                append("_").append(getCodePeriode()).
                append("_").append(getIdDivSemaine()).
                append("_").append(new AsaDate(getDateDebut()));
		return sb.toString();
	}

    /**
     * To String
     *
     * @return
     */
    public String toString() {
        StringBuffer sb = new StringBuffer("(").append(key).append(")");
        sb.append("_").append(idGrille).append("_").append(codeRateLevel);
        if (codesProduit != null) {
            sb.append("_[");
            for (String chambre : codesProduit)
                sb.append((sb.toString().endsWith("[")) ? chambre : "," + chambre);
            sb.append("]");
        } else {
            sb.append("_").append("null");
        }
        sb.append("_").append(codeMealPlan).
                append("_").append(codePeriode).
                append("_").append(idDivSemaine).
                append("_").append(new AsaDate(dateDebut)).
                append("_").append(new AsaDate(dateFin)).
                append("_").append(getPriceForPax(1)).
                append("_").append(getPriceForPax(2)).
                append("_").append(idDureeSejour).
                append("_").append(libelleSalon).
                append("_").append(codePetitDej).
                append("_").append(prixPdj).
                append("_").append(codeDevise).
                append("_").append(valueCommission).
                append("_").append(uniteCommission).
                append("_").append(lunWe).
                append("_").append(marWe).
                append("_").append(merWe).
                append("_").append(jeuWe).
                append("_").append(venWe).
                append("_").append(samWe).
                append("_").append(dimWe).
                append("_").append(openNewContrat).
                append("_").append(blackOutDates).
                append("_").append(nbreNuitsMin).
                append("_").append(nbreNuitsMax);
        return sb.toString();
    }

}
