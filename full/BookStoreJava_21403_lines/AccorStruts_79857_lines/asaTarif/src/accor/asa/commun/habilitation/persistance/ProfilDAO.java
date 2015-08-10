package com.accor.asa.commun.habilitation.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CacheManager;
import com.accor.asa.commun.habilitation.metier.ListProfils;
import com.accor.asa.commun.habilitation.metier.Profil;
import com.accor.asa.commun.habilitation.metier.Role;
import com.accor.asa.commun.habilitation.metier.visibilite.AxeVisibilite;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.DAO;
import com.accor.asa.commun.persistance.SQLCallExecuter;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.user.metier.UserLight;

public class ProfilDAO extends DAO {

	private static ProfilDAO _instance = new ProfilDAO();
	
	private ProfilDAO() {}
	
	public static ProfilDAO getInstance() {
		return _instance;
	}

    /**
     * Recherche de tous les profils ( supprimer inclus )
     * @param contexte 
     * @return List<Profil>
     */
    public List<Profil> getProfilsAdmin( final Contexte contexte ) throws TechnicalException {
    	return initProfils( true, contexte );
    }
    
    /**
     * Recherche de tous les profils
     *
     * @param contexte 
     * @return List<Profil>
     */
    public List<Profil> getProfils( final Contexte contexte ) throws TechnicalException {

        ListProfils profils = (ListProfils) CacheManager.getInstance().getObjectInCache(ListProfils.class);

        if (profils == null) {
        	profils = new ListProfils( initProfils( false, contexte ) );
            CacheManager.getInstance().setObjectInCache(profils);
        }
        return profils.getElements();
    }

    /***
     * Methode de creation d'un nouveau profil
     * @param p
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public int insertProfil( Profil p, final Contexte contexte ) throws TechnicalException {

    	int codeRetour = Profil.NO_CONSTRAINT_ERROR;
    	String codeProfil = null;
    	
    	try {
    		if( p != null ) {
    			
    			String codeRole = null;
    			if( p.getRole() != null )
    				codeRole = p.getRole().getCode();
    			
    			String codeRoleVente = null;
    			if( p.getRoleVente() != null )
    				codeRoleVente = p.getRoleVente().getCode();
    			
    			SQLParamDescriptor[] params = new SQLParamDescriptor[] {
    					new SQLParamDescriptor( null, true, Types.INTEGER ),
    					new SQLParamDescriptor( p.getLibelle(), false, Types.VARCHAR ),
    					new SQLParamDescriptor( codeRole, false, Types.SMALLINT ),
    					new SQLParamDescriptor( codeRoleVente, false, Types.SMALLINT ),
    					new SQLParamDescriptor( new Boolean( p.isTarifLight() ), false, Types.BIT )	
    			};
    			
    			codeRetour = SQLCallExecuter.getInstance().executeUpdateWithReturnCode( "admin_insert_profile" , 
    					new SQLParamDescriptorSet( params ), "ProfilDAO", "insertProfil", 
    					contexte.getContexteAppelDAO() );
    			
    			if( codeRetour > Profil.NO_CONSTRAINT_ERROR ) {
    				codeProfil = String.valueOf( codeRetour );    				
    				insertProfilAxes( codeProfil, p.getAxesTarif(), contexte );
    				insertProfilAxes( codeProfil, p.getAxesVente(), contexte );
    				p.setCode( codeProfil );
    				codeRetour = Profil.NO_CONSTRAINT_ERROR;
    			}
    		}
		} catch( SQLException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "ProfilDAO", "insertProfil", e.getMessage(), e );
			throw new TechnicalException(e);
		}
    	return codeRetour;
    }
    
    /***
     * methode de mise a jour d'un profil et de ses axes de visibilites
     * @param p
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public int updateProfil( final Profil p, final Contexte contexte ) throws TechnicalException {
    	
    	int codeRetour = Profil.NO_CONSTRAINT_ERROR;
    	
    	try {
	    	if( p != null ) {
	    		
    			String codeRole = null;
    			if( p.getRole() != null )
    				codeRole = p.getRole().getCode();
    			
    			String codeRoleVente = null;
    			if( p.getRoleVente() != null )
    				codeRoleVente = p.getRoleVente().getCode();
    			
    			SQLParamDescriptor[] params = new SQLParamDescriptor[] {
    					new SQLParamDescriptor( null, true, Types.INTEGER ),
    					new SQLParamDescriptor( p.getCode(), false, Types.CHAR ),
    					new SQLParamDescriptor( p.getLibelle(), false, Types.VARCHAR ),
    					new SQLParamDescriptor( codeRole, false, Types.SMALLINT ),
    					new SQLParamDescriptor( codeRoleVente, false, Types.SMALLINT ),
    					new SQLParamDescriptor( new Boolean( p.isTarifLight() ), false, Types.BIT )	
    			};
    			
    			codeRetour = SQLCallExecuter.getInstance().executeUpdateWithReturnCode( "admin_update_profile" , 
    					new SQLParamDescriptorSet( params ), "ProfilDAO", "updateProfil", 
    					contexte.getContexteAppelDAO() );
	    		
    			if( codeRetour == Profil.NO_CONSTRAINT_ERROR ) {
    				String codeProfil = p.getCode();
    				deleteProfilAxe( codeProfil, contexte );
    				insertProfilAxes( codeProfil, p.getAxesTarif(), contexte );
    				insertProfilAxes( codeProfil, p.getAxesVente(), contexte );
    			}
	    		
	    	}
		} catch( SQLException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "ProfilDAO", "updateProfil", e.getMessage(), e );
			throw new TechnicalException(e);
		}
    	return codeRetour;
    }
    
    /***
     * Methode suppression logique d'un profil
     * @param p
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public int deleteProfil( final Profil p, final Contexte contexte ) throws TechnicalException {
    	
    	int codeRetour = Profil.NO_CONSTRAINT_ERROR;
    	
    	try {
    		if( p != null ) {
    			SQLParamDescriptor[] params = new SQLParamDescriptor[] {
    					new SQLParamDescriptor( null, true, Types.INTEGER ),
    					new SQLParamDescriptor( p.getCode(), false, Types.CHAR )
    			};

    			codeRetour = SQLCallExecuter.getInstance().executeUpdateWithReturnCode( "admin_delete_Profile", 
    					new SQLParamDescriptorSet( params ), "ProfilDAO", "deleteProfil", 
    					contexte.getContexteAppelDAO() );
    		}
		} catch( SQLException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "ProfilDAO", "deleteProfil", e.getMessage(), e );
			throw new TechnicalException(e);
		}
    	return codeRetour;
    }
    
	/***
	 * Methode de recuperation des utilisateurs lie a ce profil
	 * @param idTerritory
	 * @param contexte
	 * @return List<UserLight>
	 * @throws TechnicalException
	 */
	@SuppressWarnings("unchecked")
	public List<UserLight> getUsers( final String codeProfil, final Contexte contexte ) throws TechnicalException {
		
		List<UserLight> users = null;
		
		try {
			SQLParamDescriptor param = new SQLParamDescriptor( codeProfil, false, Types.CHAR );
			
			users = (List<UserLight>) SQLCallExecuter.getInstance().executeSelectProc( "admin_get_users_by_profile", 
					new SQLParamDescriptorSet( param ), "ProfilDAO", "getUsers", contexte.getContexteAppelDAO(), 
					new SQLResultSetReader() {
						public Object instanciateFromLine( ResultSet rs ) throws SQLException {
							UserLight u = new UserLight();
							u.setLogin(  StringUtils.trimToEmpty( rs.getString( "login" ) ) );
							u.setFirstName( StringUtils.trimToEmpty( rs.getString( "nom" ) ) );
							u.setLastName( StringUtils.trimToEmpty( rs.getString( "prenom" ) ) );
							u.setCivility( StringUtils.trimToEmpty( rs.getString( "civilite" ) ) );
							return u;
						}
					}
			);
			
		} catch( SQLException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "ProfilDAO", "getUsers", e.getMessage(), e );
			throw new TechnicalException(e);
		}
		return users;
	}
	
	
    
    /***
     * Methode de recuperation des profils
     * @param withDeleted
     * @param contexte
     * @return List<Profil>
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	private List<Profil> initProfils( final boolean withDeleted, final Contexte contexte ) throws TechnicalException {
    	
    	List<Profil> profils = null;
    	try {
            SQLParamDescriptor param = new SQLParamDescriptor( new Boolean( withDeleted ), false, Types.BIT );
    		
            final List<Role> roles = RoleDAO.getInstance().getRolesAdmin(contexte);

            profils = (List<Profil>) SQLCallExecuter.getInstance().executeSelectProc("proc_sel_profile",
                    new SQLParamDescriptorSet( param ), "HabilitationDAO", "initProfils",
                    contexte.getContexteAppelDAO(), new SQLResultSetReader() {
                		public Object instanciateFromLine(ResultSet rs) throws SQLException {
                			Profil p = new Profil();
                			p.setCode( StringUtils.trimToEmpty( rs.getString("code_profile") ) );
                			p.setLibelle(rs.getString("libelle"));

                			String code = rs.getString("code_role");
                			if( StringUtils.isNotEmpty(code) )
                				p.setRole( getRole( code, roles ) );

                			code = rs.getString("code_role_vente");
                			if( StringUtils.isNotEmpty(code) )
                				p.setRoleVente( getRole( code, roles ) );

                			p.setDeleted( rs.getBoolean( "supprimer" ) );
                			p.setTarifLight( rs.getBoolean( "is_profile_tarif_light" ) );
                			return p;
                		}
            		}
            );

            Profil p;
            List<AxeVisibilite> axes; 
            List<AxeVisibilite> axesVente, otherAxes;
            for( int i=0, nbProfils=profils.size(); i<nbProfils; i++ ) {
                //Recuperation des axes associes au profil
                p = profils.get(i);
                axes = VisibiliteDAO.getInstance().getListeAxesProfil( p.getCode(), contexte );
                if( axes != null && axes.size() > 0 ) {
                	axesVente = new ArrayList<AxeVisibilite>();
                	otherAxes = new ArrayList<AxeVisibilite>();
                	for( int j=0, nbAxes=axes.size(); j<nbAxes; j++ ) {
                		if( axes.get(j).isAxeVente() )
                			axesVente.add( axes.get(j) );
                		else
                			otherAxes.add( axes.get(j) );
                	}
                    if( axesVente.size()>0 )
                    	p.setAxesVente( axesVente );
                    if( otherAxes.size()>0 )
                    	p.setAxesTarif( otherAxes );
                }
                profils.set(i, p);
            }

        } catch (SQLException e) {
            LogCommun.major( contexte.getCodeUtilisateur(), "ProfilDAO", "initProfils", e.getMessage(), e );
            throw new TechnicalException(e);
        }
        return profils;
    }

    /***
     * Methode d'ajout d'une liste d'axes de visibilite sur un profil
     * @param codeProfil
     * @param codeAxe
     * @param contexte
     * @throws TechnicalException
     */
    private void insertProfilAxes( final String codeProfil, final List<AxeVisibilite> axes, 
    		final Contexte contexte ) throws TechnicalException {
		if( axes != null ) {
			AxeVisibilite av;
			for( int i=0, size=axes.size(); i<size; i++ ) {
				av = axes.get(i);
				insertProfilAxe( codeProfil, av.getCode(), contexte );
			}
		}
    }
    
    /***
     * Methode d'ajout d'un axe de visibilite sur un profil
     * @param codeProfil
     * @param codeAxe
     * @param contexte
     * @throws TechnicalException
     */
    private void insertProfilAxe( final String codeProfil, final String codeAxe, final Contexte contexte ) 
    		throws TechnicalException {
    	
    	try {
    		SQLParamDescriptor[] params = new SQLParamDescriptor[] {
    				new SQLParamDescriptor( codeProfil, false, Types.CHAR ),
    				new SQLParamDescriptor( codeAxe, false, Types.SMALLINT )
    		};
    		
    		SQLCallExecuter.getInstance().executeUpdate( "admin_insert_profile_axe" , 
    				new SQLParamDescriptorSet( params ), "ProfilDAO", "insertProfilAxe", 
    				contexte.getContexteAppelDAO() );
    		
        } catch (SQLException e) {
            LogCommun.major( contexte.getCodeUtilisateur(), "ProfilDAO", "insertProfilAxe", e.getMessage(), e );
            throw new TechnicalException(e);
        }
    }


    /***
     * Methode de suppression des axes de visibilite d'un profil
     * @param codeProfil
     * @param contexte
     * @throws TechnicalException
     */
    private void deleteProfilAxe( final String codeProfil, final Contexte contexte ) 
    		throws TechnicalException {
    	
    	try {
    		SQLParamDescriptor param = new SQLParamDescriptor( codeProfil, false, Types.CHAR );
    		
    		SQLCallExecuter.getInstance().executeUpdate( "admin_delete_profile_axe" , 
    				new SQLParamDescriptorSet( param ), "ProfilDAO", "deleteProfilAxe", 
    				contexte.getContexteAppelDAO() );
    		
        } catch (SQLException e) {
            LogCommun.major( contexte.getCodeUtilisateur(), "ProfilDAO", "deleteProfilAxe", e.getMessage(), e );
            throw new TechnicalException(e);
        }
    }
    
    private Role getRole( final String codeRole, final List<Role> roles ) {
    	if( roles != null ) {
    		for( int i=0, size=roles.size(); i<size; i++ ) {
    			if( StringUtils.equals( ( (Role) roles.get( i ) ).getCode(), codeRole ) ) {
    				return (Role) roles.get( i );
    			}
    		}
    	}
    	return null;
    }
}
