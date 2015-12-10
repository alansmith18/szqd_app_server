/**
 * Created by like on 11/26/15.
 */

window.onload = function () {
    var gs = document.getElementsByTagName("g");
    for (var i = 0; i < gs.length; i++) {
        var gVar = gs[i];
        gVar.addEventListener("mouseover", onPoint);
        gVar.addEventListener("mouseover", outPoint);
    }
}

function onPoint(e) {

    var g = e.target;
    var circleVar = searchGTag(g);
    var radius = circleVar.getAttribute("r") * 2;
    circleVar.setAttribute("r", radius);
}

function outPoint(e) {
//      var circleVar = document.getElementById(circleID);
//      var radius = circleVar.getAttribute("r") / 2;
//      circleVar.setAttribute("r",radius);
}

function searchGTag(g)
{
    var childNodes = g.children;
    for (var i = 0; i < childNodes.length; i++)
    {
        var childVar = childNodes[i];
        var tagName = childVar.tagName;
        alert(tagName);
        if (tagName == 'circle')
        {
            return childVar;
        }
    }
}