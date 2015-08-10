package com.accor.asa.commun.reference.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 9 août 2007
 * Time: 16:48:08
 */
@SuppressWarnings("serial")
public class RemunerationTypeCachList extends AbstractCachable implements CachableInterface {

	protected List<RemunerationTypeRefBean> types;
	
	public RemunerationTypeCachList( List<RemunerationTypeRefBean> types, String codeLangue ) {
		this.types = types;
		this.params = new String[1];
		this.params[0] = codeLangue;
	}
	
	public List<RemunerationTypeRefBean> getElements() {
		return types;
	}
}
