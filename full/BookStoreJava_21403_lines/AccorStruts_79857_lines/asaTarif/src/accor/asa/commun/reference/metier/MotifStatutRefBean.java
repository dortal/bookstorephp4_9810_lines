package com.accor.asa.commun.reference.metier;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class MotifStatutRefBean extends RefBean {

	private List<String> idsGroupeOffre;
	private List<String> idsStatut;
	private boolean hasInfo = false;

	public MotifStatutRefBean () {
		idsGroupeOffre = new ArrayList<String>();
		idsStatut = new ArrayList<String>();
	}

	public List<String> getIdsGroupeOffre () {
		return idsGroupeOffre;
	}

	public List<String> getIdsStatut () {
		return idsStatut;
	}

	public boolean getHasInfo () {
		return hasInfo;
	}

	public void setIdsGroupeOffre (List<String> idsGroupeOffre) {
		this.idsGroupeOffre = idsGroupeOffre;
	}

	public void setIdsStatut (List<String> idsStatut) {
		this.idsStatut = idsStatut;
	}

	public void setHasInfo (boolean hasInfo) {
		this.hasInfo = hasInfo;
	}

	public void addGroupeOffre (String idGroupeOffre) {
		idsGroupeOffre.add(idGroupeOffre);
	}

	public void addStatut (String idStatut) {
		idsStatut.add(idStatut);
	}

}