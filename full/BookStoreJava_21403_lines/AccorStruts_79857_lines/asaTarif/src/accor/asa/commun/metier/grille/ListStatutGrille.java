package com.accor.asa.commun.metier.grille;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractListCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 6 avr. 2007
 * Time: 16:28:31
 */
@SuppressWarnings("serial")
public class ListStatutGrille  extends AbstractListCachable implements CachableInterface {

    /**
     * Constructeur
     * @param ListStatutGrille
     * @param codeLangue
     */
    public ListStatutGrille( List listStatutGrille, String codeLangue ) {
        this.elements = listStatutGrille;
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
