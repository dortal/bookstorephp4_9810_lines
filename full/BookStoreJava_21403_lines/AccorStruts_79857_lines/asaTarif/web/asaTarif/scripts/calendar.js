//  Variables required by both calShow and calShowMonth
var calTargetEle,
        calTriggerEle,
        calMonthSum = 0,
        calBlnFullInputDate = false,
        calPassEnabledDay = new Array(),
        calSeedDate = new Date(),
        calParmActiveToday = true,
        calWeekStart = calWeekStart % 7,
        calToday,
        calClear,
        calDrag,
        calArrMonthNames,
        calArrWeekInits,
        calInvalidDateMsg,
        calOutOfRangeMsg,
        calDoesNotExistMsg,
        calInvalidAlert,
        calDateDisablingError,
        calRangeDisablingError;
// Add a method to format a date into the required pattern
Date.prototype.calFormat =
function(calFormat) {
    var charCount = 0,
            codeChar = '',
            result = '';

    for (var i = 0; i <= calFormat.length; i++)     {
        if (i < calFormat.length && calFormat.charAt(i) == codeChar)         {
            // If we haven't hit the end of the string and
            // the format string character is the same as
            // the previous one, just clock up one to the
            // length of the current element definition
            charCount++;
        } else {
            switch (codeChar)
                    {case 'y': case 'Y':
                result += (this.getFullYear() % Math.
                        pow(10, charCount)).toString().
                        calPadLeft(charCount);
                break;
                case 'm': case 'M':
                // If we find an M, check the number of them to
                // determine whether to get the month number or
                // the month name.
                result += (charCount < 3)
                        ? (this.getMonth() + 1).
                        toString().calPadLeft(charCount)
                        : calArrMonthNames[this.getMonth()];
                break;
                case 'd': case 'D':
            // If we find a D, get the date and format it
                result += this.getDate().toString().
                        calPadLeft(charCount);
                break;
                default:
                // Copy any unrecognised characters across
                    while (charCount-- > 0) {
                        result += codeChar;
                    }
            }

            if (i < calFormat.length)
            {// Store the character we have just worked on
                codeChar = calFormat.charAt(i);
                charCount = 1;
            }
        }
    }
    return result;
};
// Add a method to left pad zeroes
String.prototype.calPadLeft =
function(padToLength) {
    var result = '';
    for (var i = 0; i < (padToLength - this.length); i++) {
        result += '0';
    }
    return (result + this);
};
// Set up a closure so that any next function can be triggered
// after the calendar has been closed AND that function can take
// arguments.

Function.prototype.runsAftercal =
function() {
    var func = this,
            args = new Array(arguments.length);

    for (var i = 0; i < args.length; ++i) {
        args[i] = arguments[i];
    }
    return function()     {
        // concat/join the two argument arrays
        for (var i = 0; i < arguments.length; ++i) {
            args[args.length] = arguments[i];
        }
        return (args.shift() == calTriggerEle) ? func.apply(this, args) : null;
    };
};
// Set up some shortcuts
function calID(id) {
    if (document.getElementById(id) || (!document.getElementById(id) && document.getElementsByName(id).length == 0)){
    // IF   An ID attribute is assigned
    // OR   No ID attribute is assigned but using IE and Opera
    //          (which will find the NAME attribute value using getElementById)
    // OR   No element has this ID or NAME attribute value
    //          (used internally by the script)
    // THEN Return the required element.
        return document.getElementById(id);
    } else {
        if (document.getElementsByName(id).length == 1) {
        // IF   No ID attribute is assigned
        // AND  Using a standards-based browser
        // AND  Only one element has the NAME attribute set to the value
        // THEN Return the required element (using the NAME attribute value).
            return document.getElementsByName(id)[0];
        } else {
            if (document.getElementsByName(id).length > 1)
            {   // IF   No ID attribute is assigned
                // AND  using a standards-based browser
                // AND  more than one element has the NAME attribute set to the value
                // THEN alert developer to fix the fault.
                alert('cal' +
                      ' \nCannot uniquely identify element named: ' + id +
                      '.\nMore than one identical NAME attribute defined' +
                      '.\nSolution: Assign the required element a unique ID attribute value.');
            }
        }
    }
}
;
// Use a global variable for the return value from the next action
// IE fails to pass the function through if the target element is in
// a form and calNextAction is not defined.
var calNextActionReturn, calNextAction;
// ****************************************************************************
// Start of Function Library
//
//  Exposed functions:
//
//      calShow             Entry point for display of calendar,
//                              called in main page.
//      showCal             Legacy name of calShow:
//                              Passes only legacy arguments,
//                              not the optional day disabling arguments.
//
//      calShowMonth        Displays a month on the calendar,
//                              Called when a month is set or changed.
//
//      calBeginDrag        Controls calendar dragging.
//
//      calCancel           Called when the calendar background is clicked:
//                              Calls calStopPropagation and may call calHide.
//      calHide             Hides the calendar, called on various events.
//      calStopPropagation  Stops the propagation of an event.
//
// ****************************************************************************
function showCal(calEle, calSource) {
    calShow(calEle, calSource);
}
;
function calShow(calEle, calSource) {
    if (!calSource) {
        calSource = window.event;
    }
    if (calSource.tagName)  {
        // Second parameter isn't an event it's an element
        var calSourceEle = calSource;
        if (calID('calIE')) {
            window.event.cancelBubble = true;
        } else {
            calSourceEle.parentNode.addEventListener('click', calStopPropagation, false);
        }
    } else   {
        // Second parameter is an event
        var calSourceEle = (calSource.target)
                ? calSource.target
                : calSource.srcElement;
        // Stop the click event that opens the calendar from bubbling up to
        // the document-level event handler that hides it!
        if (calSource.stopPropagation) {
            calSource.stopPropagation();
        } else {
            calSource.cancelBubble = true;
        }
    }
    calTriggerEle = calSourceEle;
    // Take any parameters that there might be from the third onwards as
    // day numbers to be disabled 0 = Sunday through to 6 = Saturday.
    calParmActiveToday = true;
    for (var i = 0; i < 7; i++) {
        calPassEnabledDay[(i + 7 - calWeekStart) % 7] = true;
        for (var j = 2; j < arguments.length; j++) {
            if (arguments[j] == i) {
                calPassEnabledDay[(i + 7 - calWeekStart) % 7] = false;
                if (calDateNow.getDay() == i) {
                    calParmActiveToday = false;
                }
            }
        }
    }
    //   If no value is preset then the seed date is
    //      Today (when today is in range) OR
    //      The middle of the date range.
    calSeedDate = calDateNow;
    // Find the date and Strip space characters from start and
    // end of date input.
    var calDateValue = '';
    if (calEle.value) {
        calDateValue = calEle.value.replace(/^\s+/, '').replace(/\s+$/, '');
    } else {
        if (typeof calEle.value == 'undefined') {
            var calChildNodes = calEle.childNodes;
            for (var i = 0; i < calChildNodes.length; i++) {
                if (calChildNodes[i].nodeType == 3) {
                    calDateValue = calChildNodes[i].nodeValue.replace(/^\s+/, '').replace(/\s+$/, '');
                    if (calDateValue.length > 0) {
                        calTriggerEle.calTextNode = calChildNodes[i];
                        calTriggerEle.calLength = calChildNodes[i].nodeValue.length;
                        break;
                    }
                }
            }
        }
    }
    // Set the language-dependent elements
    calSetDefaultLanguage();
    calID('calDragText').innerHTML = calDrag;
    calID('calMonths').options.length = 0;
    for (var i = 0; i < calArrMonthNames.length; i++) {
        calID('calMonths').options[i] = new Option(calArrMonthNames[i], calArrMonthNames[i]);
    }
    calID('calYears').options.length = 0;
    for (var i = 0; i < calDropDownYears; i++) {
        calID('calYears').options[i] = new Option((calBaseYear + i), (calBaseYear + i));
    }
    for (var i = 0; i < calArrWeekInits.length; i++) {
        calID('calWeekInit' + i).innerHTML = calArrWeekInits[(i + calWeekStart) % calArrWeekInits.length];
    }
    if (((new Date(calBaseYear + calDropDownYears, 0, 0)) > calDateNow &&
         (new Date(calBaseYear, 0, 0)) < calDateNow) ||
        (calClearButton && (calEle.readOnly || calEle.disabled)) ) {
        calID('calFoot').style.display = '';
        calID('calNow').innerHTML = calToday + ' ' + calDateNow.calFormat(calDateDisplayFormat);
        calID('calClearButton').value = calClear;
        if ((new Date(calBaseYear + calDropDownYears, 0, 0)) > calDateNow &&
            (new Date(calBaseYear, 0, 0)) < calDateNow ) {
            calID('calNow').style.display = '';
            if (calClearButton && (calEle.readOnly || calEle.disabled)) {
                calID('calClear').style.display = '';
                calID('calClear').style.textAlign = 'left';
                calID('calNow').style.textAlign = 'right';
            } else {
                calID('calClear').style.display = 'none';
                calID('calNow').style.textAlign = 'center';
            }
        } else {
            calID('calClear').style.textAlign = 'center';
            calID('calClear').style.display = '';
            calID('calNow').style.display = 'none';
        }
    } else {
        calID('calFoot').style.display = 'none';
    }
    if (calDateValue.length == 0) {
        // If no value is entered and today is within the range,
        // use today's date, otherwise use the middle of the valid range.
        calBlnFullInputDate = false;
        if ((new Date(calBaseYear + calDropDownYears, 0, 0)) < calSeedDate ||
            (new Date(calBaseYear, 0, 1)) > calSeedDate ) {
            calSeedDate = new Date(calBaseYear + Math.floor(calDropDownYears / 2), 5, 1);
        }
    } else {
        function calInputFormat() {
            var calArrSeed = new Array(),
                    calArrInput = calDateValue.split(new RegExp('[\\' + calArrDelimiters.join('\\') + ']+', 'g'));
            // "Escape" all the user defined date delimiters above -
            // several delimiters will need it and it does no harm for
            // the others.
            // Strip any empty array elements (caused by delimiters)
            // from the beginning or end of the array. They will
            // still appear in the output string if in the output
            // format.
            if (calArrInput[0] != null) {
                if (calArrInput[0].length == 0) {
                    calArrInput.splice(0, 1);
                }
                if (calArrInput[calArrInput.length - 1].length == 0) {
                    calArrInput.splice(calArrInput.length - 1, 1);
                }
            }
            calBlnFullInputDate = false;
            calDateOutputFormat = calDateOutputFormat.toUpperCase();
            // List all the allowed letters in the date format
            var template = ['D','M','Y'];
            // Prepare the sequence of date input elements
            var result = new Array();
            for (var i = 0; i < template.length; i++) {
                if (calDateOutputFormat.search(template[i]) > -1) {
                    result[calDateOutputFormat.search(template[i])] = template[i];
                }
            }
            var calDateSequence = result.join('');
                // Separate the elements of the date input
                switch (calArrInput.length) {
                    case 1: {
                    if (calDateOutputFormat.indexOf('Y') > -1 &&
                        calArrInput[0].length > calDateOutputFormat.lastIndexOf('Y')) {
                        calArrSeed[0] = parseInt(calArrInput[0].substring(calDateOutputFormat.indexOf('Y'),
                                calDateOutputFormat.lastIndexOf('Y') + 1), 10);
                    } else {
                        calArrSeed[0] = 0;
                    }
                    if (calDateOutputFormat.indexOf('M') > -1 &&
                        calArrInput[0].length > calDateOutputFormat.lastIndexOf('M')) {
                        calArrSeed[1] = calArrInput[0].substring(calDateOutputFormat.indexOf('M'),
                                calDateOutputFormat.lastIndexOf('M') + 1);
                    } else {
                        calArrSeed[1] = '6';
                    }
                    if (calDateOutputFormat.indexOf('D') > -1 &&
                        calArrInput[0].length > calDateOutputFormat.lastIndexOf('D')) {
                        calArrSeed[2] = parseInt(calArrInput[0].substring(calDateOutputFormat.indexOf('D'),
                                calDateOutputFormat.lastIndexOf('D') + 1), 10);
                    } else {
                        calArrSeed[2] = 1;
                    }
                    if (calArrInput[0].length == calDateOutputFormat.length) {
                        calBlnFullInputDate = true;
                    }
                    break;
                }
                case 2: {
                    // Year and Month entry
                    calArrSeed[0] =
                    parseInt(calArrInput[calDateSequence.
                            replace(/D/i, '').
                            search(/Y/i)], 10);
                    // Year
                    calArrSeed[1] = calArrInput[calDateSequence.
                            replace(/D/i, '').
                            search(/M/i)];
                    // Month
                    calArrSeed[2] = 1;
                    // Day
                    break;
                }
                case 3: {
                    // Day Month and Year entry
                    calArrSeed[0] =
                    parseInt(calArrInput[calDateSequence.
                            search(/Y/i)], 10);
                    // Year
                    calArrSeed[1] = calArrInput[calDateSequence.
                            search(/M/i)];
                    // Month
                    calArrSeed[2] =
                    parseInt(calArrInput[calDateSequence.
                            search(/D/i)], 10);
                    // Day
                    calBlnFullInputDate = true;
                    break;
                }
                default: {
                    // A stuff-up has led to more than three elements in
                    // the date.
                    calArrSeed[0] = 0;
                    // Year
                    calArrSeed[1] = 0;
                    // Month
                    calArrSeed[2] = 0;
                    // Day
                }
            }
            // These regular expressions validate the input date format
            // to the following rules;
            //         Day   1-31 (optional zero on single digits)
            //         Month 1-12 (optional zero on single digits)
            //                     or case insensitive name
            //         Year  One, Two or four digits

            // Months names are as set in the language-dependent
            // definitions and delimiters are set just below there
            var calExpValDay = new RegExp('^(0?[1-9]|[1-2][0-9]|3[0-1])$'),
                    calExpValMonth = new RegExp('^(0?[1-9]|1[0-2]|' +
                                                calArrMonthNames.join('|') +
                                                ')$', 'i'),
                    calExpValYear = new RegExp('^([0-9]{1,2}|[0-9]{4})$');
            // Apply validation and report failures
            if (calExpValYear.exec(calArrSeed[0]) == null ||
                calExpValMonth.exec(calArrSeed[1]) == null ||
                calExpValDay.exec(calArrSeed[2]) == null ) {
                if (calShowInvalidDateMsg) {
                    alert(calInvalidDateMsg +
                          calInvalidAlert[0] + calDateValue +
                          calInvalidAlert[1]);
                }
                calBlnFullInputDate = false;
                calArrSeed[0] = calBaseYear +
                                Math.floor(calDropDownYears / 2);
                // Year
                calArrSeed[1] = '6';
                // Month
                calArrSeed[2] = 1;
                // Day
            }
            // Return the  Year    in calArrSeed[0]
            //             Month   in calArrSeed[1]
            //             Day     in calArrSeed[2]

            return calArrSeed;
        }
        ;
        // Parse the string into an array using the allowed delimiters
        calArrSeedDate = calInputFormat();
        // So now we have the Year, Month and Day in an array.
        //   If the year is one or two digits then the routine assumes a
        //   year belongs in the 21st Century unless it is less than 50
        //   in which case it assumes the 20th Century is intended.
        if (calArrSeedDate[0] < 100) {
            calArrSeedDate[0] += (calArrSeedDate[0] > 50) ? 1900 : 2000;
        }
        // Check whether the month is in digits or an abbreviation
        if (calArrSeedDate[1].search(/\d+/) < 0) {
            for (i = 0; i < calArrMonthNames.length; i++) {
                if (calArrSeedDate[1].toUpperCase() == calArrMonthNames[i].toUpperCase()) {
                    calArrSeedDate[1] = i + 1;
                    break;
                }
            }
        }
        calSeedDate = new Date(calArrSeedDate[0], calArrSeedDate[1] - 1, calArrSeedDate[2]);
    }
    // Test that we have arrived at a valid date
    if (isNaN(calSeedDate)) {
        if (calShowInvalidDateMsg) {
            alert(calInvalidDateMsg + calInvalidAlert[0] + calDateValue + calInvalidAlert[1]);
        }
        calSeedDate = new Date(calBaseYear + Math.floor(calDropDownYears / 2), 5, 1);
        calBlnFullInputDate = false;
    } else {
        // Test that the date is within range,
        // if not then set date to a sensible date in range.
        if ((new Date(calBaseYear, 0, 1)) > calSeedDate) {
            if (calBlnStrict && calShowOutOfRangeMsg) {
                alert(calOutOfRangeMsg);
            }
            calSeedDate = new Date(calBaseYear, 0, 1);
            calBlnFullInputDate = false;
        } else {
            if ((new Date(calBaseYear + calDropDownYears, 0, 0)) < calSeedDate) {
                if (calBlnStrict && calShowOutOfRangeMsg) {
                    alert(calOutOfRangeMsg);
                }
                calSeedDate = new Date(calBaseYear + Math.floor(calDropDownYears) - 1, 11, 1);
                calBlnFullInputDate = false;
            } else {
                if (calBlnStrict && calBlnFullInputDate &&
                    (calSeedDate.getDate() != calArrSeedDate[2] ||
                     (calSeedDate.getMonth() + 1) != calArrSeedDate[1] ||
                     calSeedDate.getFullYear() != calArrSeedDate[0]
                            )
                        )
                {
                    if (calShowDoesNotExistMsg) alert(calDoesNotExistMsg);
                    calSeedDate = new Date(calSeedDate.getFullYear(), calSeedDate.getMonth() - 1, 1);
                    calBlnFullInputDate = false;
                }
            }
        }
    }
    // Test the disabled dates for validity
    // Give error message if not valid.
    for (var i = 0; i < calDisabledDates.length; i++) {
        if (!((typeof calDisabledDates[i] == 'object') && (calDisabledDates[i].constructor == Date))) {
            if ((typeof calDisabledDates[i] == 'object') && (calDisabledDates[i].constructor == Array)) {
                var calPass = true;
                if (calDisabledDates[i].length != 2) {
                    if (calShowRangeDisablingError) {
                        alert(calRangeDisablingError[0] + calDisabledDates[i] + calRangeDisablingError[1]);
                    }
                    calPass = false;
                } else {
                    for (var j = 0; j < calDisabledDates[i].length; j++) {
                        if (!((typeof calDisabledDates[i][j] == 'object') && (calDisabledDates[i][j].constructor == Date))) {
                            if (calShowRangeDisablingError) {
                                alert(calDateDisablingError[0] + calDisabledDates[i][j] + calDateDisablingError[1]);
                            }
                            calPass = false;
                        }
                    }
                }
                if (calPass && (calDisabledDates[i][0] > calDisabledDates[i][1])) {
                    calDisabledDates[i].reverse();
                }
            } else {
                if (calShowRangeDisablingError) {
                    alert(calDateDisablingError[0] + calDisabledDates[i] + calDateDisablingError[1]);
                }
            }
        }
    }
    // Calculate the number of months that the entered (or
    // defaulted) month is after the start of the allowed
    // date range.
    calMonthSum = 12 * (calSeedDate.getFullYear() - calBaseYear) + calSeedDate.getMonth();
    calID('calYears').options.selectedIndex = Math.floor(calMonthSum / 12);
    calID('calMonths').options.selectedIndex = (calMonthSum % 12);
    // Check whether or not dragging is allowed and display drag handle if necessary
    calID('calDrag').style.display = (calAllowDrag) ? '' : 'none';
    // Display the month
    calShowMonth(0);
    // Position the calendar box
    // The object sniffing for Opera allows for the fact that Opera
    // is the only major browser that correctly reports the position
    // of an element in a scrollable DIV.  This is because IE and
    // Firefox omit the DIV from the offsetParent tree.
    calTargetEle = calEle;
    var offsetTop = parseInt(calEle.offsetTop, 10) + parseInt(calEle.offsetHeight, 10),
            offsetLeft = parseInt(calEle.offsetLeft, 10);
    if (!window.opera) {
        while (calEle.tagName != 'BODY' && calEle.tagName != 'HTML') {
            offsetTop -= parseInt(calEle.scrollTop, 10);
            offsetLeft -= parseInt(calEle.scrollLeft, 10);
            calEle = calEle.parentNode;
        }
        calEle = calTargetEle;
    }
    do {
        calEle = calEle.offsetParent;
        offsetTop += parseInt(calEle.offsetTop, 10);
        offsetLeft += parseInt(calEle.offsetLeft, 10);
    }
    while (calEle.tagName != 'BODY' && calEle.tagName != 'HTML');
    if (calAutoPosition) {
        var calWidth = parseInt(calID('cal').offsetWidth, 10),
                calHeight = parseInt(calID('cal').offsetHeight, 10),
                calWindowLeft =
                        (document.body && document.body.scrollLeft)
                                ? document.body.scrollLeft                  //DOM compliant
                                : (document.documentElement && document.documentElement.scrollLeft)
                                ? document.documentElement.scrollLeft   //IE6+ standards compliant
                                : 0,                                    //Failed
                calWindowWidth =
                        (typeof(innerWidth) == 'number')
                                ? innerWidth                                //DOM compliant
                                : (document.documentElement && document.documentElement.clientWidth)
                                ? document.documentElement.clientWidth  //IE6+ standards compliant
                                : (document.body && document.body.clientWidth)
                                ? document.body.clientWidth         //IE non-compliant
                                : 0,                                //Failed
                calWindowTop =
                        (document.body && document.body.scrollTop)
                                ? document.body.scrollTop                   //DOM compliant
                                : (document.documentElement && document.documentElement.scrollTop)
                                ? document.documentElement.scrollTop    //IE6+ standards compliant
                                : 0,                                    //Failed
                calWindowHeight =
                        (typeof(innerHeight) == 'number')
                                ? innerHeight                               //DOM compliant
                                : (document.documentElement && document.documentElement.clientHeight)
                                ? document.documentElement.clientHeight //IE6+ standards compliant
                                : (document.body && document.body.clientHeight)
                                ? document.body.clientHeight        //IE non-compliant
                                : 0;
        //Failed
        offsetLeft -= (offsetLeft - calWidth + parseInt(calTargetEle.offsetWidth, 10) >= calWindowLeft &&
                       offsetLeft + calWidth > calWindowLeft + calWindowWidth
                ) ? (calWidth - parseInt(calTargetEle.offsetWidth, 10)) : 0;

        offsetTop -= (offsetTop - calHeight - parseInt(calTargetEle.offsetHeight, 10) >= calWindowTop &&
                      offsetTop + calHeight > calWindowTop + calWindowHeight
                ) ? (calHeight + parseInt(calTargetEle.offsetHeight, 10)) : 0;
    }
    calID('cal').style.top = offsetTop + 'px';
    calID('cal').style.left = offsetLeft + 'px';
    calID('calIframe').style.top = offsetTop + 'px';
    calID('calIframe').style.left = offsetLeft + 'px';

    calID('calIframe').style.width = (calID('cal').offsetWidth - (calID('calIE') ? 2 : 4)) + 'px';
    calID('calIframe').style.height = (calID('cal').offsetHeight - (calID('calIE') ? 2 : 4)) + 'px';
    calID('calIframe').style.visibility = 'inherit';
    // Show it on the page
    calID('cal').style.visibility = 'inherit';
}
;
function calHide() {
    calID('cal').style.visibility = 'hidden';
    calID('calIframe').style.visibility = 'hidden';
    if (typeof calNextAction != 'undefined' && calNextAction != null) {
        calNextActionReturn = calNextAction();
        // Explicit null set to prevent closure causing memory leak
        calNextAction = null;
    }
}
;
function calCancel(calEvt) {
    if (calClickToHide) {
        calHide();
    }
    calStopPropagation(calEvt);
}
;

function calStopPropagation(calEvt) {
    if (calEvt.stopPropagation) {
        calEvt.stopPropagation();
    } else {
        // Capture phase
        calEvt.cancelBubble = true;
    }
    // Bubbling phase
}
;

function calBeginDrag(event) {
    var elementToDrag = calID('cal');
    var deltaX = event.clientX,
            deltaY = event.clientY,
            offsetEle = elementToDrag;
    do {
        deltaX -= parseInt(offsetEle.offsetLeft, 10);
        deltaY -= parseInt(offsetEle.offsetTop, 10);
        offsetEle = offsetEle.offsetParent;
    }
    while (offsetEle.tagName != 'BODY' &&
           offsetEle.tagName != 'HTML');
    if (document.addEventListener)    {
        document.addEventListener('mousemove', moveHandler, true);
        // Capture phase
        document.addEventListener('mouseup', upHandler, true);
        // Capture phase
    }  else {
        elementToDrag.attachEvent('onmousemove', moveHandler);
        // Bubbling phase
        elementToDrag.attachEvent('onmouseup', upHandler);
        // Bubbling phase
        elementToDrag.setCapture();
    }
    calStopPropagation(event);
    function moveHandler(calEvt)     {
        if (!calEvt) calEvt = window.event;
        elementToDrag.style.left = (calEvt.clientX - deltaX) + 'px';
        elementToDrag.style.top = (calEvt.clientY - deltaY) + 'px';
        calID('calIframe').style.left = (calEvt.clientX - deltaX) + 'px';
        calID('calIframe').style.top = (calEvt.clientY - deltaY) + 'px';
        calStopPropagation(calEvt);
    }
    ;
    function upHandler(calEvt)    {
        if (!calEvt) calEvt = window.event;
        if (document.removeEventListener)        {
            document.removeEventListener('mousemove', moveHandler, true);
            // Capture phase
            document.removeEventListener('mouseup', upHandler, true);
            // Capture phase
        } else {
            elementToDrag.detachEvent('onmouseup', upHandler);
            // Bubbling phase
            elementToDrag.detachEvent('onmousemove', moveHandler);
            // Bubbling phase
            elementToDrag.releaseCapture();
        }
        calStopPropagation(calEvt);
    }
    ;
}
;
function calShowMonth(calBias) {
    // Set the selectable Month and Year
    // May be called: from the left and right arrows
    //                  (shift month -1 and +1 respectively)
    //                from the month selection list
    //                from the year selection list
    //                from the showCal routine
    //                  (which initiates the display).
    var calShowDate = new Date(Date.parse(new Date().toDateString())),
            calStartDate = new Date();
    // Set the time to the middle of the day so that the handful of
    // regions that have daylight saving shifts that change the day
    // of the month (i.e. turn the clock back at midnight or forward
    // at 23:00) do not mess up the date display in the calendar.
    calShowDate.setHours(12);
    calSelYears = calID('calYears');
    calSelMonths = calID('calMonths');
    if (calSelYears.options.selectedIndex > -1)    {
        calMonthSum = 12 * (calSelYears.options.selectedIndex) + calBias;
        if (calSelMonths.options.selectedIndex > -1) {
            calMonthSum += calSelMonths.options.selectedIndex;
        }
    }    else    {
        if (calSelMonths.options.selectedIndex > -1) {
            calMonthSum += calSelMonths.options.selectedIndex;
        }
    }
    calShowDate.setFullYear(calBaseYear + Math.floor(calMonthSum / 12), (calMonthSum % 12), 1);
    // If the Week numbers are displayed, shift the week day names to the right.
    calID('calWeek_').style.display = (calWeekNumberDisplay) ? '' : 'none';
    // Opera has a bug with setting the selected index.
    // It requires the following work-around to force SELECTs to display correctly.
    if (window.opera)    {
        calID('calMonths').style.display = 'inherit';
        calID('calYears').style.display = 'inherit';
    }
    // Set the drop down boxes.
    calTemp = (12 * parseInt((calShowDate.getFullYear() - calBaseYear), 10)) + parseInt(calShowDate.getMonth(), 10);
    if (calTemp > -1 && calTemp < (12 * calDropDownYears))    {
        calSelYears.options.selectedIndex = Math.floor(calMonthSum / 12);
        calSelMonths.options.selectedIndex = (calMonthSum % 12);
        calCurMonth = calShowDate.getMonth();
        calShowDate.setDate((((calShowDate.
                getDay() - calWeekStart) < 0) ? -6 : 1) +
                            calWeekStart - calShowDate.getDay());
        // This statement moved by Michael Cerveny to make version 3.55
        var calCompareDateValue = new Date(calShowDate.getFullYear(),
                calShowDate.getMonth(),
                calShowDate.getDate()).valueOf();
        calStartDate = new Date(calShowDate);
        if ((new Date(calBaseYear + calDropDownYears, 0, 0)) > calDateNow &&
            (new Date(calBaseYear, 0, 0)) < calDateNow)        {
            var calNow = calID('calNow');
            function calNowOutput() {
                calSetOutput(calDateNow);
            }
            ;
            if (calDisabledDates.length == 0) {
                if (calActiveToday && calParmActiveToday) {
                    calNow.onclick = calNowOutput;
                    calNow.className = 'calNow';
                    if (calID('calIE')) {
                        calNow.onmouseover = calChangeClass;
                        calNow.onmouseout = calChangeClass;
                    }
                } else {
                    calNow.onclick = null;
                    calNow.className = 'calNowDisabled';
                    if (calID('calIE')) {
                        calNow.onmouseover = null;
                        calNow.onmouseout = null;
                    }
                    if (document.addEventListener) {
                        calNow.addEventListener('click', calStopPropagation, false);
                    } else {
                        calNow.attachEvent('onclick', calStopPropagation);
                    }
                }
            } else {
                for (var k = 0; k < calDisabledDates.length; k++) {
                    if (!calActiveToday || !calParmActiveToday ||
                        ((typeof calDisabledDates[k] == 'object') &&
                         (((calDisabledDates[k].constructor == Date) &&
                           calDateNow.valueOf() == calDisabledDates[k].valueOf()
                                 ) ||
                          ((calDisabledDates[k].constructor == Array) &&
                           calDateNow.valueOf() >= calDisabledDates[k][0].valueOf() &&
                           calDateNow.valueOf() <= calDisabledDates[k][1].valueOf()
                                  )
                                 )
                                )
                            ) {
                        calNow.onclick = null;
                        calNow.className = 'calNowDisabled';
                        if (calID('calIE')) {
                            calNow.onmouseover = null;
                            calNow.onmouseout = null;
                        }
                        if (document.addEventListener) {
                            calNow.addEventListener('click', calStopPropagation, false);
                        } else {
                            calNow.attachEvent('onclick', calStopPropagation);
                        }
                        break;
                    } else {
                        calNow.onclick = calNowOutput;
                        calNow.className = 'calNow';
                        if (calID('calIE')) {
                            calNow.onmouseover = calChangeClass;
                            calNow.onmouseout = calChangeClass;
                        }
                    }
                }
            }
        }
        function calSetOutput(calOutputDate) {
            if (typeof calTargetEle.value == 'undefined') {
                calTriggerEle.calTextNode.replaceData(0, calTriggerEle.calLength, calOutputDate.calFormat(calDateOutputFormat));
            } else {
                calTargetEle.value = calOutputDate.calFormat(calDateOutputFormat);
            }
            // Executer la fonction onChange
            if(calTargetEle.onchange != null)
                calTargetEle.onchange()
            calHide();
        }
        ;
        function calCellOutput(calEvt) {
            var calEle = calEventTrigger(calEvt),
            calOutputDate = new Date(calStartDate);
            if (calEle.nodeType == 3) calEle = calEle.parentNode;
            calOutputDate.setDate(calStartDate.getDate() + parseInt(calEle.id.substr(8), 10));
            calSetOutput(calOutputDate);
        }
        ;
        function calChangeClass(calEvt) {
            var calEle = calEventTrigger(calEvt);
            if (calEle.nodeType == 3) {
                calEle = calEle.parentNode;
            }
            switch (calEle.className) {
                case 'calCells':
                    calEle.className = 'calCellsHover';
                    break;
                case 'calCellsHover':
                    calEle.className = 'calCells';
                    break;
                case 'calCellsExMonth':
                    calEle.className = 'calCellsExMonthHover';
                    break;
                case 'calCellsExMonthHover':
                    calEle.className = 'calCellsExMonth';
                    break;
                case 'calCellsWeekend':
                    calEle.className = 'calCellsWeekendHover';
                    break;
                case 'calCellsWeekendHover':
                    calEle.className = 'calCellsWeekend';
                    break;
                case 'calNow':
                    calEle.className = 'calNowHover';
                    break;
                case 'calNowHover':
                    calEle.className = 'calNow';
                    break;
                case 'calInputDate':
                    calEle.className = 'calInputDateHover';
                    break;
                case 'calInputDateHover':
                    calEle.className = 'calInputDate';
            }
            return true;
        }
        function calEventTrigger(calEvt) {
            if (!calEvt) {
                calEvt = event;
            }
            return calEvt.target || calEvt.srcElement;
        }
        ;
        function calWeekNumber(calInDate) {
            // The base day in the week of the input date
            var calInDateWeekBase = new Date(calInDate);
            calInDateWeekBase.setDate(calInDateWeekBase.getDate()
                    - calInDateWeekBase.getDay()
                    + calWeekNumberBaseDay
                    + ((calInDate.getDay() >
                        calWeekNumberBaseDay) ? 7 : 0));
            // The first Base Day in the year
            var calFirstBaseDay = new Date(calInDateWeekBase.getFullYear(), 0, 1);

            calFirstBaseDay.setDate(calFirstBaseDay.getDate()
                    - calFirstBaseDay.getDay()
                    + calWeekNumberBaseDay
                    );
            if (calFirstBaseDay < new Date(calInDateWeekBase.getFullYear(), 0, 1)) {
                calFirstBaseDay.setDate(calFirstBaseDay.getDate() + 7);
            }
            // Start of Week 01
            var calStartWeekOne = new Date(calFirstBaseDay
                    - calWeekNumberBaseDay
                    + calInDate.getDay());
            if (calStartWeekOne > calFirstBaseDay) {
                calStartWeekOne.setDate(calStartWeekOne.getDate() - 7);
            }
            // Subtract the date of the current week from the date of the
            // first week of the year to get the number of weeks in
            // milliseconds.  Divide by the number of milliseconds
            // in a week then round to no decimals in order to remove
            // the effect of daylight saving.  Add one to make the first
            // week, week 1.  Place a string zero on the front so that
            // week numbers are zero filled.
            var calWeekNo = '0' + (Math.round((calInDateWeekBase - calFirstBaseDay) / 604800000, 0) + 1);
            // Return the last two characters in the week number string
            return calWeekNo.substring(calWeekNo.length - 2, calWeekNo.length);
        }
        ;
        // Treewalk to display the dates.
        // I tried to use getElementsByName but IE refused to cooperate
        // so I resorted to this method which works for all tested
        // browsers.
        var calCells = calID('calCells');
        for (i = 0; i < calCells.childNodes.length; i++) {
            var calRows = calCells.childNodes[i];
            if (calRows.nodeType == 1 && calRows.tagName == 'TR') {
                if (calWeekNumberDisplay) {
                    //Calculate the week number using calShowDate
                    calTmpEl = calRows.childNodes[0];
                    calTmpEl.innerHTML = calWeekNumber(calShowDate);
                    calTmpEl.style.borderColor =
                    (calTmpEl.currentStyle)
                            ? calTmpEl.currentStyle['backgroundColor']
                            : (window.getComputedStyle)
                            ? document.defaultView.getComputedStyle(calTmpEl, null).getPropertyValue('background-color')
                            : '';
                    calTmpEl.style.display = '';
                } else {
                    calRows.childNodes[0].style.display = 'none';
                }
                for (j = 1; j < calRows.childNodes.length; j++) {
                    var calCols = calRows.childNodes[j];
                    if (calCols.nodeType == 1 && calCols.tagName == 'TD') {
                        calRows.childNodes[j].innerHTML =
                        calShowDate.getDate();
                        var calCell = calRows.childNodes[j],
                                calDisabled =
                                        ((calOutOfRangeDisable &&
                                          (calShowDate <
                                           (new Date(calBaseYear, 0, 1,
                                                   calShowDate.getHours()))
                                                  ||
                                           calShowDate >
                                           (new Date(calBaseYear +
                                                     calDropDownYears, 0, 0,
                                                   calShowDate.getHours()))
                                                  )
                                                ) ||
                                         (calOutOfMonthDisable &&
                                          (calShowDate <
                                           (new Date(calShowDate.getFullYear(),
                                                   calCurMonth, 1,
                                                   calShowDate.getHours()))
                                                  ||
                                           calShowDate >
                                           (new Date(calShowDate.getFullYear(),
                                                   calCurMonth + 1, 0,
                                                   calShowDate.getHours()))
                                                  )
                                                 )
                                                ) ? true : false;
                        calCell.style.visibility =
                        (calOutOfMonthHide &&
                         (calShowDate <
                          (new Date(calShowDate.getFullYear(),
                                  calCurMonth, 1,
                                  calShowDate.getHours()))
                                 ||
                          calShowDate >
                          (new Date(calShowDate.getFullYear(),
                                  calCurMonth + 1, 0,
                                  calShowDate.getHours()))
                                 )
                                ) ? 'hidden' : 'inherit';
                        for (var k = 0; k < calDisabledDates.length; k++) {
                            if ((typeof calDisabledDates[k] == 'object') &&
                                (calDisabledDates[k].constructor == Date) &&
                                calCompareDateValue == calDisabledDates[k].valueOf() ) {
                                calDisabled = true;
                            } else {
                                if ((typeof calDisabledDates[k] == 'object') &&
                                    (calDisabledDates[k].constructor == Array) &&
                                    calCompareDateValue >= calDisabledDates[k][0].valueOf() &&
                                    calCompareDateValue <= calDisabledDates[k][1].valueOf() ) {
                                    calDisabled = true;
                                }
                            }
                        }
                        if (calDisabled ||
                            !calEnabledDay[j - 1 + (7 * ((i * calCells.childNodes.length) / 6))] ||
                            !calPassEnabledDay[(j - 1 + (7 * (i * calCells.childNodes.length / 6))) % 7] ) {
                            calRows.childNodes[j].onclick = null;
                            if (calID('calIE')) {
                                calRows.childNodes[j].onmouseover = null;
                                calRows.childNodes[j].onmouseout = null;
                            }
                            calCell.className =
                            (calShowDate.getMonth() != calCurMonth)
                                    ? 'calCellsExMonthDisabled'
                                    : (calBlnFullInputDate &&
                                       calShowDate.toDateString() ==
                                       calSeedDate.toDateString())
                                    ? 'calInputDateDisabled'
                                    : (calShowDate.getDay() % 6 == 0)
                                    ? 'calCellsWeekendDisabled'
                                    : 'calCellsDisabled';

                            calCell.style.borderColor =
                            (calFormatTodayCell && calShowDate.toDateString() == calDateNow.toDateString())
                                    ? calTodayCellBorderColour
                                    : (calCell.currentStyle)
                                    ? calCell.currentStyle['backgroundColor']
                                    : (window.getComputedStyle)
                                    ? document.defaultView.getComputedStyle(calCell, null).getPropertyValue('background-color')
                                    : '';
                        } else {
                            calRows.childNodes[j].onclick = calCellOutput;
                            if (calID('calIE')) {
                                calRows.childNodes[j].onmouseover = calChangeClass;
                                calRows.childNodes[j].onmouseout = calChangeClass;
                            }
                            calCell.className =
                            (calShowDate.getMonth() != calCurMonth)
                                    ? 'calCellsExMonth'
                                    : (calBlnFullInputDate &&
                                       calShowDate.toDateString() ==
                                       calSeedDate.toDateString())
                                    ? 'calInputDate'
                                    : (calShowDate.getDay() % 6 == 0)
                                    ? 'calCellsWeekend'
                                    : 'calCells';

                            calCell.style.borderColor =
                            (calFormatTodayCell && calShowDate.toDateString() == calDateNow.toDateString())
                                    ? calTodayCellBorderColour
                                    : (calCell.currentStyle)
                                    ? calCell.currentStyle['backgroundColor']
                                    : (window.getComputedStyle)
                                    ? document.defaultView.getComputedStyle(calCell, null).getPropertyValue('background-color')
                                    : '';
                        }
                        calShowDate.setDate(calShowDate.getDate() + 1);
                        calCompareDateValue = new Date(calShowDate.getFullYear(), calShowDate.getMonth(), calShowDate.getDate()).valueOf();
                    }
                }
            }
        }
    }
    // Opera has a bug with setting the selected index.
    // It requires the following work-around to force SELECTs to display correctly.
    // Also Opera's poor dynamic rendering prior to 9.5 requires
    // the visibility to be reset to prevent garbage in the calendar
    // when the displayed month is changed.
    if (window.opera) {
        calID('calMonths').style.display = 'inline';
        calID('calYears').style.display = 'inline';
        calID('cal').style.visibility = 'hidden';
        calID('cal').style.visibility = 'inherit';
    }
}
;
// *************************
//  End of Function Library
// *************************

// ***************************
// Start of Calendar structure
// ***************************
document.writeln("<!--[if IE]><div id='calIE'></div><![endif]-->");
document.writeln("<!--[if lt IE 7]><div id='calIElt7'></div><![endif]-->");
document.write(
        "<iframe class='cal' " + (calID('calIElt7') ? "src='/calblank.html '" : '') +
        "id='calIframe' name='calIframe' frameborder='0'>" +
        "</iframe>" +
        "<table id='cal' class='cal'>" +
        "<tr class='cal'>" +
        "<td class='cal'>" +
        "<table class='calHead' id='calHead' width='100%' " +
        "cellspacing='0' cellpadding='0'>" +
        "<tr id='calDrag' style='display:none;'>" +
        "<td colspan='4' class='calDrag' " +
        "onmousedown='calBeginDrag(event);'>" +
        "<span id='calDragText'></span>" +
        "</td>" +
        "</tr>" +
        "<tr class='calHead' >" +
        "<td class='calHead'>" +
        "<input class='calHead' id='calHeadLeft' type='button' value='<' " +
        "onclick='calShowMonth(-1);'  /></td>" +
        "<td class='calHead'>" +
        "<select id='calMonths' class='calHead' " +
        "onchange='calShowMonth(0);'>" +
        "</select>" +
        "</td>" +
        "<td class='calHead'>" +
        "<select id='calYears' class='calHead' " +
        "onchange='calShowMonth(0);'>" +
        "</select>" +
        "</td>" +
        "<td class='calHead'>" +
        "<input class='calHead' id='calHeadRight' type='button' value='>' " +
        "onclick='calShowMonth(1);' /></td>" +
        "</tr>" +
        "</table>" +
        "</td>" +
        "</tr>" +
        "<tr class='cal'>" +
        "<td class='cal'>" +
        "<table class='calCells' align='center'>" +
        "<thead>" +
        "<tr><td class='calWeekNumberHead' id='calWeek_' ></td>");
for (i = 0; i < 7; i++) {
    document.write("<td class='calWeek' id='calWeekInit" + i + "'></td>");
}
document.write("</tr>" + "</thead>" + "<tbody id='calCells' onClick='calStopPropagation(event);'>");
for (i = 0; i < 6; i++) {
    document.write( "<tr>" + "<td class='calWeekNo' id='calWeek_" + i + "'></td>");
    for (j = 0; j < 7; j++) {
        document.write( "<td class='calCells' id='calCell_" + (j + (i * 7)) + "'></td>");
    }
    document.write(
            "</tr>");
}
document.write(
        "</tbody>" +
        "<tfoot>" +
        "<tr id='calFoot'>" +
        "<td colspan='8' style='padding:0px;'>" +
        "<table width='100%'>" +
        "<tr>" +
        "<td id='calClear' class='calClear'>" +
        "<input type='button' id='calClearButton' class='calClear' " +
        "onclick='calTargetEle.value = \"\";calHide();' />" +
        "</td>" +
        "<td class='calNow' id='calNow'></td>" +
        "</tr>" +
        "</table>" +
        "</td>" +
        "</tr>" +
        "</tfoot>" +
        "</table>" +
        "</td>" +
        "</tr>" +
        "</table>");
if (document.addEventListener) {
    calID('cal').addEventListener('click', calCancel, false);
    calID('calHeadLeft').addEventListener('click', calStopPropagation, false);
    calID('calMonths').addEventListener('click', calStopPropagation, false);
    calID('calMonths').addEventListener('change', calStopPropagation, false);
    calID('calYears').addEventListener('click', calStopPropagation, false);
    calID('calYears').addEventListener('change', calStopPropagation, false);
    calID('calHeadRight').addEventListener('click', calStopPropagation, false);
} else {
    calID('cal').attachEvent('onclick', calCancel);
    calID('calHeadLeft').attachEvent('onclick', calStopPropagation);
    calID('calMonths').attachEvent('onclick', calStopPropagation);
    calID('calMonths').attachEvent('onchange', calStopPropagation);
    calID('calYears').attachEvent('onclick', calStopPropagation);
    calID('calYears').attachEvent('onchange', calStopPropagation);
    calID('calHeadRight').attachEvent('onclick', calStopPropagation);
}
// ***************************
//  End of Calendar structure
// ***************************

// ****************************************
// Start of document level event definition
// ****************************************
if (document.addEventListener) {
    document.addEventListener('click', calHide, false);
} else {
    document.attachEvent('onclick', calHide);
}
// ****************************************
//  End of document level event definition
// ****************************************