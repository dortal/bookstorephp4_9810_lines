package com.accor.asa.commun.habilitation.metier;

public class AsaModule {

	public static final int DEFAULT_CODE 		= 0;
	public static final int CODE_APP_VENTE 		= 1;
	public static final int CODE_APP_TARIF 		= 2;
	public static final int CODE_APP_ADMIN 		= 3;
	public static final int CODE_APP_TRANSLATE 	= 4;
	public static final int CODE_APP_CRON 		= 5;
	public static final int CODE_APP_CONSOLE 	= 6;

	public static final String DEFAULT_NAME 		= "ASA";
	public static final String NAME_APP_VENTE 		= "VENTE";
	public static final String NAME_APP_TARIF 		= "TARIF";
	public static final String NAME_APP_ADMIN 		= "ADMIN";
	public static final String NAME_APP_TRANSLATE 	= "TRANSLATE";
	public static final String NAME_APP_CRON 		= "CRON";
	public static final String NAME_APP_CONSOLE 	= "CONSOLE";

	public static final AsaModule DEFAULT_APP 	= new AsaModule( DEFAULT_CODE, DEFAULT_NAME ); 
	public static final AsaModule APP_VENTE 	= new AsaModule( CODE_APP_VENTE, NAME_APP_VENTE ); 
	public static final AsaModule APP_TARIF 	= new AsaModule( CODE_APP_TARIF, NAME_APP_TARIF ); 
	public static final AsaModule APP_ADMIN 	= new AsaModule( CODE_APP_ADMIN, NAME_APP_ADMIN ); 
	public static final AsaModule APP_TRANSLATE	= new AsaModule( CODE_APP_TRANSLATE, NAME_APP_TRANSLATE ); 
	public static final AsaModule APP_CRON 		= new AsaModule( CODE_APP_CRON, NAME_APP_CRON ); 
	public static final AsaModule APP_CONSOLE	= new AsaModule( CODE_APP_CONSOLE, NAME_APP_CONSOLE ); 
	
	int		code;
	String	name;
	
	public AsaModule( final int code, final String name ) {
		this.code = code;
		this.name = name;
	}
	
	public static AsaModule getModule( final int code ) {
		switch( code ) {
			case CODE_APP_VENTE 	: return APP_VENTE;
			case CODE_APP_TARIF 	: return APP_TARIF;
			case CODE_APP_ADMIN 	: return APP_ADMIN;
			case CODE_APP_TRANSLATE	: return APP_TRANSLATE;
			case CODE_APP_CRON 		: return APP_CRON;
			case CODE_APP_CONSOLE 	: return APP_CONSOLE;
			default					: return DEFAULT_APP;
		}
	}
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append( "[code=" ).append( code ).append( "]," );
		sb.append( "[name=" ).append( name ).append( "]," );
		return sb.toString();
	}

	public boolean equals( final AsaModule module ) {
		if( module == null )
			return false;
		
		return this.code == module.getCode();
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
