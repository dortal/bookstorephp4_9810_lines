package com.accor.asa.commun.donneesdereference.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 24 mai 2007
 * Time: 15:57:14
 */
@SuppressWarnings("serial")
public class ListMotifsStatut  extends AbstractCachable implements CachableInterface {

	private List<MotifsDeStatut> motifs = null;

	/**
	 * Constructeur
	 * @param listMotifsStatut
	 */
	public ListMotifsStatut( List<MotifsDeStatut> listMotifsStatut, String codeLangue ) {
		this.motifs = listMotifsStatut;
		this.params = new String[1];
		this.params[0] = codeLangue;
	}

	public List<MotifsDeStatut> getElements () {
		return this.motifs;
	}
}