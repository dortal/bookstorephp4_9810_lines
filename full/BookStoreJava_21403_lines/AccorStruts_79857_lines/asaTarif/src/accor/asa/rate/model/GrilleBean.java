package com.accor.asa.rate.model;

import java.io.Serializable;
import java.util.Date;

import com.accor.asa.commun.hotel.metier.Hotel;
import com.accor.asa.commun.reference.metier.FamilleTarifRefBean;
import com.accor.asa.commun.reference.metier.PeriodeValiditeRefBean;
import com.accor.asa.commun.reference.metier.StatutGrilleRefBean;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class GrilleBean implements Serializable {

    protected Long idGrille;
    protected String codeHotel;
    protected Integer idPeriodeValidite;
    protected Integer idFamilleTarif;
    protected Integer idStatut;
    protected Date dateCreation;
    protected String loginCreation;
    protected Date dateAddCXX;
    protected String loginAddCXX;
    protected Date dateLastModif;
    protected String loginLastModif;

    private Hotel hotel;
    private PeriodeValiditeRefBean periodeValidite;
    private FamilleTarifRefBean familleTarif;
    private StatutGrilleRefBean statutGrille;
    
    
    public GrilleBean() {
		super();
	}

	public GrilleBean(Long idGrille, String codeHotel, Integer idPeriodeValidite, Integer idFamilleTarif, Integer idStatut, Date dateCreation, String loginCreation, Date dateAddCXX, String loginAddCXX, Date dateLastModif, String loginLastModif) {
        this.idGrille = idGrille;
        this.codeHotel = codeHotel;
        this.idPeriodeValidite = idPeriodeValidite;
        this.idFamilleTarif = idFamilleTarif;
        this.idStatut = idStatut;
        this.dateCreation = dateCreation;
        this.loginCreation = loginCreation;
        this.dateAddCXX = dateAddCXX;
        this.loginAddCXX = loginAddCXX;
        this.dateLastModif = dateLastModif;
        this.loginLastModif = loginLastModif;
    }

    public Long getIdGrille() {
        return idGrille;
    }

    public void setIdGrille(Long idGrille) {
        this.idGrille = idGrille;
    }

    public String getCodeHotel() {
        return codeHotel;
    }

    public void setCodeHotel(String codeHotel) {
        this.codeHotel = codeHotel;
    }

    public Integer getIdPeriodeValidite() {
        return idPeriodeValidite;
    }

    public void setIdPeriodeValidite(Integer idPeriodeValidite) {
        this.idPeriodeValidite = idPeriodeValidite;
    }

    public Integer getIdFamilleTarif() {
        return idFamilleTarif;
    }

    public void setIdFamilleTarif(Integer idFamilleTarif) {
        this.idFamilleTarif = idFamilleTarif;
    }

    public Integer getIdStatut() {
        return idStatut;
    }

    public void setIdStatut(Integer idStatut) {
        this.idStatut = idStatut;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getLoginCreation() {
        return loginCreation;
    }

    public void setLoginCreation(String loginCreation) {
        this.loginCreation = loginCreation;
    }

    public Date getDateAddCXX() {
        return dateAddCXX;
    }

    public void setDateAddCXX(Date dateAddCXX) {
        this.dateAddCXX = dateAddCXX;
    }

    public String getLoginAddCXX() {
        return loginAddCXX;
    }

    public void setLoginAddCXX(String loginAddCXX) {
        this.loginAddCXX = loginAddCXX;
    }

    public Date getDateLastModif() {
        return dateLastModif;
    }

    public void setDateLastModif(Date dateLastModif) {
        this.dateLastModif = dateLastModif;
    }

    public String getLoginLastModif() {
        return loginLastModif;
    }

    public void setLoginLastModif(String loginLastModif) {
        this.loginLastModif = loginLastModif;
    }

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
		if(hotel==null)
			setCodeHotel(null);
		else
			setCodeHotel(hotel.getCode());
	}

	public PeriodeValiditeRefBean getPeriodeValidite() {
		return periodeValidite;
	}

	public void setPeriodeValidite(PeriodeValiditeRefBean periodeValidite) {
		this.periodeValidite = periodeValidite;
		if(periodeValidite==null)
			setIdPeriodeValidite(null);
		else
			setIdPeriodeValidite(Integer.valueOf(periodeValidite.getCode()));
	}

	public FamilleTarifRefBean getFamilleTarif() {
		return familleTarif;
	}

	public void setFamilleTarif(FamilleTarifRefBean familleTarif) {
		this.familleTarif = familleTarif;
		if(familleTarif==null)
			setIdFamilleTarif(null);
		else
			setIdFamilleTarif(Integer.valueOf(familleTarif.getId()));
	}

	public StatutGrilleRefBean getStatutGrille() {
		return statutGrille;
	}

	public void setStatutGrille(StatutGrilleRefBean statutGrille) {
		this.statutGrille = statutGrille;
		if(statutGrille==null)
			setIdStatut(null);
		else
			setIdStatut(Integer.valueOf(statutGrille.getCode()));
	}
    
    
}
