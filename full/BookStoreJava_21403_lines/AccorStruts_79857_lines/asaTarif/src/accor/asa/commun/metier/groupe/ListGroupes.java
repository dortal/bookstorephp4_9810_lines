package com.accor.asa.commun.metier.groupe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 5 mai 2006
 * Time: 10:57:51
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings("serial")
public class ListGroupes  implements Serializable {
    protected List<Groupe> groupes = new ArrayList<Groupe>();

    public ListGroupes() {
    }
    // ====================== GETTER AND SETTER ====================================

    //......

    //==============================================================================
    //===============           GESTION DES  Groupes
    //==============================================================================
    /**
     * Get the count of elements in the liste.
     * @return  int
     */
    public int getGroupesCount () {
        if (groupes.isEmpty())  return (0);
        return (groupes.size());
    }
    /**
     * Get the Liste of object.
     * @return   List
     */
    public List<Groupe> getGroupes () {
        return groupes;
    }
    /**
     * Get the object at index.
     * @param index
     * @return  Groupes
     */
    public Groupe getGroupesAt (int index) {
        if (index < 0 || index >= groupes.size()) return (null);
        return ((Groupe)groupes.get(index));
    }

    /**
     * Set the liste.
     * @param newList
     */
    public void setGroupes (List<Groupe> newList) {
        groupes.clear();
        if (newList!=null && !newList.isEmpty())
            groupes.addAll(newList);
    }
    /**
     * Set the object obj at index
     * @param obj
     * @param index
     */
    public void setGroupesAt (Groupe obj, int index) {
        if (obj == null)  return;
        if (index < 0 || index >= groupes.size()) return;
        groupes.set(index, obj);
    }
    /**
     * Add object in the list
     * @param obj
     */
    public void addGroupes (Groupe obj) {
        if (obj == null) return;
        groupes.add(obj);
    }
    /**
     * Remove the object obj
     * @param obj
     */
    public void removeGroupes (Groupe obj) {
        if (obj == null) return;
        groupes.remove(obj);
    }
    /**
     * Remove the object at index
     * @param index
     */
    public void removeGroupesAt (int index) {
        if (index < 0 || index >= groupes.size()) {
            return;
        }
        groupes.remove(index);
    }
    /**
     * toString
     * @return String
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(">>> LISTE Groupes  >>> ");
        for (int i=0; i <groupes.size(); i++ )
            sb.append("  {").append(groupes.get(i)).append("}  ");
        return sb.toString();
    }
}

