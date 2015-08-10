package com.accor.asa.commun.user.persistance;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.metier.AsaDate;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.metier.Coordinates;
import com.accor.asa.commun.persistance.DAO;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.user.metier.CritereRecherche;
import com.accor.asa.commun.user.metier.User;
import com.accor.asa.commun.user.metier.exception.TropDUtilisateursException;

/**
  * aide pour la recheche d'utilisateur : mise en commun du code pour
  * la recherche JDBC multi-critères.
  * Utilisé dans les UtilisateurDAO des modules Vente, Tarif et Admin
  */
public class SearchUserQBEDAO extends DAO {

	/* singleton */
	private static SearchUserQBEDAO _instance =  new SearchUserQBEDAO();
	
	private SearchUserQBEDAO() {}
	
	public static SearchUserQBEDAO getInstance() {
		return _instance;
	}

	/**
	 * Recherche multi-criteres sur les utilisateurs
	 * @param login
	 * @param Les criteres de recherche @see com.accor.commun.user.metier.CritereRecherche
	 * @return List<User>
	 * @throws com.accor.commun.TechnicalException
	 * @throws com.accor.commun.utilisateur.metier.TropDUtilisateursException si on 
	 *          dépasse le nombre max de lignes (par défaut celui des grandes listes)
	 */
	public List<User> searchUsers( final CritereRecherche critere, final Contexte contexte ) 
			throws TechnicalException, TropDUtilisateursException {

		Connection conn = null;
		PreparedStatement stm = null;
		int nbRows = 0;

		// limitation du nombre de lignes
		int nbMaxCritere = critere.getNbRetourLignesMax();
		boolean rowLimit = ( nbMaxCritere > 0 );

		try {
			conn = PoolCommunFactory.getInstance().getConnectionQBE();
			
			// apllication du setRowCount
			if( rowLimit )
				super.setRowCount( conn, nbMaxCritere + 1 );

			// contiendra les parametres retenus
			List<SqlParam> params = new ArrayList<SqlParam>();

			String listeRolesResponsables = null;
			if( critere.isResponsableDossier() ) {
				listeRolesResponsables = getRolesResponsableDossier( conn );
				if( listeRolesResponsables == null )
					return new ArrayList<User>();
			}
			// 1) fabrication de la requête
			String query = createSqlQueryFromCriteria(critere, params, listeRolesResponsables);

			// 2) création du statement
			stm = conn.prepareStatement(query);

			// 3) passage des paramètres
			StringTokenizer skQuery = new StringTokenizer(query, "?");
			StringBuffer sQuery = new StringBuffer();
			int vParamsSize = params.size();
			for (int i = 0; i < vParamsSize; i++) {
				SqlParam sp = params.get(i);
				String value = sp.value;
				sQuery.append(skQuery.nextToken());
				switch (sp.typeSql) {
					case Types.VARCHAR :
						stm.setString(i + 1, value);
						sQuery.append("\"" + value + "\"");
						break;
					case Types.INTEGER :
						stm.setInt(i + 1, Integer.parseInt(value));
						sQuery.append(value);
						break;
					case Types.NUMERIC :
						stm.setLong(i + 1, Integer.parseInt(value));
						sQuery.append(value);
						break;
					case Types.DATE :
						SimpleDateFormat dParse = new SimpleDateFormat("dd/MM/yyyy");
						Date dateSql = new Date(dParse.parse(value).getTime());
						stm.setDate(i + 1,dateSql);
						break;
					default :
						throw new TechnicalException("type inattendu : Type sql #" + sp.typeSql);
				}
			}
			
			while( skQuery.hasMoreTokens() )
				sQuery.append(skQuery.nextToken());

			// 4) execution reqête 
			ResultSet rs = stm.executeQuery();
			
			List<User> users = new ArrayList<User>();

			while( rs.next() ) {
				nbRows++; /* pour la log */
				User user = new User();

				user.setLogin( rs.getString( "login" ) );
				user.setLanguageCode( StringUtils.trimToNull( rs.getString( "codelangue" ) ) );
				user.setCountryCode( rs.getString( "codepays" ) );
				user.setCity( StringUtils.trimToNull( rs.getString( "ville" ) ) );
				user.setSaleZoneId( StringUtils.trimToNull( rs.getString( "idzonevente") ) );
				user.setSaleRegionId( StringUtils.trimToNull( rs.getString( "idregionVente") ) );
				user.setMarketId( rs.getString( "idmarche" ) );
				user.setLastName( StringUtils.trimToNull( rs.getString( "nom" ) ) );
				user.setFirstName( StringUtils.trimToNull( rs.getString( "prenom" ) ) );
				user.setProfileId( StringUtils.trimToNull( rs.getString( "idprofile" ) ) );

				Coordinates coord = user.getCoordinates();
				coord.setPhone( StringUtils.trimToNull( rs.getString( "telephone" ) ) );
				coord.setFax( StringUtils.trimToNull( rs.getString( "telecopie" ) ) );
				coord.setMail( StringUtils.trimToNull( rs.getString( "email" ) ) );
				user.setCoordinates( coord );
				
				user.setStatus( StringUtils.trimToNull( rs.getString( "status" ) ) );
				user.setComments( rs.getString( "commentaires" ) );
				user.setCivility( StringUtils.trimToNull( rs.getString( "civilite" ) ) );
				user.setSaleOfficeCode( rs.getInt( "codebureauvente" ) );
				user.setManagerCode( rs.getString( "codeutilisateurchef" ) );
				user.setId( new Integer( rs.getInt( "id_intervenant" ) ) );
				user.setDefaultTerritoryId( StringUtils.trimToNull( rs.getString( "id_territoire_default" ) ) );
				user.setHotelId( StringUtils.trimToNull( rs.getString( "idhotel" ) ) );

				user.setDateCreation(new AsaDate(rs.getDate("creationDate")));
				user.setDateSuppression(new AsaDate(rs.getDate("suppressionDate")));
				user.setDateMaj(new AsaDate(rs.getDate("majDate")));
				// le nom "réel" de l'hôtel est récupéré dans LOCAL..HOTEL, 
				// et non plus dans UTILISATEURS.Nom
				user.setHotelName( StringUtils.trimToNull( rs.getString( "nomhotel" ) ) );
				users.add( user );
			}
			LogCommun.traceQBE( contexte.getCodeUtilisateur(), sQuery.toString(), nbRows );
			

			if( rowLimit && nbRows > nbMaxCritere )
				throw new TropDUtilisateursException("" + nbRows);

			return users;

		} catch (TropDUtilisateursException e) {
			if (LogCommun.isTechniqueDebug()) {
				LogCommun.info( contexte.getCodeUtilisateur(), "SearchUserQBEDAO", "searchUsers", 
						"trop d'utilisateurs : " + nbRows );
			}
			throw e;
		} catch (Exception e) {
			LogCommun.major( contexte.getCodeUtilisateur(), "SearchUserQBEDAO", "searchUsers", 
					"SQLException " + e.getMessage() );
			throw new TechnicalException(e);
		} finally {
	        try {
	            if( rowLimit )
	            	setRowCount(conn,0);
	        } catch (Exception e) {
				LogCommun.major( contexte.getCodeUtilisateur(), "SearchUserQBEDAO", "searchUsers", 
						"SQLException " + e.getMessage() );
				throw new TechnicalException(e);
	        } finally {
	            releaseConnection( conn, stm );
	        }
		}
	}

	private String getRolesResponsableDossier( Connection cnt ) throws SQLException {
		
		PreparedStatement stm = null;
		
		StringBuffer query = new StringBuffer();
		query.append("SELECT codeRole");
		query.append(" FROM HABIL_ROLE");
		query.append(" WHERE responsable_dossier = 1");
		
		stm = cnt.prepareStatement( query.toString() );
		ResultSet rs = stm.executeQuery();
		List<String> resultList = new ArrayList<String>();
		while( rs.next() ) { 
			resultList.add( rs.getString( "codeRole" ) );
		}
		
		if( resultList.size() == 0 )
			return null;
		
		Iterator<String> iter = resultList.iterator();
		StringBuffer resultString = new StringBuffer();
		resultString.append( "(" ).append( iter.next() );
		while( iter.hasNext() ) {
			resultString.append(",").append( iter.next() );
		}
		resultString.append(")");
		
		return resultString.toString();	
	}

	/**
	 * création de la string de SQL d'après les critères de recherche
	 * remplit le vecteur avec les paramètres non vides
	 */
	private String createSqlQueryFromCriteria(CritereRecherche critere, List<SqlParam> params, String listeRolesResponsables) {

		// le principe est de remplir condition par condition

		// partie WHERE de la clause
		StringBuffer sbWhere = new StringBuffer();
		ajouteLigne(sbWhere, params, critere.getLogin(), "U.login = ?");
		ajouteLigne(sbWhere, params, critere.getManagerCode(), "U.codeutilisateurchef = ?");
		ajouteLigne(sbWhere, params, critere.getHotelId(), "U.idhotel = ?");
		ajouteLigne(sbWhere, params, critere.getLanguageCode(), "U.codelangue = ?");

		boolean critereBV = !( ( critere.getSaleOfficeCode() == null ) 
								&& isEmptyString( critere.getCountryCode() ) 
								&& isEmptyString( critere.getCity() ) );
		if (critereBV) {
			StringBuffer sbBv = new StringBuffer();

			sbBv.append(" exists ( select B.CODE_BUREAU_VENTE FROM BUREAU_VENTE B ");
			sbBv.append("where B.CODE_BUREAU_VENTE = U.CODEBUREAUVENTE");
			if( critere.getSaleOfficeCode() != null )
				ajouteLigne(sbBv, params, critere.getSaleOfficeCode().toString(), "B.CODE_BUREAU_VENTE = ?", Types.NUMERIC);
			ajouteLigne(sbBv, params, critere.getCountryCode(), "B.codepays = ?");
			ajouteLigne(sbBv, params, critere.getCity(), "B.ville = ?");
			sbBv.append(" )");
			// on recolle
			if (sbWhere.length() > 0)
				sbWhere.append(" AND ");
			sbWhere.append(sbBv);
		}

		ajouteLigne(sbWhere, params, replaceCriteriaValue(critere.getSaleZoneId(), ""), "U.idzonevente = ?");
		ajouteLigne(sbWhere, params, replaceCriteriaValue(critere.getSaleRegionId(), ""), "U.idregionvente = ?");
		ajouteLigne(sbWhere, params, critere.getMarketId(), "U.idmarche = ?", Types.INTEGER);
		ajouteLigne(sbWhere, params, critere.getLastName(), "U.nom like ?", true);
		ajouteLigne(sbWhere, params, critere.getFirstName(), "U.prenom like ?", true);
		ajouteLigne(sbWhere, params, critere.getProfileId(), "U.idprofile = ?");
		ajouteLigne(sbWhere, params, critere.getPhone(), "U.telephone = ?");
		ajouteLigne(sbWhere, params, critere.getFax(), "U.telecopie = ?");
		ajouteLigne(sbWhere, params, critere.getMail(), "U.email like ?", true);
		ajouteLigne(sbWhere, params, critere.getStatus(), "U.status = ?");
		ajouteLigne(sbWhere, params, critere.getCivility(), "U.civilite = ?");
		
		if(critere.getDeletedDateDebut() != null || critere.getDeletedDateFin() != null){
			if(critere.getDeletedDateDebut().equals(critere.getDeletedDateFin() )){
				ajouteLigne(sbWhere, params, critere.getDeletedDateDebut(), "U.date_suppression = ?",Types.DATE);
			}else if(critere.getDeletedDateDebut() == null){
				ajouteLigne(sbWhere, params, critere.getDeletedDateFin(), "U.date_suppression = ?",Types.DATE);
			}else if(critere.getDeletedDateFin() == null){
				ajouteLigne(sbWhere, params, critere.getDeletedDateDebut(), "U.date_suppression = ?",Types.DATE);
			}else{
				ajouteLigne(sbWhere, params, critere.getDeletedDateDebut(), "U.date_suppression >= ?",Types.DATE);
				ajouteLigne(sbWhere, params, critere.getDeletedDateFin(), "U.date_suppression <= ?",Types.DATE);
			}
		}
		
		if(critere.getCreationDateDebut() != null || critere.getCreationDateFin() != null){
			if(critere.getCreationDateDebut().equals(critere.getCreationDateFin() )){
				ajouteLigne(sbWhere, params, critere.getCreationDateDebut(), "U.date_creation = ?",Types.DATE);
			}else if(critere.getCreationDateDebut() == null){
				ajouteLigne(sbWhere, params, critere.getCreationDateFin(), "U.date_creation = ?",Types.DATE);
			}else if(critere.getCreationDateFin() == null){
				ajouteLigne(sbWhere, params, critere.getCreationDateDebut(), "U.date_creation = ?",Types.DATE);
			}else{
				ajouteLigne(sbWhere, params, critere.getCreationDateDebut(), "U.date_creation >= ?",Types.DATE);
				ajouteLigne(sbWhere, params, critere.getCreationDateFin(), "U.date_creation <= ?",Types.DATE);
			}
		}
		
		if(critere.getMajDateDebut() != null || critere.getMajDateFin() != null){
			if(critere.getMajDateDebut().equals(critere.getMajDateFin() )){
				ajouteLigne(sbWhere, params, critere.getMajDateDebut(), "U.date_maj = ?",Types.DATE);
			}else if(critere.getMajDateDebut() == null){
				ajouteLigne(sbWhere, params, critere.getMajDateFin(), "U.date_maj = ?",Types.DATE);
			}else if(critere.getMajDateFin() == null){
				ajouteLigne(sbWhere, params, critere.getMajDateDebut(), "U.date_maj = ?",Types.DATE);
			}else{
				ajouteLigne(sbWhere, params, critere.getMajDateDebut(), "U.date_maj >= ?",Types.DATE);
				ajouteLigne(sbWhere, params, critere.getMajDateFin(), "U.date_maj <= ?",Types.DATE);
			}
		}

		if( critere.getSaleOfficeCode() != null )
			ajouteLigne(sbWhere, params, critere.getSaleOfficeCode().toString(), "U.codebureauvente = ?", Types.NUMERIC);

		/* on prend seulement les utilisateurs qui ont '_all' ou rien */
		if (sbWhere.length() > 0)
			sbWhere.append(" AND ");
		sbWhere.append("(U.login*=V.login AND V.code_valeur='_all')");
		
		if (critere.isResponsableDossier())
			sbWhere.append(" AND U.idprofile = P.code_profile AND P.code_role_vente IN "+listeRolesResponsables);
		
		sbWhere.append(" order by U.nom, U.prenom");

		StringBuffer sbSel = new StringBuffer();
		sbSel.append("SELECT distinct");
		sbSel.append(" U.login, U.codelangue, U.codepays, U.ville, U.idzonevente,");
		sbSel.append(" U.idregionVente, U.idmarche, U.nom, U.prenom, U.idprofile, U.telephone,");
		sbSel.append(" U.telecopie, U.status, U.email, U.commentaires, U.civilite, U.codebureauvente,");
		sbSel.append(" U.date_creation creationDate, U.date_suppression suppressionDate, U.date_maj majDate,");
		sbSel.append(" U.codeutilisateurchef, V.code_valeur, U.id_intervenant, U.id_territoire_default, U.idhotel, H.NOMHOTEL");
		sbSel.append(" FROM UTILISATEURS U, HABIL_VISIBILITE V, LOCAL..HOTEL H, HABIL_PROFILE P");
		sbSel.append(" WHERE ");
		sbSel.append(" H.CODEHOTEL =* U.IDHOTEL ");
		sbSel.append(" AND ");

		return "" + sbSel + sbWhere;
	}

	/**
	 * Verifie que le parametre 's' est correct. Sinon, renvoie le parametre 'defaut'
	 */
	private String replaceCriteriaValue(String s, String defaut) {
		return ((s == null) || s.trim().equals("") || s.trim().equals("ALL")) ? defaut : s;
	}

	private void ajouteLigne(StringBuffer sb, List<SqlParam> params, String value, String sql) {
		ajouteLigne(sb, params, value, sql, Types.VARCHAR);
	}

	private void ajouteLigne(StringBuffer sb, List<SqlParam> params, String value, String sql, boolean addPercent) {
		ajouteLigne(sb, params, value, sql, Types.VARCHAR, value + '%');
	}

	private void ajouteLigne(StringBuffer sb, List<SqlParam> params, String value, String sql, int typeSql) {
		ajouteLigne(sb, params, value, sql, typeSql, value);
	}
	/**
	 * @param index numéro du dernier paramètre 
	 * @return le numéro du prochain paramètre (=index +1 si inséré)
	 */
	private void ajouteLigne(StringBuffer sb, List<SqlParam> params, String value, String sql, int typeSql, String valueToPass) {
		// tster si paramètre à insérer dans la requête
		if (!isEmptyString(value)) {
			// tester si premier paramètre
			if (sb.length() > 0)
				sb.append(" AND ");
			sb.append(" ( ");
			// ajouter le code
			sb.append(sql);
			// passage au statement
			params.add(new SqlParam(valueToPass, typeSql));
			sb.append(" ) ");
		}
	}

	/* @todo déplacer dans StringUtils dans Commun*/
	private boolean isEmptyString(String s) {
		return (s == null || s.trim().equals(""));
	}
}

/** pour contenir un paramètre SQL et son type */
class SqlParam {
	SqlParam(String value, int typeSql) {
		this.value = value;
		this.typeSql = typeSql;
	}
	String value;
	int typeSql;

	public String toString() {
		return value + " [" + typeSql + "]";
	}

}