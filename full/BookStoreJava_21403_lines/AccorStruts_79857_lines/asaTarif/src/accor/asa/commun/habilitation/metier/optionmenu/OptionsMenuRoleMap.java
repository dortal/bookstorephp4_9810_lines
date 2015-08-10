package com.accor.asa.commun.habilitation.metier.optionmenu;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * Title:        ASA Vente
 * Description:  Cette classe centralise les associations role -> Options du menu.
 * Copyright:    Copyright (c) 2001
 * Company:      Valtech
 * @author David Dreistadt
 * @version 1.0
 * <b>pattern<b> : Singleton
 */
@SuppressWarnings("serial")
public class OptionsMenuRoleMap implements Serializable{
    
	private Map<String,OptionMenuRole> optionsMenuRole = new HashMap<String,OptionMenuRole>();

    public void addOptionMenuRole( final String codeRole, final String codeOption ) {
    	optionsMenuRole.put( buildKey( codeRole, codeOption ), new OptionMenuRole( codeRole, codeOption ) );
    }
    
    public OptionMenuRole getOptionMenuRole( final String codeRole, final String codeOption ) {
    	return (OptionMenuRole) optionsMenuRole.get( buildKey( codeRole, codeOption ) );
    }
    
    public OptionMenuRole getOptionMenuRole( final String key ) {
    	return (OptionMenuRole) optionsMenuRole.get( key );
    }
    
    public Set<String> getKeys() {
    	return optionsMenuRole.keySet();
    }
    
    private String buildKey( final String codeRole, final String codeOption ) {
    	return codeRole + "_" + codeOption;
    }
}
