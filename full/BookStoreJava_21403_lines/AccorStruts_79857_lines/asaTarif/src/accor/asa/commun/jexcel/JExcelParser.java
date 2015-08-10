package com.accor.asa.commun.jexcel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import jxl.CellView;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.PageOrientation;
import jxl.format.UnderlineStyle;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.lang.StringUtils;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.metier.AsaDate;
import com.accor.asa.commun.metier.Contexte;

/***
 * Classe permettant de transformer un ou plusieurs objets JAVA en leur equivalent Excel
 * @author FCHIVAUX
 *
 */
public class JExcelParser {

	public static final Colour defaultHeaderBackground 	= Colour.GREY_80_PERCENT;
	public static final Colour defaultBodyBackground 	= Colour.GREY_25_PERCENT;
	public static final Colour defaultBody2Background 	= Colour.WHITE;
	
	public static final Alignment defaultHeaderAlignment 	= Alignment.CENTRE;
	public static final Alignment defaultBodyAlignment 		= Alignment.CENTRE;
	
	public static final BorderLineStyle defaultHeaderBorderStyle 	= BorderLineStyle.THIN; 
	public static final BorderLineStyle defaultBodyBorderStyle 		= BorderLineStyle.THIN; 
	
	public static final Colour defaultHeaderBorderColor 	= Colour.BLACK; 
	public static final Colour defaultBodyBorderColor 		= Colour.GREY_40_PERCENT; 

	private static JExcelParser _instance = new JExcelParser();
	
	private JExcelParser() {}
	
	public static JExcelParser getInstance() {
		return _instance;
	}
	
	/***
	 * Methode permettant de generer une tableau de donnees dans un format XLS
	 * @param output	
	 * @param datas		
	 * @param contexte
	 * @throws TechnicalException
	 */
	public void generateDatasIntoXLS( OutputStream output, final JExcelArray datas, final Contexte contexte ) 
			throws TechnicalException {

		WritableWorkbook workbook = null;
	
		try {
    		workbook = Workbook.createWorkbook( output );

    		WritableSheet sheet = workbook.createSheet( "ASA", 0 ); 
    		sheet.setPageSetup( PageOrientation.LANDSCAPE );
    		
    		int[] indexs = insertArrayIntoXLS( sheet, 0, 0, datas, contexte );
    		
    		setAutomaticSize( sheet, 0, indexs[1], 0, indexs[0]  );
    		
    		workbook.write();

		} catch( IOException e ) {
        	LogCommun.major( contexte.getCodeUtilisateur(), "JExcelParser", "generateDatasIntoXLS", "erreur lors de l ecriture dans le flux de sortie" );
        	throw new TechnicalException(e);
		} finally {
            try {
            	workbook.close();
            	workbook = null;
            } catch( WriteException e ) {
            	LogCommun.major( contexte.getCodeUtilisateur(), "JExcelParser", "generateDatasIntoXLS", "erreur lors de la fermeture du workbook" );
            	throw new TechnicalException(e);
            } catch( IOException e ) {
            	LogCommun.major( contexte.getCodeUtilisateur(), "JExcelParser", "generateDatasIntoXLS", "erreur lors de la fermeture du workbook" );
		    	throw new TechnicalException(e);
		    } 
        }
	}

	/***
	 * Methode inserant un tableau de donnees dans une feuille excel
	 * @param sheet
	 * @param datas
	 * @param startRowIndex
	 * @param startRowIndex
	 * @param contexte
	 * @return int[] : index max des colonnes et lignes
	 * @throws TechnicalException
	 */
	public int[] insertArrayIntoXLS( WritableSheet sheet, final int startColIndex, final int startRowIndex, 
			final JExcelArray datas, final Contexte contexte ) throws TechnicalException {
        
		int[] indexs = new int[2];
		
		int endColIndex = startColIndex;
		int endRowIndex = startRowIndex;

		try {
    		String[] header = datas.getHeader();
    		if( header != null ) {
	    		WritableCellFormat headerStyle = getHeaderStyle();
    			for( int i=0, size=header.length; i<size; i++ ) {
	    			Label l = new Label( endColIndex, endRowIndex, String.valueOf( header[i] ), headerStyle  );
	    			sheet.addCell( l );
	    			endColIndex++;
	            }
    			endRowIndex++;
    			indexs[0] = endColIndex;
    			indexs[1] = endRowIndex;
    		}
    		
    		List<JExcelRow> body = datas.getBody(); 
    		if( body != null ) {
    			JExcelRow rowData;
    			JExcelCell cellData;
	            WritableCellFormat cellFormat;
	            WritableCell cell;
	            Object data;
	            
	            int posX;
	            for( int i=0, size=body.size(); i<size; i++ ) {
	                rowData = body.get(i);
	                posX=startColIndex;
	                for( int j=0, size2=rowData.size(); j<size2; j++, posX++ ) {
	                    cellData = rowData.get(j); 
	                    data = cellData.getData();
	                    
                    	cellFormat = getBodyStyle( i, cellData ); 	
	                    
                    	if( data instanceof AsaDate ) {
                			cell = new DateTime( posX, endRowIndex, ( (AsaDate) data ).getDate(), cellFormat );
	                    } else {
	                    	if( data instanceof Integer ) {
	                        	cell = new jxl.write.Number( posX, endRowIndex, ( (Integer) data ).intValue(), cellFormat );
	                    	} else {
	                    		if( data instanceof Float ) {
	                            	cell = new jxl.write.Number( posX, endRowIndex, ( (Float) data ).floatValue(), cellFormat );
	                    		} else {
	                    			if( data instanceof Boolean ){
	                                	cell = new jxl.write.Boolean( posX, endRowIndex, ( (Boolean) data ).booleanValue(), cellFormat  );
	                        		} else {
	                        			cell = new Label( posX, endRowIndex, StringUtils.trimToEmpty( (String) data ), cellFormat  );
	                        		}
	                    		}
	                    	}
	                    }
                    	sheet.addCell( cell );
	                }
	                if( posX > indexs[0] )
	                	indexs[0] = posX;
	                endRowIndex++;
	            }
				indexs[1] = endRowIndex;
            }
        } catch( RowsExceededException e ) {
        	LogCommun.major( contexte.getCodeUtilisateur(), "JExcelParser", "insertArrayIntoXLS", "erreur lors de l ajout de la cellule" );
        	throw new TechnicalException(e);
        } catch( WriteException e ) {
        	LogCommun.major( contexte.getCodeUtilisateur(), "JExcelParser", "insertArrayIntoXLS", "erreur lors de l ajout de la cellule" );
        	throw new TechnicalException(e);
        }
        return indexs;
	}
	
	/***
	 * Ajuste automatiquement les tailles des colonnes en fonction de leur contenu
	 * @param sheet
	 * @param initialRow
	 * @param finalRow
	 * @param initalColumn
	 * @param finalColumn
	 */
	public void setAutomaticSize( WritableSheet sheet, final int initialRow, final int finalRow, 
			final int initalColumn, final int finalColumn ) {
		
		String cellContents;
		CellView cw;
		int colSize;

		for( int i=initalColumn; i<=finalColumn; i++ ) {
			colSize = 0;
			for( int j=initialRow; j<=finalRow; j++ ) {
				cellContents = sheet.getCell( i, j ).getContents();
				if( StringUtils.isNotBlank( cellContents) && colSize < cellContents.length() )
					colSize = cellContents.length();
			}
			cw = sheet.getColumnView( i );
			cw.setSize( ( colSize +2 ) * 256 );
			sheet.setColumnView( i, cw );
		}
	}
	
	private WritableCellFormat getHeaderStyle() throws WriteException {
		WritableFont cellFont = new WritableFont( WritableFont.ARIAL, 10, WritableFont.BOLD );
		cellFont.setUnderlineStyle( UnderlineStyle.NO_UNDERLINE );
		cellFont.setColour( Colour.WHITE );
		cellFont.setItalic( false );

		WritableCellFormat cellFormat = new WritableCellFormat( cellFont );
		cellFormat.setBackground( defaultHeaderBackground );
		cellFormat.setAlignment( defaultHeaderAlignment );
		cellFormat.setBorder( Border.ALL, defaultHeaderBorderStyle, defaultHeaderBorderColor );
		return cellFormat;
	}
	
	private WritableCellFormat getBodyStyle( final int numRow, final JExcelCell cell ) 
			throws WriteException {
    	
		WritableCellFormat cellFormat = null;
		
		WritableFont cellFont = new WritableFont( WritableFont.ARIAL, 10, WritableFont.NO_BOLD );
		cellFont.setUnderlineStyle( UnderlineStyle.NO_UNDERLINE );
		cellFont.setColour( Colour.BLACK );

		if( cell.isDeleted() ) {
			cellFont.setItalic( true );
			cellFont.setStruckout( true );
		} else {
			cellFont.setItalic( false );
		}
		
		Object data = cell.getData();

		if( data instanceof AsaDate ) {
			cellFormat = new WritableCellFormat( cellFont, new DateFormat( AsaDate.defaulDateFormat ) );
		} else {
			if( data instanceof Integer ) {
				cellFormat = new WritableCellFormat( cellFont, NumberFormats.INTEGER );
			} else {
				if( data instanceof Float ) {
					cellFormat = new WritableCellFormat( cellFont, NumberFormats.FLOAT );
				} else {
					cellFormat = new WritableCellFormat( cellFont );
				}
			}
		}
		cellFormat.setAlignment( defaultBodyAlignment );
    	cellFormat.setBorder( Border.ALL, defaultBodyBorderStyle, defaultBodyBorderColor );
    	
    	if( numRow % 2 == 0 ) {
    		cellFormat.setBackground( defaultBodyBackground );
    	} else {
    		cellFormat.setBackground( defaultBody2Background );
    	}
		
		return cellFormat;
	}
	
}
