package com.accor.asa.commun.user.metier;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.metier.Element;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.user.process.UserFacade;
import com.accor.asa.commun.utiles.FilesPropertiesCache;
import com.accor.asa.commun.utiles.Proprietes;
import com.accor.commun.internationalisation.Translator;
import com.accor.commun.internationalisation.TranslatorFactory;

@SuppressWarnings("serial")
public class Territory extends Element {

	public static final int NO_CONSTRAINT_ERROR						= 0;
	public static final int ACCOUNT_CONSTRAINT_ERROR				= 1;
//	public static final int USER_CONSTRAINT_ERROR					= 2;
	public static final int DEFAULT_TERRITORY_CONSTRAINT_ERROR		= 3;
	public static final int DUPLICATE_TERRITORY_CONSTRAINT_ERROR	= 4;
	
	protected Integer 			typeCode;
	protected String 			typeLabel;
	protected Integer 			managerID;
	protected String			managerCivility;
	protected String			managerFirstName;
	protected String			managerLastName;
	protected boolean			byDefault;
	protected boolean			deleted;
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append( super.toString() );
		sb.append( "[typeCode= " ).append( typeCode ).append( "]," );
		sb.append( "[typeLabel= " ).append( typeLabel ).append( "]," );
		sb.append( "[managerID= " ).append( managerID ).append( "]," );
		sb.append( "[managerCivility= " ).append( managerCivility ).append( "]," );
		sb.append( "[managerFirstName= " ).append( managerFirstName ).append( "]," );
		sb.append( "[managerLastName= " ).append( managerLastName ).append( "]," );
		sb.append( "[byDefault= " ).append( byDefault ).append( "]," );
		sb.append( "[deleted= " ).append( deleted ).append( "]," );
		return sb.toString();
		
	}
	
	public boolean isByDefault() {
		return byDefault;
	}
	public void setByDefault(boolean byDefault) {
		this.byDefault = byDefault;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getManagerFirstName() {
		return managerFirstName;
	}

	public void setManagerFirstName(String managerFirstName) {
		this.managerFirstName = managerFirstName;
	}

	public Integer getManagerID() {
		return managerID;
	}

	public void setManagerID(Integer managerID) {
		this.managerID = managerID;
	}

	public void setManagerID(int managerID) {
		this.managerID = new Integer(managerID);
	}

	public String getManagerLastName() {
		return managerLastName;
	}

	public void setManagerLastName(String managerLastName) {
		this.managerLastName = managerLastName;
	}

	public Integer getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(Integer typeCode) {
		this.typeCode = typeCode;
	}

	public void setTypeCode(int typeCode) {
		this.typeCode = new Integer( typeCode );
	}

	public String getTypeLabel() {
		return typeLabel;
	}

	public void setTypeLabel(String typeLabel) {
		this.typeLabel = typeLabel;
	}
	
	public String getManagerName() {
		return managerLastName + " " + managerFirstName;
	}
	
	/***
	 * Methode de verification de l'absence de l objet a inserer dans la liste
	 * @param listItems
	 * @return
	 */
	public boolean notExist( final List<Territory> territories ) {

		if( territories == null )
			return true;
		
		for( int i=0, size=territories.size(); i<size; i++ ) {
			if( StringUtils.equalsIgnoreCase( territories.get( i ).getCode(), code ) )
				return false;
		}
		
		return true;
	}

	/***
	 * Methode de verification de l'unicite de l'objet dans la liste
	 * @param listItems
	 * @return
	 */
/*		
	public boolean isUnique( final ListTerritories territories ) {

		if( territories == null )
			return true;
		
		Object o;
		
		for( int i=0, size=territories.size(); i<size; i++ ) {
			o = territories.get( i );
			if( o instanceof Territory ) {
				if( StringUtils.equalsIgnoreCase( ( (Territory) o ).getLibelle(), libelle )
					&& ( ! StringUtils.equalsIgnoreCase( ( (Territory) o ).getCode(), code ) ) )
					return false;
			}
		}
		return true;
	}
*/		
	
	/***
	 * Methode de récupération du message d'erreur Sybase associé au codeErreur
	 * @param codeError
	 * @param contexte
	 * @return
	 * @throws TechnicalException
	 * @throws IncoherenceException
	 */
	public static String getErrorMessage( final int codeError, final String codeTerritory, final Contexte contexte ) 
			throws TechnicalException, IncoherenceException {
		
		StringBuffer message = new StringBuffer();
		Translator trans = TranslatorFactory.getTranslator( contexte.getCodeLangue(), true );
		UserFacade uFacade = PoolCommunFactory.getInstance().getUserFacade();
//		User user;
		
		int maxErrors = Integer.parseInt( FilesPropertiesCache.getInstance().getValue(
				Proprietes.PROPERTIES_FILE_NAME, Proprietes.NB_MAX_ERRORS_IN_USERS ) );		

		switch( codeError ) {
			
			case ACCOUNT_CONSTRAINT_ERROR :
				message.append( trans.getLibelle( "ADMIN_USERS_TERRITORY_ACCOUNT_ERROR" ) );
				List<Element> errors = uFacade.getAccountsForTerritory( codeTerritory, contexte );
				if( errors != null ) {
					Element e;
					
					boolean moreAccounts = false;
					int size = errors.size();
					if( size > maxErrors ) {
						size = maxErrors;
						moreAccounts = true;
					}
					
					for( int i=0; i<size; i++ ) {
						e = errors.get(i);
						message.append( "\\n\\t" ).append( e.getLibelle() )
						   		.append( " (" ).append( e.getCode() ).append( ")" );
					}
					
					if( moreAccounts )
						message.append( "\\n\\t...." );
				}
				break;
			
//			case USER_CONSTRAINT_ERROR : 
//				message.append( trans.getLibelle( "ADMIN_USERS_TERRITORY_USER_ERROR" ) );
//				user = uFacade.getUserForTerritory( codeTerritory, contexte );
//				message.append( "\\n\\t" ).append( user.getLabel() )
//						.append( " (" ).append( user.getLogin() ).append( ")" );
//				break;
				
			case DEFAULT_TERRITORY_CONSTRAINT_ERROR :
				message.append( trans.getLibelle( "ADMIN_USERS_TERRITORY_DEFAULT_TERRITORY_ERROR" ) );
				break;

				
			case DUPLICATE_TERRITORY_CONSTRAINT_ERROR : 
				message.append( trans.getLibelle( "ADMIN_USERS_TERRITORY_DUPLICATE_TERRITORY" ) );
				break;
		}
		return message.toString();
	}

	public String getManagerCivility() {
		return managerCivility;
	}

	public void setManagerCivility(String managerCivility) {
		this.managerCivility = managerCivility;
	}
}
