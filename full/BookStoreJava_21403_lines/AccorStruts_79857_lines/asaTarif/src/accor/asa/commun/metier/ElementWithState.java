package com.accor.asa.commun.metier;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 2 août 2007
 * Time: 17:21:13
 */
@SuppressWarnings("serial")
public class ElementWithState  extends Element {

    protected String codeGroupe;
    protected boolean state;

    public ElementWithState() {
    }

    public ElementWithState(String code, String libelle, String codeGroupe, boolean state) {
        super(code, libelle);
        this.codeGroupe = codeGroupe;
        this.state = state;
    }

    public String getCodeGroupe() {
        return codeGroupe;
    }

    public void setCodeGroupe(String codeGroupe) {
        this.codeGroupe = codeGroupe;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append(code).append(", ").
              append(libelle).append(", ").
              append(codeGroupe).append(", ").
              append(state);
      return sb.toString();
    }
}
