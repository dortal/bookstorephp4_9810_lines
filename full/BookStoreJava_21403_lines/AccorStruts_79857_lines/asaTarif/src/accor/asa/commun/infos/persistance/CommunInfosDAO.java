/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 25 oct. 2006
 * Time: 15:49:32
 */
package com.accor.asa.commun.infos.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CacheManager;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.metier.AsaDate;
import com.accor.asa.commun.metier.Element;
import com.accor.asa.commun.metier.grille.Grille;
import com.accor.asa.commun.metier.grille.ListStatutGrille;
import com.accor.asa.commun.metier.mealplan.ListMealPlan;
import com.accor.asa.commun.metier.periodedevalidite.PeriodeDeValidite;
import com.accor.asa.commun.metier.validationinfo.ValidationHotel;
import com.accor.asa.commun.metier.validationinfo.ValidationInfo;
import com.accor.asa.commun.persistance.ContexteAppelDAO;
import com.accor.asa.commun.persistance.DAO;
import com.accor.asa.commun.persistance.SQLCallExecuter;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;

public class CommunInfosDAO extends DAO {

    private static CommunInfosDAO ourInstance = new CommunInfosDAO();

    public static CommunInfosDAO getInstance() {
        return ourInstance;
    }

    private CommunInfosDAO() {
    }

    //========================== METHODES METIERS =======================================================

    /**
     * Renvoie les dates de validation d'un hotel
     *
     * @param codeHotel
     * @param contexte
     * @return
     * @throws com.accor.asa.commun.TechnicalException
     *
     */
    public ValidationInfo getValidationInfo(String codeHotel, ContexteAppelDAO contexte)
            throws TechnicalException {
        try {
            return (ValidationInfo) SQLCallExecuter.getInstance().
                    executeSelectSingleObjetProc("proc_getValidationHotel",
                    new SQLParamDescriptorSet(new SQLParamDescriptor(codeHotel)),
                    "CommunInfosDAO", "getValidationInfo",
                    contexte, new SQLResultSetReader() {
                public Object instanciateFromLine(ResultSet rs) throws SQLException {
                    ValidationInfo vi = new ValidationInfo();
                    vi.setCodeHotel(rs.getString("codehotel"));
                    vi.setNomHotel(rs.getString("nomhotel"));
                    vi.setDateOuvertureBusiness(rs.getString("dateouverturebusiness"));
                    vi.setDateOuvertureTourism(rs.getString("dateouverturetourism"));
                    vi.setDateFermetureBusiness(rs.getString("datefermeturebusiness"));
                    vi.setDateFermetureTourism(rs.getString("datefermeturetourism"));
                    vi.setDateValidationBusiness1(rs.getString("datevalidationbusiness1"));
                    vi.setDateValidationTourism1(rs.getString("datevalidationtourism1"));
                    vi.setDateValidationBusiness2(rs.getString("datevalidationbusiness2"));
                    vi.setDateValidationTourism2(rs.getString("datevalidationtourism2"));
                    return vi;
                }
            });
        } catch (SQLException e) {
            LogCommun.major(contexte.getLogin(), "CommunInfosDAO", "getValidationInfo", e.getMessage());
            throw new TechnicalException(e);
        }
    }

    /**
     * Retourne les dates de validations pour une liste d'hotels.
     * @param listehotels
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	public List<ValidationHotel> getValidationHotels(String listehotels, ContexteAppelDAO contexte)
            throws TechnicalException {
        try {
            return (List<ValidationHotel>) SQLCallExecuter.getInstance().executeSelectProc(
                    "proc_getValidationHotels",
                    new SQLParamDescriptorSet(new SQLParamDescriptor(listehotels, false, Types.CHAR)),
                    "CommunInfosDAO", "getValidationHotels", contexte,
                    new SQLResultSetReader() {
                        public Object instanciateFromLine(ResultSet rs) throws SQLException {
                            ValidationHotel obj = new ValidationHotel();
                            obj.setCodeHotel(StringUtils.trimToEmpty(rs.getString("codehotel")));
                            obj.setNomHotel(StringUtils.trimToEmpty(rs.getString("nomhotel")));
                            obj.setDateValidationBusiness(StringUtils.trimToEmpty(rs.getString("datevalidationbusiness")));
                            obj.setDateValidationTourism(StringUtils.trimToEmpty(rs.getString("datevalidationtourism")));
                            return obj;
                        }
                    }
            );
        } catch (SQLException e) {
            LogCommun.major(contexte.getLogin(), "CommunInfosDAO", "getValidationHotels",
                    "Echec lors de la recuperation de la liste des dates de validation des hotels: " + listehotels, e);
            throw new TechnicalException(e);
        }
    }

    /**
     * etourne la liste des grilles tarifaires
     * pour un ou plusieurs hotels
     *
     * @param listehotels
     * @param codelangue
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	public List<Grille> getStatutGrilles(String listehotels, String codelangue, ContexteAppelDAO contexte)
            throws TechnicalException {
        try {
            return (List<Grille>) SQLCallExecuter.getInstance().executeSelectProc(
                    "proc_selGrillesTarifs",
                    new SQLParamDescriptorSet(new SQLParamDescriptor[]{
                            new SQLParamDescriptor(listehotels, false, Types.CHAR),
                            new SQLParamDescriptor(codelangue, false, Types.CHAR)
                    }),
                    "CommunInfosDAO", "getStatutGrilles", contexte,
                    new SQLResultSetReader() {
                        public Object instanciateFromLine(ResultSet rs) throws SQLException {
                            Grille obj = new Grille();
                            obj.setIdGrilleTarifs(StringUtils.trimToEmpty(rs.getString("idgrilletarifs")));
                            obj.setCodeHotel(StringUtils.trimToEmpty(rs.getString("codehotel")));
                            obj.setIdFamilleTarif(StringUtils.trimToEmpty(rs.getString("idfamilletarif")));
                            obj.setStatutTarif(StringUtils.trimToEmpty(rs.getString("statuttarif")));
                            return obj;
                        }
                    }
            );
        } catch (SQLException e) {
            LogCommun.major(contexte.getLogin(), "CommunInfosDAO", "getStatutGrilles",
                    "Echec lors de la recuperation de la liste des grilles des hotels: " + listehotels, e);
            throw new TechnicalException(e);
        }
    }

    /**
     * retourne la liste des statut grilles
     * @param codeLangue
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public ListStatutGrille getListeStatutGrille(String codeLangue, ContexteAppelDAO contexte)
            throws TechnicalException {
        ListStatutGrille listStatutGrille = (ListStatutGrille)
                CacheManager.getInstance().getObjectInCache(ListStatutGrille.class, codeLangue);
        if (listStatutGrille == null) {
            try {
                List<?> res = SQLCallExecuter.getInstance().executeSelectProc("proc_selStatutGrille",
                        new SQLParamDescriptorSet(new SQLParamDescriptor(codeLangue, false, Types.CHAR)),
                        "CommunUtilsDAO", "getMarche", contexte,
                        new SQLResultSetReader() {
                            public Object instanciateFromLine(ResultSet rs)
                                    throws TechnicalException, SQLException {
                                Element marche = new Element();
                                marche.setCode(StringUtils.trimToEmpty(rs.getString("statuttarif")));
                                marche.setLibelle(StringUtils.trimToEmpty(rs.getString("libstatuttarif")));
                                return marche;
                            }
                        });
                listStatutGrille = new ListStatutGrille(res, codeLangue);
                CacheManager.getInstance().setObjectInCache(listStatutGrille);
            } catch (SQLException ex) {
                LogCommun.major(contexte.getLogin(), "CommunUtilsDAO", "getMarche",
                        "Erreur lors de la recuperation de la liste des statuts grilles ", ex);
                throw new TechnicalException(ex);
            }
        }
        return listStatutGrille;
    }

/**
     * Renvoie la période de validité d'un hotel, famille de tarifs, groupe de tarifs
     *
     * @param idGroupe
     * @param codeHotel
     * @param idFamille
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public PeriodeDeValidite getPeriodeDeValidite(final int idGroupe,
                                                  final String codeHotel,
                                                  final int idFamille,
                                                  ContexteAppelDAO contexte)
            throws TechnicalException {
        try {
            return (PeriodeDeValidite) SQLCallExecuter.getInstance().
                    executeSelectSingleObjetProc("proc_GetPeriodeValidite",
                    new SQLParamDescriptorSet(new SQLParamDescriptor[]{
                            new SQLParamDescriptor(new Integer(idGroupe), false, Types.INTEGER),
                            new SQLParamDescriptor(StringUtils.trimToEmpty(codeHotel), false, Types.CHAR),
                            new SQLParamDescriptor(new Integer(idFamille), false, Types.INTEGER)}),
                    "CommunInfosDAO", "getPeriodeValidite",
                    contexte, new SQLResultSetReader() {
                public Object instanciateFromLine(ResultSet rs) throws SQLException, TechnicalException {
                    try {
                        PeriodeDeValidite obj = new PeriodeDeValidite();
                        obj.setIdPeriodeValidite(rs.getInt("idperiodevalidite"));
                        obj.setDateDebut(new AsaDate(rs.getString("datedeb"), AsaDate.SYBASE_DATETIME));
                        obj.setDateFin(new AsaDate(rs.getString("datefin"), AsaDate.SYBASE_DATETIME));
                        return obj;
                    } catch (ParseException e) {
                        LogCommun.major("", "CommunInfosDAO", "getValidationInfo", e.getMessage());
                        throw new TechnicalException(e);
                    }
                }
            });
        } catch (SQLException e) {
            LogCommun.major(contexte.getLogin(), "CommunInfosDAO", "getValidationInfo", e.getMessage());
            throw new TechnicalException(e);
        }
    }

    /**
     * Retourne la liste des MealPlans
     * @param codeLangue
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	public ListMealPlan getListeMealPlan(String codeLangue, ContexteAppelDAO contexte)
            throws TechnicalException {
        ListMealPlan listMealPlan = (ListMealPlan)
                CacheManager.getInstance().getObjectInCache(ListMealPlan.class, codeLangue);
        if (listMealPlan == null) {
            try {
                List<Element> res = ( List<Element>) SQLCallExecuter.getInstance().executeSelectProc("proc_sel_meal_plan",
                        new SQLParamDescriptorSet(new SQLParamDescriptor(codeLangue, false, Types.CHAR)),
                        "CommunUtilsDAO", "getListeMealPlan", contexte,
                        new SQLResultSetReader() {
                            public Object instanciateFromLine(final ResultSet rs)
                                    throws TechnicalException, SQLException {
                                final Element mealPlan = new Element();
                                mealPlan.setCode(StringUtils.trimToEmpty(rs.getString("CODEMEALPLAN")));
                                mealPlan.setLibelle(StringUtils.trimToEmpty(rs.getString("NOMMEALPLAN")));
                                return mealPlan;
                            }
                        });
                listMealPlan = new ListMealPlan(res, codeLangue);
                CacheManager.getInstance().setObjectInCache(listMealPlan);
            } catch (SQLException ex) {
                LogCommun.major(contexte.getLogin(), "CommunUtilsDAO", "getListeMealPlan",
                        "Erreur lors de la recuperation de la liste des MealPlans ", ex);
                throw new TechnicalException(ex);
            }
        }
        return listMealPlan;
    }
}
