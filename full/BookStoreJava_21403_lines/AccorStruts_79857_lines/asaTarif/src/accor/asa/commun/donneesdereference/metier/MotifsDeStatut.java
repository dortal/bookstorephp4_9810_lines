package com.accor.asa.commun.donneesdereference.metier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.metier.VenteGroupeOffre;

@SuppressWarnings("serial")
public class MotifsDeStatut extends DonneeReference implements Serializable {
	private List<String> liste_offres;
	private List<String> liste_statuts;
	private String m_listesModifiee;
	private boolean hasInfo = false;

	public static final List<MotifsDeStatut> getListeFiltreeParGroupeOffre(List<MotifsDeStatut> listeGlobale, VenteGroupeOffre groupeOffre) {
		List<MotifsDeStatut> listeFiltree = new ArrayList<MotifsDeStatut>();
		for (Iterator<MotifsDeStatut> iter = listeGlobale.iterator(); iter.hasNext();) {
			MotifsDeStatut motif = iter.next();
			if (motif.getListe_offres().contains(groupeOffre.getLabel()))
				listeFiltree.add(motif);
		}
		return listeFiltree;
	}

	public DonneeReference getInstance(HttpServletRequest req) {
		MotifsDeStatut result = new MotifsDeStatut();
		result.setCode(req.getParameter("code"));
		result.setLibelle(req.getParameter("libelle"));
		result.setListe_offres(getListParameter(req, "liste_groupe_offre", liste_offres));
		result.setListe_statuts(getListParameter(req, "liste_liste_statut", liste_statuts));
		result.setListesModifiee(req.getParameter("listeModifiee"));
		return result;
	}

	private List<String> getListParameter(HttpServletRequest req, String parameter, List<String> selection) {
		StringTokenizer liste = new StringTokenizer(req.getParameter(parameter), ";");
		selection = new ArrayList<String>(liste.countTokens());
		while (liste.hasMoreElements())
			selection.add((String) liste.nextElement());
		LogCommun.debug("MotifDeStatut", "getListeParameter", selection.toString());
		return selection;
	}

	public List<String> getListe_offres() {
		return liste_offres;
	}

	public List<String> getListe_statuts() {
		return liste_statuts;
	}

	public boolean getHasInfo () {
		return hasInfo;
	}

	public void setListe_offres(List<String> list) {
		liste_offres = list;
	}

	public void setListe_statuts(List<String> list) {
		liste_statuts = list;
	}

	public void setHasInfo (boolean hasInfo) {
		this.hasInfo = hasInfo;
	}

	public String getListesModifiee() {
		return m_listesModifiee;
	}

	public void setListesModifiee(String string) {
		m_listesModifiee = string;
	}

}