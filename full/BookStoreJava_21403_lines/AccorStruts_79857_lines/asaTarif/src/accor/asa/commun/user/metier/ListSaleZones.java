package com.accor.asa.commun.user.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;

@SuppressWarnings("serial")
public class ListSaleZones extends AbstractCachable implements
		CachableInterface {

	protected List<SaleZone> zones;
	
	public ListSaleZones( final List<SaleZone> zones ) {
		this.zones = zones;
	}

	public List<SaleZone> getElements() {
		return zones;
	}
}
