/**
 *
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
package com.accor.asa.rate.service.process.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.AsaDate;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.DAO;
import com.accor.asa.commun.persistance.SQLCallExecuter;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.rate.RatesTechnicalException;
import com.accor.asa.rate.RatesUserException;
import com.accor.asa.rate.common.Log;
import com.accor.asa.rate.model.TaxeBean;

public class TaxeDAO  extends DAO {
    private static TaxeDAO ourInstance = new TaxeDAO();

    private final static String PROC_SELTAXERLS = "tarif_selTaxeRLs";
    private final static String PROC_ADDTAXERL  = "tarif_addTaxeRL";

    private final static String PROC_SELTAXES   = "tarif_selTaxes";
    private final static String PROC_SELTAXE    = "tarif_selTaxe";
    private final static String PROC_ADDTAXE    = "tarif_addTaxe";
    private final static String PROC_DELTAXE    = "tarif_delTaxe";

    public static TaxeDAO getInstance() {
        return ourInstance;
    }

    private TaxeDAO() {
    }

    /**
     * Retourne la liste des RLS associee a une taxe
     * @param idTaxe
     * @param contexte
     * @return
     * @throws RatesTechnicalException
     */
    private List<String> getTaxeRLs(long idTaxe, Contexte contexte) throws RatesTechnicalException {
        try {
            return (List<String>) SQLCallExecuter.getInstance().executeSelectProc(
                    PROC_SELTAXERLS,
                    new SQLParamDescriptorSet(new SQLParamDescriptor(idTaxe, false, Types.NUMERIC)),
                    "TaxeDAO", "getTaxeRLs",
                    contexte.getContexteAppelDAO(),
                    new SQLResultSetReader() {
                        public Object instanciateFromLine(ResultSet rs) throws SQLException {
                             return rs.getString("codeRateLevel");
                        }
                    }
            );
        } catch (SQLException e) {
            Log.major( contexte.getCodeUtilisateur(), "TaxeDAO", "getTaxeRLs", e.getMessage() );
            throw new RatesTechnicalException(e);
        } catch (TechnicalException e) {
            Log.major( contexte.getCodeUtilisateur(), "TaxeDAO", "getTaxeRLs", e.getMessage() );
            throw new RatesTechnicalException(e);
        }
    }

    /**
     * Ajouter un RL associee a une taxe
     * @param idTaxe
     * @param contexte
     * @return
     * @throws RatesTechnicalException
     */
    private void addTaxeRL(long idTaxe, String codeRateLevel, Contexte contexte) throws RatesTechnicalException {
        try {
            SQLCallExecuter.getInstance().executeUpdate(
                    PROC_ADDTAXERL,
                    new SQLParamDescriptorSet(new SQLParamDescriptor[]{
                            new SQLParamDescriptor(idTaxe, false, Types.NUMERIC),
                            new SQLParamDescriptor(codeRateLevel, false, Types.VARCHAR)
                    }),
                    "TaxeDAO", "addTaxeRL",
                    contexte.getContexteAppelDAO());
        } catch (SQLException e) {
            Log.major( contexte.getCodeUtilisateur(), "TaxeDAO", "addTaxeRL", e.getMessage() );
            throw new RatesTechnicalException(e);
        } catch (TechnicalException e) {
            Log.major( contexte.getCodeUtilisateur(), "TaxeDAO", "addTaxeRL", e.getMessage() );
            throw new RatesTechnicalException(e);
        }
    }

    /**
     * Retourne la liste des taxes d'un hotel
     * @param codeHotel
     * @param contexte
     * @return
     * @throws RatesTechnicalException
     */
    public List<TaxeBean> getTaxes(String codeHotel, Contexte contexte) throws RatesTechnicalException {
        try {
            List<TaxeBean> res = (List<TaxeBean>) SQLCallExecuter.getInstance().executeSelectProc(
                    PROC_SELTAXES,
                    new SQLParamDescriptorSet(new SQLParamDescriptor[]{
                            new SQLParamDescriptor(codeHotel, false, Types.VARCHAR),
                            new SQLParamDescriptor(contexte.getCodeLangue(), false, Types.VARCHAR)
                    }),
                    "TaxeDAO", "getTaxes",
                    contexte.getContexteAppelDAO(),
                    new SQLResultSetReader() {
                        public Object instanciateFromLine(ResultSet rs) throws SQLException {
                            TaxeBean obj= new TaxeBean();
                            obj.setIdTaxe(rs.getLong("idTaxe"));
                            obj.setCodeHotel(rs.getString("codeHotel"));
                            obj.setCode(rs.getString("codeTaxe"));
                            obj.setNom(rs.getString("nomTaxe"));
                            obj.setDateDebut(rs.getDate("dateDebut"));
                            obj.setDateFin(rs.getDate("dateFin"));
                            obj.setInclus(rs.getBoolean("isInclus"));
                            obj.setMontant(rs.getDouble("montant"));
                            obj.setIdTypePrix(rs.getInt("idTypePrix"));
                            obj.setIdUniteTaxe(rs.getInt("idUniteTaxe"));
                            obj.setCodeDevise(rs.getString("codeDevise"));
                            obj.setIdStatut(rs.getInt("idStatut"));
                            obj.setSupprime(rs.getBoolean("supprime"));
                            return obj;
                        }
                    }
            );
            if (res!=null)
                for (TaxeBean taxe : res)
                    taxe.setListRateLevels(getTaxeRLs(taxe.getIdTaxe(), contexte));
            return res;
        } catch (SQLException e) {
            Log.major( contexte.getCodeUtilisateur(), "TaxeDAO", "getTaxes", e.getMessage() );
            throw new RatesTechnicalException(e);
        } catch (TechnicalException e) {
            Log.major( contexte.getCodeUtilisateur(), "TaxeDAO", "getTaxes", e.getMessage() );
            throw new RatesTechnicalException(e);
        }
    }

    /**
     * Retourne une taxe d'un hotel
     * @param codeHotel
     * @param contexte
     * @return
     * @throws RatesTechnicalException
     */
    public TaxeBean getTaxe(long idTaxe, Contexte contexte) throws RatesTechnicalException {
        try {
            TaxeBean taxe =  (TaxeBean) SQLCallExecuter.getInstance().executeSelectSingleObjetProc(
                    PROC_SELTAXE,
                    new SQLParamDescriptorSet(new SQLParamDescriptor[]{
                            new SQLParamDescriptor(idTaxe, false, Types.NUMERIC),
                            new SQLParamDescriptor(contexte.getCodeLangue(), false, Types.VARCHAR)
                    }),
                    "TaxeDAO", "getTaxe",
                    contexte.getContexteAppelDAO(),
                    new SQLResultSetReader() {
                        public Object instanciateFromLine(ResultSet rs) throws SQLException {
                            TaxeBean obj= new TaxeBean();
                            obj.setIdTaxe(rs.getLong("idTaxe"));
                            obj.setCodeHotel(rs.getString("codeHotel"));
                            obj.setCode(rs.getString("codeTaxe"));
                            obj.setNom(rs.getString("nomTaxe"));
                            obj.setDateDebut(rs.getDate("dateDebut"));
                            obj.setDateFin(rs.getDate("dateFin"));
                            obj.setInclus(rs.getBoolean("isInclus"));
                            obj.setMontant(rs.getDouble("montant"));
                            obj.setIdTypePrix(rs.getInt("idTypePrix"));
                            obj.setIdUniteTaxe(rs.getInt("idUniteTaxe"));
                            obj.setCodeDevise(rs.getString("codeDevise"));
                            obj.setIdStatut(rs.getInt("idStatut"));
                            obj.setSupprime(rs.getBoolean("supprime"));
                            return obj;
                        }
                    }
            );
            if (taxe!=null)
                taxe.setListRateLevels(getTaxeRLs(taxe.getIdTaxe(), contexte));
            return taxe;
        } catch (SQLException e) {
            Log.major( contexte.getCodeUtilisateur(), "TaxeDAO", "getTaxe", e.getMessage() );
            throw new RatesTechnicalException(e);
        } catch (TechnicalException e) {
            Log.major( contexte.getCodeUtilisateur(), "TaxeDAO", "getTaxe", e.getMessage() );
            throw new RatesTechnicalException(e);
        }
    }

    /**
     * Ajouter une taxe
     * @param taxe
     * @param contexte
     * @throws RatesTechnicalException
     * @throws RatesUserException
     */
    public void addTaxe(TaxeBean taxe, Contexte contexte) throws RatesTechnicalException, RatesUserException {
        try {
            SQLParamDescriptor[] params = {
                new SQLParamDescriptor(taxe.getIdTaxe(), false, Types.NUMERIC),
                new SQLParamDescriptor(taxe.getCodeHotel(), false, Types.VARCHAR),
                new SQLParamDescriptor(taxe.getCode(), false, Types.VARCHAR),
                new SQLParamDescriptor(new AsaDate(taxe.getDateDebut()), Types.DATE),
                new SQLParamDescriptor(new AsaDate(taxe.getDateFin()), Types.DATE),
                new SQLParamDescriptor(taxe.getInclus(), Types.BOOLEAN),
                new SQLParamDescriptor(taxe.getMontant(), Types.DOUBLE),
                new SQLParamDescriptor(taxe.getIdTypePrix(), Types.INTEGER),
                new SQLParamDescriptor(taxe.getIdUniteTaxe(), Types.INTEGER),
                new SQLParamDescriptor(taxe.getCodeDevise(), false, Types.VARCHAR),
                new SQLParamDescriptor(taxe.getSupprime(), Types.BOOLEAN),
                new SQLParamDescriptor(null, true, Types.NUMERIC)
            };
            SQLCallExecuter.getInstance().executeUpdate(
                    PROC_ADDTAXE,
                    new SQLParamDescriptorSet(params),
                    "TaxeDAO", "addTaxe",
                    contexte.getContexteAppelDAO());
            taxe.setIdTaxe((Long)params[11].getValue());
            if(taxe.getRateLevels()!=null)
                for (String rl : taxe.getRateLevels())
                    addTaxeRL(taxe.getIdTaxe(),rl, contexte);
        } catch (SQLException e) {
            Log.major( contexte.getCodeUtilisateur(), "TaxeDAO", "addTaxe", e.getMessage() );
            if (e.getErrorCode() == SYBASE_FOREIGN_KEY_ERROR_CODE_2 || e.getErrorCode() == SYBASE_FOREIGN_KEY_ERROR_CODE)
                throw new RatesUserException(e, e.getMessage());
            else if (e.getErrorCode() == SYBASE_PRIMARY_KEY_ERROR_CODE)
                throw new RatesUserException(e, e.getMessage());
            throw new RatesTechnicalException(e);
        } catch (TechnicalException e) {
            Log.major( contexte.getCodeUtilisateur(), "TaxeDAO", "addTaxe", e.getMessage() );
            throw new RatesTechnicalException(e);
        }
    }

    /**
     * Supprimer une taxe
     * @param idTaxe
     * @param contexte
     * @throws RatesTechnicalException
     * @throws RatesUserException
     */
    public void deleteTaxe(long idTaxe, Contexte contexte) throws RatesTechnicalException, RatesUserException {
        try {
            SQLCallExecuter.getInstance().executeUpdate(
                    PROC_DELTAXE,
                    new SQLParamDescriptorSet(new SQLParamDescriptor(idTaxe, false, Types.NUMERIC)),
                    "TaxeDAO", "deleteTaxe",
                    contexte.getContexteAppelDAO());
        } catch (SQLException e) {
            Log.major( contexte.getCodeUtilisateur(), "TaxeDAO", "deleteTaxe", e.getMessage() );
            if (e.getErrorCode() == SYBASE_FOREIGN_KEY_ERROR_CODE_2 || e.getErrorCode() == SYBASE_FOREIGN_KEY_ERROR_CODE)
                throw new RatesUserException(e, e.getMessage(), null);
            throw new RatesTechnicalException(e);
        } catch (TechnicalException e) {
            Log.major( contexte.getCodeUtilisateur(), "TaxeDAO", "deleteTaxe", e.getMessage() );
            throw new RatesTechnicalException(e);
        }
    }
}
