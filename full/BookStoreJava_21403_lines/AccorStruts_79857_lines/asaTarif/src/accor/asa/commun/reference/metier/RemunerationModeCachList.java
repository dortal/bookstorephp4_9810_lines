package com.accor.asa.commun.reference.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 9 août 2007
 * Time: 16:46:40
 */
@SuppressWarnings("serial")
public class RemunerationModeCachList extends AbstractCachable implements CachableInterface {

	protected List<RemunerationModeRefBean> modes;
	
	public RemunerationModeCachList( List<RemunerationModeRefBean> modes, String codeLangue ) {
		this.modes = modes;
		this.params = new String[1];
		this.params[0] = codeLangue;
	}
	
	public List<RemunerationModeRefBean> getElements() {
		return modes;
	}
}
