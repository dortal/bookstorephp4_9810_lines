package com.accor.asa.commun.reference.process;

import java.util.List;

import com.accor.asa.commun.DuplicateKeyException;
import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.ForeignKeyException;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.reference.metier.RefBean;

public interface RefFacade {

	public static final String POOL_NAME = "communRefFacade";

	public static final String FAMILLE_SEGMENT_REF_KEY = "FamilleSegment";

	public static final String RATELEVEL_HOTELUSE_BYFT_REF_KEY = "RateLevelHotelUseByFT";

	public static final String MODE_RESERVATION_REF_KEY = "ModeReservation";
	public static final String MODE_RESERVATION_BY_TO_REF_KEY = "ModeReservationByTO";

	public static final String MODE_PAIEMENT_REF_KEY = "ModePaiement";
	public static final String MODE_PAIEMENT_BY_TO_REF_KEY = "ModePaiementByTO";

	public static final String REMUNERATION_TYPE_REF_KEY = "RemunerationType";
	public static final String REMUNERATION_TYPE_BY_TO_REF_KEY = "RemunerationTypeByTO";

	public static final String REMUNERATION_MODE_REF_KEY = "RemunerationMode";
	public static final String REMUNERATION_MODE_BY_TO_REF_KEY = "RemunerationModeByTO";

	public static final String REMUNERATION_FREQUENCE_REF_KEY = "RemunerationFrequence";
	public static final String PRISE_EN_CHARGE_REF_KEY = "PriseEnCharge";

	public static final String FACTURATION_MODE_REF_KEY = "FacturationMode";
	public static final String FACTURATION_MODE_BY_TO_REF_KEY = "FacturationModeByTO";

	public static final String ACCOUNT_PROFILE_QUESTION_REF_KEY = "AccountProfileQuestion";
	public static final String ACTIVITE_COMMERCIALE_TOUR_OP_REF_KEY = "ActiviteCommercialeTourOp";
	public static final String CDV_REF_KEY = "Cdv";
	public static final String CIBLE_COMMERCIALE_REF_KEY = "CibleCommerciale";
	public static final String DIVISION_SEMAINE_REF_KEY = "DivisionSemaine";
	public static final String CYCLE_ACTION_REF_KEY = "CycleAction";
	public static final String GROUPE_PRESTATION_REF_KEY = "GroupePrestation";
	public static final String MAILING_REF_KEY = "Mailing";
	public static final String MARCHE_REF_KEY = "Marche";
	public static final String MISE_A_DISPO_REF_KEY = "MiseADispoChambre";
	public static final String MOTIF_STATUT_REF_KEY = "MotifStatut";
	public static final String PERIODE_GENERIQUE_REF_KEY = "PeriodeGenerique";
	public static final String RFP_QUESTION_ACQ_REF_KEY = "RFPQuestionACQ";
	public static final String RFP_TYPE_QUESTION_ACQ_REF_KEY = "RFPTypeQuestionACQ";
	public static final String RFP_TYPE_REPONSE_ACQ_REF_KEY = "RFPTypeReponseACQ";
	public static final String SOLUTION_CONNECTIVITE_TOUR_OP_REF_KEY = "SolutionConnectiviteTourOp";
	public static final String TEMPLATE_REF_KEY = "Template";
	public static final String TYPE_ACTION_REF_KEY = "TypeAction";
	public static final String TYPE_OFFRE_REF_KEY = "TypeOffre";
	public static final String TYPE_PETIT_DEJ_REF_KEY = "TypePetitDej";
	public static final String TYPE_PRESTATION_REF_KEY = "TypePrestation";
	public static final String VOUCHER_REF_KEY = "Voucher";
	public static final String DUREE_SEJOUR_KEY = "DureeSejour";
	public static final String OFFRE_SPECIALE_KEY = "OffreSpeciale";
	public static final String GROUPE_TARIF_KEY = "GroupeTarif";
	public static final String PERIODE_VALIDITE_KEY = "PeriodeValidite";
	public static final String MEALPLAN_KEY = "MealPlan";
	public static final String FAMILLE_TARIF_KEY = "FamilleTarif";
	public static final String SUPPLEMENT_KEY="Supplement";
	public static final String PETITDEJ_KEY="PetitDej";
	public static final String STATUTGRILLE_KEY="StatutGrille";
	public static final String TYPE_PRIX_KEY="TypePrix";
	public static final String UNITE_PRIX_KEY="UnitePrix";
    public static final String UNITE_TAXE_KEY="UniteTaxe";
    public static final String ABSENT_TARS_KEY="AbsentTars";

    public static final String PARAM_PERIODE_GENERIQUE_KEY = "ParamPeriodeGenerique";
    public static final String PARAM_PERIODE_GENERIQUE_EXCLUHOTEL_KEY = "ParamPeriodeGeneriqueExcluHotel";
	public static final String PARAM_PERIODE_VALIDITE_KEY = "ParamPeriodeValidite";
	public static final String PARAM_RATE_LEVEL_KEY="ParamRateLevel";
	public static final String PARAM_TRANSFERT_KEY = "ParamTransfert";
	public static final String PARAM_ASACAT_NRRATELEVELS_KEY="ParamAsacatNrRateLevels";
	public static final String PARAM_MEALPLAN_KEY="ParamMealplan";
	public static final String PARAM_TARIF_GO_KEY="ParamTarifGO";
	public static final String PARAM_SUPPLEMENT_KEY = "ParamSupplement";
	public static final String PARAM_GRILLE_KEY = "ParamGrille";
	public static final String PARAM_RLHU_KEY="ParamRateLevelHotelUse";
	public static final String PARAM_PGFT_KEY="ParamPeriodeGenFamTarif";
	public static final String PARAM_RL_BY_DS_KEY="RateLevelByDureeSejour";
	public static final String PARAM_DUREE_SEJOUR_KEY="ParamDureeSejour";
	
	public static final String MODIFY_TYPE_CHAMBRE_KEY="ModificationTypeChambre";
	public static final String TARIF_CONTROLE_KEY="TarifControle";
	
	
	public CachableInterface getCacheRefList(String refKey, Contexte contexte)
			throws TechnicalException, IncoherenceException;

	public List<?> getRefList(String refKey, Contexte contexte)
			throws TechnicalException;

	public List<?> getAdminRefList(String refKey, RefBean bean, Contexte contexte)
			throws TechnicalException;

	public int insertRef(String refKey, RefBean bean, Contexte contexte)
			throws TechnicalException, IncoherenceException,
			DuplicateKeyException;

	public int updateRef(String refKey, RefBean bean, Contexte contexte)
			throws TechnicalException, IncoherenceException,
			DuplicateKeyException, ForeignKeyException;

	public int deleteRef(String refKey, RefBean bean, Contexte contexte)
			throws TechnicalException, IncoherenceException,
			ForeignKeyException;

	public void refreshCacheRefList(String refKey, Contexte contexte)
			throws TechnicalException;
	
	public int doAction(int actionType, String refKey, RefBean bean, Contexte contexte)
	throws TechnicalException, IncoherenceException, ForeignKeyException, DuplicateKeyException;
	
	public RefBean getByIdentifier( String refKey, Contexte contexte, RefBean identifier) throws TechnicalException, IncoherenceException;
	
	public List<?> getByCriteria(int actionType, String refKey, Contexte contexte, RefBean criteria) throws TechnicalException, IncoherenceException;
	
	public int doListAction(int actionType, String refKey, Contexte contexte, List<RefBean> beans) throws TechnicalException, IncoherenceException, ForeignKeyException, DuplicateKeyException;
}