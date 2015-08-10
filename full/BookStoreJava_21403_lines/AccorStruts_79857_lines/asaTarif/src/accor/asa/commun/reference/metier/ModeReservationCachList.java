package com.accor.asa.commun.reference.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;

@SuppressWarnings("serial")
public class ModeReservationCachList extends AbstractCachable implements CachableInterface {

	protected List<ModeReservationRefBean> modes;
	
	public ModeReservationCachList( List<ModeReservationRefBean> modes, String codeLangue ) {
		this.modes = modes;
		this.params = new String[1];
		this.params[0] = codeLangue;
	}
	
	public List<ModeReservationRefBean> getElements() {
		return modes;
	}
}
