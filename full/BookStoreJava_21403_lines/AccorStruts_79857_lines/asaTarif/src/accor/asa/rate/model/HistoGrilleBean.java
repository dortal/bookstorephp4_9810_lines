package com.accor.asa.rate.model;

import java.io.Serializable;

import com.accor.asa.commun.user.metier.User;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class HistoGrilleBean implements Serializable {
    GrilleBean grille;
    User userCreation;
    User userAddCXX;
    User userLastModif;


    public GrilleBean getGrille() {
        return grille;
    }

    public void setGrille(GrilleBean grille) {
        this.grille = grille;
    }

    public User getUserCreation() {
        return userCreation;
    }

    public void setUserCreation(User userCreation) {
        this.userCreation = userCreation;
    }

    public User getUserAddCXX() {
        return userAddCXX;
    }

    public void setUserAddCXX(User userAddCXX) {
        this.userAddCXX = userAddCXX;
    }

    public User getUserLastModif() {
        return userLastModif;
    }

    public void setUserLastModif(User userLastModif) {
        this.userLastModif = userLastModif;
    }
}
