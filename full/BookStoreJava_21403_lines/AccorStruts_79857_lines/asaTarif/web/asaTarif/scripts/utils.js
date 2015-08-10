/*
  Initialiser un formulaire
*/
function cleanForm(formulaire) {
    for (var i = 0; i < formulaire.length; i++) {
        var obj = formulaire.elements[i];
        if (!obj.disabled) {
            if (obj.type == 'hidden' || obj.type == 'text')
                obj.value = '';
            else
                if (obj.type == 'select-one')
                    obj.selectedIndex = 0;
                else
                    if (obj.type == 'checkbox' || obj.type == 'radio')
                        obj.checked = false;
                    else
                        if (obj.type == 'selecte-multiple') {
                            for (var j = 0; j < obj.length; j++)
                                obj[j].checked = false;
                        }
        }
    }
}
/*
   Active / Desactive les champs d un formulaire
*/
function setFormReadOnly(formulaire, isReadOnly) {
    for (var i = 0; i < formulaire.length; i++) {
        formulaire.elements[i].disabled = isReadOnly;
    }
}

// Cette fonction selectionne la ligne de valeur egale au param value dans la combo
// 	et eventuellement la selection par defaut ( param withDefault )
function selectValueInCombo(selectField, value, withDefault) {
    if (selectField!=null && selectField.length!=null) {
        for (var i = 0; i < selectField.length; i++) {
            if (selectField[i].value == value) {
                selectField[i].selected = true;
                if (withDefault) selectField[i].defaultSelected = true;
            } else {
                selectField[i].selected = false;
            }
        }
    }
}
function checkRadioOption(radioObj, newValue) {
    if (!radioObj) return;
    var radioLength = radioObj.length;
    if (radioLength == undefined) {
        radioObj.checked = (radioObj.value == newValue.toString());
        return;
    }
    for (var i = 0; i < radioLength; i++) {
        radioObj[i].checked = false;
        if (radioObj[i].value == newValue.toString()) {
            radioObj[i].checked = true;
        } else {
            radioObj[i].checked = false;
        }
    }
}
// Permet l'ouverture d'une popup centr?e devant la fen?tre courante
function openCentered(url, width, height, windowName, featureString) {
    if (!windowName)    windowName = '';
    if (!featureString)
        featureString = '';
    else
        featureString = ',' + featureString;
    var x = Math.round((screen.availWidth - width) / 2);
    var y = Math.round((screen.availHeight - height) / 2);
    featureString = 'left=' + x + ',top=' + y + ',width=' + width + ',height=' + height + featureString;
    return open(url, windowName, featureString);
}
function validDate(s) {
    if (s == "") return false;
    var d = stringToDate (s);
    var d1 = displayStringToDate(s);
    return !((d == null || d.toString() == "NaN" || s.substring(2, 3) != s.substring(5, 6) ||
              s.substring(2, 3) != "/" && s.substring(2, 3) != "." && s.substring(2, 3) != " ") &&
             (d1.toString() == "NaN" || d1.getFullYear() < 1910));

}
function stringToDate (s) {
	  var d;
	  var separateurs="/ "; // separateurs possibles "/" et " "
 	  var itChaine = 0;
 	  var unCaractere =null;
 	  var indiceElementDate = 0; //0:jour, 1:mois, 2:année
 	  var day  = "";
      var month = "";
      var year   = "";
      while(itChaine < s.length) {
      	unCaractere = s.substring(itChaine, itChaine + 1);
      	if(separateurs.indexOf(unCaractere) != -1) {
      		indiceElementDate++;
      		//On supprime tous les séparateurs
      		while( (itChaine < s.length) && (separateurs.indexOf(unCaractere) != -1) ) {
      			unCaractere = s.substring(itChaine, itChaine + 1);
      			if(separateurs.indexOf(unCaractere) != -1)
      				itChaine++;
      		}
      	}
      	if(indiceElementDate == 0) {
      		day	+= unCaractere;
      	} else if(indiceElementDate == 1) {
      		month += unCaractere;
      	} else if(indiceElementDate == 2) {
      		year += unCaractere;
 	  	}
 	  	itChaine++;
 	  }
 	  if(indiceElementDate != 2) return null; //Saisie incorrecte
      if (year < 70) year = 20 + year;
      var listeMois = new Array('Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec');
      var indiceMois = 0;
      while( (indiceMois < listeMois.length) && (month != listeMois[indiceMois]) ) {
      	indiceMois++;
      }
      if(indiceMois < listeMois.length) {
      	month = indiceMois + 1;
      }
      if ( (isNaN(day)) || //Jour incorrect
           (day<1)      ||
           (day>31)     ||
           (isNaN(month)) || //Mois incorrect
           (month<1) ||
           (month>12) ||
           ( (year.length != 4) && (year.length != 2) ) )
      	return null;
      d = new Date (year, month - 1, day);
      if(d.toString() == "NaN") {
      	//alert("StringToDate| 1 : " + s)
      	d = displayStringToDate(s);
      }
      return d;
}
function displayStringToDate (s) {
      var index = s.indexOf(" ");
      var day  = s.substring(0, index);
      var month = s.substring(index+1, index + 4);
      var a = new Array('Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec');
      var i = 0;
      while( (i < a.length) && (month != a[i]) )
      	i++;
      month = i;
      var year = s.substring(index + 5);
      return new Date (year, month, day);
}
