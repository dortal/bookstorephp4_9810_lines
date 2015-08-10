package com.accor.asa.commun.habilitation.persistance;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CacheManager;
import com.accor.asa.commun.habilitation.metier.Droit;
import com.accor.asa.commun.habilitation.metier.InfoPonderation;
import com.accor.asa.commun.habilitation.metier.acces.Acces;
import com.accor.asa.commun.habilitation.metier.acces.AccesRoleMap;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.DAO;
import com.accor.asa.commun.persistance.SQLCallExecuter;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.persistance.TimestampException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.vente.commun.PoolVenteFactory;
import com.accor.asa.vente.commun.habilitation.metier.MapPonderation;

/**
 * <b>pattern</b>       Singleton
 *
 * @author D.DREISTADT
 *         <b>creation date</b>    22/06/2001
 */
public class HabilitationDAO extends DAO {
	// le singleton
	private static final HabilitationDAO _dao = new HabilitationDAO();

	/**
	 * put your documentation comment here
	 *
	 * @return
	 */
	public static HabilitationDAO getInstance() {
		return _dao;
	}

    /***
     * Renvoie la liste des ponderations
     * @param contexte
     * @return List<InfoPonderation>
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	private List<InfoPonderation> getAllPonderation( final Contexte contexte ) throws TechnicalException {
        List<InfoPonderation> infos;
        try {
        	infos = (List<InfoPonderation>) SQLCallExecuter.getInstance().executeSelectProcSansLimite(
                        "vente_Select_Ponderation",
                        new SQLParamDescriptorSet(), "HabilitationDAO", "getAllPonderation",
                        contexte.getContexteAppelDAO(), new SQLResultSetReader() {
                public Object instanciateFromLine(ResultSet rs) throws SQLException {
                    InfoPonderation obj = new InfoPonderation();
                    obj.setPonderation( rs.getInt( "ponderation" ) );
                    obj.setCodeAxe( rs.getInt( "codeAxe" ) );
                    obj.setCodeGroupeEcran( rs.getInt( "codeGroupeEcran" ) );
                    return obj;
                }
            });
        } catch (SQLException e) {
            LogCommun.major( contexte.getCodeUtilisateur(), "HabilitationDAO", "getAllPonderation", e.getMessage(), e );
            throw new TechnicalException(e);
        }
        return  infos;
    }

	/**
	 * récupère la pondération pour un couple (axe, groupe ecran),
	 * <br> voir table vente_axe_ponderation
	 * @param contexte
	 * @param codeAxe
	 * @param codeGroupeEcran
	 * @return la pondération pour un coupe (axe, groupe ecran)
	 * @throws TechnicalException
	 */
	public Integer getPonderationPourAxe( final String codeAxe, final String codeGroupeEcran,
			final Contexte contexte ) throws TechnicalException {

		MapPonderation ponderations = (MapPonderation)
				CacheManager.getInstance().getObjectInCache( MapPonderation.class );

    	if( ponderations == null ) {
    		HashMap<String,Integer> ponderationsParAxeEtGroupe = new HashMap<String,Integer>();
    		List<InfoPonderation> listePonderations = getAllPonderation(contexte);
    		
    		// lecture du retour TC
    		if( listePonderations != null ) {
		        if (LogCommun.isTechniqueDebug()) {
		        	LogCommun.debug("HabilitationDAO", "getPonderationPourAxe", "" + listePonderations.size() + " infos trouvées");
		        }
		        
		        InfoPonderation pdr = null; 
		        String key = null;
		        for( int i=0, size=listePonderations.size(); i<size; i++ ) {
		        	pdr = listePonderations.get(i);
		        	key = buildClefPonderation( String.valueOf( pdr.getCodeAxe() ), 
		        			String.valueOf( pdr.getCodeGroupeEcran() ) );
		        	ponderationsParAxeEtGroupe.put( key, pdr.getPonderation() );
		        }
    		}
    		
    		if( ponderationsParAxeEtGroupe.isEmpty() )
    			ponderationsParAxeEtGroupe.put( "VIDE", null );
        
    		ponderations = new MapPonderation( ponderationsParAxeEtGroupe );
    		CacheManager.getInstance().setObjectInCache( ponderations );
    	}
    	return ponderations.getElements().get( 
    						buildClefPonderation( codeAxe, codeGroupeEcran ) );
    }

	private String buildClefPonderation( final String axe, final String groupeEcran ) {
		StringBuffer sb = new StringBuffer();
		sb.append( axe ).append( "_" ).append( groupeEcran );
		return sb.toString();
	}

	/***
	 * Methode d'enregistrement de la connexion Utilisateur
	 * @param login
	 * @param module
	 * @param contexte
	 * @throws TechnicalException
	 */
	public void connexion( final String login, final String module, final Contexte contexte )
			throws TechnicalException {

		try {
			SQLParamDescriptor[] params = new SQLParamDescriptor[] {
					new SQLParamDescriptor( login, false, Types.VARCHAR ),
					new SQLParamDescriptor( module, false, Types.VARCHAR ),
			};

			SQLCallExecuter.getInstance().executeUpdate( "asa_connexion_module_asav2",
					new SQLParamDescriptorSet( params ), "HabilitationDAO", "connexion",
					contexte.getContexteAppelDAO() );

		} catch (Exception e) {
			LogCommun.major( login, "HabilitationDAO", "connexion", e.getMessage(), e );
			throw new TechnicalException(e);
		}
	}

	/***
	 * Methode de suppression de la log de la connexion Utilisateur
	 * @param login
	 * @param module
	 * @param contexte
	 * @throws TechnicalException
	 */
	public void deconnexion( final String login, final String module, final Contexte contexte )
			throws TechnicalException {

		try {
			SQLParamDescriptor[] params = new SQLParamDescriptor[] {
					new SQLParamDescriptor( login, false, Types.VARCHAR ),
					new SQLParamDescriptor( module, false, Types.VARCHAR ),
			};

			SQLCallExecuter.getInstance().executeUpdate( "asa_deconnexion_module_asav2",
					new SQLParamDescriptorSet( params ), "HabilitationDAO", "deconnexion",
					contexte.getContexteAppelDAO() );

		} catch (Exception e) {
			LogCommun.major( login, "HabilitationDAO", "deconnexion", e.getMessage(), e );
			throw new TechnicalException(e);
		}
	}

	/**
	 * Vérifie si l'estampille n'a pas changé pour le compte passé en paramètre (Verrouillage optimiste)
	 *
	 * @param codeCompte
	 * @param timestamp
	 * @return nouveau timestamp
	 * @throws TimestampException si le timestamp a changé
	 * @throws TechnicalException
	 */
	public void checkVerrouOptimisteCompte( final Integer codeCompte, final String timestamp,
			final Contexte contexte ) throws TechnicalException, TimestampException {
		try {
			if( codeCompte != null )
				checkVerrouOptimiste( VERROU_SUR_COMPTE, codeCompte.longValue(), timestamp, contexte );
		} catch( SQLException e ) {
			if( isTimestampException( e ) ) {
	            throw new TimestampException(e, VERROU_SUR_COMPTE);
			} else {
				LogCommun.major( contexte.getCodeUtilisateur(), "HabilitationDAO", "checkVerrouOptimisteCompte", e.getMessage(), e );
	            throw new TechnicalException(e);
			}
		}
	}

	/**
	 * Vérifie si l'estampille n'a pas changé pour le compte passé en paramètre (Verrouillage optimiste)
	 *
	 * @param codeContact
	 * @param timestamp
	 * @return nouveau timestamp
	 * @throws TimestampException si le timestamp a changé
	 * @throws TechnicalException
	 */
	public void checkVerrouOptimisteContact( final Integer codeContact, final String timestamp,
			final Contexte contexte ) throws TechnicalException, TimestampException {
		try {
			if( codeContact != null )
				checkVerrouOptimiste( VERROU_SUR_CONTACT, codeContact.longValue(), timestamp, contexte );
		} catch( SQLException e ) {
			if( isTimestampException( e ) ) {
	            throw new TimestampException(e, VERROU_SUR_CONTACT);
			} else {
				LogCommun.major( contexte.getCodeUtilisateur(), "HabilitationDAO", "checkVerrouOptimisteContact", e.getMessage(), e );
	            throw new TechnicalException(e);
			}
		}
	}

	/**
	 * Vérifie si l'estampille n'a pas changé pour le compte passé en paramètre (Verrouillage optimiste)
	 *
	 * @param codeDossier
	 * @param timestamp
	 * @return nouveau timestamp
	 * @throws TimestampException si le timestamp a changé
	 * @throws TechnicalException
	 */
	public void checkVerrouOptimisteDossier( final Long codeDossier, final String timestamp,
			final Contexte contexte ) throws TechnicalException, TimestampException {
		try {
			if( codeDossier != null )
				checkVerrouOptimiste( VERROU_SUR_DOSSIER, codeDossier.longValue(), timestamp, contexte );
		} catch( SQLException e ) {
			if( isTimestampException( e ) ) {
	            throw new TimestampException(e, VERROU_SUR_DOSSIER);
	        } else {
				LogCommun.major( contexte.getCodeUtilisateur(), "HabilitationDAO", "checkVerrouOptimisteDossier", e.getMessage(), e );
	            throw new TechnicalException(e);
	        }
		}
	}

	/**
	 * Vérifie si l'estampille n'a pas changé pour l'objet maître (Verrouillage optimiste)
	 *
	 * @param codeObjetMaitre
	 * @param code
	 * @param timestamp
	 * @param contexte
	 * @throws TimestampException si le timestamp a changé
	 * @throws TechnicalException
	 */
	private void checkVerrouOptimiste( final int codeObjetMaitre, final long code,
			final String timestamp, final Contexte contexte ) throws SQLException, TechnicalException {

		Connection cnt = null;
		Statement st = null;

		try {
			cnt = PoolVenteFactory.getInstance().getConnection();
			st = cnt.createStatement();

			StringBuffer sql = new StringBuffer();
			String nomTable = null;
			String nomID = null;
			String colonneAMettreAJour = null;

			switch (codeObjetMaitre) {
				case VERROU_SUR_COMPTE:
					nomTable = "compte_accor";
					nomID = "idseq";
					colonneAMettreAJour = "flagexport";
					break;
				case VERROU_SUR_CONTACT:
					nomTable = "interlocuteur_externe";
					nomID = "id_interlocuteur";
					colonneAMettreAJour = "id_role";
					break;
				case VERROU_SUR_DOSSIER:
					nomTable = "vente_dossier_offre";
					nomID = "code";
					colonneAMettreAJour = "flagexport";
					break;
			}

			sql.append("update ");
			sql.append(nomTable);
			sql.append(" set ");
			sql.append(colonneAMettreAJour);
			sql.append(" = ");
			sql.append(colonneAMettreAJour);
			sql.append(" where ");
			sql.append(nomID);
			sql.append(" = ");
			sql.append(code);
			if ((timestamp != null) && (timestamp.trim().length() > 0)) {
				sql.append(" and tsequal(TIMESTAMP, 0x");
				sql.append(timestamp);
				sql.append(")");
			} else {
				sql.append(" and tsequal(TIMESTAMP, NULL)");
			}

			if (LogCommun.isTechniqueDebug()) {
				LogCommun.debug("HabilitationDAO", "checkVerrouOptimiste", sql.toString());
			}

			st.executeUpdate(sql.toString());
			if (LogCommun.isFonctionelleDebug()) {
				LogCommun.traceFonctionnelle(contexte.getCodeUtilisateur(), nomTable, "UPD", nomID);
			}

		} catch( SQLException e ) {
			throw e;
		} catch (Exception e) {
			LogCommun.major(contexte.getCodeUtilisateur(), "HabilitationDAO", "checkVerrouOptimiste", e.getMessage(), e);
			throw new TechnicalException(e.getMessage());
		} finally {
			releaseConnection(cnt, st);
		}
	}


	/**
	 * Renvoie l'estampille du compte passé en paramètre (Verrouillage optimiste)
	 * Doit être appelée avant la lecture en base
	 *
	 * @param codeCompte
	 * @return
	 * @throws TechnicalException
	 */
	public String getVerrouOptimisteCompte( final Integer codeCompte, final Contexte contexte )
			throws TechnicalException {
		try {
			if( codeCompte != null )
				return getVerrouOptimiste( "vente_verrouillage_compte", codeCompte.longValue(), contexte );
		} catch( TechnicalException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "HabilitationDAO", "getVerrouOptimisteCompte", "", e );
			throw new TechnicalException(e);
		}
		return null;
	}

	/**
	 * Renvoie l'estampille du contact passé en paramètre (Verrouillage optimiste)
	 * Doit être appelée avant la lecture en base
	 *
	 * @param codeContact
	 * @return
	 * @throws TechnicalException
	 */
	public String getVerrouOptimisteContact( final Integer codeContact, final Contexte contexte )
			throws TechnicalException {
		try {
			if( codeContact != null )
				return getVerrouOptimiste( "vente_verrouillage_contact", codeContact.longValue(), contexte );
		} catch( TechnicalException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "HabilitationDAO", "getVerrouOptimisteContact", "", e );
			throw new TechnicalException(e);
		}
		return null;
	}

	/**
	 * Renvoie l'estampille du dossier passé en paramètre (Verrouillage optimiste)
	 * Doit être appelée avant la lecture en base
	 * @param codeDossier
	 * @return
	 * @throws TechnicalException
	 */
	public String getVerrouOptimisteDossier( final Long codeDossier, final Contexte contexte )
			throws TechnicalException {
		try {
			if( codeDossier != null )
				return getVerrouOptimiste( "vente_verrouillage_dossier", codeDossier.longValue(), contexte );
		} catch( TechnicalException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "HabilitationDAO", "getVerrouOptimisteDossier", "", e );
			throw new TechnicalException(e);
		}
		return null;
	}

	/**
	 * Renvoie l'estampille de l'hotel passé en paramètre (Verrouillage optimiste)
	 * Doit être appelée avant la lecture en base
	 * @param codeDossier
	 * @return
	 * @throws TechnicalException
	 */
	public String getVerrouOptimisteHotel( final String codeHotel, final Contexte contexte )
			throws TechnicalException{
		try
		{
			SQLParamDescriptorSet params = new SQLParamDescriptorSet(new SQLParamDescriptor [] {
				new SQLParamDescriptor( codeHotel, false, Types.CHAR ),
				new SQLParamDescriptor( contexte.getCodeUtilisateur(), false, Types.VARCHAR )
			});

			String timestamp = (String) SQLCallExecuter.getInstance()
				.executeSelectSingleObjetProc("proc_sel_timestamp_tarif_hotel", params,
				"HabilitationDAO", "getVerrouOptimisteHotel", contexte.getContexteAppelDAO(),
				new SQLResultSetReader() {
					public Object instanciateFromLine(ResultSet rs) throws SQLException {
						return rs.getString("timestamp_value");
					}
				}
			);
			return timestamp;

		} catch( SQLException e ) {
			LogCommun.major(contexte.getCodeUtilisateur(), "HabilitationDAO", "getVerrouOptimisteHotel", e.getMessage(), e);
			throw new TechnicalException(e);
		}
	}

	/***
	 * Methode de mise a jour de l estampille de l'hotel passé en paramètre (Verrouillage optimiste)
	 * @param codeHotel
	 * @param contexte
	 * @throws TimestampException
	 */
	public void updateTimestampHotel( final String codeHotel, final Contexte contexte )
			throws TimestampException {

		try {
			if( LogCommun.isTechniqueDebug() )
				LogCommun.debug( "HabilitationDAO", "updateTimestampHotel",
						"timestap avant update: " + contexte.getTimestampHotel() );

			SQLParamDescriptorSet params = new SQLParamDescriptorSet(new SQLParamDescriptor [] {
				  new SQLParamDescriptor( codeHotel, false, Types.CHAR ),
				  new SQLParamDescriptor( contexte.getTimestampHotel(), false, Types.VARCHAR ),
				  new SQLParamDescriptor( contexte.getCodeUtilisateur(), false, Types.VARCHAR )
			});

			String newTimestamp = (String) SQLCallExecuter.getInstance()
				.executeSelectSingleObjetProc( "proc_upd_timestamp_tarif_hotel", params,
				"HabilitationDAO", "updateTimestampHotel", contexte.getContexteAppelDAO(),
				new SQLResultSetReader() {
					public Object instanciateFromLine(ResultSet rs) throws  SQLException {
						return rs.getString("timestamp_value");
					}
				}
			);

			if( LogCommun.isTechniqueDebug() )
				LogCommun.debug( "HabilitationDAO", "updateTarifHotel", "nouveau timestamp : " + newTimestamp );

			if( newTimestamp == null )
				throw new TimestampException( "L'hotel " + codeHotel + " a été modifié par un autre utilisateur." );

			contexte.setTimestampHotel( newTimestamp );

		} catch( TechnicalException te ) {
			String message = "Erreur d'accès à la bas lors de la vérification du timestamp hotel : " + te.getMessage();
			LogCommun.major( contexte.getCodeUtilisateur(), "HabilitationDAO", "updateTimestampHotel", message, te );
			throw new RuntimeException( message );
		} catch (SQLException sqle) {
			String message = "Erreur d'accès à la bas lors de la vérification du timestamp hotel : " + sqle.getMessage();
			LogCommun.major( contexte.getCodeUtilisateur(), "HabilitationDAO", "updateTimestampHotel", message, sqle );
			throw new RuntimeException(message);
		}
	}

	/***
	 * Renvoie l'estampille de l objet maitre associe ( Compte, Contact, Dossier )
	 * @param nomProc
	 * @param code
	 * @param contexte
	 * @return
	 * @throws TechnicalException
	 */
	private String getVerrouOptimiste( final String nomProc, final long code, final Contexte contexte )
			throws TechnicalException {

		String timestamp = null;

		try {
			SQLParamDescriptor param = new SQLParamDescriptor( new Long( code ), false, Types.NUMERIC );

			timestamp = (String) SQLCallExecuter.getInstance().executeSelectSingleObjetProc( nomProc ,
					new SQLParamDescriptorSet( param ), "HabilitationDAO", "getVerrouOptimiste",
					contexte.getContexteAppelDAO(),
					new SQLResultSetReader() {
						public Object instanciateFromLine( ResultSet rs ) throws SQLException {
							return rs.getString("TIMESTAMP");
						}
					}
			);

			if (LogCommun.isFonctionelleDebug()) {
				LogCommun.traceFonctionnelle(contexte.getCodeUtilisateur(), "Insert verrou optimiste", nomProc, "key:" + code);
			}
		} catch( SQLException e ) {
			LogCommun.major(contexte.getCodeUtilisateur(), "HabilitationDAO", "getVerrouOptimiste", e.getMessage(), e);
			throw new TechnicalException(e.getMessage());
		}
		return timestamp;
	}

    /***
     * Renvoie la liste des droits d'acces sur chaque groupe_ecran par code_role et code_axe
     * 		( table ACCES )
     * @param contexte
     * @return List<Acces>
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	public List<Acces> getAllAcces( final Contexte contexte ) throws TechnicalException {
    	List<Acces> acces;
        try {
        	acces = (List<Acces>) SQLCallExecuter.getInstance().executeSelectProcSansLimite( 
        				"proc_get_all_acces", new SQLParamDescriptorSet(), "HabilitationDAO",
                        "getAllAcces", contexte.getContexteAppelDAO(), new SQLResultSetReader() {
                
        		public Object instanciateFromLine(ResultSet rs) throws SQLException {
                    Acces a = new Acces();
                    a.setCodeRole( StringUtils.trimToEmpty( rs.getString( "codeRole" ) ) );
                    a.setCodeAxe( StringUtils.trimToEmpty( rs.getString( "codeAxe" ) ) );
                    a.setCodeGroupeEcran( StringUtils.trimToEmpty( rs.getString( "codeGroupeEcran" ) ) );
                    a.setDroit( Droit.getDroitForCode( StringUtils.trimToEmpty( rs.getString( "droit" ) ) ) );
                    
                    int regle = rs.getInt( "regle" );
                    if( ! rs.wasNull() )
                    	a.setRegle( regle );
                    return a;
                }
            });
        } catch (SQLException e) {
        	LogCommun.major( contexte.getCodeUtilisateur(), "HabilitationDAO", "getAllAcces", e.getMessage(), e );
            throw new TechnicalException(e);
        }
        return  acces;
    }

	/***
	 * Renvoie une Map contenant les droits associes a chaque Axe*groupeEcran pour un Role
	 * 		( table ACCES )
	 * @param codeRole
	 * @param contexte
	 * @return AccesRoleMap
	 * @throws TechnicalException
	 */
	 @SuppressWarnings("unchecked")
	public AccesRoleMap getAccesByRole( final String codeRole, final Contexte contexte ) 
	 		throws TechnicalException {

		AccesRoleMap accesMap = new AccesRoleMap();

		try {
            SQLParamDescriptor param = new SQLParamDescriptor(new Integer(codeRole),false, Types.INTEGER );
        	
        	List<Acces> list = (List<Acces>) SQLCallExecuter.getInstance().executeSelectProc ( 
        			"admin_get_acces_by_role",
                    new SQLParamDescriptorSet( param ), "HabilitationDAO", "getAccesByRole",
                    contexte.getContexteAppelDAO(), new SQLResultSetReader() {
                    public Object instanciateFromLine(ResultSet rs) throws SQLException {
                        Acces obj = new Acces();
                        obj.setCodeRole( StringUtils.trimToEmpty( rs.getString( "codeRole" ) ) );
                        obj.setCodeAxe( StringUtils.trimToEmpty( rs.getString( "codeAxe" ) ) );
                        obj.setCodeGroupeEcran( StringUtils.trimToEmpty( rs.getString( "codeGroupeEcran" ) ) );
                        obj.setDroit( Droit.getDroitForCode( rs.getString( "droit" ) ) );
                        obj.setRegle(rs.getString("regle") == null ? null : new Integer(rs.getInt( "regle" )) );
                        return obj;
                    } } );
            
        	if( list!=null && list.size()>0 ) {
                Acces acces;
                StringBuffer cleAcces = new StringBuffer();
                for (int i=0, size=list.size(); i<size; i++) {
                    acces = (Acces) list.get(i);
                    cleAcces = new StringBuffer();
                    cleAcces.append( acces.getCodeRole() ).append( "|" )
                    		.append( acces.getCodeAxe() ).append( "|" )
                    		.append( acces.getCodeGroupeEcran() );
                    accesMap.put( cleAcces.toString(), acces.getDroit(), acces.getRegle() );
                }
            }
        } catch(SQLException e) {
        	LogCommun.major(contexte.getCodeUtilisateur(), "HabilitationDAO", "getAccesByRole", e.getMessage(), e );
            throw new TechnicalException(e);
        }
		return accesMap;
	}

	/**
	 * Sauvegarde les droits d'acces pour un role
	 * @param droitAcces
	 * @param contexte
	 * @exception TechnicalException
	 */
	 public void saveAccesByRole( final List<Acces> droitAcces, final Contexte contexte ) 
	 		throws TechnicalException {
		 
		Connection cnt = null;
		String codeUtilisateur = contexte.getCodeUtilisateur();
		StringBuffer key = new StringBuffer();
		StringBuffer value = new StringBuffer();

		try {
			if( droitAcces != null ) {
				cnt = PoolCommunFactory.getInstance().getConnection();
				String codeRole = droitAcces.get(0).getCodeRole();

				//Suppression des droits existants
//				SQLParamDescriptor param = new SQLParamDescriptor( new Integer( codeRole ), false, Types.INTEGER );

//				SQLCallExecuter.getInstance().executeUpdate( cnt, "admin_delete_acces_by_role",
//					 new SQLParamDescriptorSet( param ), "HabilitationDAO", "saveAccesByRole",
//					 contexte.getContexteAppelDAO() );

//				LogCommun.traceFonctionnelle( codeUtilisateur, "ACCES", "DEL", codeRole );

				SQLParamDescriptor[] params = new SQLParamDescriptor[5];
				params[0] = new SQLParamDescriptor( new Integer( codeRole ), false, Types.INTEGER );

				Acces a;
				for( int i=0, size=droitAcces.size(); i<size; i++ ) {
					a = droitAcces.get(i);

					params[1] = new SQLParamDescriptor( new Integer( a.getCodeAxe() ), false, Types.INTEGER );
					params[2] = new SQLParamDescriptor( new Integer( a.getCodeGroupeEcran() ), false, Types.INTEGER );
					params[3] = new SQLParamDescriptor( String.valueOf(a.getDroit().getCode()), false, Types.CHAR );
					params[4] = new SQLParamDescriptor( a.getRegle(), false, Types.TINYINT );

					SQLCallExecuter.getInstance().executeUpdate( cnt, "admin_insert_acces" ,
						 new SQLParamDescriptorSet( params ), "HabilitationDAO", "saveAccesByRole",
						 contexte.getContexteAppelDAO() );

					//Ajout des nouveaux droits
					key.delete( 0, key.length() );
					key.append( codeRole )
						.append( "_" ).append( a.getCodeAxe() )
						.append( "_" ).append( a.getCodeGroupeEcran() );
					value.delete( 0, value.length() );
					value.append( " Droit:" ).append( a.getDroit() ).append( " - Regle:" ).append( a.getRegle() );
					LogCommun.traceFonctionnelle( codeUtilisateur, "ACCES", "INS", key.toString(), value.toString() );
				}
			}
		} catch( Exception e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "HabilitationDAO", "saveAccesByRole", e.getMessage(), e );
			throw new TechnicalException(e);
		} finally {
			releaseConnection( cnt, null );
		}
	}

}