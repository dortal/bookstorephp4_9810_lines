var MENU_CSS_CLASSNAME = "Menu";
var CURRENT_MENU_CSS_CLASSNAME = "CurrentMenu";
var STYLE_CSS_CLASS_NAME = "Style";

var menuDiv = null;

// Création du menu
function initMenu () {
	var menuItems = new Array();
	var cpt = 0;
	for (var i = 0; i < menu.length; i ++) {
		if (menu[i] != null) {
			menuItems[cpt++] = menu[i];
		}
	}
	createMenu(menuItems, "menu");
}

// Application du style
function initStyle () {
	if (typeof getCurrentMenuIndex == 'function' && getCurrentMenuIndex() >= 0) {
		var mainDiv = document.getElementById("mainDiv");
		mainDiv.className = STYLE_CSS_CLASS_NAME + getCurrentMenuIndex();
	}
}

function createMenu (menuItems, menuDivId) {
  menuDiv = document.getElementById(menuDivId);
  if (menuItems instanceof Array && menuDiv != null) {
    for (var menuIndex = 0; menuIndex < menuItems.length; menuIndex ++) {
      if (menuItems[menuIndex] instanceof Array && menuItems[menuIndex].length >= 2) {
        var menu = document.createElement("div");
        menu.className = MENU_CSS_CLASSNAME + menuIndex;

        var table = document.createElement("table");
        table.onmouseover = function () {
         this.style.cursor = "pointer";
        }

        var thead = document.createElement("thead");
        thead.appendChild(getHeadRow(menuItems[menuIndex][0], menuItems[menuIndex][1], menuIndex));
        table.appendChild(thead);

        var tbody = document.createElement("tbody");
        if (menuItems[menuIndex].length > 2) {
          for (var subMenuIndex = 2; subMenuIndex < menuItems[menuIndex].length; subMenuIndex ++) {
            tbody.appendChild(getRow(menuItems[menuIndex][subMenuIndex][0], menuItems[menuIndex][subMenuIndex][1]));
          }
        }
        else {
          tbody.appendChild(getRow("", null));
        }
        tbody.style.display = "none";
        table.appendChild(tbody);

        menu.appendChild(table);
        menuDiv.appendChild(menu);
      }
    }
  }
}

function getRow (text, href) {
  var tr = document.createElement("tr");
  var td = document.createElement("td");
  var a = document.createElement("a");
  a.innerHTML = text;
  if (href != null) {
  	a.href = href;
  }
  td.appendChild(a);
  tr.appendChild(td);
  return tr;
}

function getHeadRow (text, href, menuIndex) {
  var tr = document.createElement("tr");
  var th = document.createElement("th");
  th.innerHTML = text;
  if (href == null) {
    th.onclick = function () {
      openMenu(menuIndex);
    }
  }
  else {
    th.onclick = function () {
      window.location.href = href;
    }
  }
  if (typeof getCurrentMenuIndex == 'function' && menuIndex == getCurrentMenuIndex()) {
    th.className = CURRENT_MENU_CSS_CLASSNAME;
  }
  tr.appendChild(th);
  return tr;
}

function openMenu (menuIndex) {
  if (menuDiv != null) {
    var tbodys = menuDiv.getElementsByTagName("tbody");
    for (var i = 0; i < tbodys.length; i ++) {
      if (i == menuIndex) {
        tbodys[i].style.display = tbodys[i].style.display == "none" ? "" : "none";
      }
      else {
        tbodys[i].style.display = "none";
      }
    }
  }
}