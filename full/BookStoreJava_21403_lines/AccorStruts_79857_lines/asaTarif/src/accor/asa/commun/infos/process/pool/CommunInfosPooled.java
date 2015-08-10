package com.accor.asa.commun.infos.process.pool;

import java.util.List;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.infos.persistance.CommunInfosDAO;
import com.accor.asa.commun.infos.process.CommunInfos;
import com.accor.asa.commun.metier.grille.Grille;
import com.accor.asa.commun.metier.grille.ListStatutGrille;
import com.accor.asa.commun.metier.mealplan.ListMealPlan;
import com.accor.asa.commun.metier.validationinfo.ValidationHotel;
import com.accor.asa.commun.metier.validationinfo.ValidationInfo;
import com.accor.asa.commun.persistance.ContexteAppelDAO;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 25 oct. 2006
 * Time: 13:33:13
 * To change this template use File | Settings | File Templates.
 */
public class CommunInfosPooled implements CommunInfos {

    /**
     * Constructeur
     */
    public CommunInfosPooled() {
    }

    //========================== METHODES METIERS =======================================================

    /**
     * Renvoie les dates de validation d'un hotel
     *
     * @param codeHotel
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public ValidationInfo getValidationInfo(String codeHotel, ContexteAppelDAO contexte)
            throws TechnicalException {
        return CommunInfosDAO.getInstance().getValidationInfo(codeHotel, contexte);
    }

    /**
     * Retourne les dates de validations pour une liste d'hotels.
     * @param listehotels
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public List<ValidationHotel> getValidationHotels(String listehotels, ContexteAppelDAO contexte)
            throws TechnicalException {
        return CommunInfosDAO.getInstance().getValidationHotels(listehotels, contexte);
    }

    /**
     * etourne la liste des grilles tarifaires
     * pour un ou plusieurs hotels
     * @param listehotels
     * @param codelangue
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public List<Grille> getStatutGrilles(String listehotels, String codelangue, ContexteAppelDAO contexte)
            throws TechnicalException {
        return CommunInfosDAO.getInstance().getStatutGrilles(listehotels, codelangue, contexte);
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
        return CommunInfosDAO.getInstance().getListeStatutGrille(codeLangue, contexte);
    }

    /**
     * Retourne la liste des MealPlans
     * @param codeLangue
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public ListMealPlan getListeMealPlan(String codeLangue, ContexteAppelDAO contexte)
            throws TechnicalException {
        return CommunInfosDAO.getInstance().getListeMealPlan(codeLangue, contexte);
    }
}
