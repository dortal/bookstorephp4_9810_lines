/**
 *
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
package com.accor.asa.rate.service.process.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.habilitation.metier.Droit;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.SQLCallExecuter;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.rate.RatesTechnicalException;
import com.accor.asa.rate.common.Log;

public class RateHabilitationDAO {
    private static RateHabilitationDAO ourInstance = new RateHabilitationDAO();

    public static RateHabilitationDAO getInstance() {
        return ourInstance;
    }

    private RateHabilitationDAO() {
    }

    /**
     * Vérifier une regle d'habilitation tarif 
     * @param idRegle
     * @param codeHotel
     * @param idPeriodeValidite
     * @param idFamilleTarif
     * @param idStatut
     * @param codeRole
     * @param contexte
     * @return
     * @throws RatesTechnicalException
     */
    public Droit verifierRegle(final int idRegle,
                               final String codeHotel,
                               final int idPeriodeValidite,
                               final int idFamilleTarif,
                               final int idStatut,
                               final String codeRole,
                               final Contexte contexte)
            throws RatesTechnicalException {
        try {
            Boolean res = (Boolean) SQLCallExecuter.getInstance().executeSelectSingleObjetProc(
                    "tarif_habil_regle"+idRegle,
                    new SQLParamDescriptorSet(new SQLParamDescriptor[]{
                            new SQLParamDescriptor(codeHotel, false, Types.VARCHAR),
                            new SQLParamDescriptor(idPeriodeValidite, false, Types.INTEGER),
                            new SQLParamDescriptor(idFamilleTarif, false, Types.INTEGER),
                            new SQLParamDescriptor(idStatut, false, Types.INTEGER),
                            new SQLParamDescriptor(new Integer(codeRole), false, Types.INTEGER)
                    }),
                    "RateHabilitationDAO", "verifierRegle",
                    contexte.getContexteAppelDAO(),
                    new SQLResultSetReader() {
                        public Object instanciateFromLine(ResultSet rs) throws SQLException {
                            return rs.getBoolean("isOk");
                        }
                    });
            if (res)
                return Droit.ECRITURE;
            else
                return Droit.LECTURE;
        } catch (SQLException e) {
            Log.major(contexte.getCodeUtilisateur(), "RateHabilitationDAO", "verifierRegle",
                    "echec lors de la verification de la regle tarif_habil_regle"+idRegle, e);
            throw new RatesTechnicalException(e);
        }catch(TechnicalException e){
        	 throw new RatesTechnicalException(e);
        }
    }

}
