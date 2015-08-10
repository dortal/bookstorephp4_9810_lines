package com.accor.asa.rate.decorator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.reference.metier.DivisionSemaineRefBean;
import com.accor.asa.commun.reference.metier.DureeSejourRefBean;
import com.accor.asa.commun.reference.metier.MealPlanRefBean;
import com.accor.asa.commun.reference.metier.PeriodeGeneriqueRefBean;
import com.accor.asa.commun.reference.metier.PetitDejRefBean;
import com.accor.asa.rate.common.Log;
import com.accor.asa.rate.model.ContractableRate;
import com.accor.asa.rate.model.LeisureRate;
import com.accor.asa.rate.model.LeisureRateBean;
import com.accor.asa.rate.model.RateContractInfo;
import com.accor.asa.rate.util.WeekDaysProvider;
import com.opensymphony.xwork2.conversion.annotations.ConversionType;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;

public class LeisureRateDecorator implements LeisureRate, ContractableRate{
	
	private PeriodeGeneriqueRefBean periodeGenerique;
	private MealPlanRefBean mealPlan;
	private DivisionSemaineRefBean divisionSemaine;
	private DureeSejourRefBean dureeSejour;
	private PetitDejRefBean petitDejeuner;
	
	private LeisureRateBean bean;
	private Contexte contexte;
	private List<RateContractInfo> contracts;
    private String  libProduit;
    private boolean rowReadOnly;


    public LeisureRateDecorator(LeisureRateBean leisureRate,  Contexte contexte) {
		if(leisureRate==null)
			throw new RuntimeException("The rate bean for a LeisureRateDecorator can not be null");
		this.bean = leisureRate;
		this.contexte=contexte;
	}
	private Double getDisplayablePrice(Double price)
	{
		if(price>0)
			return price;
		return null;
	}
	public Double getBagageInAndOut() {
		return  getDisplayablePrice(bean.getBagageInAndOut());
	}
	public Double getBagageInOrOut() {
		return getDisplayablePrice(bean.getBagageInOrOut());
	}
	public String getCodeDevise() {
		return bean.getCodeDevise();
	}
	public String getCodeProduit() {
		return bean.getCodeProduit();
	}
	public String getCodeMealPlan() {
		return bean.getCodeMealPlan();
	}
	public String getCodePeriode() {
		return bean.getCodePeriode();
	}
	public String getCodePetitDej() {
		return bean.getCodePetitDej();
	}
	
	public String getCodeRateLevel() {
		return bean.getCodeRateLevel();
	}
	@TypeConversion(converter="com.accor.asa.rate.util.AsaDateConverter",type=ConversionType.CLASS )
	public Date getDateDebut() {
		return bean.getDateDebut();
	}
	@TypeConversion(converter="com.accor.asa.rate.util.AsaDateConverter",type=ConversionType.CLASS )
	public Date getDateFin() {
		return bean.getDateFin();
	}
	public Boolean getDimWe() {
		return bean.getDimWe();
	}
	
	public Integer getIdDivSemaine() {
		return bean.getIdDivSemaine();
	}
	public Integer getIdDureeSejour() {
		return bean.getIdDureeSejour();
	}
	public Boolean getJeuWe() {
		return bean.getJeuWe();
	}
	public String getLibelleSalon() {
		return bean.getLibelleSalon();
	}
	public Boolean getLunWe() {
		return bean.getLunWe();
	}
	public Boolean getMarWe() {
		return bean.getMarWe();
	}
	
	public Boolean getMerWe() {
		return bean.getMerWe();
	}
	public Double getPrix() {
		return getDisplayablePrice(bean.getPrix());
	}
	public Double getPrixPdj() {
		return getDisplayablePrice(bean.getPrixPdj());
	}
	public Double getPrixSupQUAD() {
		return getDisplayablePrice(bean.getPrixSupQUAD());
	}
	public Double getPrixSupSGL() {
		return getDisplayablePrice(bean.getPrixSupSGL());
	}
	public Double getPrixSupTRI() {
		return getDisplayablePrice(bean.getPrixSupTRI());
	}
	public Boolean getSamWe() {
		return bean.getSamWe();
	}
	public Double getSupplDemPens() {
		return getDisplayablePrice(bean.getSupplDemPens());
	}
	public Double getSupplPensCompl() {
		return getDisplayablePrice(bean.getSupplPensCompl());
	}
	public String getUniteCommission() {
		return bean.getUniteCommission();
	}
	public Double getValueCommission() {
		return getDisplayablePrice(bean.getValueCommission());
	}
	public Boolean getVenWe() {
		return bean.getVenWe();
	}
	public Long getIdGrille() {
		return bean.getIdGrille();
	}
	public String getKey() {
		return bean.getKey();
	}

    public PeriodeGeneriqueRefBean getPeriodeGenerique() {
		if(periodeGenerique==null)
		{
			try {
				periodeGenerique = PeriodeGeneriqueRefBean.getCacheList(contexte).getPeriodeGeneriqueByCode(getCodePeriode());
			} catch (Exception ex) {
				Log.critical(contexte.getCodeUtilisateur(), "LeisureRateDecorator", "getPeriodeGenerique", ex.getMessage());
			}
		}
		return periodeGenerique;
	}

	public MealPlanRefBean getMealPlan() {
		if(mealPlan==null)
		{
			try {
				mealPlan = MealPlanRefBean.getCacheList(contexte).getMealplanByCode(getCodeMealPlan());
			} catch (Exception ex) {
				Log.critical(contexte.getCodeUtilisateur(), "LeisureRateDecorator", "getMealPlan", ex.getMessage());
			}
		}
		return mealPlan;
	}

	public DivisionSemaineRefBean getDivisionSemaine() {
		if(divisionSemaine==null)
		{
			try {
				divisionSemaine = DivisionSemaineRefBean.getCacheList(contexte).getDivisionSemaine(getIdDivSemaine());
			} catch (Exception ex) {
				Log.major(contexte.getCodeUtilisateur(), "LeisureRateDecorator", "getDivisionSemaine", ex.getMessage());
			}
		}
		return divisionSemaine;
	}

	public DureeSejourRefBean getDureeSejour() {
		if(dureeSejour==null)
		{
			try {
				dureeSejour = DureeSejourRefBean.getCacheList(contexte).getDureSejour(getIdDureeSejour());
			} catch (Exception ex) {
				Log.critical(contexte.getCodeUtilisateur(), "LeisureRateDecorator", "getDureeSejour", ex.getMessage());
			}
		}
		return dureeSejour;
	}

	public PetitDejRefBean getPetitDejeuner() {
		if(petitDejeuner==null)
		{
			try {
				petitDejeuner = PetitDejRefBean.getCacheList(contexte).getPetitDejByCode(getCodePetitDej());
			} catch (Exception ex) {
				Log.critical(contexte.getCodeUtilisateur(), "LeisureRateDecorator", "getPetitDejeuner", ex.getMessage());
			}
		}
		return petitDejeuner;
	}
	
	public String getWeekendDays()
	{
		String[] weekDays = WeekDaysProvider.getWeekDays(contexte);
		StringBuffer txt = new StringBuffer();
		if(getLunWe())
			txt.append(weekDays[0]).append(" ");
		if(getMarWe())
			txt.append(weekDays[1]).append(" ");
		if(getMerWe())
			txt.append(weekDays[2]).append(" ");
		if(getJeuWe())
			txt.append(weekDays[3]).append(" ");
		if(getVenWe())
			txt.append(weekDays[4]).append(" ");
		if(getSamWe())
			txt.append(weekDays[5]).append(" ");
		if(getDimWe())
			txt.append(weekDays[6]).append(" ");
		return txt.toString();
	}
	
	public String getPrixLabel()
	{
		if(bean.getPrix()>0)
			return bean.getPrix()+" "+getCodeDevise();
		return "";
	}
	
	public String getPrixSupSGLLabel()
	{
		if(bean.getPrixSupSGL()>0)
			return bean.getPrixSupSGL()+" "+getCodeDevise();
		return "";
	}
	
	public String getPrixSupTRILabel()
	{
		if(bean.getPrixSupTRI()>0)
			return bean.getPrixSupTRI()+" "+getCodeDevise();
		return "";
	}
	
	public String getCommission() {
		if(bean.getValueCommission() >0)
			return bean.getValueCommission() + " " + bean.getUniteCommission();
		return "";
	}
	
	public boolean isPdjIncluded() {
		getMealPlan();
		return mealPlan!= null && mealPlan.isPdjInclu();
	}

	public List<RateContractInfo> getContracts() {
		return contracts;
	}
	public void setContracts(List<RateContractInfo> contracts) {
		this.contracts = contracts;
	}
	
	public void addContract(RateContractInfo contract) {
		if(contracts== null)
			contracts=new ArrayList<RateContractInfo>();
		contracts.add(contract);
	}
	
	public boolean isWithContracts() {
		return contracts != null && !contracts.isEmpty();
	}

    public String getLibProduit() {
        return libProduit;
    }

    public void setLibProduit(String libProduit) {
        this.libProduit = libProduit;
    }

    public boolean isRowReadOnly() {
        return rowReadOnly;
    }

    public void setRowReadOnly(boolean rowReadOnly) {
        this.rowReadOnly = rowReadOnly;
    }

    public boolean isWeekAndWeekEnd() {
        return DivisionSemaineRefBean.ID_DIVSEM_COMPLETE == getIdDivSemaine();
    }
}
