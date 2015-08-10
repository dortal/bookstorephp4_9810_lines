package com.accor.asa.rate.model;

public interface ContractableRate extends Rate{

    public boolean isWithContracts();

    public boolean isRowReadOnly();

}
