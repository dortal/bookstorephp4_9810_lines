<%--
  Created by IntelliJ IDEA.
  User: SDEKOUM
  Date: 9 sept. 2008
  Time: 08:24:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<link href="${pageContext.request.contextPath}/css/calendar.css" rel=stylesheet type=text/css>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar.js"></script>
<script type="text/javascript">
// This date is used throughout to determine today's date.
var calDateNow = new Date(Date.parse(new Date().toDateString()));
//******************************************************************************
//------------------------------------------------------------------------------
// Customisation section
//------------------------------------------------------------------------------
//******************************************************************************
// Set the bounds for the calendar here...
var calBaseYear = calDateNow.getFullYear() - 5;
// How many years do want to be valid and to show in the drop-down list?
var calDropDownYears = 10;
// All language-dependent changes can be made here...
// If you wish to work in a single language (other than English) then
// just replace the English (in the function calSetLanguage below) with
// your own text.
// Using multiple languages:
// In order to keep this script to a resonable size I have not included
// languages here.  You can set language fields in a function that you
// should call  calSetLanguage  the script will use your languages.
// I have included all the translations that have been sent to me in
// such a function on the demonstration page.
var calLanguage;
function calSetDefaultLanguage() {
    calToday = "<s:text name="COM_CAL_LBL_TODAY"/>";
    calClear = "<s:text name="COM_CAL_LBL_CLEAR"/>";
    calDrag  = "<s:text name="COM_CAL_LBL_CLICKTODRAG"/>";
    calArrMonthNames = ["<s:text name="ALL_ALL_MONTH_ABR3_JANVIER"/>",
                                "<s:text name="ALL_ALL_MONTH_ABR3_FEVRIER"/>",
                                "<s:text name="ALL_ALL_MONTH_ABR3_MARS"/>",
                                "<s:text name="ALL_ALL_MONTH_ABR3_AVRIL"/>",
                                "<s:text name="ALL_ALL_MONTH_ABR3_MAI"/>",
                                "<s:text name="ALL_ALL_MONTH_ABR3_JUIN"/>",
                                "<s:text name="ALL_ALL_MONTH_ABR3_JUILLET"/>",
                                "<s:text name="ALL_ALL_MONTH_ABR3_AOUT"/>",
                                "<s:text name="ALL_ALL_MONTH_ABR3_SEPTEMBRE"/>",
                                "<s:text name="ALL_ALL_MONTH_ABR3_OCTOBRE"/>",
                                "<s:text name="ALL_ALL_MONTH_ABR3_NOVEMBRE"/>",
                                "<s:text name="ALL_ALL_MONTH_ABR3_DECEMBRE"/>"];
    calArrWeekInits = [ "<s:text name="ALL_ALL_DAY_ABR2_DIMANCHE"/>",
                                "<s:text name="ALL_ALL_DAY_ABR2_LUNDI"/>",
                                "<s:text name="ALL_ALL_DAY_ABR2_MARDI"/>",
                                "<s:text name="ALL_ALL_DAY_ABR2_MERCREDI"/>",
                                "<s:text name="ALL_ALL_DAY_ABR2_JEUDI"/>",
                                "<s:text name="ALL_ALL_DAY_ABR2_VENDREDI"/>",
                                "<s:text name="ALL_ALL_DAY_ABR2_SAMEDI"/>" ];
    calInvalidDateMsg       = "<s:text name="COM_CAL_MSG_INVALIDDATE"/>\n";
    calOutOfRangeMsg        = "<s:text name="COM_CAL_MSG_DATEOUTOFRANGE"/>";
    calDoesNotExistMsg      = "<s:text name="COM_CAL_MSG_DATENOTEXIST"/>";
    calInvalidAlert         = ["<s:text name="COM_CAL_MSG_LEFTINVALIDDATE"/>","<s:text name="COM_CAL_MSG_RIGHTINVALIDDATE"/>"];
    calDateDisablingError   = ["<s:text name="COM_CAL_MSG_ERROR"/> ","<s:text name="COM_CAL_MSG_NOTDATEOBJECT"/>"];
    calRangeDisablingError  = ["<s:text name="COM_CAL_MSG_ERROR"/> ","<s:text name="COM_CAL_MSG_SHOLD2ELEMNTS"/>"];
}
// Note:  Always start the calArrWeekInits array with your string for
//        Sunday whatever calWeekStart (below) is set to.
// calWeekStart determines the start of the week in the display
// Set it to: 0 (Zero) for Sunday, 1 (One) for Monday etc..
var calWeekStart = 1;
// The week start day for the display is taken as the week start
// for week numbering.  This ensures that only one week number
// applies to one line of the calendar table.
// [ISO 8601 begins the week with Day 1 = Monday.]
// If you want to see week numbering on the calendar, set
// this to true.  If not, false.
var calWeekNumberDisplay = false;
// Week numbering rules are generally based on a day in the week
// that determines the first week of the year.  ISO 8601 uses
// Thursday (day four when Sunday is day zero).  You can alter
// the base day here.
var calWeekNumberBaseDay = 1;
// Each of the calendar's alert message types can be disabled
// independently here.
var calShowInvalidDateMsg       = true,
    calShowOutOfRangeMsg        = true,
    calShowDoesNotExistMsg      = true,
    calShowInvalidAlert         = true,
    calShowDateDisablingError   = true,
    calShowRangeDisablingError  = true;
// Set the allowed input date delimiters here...
// E.g. To set the rising slash, hyphen, full-stop (aka stop or point),
//      comma and space as delimiters use
//      var calArrDelimiters   = ['/','-','.',',',' '];
var calArrDelimiters = ['/','-','.',',',' '];
// Set the format for the displayed 'Today' date and for the output
// date here.
//
// The format is described using delimiters of your choice (as set
// in calArrDelimiters above) and case insensitive letters D, M and Y.
//
// NOTE: If no delimiters are input then the date output format is used
//       to parse the value.  This allows less flexiblility in the input
//       value than using delimiters but an accurately entered date
//       remains parsable.
//
// Definition               Returns
// ----------               -------
// D            date in the month without zero filling
// DD           date in the month left zero filled
// M            month number without zero filling
// MM           month number left zero filled
// MMM          month string from calArrMonthNames
// YY           year number in two digits
// YYYY         year number in four digits
// Displayed "Today" date format
var calDateDisplayFormat = 'dd/mm/yyyy';
// e.g. 'MMM-DD-YYYY' for the US
// Output date format
var calDateOutputFormat = 'dd/mm/yyyy';
// e.g. 'MMM-DD-YYYY' for the US
// Note: The delimiters used should be in calArrDelimiters.
// calZindex controls how the pop-up calendar interacts with the rest
// of the page.  It is usually adequate to leave it as 1 (One) but I
// have made it available here to help anyone who needs to alter the
// level in order to ensure that the calendar displays correctly in
// relation to all other elements on the page.
var calZindex = 1;
// Personally I like the fact that entering 31-Sep-2005 displays
// 1-Oct-2005, however you may want that to be an error.  If so,
// set calBlnStrict = true.  That will cause an error message to
// display and the selected month is displayed without a selected
// day. Thanks to Brad Allan for his feedback prompting this feature.
var calBlnStrict = false;
// If you are using ReadOnly or Disabled fields to return the date
// value into, it can be useful to show a button on the calendar
// that allows the value to be cleared.  If you want to do that,
// set calClearButton = true;
var calClearButton = true;
// The calendar will position itself aligned with the bottom left
// corner of the target element.  If automatic positioning is turned
// on  with  calAutoPosition = true  then if that would cause the
// calendar to display off the visible screen, it is shifted to
// a position that is visible.
var calAutoPosition = true;
// If you wish to disable any displayed day, e.g. Every Monday,
// you can do it by setting the following array.  The array elements
// match the displayed cells.
//
// You could put something like the following in your calling page
// to disable all weekend days;
//
//  for (var i=0;i<calEnabledDay.length;i++)
//      {if (i%7%6==0) calEnabledDay[i] = false;}
//
// The above approach will allow you to disable days of the week
// for the whole of your page easily.  If you need to set different
// disabled days for a number of date input fields on your page
// there is an easier way: You can pass additional arguments to
// calShow. The syntax is described at the top of this script in
// the section:
//    "How to use the Calendar once it is defined for your page:"
//
// It is possible to use these two approaches in combination.
var calEnabledDay = [   true, true, true, true, true, true, true,
                        true, true, true, true, true, true, true,
                        true, true, true, true, true, true, true,
                        true, true, true, true, true, true, true,
                        true, true, true, true, true, true, true,
                        true, true, true, true, true, true, true];
// You can disable any specific date (e.g. 24-Jan-2006 or Today) by
// creating an element of the array calDisabledDates as a date object
// with the value you want to disable.  Date ranges can be disabled
// by placing an array of two values (Start and End) into an element
// of this array.
var calDisabledDates = new Array();
// e.g. To disable 10-Dec-2005:
//          calDisabledDates[0] = new Date(2005,11,10);
//
//      or a range from 2004-Dec-25 to 2005-Jan-01:
//          calDisabledDates[1] = [new Date(2004,11,25),new Date(2005,0,1)];
//
// Remember that Javascript months are Zero-based.

// The disabling by date and date range does prevent the current day
// from being selected.  Disabling days of the week does not so you can set
// the calActiveToday value to false to prevent selection.
var calActiveToday = true;
// Dates that are out of the displayed month are shown at the start
// (unless the month starts on the first day of the week) and end of each
// month.
//
// Set calOutOfMonthDisable to  true  to disable these dates (or  false
// to allow their selection).
//
// Set calOutOfMonthHide    to  true  to hide    these dates (or  false
// to make them visible).

var calOutOfMonthDisable    = false;
var calOutOfMonthHide       = false;
// Dates that are out of the specified range can be displayed at the start
// of the very first month and end of the very last.  Set
// calOutOfRangeDisable to  true  to disable these dates (or  false  to
// allow their selection).
var calOutOfRangeDisable = true;
// If you want a special format for the cell that contains the current day
// set this to true.  This sets a thin border around the cell in the colour
// set by calTodayCellBorderColour.
var calFormatTodayCell = true;
var calTodayCellBorderColour = 'red';
// You can allow the calendar to be dragged around the screen by
// using the setting calAllowDrag to true.
// I can't say I recommend it because of the danger of the user
// forgetting which date field the calendar will update when there
// are multiple date fields on a page.
var calAllowDrag = false;
// Closing the calendar by clicking on it (rather than elsewhere on the
// main page) can be inconvenient.  The calClickToHide boolean value
// controls this feature.
var calClickToHide = true;
//******************************************************************************
//------------------------------------------------------------------------------
// End of customisation section
//------------------------------------------------------------------------------
//******************************************************************************
function getDayFromDate(theDate) {
    var jours = new Array(  '<s:text name="ALL_ALL_DAY_FULL_DIMANCHE"/>',
                            '<s:text name="ALL_ALL_DAY_FULL_LUNDI"/>',
                            '<s:text name="ALL_ALL_DAY_FULL_MARDI"/>',
                            '<s:text name="ALL_ALL_DAY_FULL_MERCREDI"/>',
                            '<s:text name="ALL_ALL_DAY_FULL_JEUDI"/>',
                            '<s:text name="ALL_ALL_DAY_FULL_VENDREDI"/>',
                            '<s:text name="ALL_ALL_DAY_FULL_SAMEDI"/>',
                            '<s:text name="ALL_ALL_DAY_FULL_DIMANCHE"/>'    );
    return jours[theDate.getDay()];
}
</script>
