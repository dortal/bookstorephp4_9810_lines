package com.accor.asa.commun.habilitation.metier.optionmenu;

import java.util.List;
import java.util.Map;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;

/***
 * Map regroupant les options du Menu par groupe d'option pour chaque module
 * @author FCHIVAUX
 *
 */
@SuppressWarnings("serial")
public class GroupeOptionMenuMap extends AbstractCachable implements CachableInterface {

	protected Map<GroupeOptionMenu,List<OptionMenu>> groups;
	
	public GroupeOptionMenuMap( final Map<GroupeOptionMenu,List<OptionMenu>> groups, 
			final String codeApp ) {
		this.groups = groups;
		this.params = new String[1];
		this.params[0] = codeApp;
	}

    public Map<GroupeOptionMenu,List<OptionMenu>> getElements() {
    	return groups;
    }
}	
