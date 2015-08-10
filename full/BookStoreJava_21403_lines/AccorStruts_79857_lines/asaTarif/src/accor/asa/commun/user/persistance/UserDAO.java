package com.accor.asa.commun.user.persistance;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.metier.AsaDate;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.metier.Coordinates;
import com.accor.asa.commun.persistance.*;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.user.metier.Territory;
import com.accor.asa.commun.user.metier.User;
import com.accor.asa.commun.user.metier.exception.UtilisateurInexistantException;
import org.apache.commons.lang.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

public class UserDAO extends DAO {

	private final static String PROC_GET_USER 			= "proc_get_user";
	private final static String PROC_INSERT_USER 		= "proc_insert_Utilisateur";
	private final static String PROC_UPDATE_USER 		= "proc_update_Utilisateur";
	private final static String PROC_DELETE_USER 		= "proc_delete_Utilisateur";
	private final static String PROC_GET_PASSWORD 		= "proc_get_password";
	private final static String PROC_UPDATE_PASSWORD	= "proc_update_password";
	private final static String PROC_GET_LOGINS			= "proc_get_logins";
	private final static String PROC_GET_TERRITORIES	= "proc_user_get_territoires";
	
	private static UserDAO _instance = new UserDAO();
	
	private UserDAO() {}
	
	public static UserDAO getInstance() {
		return _instance;
	}


	/***
	 * Methode de recuperation des informations lies a un utilisateur
	 * @param login
	 * @param contexte
	 * @return
	 * @throws TechnicalException
	 */
	public User getUser( final String login, final Contexte contexte ) 
			throws UtilisateurInexistantException, TechnicalException {
		User user = null;
		try {
			if( StringUtils.isNotBlank( login ) ) {
				
				SQLParamDescriptor param = new SQLParamDescriptor( login, false, Types.VARCHAR );
				
				user = (User) SQLCallExecuter.getInstance().executeSelectSingleObjetProc( 
						PROC_GET_USER, new SQLParamDescriptorSet( param ), 
						"UserDAO", "getUser", contexte.getContexteAppelDAO(), 
						new SQLResultSetReader() {
							public Object instanciateFromLine( ResultSet rs ) throws SQLException {
								User u = new User();
								u.setLogin( rs.getString( "login" ) );
								u.setHotelId( StringUtils.trimToEmpty( rs.getString( "code_hotel" ) ) );
								u.setLanguageCode( StringUtils.trimToEmpty( rs.getString( "code_langue" ) ) );
								u.setCountryCode( StringUtils.trimToEmpty( rs.getString( "code_pays" ) ) );
								u.setCity( StringUtils.trimToEmpty( rs.getString( "ville" ) ) );
								u.setSaleZoneId( StringUtils.trimToEmpty( rs.getString( "id_zone_vente" ) ) );
								u.setSaleRegionId( StringUtils.trimToEmpty( rs.getString( "id_region_vente" ) ) );
								u.setMarketId( StringUtils.trimToEmpty( rs.getString( "id_marche" ) ) );
								u.setLastName( StringUtils.trimToEmpty( rs.getString( "nom" ) ) );
								u.setFirstName( StringUtils.trimToEmpty( rs.getString( "prenom" ) ) );
								u.setProfileId( StringUtils.trimToEmpty( rs.getString( "id_profile" ) ) );
								
								Coordinates coord = u.getCoordinates();
								coord.setPhone( StringUtils.trimToEmpty( rs.getString( "telephone" ) ) );
								coord.setFax( StringUtils.trimToEmpty( rs.getString( "fax" ) ) );
								coord.setMail( StringUtils.trimToEmpty( rs.getString( "email" ) ) );
								u.setCoordinates( coord );
								
								u.setStatus( rs.getString( "status" ) );
								u.setComments( rs.getString( "commentaires" ) );
								u.setCivility( StringUtils.trimToEmpty( rs.getString( "civilite" ) ) );
								u.setSaleOfficeCode( rs.getInt( "code_bureau_vente" ) );
								u.setManagerCode( StringUtils.trimToEmpty( rs.getString( "code_utilisateur_chef" ) ) );
								u.setDefaultTerritoryId( StringUtils.trimToEmpty( rs.getString( "id_territoire_default" ) ) );
								u.setId( new Integer( rs.getInt( "id_intervenant" ) ) );
								u.setHotelName( StringUtils.trimToEmpty( rs.getString( "nom_hotel" ) ) );
								u.setDateCreation(new AsaDate(rs.getDate("create_date")));
								u.setDateMaj(new AsaDate(rs.getDate("update_date")));
								u.setDateSuppression(new AsaDate(rs.getDate("delete_date")));
								u.setAuthor(rs.getString("author"));
								u.setUpdater(rs.getString("updater"));
								return u;
							}
						}
				);
			}
			
			if( user == null ) 
				throw new UtilisateurInexistantException( login );
		
		} catch( SQLException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "UserDAO", "getUser", e.getMessage(), e );
			throw new TechnicalException( e );
		}
		return user;
	}
	
    /**
     * Methode d'insertion d'un nouvel utilisateur.
     * @param user
     * @param contexte
     * @return
     * @throws TechnicalException
     * @throws IncoherenceException
     */
    @SuppressWarnings("unchecked")
	public int insertUser( User user, final Contexte contexte ) 
    		throws TechnicalException, IncoherenceException {


    	String login;
    	String password;
    	int codeRetour = User.NO_CONSTRAINT_ERROR;

    	try {
    		int index = 0; //utiliser pour eviter les doublons sur les logins
    		login = buildLogin( user, index, contexte );
    		password = buildPassword( login );
    	  
    		SQLParamDescriptor param = new SQLParamDescriptor( login, false, Types.VARCHAR );
    	  
    		List<String> logins = (List<String>) SQLCallExecuter.getInstance().executeSelectProc( 
    				PROC_GET_LOGINS, new SQLParamDescriptorSet( param ), "UserDAO", "insertUser", 
    				contexte.getContexteAppelDAO(), new SQLResultSetReader() {
    		  		public Object instanciateFromLine( ResultSet rs ) throws SQLException {
    		  			return rs.getString( "login" ).toUpperCase();
    		  		}
    	  		  }
    		);
    	  
    		if( logins != null && logins.size()>0 ) {
    			
    			if( StringUtils.isNotBlank( user.getLogin() ) &&
    					logins.contains( login.toUpperCase() ) ) {
    				throw new IncoherenceException( User.getErrorMessage( User.DUPLICATE_USER_ERROR , contexte ) );
    			}
    			
    			while( logins.contains( login.toUpperCase() ) ) {
    				index++;
    				login = buildLogin( user, index, contexte );
    				password = buildPassword( login );
    			}
    		}
    	  
    		SQLParamDescriptor[] params = new SQLParamDescriptor[] {
  				new SQLParamDescriptor( null, true, Types.SMALLINT ),
    			new SQLParamDescriptor( login, false, Types.VARCHAR ),
    			new SQLParamDescriptor( password, false, Types.VARCHAR ),
    			new SQLParamDescriptor( user.getHotelId(), false, Types.CHAR ),
    			new SQLParamDescriptor( user.getLanguageCode(), false, Types.CHAR ),
    			new SQLParamDescriptor( user.getCountryCode(), false, Types.CHAR ),
    			new SQLParamDescriptor( user.getCity(), false, Types.VARCHAR ),
    			new SQLParamDescriptor( user.getSaleZoneId(), false, Types.CHAR ),
    			new SQLParamDescriptor( user.getSaleRegionId(), false, Types.CHAR ),
    			new SQLParamDescriptor( user.getMarketId(), false, Types.INTEGER ),
    			new SQLParamDescriptor( user.getLastName(), false, Types.CHAR ),
    			new SQLParamDescriptor( user.getFirstName(), false, Types.CHAR ),
    			new SQLParamDescriptor( user.getProfileId(), false, Types.CHAR ),
    			new SQLParamDescriptor( user.getPhone(), false, Types.CHAR ),
    			new SQLParamDescriptor( user.getFax(), false, Types.CHAR ),
    			new SQLParamDescriptor( User.STATUS_IN, false, Types.CHAR ),
    			new SQLParamDescriptor( user.getMail(), false, Types.CHAR ),
    			new SQLParamDescriptor( user.getComments(), false, Types.VARCHAR ),
    			new SQLParamDescriptor( user.getCivility(), false, Types.CHAR ),
    			new SQLParamDescriptor( user.getSaleOfficeCode(), false, Types.INTEGER ),
    			new SQLParamDescriptor( user.getManagerCode(), false, Types.VARCHAR ),
  				new SQLParamDescriptor( user.getAuthor(), false, Types.VARCHAR),
  				new SQLParamDescriptor( user.getUpdater(), false, Types.VARCHAR)
    		};
    	  
    		codeRetour = SQLCallExecuter.getInstance().executeUpdateWithReturnCode( 
    				PROC_INSERT_USER, new SQLParamDescriptorSet( params ), 
    				"UserDAO", "insertUser", contexte.getContexteAppelDAO() );
    	  
    		if( LogCommun.isFonctionelleDebug() ) {
    			LogCommun.traceFonctionnelle( contexte.getCodeUtilisateur(), "UTILISATEURS", "INS", login );
    		}
    		
    		user.setLogin(login);

    	} catch(SQLException e) {
    	  LogCommun.major( contexte.getCodeUtilisateur(), "UserDAO", "insertUser", e.getMessage(), e );
    	  throw new TechnicalException( e );
    	} 
    	return codeRetour;
    }


    /**
     * Methode de mise à jour d'un utilisateur
     * @param user
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public int updateUser( final User user, final Contexte contexte )
    		throws TechnicalException {
      
    	int codeRetour = User.NO_CONSTRAINT_ERROR;

    	try {
    		SQLParamDescriptor[] params = new SQLParamDescriptor[] {
      				new SQLParamDescriptor( null, true, Types.SMALLINT ),
        			new SQLParamDescriptor( user.getLogin(), false, Types.VARCHAR ),
        			new SQLParamDescriptor( user.getHotelId(), false, Types.CHAR ),
        			new SQLParamDescriptor( user.getLanguageCode(), false, Types.CHAR ),
        			new SQLParamDescriptor( user.getCountryCode(), false, Types.CHAR ),
        			new SQLParamDescriptor( user.getCity(), false, Types.VARCHAR ),
        			new SQLParamDescriptor( user.getSaleZoneId(), false, Types.CHAR ),
        			new SQLParamDescriptor( user.getSaleRegionId(), false, Types.CHAR ),
        			new SQLParamDescriptor( user.getMarketId(), false, Types.INTEGER ),
        			new SQLParamDescriptor( user.getLastName(), false, Types.CHAR ),
        			new SQLParamDescriptor( user.getFirstName(), false, Types.CHAR ),
        			new SQLParamDescriptor( user.getProfileId(), false, Types.CHAR ),
        			new SQLParamDescriptor( user.getPhone(), false, Types.CHAR ),
        			new SQLParamDescriptor( user.getFax(), false, Types.CHAR ),
        			new SQLParamDescriptor( user.getMail(), false, Types.CHAR ),
        			new SQLParamDescriptor( user.getComments(), false, Types.VARCHAR ),
        			new SQLParamDescriptor( user.getCivility(), false, Types.CHAR ),
        			new SQLParamDescriptor( user.getSaleOfficeCode(), false, Types.INTEGER ),
        			new SQLParamDescriptor( user.getManagerCode(), false, Types.VARCHAR ),
        			new SQLParamDescriptor( user.getUpdater(), false, Types.VARCHAR )
        	};
        	  
        	codeRetour = SQLCallExecuter.getInstance().executeUpdateWithReturnCode( 
        			PROC_UPDATE_USER, new SQLParamDescriptorSet( params ), 
        			"UserDAO", "updateUser", contexte.getContexteAppelDAO() );
    		
        	if( LogCommun.isFonctionelleDebug() ) {
        		LogCommun.traceFonctionnelle( contexte.getCodeUtilisateur(), "UTILISATEURS", "UPD", user.getLogin() );
        	}
    	} catch( SQLException e ) {
      	  LogCommun.major( contexte.getCodeUtilisateur(), "UserDAO", "updateUser", e.getMessage(), e );
    	  throw new TechnicalException( e );
    	}
    	return codeRetour;
    }

    /***
     * Methode de suppression logique d'un utilisateur
     * @param login
     * @param contexte
     * @throws TechnicalException
     * @throws SuppressionUtilisateurException
     */
    public int deleteUser( final String login, final Contexte contexte ) 
    		throws TechnicalException {

    	int codeRetour = User.NO_CONSTRAINT_ERROR;
    	
    	try {
    		if( StringUtils.isNotBlank( login ) ) {
    			SQLParamDescriptor[] params = new SQLParamDescriptor[] {
    					new SQLParamDescriptor( null, true, Types.SMALLINT ),
    					new SQLParamDescriptor( login, false, Types.VARCHAR )
    			};
    		
    			codeRetour = SQLCallExecuter.getInstance().executeUpdateWithReturnCode( 
    					PROC_DELETE_USER, new SQLParamDescriptorSet(params), 
    					"UserDAO", "deleteUser", contexte.getContexteAppelDAO() ); 
    		
	    		if( LogCommun.isFonctionelleDebug() )
	    			LogCommun.traceFonctionnelle( contexte.getCodeUtilisateur(), "UTILISATEURS", "DEL", login );
    		}

    	} catch( SQLException e ) {
    		LogCommun.major( contexte.getCodeUtilisateur(), "UserDAO", "deleteUser", e.getMessage(), e );
            throw new TechnicalException( e );
    	}
    	return codeRetour;
    }

    /***
     * Methode de changement du mot de passe de l'utilisateur donne
     * @param login
     * @param password
     * @param contexte
     * @throws TechnicalException
     */
    public void setPassword( final String login, final String password, final Contexte contexte ) 
    		throws TechnicalException {
        
    	try {
            if( StringUtils.isNotBlank( login ) && StringUtils.isNotBlank( password ) ) {
            	SQLParamDescriptor[] params = new SQLParamDescriptor[] {
            			new SQLParamDescriptor( login, false, Types.VARCHAR ),
            			new SQLParamDescriptor( password, false, Types.VARCHAR )
            	};
        	
            	SQLCallExecuter.getInstance().executeUpdate( PROC_UPDATE_PASSWORD, 
            			new SQLParamDescriptorSet( params ), "UserDAO", "setPassword", 
            			contexte.getContexteAppelDAO() );
            	
            	if( LogCommun.isFonctionelleDebug() )
            		LogCommun.traceFonctionnelle( login, "UTILISATEUR", "UPD", login );

            }
    	} catch( SQLException e ) {
    		LogCommun.major( login,"UserDAO", "setPassword" ,
                new StringBuffer("paramètres : ").append(login).append(" , ").append(password).toString(), e );
            throw  new TechnicalException(e);
    	}
    }

    /***
     * Renvoie le login (saisi par l'utilisateur ou genere par le systeme).
     * 	UC-H-01 : le login de l'utilisateur peut être saisi par le DBM. Dans le cas où celui-ci n'est pas saisi,
     * 	il est déterminé par le système, à partir de la règle suivante :
     * 	si un code est saisi, alors login = h + code hôtel + gm,
     * 	sinon, login = 1 premiere lettre du prénom + 5 premieres lettres du nom.
     * 	Lorsque le nom fait moins de 5 caractères, le login est complété par des 0 (zéros).
     * @param user
     * @param index
     * @return
     * @throws DonneesInsuffisantesException
     */
    private String buildLogin( final User user, final int index, final Contexte contexte ) 
    		throws IncoherenceException {
        StringBuffer login = new StringBuffer();
        
        if( StringUtils.isNotEmpty( user.getLogin() ) ) {
          return user.getLogin();
        } else {
        	if( StringUtils.isNotBlank( user.getHotelId() ) ) {
        		login.append( "h" ).append( user.getHotelId() ).append( "gm" );
        	} else { 
        		if( StringUtils.isNotBlank( user.getFirstName() ) 
        				&& StringUtils.isNotBlank( user.getLastName() ) ) {
        			
        			char caractere = user.getFirstName().charAt(0);
        			if( Character.isLetter( caractere ) ) {
        				login.append( caractere );
        			}

        			//Le login ne doit pas contenir d'espaces
        			String name = user.getLastName().trim();
        			int nameLength = name.length();
        			int indiceCaractere = 0;
        			caractere = ' ';
        			
          			while( ( login.length() < User.LOGIN_MAX_LENGTH ) 
          					&& ( indiceCaractere < nameLength ) ) {
          				caractere = name.charAt( indiceCaractere );
          				if( Character.isLetter( caractere ) ) {
          					login.append( caractere );
          				}
          				indiceCaractere++;
          			}
        		} else {
        	          throw new IncoherenceException( User.getErrorMessage( User.INSUFFICIENT_DATA_ERROR, contexte ) );
                }
        	}
        	
        	while( login.length() < User.LOGIN_MAX_LENGTH )
        		login.append( "0" );
        	
        	if( index > 0 )
        		login.append( index );
        }
        return login.toString();
      }

    /**
     * Renvoie le mot de passe par defaut de l'uilisateur.
     * 	En base un password est sur 12 caracteres
     * 	UC-H-01 : un mot de passe par défaut est déterminé par le système.
     * 	Celui-ci est égal au login suivi de ‘000’ (trois zéros).
     * @param login
     * @return
     */
    private String buildPassword( final String login ) {
    	StringBuffer password = new StringBuffer();
    	if( login.length() > User.PASSWORD_MAX_LENGTH-3 )
    		password.append( login.substring( 0, User.PASSWORD_MAX_LENGTH-3 ) );
    	else
    		password.append( login );
      
    	password.append( "000" );
    	return password.toString();
    }
    
    /***
     * Renvoie si le password actuel de l'utilisateur est toujours le passord par defaut
     * @param login
     * @param password
     * @return
     * @throws TechnicalException
     */
    public boolean isDefaultPassword( final String login, final String password ) {
    	if( StringUtils.isNotBlank( login ) && StringUtils.isNotBlank( password ) 
    			&& password.equals( buildPassword( login ) ) ) {
    		return true;
    	}
    	return false;
    }
    
    /***
     * Methode de verification du mot de passe de l utilisateur
     * @param login
     * @param password
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public boolean checkPassword( final String login, final String password, final Contexte contexte ) 
    		throws TechnicalException {
    	
    	try {
    		SQLParamDescriptor param = new SQLParamDescriptor( login, false, Types.VARCHAR );
    		
    		String realPwd = (String) SQLCallExecuter.getInstance().executeSelectSingleObjetProc( 
    				PROC_GET_PASSWORD, new SQLParamDescriptorSet( param ), 
    				"UserDAO", "checkPassword", contexte.getContexteAppelDAO(), 
    				new SQLResultSetReader() {
    					public Object instanciateFromLine( ResultSet rs ) throws SQLException {
    						return rs.getString( "password" );
    					}
    				}
    		);
    		
    		if( realPwd != null && StringUtils.equals( realPwd , password ) )
    			return true;
    		
    	} catch( SQLException e ) {
    		LogCommun.major( contexte.getCodeUtilisateur(), "UserDAO", "checkPassword", e.getMessage(), e );
    		throw new TechnicalException( e );
    	}
    	return false;
    }

    /**
     * Retourne la liste des territoires d'un utilisateur.
     * @param accountManager
     * @param contexte
     * @return List<Territory>
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	public List<Territory> getTerritories( final User accountManager, final Contexte contexte )
            throws TechnicalException {
    	List<Territory> lhs = null;
    	try {
    		SQLParamDescriptor[] params = new SQLParamDescriptor[] {
                    new SQLParamDescriptor( accountManager.getId(), false, Types.INTEGER ),
                    new SQLParamDescriptor( contexte.getCodeLangue(), false, Types.CHAR )
            };
    		
    		lhs = (List<Territory>) SQLCallExecuter.getInstance().executeSelectProc ( 
        		  PROC_GET_TERRITORIES, new SQLParamDescriptorSet( params ),
                  "UserDAO", "getTerritories", contexte.getContexteAppelDAO(), 
                  new SQLResultSetReader() {
        			  public Object instanciateFromLine(ResultSet rs) throws SQLException {
        				  Territory obj = new Territory();
        				  obj.setCode( StringUtils.trimToEmpty( rs.getString( "ID_TERRITOIRE_DEFAULT" ) ) );
        				  obj.setLibelle( StringUtils.trimToEmpty( rs.getString( "LIBELLE_TERRITOIRE" ) ) );
        				  obj.setTypeCode( rs.getInt("CODE_TYPE_TERRITOIRE") );
        				  obj.setManagerFirstName( StringUtils.trimToEmpty( rs.getString("nom" ) ) );
        				  return obj;
        			  } 
        		  } 
    		);
        } catch (SQLException e) {
          LogCommun.major(contexte.getCodeUtilisateur(), "UserDAO", "getTerritories", e.getMessage() );
          throw new TechnicalException(e);
        }
        return lhs;
    }

}
