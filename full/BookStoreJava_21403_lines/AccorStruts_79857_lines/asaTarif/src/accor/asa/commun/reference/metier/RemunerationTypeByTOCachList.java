package com.accor.asa.commun.reference.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 9 août 2007
 * Time: 16:48:21
 */
@SuppressWarnings("serial")
public class RemunerationTypeByTOCachList  extends AbstractCachable implements CachableInterface {

	protected List<ParamRefBean> types;
	
	public RemunerationTypeByTOCachList( List<ParamRefBean> types ) {
		this.types = types;
	}
	
	public List<ParamRefBean> getElements() {
		return types;
	}
}
