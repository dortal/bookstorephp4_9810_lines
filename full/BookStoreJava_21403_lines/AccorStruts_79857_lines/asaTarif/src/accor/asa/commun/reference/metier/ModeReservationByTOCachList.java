package com.accor.asa.commun.reference.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 8 août 2007
 * Time: 16:21:12
 */
@SuppressWarnings("serial")
public class ModeReservationByTOCachList extends AbstractCachable implements CachableInterface {

	protected List<ParamRefBean> modes;
	
	public ModeReservationByTOCachList( List<ParamRefBean> modes) {
		this.modes = modes;
	}
	
	public List<ParamRefBean> getElements() {
		return modes;
	}
}
