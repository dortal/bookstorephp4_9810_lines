package com.accor.asa.commun.habilitation.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;

@SuppressWarnings("serial")
public class ListRoles extends AbstractCachable implements CachableInterface {

	protected List<Role> roles;
	
	public ListRoles( final List<Role> roles, final String module ) {
		this.roles = roles;
		this.params = new String[1];
		this.params[0] = module;
	}
	
	public List<Role> getElements() {
		return roles;
	}
}
