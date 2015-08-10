package com.accor.asa.commun.reference.metier;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class MailingRefBean extends RefBean {

	private List<String> idsPays = null;
	private String libellesPays = null;

	public MailingRefBean () {
		idsPays = new ArrayList<String>();
	}

	public List<String> getIdsPays () {
		return idsPays;
	}

	public String getLibellesPays () {
		return libellesPays;
	}

	public void setIdsPays (List<String> idsPays) {
		this.idsPays = idsPays;
	}

	public void setLibellesPays (String libellesPays) {
		this.libellesPays = libellesPays;
	}

	public void addPays (String idPays) {
		this.idsPays.add(idPays);
	}

}