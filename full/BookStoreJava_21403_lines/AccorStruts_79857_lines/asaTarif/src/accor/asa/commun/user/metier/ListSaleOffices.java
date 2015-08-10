package com.accor.asa.commun.user.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;


@SuppressWarnings("serial")
public class ListSaleOffices extends AbstractCachable implements CachableInterface {

	protected List<SaleOffice> offices;
	
	public ListSaleOffices( final List<SaleOffice> offices, final String codeLangue ) {
		this.offices = offices;
		this.params = new String[1];
		this.params[0] = codeLangue;
	}
	
	public List<SaleOffice> getElements() {
		return offices;
	}
}
