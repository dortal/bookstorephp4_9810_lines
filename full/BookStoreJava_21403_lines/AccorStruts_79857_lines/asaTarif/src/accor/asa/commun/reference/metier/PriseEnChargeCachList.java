package com.accor.asa.commun.reference.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 14 août 2007
 * Time: 10:21:49
 */
@SuppressWarnings("serial")
public class PriseEnChargeCachList extends AbstractCachable implements CachableInterface {

	protected List<PriseEnChargeRefBean> cares;
	
	public PriseEnChargeCachList( List<PriseEnChargeRefBean> cares, String codeLangue ) {
		this.cares = cares;
		this.params = new String[1];
		this.params[0] = codeLangue;
	}
	
	public List<PriseEnChargeRefBean> getElements() {
		return cares;
	}
}
