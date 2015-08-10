package com.accor.asa.commun.metier.periode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 5 mai 2006
 * Time: 10:58:09
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings("serial")
public class ListPeriodes  implements Serializable {
    protected List<Periode> periodes = new ArrayList<Periode>();

    public ListPeriodes() {
    }
    // ====================== GETTER AND SETTER ====================================

    //......

    //==============================================================================
    //===============           GESTION DES  Periodes
    //==============================================================================
    /**
     * Get the count of elements in the liste.
     * @return  int
     */
    public int getPeriodesCount () {
        if (periodes.isEmpty())  return (0);
        return (periodes.size());
    }
    /**
     * Get the Liste of object.
     * @return   List
     */
    public List<Periode> getPeriodes () {
        return periodes;
    }
    /**
     * Get the object at index.
     * @param index
     * @return  Periodes
     */
    public Periode getPeriodesAt (int index) {
        if (index < 0 || index >= periodes.size()) return (null);
        return ((Periode)periodes.get(index));
    }

    /**
     * Set the liste.
     * @param newList
     */
    public void setPeriodes (List<Periode> newList) {
        periodes.clear();
        if (newList!=null && !newList.isEmpty())
            periodes.addAll(newList);
    }
    /**
     * Set the object obj at index
     * @param obj
     * @param index
     */
    public void setPeriodesAt (Periode obj, int index) {
        if (obj == null)  return;
        if (index < 0 || index >= periodes.size()) return;
        periodes.set(index, obj);
    }
    /**
     * Add object in the list
     * @param obj
     */
    public void addPeriodes (Periode obj) {
        if (obj == null) return;
        periodes.add(obj);
    }
    /**
     * Remove the object obj
     * @param obj
     */
    public void removePeriodes (Periode obj) {
        if (obj == null) return;
        periodes.remove(obj);
    }
    /**
     * Remove the object at index
     * @param index
     */
    public void removePeriodesAt (int index) {
        if (index < 0 || index >= periodes.size()) {
            return;
        }
        periodes.remove(index);
    }
    /**
     * toString
     * @return String
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(">>> LISTE Periodes  >>> ");
        for (int i=0; i <periodes.size(); i++ )
            sb.append("  {").append(periodes.get(i)).append("}  ");
        return sb.toString();
    }
}


