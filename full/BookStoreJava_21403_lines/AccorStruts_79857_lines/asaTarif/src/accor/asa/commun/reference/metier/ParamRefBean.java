package com.accor.asa.commun.reference.metier;

import java.util.ArrayList;
import java.util.List;

import com.accor.asa.commun.metier.ElementWithState;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 30 juil. 2007
 * Time: 10:43:14
 */
@SuppressWarnings("serial")
public class ParamRefBean extends RefBean {

    @SuppressWarnings("unchecked")
	protected List<ElementWithState> params = new ArrayList<ElementWithState>();
    protected String codeGroupe;
    protected String libelleGroupe;

    public ParamRefBean() {
    }

    public List<ElementWithState> getParams() {
        return params;
    }

    public void setParams(List<ElementWithState> params) {
        this.params = params;
    }

    public void addParam (ElementWithState rl) {
        this.params.add(rl);
    }

    public String getCodeGroupe() {
        return codeGroupe;
    }

    public void setCodeGroupe(String codeGroupe) {
        this.codeGroupe = codeGroupe;
    }

    public String getLibelleGroupe() {
        return libelleGroupe;
    }

    public void setLibelleGroupe(String libelleGroupe) {
        this.libelleGroupe = libelleGroupe;
    }
    public String toString () {
        StringBuffer sb = new StringBuffer();
        sb.append(getClass().getName()).
                append(" : code=").append(code).
                append(", libelle=").append(libelle).
                append(", codeGroupe=").append(codeGroupe).
                append(", libelleGroupe=").append(libelleGroupe).append("\n");
        if (params!=null)
            for (int i=0, size=params.size(); i<size; i++)
                sb.append(i).append(":").append(params.get(i));    
        return sb.toString();
    }


}

