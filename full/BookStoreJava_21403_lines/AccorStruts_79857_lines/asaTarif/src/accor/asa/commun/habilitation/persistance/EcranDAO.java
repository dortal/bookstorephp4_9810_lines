package com.accor.asa.commun.habilitation.persistance;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CacheManager;
import com.accor.asa.commun.habilitation.metier.AsaModule;
import com.accor.asa.commun.habilitation.metier.ecran.Ecran;
import com.accor.asa.commun.habilitation.metier.ecran.GroupeEcran;
import com.accor.asa.commun.habilitation.metier.ecran.ListGroupeEcrans;
import com.accor.asa.commun.habilitation.metier.optionmenu.GroupeOptionMenu;
import com.accor.asa.commun.habilitation.metier.optionmenu.GroupeOptionMenuMap;
import com.accor.asa.commun.habilitation.metier.optionmenu.OptionMenu;
import com.accor.asa.commun.habilitation.metier.optionmenu.OptionMenuRole;
import com.accor.asa.commun.habilitation.metier.optionmenu.OptionsMenuRoleMap;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.DAO;
import com.accor.asa.commun.persistance.SQLCallExecuter;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.process.PoolCommunFactory;

public class EcranDAO extends DAO {

	// le singleton
	private static final EcranDAO _dao = new EcranDAO();

	/**
	 * put your documentation comment here
	 *
	 * @return
	 */
	public static EcranDAO getInstance() {
		return _dao;
	}

    /**
     * Renvoie toutes les options du menu de vente
     * @params Contexte
     * @return Map<GroupeOptionMenu,List<OptionMenu>>
     * @throws TechnicalException
     */
    public Map<GroupeOptionMenu,List<OptionMenu>> getAllOptionMenuVente( final Contexte contexte ) 
    		throws TechnicalException {
    	return getAllOptionMenu( AsaModule.APP_VENTE, contexte );
    }
    	
    /**
     * Renvoie toutes les options du menu de tarif
     * @params Contexte
     * @return Map<GroupeOptionMenu,List<OptionMenu>>
     * @throws TechnicalException
     */
    public Map<GroupeOptionMenu,List<OptionMenu>> getAllOptionMenuTarif( final Contexte contexte ) 
    		throws TechnicalException {
    	return getAllOptionMenu( AsaModule.APP_TARIF, contexte );
    }
    	
    /**
     * Renvoie toutes les options du menu d'admin
     * @params Contexte
     * @return Map<GroupeOptionMenu,List<OptionMenu>>
     * @throws TechnicalException
     */
    public Map<GroupeOptionMenu,List<OptionMenu>> getAllOptionMenuAdmin( final Contexte contexte ) 
    		throws TechnicalException {
    	return getAllOptionMenu( AsaModule.APP_ADMIN, contexte );
    }
    	
    /**
     * Renvoie toutes les options du menu de translate
     * @params Contexte
     * @return Map<GroupeOptionMenu,List<OptionMenu>>
     * @throws TechnicalException
     */
    public Map<GroupeOptionMenu,List<OptionMenu>> getAllOptionMenuTranslate( final Contexte contexte ) 
    		throws TechnicalException {
    	return getAllOptionMenu( AsaModule.APP_TRANSLATE, contexte );
    }
    	
    /***
     * Renvoie toutes les options du menu d'un des modules
     * @param module
     * @param contexte
     * @return Map<GroupeOptionMenu,List<OptionMenu>>
     * @throws TechnicalException
     */
    private Map<GroupeOptionMenu,List<OptionMenu>> getAllOptionMenu( final AsaModule module, 
    		final Contexte contexte ) throws TechnicalException {
    	GroupeOptionMenuMap optionsMap = (GroupeOptionMenuMap) CacheManager.getInstance().getObjectInCache( 
    			GroupeOptionMenuMap.class, module.getName() );
    	
    	if( optionsMap == null ) {
    		initAllOptionMenu( contexte );
    		optionsMap = (GroupeOptionMenuMap) CacheManager.getInstance().getObjectInCache( 
        			GroupeOptionMenuMap.class, module.getName() );
    	}
    	return optionsMap.getElements();
    }
    	
    /**
     * Initialise la Map contenant les options de tous les modules par module et par groupe d options
     * @params Contexte
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	private void initAllOptionMenu( final Contexte contexte ) throws TechnicalException {
    	
		List<OptionMenu> options;
        try {
        	options = (List<OptionMenu>) SQLCallExecuter.getInstance().executeSelectProcSansLimite( 
        			"proc_get_all_option_by_groupe", new SQLParamDescriptorSet(), "EcranDAO",
                    "getAllOptionMenu", contexte.getContexteAppelDAO(), 
                    new SQLResultSetReader() {
        				public Object instanciateFromLine( ResultSet rs ) throws SQLException {
        					OptionMenu o = new OptionMenu();
        					o.setCodeOption( rs.getInt( "CodeOption" ) );
        					o.setCleInternationalisation( StringUtils.trimToEmpty(
        							rs.getString( "CleInternationalisation" ) ) );
        					o.setCodeGroupeOption( rs.getInt( "CodeGroupeOption" ) );
        					o.setCleInternationalisationGroupe( StringUtils.trimToEmpty(
        							rs.getString( "CleInternationalisationGroupe" ) ) );
        					o.setEcranVente( rs.getBoolean( "is_ecran_vente" ) );
        					o.setEcranTarif( rs.getBoolean( "is_ecran_tarif" ) );
        					o.setEcranAdmin( rs.getBoolean( "is_ecran_admin" ) );
        					o.setEcranTrans( rs.getBoolean( "is_ecran_trans" ) );
        					return o;
        				}
        			}
        	);
        } catch (SQLException e) {
        	LogCommun.major( contexte.getCodeUtilisateur(), "EcranDAO", "getAllOptionMenu", e.getMessage(), e );
            throw new TechnicalException(e);
        }

        if( options!=null && options.size()>0 ) {

        	Map<GroupeOptionMenu,List<OptionMenu>> optionsVente = 
        			new HashMap<GroupeOptionMenu,List<OptionMenu>>();
        	Map<GroupeOptionMenu,List<OptionMenu>> optionsTarif = 
        			new HashMap<GroupeOptionMenu,List<OptionMenu>>();
        	Map<GroupeOptionMenu,List<OptionMenu>> optionsAdmin = 
        			new HashMap<GroupeOptionMenu,List<OptionMenu>>();
        	Map<GroupeOptionMenu,List<OptionMenu>> optionsTrans = 
        			new HashMap<GroupeOptionMenu,List<OptionMenu>>();
            
            OptionMenu option = null;
            
            for( int i=0, size=options.size(); i<size; i++ ) {
            	option = options.get(i);
            	
            	if( option.isEcranVente() ) {
            		addOption( option, optionsVente );
                }
            	if( option.isEcranTarif() ) {
            		addOption( option, optionsTarif );
                }
            	if( option.isEcranAdmin() ) {
            		addOption( option, optionsAdmin );
                }
            	if( option.isEcranTrans() ) {
            		addOption( option, optionsTrans );
                }
            }
            
            CacheManager.getInstance().setObjectInCache( 
            		new GroupeOptionMenuMap( optionsVente, AsaModule.NAME_APP_VENTE ) );
            CacheManager.getInstance().setObjectInCache( 
            		new GroupeOptionMenuMap( optionsTarif, AsaModule.NAME_APP_TARIF ) );
            CacheManager.getInstance().setObjectInCache( 
            		new GroupeOptionMenuMap( optionsAdmin, AsaModule.NAME_APP_ADMIN ) );
            CacheManager.getInstance().setObjectInCache( 
            		new GroupeOptionMenuMap( optionsTrans, AsaModule.NAME_APP_TRANSLATE ) );
        }
    }

    /***
     * Methode permettant de rajouter une option dans la liste des options du groupe concerne
     * @param o
     * @param optionsMap
     */
    private void addOption( final OptionMenu o, Map<GroupeOptionMenu,List<OptionMenu>> optionsMap ) {
    	GroupeOptionMenu groupe = new GroupeOptionMenu( 
    			o.getCodeGroupeOption(), o.getCleInternationalisationGroupe() );
    	List<OptionMenu> options = optionsMap.get( groupe );
		if( options == null )
			options = new ArrayList<OptionMenu>();
		options.add( o );
		optionsMap.put( groupe, options );
    }
    
    /**
     * Renvoie les toutes les options de menu pour tous les roles
     * @param contexte
     * @param codeRole
     * @return List<OptionMenuRole>
     * @throws TechnicalException
     */
    public List<OptionMenuRole> getAllOptionsMenuRole( final Contexte contexte ) 
    		throws TechnicalException {
    	return getInnerOptionsMenuRole( "-1", contexte );
    }

    /**
     * Renvoie les options de menu pour le role donne en parametre
     * @param contexte
     * @param codeRole
     * @return Set<String>
     * @throws TechnicalException
     */
    public Set<String> getOptionsMenuRole( final String codeRole, final Contexte contexte ) throws TechnicalException {
        
    	List<OptionMenuRole> options = getInnerOptionsMenuRole( codeRole, contexte );
    	Set<String> res = new HashSet<String>();
        if( options != null ) {
            for (int i = 0; i < options.size(); i++) {
                res.add( options.get(i).getCodeOption() );
            }
        }
        return res;
    }
    
    /**
     * Renvoie les options de menu pour le role donne en parametre
     * @param contexte
     * @param codeRole
     * @return List<OptionMenuRole>
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	private List<OptionMenuRole> getInnerOptionsMenuRole( final String codeRole, 
    		final Contexte contexte ) throws TechnicalException {
    	List<OptionMenuRole> options = null;
        try {
            SQLParamDescriptor param = new SQLParamDescriptor( codeRole, false, Types.INTEGER );
        	
            options = (List<OptionMenuRole>) SQLCallExecuter.getInstance().executeSelectProcSansLimite( 
        			"proc_get_options_menu_by_role", new SQLParamDescriptorSet( param ),
        			"EcranDAO", "getOptionsMenuRole", contexte.getContexteAppelDAO(),
                    new SQLResultSetReader() {
        				public Object instanciateFromLine(ResultSet rs) throws SQLException {
        					OptionMenuRole o = new OptionMenuRole();
        					o.setCodeOption( rs.getString("CODEOPTION") );
                            o.setCodeRole( rs.getString("CODEROLE") );
                            return o;	
        				}
        			}
        	);
        	
        } catch (SQLException e) {
        	LogCommun.major( contexte.getCodeUtilisateur(), "EcranDAO", "getOptionsMenuRole", e.getMessage(), e );
            throw new TechnicalException(e);
        }
        return options;
    }

    /**
     * Charge tous les ecrans avec leur groupe. Cache dans metier.Habilitation
     * @return List<Ecran>
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	public List<Ecran> loadEcransGroupeRegleHabilitation() throws TechnicalException {
      
    	List<Ecran> result;
    	
    	try {
          result = (List<Ecran>) SQLCallExecuter.getInstance().executeSelectProcSansLimite( 
        		  "proc_get_all_ecrans_by_groupe",
        		  new SQLParamDescriptorSet(),"EcranDAO","loadEcransGroupeRegleHabilitation",
                  ( new Contexte() ).getContexteAppelDAO(), new SQLResultSetReader() {
              		public Object instanciateFromLine(ResultSet rs) throws SQLException {
              			return new Ecran(
                            rs.getString("codeEcran"),
                            rs.getString("codeGroupeEcran"),
                            StringUtils.trimToEmpty(rs.getString("procHabilitation")),
                            StringUtils.trimToEmpty(rs.getString("fichier_aide")),
                            rs.getBoolean("is_ecran_vente"),
                            rs.getBoolean("is_ecran_tarif"),
                            rs.getBoolean("is_ecran_admin"),
                            rs.getBoolean("is_ecran_trans")
                    );
              }
          });
      } catch (SQLException e) {
    	  LogCommun.major( "", "EcranDAO", "loadEcransGroupeRegleHabilitation", e.getMessage(), e );
          e.printStackTrace();
          throw new TechnicalException(e);
      }
      return result;
    }

	/***
	 * Renvoie la liste des groupes d'ecrans declares avec leur appartenance ( vente, tarif ... )
	 * @param contexte
	 * @return List<GroupeEcran>
	 * @throws TechnicalException
	 */
    @SuppressWarnings("unchecked")
	public List<GroupeEcran> getGroupeEcrans( AsaModule module, final Contexte contexte ) throws TechnicalException {

		ListGroupeEcrans groupes = (ListGroupeEcrans) CacheManager.getInstance()
				.getObjectInCache( ListGroupeEcrans.class, module.getName());

		if( groupes == null ) {
			try {
				List<GroupeEcran> list = (List<GroupeEcran>) SQLCallExecuter.getInstance()
						.executeSelectProc ( "admin_get_all_groupe_IHM",
						new SQLParamDescriptorSet(), "EcranDAO", "getGroupeEcrans",
						contexte.getContexteAppelDAO(), new SQLResultSetReader() {
							public Object instanciateFromLine(ResultSet rs) throws SQLException {
								GroupeEcran ge = new GroupeEcran();
								ge.setCode( StringUtils.trimToEmpty( rs.getString( "codeGroupeEcran" ) ) );
								ge.setLibelle( StringUtils.trimToEmpty( rs.getString( "libelleGroupeEcran" ) ) );
								ge.setGroupeEcranVente(rs.getBoolean("is_vente"));
								ge.setGroupeEcranTarif(rs.getBoolean("is_tarif"));
								ge.setGroupeEcranAdmin(rs.getBoolean("is_admin"));
								ge.setGroupeEcranTranslate(rs.getBoolean("is_translate"));
								return ge;
							}
						}
				);

				if (list != null && list.size() > 0) {
					List<GroupeEcran> groupesEcransVente = new ArrayList<GroupeEcran>();
					List<GroupeEcran> groupesEcransTarif = new ArrayList<GroupeEcran>();
					List<GroupeEcran> groupesEcransAdmin = new ArrayList<GroupeEcran>();
					List<GroupeEcran> groupesEcransTranslate = new ArrayList<GroupeEcran>();
					
					GroupeEcran groupeEcran = null;
					for (int i = 0; i < list.size(); i ++) {
						groupeEcran = list.get(i);
						if (groupeEcran.isGroupeEcranVente())
							groupesEcransVente.add(groupeEcran);
						if (groupeEcran.isGroupeEcranTarif())
							groupesEcransTarif.add(groupeEcran);
						if (groupeEcran.isGroupeEcranAdmin())
							groupesEcransAdmin.add(groupeEcran);
						if (groupeEcran.isGroupeEcranTranslate())
							groupesEcransTranslate.add(groupeEcran);
					}

					CacheManager.getInstance().setObjectInCache(new ListGroupeEcrans(groupesEcransVente, AsaModule.NAME_APP_VENTE));
					CacheManager.getInstance().setObjectInCache(new ListGroupeEcrans(groupesEcransTarif, AsaModule.NAME_APP_TARIF));
					CacheManager.getInstance().setObjectInCache(new ListGroupeEcrans(groupesEcransAdmin, AsaModule.NAME_APP_ADMIN));
					CacheManager.getInstance().setObjectInCache(new ListGroupeEcrans(groupesEcransTranslate, AsaModule.NAME_APP_TRANSLATE));
				}
			} catch(SQLException e) {
				LogCommun.major( contexte.getCodeUtilisateur(), "EcranDAO", "getGroupeEcrans", e.getMessage(), e );
				throw new TechnicalException(e);
			}
		}
		groupes = (ListGroupeEcrans) CacheManager.getInstance().getObjectInCache( ListGroupeEcrans.class, module.getName());
		return groupes.getElements();
	}

	 /***
	  * Enregistre les options du menu par role
	  * @param table de hashage (codeRole) -> codeOption
	  * @param module
	  * @param tableOptionsMenuRole
	  * @param contexte
	  * @throws TechnicalException
	  */
	 public void setOptionMenuByRole( final AsaModule module, final OptionsMenuRoleMap optionsMenuRole,
			final Contexte contexte ) throws TechnicalException {

		Connection cnt = null;

		try {
			if( optionsMenuRole != null ) {

				cnt = PoolCommunFactory.getInstance().getConnection();

				//Suppression des associations Role/Option existantes
				SQLParamDescriptor[] delParams = new SQLParamDescriptor[] {
					new SQLParamDescriptor( new Boolean( module.equals( AsaModule.APP_VENTE ) ), false, Types.BIT ),
					new SQLParamDescriptor( new Boolean( module.equals( AsaModule.APP_TARIF ) ), false, Types.BIT ),
					new SQLParamDescriptor( new Boolean( module.equals( AsaModule.APP_ADMIN ) ), false, Types.BIT ),
					new SQLParamDescriptor( new Boolean( module.equals( AsaModule.APP_TRANSLATE ) ), false, Types.BIT )
				};

				SQLCallExecuter.getInstance().executeUpdate( cnt, "admin_delete_option_menu_role",
						new SQLParamDescriptorSet( delParams ), "EcranDAO", "setOptionMenuByRole",
						contexte.getContexteAppelDAO() );

				LogCommun.traceFonctionnelle(contexte.getCodeUtilisateur(), "HABIL_OPTION_MENU_ROLE", "DEL", module.toString() );

				//Ajout des nouvelles associations
				OptionMenuRole option;
				SQLParamDescriptor[] insParams = new SQLParamDescriptor[2];

				Set<String> keys = optionsMenuRole.getKeys();
				if( keys != null ) {
					Iterator<String> it= keys.iterator();
					
					while( it.hasNext() ) {
						option = optionsMenuRole.getOptionMenuRole( it.next() );

						insParams[0] = new SQLParamDescriptor( option.getCodeRole(), false, Types.INTEGER );
						insParams[1] = new SQLParamDescriptor( option.getCodeOption(), false, Types.INTEGER );

						SQLCallExecuter.getInstance().executeUpdate( cnt,
								"admin_insert_option_menu_role", new SQLParamDescriptorSet( insParams ),
								"EcranDAO", "setOptionMenuByRole", contexte.getContexteAppelDAO() );

						if( LogCommun.isFonctionelleDebug() )
							LogCommun.traceFonctionnelle( contexte.getCodeUtilisateur(),
								"HABIL_OPTION_MENU_ROLE", "INS", option.getCodeRole() + "_" + option.getCodeOption() );
					}
				}
			}
		} catch( SQLException e ) {
			LogCommun.major(contexte.getCodeUtilisateur(), "EcranDAO", "setOptionMenuVenteByRole",
					e.getMessage(), e);
			throw new TechnicalException(e);
		} catch(Exception e) {
			LogCommun.major(contexte.getCodeUtilisateur(), "EcranDAO", "setOptionMenuVenteByRole",
					e.getMessage(), e);
			throw new TechnicalException(e);
		} finally {
			releaseConnection(cnt, null);
		}
	}

}