package com.accor.asa.commun.metier.seminaire;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractListCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 18 avr. 2007
 * Time: 12:52:41
 */
public class ListTypesSeminaires extends AbstractListCachable implements CachableInterface {

	public ListTypesSeminaires( List seminaires, String codeLangue ) {
		this.elements = seminaires;
		this.params = new String[1];
		this.params[0] = codeLangue;
	}

	public String getCodeLangue() {
		return this.params[0];
	}

	public void setCodeLangue(String codeLangue) {
		this.params[0] = codeLangue;
	}
}
