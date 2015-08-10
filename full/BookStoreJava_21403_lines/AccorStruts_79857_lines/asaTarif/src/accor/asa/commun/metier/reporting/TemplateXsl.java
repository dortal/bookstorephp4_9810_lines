package com.accor.asa.commun.metier.reporting;

import javax.xml.transform.Templates;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;

@SuppressWarnings("serial")
public class TemplateXsl extends AbstractCachable implements CachableInterface {

	private Templates template;

	public TemplateXsl() {
		this.params = new String[2];
	}

	public String getName() {
		return this.params[1];
	}

	public void setName(String name) {
		this.params[1] = name;
	}

	public void setTemplate(Templates template) {
		this.template = template;
	}

	public String getCodeLangue() {
		return this.params[0];
	}

	public void setCodeLangue(String codeLangue) {
		this.params[0] = codeLangue;
	}

	public Templates getElements () {
		return this.template;
	}

}