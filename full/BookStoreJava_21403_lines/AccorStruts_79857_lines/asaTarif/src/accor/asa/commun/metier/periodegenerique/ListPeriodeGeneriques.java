package com.accor.asa.commun.metier.periodegenerique;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 4 mai 2006
 * Time: 11:05:16
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings("serial")
public class ListPeriodeGeneriques  implements Serializable {
    protected List<PeriodeGenerique> periodeGeneriques = new ArrayList<PeriodeGenerique>();

    public ListPeriodeGeneriques() {
    }
    // ====================== GETTER AND SETTER ====================================

    //......

    //==============================================================================
    //===============           GESTION DES  PeriodeGeneriques
    //==============================================================================
    /**
     * Get the count of elements in the liste.
     * @return  int
     */
    public int getPeriodeGeneriquesCount () {
        if (periodeGeneriques.isEmpty())  return (0);
        return (periodeGeneriques.size());
    }
    /**
     * Get the Liste of object.
     * @return   List
     */
    public List<PeriodeGenerique> getPeriodeGeneriques () {
        return periodeGeneriques;
    }
    /**
     * Get the object at index.
     * @param index
     * @return  PeriodeGeneriques
     */
    public PeriodeGenerique getPeriodeGeneriquesAt (int index) {
        if (index < 0 || index >= periodeGeneriques.size()) return (null);
        return ((PeriodeGenerique)periodeGeneriques.get(index));
    }

    /**
     * Set the liste.
     * @param newList
     */
    public void setPeriodeGeneriques (List<PeriodeGenerique> newList) {
        periodeGeneriques.clear();
        if (newList!=null && !newList.isEmpty())
            periodeGeneriques.addAll(newList);
    }
    /**
     * Set the object obj at index
     * @param obj
     * @param index
     */
    public void setPeriodeGeneriquesAt (PeriodeGenerique obj, int index) {
        if (obj == null)  return;
        if (index < 0 || index >= periodeGeneriques.size()) return;
        periodeGeneriques.set(index, obj);
    }
    /**
     * Add object in the list
     * @param obj
     */
    public void addPeriodeGeneriques (PeriodeGenerique obj) {
        if (obj == null) return;
        periodeGeneriques.add(obj);
    }
    /**
     * Remove the object obj
     * @param obj
     */
    public void removePeriodeGeneriques (PeriodeGenerique obj) {
        if (obj == null) return;
        periodeGeneriques.remove(obj);
    }
    /**
     * Remove the object at index
     * @param index
     */
    public void removePeriodeGeneriquesAt (int index) {
        if (index < 0 || index >= periodeGeneriques.size()) {
            return;
        }
        periodeGeneriques.remove(index);
    }
    /**
     * toString
     * @return String
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(">>> LISTE PeriodeGeneriques  >>> ");
        for (int i=0; i <periodeGeneriques.size(); i++ )
            sb.append("  {").append(periodeGeneriques.get(i)).append("}  ");
        return sb.toString();
    }
}

