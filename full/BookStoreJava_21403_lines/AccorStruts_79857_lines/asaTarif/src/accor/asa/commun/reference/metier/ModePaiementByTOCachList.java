package com.accor.asa.commun.reference.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 9 août 2007
 * Time: 08:41:05
 */
@SuppressWarnings("serial")
public class ModePaiementByTOCachList extends AbstractCachable implements CachableInterface {

	protected List<ParamRefBean> modes;
	
	public ModePaiementByTOCachList( List<ParamRefBean> modes) {
		this.modes = modes;
	}
	
	public List<ParamRefBean> getElements() {
		return modes;
	}
}
