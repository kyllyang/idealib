var contentX = 0;
var contentY = 0;
var tableHightlightTrClassName = "";

function onMouseOutForTable(tr) {
	tr.className = tableHightlightTrClassName;
}

function onMouseOverForTable(tr) {
	tableHightlightTrClassName = tr.className;
	tr.className = "hightlight";
}

function onMouseOutForButton(button) {
	button.className = "out";
}

function onMouseOverForButton(button) {
	button.className = "over";
}

function onMouseDownForButton(button) {
	button.className = "down";
}

function onMouseUpForButton(button) {
	button.className = "up";
}

function getMouseCoordinate(event) {
	return {
		x:event.clientX + document.body.scrollLeft + document.documentElement.scrollLeft,
		y:event.clientY + document.body.scrollTop + document.documentElement.scrollTop
	};
}

function getElementArrayByName(name) {
	var array = new Array();
	var i = 0;
	while (true) {
		var e = document.getElementById(name + "[" + (i++) + "]");
		if (e == undefined) {
			break;
		}
		array.push(e);
	}
	return array;
}
