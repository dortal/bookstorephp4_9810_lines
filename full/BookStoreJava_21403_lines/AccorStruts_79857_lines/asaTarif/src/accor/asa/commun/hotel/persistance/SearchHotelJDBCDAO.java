package com.accor.asa.commun.hotel.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.hotel.metier.Hotel;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.metier.Address;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.metier.Element;
import com.accor.asa.commun.metier.categorie.AsaCategory;
import com.accor.asa.commun.persistance.DAO;
import com.accor.asa.commun.persistance.SQLCallExecuter;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.user.metier.SaleZone;

public class SearchHotelJDBCDAO extends DAO {

	private static SearchHotelJDBCDAO _instance = new SearchHotelJDBCDAO();

	private SearchHotelJDBCDAO() {}
	
	public static SearchHotelJDBCDAO getInstance() {
		return _instance;
	}

    /**
     * Recherche par critère des hotels - les elements de la liste sont des objet Hotel
     * @param paramValues
     * @param contexte
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<Hotel> searchHotels(final String[] paramValues, final Contexte contexte) throws TechnicalException {
    	
    	List<Hotel> hotels = null;
        
    	try {
          if( paramValues != null ) {
	          SQLParamDescriptor[] paramDescriptors = new SQLParamDescriptor[ paramValues.length ];
	          // init params
	          for( int i=0, size=paramValues.length; i<size; i++ )
	              paramDescriptors[i] = new SQLParamDescriptor(paramValues[i], false, Types.VARCHAR);

	          hotels = (List<Hotel>) SQLCallExecuter.getInstance().executeSelectProcSansLimite(
	        		  "proc_Selhotels_r2", new SQLParamDescriptorSet( paramDescriptors ), 
	        		  "SearchHotelsJDBCDAO", "searchHotels", contexte.getContexteAppelDAO(), 
	        		  new SQLResultSetReader() {
	                    public Object instanciateFromLine(ResultSet rs) throws SQLException {
	                          Hotel hotel = new Hotel();
	                          hotel.setCode(rs.getString("codehotel"));
	                          hotel.setName(rs.getString("nomhotel"));
	                          hotel.setFlagAsaTarifLight(rs.getString("flagAsaTarifLight"));
                              hotel.setFlagAsaTarifLoisir(rs.getString("flagAsaTarifLoisir"));

                              Element mark = new Element();
	                          mark.setCode(rs.getString("codemarque"));
	                          mark.setLibelle(rs.getString("nomlabel"));
	                          hotel.setMark(mark);

	                          Element chain = new Element();
	                          chain.setCode(rs.getString("codechaine"));
	                          chain.setLibelle(rs.getString("nomchaine"));
	                          hotel.setChain(chain);

	                          Address addr = hotel.getAddress();
	                          addr.setCity(rs.getString("villehotel"));
	                          addr.setCountryId(rs.getString("codepays"));
	                          addr.setCountryName(rs.getString("nompays"));
	                          hotel.setAddress( addr );

	                          AsaCategory ac = new AsaCategory();
	                          ac.setCode(rs.getString("codeasacategory"));
	                          hotel.setAsaCategory(ac);

	                          Element dop = new Element();
	                          dop.setCode(rs.getString("codediroper"));
	                          hotel.setDOP( dop );
							
	                          Element dgr = new Element();
	                          dgr.setCode(rs.getString("oploccode"));
	                          hotel.setDGR( dgr );
							
	                          SaleZone sz = new SaleZone();
	                          sz.setCode(rs.getString("codezonevente"));
	                          hotel.setSaleZone( sz );

	                          return hotel;
	                    }
	                  }
	          );
          }
        } catch (SQLException e) {
            LogCommun.major( contexte.getCodeUtilisateur(), "SearchHotelsJDBCDAO", "searchHotels", e.getMessage(), e );
            throw new TechnicalException(e);
        } 
        return hotels;
    }
}
