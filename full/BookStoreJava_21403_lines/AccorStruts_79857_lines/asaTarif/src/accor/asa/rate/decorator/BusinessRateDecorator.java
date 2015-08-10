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
import com.accor.asa.rate.model.BusinessRate;
import com.accor.asa.rate.model.BusinessRateBean;
import com.accor.asa.rate.model.ContractableRate;
import com.accor.asa.rate.model.RateContractInfo;
import com.accor.asa.rate.util.WeekDaysProvider;
import com.opensymphony.xwork2.conversion.annotations.ConversionType;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;

@SuppressWarnings("serial")
public class BusinessRateDecorator  implements BusinessRate,  ContractableRate{

	private BusinessRateBean bean;
	private Contexte contexte;
    private PeriodeGeneriqueRefBean periodeGenerique;
    private MealPlanRefBean mealPlan;
    private DivisionSemaineRefBean divisionSemaine;
    private DureeSejourRefBean dureeSejour;
    private PetitDejRefBean petitDejeuner;
    private String  libProduit;
    private List<RateContractInfo> contracts;
    private boolean rowReadOnly;



    public BusinessRateDecorator(BusinessRateBean bean, Contexte contexte) {
		if (bean == null)
			throw new RuntimeException("The rate bean for a RateBeanDecorator can not be null");
		this.bean = bean;
		this.contexte=contexte;
		
	}

	public String getKey() {
		return bean.getKey();
	}

	public Long getIdGrille() {
		return bean.getIdGrille();
	}

	public Boolean getBlackOutDates() {
		return bean.getBlackOutDates();
	}

	public String getCodeDevise() {
		return bean.getCodeDevise();
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

	public Integer getNbreNuitsMax() {
		return bean.getNbreNuitsMax();
	}

	public Integer getNbreNuitsMin() {
		return bean.getNbreNuitsMin();
	}

	public Boolean getOpenNewContrat() {
		return bean.getOpenNewContrat();
	}

	public Double getPrix1Pax() {
		Double d= bean.getPriceForPax(1);
		return d>0?d:null;
	}

	public Double getPrix2Pax() {
		Double d= bean.getPriceForPax(2);
		return d>0?d:null;
	}

	public Boolean getSamWe() {
		return bean.getSamWe();
	}

	public String getUniteCommission() {
		return bean.getUniteCommission();
	}

	public Double getValueCommission() {
		Double d=bean.getValueCommission();
		return d>0?d:null;
	}

	public Boolean getVenWe() {
		return bean.getVenWe();
	}
	
	
	public String getPrix1() {
		Double prix = bean.getPriceForPax(1);
		if (prix == null)
			return "";
		return prix + " " + bean.getCodeDevise();
	}

	public String getPrix2() {
		Double prix = bean.getPriceForPax(2);
		if (prix >0)
			return prix + " " + bean.getCodeDevise();
		return "";
		
	}

	public Double getPrixPdj() {
		Double d= bean.getPrixPdj();
		return d>0?d:null;
	}
	
	

	public String getCommission() {
		if(bean.getValueCommission() >0)
			return bean.getValueCommission() + " " + bean.getUniteCommission();
		return "";
	}

	public String getCodeProduit() {
		return bean.getCodeProduit();
	}
	
	public String[] getCodesProduit() {
		return bean.getCodesProduit();
	}

	public BusinessRateBean getBean() {
		return bean;
	}
	
	

	protected void setBean(BusinessRateBean bean) {
		this.bean = bean;
	}

	public PeriodeGeneriqueRefBean getPeriodeGenerique() {
		if(periodeGenerique==null) {
			try {
				periodeGenerique = PeriodeGeneriqueRefBean.getCacheList(contexte).getPeriodeGeneriqueByCode(getCodePeriode());
			} catch (Exception ex) {
				Log.critical(contexte.getCodeUtilisateur(), "BusinessRateBeanDecorator", "getPeriodeGenerique", ex.getMessage());
			}
		}
		return periodeGenerique;
	}

	public MealPlanRefBean getMealPlan() {
		if(mealPlan==null) {
			try {
				mealPlan = MealPlanRefBean.getCacheList(contexte).getMealplanByCode(getCodeMealPlan());
			} catch (Exception ex) {
				Log.critical(contexte.getCodeUtilisateur(), "BusinessRateBeanDecorator", "getMealPlan", ex.getMessage());
			}
		}
		return mealPlan;
	}

	public DivisionSemaineRefBean getDivisionSemaine() {
		if(divisionSemaine==null) {
			try {
				divisionSemaine = DivisionSemaineRefBean.getCacheList(contexte).getDivisionSemaine(getIdDivSemaine());
			} catch (Exception ex) {
				Log.major(contexte.getCodeUtilisateur(), "BusinessRateBeanDecorator", "getDivisionSemaine", ex.getMessage());
			}
		}
		return divisionSemaine;
	}

	public DureeSejourRefBean getDureeSejour() {
		if(dureeSejour==null) {
			try {
				dureeSejour = DureeSejourRefBean.getCacheList(contexte).getDureSejour(getIdDureeSejour());
			} catch (Exception ex) {
				Log.critical(contexte.getCodeUtilisateur(), "BusinessRateBeanDecorator", "getDureeSejour", ex.getMessage());
			}
		}
		return dureeSejour;
	}

	public PetitDejRefBean getPetitDejeuner() {
		if(petitDejeuner==null) {
			try {
				petitDejeuner = PetitDejRefBean.getCacheList(contexte).getPetitDejByCode(getCodePetitDej());
			} catch (Exception ex) {
				Log.critical(contexte.getCodeUtilisateur(), "BusinessRateBeanDecorator", "getPetitDejeuner", ex.getMessage());
			}
		}
		return petitDejeuner;
	}
	
	public boolean isPdjInclus() {
		getMealPlan();
		return mealPlan!= null && mealPlan.isPdjInclu();
	}

	public String getWeekendDays() {
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
	

	public List<RateContractInfo> getContracts() {
		return contracts;
	}

	public void setContracts(List<RateContractInfo> contracts) {
		this.contracts = contracts;
	}
	
	public void addContract(RateContractInfo contract)	{
		if(contracts== null)
			contracts=new ArrayList<RateContractInfo>();
		contracts.add(contract);
	}
	
	public boolean isWithContracts()	{
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

