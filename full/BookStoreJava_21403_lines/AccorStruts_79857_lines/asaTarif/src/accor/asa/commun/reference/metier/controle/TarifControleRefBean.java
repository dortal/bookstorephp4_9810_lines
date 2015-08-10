package com.accor.asa.commun.reference.metier.controle;

import com.accor.asa.commun.reference.metier.RefBean;

@SuppressWarnings("serial")
public class TarifControleRefBean extends RefBean{

	private String nameProcedure;
	private int idTypeParam;
	private TarifControleTypeParamRefBean typeParam;
	private String libelleMessage;

	public int getIdTypeParam() {
		return idTypeParam;
	}

	public void setIdTypeParam(int idTypeParam) {
		this.idTypeParam = idTypeParam;
	}

	public TarifControleTypeParamRefBean getTypeParam() {
		return typeParam;
	}

	public void setTypeParam(TarifControleTypeParamRefBean typeParam) {
		this.typeParam = typeParam;
		if(typeParam==null)
			setIdTypeParam(0);
		else
			setIdTypeParam(Integer.parseInt(typeParam.getCode()));
	}

	public String getLibelleMessage() {
		return libelleMessage;
	}

	public void setLibelleMessage(String libelleMessage) {
		this.libelleMessage = libelleMessage;
	}

	public String getNameProcedure() {
		return nameProcedure;
	}

	public void setNameProcedure(String nameProcedure) {
		this.nameProcedure = nameProcedure;
	}

	
	
	
	
}
