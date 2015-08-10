package com.accor.asa.commun.habilitation.metier.optionmenu;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 9 oct. 2006
 * Time: 14:54:08
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings("serial")
public class OptionMenuRole implements Serializable {
    protected String 	codeOption;
    protected String 	codeRole;

    /**
     * Constructeur
     */
    public OptionMenuRole() {
    }

    /**
     * Constructeur
     * @param codeOption
     * @param codeRole
     */
    public OptionMenuRole( String codeRole, String codeOption ) {
        this.codeOption = codeOption;
        this.codeRole = codeRole;
    }

    public String getCodeOption() {
        return codeOption;
    }

    public void setCodeOption(String codeOption) {
        this.codeOption = codeOption;
    }

    public String getCodeRole() {
        return codeRole;
    }

    public void setCodeRole(String codeRole) {
        this.codeRole = codeRole;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final OptionMenuRole that = (OptionMenuRole) o;

        if (!codeOption.equals(that.codeOption)) return false;
        if (!codeRole.equals(that.codeRole)) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = codeOption.hashCode();
        result = 29 * result + codeRole.hashCode();
        return result;
    }

    /**
     * To string
     * @return
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[codeOption=").append(codeOption).append("]  ");
        sb.append("[codeRole=").append(codeRole).append("]  ");
        return sb.toString();
    }
}
