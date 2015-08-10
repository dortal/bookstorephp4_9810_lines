package com.accor.asa.commun.habilitation.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CacheManager;
import com.accor.asa.commun.habilitation.metier.AsaModule;
import com.accor.asa.commun.habilitation.metier.ListRoles;
import com.accor.asa.commun.habilitation.metier.Role;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.DAO;
import com.accor.asa.commun.persistance.SQLCallExecuter;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;

public class RoleDAO extends DAO {

	private static RoleDAO _instance = new RoleDAO();
	
	private RoleDAO() {}
	
	public static RoleDAO getInstance() {
		return _instance;
	}

    /***
     * Renvoie tous les roles de vente ( table ROLES )
     * @param contexte
     * @return List<Role>
     * @throws TechnicalException
     */
    public List<Role> getRolesVente( final Contexte contexte ) throws TechnicalException {
    	return getRoles( AsaModule.APP_VENTE, contexte );
    }
    
    /***
     * Renvoie tous les roles qui n'appartiennent pas a Vente ( table ROLES )
     * @param contexte
     * @return List<Role>
     * @throws TechnicalException
     */
    public List<Role> getOthersRoles( final Contexte contexte ) throws TechnicalException {
    	return getRoles( AsaModule.DEFAULT_APP, contexte );
    }

    
    /***
     * Renvoie tous les roles d'un module ( table ROLES )
     * @param module
     * @param contexte
     * @return List<Role>
     * @throws TechnicalException
     */
    private List<Role> getRoles( final AsaModule module, final Contexte contexte ) 
    		throws TechnicalException {
        
    	ListRoles roles = (ListRoles) CacheManager.getInstance().getObjectInCache( ListRoles.class, module.getName() );
    	if( roles == null ) {
    		List<Role> allRoles = getRolesAdmin( contexte );
    		initCacheRoles( allRoles, contexte );
    		roles = (ListRoles) CacheManager.getInstance().getObjectInCache( ListRoles.class, module.getName() );
    	}
    	return roles.getElements();
    }
    
    /***
     * Renvoie l'ensemble des roles du systeme 
     * @param contexte
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	public List<Role> getRolesAdmin( final Contexte contexte ) throws TechnicalException {
    
    	List<Role> roles = null;
    	
    	try {
        	SQLParamDescriptor param= new SQLParamDescriptor( new Boolean( false ), false, Types.BIT );

        	roles = (List<Role>) SQLCallExecuter.getInstance().executeSelectProc("proc_sel_roles", 
        			new SQLParamDescriptorSet(param), "HabilitationDAO", "initRoles",  
        			contexte.getContexteAppelDAO(), new SQLResultSetReader() {
                        public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
                            Role r = new Role();
                            r.setCode( String.valueOf( rs.getInt("codeRole") ) );
                            r.setLibelle( rs.getString("libelleRole") );
                            r.setResponsableDossier(rs.getBoolean("responsable_dossier"));
                            r.setReattributionDossier(rs.getBoolean("reattribution_dossier"));
                            r.setRoleVente( rs.getBoolean( "is_role_vente" ) );
                            r.setRoleDbm(rs.getBoolean("is_role_dbm"));
                            r.setRoleHotel(rs.getBoolean("is_role_hotel"));
                            r.setRoleValidateurPremierNiveau(rs.getBoolean("is_role_validateur_1er_niveau"));
                            r.setRoleValidateurDeuxiemeNiveau(rs.getBoolean("is_role_validateur_2eme_niveau"));
                            r.setRoleValidateurTroisiemeNiveau(rs.getBoolean("is_role_validateur_3eme_niveau"));
                            return r;
                        }
                    }
        	);
        	
	    } catch (SQLException e) {
	        LogCommun.major( contexte.getCodeUtilisateur(), "HabilitationDAO", "initRoles", e.getMessage(), e );
	        throw new TechnicalException(e);
	    }
	    return roles;
	}

   /***
	* Mise a jour des infos d'un role
	* @param codeRole
	* @param responsableDossier
	* @param reattributionDossier
	* @param contexte
	* @throws TechnicalException
	*/
	public void saveInfoRole( final String codeRole, final boolean responsableDossier, 
			final boolean reattributionDossier, final Contexte contexte ) throws TechnicalException {

		try {
			SQLParamDescriptor[] params = new SQLParamDescriptor[] {
					new SQLParamDescriptor( new Integer( codeRole ), false, Types.INTEGER ),
					new SQLParamDescriptor( new Boolean( responsableDossier ), false,Types.BIT ),
					new SQLParamDescriptor( new Boolean( reattributionDossier ), false,Types.BIT )
			};
			
			SQLCallExecuter.getInstance().executeUpdate( "admin_upd_info_role", 
					new SQLParamDescriptorSet( params ), "HabilitationDAO", "saveInfoRole", 
					contexte.getContexteAppelDAO() );
			
			List<Role> allRoles = getRolesAdmin( contexte );
    		initCacheRoles( allRoles, contexte );

		} catch( SQLException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "HabilitationDAO", "saveInfoRole", e.getMessage(), e );
			throw new TechnicalException(e);
		}
	}

    /***
	 * Initialise les caches contenant les listes de Roles 
	 * @param contexte
	 * @throws TechnicalException
	 */
	private void initCacheRoles( final List<Role> roles, final Contexte contexte ) throws TechnicalException {
    	if( roles != null ) {
    		Role r = null;
    		List<Role> rolesVente = new ArrayList<Role>();
    		List<Role> otherRoles = new ArrayList<Role>();
    		
    		for( int i=0, size=roles.size(); i<size; i++ ) {
    			r = (Role) roles.get( i );
    			if( r.isRoleVente() )
    				rolesVente.add( r );
    			else
    				otherRoles.add( r );
    		}
    	
    		CacheManager.getInstance().setObjectInCache( 
    				new ListRoles( rolesVente, AsaModule.NAME_APP_VENTE ) );
    		CacheManager.getInstance().setObjectInCache( 
    				new ListRoles( otherRoles, AsaModule.DEFAULT_NAME ) );
    	}
    }
}
