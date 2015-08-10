package com.accor.asa.commun.metier;

import java.util.Map;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;

@SuppressWarnings("serial")
public class MapTranslateKey extends AbstractCachable implements CachableInterface {

	Map<String,String> translateKey;
	
	public MapTranslateKey( Map<String,String> translateKey, String codeLangue ) {
		this.translateKey = translateKey;
		this.params = new String[1];
		this.params[0] = codeLangue;
	}

	public String getCodeLangue () {
		return params[0];
	}

	public void setCodeLangue( String codeLangue ) {
		this.params[0] = codeLangue;
	}
	
	public Map<String,String> getTranslateKey() {
		return translateKey;
	}

	public void setTranslateKey(Map<String,String> translateKey) {
		this.translateKey = translateKey;
	}
	
	public Map<String,String> getElements() {
		return translateKey;
	}
	
}
