package com.accor.asa.commun.habilitation.persistance;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.DAO;
import com.accor.asa.vente.commun.PoolVenteFactory;
import com.accor.asa.vente.commun.habilitation.metier.ObjetMaitreHabilitation;

/**
 * Les regles de visibilite sont invoquees directement en JDBC car le nom de la
 * procedure stockee a appeler est dynamique. En effet, il depend de l'ecran sur
 * lequel on se trouve.
 */
public class HabilitationQBEDAO extends DAO {
    private static HabilitationQBEDAO _dao = null;

    static {
        _dao = new HabilitationQBEDAO();
    }

    /**
     * put your documentation comment here
     *
     * @return
     */
    public static HabilitationQBEDAO getInstance() {
        return _dao;
    }

    /**
     * appel de la procédure d'habilitation
     *
     * @return true si visible
     */
    public boolean appliquerVisibiliteHabil(Contexte contexte, ObjetMaitreHabilitation objMaitreHabil, String codeAxe)
            throws TechnicalException {
        boolean bRet = false;

        String codeUtilisateur = contexte.getCodeUtilisateur();
        String nomProcVisibiliteHabil = objMaitreHabil.getTypeObjet().getNomProcedureCalculHabilitation();
        String codeATester = objMaitreHabil.getCodeObjetMaitre();

        Connection cnx = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        String LOG_DAO =
                "HabilitationQBEDAO :: appliquerVisibiliteHabil :: "
                + nomProcVisibiliteHabil
                + " (paramètres : Login "
                + codeUtilisateur
                + ", CodeATester "
                + codeATester
                + ", CodeAxe "
                + codeAxe
                + ")";

        // systèmatiquement tracer l'accès au DAO
        if (LogCommun.isTechniqueDebug()) {
            LogCommun.debug(contexte.getCodeUtilisateur(), "HabilitationQBEDAO", LOG_DAO);
        }
        try {
            cnx = PoolVenteFactory.getInstance().getConnectionQBE();

            cs = cnx.prepareCall("{call " + nomProcVisibiliteHabil + " (?, ?, ?)}");

            cs.setString(1, codeUtilisateur);
            cs.setString(2, codeATester);
            cs.setInt(3, Integer.parseInt(codeAxe));

            rs = cs.executeQuery();

            StringBuffer qbeQuery = new StringBuffer("");
            qbeQuery.append(nomProcVisibiliteHabil);
            qbeQuery.append(" \"" + codeUtilisateur + "\", ");
            qbeQuery.append(" \"" + codeATester + "\", ");
            qbeQuery.append(codeAxe);

            /* Si une valeur "1" est retournée par la requete,
               alors on a la visibilite
            */
            int nbRows = 0;
            if (rs.next()) {
                nbRows++;
                bRet = rs.getBoolean(1);
                LogCommun.traceQBE(contexte.getCodeUtilisateur(), "resultat query = " + bRet, 1);
            }

            LogCommun.traceQBE(contexte.getCodeUtilisateur(), qbeQuery.toString(), nbRows);
            if (LogCommun.isFonctionelleDebug()) {
                LogCommun.traceFonctionnelle(contexte.getCodeUtilisateur(), nomProcVisibiliteHabil, "INS", codeUtilisateur);
            }

        } catch (SQLException ex) {
            LogCommun.major(contexte.getCodeUtilisateur(), "HabilitationQBEDAO", "appliquerVisibiliteHabil", LOG_DAO, ex);
            throw new TechnicalException(ex);
        } catch (Exception ex) {
            LogCommun.major(contexte.getCodeUtilisateur(), "HabilitationQBEDAO", "appliquerVisibiliteHabil", LOG_DAO, ex);
            throw new TechnicalException(ex);
        } finally {
            releaseConnection(cnx, cs);
        }

        return bRet;

    }
}
