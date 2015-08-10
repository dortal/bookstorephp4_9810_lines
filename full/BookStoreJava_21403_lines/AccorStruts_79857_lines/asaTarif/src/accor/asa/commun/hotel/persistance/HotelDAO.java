package  com.accor.asa.commun.hotel.persistance;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CacheManager;
import com.accor.asa.commun.hotel.metier.Hotel;
import com.accor.asa.commun.hotel.metier.HotelContact;
import com.accor.asa.commun.hotel.metier.HotelStaff;
import com.accor.asa.commun.hotel.metier.descriptive.*;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.metier.*;
import com.accor.asa.commun.metier.categorie.AsaCategory;
import com.accor.asa.commun.metier.devise.Devise;
import com.accor.asa.commun.persistance.*;
import com.accor.asa.commun.user.metier.SaleRegion;
import com.accor.asa.commun.user.metier.SaleZone;
import com.accor.commun.internationalisation.Translator;
import com.accor.commun.internationalisation.TranslatorFactory;
import org.apache.commons.lang.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */
public class HotelDAO extends DAO {
    
	private static HotelDAO instance = new HotelDAO();

    /**
     * put your documentation comment here
     */
    private HotelDAO () {}

    /**
     * put your documentation comment here
     * @return
     */
    public static HotelDAO getInstance () {
        return  instance;
    }

    /***
     * Retourne les infos marketing de l'hotel
     * @param rid
     * @param contexte
     * @return HotelMarketingInfo
     * @throws TechnicalException
     */
    public HotelMarketing getMarketingInfos( final String rid, final Contexte contexte ) 
    		throws TechnicalException {

    	String[] cacheParams = new String[] { rid, contexte.getCodeLangue() };
    	
    	HotelCachable hotel = (HotelCachable) CacheManager.getInstance()
    		.getObjectInCache( HotelCachable.class, cacheParams );
    	
        if( hotel == null || hotel.getMarketing() == null ) {
        	SQLParamDescriptor param = new SQLParamDescriptor( rid, false, Types.VARCHAR );
        	
	    	try {
	    		HotelMarketing marketing = (HotelMarketing) SQLCallExecuter.getInstance().executeSelectSingleObjetProc(
	            		"local..co_SelHotMark_R0", new SQLParamDescriptorSet( param ), 
	            		"HotelDAO", "getMarketingInfos", contexte.getContexteAppelDAO(), 
	            		new SQLResultSetReader() {
	            			public Object instanciateFromLine( ResultSet rs ) throws SQLException {
	            				HotelMarketing h = new HotelMarketing();
	            				h.setContact( StringUtils.trimToNull( rs.getString( "NOMMARK" ) ) );
	            				h.setContactTitle( StringUtils.trimToNull( rs.getString( "TITREMARK" ) ) );
	            				h.setFunctionCode( StringUtils.trimToNull( rs.getString( "CODEFONCTION" ) ) );
	            				
	            				Address addr = h.getAddress();
	            				addr.setAddress1( StringUtils.trimToNull( rs.getString( "ADR1MARK" ) ) );
	            				addr.setAddress2( StringUtils.trimToNull( rs.getString( "ADR2MARK" ) ) );
	            				addr.setAddress3( StringUtils.trimToNull( rs.getString( "ADR3MARK" ) ) );
	            				addr.setZipCode( StringUtils.trimToNull( rs.getString( "CPMARK" ) ) );
	            				addr.setCity( StringUtils.trimToNull( rs.getString( "VILLEMARK" ) ) );
	            				addr.setStateId( StringUtils.trimToNull( rs.getString( "CODEETATMARK" ) ) );
	            				addr.setCountryId( StringUtils.trimToNull( rs.getString( "CODEPAYSMARK" ) ) );
	            				h.setAddress( addr );
	            				
	            				Coordinates coord = h.getCoordinates();
	            				coord.setPhonePrefix( StringUtils.trimToNull( rs.getString( "INDTELMARK" ) ) );
	            				coord.setPhone( StringUtils.trimToNull( rs.getString( "TELMARK" ) ) );
	            				coord.setFax( StringUtils.trimToNull( rs.getString( "FAXMARK" ) ) );
	            				coord.setMail( StringUtils.trimToNull( rs.getString( "EMAIL" ) ) );
	            				h.setCoordinates( coord );
	            				return h;
	            			}
	            		}
	            );
	    		
	    		if( hotel == null ) {
	    			hotel = new HotelCachable( rid, contexte.getCodeLangue() );
	    		}
	    		hotel.setMarketing( marketing );
	    		CacheManager.getInstance().setObjectInCache( hotel );
	    		
	        } catch( SQLException ex ) {
	        	LogCommun.major( contexte.getCodeUtilisateur(), "HotelDAO", "getMarketingInfos", "codeHotel : " + rid, ex );
	            throw new TechnicalException( ex );
	        }
        }
        return hotel.getMarketing();
    }

    /***
     * Retourne les infos comptabilite de l'hotel
     * @param rid
     * @param contexte
     * @return HotelAccounting
     * @throws TechnicalException
     */
    public HotelAccounting getAccountingInfos( final String rid, final Contexte contexte ) 
    		throws TechnicalException {
    	
    	String[] cacheParams = new String[] { rid, contexte.getCodeLangue() };
    	
    	HotelCachable hotel = (HotelCachable) CacheManager.getInstance()
    		.getObjectInCache( HotelCachable.class, cacheParams );
    	
    	if( hotel == null || hotel.getAccounting() == null ) {
    	
	    	SQLParamDescriptor param = new SQLParamDescriptor( rid, false, Types.VARCHAR );
	
	    	try {
	        	HotelAccounting accounting = (HotelAccounting) SQLCallExecuter.getInstance().executeSelectSingleObjetProc( 
	        			"local..co_SelHotCompta_R1", new SQLParamDescriptorSet( param ), 
	        			"HotelDAO", "getAccountingInfos", contexte.getContexteAppelDAO(), 
	        			new SQLResultSetReader() {
	        				public Object instanciateFromLine( ResultSet rs ) throws SQLException {
	        					HotelAccounting a = new HotelAccounting();
	        					a.setAccountantName( StringUtils.trimToNull( rs.getString( "NOMCOMPTA" ) ) );
	        					a.setAccountantNumber( StringUtils.trimToNull( rs.getString( "NUMCOMPTA" ) ) );
	        					a.setAccountantTitle( StringUtils.trimToNull( rs.getString( "TITRECOMPTA" ) ) );
	        					a.setFunctionCode( StringUtils.trimToNull( rs.getString( "CodeFonction" ) ) );
	        					a.setInvoicingName( StringUtils.trimToNull( rs.getString( "INVOICINGNAME" ) ) );
	        					
	        					Address addr = a.getAddress();
	        					addr.setAddress1( StringUtils.trimToNull( rs.getString( "ADR1COMPTA" ) ) );
	        					addr.setAddress2( StringUtils.trimToNull( rs.getString( "ADR2COMPTA" ) ) );
	        					addr.setAddress3( StringUtils.trimToNull( rs.getString( "ADR3COMPTA" ) ) );
	        					addr.setZipCode( StringUtils.trimToNull( rs.getString( "CPCOMPTA" ) ) );
	        					addr.setCity( StringUtils.trimToNull( rs.getString( "VILLECOMPTA" ) ) );
	        					addr.setStateId( StringUtils.trimToNull( rs.getString( "CODEETATCOMPTA" ) ) );
	        					addr.setCountryId( StringUtils.trimToNull( rs.getString( "CODEPAYSCOMPTA" ) ) );
	        					a.setAddress( addr );
	        					
	        					Coordinates coord = a.getCoordinates();
	        					coord.setPhonePrefix( StringUtils.trimToNull( rs.getString( "INDTELCOMPTA" ) ) );
	        					coord.setPhone( StringUtils.trimToNull( rs.getString( "TELCOMPTA" ) ) );
	        					coord.setFax( StringUtils.trimToNull( rs.getString( "FAXCOMPTA" ) ) );
	        					coord.setMail( StringUtils.trimToNull( rs.getString( "emailCompta" ) ) );
	        					a.setCoordinates( coord );
	        					
	        					Address invAddr = a.getInvoicingAddress();
	        					invAddr.setAddress1( StringUtils.trimToNull( rs.getString( "INVOICINGADRESS" ) ) );
	        					invAddr.setAddress2( StringUtils.trimToNull( rs.getString( "INVOICINGADRESS2" ) ) );
	        					invAddr.setAddress3( StringUtils.trimToNull( rs.getString( "INVOICINGADRESS3" ) ) );
	        					invAddr.setZipCode( StringUtils.trimToNull( rs.getString( "INVOICINGCP" ) ) );
	        					invAddr.setCity( StringUtils.trimToNull( rs.getString( "INVOICINGCITY" ) ) );
	        					invAddr.setStateId( StringUtils.trimToNull( rs.getString( "INVOICINGSTATE" ) ) );
	        					invAddr.setCountryId( StringUtils.trimToNull( rs.getString( "INVOICINGCOUNTRY" ) ) );
	        					a.setInvoicingAddress( invAddr );
	        					return a;
	        				}
	        			}
	        	);
	        	
	        	if( hotel == null ) {
	        		hotel = new HotelCachable( rid, contexte.getCodeLangue() );
	        	}
	        	hotel.setAccounting( accounting );
	        	CacheManager.getInstance().setObjectInCache( hotel );

	    	} catch( SQLException ex ) {
	        	LogCommun.major( contexte.getCodeUtilisateur(), "HotelDAO", "getAccountingInfos", "codeHotel : " + rid, ex );
	            throw new TechnicalException( ex );
	        }
    	}
        return hotel.getAccounting();
    }

    /***
     * Retourne les infos bancaires de l'hotel
     * @param rid
     * @param contexte
     * @return HotelBank
     * @throws TechnicalException
     */
    public HotelBank getBankInfos( final String rid, final Contexte contexte ) 
    		throws TechnicalException {

    	String[] cacheParams = new String[] { rid, contexte.getCodeLangue() };

    	HotelCachable hotel = (HotelCachable) CacheManager.getInstance()
    		.getObjectInCache( HotelCachable.class, cacheParams );
    	
    	if( hotel == null || hotel.getBank() == null ) {
	    	
	    	SQLParamDescriptor param = new SQLParamDescriptor( rid, false, Types.VARCHAR );
	    	
	        try {
	            HotelBank bank = (HotelBank) SQLCallExecuter.getInstance().executeSelectSingleObjetProc( 
	            		"local..co_SelHotBan_R1", new SQLParamDescriptorSet( param ), 
	            		"HotelDAO", "getBankInfos", contexte.getContexteAppelDAO(), 
	            		new SQLResultSetReader() {
	            			public Object instanciateFromLine( ResultSet rs ) throws SQLException {
	            				HotelBank h = new HotelBank();
	            				h.setBankName( StringUtils.trimToNull( rs.getString( "NOM1BANQUE" ) ) );
	            				h.setBankName2( StringUtils.trimToNull( rs.getString( "NOM2BANQUE" ) ) );
	            				h.setAccountNumber( StringUtils.trimToNull( rs.getString( "NUMCOMPTE" ) ) );
	            				h.setAmexCode( StringUtils.trimToNull( rs.getString( "AMEXCODE" ) ) );
	            				h.setVatNumber( StringUtils.trimToNull( rs.getString( "VATNUMBER" ) ) );
	            				h.setCitiHotelCode( StringUtils.trimToNull( rs.getString( "CODEHOTELCITI" ) ) );
	            				
	            				Address addr = h.getAddress();
	            				addr.setAddress1( StringUtils.trimToNull( rs.getString( "ADR1BANQUE" ) ) );
	            				addr.setAddress2( StringUtils.trimToNull( rs.getString( "ADR2BANQUE" ) ) );
	            				addr.setAddress3( StringUtils.trimToNull( rs.getString( "ADR3BANQUE" ) ) );
	            				addr.setZipCode( StringUtils.trimToNull( rs.getString( "CPBANQUE" ) ) );
	            				addr.setCity( StringUtils.trimToNull( rs.getString( "VILLEBANQUE" ) ) );
	            				addr.setStateId( StringUtils.trimToNull( rs.getString( "CODEETATBANQUE" ) ) );
	            				addr.setCountryId( StringUtils.trimToNull( rs.getString( "CODEPAYSBANQUE" ) ) );
	            				h.setAddress( addr );
	            				
	            				Coordinates coord = h.getCoordinates();
	            				coord.setPhonePrefix( StringUtils.trimToNull( rs.getString( "INDTELBANQUE" ) ) );
	            				coord.setPhone( StringUtils.trimToNull( rs.getString( "TELBANQUE" ) ) );
	            				coord.setFax( StringUtils.trimToNull( rs.getString( "FAXBANQUE" ) ) );
	            				h.setCoordinates( coord );
	            				
	            				return h;
	            			}
	            		}
	            );
	            if( hotel == null ) {
	            	hotel = new HotelCachable( rid, contexte.getCodeLangue() );
	            }
	            hotel.setBank( bank );
	            CacheManager.getInstance().setObjectInCache( hotel );
	            
	        } catch( SQLException ex ) {
	        	LogCommun.major( contexte.getCodeUtilisateur(), "HotelDAO", "getBankInfos", "codeHotel : " + rid, ex );
	            throw new TechnicalException( ex );
	        }
    	}
        return hotel.getBank();
    }

    /***
     * Retourne la liste des centres d'interêts de l'hotel
     * @param rid
     * @param contexte
     * @return List<HotelInterestCenter>
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	public List<HotelInterestCenter> getInterestCenters( final String rid, final Contexte contexte ) 
    		throws TechnicalException {

    	String[] cacheParams = new String[] { rid, contexte.getCodeLangue() };
    	
    	HotelCachable hotel = (HotelCachable) CacheManager.getInstance()
    		.getObjectInCache( HotelCachable.class, cacheParams );
    	
    	if( hotel == null || hotel.getInterestCenters() == null ) {

    		SQLParamDescriptor[] params = new SQLParamDescriptor[] {
        			new SQLParamDescriptor( rid, false, Types.VARCHAR ),
        			new SQLParamDescriptor( contexte.getCodeLangue(), false, Types.VARCHAR )
        	};
	            
	        try {

	        	List<HotelInterestCenter> ics = (List<HotelInterestCenter>) SQLCallExecuter.getInstance().executeSelectProc( 
	        			"local..co_SelHotCi_R0", new SQLParamDescriptorSet( params ), 
	        			"HotelDAO", "getInterestCenters", contexte.getContexteAppelDAO(), 
	        			new SQLResultSetReader() {
	        				public Object instanciateFromLine( ResultSet rs ) throws SQLException {
	        					HotelInterestCenter ic = new HotelInterestCenter();
	        					ic.setName( StringUtils.trimToNull( rs.getString( "NOMCI" ) ) );
	        					ic.setOrientation( StringUtils.trimToNull( rs.getString( "ORIENTATION" ) ) );
	        					ic.setWeb( StringUtils.trimToNull( rs.getString( "ADRINTERNET" ) ) );
	        					ic.setVuGdsWeb( StringUtils.trimToNull( rs.getString( "VUGDSWEB" ) ) );
	        					ic.setShuttleOnCall( StringUtils.trimToNull( rs.getString( "SHUTTLEONCALL" ) ) );
	        					ic.setShuttleScheduled( StringUtils.trimToNull( rs.getString( "SHUTTLESCHEDULED" ) ) );
	
	        					ic.setFreeShuttle( rs.getBoolean( "NAVGRATUITE" ) );
	        					ic.setShuttle( rs.getBoolean( "NAVETTE" ) );
	        					
	        					Element type = new Element();
	        					type.setCode( StringUtils.trimToNull( rs.getString( "CODETYPECI" ) ) );
	        					type.setLibelle( StringUtils.trimToNull( rs.getString( "NOMTYPECI" ) ) );
	        					ic.setType( type );
	
	        					float floatDistance = rs.getFloat( "DISTANCEMI" );
	        					if( ! rs.wasNull() ) {
	        						ic.setDistanceInMiles( new Float( floatDistance ) );
	        					}
	        					
	        					floatDistance = rs.getFloat( "DISTANCEKM" );
	        					if( ! rs.wasNull() ) {
	        						ic.setDistanceInKm( new Float( floatDistance ) );
	        					}
	
	        					float price = rs.getFloat( "PRIXLIMOUSINE" );
	        					if( ! rs.wasNull() ) {
	        						ic.setLimousinePrice( new Float( price ) );
	        					}
	
	        					price = rs.getFloat( "PRIXNAVETTE" );
	        					if( ! rs.wasNull() ) {
	        						ic.setShuttlePrice( new Float( price ) );
	        					}
	
	        					price = rs.getFloat( "PRIXTAXI" );
	        					if( ! rs.wasNull() ) {
	        						ic.setTaxiPrice( new Float( price ) );
	        					}
	
	        					int order = rs.getInt( "ORDREAFFICHAGE" );
	        					if( ! rs.wasNull() ) {
	        						ic.setDisplayOrder( new Integer( order ) );
	        					}
	        					
	        					int intDistance = rs.getInt( "WALKDIST" );
	        					if( ! rs.wasNull() ) {
	        						ic.setWalkDistance( new Integer( intDistance ) );
	        					}
	        					
	        					intDistance = rs.getInt( "DRIVDIST" );
	        					if( ! rs.wasNull() ) {
	        						ic.setDriveDistance( new Integer( intDistance ) );
	        					}
	
	        					return ic;
	        				}
	        			}
	        	);
	        	if( hotel == null ) {
	        		hotel = new HotelCachable( rid, contexte.getCodeLangue() );
	        	}
	        	hotel.setInterestCenters( ics );
	        	CacheManager.getInstance().setObjectInCache( hotel );

	        } catch( SQLException ex ) {
	        	LogCommun.major(contexte.getCodeUtilisateur(), "HotelDAO", "getInterestCenters", "codeHotel : " + rid, ex );
	            throw new TechnicalException( ex );
	        }
    	}
        return hotel.getInterestCenters();
    }

    /***
     * Retourne la liste des produits d'un hotel
     * @param rid
     * @param contexte
     * @return List<HotelProduct>
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	public List<HotelProduct> getProducts( final String rid, final Contexte contexte ) 
    		throws TechnicalException {
        
    	String[] cacheParams = new String[] { rid, contexte.getCodeLangue() };
    	
    	HotelCachable hotel = (HotelCachable) CacheManager.getInstance()
    		.getObjectInCache( HotelCachable.class, cacheParams );
    	
    	if( hotel == null || hotel.getProducts() == null ) {
    	
	    	SQLParamDescriptor[] params = new SQLParamDescriptor[] {
	        		new SQLParamDescriptor( rid, false, Types.CHAR ),
	        		new SQLParamDescriptor( contexte.getCodeLangue(), false, Types.CHAR )
	        	};
	        
	    	try {
	        	List<HotelProduct> products = (List<HotelProduct>) SQLCallExecuter.getInstance().executeSelectProc( 
	            		"local..co_selproduitshotel_r2", new SQLParamDescriptorSet( params ), 
	            		"HotelDAO", "getProducts", contexte.getContexteAppelDAO(), 
	            		new SQLResultSetReader() {
	            			public Object instanciateFromLine( ResultSet rs ) throws SQLException {
	            				HotelProduct p = new HotelProduct();
	            				p.setCode( StringUtils.trimToNull( rs.getString( "codeproduit" ) ) );
	            				p.setName( StringUtils.trimToNull( rs.getString( "nomproduit" ) ) );
	            				p.setSitu( StringUtils.trimToNull( rs.getString( "situ" ) ) );
	            				p.setPmsProductCode( StringUtils.trimToNull( rs.getString( "codeproduitpms" ) ) );
	            				p.setFlagType( StringUtils.trimToNull( rs.getString( "flagtype" ) ) );
	            				p.setVuGdsWeb( StringUtils.trimToNull( rs.getString( "vugdsweb" ) ) );
	            				
	            				p.setFree( ! rs.getBoolean( "payant" ) );
	            				p.setBooking( rs.getBoolean( "reservable" ) );
	            				p.setDeleted( "S".equals( rs.getString( "supprime" ) ) );
	            				
	            				Element type = new Element();
	            				type.setCode( StringUtils.trimToNull( rs.getString( "codetypeproduit" ) ) );
	            				p.setType( type );
	
	            				int nb = rs.getInt( "nbpers" );
	            				if( ! rs.wasNull() ) {
	            					p.setNbPax( new Integer( nb ) );
	            				}
	            				
	            				nb = rs.getInt( "qteprodhot" );
	            				if( ! rs.wasNull() ) {
	            					p.setQuantity( new Integer( nb ) );
	            				}
	
	            				nb = rs.getInt( "ordreaffichage" );
	            				if( ! rs.wasNull() ) {
	            					p.setDisplayOrder( new Integer( nb ) );
	            				}
	            				
	            				nb = rs.getInt( "prodinroom" );
	            				if( ! rs.wasNull() ) {
	            					p.setProductInRoom( new Integer( nb ) );
	            				}
	
	            				nb = rs.getInt( "nbmaxch" );
	            				if( ! rs.wasNull() ) {
	            					p.setNbMaxRoom( new Integer( nb ) );
	            				}
	            				
	            				nb = rs.getInt( "nbmaxpax" );
	            				if( ! rs.wasNull() ) {
	            					p.setNbMaxPax( new Integer( nb ) );
	            				}
	            				
	            				nb = rs.getInt( "Nblitsimple" );
	            				if( ! rs.wasNull() ) {
	            					p.setNbSingleBed( new Integer( nb ) );
	            				}
	            				
	            				nb = rs.getInt( "Nblitdouble" );
	            				if( ! rs.wasNull() ) {
	            					p.setNbDoubleBed( new Integer( nb ) );
	            				}
	            				
	            				return p;
	            			}
	            		}
	            );
	        	if( products != null ) {
	        		List<HotelProduct> asaProducts = new ArrayList<HotelProduct>();
	        		for( HotelProduct p : products ) {
	        			if( ! p.isDeleted() ) {
	        				asaProducts.add( p );
	        			}
	        		}
	        		products = asaProducts;
	        	}
	        	
	        	if( hotel == null ) {
	        		hotel = new HotelCachable( rid, contexte.getCodeLangue() );
	        	}
	        	hotel.setProducts( products );
	        	CacheManager.getInstance().setObjectInCache( hotel );
	        
	    	} catch( SQLException e ) {
	        	LogCommun.major( contexte.getCodeUtilisateur(), "HotelDAO", "getProducts", "codeHotel : " + rid, e );
	            throw  new TechnicalException(e);
	        }
    	}
        return hotel.getProducts();
    }

    /***
     * Retourne la liste des restaurants de l'hotel
     * @param rid
     * @param contexte
     * @return List<HotelRestaurant>
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	public List<HotelRestaurant> getRestaurants( final String rid, final Contexte contexte ) 
    		throws TechnicalException {
        
    	String[] cacheParams = new String[] { rid, contexte.getCodeLangue() };
    	
    	HotelCachable hotel = (HotelCachable) CacheManager.getInstance()
    		.getObjectInCache( HotelCachable.class, cacheParams );
    	
    	if( hotel == null || hotel.getRestaurants() == null ) {

    		SQLParamDescriptor[] params = new SQLParamDescriptor[] {
        		new SQLParamDescriptor( rid, false, Types.VARCHAR ),
        		new SQLParamDescriptor( contexte.getCodeLangue(), false, Types.VARCHAR )
    		};
    	
    		try {
    			List<HotelRestaurant> restaurants = (List<HotelRestaurant>) SQLCallExecuter.getInstance().executeSelectProc(
    					"local..co_SelHotSalRest_R0", new SQLParamDescriptorSet( params ), 
	            		"HotelDAO", "getRestaurants", contexte.getContexteAppelDAO(), 
	            		new SQLResultSetReader() {
	            			public Object instanciateFromLine( ResultSet rs ) throws SQLException {
	            				HotelRestaurant r = new HotelRestaurant();
	            				r.setName( StringUtils.trimToNull( rs.getString( "nomrest" ) ) );
	            				r.setOpeningHours( StringUtils.trimToNull( rs.getString( "horaires" ) ) );
	            				r.setCuisineTypeCode( StringUtils.trimToNull( rs.getString( "codetypecuisine" ) ) );
	            				r.setAmexCode( StringUtils.trimToNull( rs.getString( "amexcode" ) ) );
	            				r.setOpenDays( StringUtils.trimToNull( rs.getString( "opendays"  ) ) );
	            				
	            				r.setAirConditionning( rs.getBoolean( "aircond" ) );
	            				r.setSmokeForbidden( rs.getBoolean( "nonfum" ) );
	            				r.setTerrace( StringUtils.trimToNull( rs.getString( "fterrace" ) ) );
	            				r.setMealsServedAtSwimmingPool( StringUtils.trimToNull( rs.getString( "fmealswim" ) ) );
	            				r.setPetsAccepted( StringUtils.trimToNull( rs.getString( "fpets" ) ) );
	            				r.setThemedActivities( StringUtils.trimToNull( rs.getString( "fthem" ) ) );
	            				r.setChildMeal( StringUtils.trimToNull( rs.getString( "fchild" ) ) );
	            				r.setFullBoard( StringUtils.trimToNull( rs.getString( "ffb" ) ) );
	            				r.setHalfBoard( StringUtils.trimToNull( rs.getString( "fhb" ) ) );
	            				
	            				Element type = new Element();
	            				type.setCode( StringUtils.trimToNull( rs.getString( "codetyperest" ) ) );
	            				type.setLibelle( StringUtils.trimToNull( rs.getString( "nomtyperest" ) ) );
	            				r.setType( type );
	            				
	            				int nb = rs.getInt( "nbplaces" );
	            				if( ! rs.wasNull() ) { 
	            					r.setCapacity( new Integer( nb ) );
	            				}
	            				
	            				float price = rs.getFloat( "prixmoyrest" );
	            				if( ! rs.wasNull() ) {
	            					r.setAveragePrice( new Float( price ) );
	            				}
	            				
	            				String date = rs.getString( "opendate" );
	            				try {
		            				if( StringUtils.isNotBlank( date ) ) {
		            					r.setOpeningDate( new AsaDate( date, AsaDate.SYBASE_DATETIME ) );
		            				}
	            				} catch( ParseException e ) {
	            					LogCommun.major( contexte.getCodeUtilisateur(), "FicheHotelDAO", "getRestaurants", "Erreur lors du parsing de la date openingDate : " + date );
		            			}
	            				
	            				date = rs.getString( "closedate" );
	            				try {
		            				if( StringUtils.isNotBlank( date ) ) {
		            					r.setClosedDate( new AsaDate( date, AsaDate.SYBASE_DATETIME ) );
		            				}
	            				} catch( ParseException e ) {
	            					LogCommun.major( contexte.getCodeUtilisateur(), "FicheHotelDAO", "getRestaurants", "Erreur lors du parsing de la date closedDate : " + date );
		            			}
	
	            				return r;
	            			}
	            		}
	            );
    			if( hotel == null ) {
    				hotel = new HotelCachable( rid, contexte.getCodeLangue() );
    			}
    			hotel.setRestaurants( restaurants );
    			CacheManager.getInstance().setObjectInCache( hotel );
    			
	        } catch( SQLException e ) {
	        	LogCommun.major( contexte.getCodeUtilisateur(), "HotelDAO", "getRestaurants", "codeHotel : " + rid, e );
	            throw  new TechnicalException(e);
	        }
    	}
        return hotel.getRestaurants();
    }

    /***
     * Retourne la liste des bars de l'hotel
     * @param rid
     * @param contexte
     * @return List<HotelBar>
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	public List<HotelBar> getBars( final String rid, final Contexte contexte ) 
    		throws TechnicalException {
        
    	String[] cacheParams = new String[] { rid, contexte.getCodeLangue() };
    	
    	HotelCachable hotel = (HotelCachable) CacheManager.getInstance()
    		.getObjectInCache( HotelCachable.class, cacheParams );
    	
    	if( hotel == null || hotel.getBars() == null ) {

    		SQLParamDescriptor[] params = new SQLParamDescriptor[] {
	        		new SQLParamDescriptor( rid, false, Types.VARCHAR ),
	        		new SQLParamDescriptor( contexte.getCodeLangue(), false, Types.VARCHAR )
	    	};
	
	    	try {
	    		List<HotelBar> bars = (List<HotelBar>) SQLCallExecuter.getInstance().executeSelectProc( 
	            		"local..co_SelHotBar_R0", new SQLParamDescriptorSet( params ), 
	            		"HotelDAO", "getBars", contexte.getContexteAppelDAO(), 
	            		new SQLResultSetReader() {
	            			public Object instanciateFromLine( ResultSet rs ) throws SQLException {
	            				HotelBar b = new HotelBar();
	            				b.setName( StringUtils.trimToNull( rs.getString( "nombar" ) ) );
	            				b.setOpeningHours( StringUtils.trimToNull( rs.getString( "horaires" ) ) );
	            				b.setAmexCode( StringUtils.trimToNull( rs.getString( "amexcode" ) ) );
	            				b.setOpenDays( StringUtils.trimToNull( rs.getString( "opendays"  ) ) );
	            				
	            				b.setPetsAccepted( StringUtils.trimToNull( rs.getString( "fpets" ) ) );
	            				b.setRoomService( StringUtils.trimToNull( rs.getString( "froomserv" ) ) );
	            				b.setLightMeal( StringUtils.trimToNull( rs.getString( "flightmeal" ) ) );
	            				b.setHappyHour( StringUtils.trimToNull( rs.getString( "fhappyhour" ) ) );
	            				b.setMusic( StringUtils.trimToNull( rs.getString( "fmusic" ) ) );
	
	            				Element type = new Element();
	            				type.setCode( StringUtils.trimToNull( rs.getString( "codetypebar" ) ) );
	            				type.setLibelle( StringUtils.trimToNull( rs.getString( "nomtypebar" ) ) );
	            				b.setType( type );
	
	            				int nb = rs.getInt( "nbplaces" );
	            				if( ! rs.wasNull() ) { 
	            					b.setCapacity( new Integer( nb ) );
	            				}
	
	            				float price = rs.getFloat( "prixmoybar" );
	            				if( ! rs.wasNull() ) {
	            					b.setAveragePrice( new Float( price ) );
	            				}
	
	            				String date = rs.getString( "opendate" );
	            				try {
		            				if( StringUtils.isNotBlank( date ) ) {
		            					b.setOpeningDate( new AsaDate( date, AsaDate.SYBASE_DATETIME ) );
		            				}
	            				} catch( ParseException e ) {
	            					LogCommun.major( contexte.getCodeUtilisateur(), "FicheHotelDAO", "getBars", "Erreur lors du parsing de la date openingDate : " + date );
		            			}
	            				
	            				date = rs.getString( "closedate" );
	            				try {
		            				if( StringUtils.isNotBlank( date ) ) {
		            					b.setClosedDate( new AsaDate( date, AsaDate.SYBASE_DATETIME ) );
		            				}
	            				} catch( ParseException e ) {
	            					LogCommun.major( contexte.getCodeUtilisateur(), "FicheHotelDAO", "getBars", "Erreur lors du parsing de la date closedDate : " + date );
		            			}
	
	            				return b;
	            			}
	            		}
	            );
	    		if( hotel == null ) {
	    			hotel = new HotelCachable( rid, contexte.getCodeLangue() );
	    		}
	    		hotel.setBars( bars );
	    		CacheManager.getInstance().setObjectInCache( hotel );
	    		
	        } catch( SQLException e ) {
	        	LogCommun.major( contexte.getCodeUtilisateur(), "HotelDAO", "getBars", "codeHotel : " + rid, e );
	            throw new TechnicalException(e);
	        }
    	}
        return hotel.getBars();
    }

    /***
     * Retourne la liste des salles de reunions de l'hotel
     * @param rid
     * @param contexte
     * @return List<Element>
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	public List<Element> getMeetingRooms( final String rid, final Contexte contexte ) 
    		throws TechnicalException {

    	String[] cacheParams = new String[] { rid, contexte.getCodeLangue() };
    	
    	HotelCachable hotel = (HotelCachable) CacheManager.getInstance()
    		.getObjectInCache( HotelCachable.class, cacheParams );
    	
    	if( hotel == null || hotel.getMeetingRooms() == null ) {

    		SQLParamDescriptor[] params = new SQLParamDescriptor[] {
    				new SQLParamDescriptor( rid, false, Types.VARCHAR ),
	        		new SQLParamDescriptor( contexte.getCodeLangue(), false, Types.VARCHAR )
	    	};
	
	    	try {
	    		List<Element> rooms = (List<Element>) SQLCallExecuter.getInstance().executeSelectProc(
	            		"local..co_SelHotMeeting_R0", new SQLParamDescriptorSet( params ), 
	            		"HotelDAO", "getMeetingRooms", contexte.getContexteAppelDAO(), 
	            		new SQLResultSetReader() {
	            			public Object instanciateFromLine( ResultSet rs )  throws SQLException {
	            				Element r = new Element();
	            				r.setCode( StringUtils.trimToNull( rs.getString( "codetypemeeting" ) ) );
	            				r.setLibelle( StringUtils.trimToNull( rs.getString( "nomtypemeeting" ) ) );
	            				return r;
	            			}
	            		}
	            );
	    		if( hotel == null ) {
	    			hotel = new HotelCachable( rid, contexte.getCodeLangue() );
	    		}
	    		hotel.setMeetingRooms( rooms );
	    		CacheManager.getInstance().setObjectInCache( hotel );
	    		
	        } catch( SQLException ex ) {
	        	LogCommun.major( contexte.getCodeUtilisateur(), "HotelDAO", "getMeetingRooms", "codeHotel : " + rid, ex );
	            throw new TechnicalException( ex );
	        }
    	}
        return hotel.getMeetingRooms();
    }

    /***
     * Retourne la liste des salons de l'hotel
     * @param rid
     * @param contexte
     * @return List<HotelLounge>
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	public List<HotelLounge> getLounges( final String rid, final Contexte contexte ) 
    		throws TechnicalException {

    	String[] cacheParams = new String[] { rid, contexte.getCodeLangue() };
    	
    	HotelCachable hotel = (HotelCachable) CacheManager.getInstance()
    		.getObjectInCache( HotelCachable.class, cacheParams );
    	
    	if( hotel == null || hotel.getLounges() == null ) {

    		SQLParamDescriptor[] params = new SQLParamDescriptor[] {
	        		new SQLParamDescriptor( rid, false, Types.VARCHAR ),
	        		new SQLParamDescriptor( contexte.getCodeLangue(), false, Types.VARCHAR )
	    	};
	
	    	try {
	    		List<HotelLounge> lounges = (List<HotelLounge>) SQLCallExecuter.getInstance().executeSelectProc(
	            		"local..co_SelHotLounge_R0", new SQLParamDescriptorSet( params ), 
	            		"HotelDAO", "getLounges", contexte.getContexteAppelDAO(),
	            		new SQLResultSetReader() {
	            			public Object instanciateFromLine( ResultSet rs ) throws SQLException {
	            				HotelLounge l = new HotelLounge();
	            				l.setName( StringUtils.trimToNull( rs.getString( "nomsalle" ) ) );
	            				l.setCover( StringUtils.trimToNull( rs.getString( "sol" ) ) );
	            				
	            				l.setBuffet( StringUtils.trimToNull( rs.getString( "fbuffet" ) ) );
	            				l.setDanceBuffet( StringUtils.trimToNull( rs.getString( "fbuffetdance" ) ) );
	            				l.setCocktail( StringUtils.trimToNull( rs.getString( "fcocktail" ) ) );
	            				l.setExpo( StringUtils.trimToNull( rs.getString( "fexpo" ) ) );
	
	            				Element type = new Element();
	            				type.setCode( StringUtils.trimToNull( rs.getString( "codetypesalle" ) ) );
	            				type.setLibelle( StringUtils.trimToNull( rs.getString( "nomtypesalle" ) ) );
	            				l.setType( type );
	
	            				int nb = rs.getInt( "nbperssalle" );
	            				if( ! rs.wasNull() ) { 
	            					l.setCapacity( new Integer( nb ) );
	            				}
	
	            				nb = rs.getInt( "surface" );
	            				if( ! rs.wasNull() ) { 
	            					l.setSurface( new Integer( nb ) );
	            				}
	
	            				float height = rs.getFloat( "hauteur" );
	            				if( ! rs.wasNull() ) {
	            					l.setHeight( new Float( height ) );
	            				}
	
	            				nb = rs.getInt( "nbpaxboardroom" );
	            				if( ! rs.wasNull() ) { 
	            					l.setNbPaxBoardRoom( new Integer( nb ) );
	            				}
	
	            				nb = rs.getInt( "nbpaxclassroom" );
	            				if( ! rs.wasNull() ) { 
	            					l.setNbPaxClassRoom( new Integer( nb ) );
	            				}
	
	            				nb = rs.getInt( "nbpaxtheatre" );
	            				if( ! rs.wasNull() ) { 
	            					l.setNbPaxTheatre( new Integer( nb ) );
	            				}
	
	            				nb = rs.getInt( "nbpaxu" );
	            				if( ! rs.wasNull() ) { 
	            					l.setNbPaxUShape( new Integer( nb ) );
	            				}
	
	            				nb = rs.getInt( "nbpaxv" );
	            				if( ! rs.wasNull() ) { 
	            					l.setNbPaxVShape( new Integer( nb ) );
	            				}
	
	            				nb = rs.getInt( "nbpaxbanquet1" );
	            				if( ! rs.wasNull() ) { 
	            					l.setNbPaxBanquet( new Integer( nb ) );
	            				}
	
	            				nb = rs.getInt( "nbpaxbanquet2" );
	            				if( ! rs.wasNull() ) { 
	            					l.setNbPaxBanquet2( new Integer( nb ) );
	            				}
	
	            				nb = rs.getInt( "nbpaxbanquet3" );
	            				if( ! rs.wasNull() ) { 
	            					l.setNbPaxBanquet3( new Integer( nb ) );
	            				}
	
	            				nb = rs.getInt( "nbpaxbanquet4" );
	            				if( ! rs.wasNull() ) { 
	            					l.setNbPaxBanquet4( new Integer( nb ) );
	            				}
	
	            				return l;
	            			}
	            		}
	            );
	    		if( hotel == null ) {
	    			hotel = new HotelCachable( rid, contexte.getCodeLangue() );
	    		}
	    		hotel.setLounges( lounges );
	    		CacheManager.getInstance().setObjectInCache( hotel );

	    	} catch( SQLException e ) {
	        	LogCommun.major( contexte.getCodeUtilisateur(), "HotelDAO", "getLounges", "codeHotel : " + rid, e );
	            throw new TechnicalException(e);
	        }
    	}
        return hotel.getLounges();
    }

    /***
     * Retourne la liste des accès à l'hotel
     * @param rid
     * @param contexte
     * @return List<HotelAccess>
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	public List<HotelAccess> getAccess( final String rid, final Contexte contexte ) 
    		throws TechnicalException {
    	
    	String[] cacheParams = new String[] { rid, contexte.getCodeLangue() };
    	
    	HotelCachable hotel = (HotelCachable) CacheManager.getInstance()
    			.getObjectInCache( HotelCachable.class, cacheParams );
    	
        if( hotel == null || hotel.getAccess() == null ) {

        	SQLParamDescriptor[] params = new SQLParamDescriptor[] {
				new SQLParamDescriptor( rid, false, Types.VARCHAR ),
				new SQLParamDescriptor( contexte.getCodeLangue(), false, Types.VARCHAR )
			};

	    	try {
	    		List<HotelAccess> access  = (List<HotelAccess>) SQLCallExecuter.getInstance().executeSelectProc(
	            		"local..co_SelHotAccesLang_R1", new SQLParamDescriptorSet( params ), 
	            		"HotelDAO", "getAccess", contexte.getContexteAppelDAO(),
	            		new SQLResultSetReader() {
	            			public Object instanciateFromLine( ResultSet rs ) throws SQLException {
	            				HotelAccess a = new HotelAccess();
	            				a.setName( StringUtils.trimToNull( rs.getString( "nomacces" ) ) );
	            				a.setDirection( StringUtils.trimToNull( rs.getString( "direction" ) ) );
	            				a.setLine( StringUtils.trimToNull( rs.getString( "ligne" ) ) );
	            				a.setStation( StringUtils.trimToNull( rs.getString( "station" ) ) );
	
	            				Element type = new Element();
	            				type.setCode( StringUtils.trimToNull( rs.getString( "codetypeacces" ) ) );
	            				type.setLibelle( StringUtils.trimToNull( rs.getString( "nomtypeacces" ) ) );
	            				a.setType( type );
	
	            				return a;
	            			}
	            		}
	            );
	    		if( hotel == null ) {
	    			hotel = new HotelCachable( rid, contexte.getCodeLangue() );
	    		}
	    		hotel.setAccess( access );
	    		CacheManager.getInstance().setObjectInCache( hotel );
	    		
	        } catch( SQLException e ) {
	        	LogCommun.major( contexte.getCodeUtilisateur(), "HotelDAO", "getAccess", "codeHotel : " + rid, e );
	            throw new TechnicalException(e);
	        }
        }
        return hotel.getAccess();
    }
    
    /***
     * Retourne la fiche descrptive de l'hotel
     * @param rid
     * @param contexte
     * @return HotelDescriptive
     * @throws TechnicalException
     */
    public HotelDescriptive getDescriptive( final String rid, final Contexte contexte ) 
    		throws TechnicalException {
    	
    	String[] cacheParams = new String[] { rid, contexte.getCodeLangue() };
    	
    	HotelCachable hotel = (HotelCachable) CacheManager.getInstance()
    			.getObjectInCache( HotelCachable.class, cacheParams );
    	
        if( hotel == null || hotel.getDescriptive() == null ) {

        	SQLParamDescriptor[] params = new SQLParamDescriptor[] {
	        		new SQLParamDescriptor( rid, false, Types.CHAR ),
	        		new SQLParamDescriptor( contexte.getCodeLangue(), false, Types.CHAR )
	    	};
	
	    	try {
	    		HotelDescriptive desc = (HotelDescriptive) SQLCallExecuter.getInstance().executeSelectSingleObjetProc(
	            		"local..asa_selhotelinfo_r0", new SQLParamDescriptorSet( params ), 
	            		"HotelDAO", "getDescriptive", contexte.getContexteAppelDAO(),
	            		new SQLResultSetReader() {
	            			public Object instanciateFromLine( ResultSet rs ) throws SQLException {
	            				HotelDescriptive desc = new HotelDescriptive();
	            				desc.setBuildDate( StringUtils.trimToNull( rs.getString( "dateconst" ) ) );
	            				desc.setCreationDate( StringUtils.trimToNull( rs.getString( "datecreat" ) ) );
	            				desc.setRenovationDate( StringUtils.trimToNull( rs.getString( "daterenov" ) ) );
	            				desc.setCommissionUnit( StringUtils.trimToNull( rs.getString( "flagvaleur" ) ) );
	            				desc.setTimeDifference( StringUtils.trimToNull( rs.getString( "decalage" ) ) );
	            				desc.setBookingLimitTime( StringUtils.trimToNull( rs.getString( "heurelimite" ) ) );
	            				desc.setResaDay( StringUtils.trimToNull( rs.getString( "resajour" ) ) );
	            				desc.setClosed( StringUtils.trimToNull( rs.getString( "openclose" ) ) );
	            				desc.setLanguageCode( StringUtils.trimToNull( rs.getString( "codelangue" ) ) );
	            				desc.setIndicResaPlace( StringUtils.trimToNull( rs.getString( "indicresaplace" ) ) );
	            				desc.setCollected( StringUtils.trimToNull( rs.getString( "flagcollecte" ) ) );
	            				desc.setSegmentBypass( StringUtils.trimToNull( rs.getString( "segmentbypass" ) ) );
	            				desc.setDeleted( StringUtils.trimToNull( rs.getString( "flagdelete" ) ) );
	            				
	            				int nb = rs.getInt( "nbasc" );
	            				if( ! rs.wasNull() ) {
	            					desc.setNbLifts( new Integer( nb ) );
	            				}
	            				
	            				nb = rs.getInt( "nbchb" );
	            				if( ! rs.wasNull() ) {
	            					desc.setNbRooms( new Integer( nb ) );
	            				}
	
	            				nb = rs.getInt( "nbetages" );
	            				if( ! rs.wasNull() ) {
	            					desc.setNbFloors( new Integer( nb ) );
	            				}
	
	            				float amount = rs.getFloat( "valeurcom" );
	            				if( ! rs.wasNull() ) {
	            					desc.setCommission( new Float( amount ) );
	            				}
	            				
	            				Element cro = new Element();
	            				cro.setCode( StringUtils.trimToNull( rs.getString( "codecro" ) ) );
	            				cro.setLibelle( StringUtils.trimToNull( rs.getString( "nomcro" ) ) );
	            				desc.setCRO( cro );
	
	            				Element diroper = new Element();
	            				diroper.setCode( StringUtils.trimToNull( rs.getString( "codediroper" ) ) );
	            				diroper.setLibelle( StringUtils.trimToNull( rs.getString( "nomdiroper" ) ) );
	            				desc.setOperationalArea( diroper );
	
	            				Devise cur = new Devise();
	            				cur.setCode( StringUtils.trimToNull( rs.getString( "codedevise" ) ) );
	            				cur.setLibelle( StringUtils.trimToNull( rs.getString( "nomdevise" ) ) );
	            				desc.setCurrency( cur );
	
	            				Element env = new Element();
	            				env.setCode( StringUtils.trimToNull( rs.getString( "codeenviron" ) ) );
	            				env.setLibelle( StringUtils.trimToNull( rs.getString( "libenviron" ) ) );
	            				desc.setEnvironment( env );
	
	            				Element lt = new Element();
	            				lt.setCode( StringUtils.trimToNull( rs.getString( "codeetab" ) ) );
	            				lt.setLibelle( StringUtils.trimToNull( rs.getString( "libetab" ) ) );
	            				desc.setLodgingType( lt );
	
	            				Element loc = new Element();
	            				loc.setCode( StringUtils.trimToNull( rs.getString( "codelocal" ) ) );
	            				loc.setLibelle( StringUtils.trimToNull( rs.getString( "liblocal" ) ) );
	            				desc.setLocation( loc );
	
	            				SaleRegion sr = new SaleRegion();
	            				sr.setCode( StringUtils.trimToNull( rs.getString( "coderegion" ) ) );
	            				sr.setLibelle( StringUtils.trimToNull( rs.getString( "libregion" ) ) );
	            				desc.setRegion( sr );
	
	            				SaleZone sz = new SaleZone();
	            				sz.setCode( StringUtils.trimToNull( rs.getString( "codezonevente" ) ) );
	            				sz.setLibelle( StringUtils.trimToNull( rs.getString( "nomzonevente" ) ) );
	            				sz.setRegionCode( sr.getCode() );
	            				desc.setZone( sz );
	
	            				Element std = new Element();
	            				std.setCode( StringUtils.trimToNull( rs.getString( "codestdhotel" ) ) );
	            				std.setLibelle( StringUtils.trimToNull( rs.getString( "nomstdhot" ) ) );
	            				desc.setStandard( std );
	
	            				return desc;
	            			}
	            		}
	            );
	    		if( hotel == null ) {
	    			hotel = new HotelCachable( rid, contexte.getCodeLangue() );
	    		}
	    		hotel.setDescriptive( desc );
	    		CacheManager.getInstance().setObjectInCache( hotel );

	    	} catch( SQLException e ) {
	        	LogCommun.major( contexte.getCodeUtilisateur(), "HotelDAO", "getDescriptive", "codeHotel : " + rid, e );
	            throw new TechnicalException(e);
	        }
        }
        return hotel.getDescriptive();
    }

    /***
     * Retourne la fiche hotel
     * @param rid
     * @param contexte
     * @return Hotel
     * @throws TechnicalException
     */
    public Hotel getHotel( final String rid, final Contexte contexte ) throws TechnicalException {
        
    	String[] cacheParams = new String[] { rid, contexte.getCodeLangue() };
    	
    	HotelCachable hotelInCache = (HotelCachable) CacheManager.getInstance()
    		.getObjectInCache( HotelCachable.class, cacheParams );
    	
    	if( hotelInCache == null || hotelInCache.getHotel() == null ) {
    	
    		SQLParamDescriptor param = new SQLParamDescriptor( rid, false, Types.CHAR );
    	
	        try {
	        	Hotel hotel = (Hotel) SQLCallExecuter.getInstance().executeSelectSingleObjetProc(
	            		"tars_selHotelByCode", new SQLParamDescriptorSet( param ), 
	            		"HotelDAO", "getHotel", contexte.getContexteAppelDAO(), 
	            		new SQLResultSetReader() {
	            			public Object instanciateFromLine( ResultSet rs ) throws SQLException {
	            				Hotel hot = new Hotel();
	            				hot.setCode( StringUtils.trimToNull( rs.getString( "codehotel" ) ) );
	            				hot.setName( StringUtils.trimToNull( rs.getString( "nomhotel" ) ) );
	            				hot.setResaPhone( StringUtils.trimToNull( rs.getString( "telresa" ) ) );
	            				hot.setResaFax( StringUtils.trimToNull( rs.getString( "faxresa" ) ) );
	            				
	            				Address addr = hot.getAddress();
	            				addr.setAddress1( StringUtils.trimToNull( rs.getString( "adr1hotel" ) ) );
	            				addr.setAddress2( StringUtils.trimToNull( rs.getString( "adr2hotel" ) ) );
	            				addr.setAddress3( StringUtils.trimToNull( rs.getString( "adr3hotel" ) ) );
	            				addr.setZipCode( StringUtils.trimToNull( rs.getString( "cphotel" ) ) );
	            				addr.setCity( StringUtils.trimToNull( rs.getString( "villehotel" ) ) );
	            				addr.setCountryId( StringUtils.trimToNull( rs.getString( "codepays" ) ) );
	            				addr.setCountryName( StringUtils.trimToNull( rs.getString( "nompays" ) ) );
	            				addr.setStateId( StringUtils.trimToNull( rs.getString( "codeetat" ) ) );
	            				addr.setStateName( StringUtils.trimToNull( rs.getString( "nometat" ) ) );
	            				hot.setAddress( addr );
	            				
	            				Coordinates coord = hot.getCoordinates();
	            				coord.setPhonePrefix( StringUtils.trimToNull( rs.getString( "indtelhotel" ) ) );
	            				coord.setPhone( StringUtils.trimToNull( rs.getString( "telhotel" ) ) );
	            				coord.setFax( StringUtils.trimToNull( rs.getString( "faxhotel" ) ) );
	            				coord.setMail( StringUtils.trimToNull( rs.getString( "adremail" ) ) );
	            				hot.setCoordinates( coord );
	            				
	            				Devise currency = hot.getCurrency();
	            				currency.setCode( StringUtils.trimToNull( rs.getString( "codedevise" ) ) );
	            				currency.setLibelle( StringUtils.trimToNull( rs.getString( "nomdevise" ) ) );
	            				hot.setCurrency( currency );
	            				
	            				Element chain = hot.getChain();
	            				chain.setCode( StringUtils.trimToNull( rs.getString( "cchaine" ) ) );
	            				chain.setLibelle( StringUtils.trimToNull( rs.getString( "nchaine" ) ) );
	            				hot.setChain( chain );
	            				
	            				Element mark = hot.getMark();
	            				mark.setCode( StringUtils.trimToNull( rs.getString( "cmarque" ) ) );
	            				mark.setLibelle( StringUtils.trimToNull( rs.getString( "nmarque" ) ) );
	            				hot.setMark( mark );
	
	            				Element DOP = new Element();
	            				DOP.setCode( StringUtils.trimToNull( rs.getString( "codediroper" ) ) );
	            				DOP.setLibelle( StringUtils.trimToNull( rs.getString( "nomdiroper" ) ) );
	            				hot.setDOP( DOP );
	            				
	            				Element DGR = new Element();
	            				DGR.setCode( StringUtils.trimToNull( rs.getString( "coderegion" ) ) );
	            				DGR.setLibelle( StringUtils.trimToNull( rs.getString( "coderegion" ) ) );
	            				hot.setDGR( DGR );
	
	            				Element place = new Element();
	            				place.setCode( StringUtils.trimToNull( rs.getString( "codeplace" ) ) );
	            				place.setLibelle( StringUtils.trimToNull( rs.getString( "nomplace" ) ) );
	            				hot.setPlace( place );
	            				
	            				Element widePlace = new Element();
	            				widePlace.setCode( StringUtils.trimToNull( rs.getString( "codeplaceelargie" ) ) );
	            				widePlace.setLibelle( StringUtils.trimToNull( rs.getString( "nomplaceelargie" ) ) );
	            				hot.setWidePlace( widePlace );
	
	            				SaleZone sz = new SaleZone();
	            				sz.setCode( StringUtils.trimToNull( rs.getString( "codezonevente" ) ) );
	            				sz.setLibelle( StringUtils.trimToNull( rs.getString( "nomzone" ) ) );
	            				hot.setSaleZone( sz );
	            				
	            				AsaCategory asa = new AsaCategory();
	            				asa.setCode( StringUtils.trimToNull( rs.getString( "codeasacategorie" ) ) );
	            				asa.setLibelle( StringUtils.trimToNull( rs.getString( "nomasacategorie" ) ) );
	            				hot.setAsaCategory(asa);

                                hot.setFlagAsaTarifLight(StringUtils.trimToNull( rs.getString( "flagAsaTarifLight" ) ));
                                hot.setFlagAsaTarifLoisir(StringUtils.trimToNull( rs.getString( "flagAsaTarifLoisir" ) ));
                                
                                return hot;
	            			}
	            		}
	            );
	        	if( hotelInCache == null ) {
	        		hotelInCache = new HotelCachable( rid, contexte.getCodeLangue() );
	        	}
	        	hotelInCache.setHotel( hotel );
	        	CacheManager.getInstance().setObjectInCache( hotelInCache );

	        } catch( SQLException ex ) {
	        	LogCommun.major( contexte.getCodeUtilisateur(), "HotelDAO", "getHotel", "codeHotel : " + rid, ex );
	            throw new TechnicalException( ex );
	        }
    	}
        return hotelInCache.getHotel();
    }

    /***
     * Retourne la liste des contacts de l'hotel
     * @param rid
     * @param contexte
     * @return List<HotelContact>
     * @throws TechnicalException
     */
    public List<HotelContact> getStaff( final String rid, final Contexte contexte ) 
    		throws TechnicalException {
		
    	String[] cacheParams = new String[] { rid, contexte.getCodeLangue() };
    	
    	HotelCachable hotel = (HotelCachable) CacheManager.getInstance()
    			.getObjectInCache( HotelCachable.class, cacheParams );
    	
        if( hotel == null || hotel.getStaff() == null ) {

        	SQLParamDescriptor param = new SQLParamDescriptor( rid, false, Types.VARCHAR );
		
	    	try {
	    		HotelStaff staff = (HotelStaff) SQLCallExecuter.getInstance().executeSelectSingleObjetProc(
						"local..co_SelHotStaff_R2", new SQLParamDescriptorSet( param ),
						"HotelDAO", "getStaff", contexte.getContexteAppelDAO(),
						new SQLResultSetReader() {
							public Object instanciateFromLine(ResultSet rs) throws SQLException {
								HotelStaff staff = new HotelStaff();
								staff.setFrontOfficeManager( StringUtils.trimToNull( rs.getString( "NOMCHEF" ) ) );
								staff.setGeneralManager( StringUtils.trimToNull( rs.getString( "NOMDIR" ) ) );
								staff.setAssistGeneralManager( StringUtils.trimToNull( rs.getString( "assistgenemanager" ) ) );
								staff.setExecutiveChief( StringUtils.trimToNull( rs.getString( "execchief" ) ) );
								staff.setReservationManager( StringUtils.trimToNull( rs.getString( "resamanager" ) ) );
								staff.setGroupsManager( StringUtils.trimToNull( rs.getString( "RESPGP" ) ) );
								staff.setRoomDivisionManager( StringUtils.trimToNull( rs.getString( "RESPHEBER" ) ) );
								staff.setBanquetingManager( StringUtils.trimToNull( rs.getString( "sembanqmanager" ) ) );
								staff.setBanquetingManagerMail( StringUtils.trimToNull( rs.getString( "emailSBManager" ) ) );
								staff.setMarketingManager( StringUtils.trimToNull( rs.getString( "mktmanager" ) ) );
								staff.setMarketingManagerMail( StringUtils.trimToNull( rs.getString( "mktemail" ) ) );
								return staff;
							}
						}
				);
				if( hotel == null ) {
					hotel = new HotelCachable( rid, contexte.getCodeLangue() );
				}
	    		hotel.setStaff( buildListeContactsHotel( staff, contexte ) );
	    		CacheManager.getInstance().setObjectInCache( hotel );
	    		
	    	} catch( SQLException e ) {
	    		LogCommun.major( contexte.getCodeUtilisateur(), "HotelDAO", "getStaff", "codeHotel : " + rid, e );
	            throw new TechnicalException( e );
			}
        }
        return hotel.getStaff();
	}

    /**
	 * @return List<HotelContact>
	 * Attention : les clés pour l'internationalisation sont "VENTE_OH_CONTACT_HOTEL_" + le nom de la colonne renvoyé 
	 * par le tronc commun
	 */
	private List<HotelContact> buildListeContactsHotel( final HotelStaff staff, final Contexte contexte ) {
		List<HotelContact> contacts = new ArrayList<HotelContact>();
		Translator trans = TranslatorFactory.getTranslator( contexte.getCodeLangue(), true );
		addListeContactsHotel( contacts, staff.getFrontOfficeManager(),    "FH_CONTACTS_NOM_CHEF"       , trans );
		addListeContactsHotel( contacts, staff.getGeneralManager(),        "FH_CONTACTS_NOM_DIRECTEUR"  , trans );
		addListeContactsHotel( contacts, staff.getGroupsManager(),         "FH_CONTACTS_RESP_GP"        , trans );
		addListeContactsHotel( contacts, staff.getRoomDivisionManager(),   "FH_CONTACTS_RESP_HEBER"     , trans );
		addListeContactsHotel( contacts, staff.getReservationManager(),    "FH_CONTACTS_RESAMANAGER"    , trans );
		addListeContactsHotel( contacts, staff.getAssistGeneralManager(),  "FH_CONTACTS_ASSISTANT_GENE_MANAGER", trans );
		addListeContactsHotel( contacts, staff.getExecutiveChief(),        "FH_CONTACTS_EXEC_CHIEF"     , trans );
		addListeContactsHotel( contacts, staff.getBanquetingManager(),     "FH_CONTACTS_BANQUE_MANAGER" , trans );
		addListeContactsHotel( contacts, staff.getBanquetingManagerMail(), "FH_CONTACTS_BANQUE_MANAGER_EMAIL", trans );
		addListeContactsHotel( contacts, staff.getMarketingManager(),      "FH_CONTACTS_SALES_MANAGER"  , trans );
		addListeContactsHotel( contacts, staff.getMarketingManagerMail(),  "FH_CONTACTS_SALES_MANAGER_EMAIL", trans );
		return contacts;
	}
	private void addListeContactsHotel( List<HotelContact> contacts, String contact, String cleFonction, Translator trans ) {
		if( StringUtils.isNotBlank( contact ) )
			contacts.add( new HotelContact( contact, trans.getLibelle( cleFonction ) ) );
	}
}



