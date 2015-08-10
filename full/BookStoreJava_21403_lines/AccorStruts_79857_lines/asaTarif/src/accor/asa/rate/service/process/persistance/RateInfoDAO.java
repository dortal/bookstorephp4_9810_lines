/**
 *
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
package com.accor.asa.rate.service.process.persistance;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.metier.ratelevel.RateLevel;
import com.accor.asa.commun.persistance.SQLCallExecuter;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.reference.metier.FamilleTarifRefBean;
import com.accor.asa.commun.reference.metier.PeriodeValiditeRefBean;
import com.accor.asa.commun.reference.metier.StatutGrilleRefBean;
import com.accor.asa.rate.RatesTechnicalException;
import com.accor.asa.rate.model.GrilleBean;
import com.accor.asa.rate.model.RateContractInfo;
import com.accor.asa.vente.commun.log.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

public class RateInfoDAO {
    private static RateInfoDAO ourInstance = new RateInfoDAO();

    public static RateInfoDAO getInstance() {
        return ourInstance;
    }

    private RateInfoDAO() {
    }


    //================================  RATES INFOS  ========================================
   

    /**
     * Retourne la liste des tarifs d'un hotel, d'une famille de tarif
     * et d'une periode de validite
     * @param codeHotel
     * @param idFamilleTarif
     * @param idPeriodeValidite
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	public List<RateLevel> getRateLevelsList( String codeAsaCategory, int idFamilleTarif, int idPeriodeValidite, Contexte contexte )
            throws RatesTechnicalException{
        try {
            return (List<RateLevel>)SQLCallExecuter.getInstance().executeSelectProc(
                    "tarif_selRateLevelsForGrille",
                    new SQLParamDescriptorSet(new SQLParamDescriptor[]{
                            new SQLParamDescriptor(codeAsaCategory, false, Types.VARCHAR),
                            new SQLParamDescriptor(idFamilleTarif, false, Types.INTEGER),
                            new SQLParamDescriptor(idPeriodeValidite, false, Types.INTEGER),
                            new SQLParamDescriptor(contexte.getCodeLangue(), false, Types.VARCHAR)
                    }),
                    "RateInfoDAO", "getListRateLevels",
                    contexte.getContexteAppelDAO(),
                    new SQLResultSetReader() {
                        public Object instanciateFromLine(ResultSet rs) throws SQLException {
                            RateLevel rl= new RateLevel();
                            rl.setCode(rs.getString("code"));
                             rl.setLibelle(rs.getString("nom"));
                             rl.setCodeTypeTarif(rs.getString("type"));
                             return rl;               
                        }
                    }
            );
        } catch (SQLException e) {
            Log.major(contexte.getCodeUtilisateur(), "RateInfoDAO", "getRateLevelsList",
                    "echec lors de la recuperation de la liste des ratelevels pour asacategory: "+codeAsaCategory, e);
            throw new RatesTechnicalException(e);
        }catch(TechnicalException e){
        	throw new RatesTechnicalException(e);
        }
    }

    /**
     * Retourne la periode de validite en cours ou passée
     * @param codeHotel
     * @param idGroupeTarif
     * @param validite
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public PeriodeValiditeRefBean getPeriodeValidite( String codeHotel, int idGroupeTarif, int validite, Contexte contexte )
            throws RatesTechnicalException {
        try {
            return (PeriodeValiditeRefBean) SQLCallExecuter.getInstance().executeSelectSingleObjetProc(
                    "tarif_getPeriodeValidite",
                    new SQLParamDescriptorSet(new SQLParamDescriptor[]{
                            new SQLParamDescriptor(codeHotel, false, Types.VARCHAR),
                            new SQLParamDescriptor(idGroupeTarif, false, Types.INTEGER),
                            new SQLParamDescriptor(validite, false, Types.INTEGER)
                    }),
                    "RateInfoDAO", "getPeriodeValidite",
                    contexte.getContexteAppelDAO(),
                    new SQLResultSetReader() {
                        public Object instanciateFromLine(ResultSet rs) throws SQLException {
                            PeriodeValiditeRefBean pv = new PeriodeValiditeRefBean();
                            pv.setCode(String.valueOf(rs.getInt("idPeriodeValidite")));
                            pv.setDateDebut(rs.getDate("dateDebut"));
                            pv.setDateFin(rs.getDate("dateFin"));
                            pv.setIdGroupeTarif(rs.getInt("idGroupeTarif"));
                            return pv;
                        }
                    }
            );
        } catch (SQLException e) {
            Log.major(contexte.getCodeUtilisateur(), "RateInfoDAO", "getPeriodeValidite",
                    "echec lors de la recuperation de la periode de validite de l'hotel/GT/Validite: "+codeHotel+"/"+idGroupeTarif+"/"+validite, e);
            throw new RatesTechnicalException(e);
        }catch(TechnicalException e){
        	 throw new RatesTechnicalException(e);
        }
    }

    /**
     * Construire l'objet à partir d'une lignes
     * @param rs
     * @return
     * @throws SQLException
     */
    private GrilleBean getGrilleBeanObject(ResultSet rs) throws SQLException  {
        return new GrilleBean(
                rs.getLong("idSeq"),
                rs.getString("codeHotel"),
                rs.getInt("idPeriodeValidite"),
                rs.getInt("idFamilleTarif"),
                rs.getInt("idStatut"),
                rs.getDate("dateCreation"),
                rs.getString("loginCreation"),
                rs.getDate("dateAddCXX"),
                rs.getString("loginAddCXX"),
                rs.getDate("dateLastModif"),
                rs.getString("loginLastModif"));
    }
    /**
     * Retourne la grille d'un hotel pour une periode de validite
     * et une famille de tarif
     * @param codeHotel
     * @param idPeriodeValidite
     * @param idFamilleTarif
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public GrilleBean getGrille( String codeHotel, int idPeriodeValidite, int idFamilleTarif, Contexte contexte )
            throws RatesTechnicalException {
        try {
            return (GrilleBean) SQLCallExecuter.getInstance().executeSelectSingleObjetProc(
                    "tarif_getGrille",
                    new SQLParamDescriptorSet(new SQLParamDescriptor[]{
                            new SQLParamDescriptor(codeHotel, false, Types.VARCHAR),
                            new SQLParamDescriptor(idPeriodeValidite, false, Types.INTEGER),
                            new SQLParamDescriptor(idFamilleTarif, false, Types.INTEGER)
                    }),
                    "RateInfoDAO", "getGrille",
                    contexte.getContexteAppelDAO(),
                    new SQLResultSetReader() {
                        public Object instanciateFromLine(ResultSet rs) throws SQLException {
                            return getGrilleBeanObject(rs);
                        }
                    }
            );
        } catch (SQLException e) {
            Log.major(contexte.getCodeUtilisateur(), "RateInfoDAO", "getGrille",
                    "echec lors de la recuperation de la grille de l'hotel/PV/FT: "+codeHotel+"/"+idPeriodeValidite+"/"+idFamilleTarif, e);
            throw new RatesTechnicalException(e);
        }catch(TechnicalException e){
        	throw new RatesTechnicalException(e);
        }
    }

    /**
     * Retourne une grille par son id
     * @param idGrille
     * @param contexte
     * @return
     * @throws RatesTechnicalException
     */
    public GrilleBean getGrilleById( long idGrille, Contexte contexte )
            throws RatesTechnicalException {
        try {
            return (GrilleBean) SQLCallExecuter.getInstance().executeSelectSingleObjetProc(
                    "tarif_getGrilleById",
                    new SQLParamDescriptorSet(new SQLParamDescriptor(idGrille, false, Types.NUMERIC)),
                    "RateInfoDAO", "getGrilleById",
                    contexte.getContexteAppelDAO(),
                    new SQLResultSetReader() {
                        public Object instanciateFromLine(ResultSet rs) throws SQLException {
                            return getGrilleBeanObject(rs);
                        }
                    }
            );
        } catch (SQLException e) {
            Log.major(contexte.getCodeUtilisateur(), "RateInfoDAO", "getGrilleById",
                    "echec lors de la recuperation de la grille : "+idGrille, e);
            throw new RatesTechnicalException(e);
        }catch(TechnicalException e){
        	throw new RatesTechnicalException(e);
        }
    }

    public List<GrilleBean> getGrilles (String codeHotel, int idGroupeTarif, int idPeriodeValidite, Contexte contexte) throws RatesTechnicalException {
		try {
			return (List<GrilleBean>) SQLCallExecuter.getInstance().executeSelectProc(
					"tarif_selGrilles",
					new SQLParamDescriptorSet(new SQLParamDescriptor[]{
							new SQLParamDescriptor(codeHotel, false, Types.VARCHAR),
							new SQLParamDescriptor(idGroupeTarif, false, Types.INTEGER),
							new SQLParamDescriptor(idPeriodeValidite, false, Types.INTEGER),
							new SQLParamDescriptor(contexte.getCodeLangue(), false, Types.CHAR)
					}),
					"RateInfoDAO", "getGrilles",
					contexte.getContexteAppelDAO(),
					new SQLResultSetReader() {
						public Object instanciateFromLine(ResultSet rs) throws SQLException {
							GrilleBean grille = new GrilleBean();
							grille.setIdGrille(rs.getLong("idSeq"));
							grille.setCodeHotel(rs.getString("codeHotel"));
							grille.setIdPeriodeValidite(rs.getInt("idPeriodeValidite"));
							grille.setIdFamilleTarif(rs.getInt("idFamilleTarif"));
							grille.setIdStatut(rs.getInt("idStatut"));
							FamilleTarifRefBean familleTarif = new FamilleTarifRefBean();
							familleTarif.setId(rs.getString("idFamilleTarif"));
							familleTarif.setLibelle(rs.getString("libelleFamilleTarif"));
							grille.setFamilleTarif(familleTarif);
							StatutGrilleRefBean statutGrille = new StatutGrilleRefBean();
							statutGrille.setId(rs.getString("idStatut"));
							statutGrille.setLibelle(rs.getString("libelleStatut"));
							grille.setStatutGrille(statutGrille);
							return grille;
						}
					}
			);
		}
		catch (SQLException ex) {
			Log.major(contexte.getCodeUtilisateur(), "RateInfoDAO", "getGrilles",
					"Echec lors de la recuperation de la liste des grilles pour l'hotel=" + codeHotel, ex);
			throw new RatesTechnicalException(ex);
		}
		catch (TechnicalException ex){
			Log.major(contexte.getCodeUtilisateur(), "RateInfoDAO", "getGrilles",
					"Echec lors de la recuperation de la liste des grilles pour l'hotel=" + codeHotel, ex);
			throw new RatesTechnicalException(ex);
		}
	}

    /**
     * Creer une nouvelle grille
     * @param grille
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public long addGrille(String codeHotel, int idPeriodeValidite,int idFamilleTarif, Contexte contexte)
            throws RatesTechnicalException {
        try {
            return (long)SQLCallExecuter.getInstance().executeUpdateWithReturnCode(
                    "tarif_addGrille",
                    new SQLParamDescriptorSet( new SQLParamDescriptor[]{
                            new SQLParamDescriptor(null, true, Types.NUMERIC),
                            new SQLParamDescriptor(codeHotel, Types.VARCHAR),
                            new SQLParamDescriptor(idPeriodeValidite, Types.INTEGER),
                            new SQLParamDescriptor(idFamilleTarif, Types.INTEGER),
                            new SQLParamDescriptor(contexte.getCodeUtilisateur(), Types.VARCHAR)}),
                    "RateInfoDAO",
                    "addGrille",
                    contexte.getContexteAppelDAO());
        } catch(SQLException e) {
            Log.major(contexte.getCodeUtilisateur(), "RateInfoDAO", "addGrille",
                    "echec lors de la sauvegarde de la grille: " + codeHotel+"/"+idPeriodeValidite+"/"+idFamilleTarif);
            throw new RatesTechnicalException(e);
        }
        catch(TechnicalException e){
        	throw new RatesTechnicalException(e);
        }
    }

    /**
     * Maj de l'historique grille
     * @param idGrille
     * @param codeRateLevel
     * @param contexte
     * @throws RatesTechnicalException
     */
    public void updateHistoGrille(long idGrille, String codeRateLevel, Contexte contexte)
            throws RatesTechnicalException {
        try {
            SQLCallExecuter.getInstance().executeUpdate(
                    "tarif_updateHistoGrille",
                    new SQLParamDescriptorSet( new SQLParamDescriptor[]{
                            new SQLParamDescriptor(idGrille, Types.NUMERIC),
                            new SQLParamDescriptor(codeRateLevel, Types.VARCHAR),
                            new SQLParamDescriptor(contexte.getCodeUtilisateur(), Types.VARCHAR)}),
                    "RateInfoDAO",
                    "updateHistoGrille",
                    contexte.getContexteAppelDAO());
        } catch(SQLException e) {
            Log.major(contexte.getCodeUtilisateur(), "RateInfoDAO", "updateHistoGrille",
                    "echec lors de la maj histo de la grille: " + idGrille+"/"+codeRateLevel);
            throw new RatesTechnicalException(e);
        } catch(TechnicalException e){
        	throw new RatesTechnicalException(e);
        }
    }

    
    @SuppressWarnings("unchecked")
	public List<RateContractInfo> getContractsPeriodes(long idGrille, Contexte contexte) throws RatesTechnicalException
    {
    	try {
            return (List<RateContractInfo>)SQLCallExecuter.getInstance().executeSelectProc(
                    "tarif_selContractedRateLevels",
                    new SQLParamDescriptorSet(new SQLParamDescriptor[]{
                            new SQLParamDescriptor(idGrille, false, Types.NUMERIC),
                            
                    }),
                    "RateInfoDAO", "getContractsPeriodes",
                    contexte.getContexteAppelDAO(),
                    new SQLResultSetReader() {
                        public Object instanciateFromLine(ResultSet rs) throws SQLException {
                        	RateContractInfo rl= new RateContractInfo();
                            rl.setCodeRateLevel(rs.getString("code_rate_level"));
                            rl.setDateDebut(rs.getDate("date_debut"));
                            rl.setDateFin(rs.getDate("date_fin"));
                             return rl;               
                        }
                    }
            );
        } catch (SQLException e) {
            Log.major(contexte.getCodeUtilisateur(), "RateInfoDAO", "getMaximalContracts",
                    "echec lors de la recuperation de la liste des contracts maximals: "+idGrille, e);
            throw new RatesTechnicalException(e);
        }catch(TechnicalException e){
        	throw new RatesTechnicalException(e);
        }
    }
    
    @SuppressWarnings("unchecked")
	public List<RateContractInfo> getContractsForRateLevel(long idGrille, String codeRateLevel, String status,Contexte contexte) throws RatesTechnicalException
    {
    	try {
            return (List<RateContractInfo>)SQLCallExecuter.getInstance().executeSelectProc(
                    "tarif_selContractsForGrille",
                    new SQLParamDescriptorSet(new SQLParamDescriptor[]{
                            new SQLParamDescriptor(idGrille, false, Types.NUMERIC),
                            new SQLParamDescriptor(codeRateLevel, false, Types.VARCHAR),
                            new SQLParamDescriptor(status, false, Types.VARCHAR),
                            new SQLParamDescriptor(contexte.getCodeLangue(), false, Types.VARCHAR), 
                    }),
                    "RateInfoDAO", "getContractsForRateLevel",
                    contexte.getContexteAppelDAO(),
                    new SQLResultSetReader() {
                        public Object instanciateFromLine(ResultSet rs) throws SQLException {
                        	RateContractInfo rl= new RateContractInfo();
                            rl.setCode(rs.getLong("code"));
                            rl.setDateDebut(rs.getDate("dateDebut"));
                            rl.setDateFin(rs.getDate("dateFin"));
                            rl.setOperation(rs.getString("operation"));
                            rl.setOwner(rs.getString("owner"));
                            rl.setStatus(rs.getString("statut"));
                             return rl;               
                        }
                    }
            );
        } catch (SQLException e) {
            Log.major(contexte.getCodeUtilisateur(), "RateInfoDAO", "getMaximalContracts",
                    "echec lors de la recuperation de la liste des contracts: "+idGrille+"_"+codeRateLevel, e);
            throw new RatesTechnicalException(e);
        }catch(TechnicalException e){
        	throw new RatesTechnicalException(e);
        }
    }

	public void updateStatutGrille (String codeHotel, int idGroupeTarif, int idPeriodeValidite, int idStatut, Contexte contexte) throws RatesTechnicalException {
		try {
			SQLCallExecuter.getInstance().executeUpdate(
					"tarif_updStatutGrille",
					new SQLParamDescriptorSet(new SQLParamDescriptor [] {
							new SQLParamDescriptor(codeHotel, Types.VARCHAR),
							new SQLParamDescriptor(idGroupeTarif, false, Types.SMALLINT),
							new SQLParamDescriptor(idPeriodeValidite, false, Types.SMALLINT),
							new SQLParamDescriptor(idStatut, false, Types.SMALLINT)
					}),
					"RateInfoDAO",
					"updateStatutGrille",
					contexte.getContexteAppelDAO());
		}
		catch (SQLException ex) {
			Log.major(contexte.getCodeUtilisateur(), "RateInfoDAO", "updateStatutGrille",
					"Echec lors de la MAJ du statut de l'hotel " + codeHotel);
			throw new RatesTechnicalException(ex);
		}
		catch (TechnicalException ex) {
			throw new RatesTechnicalException(ex);
		}
	}

    public void updateStatutGrille (long idGrille, int idStatut, Contexte contexte) throws RatesTechnicalException {
        try {
            SQLCallExecuter.getInstance().executeUpdate(
                    "tarif_updStatutGrilleById",
                    new SQLParamDescriptorSet(new SQLParamDescriptor [] {
                            new SQLParamDescriptor(idGrille, false, Types.NUMERIC),
                            new SQLParamDescriptor(idStatut, false, Types.SMALLINT)
                    }),
                    "RateInfoDAO",
                    "updateStatutGrille",
                    contexte.getContexteAppelDAO());
        }
        catch (SQLException ex) {
            Log.major(contexte.getCodeUtilisateur(), "RateInfoDAO", "updateStatutGrille",
                    "Echec lors de la MAJ du statut de la grille " + idGrille);
            throw new RatesTechnicalException(ex);
        }
        catch (TechnicalException ex) {
            throw new RatesTechnicalException(ex);
        }
    }

}