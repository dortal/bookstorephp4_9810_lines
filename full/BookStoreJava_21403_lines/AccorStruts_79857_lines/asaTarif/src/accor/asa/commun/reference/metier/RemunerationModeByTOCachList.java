package com.accor.asa.commun.reference.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 9 août 2007
 * Time: 16:46:57
 */
@SuppressWarnings("serial")
public class RemunerationModeByTOCachList  extends AbstractCachable implements CachableInterface {

	protected List<ParamRefBean> modes;
	
	public RemunerationModeByTOCachList( List<ParamRefBean> modes) {
		this.modes = modes;
	}
	
	public List<ParamRefBean> getElements() {
		return modes;
	}
}
