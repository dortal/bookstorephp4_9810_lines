package com.accor.asa.commun.reference.metier;

import java.util.ArrayList;
import java.util.List;

import com.accor.asa.commun.metier.Element;

@SuppressWarnings("serial")
public class CdvRefBean extends RefBean {

	private String libelleCourt = null;
	private List<Element> groupesOffre = null;
	private String codeTars = null;

	public CdvRefBean () {
		groupesOffre = new ArrayList<Element>();
	}

	public String getLibelleCourt () {
		return libelleCourt;
	}

	public List<Element> getGroupesOffre () {
		return groupesOffre;
	}

	public void setLibelleCourt (String libelleCourt) {
		this.libelleCourt = libelleCourt;
	}

	public void setGroupesOffre (List<Element> groupesOffre) {
		this.groupesOffre = groupesOffre;
	}

	public void addGroupeOffre (Element groupeOffre) {
		this.groupesOffre.add(groupeOffre);
	}

	public String getCodeTars() {
		return codeTars;
	}

	public void setCodeTars(String codeTars) {
		this.codeTars = codeTars;
	}

}