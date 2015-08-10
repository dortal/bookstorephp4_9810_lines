package com.accor.asa.commun.reference.metier.controle;

import java.util.List;

import com.accor.asa.commun.reference.metier.GroupeTarifRefBean;
import com.accor.asa.commun.reference.metier.RefBean;

@SuppressWarnings("serial")
public class TarifControleInstanceRefBean extends RefBean{
	
	private int idControle;
	private int idGroupeTarif;
	private boolean bloquant;
	
	private List<String> idsPeriodesValidite;
	private List<String> idsFamTarif;
	private List<String> codesAsaCategory;
	private List<String> paramValues;
	private List<String> codesPaysExclus;
	private List<String> codesHotelsExclus;
	
	
	
	private TarifControleRefBean controle;
	private GroupeTarifRefBean groupeTraif;
	public int getIdControle() {
		return idControle;
	}
	public void setIdControle(int idControle) {
		this.idControle = idControle;
	}
	public int getIdGroupeTarif() {
		return idGroupeTarif;
	}
	public void setIdGroupeTarif(int idGroupeTarif) {
		this.idGroupeTarif = idGroupeTarif;
	}
	public boolean isBloquant() {
		return bloquant;
	}
	public void setBloquant(boolean bloquant) {
		this.bloquant = bloquant;
	}
	public TarifControleRefBean getControle() {
		return controle;
	}
	public void setControle(TarifControleRefBean controle) {
		this.controle = controle;
		if(controle==null)
			setIdControle(-1);
		else
			setIdControle(Integer.parseInt(controle.getCode()));
	}
	public GroupeTarifRefBean getGroupeTraif() {
		return groupeTraif;
	}
	public void setGroupeTraif(GroupeTarifRefBean groupeTraif) {
		this.groupeTraif = groupeTraif;
		if(groupeTraif==null)
			setIdGroupeTarif(-1);
		else
			setIdGroupeTarif(Integer.parseInt(groupeTraif.getCode()));
	}
	public List<String> getIdsPeriodesValidite() {
		return idsPeriodesValidite;
	}
	public void setIdsPeriodesValidite(List<String> idsPeriodesValidite) {
		this.idsPeriodesValidite = idsPeriodesValidite;
	}
	public List<String> getIdsFamTarif() {
		return idsFamTarif;
	}
	public void setIdsFamTarif(List<String> idsFamTarif) {
		this.idsFamTarif = idsFamTarif;
	}
	public List<String> getCodesAsaCategory() {
		return codesAsaCategory;
	}
	public void setCodesAsaCategory(List<String> codesAsaCategory) {
		this.codesAsaCategory = codesAsaCategory;
	}
	public List<String> getParamValues() {
		return paramValues;
	}
	public void setParamValues(List<String> paramsVlaues) {
		this.paramValues = paramsVlaues;
	}
	public List<String> getCodesPaysExclus() {
		return codesPaysExclus;
	}
	public void setCodesPaysExclus(List<String> codesPaysExclus) {
		this.codesPaysExclus = codesPaysExclus;
	}
	public List<String> getCodesHotelsExclus() {
		return codesHotelsExclus;
	}
	public void setCodesHotelsExclus(List<String> codesHotelsExclus) {
		this.codesHotelsExclus = codesHotelsExclus;
	}
	

}
