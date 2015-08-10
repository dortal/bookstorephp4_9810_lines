package com.accor.asa.rate.util;

import com.accor.asa.commun.habilitation.metier.Role;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.rate.model.ContractableRate;

public class ContractableRateStatusManager {
    public static String CODE_ROLE_HOTEL = "10";
    public static boolean getAccesModeForRate(ContractableRate rate, boolean isScreenReadOnly, Contexte contexte) {
        if(isScreenReadOnly) return isScreenReadOnly;
        Role role = contexte.getProfil().getRole();
        return rate.isWithContracts() && CODE_ROLE_HOTEL.equals(role.getCode());
    }
}
