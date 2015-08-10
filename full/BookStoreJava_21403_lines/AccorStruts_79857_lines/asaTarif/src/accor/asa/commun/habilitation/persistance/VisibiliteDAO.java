package com.accor.asa.commun.habilitation.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CacheManager;
import com.accor.asa.commun.habilitation.metier.AsaModule;
import com.accor.asa.commun.habilitation.metier.acces.Acces;
import com.accor.asa.commun.habilitation.metier.visibilite.AxeVisibilite;
import com.accor.asa.commun.habilitation.metier.visibilite.ListAxesVisibilites;
import com.accor.asa.commun.habilitation.metier.visibilite.ListTypeVisibilites;
import com.accor.asa.commun.habilitation.metier.visibilite.TypeVisibilite;
import com.accor.asa.commun.habilitation.metier.visibilite.Visibilite;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.DAO;
import com.accor.asa.commun.persistance.SQLCallExecuter;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.vente.commun.habilitation.metier.MapDroit;

public class VisibiliteDAO extends DAO {

    private static VisibiliteDAO _instance = null;
    
    private VisibiliteDAO() {}
    
    public static VisibiliteDAO getInstance() {
    	if( _instance == null )
    		_instance = new VisibiliteDAO();
    	return _instance;
    }
	
	/***
     * récupère le droit possible pour un trio (role, axe, groupe ecran) ( table ACCES )
     * <br> voir table habil_acces
     * @param codeRole
     * @param codeAxe
     * @param codeGroupeEcran
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public Acces getAcces( final String codeRole, final String codeAxe, 
    		final String codeGroupeEcran, final Contexte contexte ) throws TechnicalException {
      
    	MapDroit droits = (MapDroit) 
    		CacheManager.getInstance().getObjectInCache( MapDroit.class ); 
    	
    	if( droits == null ) {
    		Map<String,Acces> m_droitParAxeEtGroupe = new HashMap<String,Acces>();
    		List<Acces> access = HabilitationDAO.getInstance().getAllAcces(contexte);
    		int nbAccess = ( access == null )?0:access.size();
    		Acces acs = null;
    		String clef = null;
    		for( int i=0; i<nbAccess; i++ ) {
    			acs = access.get(i);
    			clef = buildClefDroit( acs.getCodeRole(), acs.getCodeAxe(), acs.getCodeGroupeEcran() );
    			m_droitParAxeEtGroupe.put( clef, acs );
    		}
			if( LogCommun.isTechniqueDebug() ) {
				LogCommun.info(contexte.getCodeUtilisateur(), "VisibiliteDAO", "getDroitAttenduPourAxe",
						"chargement du cache des droits d'habilitation (habil_acces) : " + nbAccess + " lignes");
			}
			droits = new MapDroit( m_droitParAxeEtGroupe );
			CacheManager.getInstance().setObjectInCache( droits );
    	}
      return droits.getElements().get( buildClefDroit( codeRole, codeAxe, codeGroupeEcran ) );
    }

    private String buildClefDroit( final String codeRole, final String axe, final String groupeEcran) {
        StringBuffer sb = new StringBuffer();
        sb.append( codeRole ).append( "_" ).append( axe ).append( "_" ).append( groupeEcran );
    	return sb.toString();
    }


    /**
     * Renvoie la liste des axes pour le profil demandé en se basant sur le cache
     * 		si possible. Sinon, effectue la lecture en base et met à jour le cache.
     * 		( table AXE_VISIBILITE )
     * @param contexte
     * @param codeProfil
     * @return List<AxeVisibilite>
     * @throws TechnicalException
     */
    public List<AxeVisibilite> getListeAxesProfilFromCache( final String codeProfil, final Contexte contexte )
            throws TechnicalException {

    	ListAxesVisibilites axes = (ListAxesVisibilites) CacheManager.getInstance().getObjectInCache( 
    			ListAxesVisibilites.class, codeProfil ); 
    	
        if( axes == null ) {
        	if( LogCommun.isTechniqueDebug() )
                LogCommun.debug("VisibiliteDAO", "getListeAxesProfilFromCache", "Init cache");
            
        	axes = new ListAxesVisibilites( getListeAxesProfil( codeProfil, contexte ), codeProfil );
        	
            if( axes != null ) {
            	CacheManager.getInstance().setObjectInCache( axes );
            }
        }
        return axes.getElements();
    }

    /**
     * Renvoie la liste des axes pour le profil demandé depuis la base. ( table AXE_VISIBILITE )
     * @param contexte
     * @param codeProfil
     * @return List<AxeVisibilite>
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	public List<AxeVisibilite> getListeAxesProfil( final String codeProfil, final Contexte contexte )
            throws TechnicalException {
        
    	List<AxeVisibilite> axes = null;
        
        try {
    		final List<TypeVisibilite> typesVisibilities = getTypesVisibilite( contexte );

    		SQLParamDescriptor param = new SQLParamDescriptor( codeProfil, false, Types.CHAR );    

            axes = (List<AxeVisibilite>) SQLCallExecuter.getInstance().executeSelectProc( "proc_select_axes_by_profil",
	                new SQLParamDescriptorSet( param ), "VisibiliteDAO", 
	                "getListeAxesProfil", contexte.getContexteAppelDAO(),
	                new SQLResultSetReader() {
	                  public Object instanciateFromLine(ResultSet rs) throws SQLException {
	                	  AxeVisibilite av = new AxeVisibilite();
	                      av.setCode( StringUtils.trimToEmpty( rs.getString( "codeAxe" ) ) );
	                      av.setLibelle( StringUtils.trimToEmpty( rs.getString( "libelleAxe" ) ) );
	                      av.setRegleHabilitation( 
	                    		  StringUtils.trimToEmpty( rs.getString( "regle_habilitation" ) ) );
	                      av.setAxeVente( rs.getBoolean( "is_axe_vente" ) );
	                      av.setAxeMultiType( rs.getBoolean( "is_axe_multi_type" ) );
	                      String types = StringUtils.trimToEmpty( rs.getString( "listeTypeValeurVisibilite" ) );
	                      if( StringUtils.isNotBlank( types  ) ) {
	                    	  List<TypeVisibilite> typesInList = new ArrayList<TypeVisibilite>();
	                    	  StringTokenizer st = new StringTokenizer( types, "," );
	                    	  TypeVisibilite t;
	                    	  while (st.hasMoreTokens()) {
	                    		  t = TypeVisibilite.getTypeVisibiliteForCode( st.nextToken(), typesVisibilities );
	                    		  if( t != null )
	                    			  typesInList.add( t );
	                    	  }
	                          av.setTypeValeurs( typesInList );
	                      }
	                      return av;
	                  }
	                }
        	);
        } catch( SQLException e ) {
        	LogCommun.major(contexte.getCodeUtilisateur(), "VisibiliteDAO",
        			"getListeAxesVenteProfilFromCache", e.getMessage(), e );
        	throw new TechnicalException(e);
        }
        return axes;
    }


	/***
	 * renvoie la liste des types de visibilites ( table TYPE_VALEUR_VISIBILITE )
	 * @param contexte
	 * @return List<TypeVisibilite>
	 * @throws TechnicalException
	 */
	@SuppressWarnings("unchecked")
	public List<TypeVisibilite> getTypesVisibilite( final Contexte contexte ) throws TechnicalException {
		
		ListTypeVisibilites types = (ListTypeVisibilites ) CacheManager.getInstance()
				.getObjectInCache( ListTypeVisibilites.class );
		
		try {
			if( types == null ) {
				
				List<TypeVisibilite> res = (List<TypeVisibilite>) SQLCallExecuter.getInstance()
						.executeSelectProc( "admin_sel_type_valeur_visib" , 
						new SQLParamDescriptorSet(), "VisibiliteDAO", "getTypesVisibilite", 
						contexte.getContexteAppelDAO(), new SQLResultSetReader() {
							public Object instanciateFromLine( ResultSet rs ) throws SQLException {
								TypeVisibilite tv = new TypeVisibilite();
								tv.setCode( StringUtils.trimToEmpty( rs.getString( "CODE_TYPE_VALEUR" ) ) );
								tv.setLibelle( StringUtils.trimToEmpty( rs.getString( "LIBELLE_TYPE_VALEUR" ) ) );
								return tv;
							}
						}
				);
				
				if( res != null ) {
					types = new ListTypeVisibilites( res );
					CacheManager.getInstance().setObjectInCache( types );
				}
				
			}
		} catch( SQLException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "VisibiliteDAO", "getTypesVisibilite", e.getMessage(), e );
			throw new TechnicalException(e);
		}
		return types.getElements();
	}

    /**
     * Renvoie tous les axes de visibilite du module Vente ( table AXE_VISIBILITE )
     * @param module 
     * @param contexte
     * @return List<AxeVisibilite>
     * @throws TechnicalException
     */
    public List<AxeVisibilite> getAxesVisibiliteVente( final Contexte contexte ) throws TechnicalException {
    	return getAxesVisibilite( AsaModule.APP_VENTE, contexte );
    }

    /**
     * Renvoie tous les axes de visibilite des modules tarif / admin / translate ( table AXE_VISIBILITE )
     * @param module 
     * @param contexte
     * @return List<AxeVisibilite>
     * @throws TechnicalException
     */
    public List<AxeVisibilite> getOtherAxesVisibilite( final Contexte contexte ) throws TechnicalException {
    	return getAxesVisibilite( AsaModule.DEFAULT_APP, contexte );
    }

    /**
     * Renvoie tous les axes de visibilite d'un module ( Vente ou les autres )
     * 		( table AXE_VISIBILITE )
     * @param module 
     * @param contexte
     * @return List<AxeVisibilite>
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	private List<AxeVisibilite> getAxesVisibilite( final AsaModule module, final Contexte contexte )
            throws TechnicalException {

    	List<AxeVisibilite> axes = null;
    	try {
            SQLParamDescriptor param = new SQLParamDescriptor( 
            		new Boolean( AsaModule.APP_VENTE.equals( module ) ), false, Types.BIT );
    		
    		final List<TypeVisibilite> typesVisibilities = getTypesVisibilite( contexte );
            
            axes = (List<AxeVisibilite>) SQLCallExecuter.getInstance().executeSelectProc(
                "admin_sel_axes_visibilite", new SQLParamDescriptorSet( param ),
                "VisibiliteDAO", "getAxesVisibilite", contexte.getContexteAppelDAO(),
                new SQLResultSetReader() {
                  public Object instanciateFromLine(ResultSet rs) throws SQLException {
                      AxeVisibilite av = new AxeVisibilite();
                      av.setCode( StringUtils.trimToEmpty( rs.getString( "codeAxe" ) ) );
                      av.setLibelle( StringUtils.trimToEmpty( rs.getString( "libelleAxe" ) ) );
                      av.setAxeMultiType( rs.getBoolean( "is_axe_multi_type" ) );
                      String types = StringUtils.trimToEmpty( rs.getString( "listeTypeValeurVisibilite" ) );
                      if( StringUtils.isNotBlank( types  ) ) {
                    	  List<TypeVisibilite> typesInList = new ArrayList<TypeVisibilite>();
                    	  StringTokenizer st = new StringTokenizer( types, "," );
                    	  TypeVisibilite t;
                    	  while (st.hasMoreTokens()) {
                    		  t = TypeVisibilite.getTypeVisibiliteForCode( st.nextToken(), typesVisibilities );
                    		  if( t != null )
                    			  typesInList.add( t );
                    	  }
                          av.setTypeValeurs( typesInList );
                      }
                      return av;
                  }
                }
            );
    		
        } catch( SQLException e ) {
            LogCommun.major(contexte.getCodeUtilisateur(), "VisibiliteDAO", "getAxesVisibilite",
                    "Erreur lors de la recuperation de la liste des axes de visibilites", e);
            throw new TechnicalException(e);
        }
        return axes;
	}

    /***
     * Ajoute une valeur de visibilite a l'utilisateur.
     * @param v
     * @param codeLangue
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public int addVisibilite( final Visibilite v, final Contexte contexte ) throws TechnicalException {
    	
      int codeRetour = Visibilite.NO_CONSTRAINT_ERROR;

      try {
    	  SQLParamDescriptor[] params = new SQLParamDescriptor[] {
    			  new SQLParamDescriptor( null, true, Types.SMALLINT ),
    			  new SQLParamDescriptor( v.getLogin(), false, Types.VARCHAR ),
    			  new SQLParamDescriptor( v.getValue(), false, Types.VARCHAR ),
    			  new SQLParamDescriptor( v.getCodeAxe(), false, Types.SMALLINT ),
    			  new SQLParamDescriptor( v.getType(), false, Types.SMALLINT ),
    	  };
    	  
    	  codeRetour = SQLCallExecuter.getInstance().executeUpdateWithReturnCode( "proc_insert_visibilite" ,
    			  new SQLParamDescriptorSet( params ), "VisibiliteDAO", "addVisibilite", 
    			  contexte.getContexteAppelDAO() );
    	  
          if( LogCommun.isFonctionelleDebug() )
        	  LogCommun.traceFonctionnelle( contexte.getCodeUtilisateur(), "VISIBILITE", "INS", v.toString() );

      } catch( SQLException e ) {
          LogCommun.major( contexte.getCodeUtilisateur(), "VisibiliteDAO", "addVisibilite", e.getMessage(), e );
          throw new TechnicalException(e);
      }
      return codeRetour;
    }

    /***
     * Renvoie les valeurs de visibilite d'un utilisateur pour un axe et un type de valeur (differentes de "_all" ) 
     * @param login
     * @param codeAxe
     * @param codeType
     * @param contexte
     * @return List<Visibilite>
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	public List<Visibilite> getVisibilites( final String login, final String codeAxe, final String codeType, 
    		final Contexte contexte ) throws TechnicalException {

    	List<Visibilite> visibilites = null;
    	
    	try {
            SQLParamDescriptor[] params = new SQLParamDescriptor[] {
            	new SQLParamDescriptor( login, false, Types.VARCHAR ),
            	new SQLParamDescriptor( codeAxe, false, Types.SMALLINT ),
            	new SQLParamDescriptor( codeType, false, Types.SMALLINT )
            };
    		
            visibilites = (List<Visibilite>) SQLCallExecuter.getInstance().executeSelectProc( 
            		"admin_sel_visib_by_user_axe",
                    new SQLParamDescriptorSet( params ), "VisibiliteDAO", "getVisibilites",
                    contexte.getContexteAppelDAO(),
                    new SQLResultSetReader() {
                        public Object instanciateFromLine(ResultSet rs) throws SQLException {
                            Visibilite v = new Visibilite();
                            v.setLogin( login );
                            v.setCodeAxe( codeAxe );
                            v.setType( StringUtils.trimToEmpty( rs.getString( "code_type_valeur" ) ) );
                            v.setValue( StringUtils.trimToEmpty( rs.getString( "code_valeur" ) ) );
                            v.setLabel( StringUtils.trimToEmpty( rs.getString( "libelle_valeur" ) ) );
                            return v;
                        }
                    }
            );

    	} catch (SQLException e) {
            LogCommun.major( contexte.getCodeUtilisateur(), "VisibiliteDAO", "getVisibilites",
                    "echec lors de la recuperation de la liste des valeurs de visibilité : " + login, e );
            throw new TechnicalException( e );
        }
    	return visibilites;
    }

    /***
     * Renvoie la liste des axes de visibilite sur vente sur lesquels
     *   l'utilisateur a une visibilité complète (All)
     * @param login
     * @param contexte
     * @return List<String>
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	public List<String> getAxesWithVisibiliteAll( final String login, final Contexte contexte )
            throws TechnicalException {
        
    	List<String> axes;
        try {
        	SQLParamDescriptor param = new SQLParamDescriptor( login, false, Types.VARCHAR );
        	
        	axes = (List<String>) SQLCallExecuter.getInstance().executeSelectProc( 
        			"admin_sel_axes_visib_all",
        			new SQLParamDescriptorSet(param), "VisibiliteDAO", "getAxesWithVisibiliteAll",
        			contexte.getContexteAppelDAO(), 
        			new SQLResultSetReader() {
                  		public Object instanciateFromLine(ResultSet rs) throws SQLException {
                  			return StringUtils.trimToEmpty( rs.getString("code_axe") );
                  		} 
                  	} 
        	);
        } catch (SQLException e) {
          LogCommun.major( contexte.getCodeUtilisateur(), "VisibiliteDAO", "getAxesWithVisibiliteAll", login, e);
          throw new TechnicalException(e);
        }
        return axes;
    }

    /**
     * Renvoie les valeurs de visibilite d'un utilisateur
     * @param Identifiant de l'utilisateur
     * @return List<Visibilite>
     */
    @SuppressWarnings("unchecked")
	public List<Visibilite> getVisibilites( final String login, final Contexte contexte )
            throws TechnicalException {
        
    	List<Visibilite> visibilites = null;
    	
    	try {
            SQLParamDescriptor param = new SQLParamDescriptor( login, false, Types.VARCHAR );
    		
            visibilites = (List<Visibilite>) SQLCallExecuter.getInstance().executeSelectProc( 
            		"proc_sel_visib_by_user",
                    new SQLParamDescriptorSet(param), "VisibiliteDAO", "getVisibilites",
                    contexte.getContexteAppelDAO(),
                    new SQLResultSetReader() {
                        public Object instanciateFromLine(ResultSet rs) throws SQLException {
                            Visibilite v = new Visibilite();
                            v.setLogin( login );
                            v.setValue( StringUtils.trimToEmpty( rs.getString( "code_valeur" ) ) );
                            v.setLabel( StringUtils.trimToEmpty( rs.getString( "libelle_valeur" ) ) );
                            v.setCodeAxe( StringUtils.trimToEmpty( rs.getString( "code_axe" ) ) );
                            v.setType( StringUtils.trimToEmpty( rs.getString( "code_type_valeur" ) ) );
                            return v;
                        }
                    }
            );
            
        } catch (SQLException e) {
            LogCommun.major( contexte.getCodeUtilisateur(), "VisibiliteDAO", "getVisibilites",
                    "echec lors de la recuperation de la liste des valeurs de visibilité : "+login, e );
            throw new TechnicalException( e );
        }
        return visibilites;
    }


    /***
     * Supprime une valeur de visibilite d'un utilisateur
     * @param login
     * @param codeAxe
     * @param codeType
     * @param contexte
     * @throws TechnicalException
     */
    public void deleteVisibilites( final String login, final String codeAxe, final Contexte contexte ) 
    		throws TechnicalException {
      
      try {
    	  SQLParamDescriptor[] params = new SQLParamDescriptor[] {
    			  new SQLParamDescriptor( login, false, Types.VARCHAR ),
    			  new SQLParamDescriptor( codeAxe, false, Types.SMALLINT )
    	  };
    	  
    	  SQLCallExecuter.getInstance().executeUpdate( "admin_delete_visibilite", 
    			  new SQLParamDescriptorSet( params ), "VisibiliteDAO", "deleteVisibilites", 
    			  contexte.getContexteAppelDAO() );
    	  
    	  if( LogCommun.isFonctionelleDebug() )
    		  LogCommun.traceFonctionnelle( contexte.getCodeUtilisateur(), "HABIL_VISIBILITE", "DEL",
                login + "_" + codeAxe );

      } catch( SQLException e ) {
        LogCommun.major( contexte.getCodeUtilisateur(), "VisibiliteDAO", "deleteVisibilites", e.getMessage(), e);
        throw new TechnicalException(e);
      } 
    }

}
