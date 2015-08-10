package com.accor.asa.rate.decorator;

import java.util.Date;
import java.util.List;

import com.accor.asa.rate.model.ChildRate;
import com.accor.asa.rate.model.ChildRateBean;
import com.accor.asa.rate.model.ChildRateServiceData;
import com.accor.asa.rate.model.ContractableRate;
import com.opensymphony.xwork2.conversion.annotations.ConversionType;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;

public class ChildRateDecorator implements ChildRate, ContractableRate {

	private ChildRateBean bean;
    private String  libProduit;
    private boolean rowReadOnly;

    public ChildRateDecorator(ChildRateBean bean) {
		super();
		this.bean = bean;
	}

    public Long getIdGrille() {
        return bean.getIdGrille();
    }

    public String getKey() {
        return bean.getKey();
    }

	public String getCodeProduit() {
		return bean.getCodeProduit();
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

    public Integer getMaxChild() {
        Integer m=   bean.getMaxChild();
        return m>0? m:null;
    }

	public Integer getMaxAdult() {
		Integer m= bean.getMaxAdult();
		return m>0? m:null;
	}

    public String getCodeDevise() {
		return bean.getCodeDevise();
	}

    public boolean isChambreSepare() {
        return bean.isChambreSepare();
    }

    public List<ChildRateServiceData> getServices() {
        return bean.getServices();
    }

    public ChildRateServiceData getService(int index) {
		return bean.getService(index);
	}

    public boolean isWithContracts()	{
        return false;
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
}
