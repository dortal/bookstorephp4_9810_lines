package com.accor.asa.commun.user.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;

@SuppressWarnings("serial")
public class ListSaleRegions extends AbstractCachable implements CachableInterface {

	protected List<SaleRegion> regions;
	
	public ListSaleRegions( final List<SaleRegion> regions ) {
		this.regions = regions;
	}
	
	public List<SaleRegion> getElements() {
		return regions;
	}
}
