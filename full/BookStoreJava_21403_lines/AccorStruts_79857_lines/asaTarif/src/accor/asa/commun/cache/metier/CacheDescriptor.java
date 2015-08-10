package com.accor.asa.commun.cache.metier;

import java.io.Serializable;

public class CacheDescriptor implements Serializable {
 
    //Nom du Cache
    private String name;
    private String libelle;
    private String group;
    private String option;
		
    public CacheDescriptor( String name, String libelle, String group, String option ) {
    	this.name = name;
    	this.libelle = libelle;
    	this.group = group;
    	this.option = option;
    }
    
    public String getGroup() {
			return group;
		}
		public void setGroup(String group) {
			this.group = group;
		}
		public String getLibelle() {
			return libelle;
		}
		public void setLibelle(String libelle) {
			this.libelle = libelle;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		public String getOption() {
			return option;
		}

		public void setOption(String option) {
			this.option = option;
		}

		public String toString() {
			StringBuffer sb = new StringBuffer();
			sb.append( name ).append( " - " ).append( libelle )
				.append( " - " ).append( group ).append( " - ").append( option );
			return sb.toString();
		}

}
