package com.accor.asa.commun.metier.reference;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractListCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;

public class ListGroupesPrestations extends AbstractListCachable implements CachableInterface {

	public ListGroupesPrestations (List groupesPrestations) {
		this.elements = groupesPrestations;
	}

}
