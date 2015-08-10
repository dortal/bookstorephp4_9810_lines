package com.accor.asa.commun.reference.metier;

import java.util.List;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.metier.categorie.AsaCategory;
import com.accor.asa.commun.metier.devise.Devise;
import com.accor.asa.commun.metier.ratelevel.RateLevel;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.reference.process.RefFacade;

@SuppressWarnings("serial")
public class ParamGrilleRefBean extends RefBean{

	public static String buildKey(int idPeriodeValidite, String codeAsaCategory,  String codeRateLevel)
	{
		return idPeriodeValidite+"_"+codeAsaCategory+"_"+codeRateLevel;
	}
	
	private String codeAsaCategory;
	private int idPeriodeValidite;
	private String codeRateLevel;
	private int idFamilleTarif;
	private Double valueCommission;
	private String uniteCommission;
	private String codeMealPlan;
	private int idDureeSejour;
	private boolean newContrat;
	private boolean blackOutDates;
	
	private AsaCategory asaCategory;
	private RateLevel rateLevel;
	private DureeSejourRefBean dureeSejour;
	private MealPlanRefBean mealplan;
	private PeriodeValiditeRefBean periodeValidite;
	private FamilleTarifRefBean familleTarif;
	private Devise devise;
	
	private List<SupplementRefBean> supplements;
	
	private boolean oneYearOnly;
	private boolean lunWe;
	private boolean marWe;
	private boolean merWe;
	private boolean jeuWe;
	private boolean venWe;
	private boolean samWe;
	private boolean dimWe;
	
	public String getCodeAsaCategory() {
		return codeAsaCategory;
	}
	public void setCodeAsaCategory(String codeAsaCategory) {
		this.codeAsaCategory = codeAsaCategory;
	}
	public String getCodeRateLevel() {
		return codeRateLevel;
	}
	public void setCodeRateLevel(String codeRateLevel) {
		this.codeRateLevel = codeRateLevel;
	}
	public int getIdFamilleTarif() {
		return idFamilleTarif;
	}
	public void setIdFamilleTarif(int idFamilleTarif) {
		this.idFamilleTarif = idFamilleTarif;
	}
    public Double getValueCommission() {
        return valueCommission;
    }
    public void setValueCommission(Double valueCommission) {
        this.valueCommission = valueCommission;
    }
    public String getUniteCommission() {
		return uniteCommission;
	}
	public void setUniteCommission(String uniteComision) {
		this.uniteCommission = uniteComision;
	}
	public String getCodeMealPlan() {
		return codeMealPlan;
	}
	public void setCodeMealPlan(String codeMealPlan) {
		this.codeMealPlan = codeMealPlan;
	}
	public int getIdDureeSejour() {
		return idDureeSejour;
	}
	public void setIdDureeSejour(int idDureeSejour) {
		this.idDureeSejour = idDureeSejour;
	}
	public boolean isNewContrat() {
		return newContrat;
	}
	public void setNewContrat(boolean newContrat) {
		this.newContrat = newContrat;
	}
	public boolean isBlackOutDates() {
		return blackOutDates;
	}
	public void setBlackOutDates(boolean blackOutDates) {
		this.blackOutDates = blackOutDates;
	}
	public String getCode() {
		return buildKey(getIdPeriodeValidite(), getCodeAsaCategory(), getCodeRateLevel());
	}
	
	
	
	public AsaCategory getAsaCategory() {
		return asaCategory;
	}
	public void setAsaCategory(AsaCategory saCategory) {
		this.asaCategory = saCategory;
		if (asaCategory == null)
			setCodeAsaCategory(null);
		else
			setCodeAsaCategory(asaCategory.getCode());
	}
	public RateLevel getRateLevel() {
		return rateLevel;
	}
	public void setRateLevel(RateLevel rateLevel) {
		this.rateLevel = rateLevel;
		if(rateLevel==null)
			setCodeRateLevel(null);
		else
			setCodeRateLevel(rateLevel.getCode());
	}
	public DureeSejourRefBean getDureeSejour() {
		return dureeSejour;
	}
	public void setDureeSejour(DureeSejourRefBean dureeSejour) {
		this.dureeSejour = dureeSejour;
		if(dureeSejour==null)
			setIdDureeSejour(-1);
		else
			setIdDureeSejour(Integer.parseInt(dureeSejour.getId()));
	}
	public MealPlanRefBean getMealplan() {
		return mealplan;
	}
	public void setMealplan(MealPlanRefBean mealplan) {
		this.mealplan = mealplan;
		if(mealplan==null)
			setCodeMealPlan(null);
		else
			setCodeMealPlan(mealplan.getCode());
	}
	public int getIdPeriodeValidite() {
		return idPeriodeValidite;
	}
	public void setIdPeriodeValidite(int idPeriodeValidite) {
		this.idPeriodeValidite = idPeriodeValidite;
	}
	public PeriodeValiditeRefBean getPeriodeValidite() {
		return periodeValidite;
	}
	public void setPeriodeValidite(PeriodeValiditeRefBean periodeValidite) {
		this.periodeValidite = periodeValidite;
		if(periodeValidite==null)
			setIdPeriodeValidite(-1);
		else
			setIdPeriodeValidite(Integer.parseInt(periodeValidite.getCode()));
	}
	public FamilleTarifRefBean getFamilleTarif() {
		return familleTarif;
	}
	public void setFamilleTarif(FamilleTarifRefBean familleTarif) {
		this.familleTarif = familleTarif;
		if(familleTarif==null)
			setIdFamilleTarif(-1);
		else
			setIdFamilleTarif(Integer.parseInt(familleTarif.getId()));
	}
	public Devise getDevise() {
		return devise;
	}
	public void setDevise(Devise devise) {
		this.devise = devise;
	}
	public List<SupplementRefBean> getSupplements() {
		return supplements;
	}
	public void setSupplements(List<SupplementRefBean> suppluements) {
		this.supplements = suppluements;
	}
	
	public boolean isOneYearOnly() {
		return oneYearOnly;
	}
	public void setOneYearOnly(boolean oneYearOnly) {
		this.oneYearOnly = oneYearOnly;
	}
	public boolean isLunWe() {
		return lunWe;
	}
	public void setLunWe(boolean lunWe) {
		this.lunWe = lunWe;
	}
	public boolean isMarWe() {
		return marWe;
	}
	public void setMarWe(boolean marWe) {
		this.marWe = marWe;
	}
	public boolean isMerWe() {
		return merWe;
	}
	public void setMerWe(boolean merWe) {
		this.merWe = merWe;
	}
	public boolean isJeuWe() {
		return jeuWe;
	}
	public void setJeuWe(boolean jeuWe) {
		this.jeuWe = jeuWe;
	}
	public boolean isVenWe() {
		return venWe;
	}
	public void setVenWe(boolean venWe) {
		this.venWe = venWe;
	}
	public boolean isSamWe() {
		return samWe;
	}
	public void setSamWe(boolean samWe) {
		this.samWe = samWe;
	}
	public boolean isDimWe() {
		return dimWe;
	}
	public void setDimWe(boolean dimWe) {
		this.dimWe = dimWe;
	}
	
	public static ParamGrilleCacheList getCacheList(Contexte contexte) throws TechnicalException, IncoherenceException
	{
		return (ParamGrilleCacheList)PoolCommunFactory.getInstance().getRefFacade().getCacheRefList(RefFacade.PARAM_GRILLE_KEY, contexte);
	}
	
	public static String formatComissionValue(Double commission) {
		if(commission==null) return "";
		double value = Math.round(commission*100)/100d;
		return String.valueOf(value);
	}
}
