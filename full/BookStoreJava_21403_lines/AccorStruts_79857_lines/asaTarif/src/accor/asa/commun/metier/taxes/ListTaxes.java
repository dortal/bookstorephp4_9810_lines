package com.accor.asa.commun.metier.taxes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 31 janv. 2006
 * Time: 10:25:14
 * To change this template use File | Settings | File Templates.
 */
public class ListTaxes  implements Serializable {
    protected List<Taxes> taxes = new ArrayList<Taxes>();

    public ListTaxes() {
    }
    // ====================== GETTER AND SETTER ====================================

    //......

    //==============================================================================
    //===============           GESTION DES  Taxes
    //==============================================================================
    /**
     * Get the count of elements in the liste.
     * @return  int
     */
    public int getTaxesCount () {
        if (taxes.isEmpty())  return (0);
        return (taxes.size());
    }
    /**
     * Get the Liste of object.
     * @return   List
     */
    public List<Taxes> getTaxes () {
        return taxes;
    }
    /**
     * Get the object at index.
     * @param index
     * @return  Taxes
     */
    public Taxes getTaxesAt (int index) {
        if (index < 0 || index >= taxes.size()) return (null);
        return taxes.get(index);
    }

    /**
     * Set the liste.
     * @param newList
     */
    public void setTaxes (List<Taxes> newList) {
        taxes.clear();
        if (newList!=null && !newList.isEmpty())
            taxes.addAll(newList);
    }
    /**
     * Set the object obj at index
     * @param obj
     * @param index
     */
    public void setTaxesAt (Taxes obj, int index) {
        if (obj == null)  return;
        if (index < 0 || index >= taxes.size()) return;
        taxes.set(index, obj);
    }
    /**
     * Add object in the list
     * @param obj
     */
    public void addTaxes (Taxes obj) {
        if (obj == null) return;
        taxes.add(obj);
    }
    /**
     * Remove the object obj
     * @param obj
     */
    public void removeTaxes (Taxes obj) {
        if (obj == null) return;
        taxes.remove(obj);
    }
    /**
     * Remove the object at index
     * @param index
     */
    public void removeTaxesAt (int index) {
        if (index < 0 || index >= taxes.size()) {
            return;
        }
        taxes.remove(index);
    }
    /**
     * toString
     * @return String
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(">>> LISTE Taxes  >>> ");
        for (int i=0; i <taxes.size(); i++ )
            sb.append("  {").append(taxes.get(i)).append("}  ");
        return sb.toString();
    }


}
