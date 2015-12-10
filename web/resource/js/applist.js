var subframevisible = false;
function setFrameVisible(visible) {
	subframevisible = visible;
	var menuframe = document.getElementById("submenuframe");
	if (visible) {
		menuframe.style.display = "block";
	} else {
		menuframe.style.display = "none";
	}
}

function getFrameVisible() {
	return subframevisible;
}

function clickHandler() {
}

function documentclick() {
	if (subframevisible && event.srcElement.id != "button2") {
			subframevisible = false;
			document.getElementById("submenuframe").style.display = "none";
		}
	}

if (document.attachEvent) {
	document.attachEvent("onclick", documentclick);
} else if (document.addEventListener) {
	document.addEventListener("click", documentclick);
}



