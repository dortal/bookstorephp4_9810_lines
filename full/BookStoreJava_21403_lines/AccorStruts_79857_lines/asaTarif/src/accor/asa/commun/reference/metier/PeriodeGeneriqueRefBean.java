package com.accor.asa.commun.reference.metier;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.reference.process.RefFacade;


@SuppressWarnings("serial")
public class PeriodeGeneriqueRefBean extends RefBean {

    public static final String CODE_PERGEN_BUSINESS_PUBLISHED   = "GB";
    public static final String CODE_PERGEN_BUSINESS_COMPANIES   = "GC";
    public static final String CODE_PERGEN_LEISURE_LEISURE      = "IT";
    public static final String CODE_PERGEN_LEISURE_GROUP        = "GR";

    public static final String CODE_PERGEN_BUSINESS_CLOSING = "BC";
    public static final String CODE_PERGEN_BUSINESS_EVENTS  = "BF";
    public static final String CODE_PERGEN_BUSINESS_HOLIDAYS= "BH";

    public static final String CODE_PERGEN_LEISURE_CLOSING = "TC";
    public static final String CODE_PERGEN_LEISURE_EVENTS  = "TF";
    public static final String CODE_PERGEN_LEISURE_HOLIDAYS= "TH";


    private String libelleVente = null;
	private int idGroupeTarif = 0;
	private String name;
	
	private GroupeTarifRefBean groupeTarif;

	public String getLibelleVente() {
		return libelleVente;
	}

	public int getIdGroupeTarif() {
		return idGroupeTarif;
	}

	public void setLibelleVente(String libelleVente) {
		this.libelleVente = libelleVente;
	}

	public void setIdGroupeTarif(int idGroupeTarifs) {
		this.idGroupeTarif = idGroupeTarifs;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

	public PeriodeGeneriqueRefBean() {
	}

	public GroupeTarifRefBean getGroupeTarif() {
		return groupeTarif;
	}

	public void setGroupeTarif(GroupeTarifRefBean groupeTarif) {
		this.groupeTarif = groupeTarif;
		if (groupeTarif == null)
			setIdGroupeTarif(-1);
		else
			setIdGroupeTarif(Integer.parseInt(groupeTarif.getId()));
	}

	public static PeriodeGeneriqueCacheList getCacheList(Contexte contexte) throws TechnicalException, IncoherenceException {
		return (PeriodeGeneriqueCacheList) PoolCommunFactory.getInstance().getRefFacade().getCacheRefList(RefFacade.PERIODE_GENERIQUE_REF_KEY, contexte);
	}
}