package com.accor.asa.rate.service.process.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.SQLCallExecuter;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.rate.RatesTechnicalException;
import com.accor.asa.rate.model.ControleFinSaisieBean;
import com.accor.asa.vente.commun.log.Log;

public class ControlesFinSaisieDAO {

	private static int PROC_KO = 0;
	private static int PROC_OK = 1;

	// Tags à remplacer dans les libellés des messages d'erreurs des contrôles
	private static String [] TAGS = new String [] { "\\[FAMILLES_TARIF\\]", "\\[PERIODES\\]", "\\[RATE_LEVELS\\]", "\\[TYPES_CHAMBRE\\]" };

	private static ControlesFinSaisieDAO ourInstance = new ControlesFinSaisieDAO();

	public static ControlesFinSaisieDAO getInstance () {
		return ourInstance;
	}

	private ControlesFinSaisieDAO () {
	}

	/**
	 * @param codeHotel
	 * @param idGroupeTarif
	 * @param idPeriodeValidite
	 * @param contexte
	 * @return la liste des contrôles qui ont raté
	 * @throws RatesTechnicalException
	 */
	public List<ControleFinSaisieBean> getControlesEnEchec (String codeHotel, int idGroupeTarif, int idPeriodeValidite, Contexte contexte) throws RatesTechnicalException {
		// Récupération de la liste des contrôles à effectuer
		List<ControleFinSaisieBean> controles = getListeControles(codeHotel, idGroupeTarif, idPeriodeValidite, contexte);
		ControleFinSaisieBean controle;
		// Suppression des controles réussis de la liste
		for (int i = controles.size() - 1; i >= 0; i --) {
			controle = controles.get(i);
			ControleFinSaisieBean resControle = execControle(controle, contexte);
			if (resControle == null)
				controles.remove(controle);
		}
		return controles;
	}

	/**
	 * @param codeHotel
	 * @param idGroupeTarif
	 * @param idPeriodeValidite
	 * @param contexte
	 * @return la liste des contrôles à exécuter
	 * @throws RatesTechnicalException
	 */
	private List<ControleFinSaisieBean> getListeControles (String codeHotel, int idGroupeTarif, int idPeriodeValidite, Contexte contexte) throws RatesTechnicalException {
		try {
			return (List<ControleFinSaisieBean>) SQLCallExecuter.getInstance().executeSelectProc(
				"tarif_selControles",
				new SQLParamDescriptorSet(new SQLParamDescriptor[]{
					new SQLParamDescriptor(codeHotel, false, Types.VARCHAR),
					new SQLParamDescriptor(idGroupeTarif, false, Types.SMALLINT),
					new SQLParamDescriptor(idPeriodeValidite, false, Types.SMALLINT),
					new SQLParamDescriptor(contexte.getCodeLangue(), false, Types.CHAR)
				}),
				"ControlesFinSaisieDAO", "getListeControles",
				contexte.getContexteAppelDAO(),
				new SQLResultSetReader() {
					public Object instanciateFromLine (ResultSet rs) throws SQLException {
						ControleFinSaisieBean controle = new ControleFinSaisieBean();
						controle.setCodeHotel(rs.getString("codeHotel"));
						controle.setIdInstance(rs.getInt("idInstance"));
						controle.setIdControle(rs.getInt("idControle"));
						controle.setIdGroupeTarif(rs.getInt("idGroupeTarif"));
						controle.setIdPeriodeValidite(rs.getInt("idPeriodeValidite"));
						controle.setLibelleMessage(rs.getString("libelleMessage"));
						controle.setBloquant(rs.getBoolean("isBloquant"));
						return controle;
					}
				}
			);
		}
		catch (SQLException ex) {
			Log.major(contexte.getCodeUtilisateur(), "ControlesFinSaisieDAO", "getListeControles",
					"Echec lors de la recuperation de la liste des controles pour l'hotel " + codeHotel, ex);
			throw new RatesTechnicalException(ex);
		}
		catch (TechnicalException ex) {
			Log.major(contexte.getCodeUtilisateur(), "ControlesFinSaisieDAO", "getListeControles",
					"Echec lors de la recuperation de la liste des controles pour l'hotel " + codeHotel, ex);
			throw new RatesTechnicalException(ex);
		}
	}

	/**
	 * @param controle à exécuter
	 * @param contexte
	 * @return null si succès, le controle passé en paramètre mis à jour si échec
	 * @throws RatesTechnicalException
	 */
	private ControleFinSaisieBean execControle (ControleFinSaisieBean controle, Contexte contexte) throws RatesTechnicalException {
		try {
			List<?> [] results = SQLCallExecuter.getInstance().executeMultipleSelectProc(
				"tarif_execControle",
				new SQLParamDescriptorSet(new SQLParamDescriptor[]{
					new SQLParamDescriptor(controle.getIdInstance(), false, Types.SMALLINT),
					new SQLParamDescriptor(controle.getCodeHotel(), false, Types.VARCHAR),
					new SQLParamDescriptor(controle.getIdPeriodeValidite(), false, Types.SMALLINT),
					new SQLParamDescriptor(contexte.getCodeLangue(), false, Types.VARCHAR)
				}),
				"ControlesFinSaisieDAO",
				"execControle",
				contexte.getContexteAppelDAO(),
				new SQLResultSetReader [] {
					new SQLResultSetReader() {
						public Object instanciateFromLine (ResultSet rs) throws SQLException {
							return rs.getInt(1);
						}
					},
					new SQLResultSetReader() {
						public Object instanciateFromLine (ResultSet rs) throws SQLException {
							return rs.getString(1);
						}
					},
					new SQLResultSetReader() {
						public Object instanciateFromLine (ResultSet rs) throws SQLException {
							return rs.getString(1);
						}
					},
					new SQLResultSetReader() {
						public Object instanciateFromLine (ResultSet rs) throws SQLException {
							return rs.getString(1);
						}
					},
					new SQLResultSetReader() {
						public Object instanciateFromLine (ResultSet rs) throws SQLException {
							return rs.getString(1);
						}
					}
				},
				false
			);
			// Résultat du controle
			Integer res = (Integer) results[0].get(0);
			if (res == PROC_OK)
				return null;
			else if (res == PROC_KO){
				// 1 = familles tarif
				// 2 = périodes
				// 3 = rate levels
				// 4 = types chambre
				for (int i = 1; i < results.length; i ++) {
					List<String> liste = (List<String>) results[i];
					if (liste.size() > 0) {
						String str = "";
						for (String s : liste)
							str += "[" + s + "] ";
						if (str.length() != 0)
							str = str.substring(0, str.length() - 1);
						// MAJ du libellé du message d'erreur
						controle.setLibelleMessage(controle.getLibelleMessage().replaceAll(TAGS[i - 1], str));
					}
				}
			}
			return controle;
		}
		catch (SQLException ex) {
			Log.major(contexte.getCodeUtilisateur(), "ControlesFinSaisieDAO", "execControle",
					"Echec lors de l'execution de l'instance de controle " + controle.getIdInstance() + " pour l'hotel " + controle.getCodeHotel(), ex);
			throw new RatesTechnicalException(ex);
		}
		catch (TechnicalException ex) {
			Log.major(contexte.getCodeUtilisateur(), "ControlesFinSaisieDAO", "execControle",
					"Echec lors de l'execution de l'instance de controle " + controle.getIdInstance() + " pour l'hotel " + controle.getCodeHotel(), ex);
			throw new RatesTechnicalException(ex);
		}
	}
}