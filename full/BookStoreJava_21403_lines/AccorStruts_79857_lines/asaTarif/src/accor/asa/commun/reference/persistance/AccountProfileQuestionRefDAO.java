package com.accor.asa.commun.reference.persistance;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.accor.asa.commun.DuplicateKeyException;
import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.reference.metier.AccountProfileQuestion;
import com.accor.asa.commun.reference.metier.RefBean;
import com.accor.asa.vente.rfp.metier.dref.ListAccountProfileQuestions;


public class AccountProfileQuestionRefDAO extends RefDAO {

	private final String PROC_SELECT = null;
	private final String PROC_ADMIN_SELECT = "admin_rfp_sel_ap_question";
	private final String PROC_ADMIN_INSERT = "admin_rfp_ins_ap_question";
	private final String PROC_ADMIN_DELETE = "admin_rfp_del_ap_question";
	private final String PROC_ADMIN_UPDATE = "admin_rfp_upd_ap_question";

	protected String getProcName (int type) {
		switch (type) {
			case SELECT :
				return PROC_SELECT;
			case ADMIN_SELECT :
				return PROC_ADMIN_SELECT;
			case INSERT :
				return PROC_ADMIN_INSERT;
			case UPDATE :
				return PROC_ADMIN_UPDATE;
			case DELETE :
				return PROC_ADMIN_DELETE;
			default :
				return null;
		}
	}

	protected SQLParamDescriptorSet getProcParameters (int type, RefBean bean, String codeLangue) {
		AccountProfileQuestion q = (AccountProfileQuestion) bean;
		SQLParamDescriptor[] params = null;
		switch (type) {
			case SELECT :
				return null;
			case ADMIN_SELECT :
				return new SQLParamDescriptorSet();
			case INSERT :
				// Pas utilisé, méthode redéfinie
				return null;
			case UPDATE :
				params = new SQLParamDescriptor[7];
				params[0] = new SQLParamDescriptor( q.getId(), false, Types.INTEGER );
				params[1] = new SQLParamDescriptor( q.getLibelle(), false, Types.VARCHAR );
				params[2] = new SQLParamDescriptor( q.getPage(), false, Types.SMALLINT );
				params[3] = new SQLParamDescriptor( q.getSection(), false, Types.SMALLINT );
				params[4] = new SQLParamDescriptor( q.getOrdre(), false, Types.SMALLINT );
				params[5] = new SQLParamDescriptor( new Boolean( q.isObligatoire() ), false, Types.BOOLEAN );
				params[6] = new SQLParamDescriptor( new Boolean( q.isDefaultValue() ), false, Types.BOOLEAN );
				return new SQLParamDescriptorSet(params);
			case DELETE :
				params = new SQLParamDescriptor[1];
				params[0] = new SQLParamDescriptor( q.getId(), false, Types.INTEGER );
				return new SQLParamDescriptorSet(params);
			default :
				return null;
		}
	}

	protected SQLResultSetReader getProcReader (int type) {
		switch (type) {
			case SELECT :
				return null;
			case ADMIN_SELECT :
				return new SQLResultSetReader() {
					public Object instanciateFromLine( ResultSet rs ) throws SQLException {
						AccountProfileQuestion q = new AccountProfileQuestion();
						q.setCode( rs.getString( "idseq" ) );
						q.setLibelle( rs.getString( "libelle" ) );
						q.setPage( rs.getInt( "page" ) );
						q.setSection( rs.getInt( "section" ) );
						q.setOrdre( rs.getInt( "ordre" ) );
						q.setObligatoire( rs.getBoolean( "obligatoire" ) );
						q.setDefaultValue( rs.getBoolean( "defaultvalue" ) );
						q.setActif( StringUtils.equals( rs.getString( "supprimer" ), "0" ) );
						return q;
					}
				};
			default :
				return null;
		}
	}

	protected String getCacheClassName() {
		return ListAccountProfileQuestions.class.getName();
	}

	protected CachableInterface getObjectInCache (String codeLangue) {
		return null;
	}

	
	

	@Override
	protected CachableInterface createCacheObject(List<?> data, Contexte contexte) throws TechnicalException, IncoherenceException {
		return null;
	}

	/**
	 * Redéfinitions des méthodes mères
	 */
	public int insertRef( Contexte contexte, RefBean bean ) 
		throws TechnicalException, IncoherenceException, DuplicateKeyException {
	  
	  AccountProfileQuestion q = (AccountProfileQuestion) bean;
	  Connection conn = null;
	  CallableStatement stmt = null;
	  
	  try {
		 conn = PoolCommunFactory.getInstance().getConnection();
		 
		 stmt = conn.prepareCall( "{ ? = call " + PROC_ADMIN_INSERT + " (?,?,?,?,?,?) }" );
		 stmt.registerOutParameter( 1, Types.INTEGER );
		 stmt.setString( 2, q.getLibelle() );
		 
		 if( q.getPage() != null )
		   stmt.setInt( 3, q.getPage().intValue() );
		 else
		   stmt.setNull( 3, Types.SMALLINT );	 
		 
		 if( q.getSection() != null )
		   stmt.setInt( 4, q.getSection().intValue() );
		 else
		   stmt.setNull( 4, Types.SMALLINT );	 

		 if( q.getOrdre() != null )
		   stmt.setInt( 5, q.getOrdre().intValue() );
		 else
		   stmt.setNull( 5, Types.SMALLINT );	 
		 
		 stmt.setBoolean( 6, q.isObligatoire() );
		 stmt.setBoolean( 7, q.isDefaultValue() );
		 
		 stmt.execute();
		 
		 int code = stmt.getInt( 1 );
		 
		 if( LogCommun.isTechniqueDebug() ) {
		   LogCommun.debug( "AccountProfileQuestionRefDAO" , "insertRef", 
				   "creation de la question Account Profile numero : " + code );
		 }
		 
		 q.setCode( String.valueOf( code ) );
		 
		 return 0;
		 
	  } catch (SQLException e) {
	    if (e.getErrorCode() == SYBASE_PRIMARY_KEY_ERROR_CODE) {
		  throw new DuplicateKeyException(e);
		} else {
		  throw new TechnicalException(e);
		}
	  } catch (Exception e) {
		throw new TechnicalException(e);
	  } finally {
		releaseConnection(conn, stmt);
	  }
	}
}
