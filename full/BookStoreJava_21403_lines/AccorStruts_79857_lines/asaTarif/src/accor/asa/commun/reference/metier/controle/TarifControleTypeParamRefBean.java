package com.accor.asa.commun.reference.metier.controle;

import com.accor.asa.commun.reference.metier.RefBean;

@SuppressWarnings("serial")
public class TarifControleTypeParamRefBean extends RefBean{

    public static final int TYPE_PARAM_NONE     =0;
    public static final int TYPE_PARAM_DECOTE   =1;
    public static final int TYPE_PARAM_RATELEVEL=2;

    private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
