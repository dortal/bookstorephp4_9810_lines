package com.accor.asa.rate.decorator;

import java.util.Date;

import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.metier.donneesdereference.Continent;
import com.accor.asa.commun.metier.donneesdereference.Pays;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.reference.metier.OffreSpecialeRefBean;
import com.accor.asa.commun.reference.metier.TypePrixRefBean;
import com.accor.asa.commun.reference.metier.UnitePrixRefBean;
import com.accor.asa.rate.model.ContractableRate;
import com.accor.asa.rate.model.SpecialRate;
import com.accor.asa.rate.model.SpecialRateBean;
import com.opensymphony.xwork2.conversion.annotations.ConversionType;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;

public class SpecailRateDecorator implements SpecialRate , ContractableRate {

	private SpecialRateBean bean;
	private Contexte contexte;
	private TypePrixRefBean typePrix;
	private UnitePrixRefBean unitePrix;
	private OffreSpecialeRefBean offreSpeciale;
    private boolean rowReadOnly;


    public SpecailRateDecorator(SpecialRateBean bean, Contexte contexte) {
		if (bean == null)
			throw new RuntimeException("The rate bean for a SpecailRateDecorator null");
		this.bean = bean;
		this.contexte = contexte;
	}


    public String getCodeContinent1() {
		return bean.getCodeContinent1();
	}

	public String getCodeContinent2() {
		return bean.getCodeContinent2();
	}

	public String getCodeContinent3() {
		return bean.getCodeContinent3();
	}

	public String getCodeDevise() {
		return bean.getCodeDevise();
	}

	public Integer getCodeOffreSpeciale() {
		return bean.getCodeOffreSpeciale();
	}

	public String getCodePays1() {
		return bean.getCodePays1();
	}

	public String getCodePays2() {
		return bean.getCodePays2();
	}

	public String getCodePays3() {
		return bean.getCodePays3();
	}

	public String getCodePays4() {
		return bean.getCodePays4();
	}

	public String getCodePays5() {
		return bean.getCodePays5();
	}

	public String getCommentaire() {
		return bean.getCommentaire();
	}

	@TypeConversion(converter="com.accor.asa.rate.util.AsaDateConverter",type=ConversionType.CLASS )
	public Date getDateDebut() {
		return bean.getDateDebut();
	}

	@TypeConversion(converter="com.accor.asa.rate.util.AsaDateConverter",type=ConversionType.CLASS )
	public Date getDateFin() {
		return bean.getDateFin();
	}

	public Integer getIdTypePrix() {
		return bean.getIdTypePrix();
	}

	public Integer getIdUnitePrix() {
		return bean.getIdUnitePrix();
	}

	public Double getPrix() {
		Double prix = bean.getPrix();
		return prix > 0 ? prix : null;
	}

	public boolean isObligatoire() {
		return bean.isObligatoire();
	}

	public boolean isTousMarches() {
		return bean.isTousMarches();
	}

	public Long getIdGrille() {
		return bean.getIdGrille();
	}

	public String getKey() {
		return bean.getKey();
	}

	public String getContinentNames() {
		StringBuffer txt = new StringBuffer("");
		for(int i = 1;i<=3;i++)
		{
			Continent continent= getContinent(i);
			if(continent!= null)
			{
				if(txt.length()>0)
					txt.append(", ");
				txt.append(continent.getLibelle());
			}
		}
		return txt.toString();
	}

	private Continent getContinent(int idx) {
		String codeContinent = null;
		Continent continent = null;
		switch (idx) {
		case 1:
			codeContinent = getCodeContinent1();
			break;
		case 2:
			codeContinent = getCodeContinent2();
			break;
		case 3:
			codeContinent = getCodeContinent3();
			break;
		}
		if (codeContinent != null) {
			try {
				 continent = PoolCommunFactory.getInstance().getCommunUtilsFacade().getContinentByCode(codeContinent, contexte);

			} catch (Exception ex) {
				// No log, beacuse the exception has been already logged on
				// upper
				// levels
			}
		}
		return continent;
	}

	private Pays getPays(int idx) {
		String codePays = null;
		switch (idx) {
		case 1:
			codePays = getCodePays1();
			break;
		case 2:
			codePays = getCodePays2();
			break;
		case 3:
			codePays = getCodePays3();
			break;
		case 4:
			codePays = getCodePays4();
			break;
		case 5:
			codePays = getCodePays5();
			break;
		default:
			break;
		}
		Pays pays = null;
		if (codePays != null) {
			try {
				pays = PoolCommunFactory.getInstance().getCommunUtilsFacade().getPaysByCode(codePays, contexte);

			} catch (Exception ex) {
				// No log, beacuse the exception has been already logged on
				// upper
				// levels
			}
		}
		return pays;
	}

	public String getPaysNames() {
		StringBuffer txt = new StringBuffer("");
		for (int i = 1; i <= 5; i++) {
			Pays pays = getPays(i);
			if (pays != null) {
				if (txt.length() > 0)
					txt.append(", ");
				txt.append(pays.getLibelle());
			}
		}
		return txt.toString();
	}

	public String getPrixLabel() {
		StringBuffer sb = new StringBuffer("");
		Double prix = bean.getPrix();
		if (prix == 0)
			return "";
		sb.append(prix).append(getCodeDevise());
		return sb.toString();
	}

	public TypePrixRefBean getTypePrix() {
		if (getPrix() != null && getIdTypePrix() != null && typePrix == null)
			try {
				typePrix = TypePrixRefBean.getCacheList(contexte).getTypePrixById(getIdTypePrix());
			} catch (Exception ex) {

			}
		return typePrix;
	}

	public UnitePrixRefBean getUnitePrix() {
		if (getPrix() != null && getIdUnitePrix() != null && unitePrix == null)
			try {
				unitePrix = UnitePrixRefBean.getCacheList(contexte).getUnitePrixById(getIdUnitePrix());
			} catch (Exception ex) {

			}
		return unitePrix;
	}

	public OffreSpecialeRefBean getOffreSpeciale() {
		if (getCodeOffreSpeciale() > 0 && offreSpeciale == null)
			try {
				offreSpeciale = OffreSpecialeRefBean.getCacheList(contexte).getOffreSpecialeByCode(getCodeOffreSpeciale());
			} catch (Exception ex) {

			}
		return offreSpeciale;
	}

    public boolean isWithContracts()	{
        return false;
    }

    public boolean isRowReadOnly() {
        return rowReadOnly;
    }

    public void setRowReadOnly(boolean rowReadOnly) {
        this.rowReadOnly = rowReadOnly;
    }
}
