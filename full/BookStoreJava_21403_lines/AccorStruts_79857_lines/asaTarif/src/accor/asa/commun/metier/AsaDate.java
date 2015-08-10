package com.accor.asa.commun.metier;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@SuppressWarnings("serial")
public class AsaDate implements java.io.Serializable
{
    private Date date;
    public static final int SYBASE          = 1;
    public static final int ASA             = 2;
    public static final int TARS            = 3;
    public static final int SYBASE_DATETIME = 4;


    public static final String defaulDateFormat = "dd/MM/yyyy";

    /**
     * crée une date a partir d'une chaine
     * format = mm-dd-yyyy si provenance = SYBASE
     * format = dd/mm/yyyy si provenance = ASA
    */
    public AsaDate(String uneDate, int provenance) throws java.text.ParseException {
        SimpleDateFormat df;
        if (provenance == SYBASE) {
            df = new SimpleDateFormat("MM-dd-yyyy");
        } else if (provenance == ASA) {

            if( (uneDate!=null) && (uneDate.length() == 8) ) {
                //Année sur 2 caractères
                if(testeFormat(uneDate, "dd/MM/yy")) {
                    uneDate = passeAnneeSur4Chiffres(uneDate, '/');
                } else if(testeFormat(uneDate, "dd-MM-yy")) {
                    uneDate = passeAnneeSur4Chiffres(uneDate, '-');
                } else {
                    uneDate = passeAnneeSur4Chiffres(uneDate, ' ');
                }
            }

            if(testeFormat(uneDate, "dd/MM/yyyy")) {
                df = new SimpleDateFormat("dd/MM/yyyy");
            } else if(testeFormat(uneDate, "dd MM yyyy")) {
                df = new SimpleDateFormat("dd MM yyyy");
            } else if(testeFormat(uneDate, "dd-MM-yyyy")) {
                df = new SimpleDateFormat("dd-MM-yyyy");
            } else {
                df = new SimpleDateFormat("MM-dd-yyyy");
            }

        } else if (provenance == TARS) {
            df = new SimpleDateFormat(
                "MMM dd yyyy hh:mm aaaa",
                Locale.ENGLISH
            );
        } else if (provenance == SYBASE_DATETIME) {
            if(testeFormat(uneDate, "yyyy-MM-dd")) {
                df = new SimpleDateFormat("yyyy-MM-dd");
            } else {
            	df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            }
        } else {
            df = new SimpleDateFormat("MM-dd-yyyy");
        }
        date = df.parse(uneDate);
    }

    public AsaDate() {
    	setDate(new Date());
    }

    public AsaDate(Date date) {
    	setDate(date);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date uneDate) {
        date = uneDate;
    }

    public String toString() {
        //SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        //return df.format(date);
    	return formatDateForDisplay(date);
    }

	public String afficherAvecCodeJour() {
		SimpleDateFormat df = new SimpleDateFormat("EEEdd/MM/yyyy");
		return df.format(date);
	}

    public String getSybaseDate() {
       return formatDateForSybase(date);
    }

    public String getSybaseDate110() {
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        return df.format(date);
    }

        public String getFormatedDate( String choosenFormat )
        {
            SimpleDateFormat df = new SimpleDateFormat( choosenFormat );
            if(date == null){
            	return null;
            }
            return df.format( date );
        }

    public long getDaysBetween(AsaDate uneDate) {
        double joursMili = uneDate.getDate().getTime() - date.getTime();
        //Ano 2918 : On arrondi sinon 0.99 devient 0       
        long jours = Math.round(joursMili / (60000*60*24));
        
        return jours;
    }

    public boolean equals( AsaDate uneDate ) {
    	if( uneDate == null )
    		return false;
    	return ( compareTo( uneDate ) == 0 );
    }
    
    /**
     * Compares two Dates for ordering.
     *
     * @param   uneDate   the <code>Date</code> to be compared.
     * @return  the value <code>0</code> if the argument Date is equal to
     *          this Date; a value less than <code>0</code> if this Date
     *          is before the Date argument; and a value greater than
     *      <code>0</code> if this Date is after the Date argument.
     * @since   1.2
     */
    public int compareTo( AsaDate uneDate ) {
    	return getDate().compareTo( uneDate.getDate() );
    }
    
    private boolean testeFormat(String uneDate, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        try {
            df.parse(uneDate);
        } catch (java.text.ParseException e) {
            return false;
        }

        return true;
    }

    private String passeAnneeSur4Chiffres(String uneDate, char separateur) {
        String newDate = uneDate.substring(0, uneDate.lastIndexOf(separateur) + 1);
        newDate += "20";
        newDate += uneDate.substring(uneDate.lastIndexOf(separateur) + 1);

        return newDate;
    }
    
    public static String formatDateForDisplay(Date d)
    {
    	if(d==null)
    		return "";
    	 SimpleDateFormat df = new SimpleDateFormat(defaulDateFormat);
         return df.format(d);
    }
   
    public static String formatDateForSybase(Date d)
    {
    	if (d == null)
    		return null;
    	 SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
         return df.format(d);
    }
   
}