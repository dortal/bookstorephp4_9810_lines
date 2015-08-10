package com.accor.asa.rate.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.accor.asa.commun.metier.AsaDate;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class FormatCompareUtil {

    public static Date getNoLimitAsaDate ()
            throws ParseException {
        return (new AsaDate("31/12/2050",AsaDate.ASA)).getDate();
    }
    
    public static String formatDateToAsaString (Date date)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
    }

    public static boolean isDatesContinues(Date d1, Date d2) {
        if (d1==null || d2==null) return false;
        Calendar cal = Calendar.getInstance();
        cal.setTime(d1);
        cal.add(1, Calendar.DATE);
        return cal.getTime().equals(d2);
    }

    public static boolean isDateBetween(Date myDate, Date dateDebut, Date dateFin) {
        return !(myDate == null || dateDebut == null || dateFin == null) &&
                (myDate.equals(dateDebut) || myDate.equals(dateFin) ||
                (myDate.after(dateDebut) && myDate.before(dateFin)));
    }

    public static boolean isDateBetweenStrict(Date myDate, Date dateDebut, Date dateFin) {
        return !(myDate == null || dateDebut == null || dateFin == null) &&
                (myDate.after(dateDebut) && myDate.before(dateFin));
    }

    public static boolean isDateBefore(Date myDate, Date date) {
        return !(myDate == null || date == null) &&
                (myDate.equals(date) || myDate.before(date));
    }

    public static boolean isDateBeforeStrict(Date myDate, Date date) {
        return !(myDate == null || date == null) &&
                myDate.before(date);
    }

    public static boolean isDateAfter(Date myDate, Date date) {
        return !(myDate == null || date == null) &&
                (myDate.equals(date) || myDate.after(date));
    }

    public static boolean isDateAfterStrict(Date myDate, Date date) {
        return !(myDate == null || date == null) &&
                myDate.after(date);
    }

    public static boolean isIntersectionList(List<String> list1, List<String> list2) {
        if (list1==null || list1.isEmpty() || list2==null || list2.isEmpty())
            return false;
        else
            for (String e1:list1)
                for (String e2:list2)
                    if (e1.equals(e2))
                        return true;
        return false;
    }


}
