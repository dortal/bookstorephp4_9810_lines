package com.accor.asa.rate.model;

import java.util.List;

public interface ChildRate extends Rate{

	public String getCodeRateLevel();

	public String getCodeProduit();

	public Integer getMaxChild();

	public Integer getMaxAdult();

	public boolean isChambreSepare();

	public String getCodeDevise();

    public List<ChildRateServiceData> getServices();

    public ChildRateServiceData getService(int index);

}
