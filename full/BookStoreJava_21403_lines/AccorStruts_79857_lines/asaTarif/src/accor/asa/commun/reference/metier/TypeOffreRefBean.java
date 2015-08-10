package com.accor.asa.commun.reference.metier;

@SuppressWarnings("serial")
public class TypeOffreRefBean extends RefBean implements Comparable<TypeOffreRefBean> {

	private boolean exportTars 			= false;
	private boolean suiviConso 			= false;
	private boolean rfp 				= false;
	private boolean precoHotel 			= false;
    private boolean contratDistrib 		= false;
    private boolean reporting 			= false;
    private Integer	maxHotels 			= null;
	private String 	idGroupeOffre 		= null;
	private String 	libelleGroupeOffre 	= null;
	private String 	defaultTarsKey 		= null;
	private String 	defaultSegment 		= null;
    private String 	codeFamSegment 		= null;
	private Long 	numeroPackage		= null;
    private boolean engagementVolume	= false;

    public TypeOffreRefBean () {}

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append( "[code=" ).append( code ).append( "],") ;
        sb.append( "[libelle=" ).append( libelle ).append( "],") ;
        sb.append( "[libelleLong=" ).append( libelleLong ).append( "],") ;
        sb.append( "[actif=" ).append( actif ).append( "],") ;
        sb.append( "[codeLangue=" ).append( codeLangue ).append( "],") ;
        sb.append( "[oldId=" ).append( oldId ).append( "],") ;
        sb.append( "[exportTars=" ).append( exportTars ).append( "],") ;
        sb.append( "[suiviConso=" ).append( suiviConso ).append( "],") ;
        sb.append( "[rfp=" ).append( rfp ).append( "],") ;
        sb.append( "[precoHotel=" ).append( precoHotel ).append( "],") ;
        sb.append( "[contratDistrib=" ).append( contratDistrib ).append( "],") ;
        sb.append( "[reporting=" ).append( reporting ).append( "],") ;
        sb.append( "[maxHotels=" ).append( maxHotels ).append( "],") ;
        sb.append( "[idGroupeOffre=" ).append( idGroupeOffre ).append( "],") ;
        sb.append( "[libelleGroupeOffre=" ).append( libelleGroupeOffre ).append( "],") ;
        sb.append( "[defaultTarsKey=" ).append( defaultTarsKey ).append( "],") ;
        sb.append( "[defaultSegment=" ).append( defaultSegment ).append( "],") ;
        sb.append( "[codeFamSegment=" ).append( codeFamSegment ).append( "],") ;
        sb.append( "[numeroPackage=" ).append( numeroPackage ).append( "],") ;
        sb.append( "[engagementVolume=" ).append( engagementVolume ).append( "],") ;
        return sb.toString();
	}
	
    public int compareTo(TypeOffreRefBean obj) {
        return this.libelle.compareToIgnoreCase(obj.libelle);
    }

	public boolean isExportTars () {
		return exportTars;
	}

	public boolean isSuiviConso () {
		return suiviConso;
	}

	public boolean isRfp () {
		return rfp;
	}

	public boolean isPrecoHotel () {
		return precoHotel;
	}

    public boolean isContratDistrib() {
        return contratDistrib;
    }

    public boolean isReporting() {
        return reporting;
    }

    public Integer getMaxHotels () {
		return maxHotels;
	}

	public String getIdGroupeOffre () {
		return idGroupeOffre;
	}

	public String getLibelleGroupeOffre () {
		return libelleGroupeOffre;
	}

	public String getDefaultTarsKey () {
		return defaultTarsKey;
	}

	public String getDefaultSegment () {
		return defaultSegment;
	}

    public String getCodeFamSegment () {
        return codeFamSegment;
    }

    public void setExportTars (boolean exportTars) {
		this.exportTars = exportTars;
	}

	public void setSuiviConso (boolean suiviConso) {
		this.suiviConso = suiviConso;
	}

	public void setRfp (boolean rfp) {
		this.rfp = rfp;
	}

	public void setPrecoHotel (boolean precoHotel) {
		this.precoHotel = precoHotel;
	}

    public void setContratDistrib(boolean contratDistrib) {
        this.contratDistrib = contratDistrib;
    }

    public void setReporting(boolean reporting) {
        this.reporting = reporting;
    }

    public void setMaxHotels (Integer maxHotels) {
		this.maxHotels = maxHotels;
	}

	public void setIdGroupeOffre (String idGroupeOffre) {
		this.idGroupeOffre = idGroupeOffre;
	}

	public void setLibelleGroupeOffre (String libelleGroupeOffre) {
		this.libelleGroupeOffre = libelleGroupeOffre;
	}

	public void setDefaultTarsKey (String defaultTarsKey) {
		this.defaultTarsKey = defaultTarsKey;
	}

	public void setDefaultSegment (String defaultSegment) {
		this.defaultSegment = defaultSegment;
	}

    public void setCodeFamSegment (String codeFamSegment) {
        this.codeFamSegment = codeFamSegment;
    }

	public Long getNumeroPackage() {
		return numeroPackage;
	}

	public void setNumeroPackage(Long numeroPackage) {
		this.numeroPackage = numeroPackage;
	}

	public boolean isEngagementVolume() {
		return engagementVolume;
	}

	public void setEngagementVolume(boolean engagementVolume) {
		this.engagementVolume = engagementVolume;
	}
}