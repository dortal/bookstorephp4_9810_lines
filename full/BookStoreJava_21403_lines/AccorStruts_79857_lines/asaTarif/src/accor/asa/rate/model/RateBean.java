package com.accor.asa.rate.model;

public abstract class RateBean implements Rate{

	    protected String    key;
	    protected Long idGrille;

	    public String getKey() {
	        return key;
	    }

	    public void setKey(String key) {
	        this.key = key;
	    }

	    public Long getIdGrille() {
	        return idGrille;
	    }

	    public void setIdGrille(Long idGrille) {
	        this.idGrille = idGrille;
	    }
	    
	    public abstract String generateBeanKey();	
}
