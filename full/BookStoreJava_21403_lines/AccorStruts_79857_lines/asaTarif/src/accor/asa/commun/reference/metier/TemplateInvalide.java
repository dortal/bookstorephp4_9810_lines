package com.accor.asa.commun.reference.metier;

import java.util.List;

/**
 * Created on 12 avr. 2007
 * @TODO expliquer la class
 * @author <a href="mailto:laurent.frobert@consulting-for.accor.com">lfrobert</a>
 * @version $Id$
 */
@SuppressWarnings("unchecked")
public class TemplateInvalide {
    String id;
    String typeOffre;
    String fichier;
   
	List errors;
    
    public final List getErrors() {
        return errors;
    }
    public final void setErrors(List errors) {
        this.errors = errors;
    }
    public final String getFichier() {
        return fichier;
    }
    public final void setFichier(String fichier) {
        this.fichier = fichier;
    }
    public final String getId() {
        return id;
    }
    public final void setId(String id) {
        this.id = id;
    }
    
    public final String getTypeOffre() {
        return typeOffre;
    }
    public final void setTypeOffre(String typeOffre) {
        this.typeOffre = typeOffre;
    }
}
