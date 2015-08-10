package com.accor.asa.commun.reference.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 9 août 2007
 * Time: 08:40:07
 */
@SuppressWarnings("serial")
public class ModePaiementCachList extends AbstractCachable implements CachableInterface {

	protected List<ModePaiementRefBean> modes;
	
	public ModePaiementCachList( List<ModePaiementRefBean> modes, String codeLangue ) {
		this.modes = modes;
		this.params = new String[1];
		this.params[0] = codeLangue;
	}
	
	public List<ModePaiementRefBean> getElements() {
		return modes;
	}
}
