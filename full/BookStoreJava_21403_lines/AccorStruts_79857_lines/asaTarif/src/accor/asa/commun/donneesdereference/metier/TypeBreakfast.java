package com.accor.asa.commun.donneesdereference.metier;

import javax.servlet.http.HttpServletRequest;

public class TypeBreakfast extends DonneeReference {
    private String description;
    private String refTars;
    
    public String getDescription() {
        return description;
    }

    public String getRefTars() {
        return refTars;
    }

    public void setDescription(String string) {
        description = string;
    }

    public void setRefTars(String string) {
        refTars = string;
    }

    public DonneeReference getInstance(HttpServletRequest req) {
        TypeBreakfast result = new TypeBreakfast();
        result.setCode(req.getParameter("code"));
        result.setLibelle(req.getParameter("libelle"));
        result.setDescription(req.getParameter("description"));
        result.setRefTars(req.getParameter("codeTars"));
        return result;
    }
}