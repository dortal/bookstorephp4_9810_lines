package com.accor.asa.rate.service.process;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.metier.ratelevel.RateLevel;
import com.accor.asa.commun.persistance.ForeignKeyException;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.reference.metier.PeriodeValiditeRefBean;
import com.accor.asa.rate.RatesTechnicalException;
import com.accor.asa.rate.RatesUserException;
import com.accor.asa.rate.model.*;

import java.util.List;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public interface RateFacade {

    public static final String POOL_NAME = "tarifRateFacade";


    public static final String BUSINESS_RATE_KEY        = "Business";
    public static final String LEISURE_RATE_KEY         = "Leisure";
    public static final String CHILD_RATE_KEY           = "Child";
    public static final String SPECIAL_RATE_KEY         = "Special";
    public static final String ADAGIO_BUSINESS_RATE_KEY = "AdagioBusiness";
    public static final String BLACK_OUT_DATES_KEY      = "BlackOutDates";

    public List<RateBean> getRatesList( String rateKey,  RateBean bean, Contexte contexte ) throws RatesTechnicalException;
    public void insertRate( String rateKey, RateBean bean, Contexte contexte ) throws RatesTechnicalException, RatesUserException;
    public void updateRate( String rateKey, RateBean bean, Contexte contexte ) throws RatesTechnicalException, RatesUserException;
    public int deleteRate( String rateKey, RateBean bean, Contexte contexte ) throws RatesTechnicalException, RatesUserException;

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
    public List<RateLevel> getRateLevelsList( String codeAsaCategory, int idFamilleTarif, int idPeriodeValidite, Contexte contexte )
            throws RatesTechnicalException;

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
            throws RatesTechnicalException;
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
            throws RatesTechnicalException;
    /**
     * Retourne une grille par son id
     * @param idGrille
     * @param contexte
     * @return
     * @throws RatesTechnicalException
     */
    public GrilleBean getGrilleById( long idGrille, Contexte contexte )
            throws RatesTechnicalException;


    public List<GrilleBean> getGrilles (String codeHotel, int idGroupeTarif, int idPeriodeValidite, Contexte contexte) throws RatesTechnicalException;

    /**
     * Creer une nouvelle grille
     * @param grille
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public long addGrille(String codeHotel, int idPeriodeValidite,int idFamilleTarif, Contexte contexte)
            throws RatesTechnicalException;

    /**
     * Maj de l'historique grille
     * @param idGrille
     * @param codeRateLevel
     * @param contexte
     * @throws RatesTechnicalException
     */
    public void updateHistoGrille(long idGrille, String codeRateLevel, Contexte contexte)
            throws RatesTechnicalException;


    /**
     *
     * @param idGrille
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public List<RateContractInfo> getContractsPeriodes(long idGrille, Contexte contexte) throws RatesTechnicalException;

    /**
     *
     * @param idGrille
     * @param codeRateLevel
     * @param status
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public List<RateContractInfo> getContractsForRateLevel(long idGrille, String codeRateLevel, String status, Contexte contexte) throws RatesTechnicalException, RatesUserException;
    
    /**
     * 
     * @param oldGrilleData
     * @param activePeriodeValidite
     * @param contexte
     * @throws TechnicalException
     * @throws IncoherenceException
     * @throws ForeignKeyException
     */
    public void importGrilleTarif(String rateKey, GrilleBean oldGrilleData,  PeriodeValiditeRefBean activePeriodeValidite,  Contexte contexte) throws RatesTechnicalException, RatesUserException;
    
    public RateBean getRateById(String rateKey,RateBean rate, Contexte context) throws RatesTechnicalException;

    public List<ControleFinSaisieBean> getControlesEnEchec (String codeHotel, int idGroupeTarif, int idPeriodeValidite, Contexte contexte) throws RatesTechnicalException;

    public void updateStatutGrille (String codeHotel, int idGroupeTarif, int idPeriodeValidite, int idStatut, Contexte contexte) throws RatesTechnicalException;

    public void updateStatutGrille (long idGrille, int idStatut, Contexte contexte) throws RatesTechnicalException;

    //================================  TAXES  ========================================
    /**
     * Retourne la liste des taxes d'un hotel
     * @param codeHotel
     * @param contexte
     * @return
     * @throws RatesTechnicalException
     */
    public List<TaxeBean> getTaxes(String codeHotel, Contexte contexte) throws RatesTechnicalException;
    /**
     * Retourne une taxe d'un hotel
     * @param codeHotel
     * @param contexte
     * @return
     * @throws RatesTechnicalException
     */
    public TaxeBean getTaxe(long idTaxe, Contexte contexte) throws RatesTechnicalException;
    /**
     * Ajouter une taxe
     * @param taxe
     * @param contexte
     * @throws RatesTechnicalException
     * @throws RatesUserException
     */
    public void addTaxe(TaxeBean taxe, Contexte contexte) throws RatesTechnicalException, RatesUserException;
    /**
     * Supprimer une taxe
     * @param idTaxe
     * @param contexte
     * @throws RatesTechnicalException
     * @throws RatesUserException
     */
    public void deleteTaxe(long idTaxe, Contexte contexte) throws RatesTechnicalException, RatesUserException;

}