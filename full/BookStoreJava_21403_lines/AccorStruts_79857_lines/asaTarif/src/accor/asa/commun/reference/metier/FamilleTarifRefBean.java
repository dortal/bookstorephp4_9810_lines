package com.accor.asa.commun.reference.metier;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.reference.process.RefFacade;

@SuppressWarnings("serial")
public class FamilleTarifRefBean extends RefBean{
	
    public static final int ID_FAMILLE_PUBLISHED = 1;
    public static final int ID_FAMILLE_COMPANY   = 3;
    public static final int ID_FAMILLE_IT        = 5;
    public static final int ID_FAMILLE_GROUP     = 6;
    public static final int ID_FAMILLE_LEISURE   = 7;
    public static final int ID_FAMILLE_OTHER     = 99;

    private String name;
	private int idGroupeTarif;
	private String codeTypeTarifTars;
	private boolean contratDistrib;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIdGroupeTarif() {
		return idGroupeTarif;
	}
	
	public String getCodeTypeTarifTars() {
		return codeTypeTarifTars;
	}
	public void setCodeTypeTarifTars(String codeTypeTarifTars) {
		this.codeTypeTarifTars = codeTypeTarifTars;
	}
	public boolean isContratDistrib() {
		return contratDistrib;
	}
	public void setContratDistrib(boolean contratDistrib) {
		this.contratDistrib = contratDistrib;
	}
	
	public void setIdGroupeTarif(int idGroupeTarif) {
		this.idGroupeTarif = idGroupeTarif;
	}
	
	
	
	private GroupeTarifRefBean groupeTarif;
	
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

	public static FamilleTarifCacheList getCacheList(Contexte contexte) throws TechnicalException, IncoherenceException
	{
		return (FamilleTarifCacheList)PoolCommunFactory.getInstance().getRefFacade().getCacheRefList(RefFacade.FAMILLE_TARIF_KEY, contexte);
	}
	

}
