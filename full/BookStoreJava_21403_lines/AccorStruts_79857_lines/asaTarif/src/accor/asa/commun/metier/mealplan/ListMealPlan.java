package com.accor.asa.commun.metier.mealplan;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractListCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 10 avr. 2007
 * Time: 11:12:17
 */
@SuppressWarnings("serial")
public class ListMealPlan extends AbstractListCachable implements CachableInterface {

    /**
     * Constructeur
     * @param ListMealPlan
     * @param codeLangue
     */
    public ListMealPlan( List listMealPlan, String codeLangue ) {
        this.elements = listMealPlan;
        this.params = new String[1];
        this.params[0] = codeLangue;
    }

    public String getCodeLangue() {
        return this.params[0];
    }

    public void setCodeLangue(String codeLangue) {
        this.params[0] = codeLangue;
    }
}
