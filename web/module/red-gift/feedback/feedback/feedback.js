/**
 * Created by brj on 2015/7/6.
 */
addLoadEvent(addEvent);
function addLoadEvent(func) {
    var oldload = window.onload;
    if (typeof window.onload != "function") {
        window.onload = func;
    } else {
        window.onload = function() {
            oldload();
            func();
        }
    }
}
function addEvent(){
    creatbag();
    clickListfg();
    clickRadio();
}
function creatbag() {
    var fgs = document.getElementsByClassName("form-group");
    var label;
    for (var i = 0; i < fgs.length; i++) {
        label = fgs[i].getElementsByTagName("label");
        var iTag = document.createElement("i");
        fgs[i].insertBefore(iTag, label[0]);
    }
}
function clickListfg() {
    var fgs = document.getElementsByClassName("form-group");
    var cn = "form-group clearfix";
    var cns = "form-group clearfix selected";
    for (var i = 0; i < fgs.length; i++) {
        fgs[i].onclick = function()
        {
            var checkbox = this.getElementsByTagName("input");
            var ckb = checkbox[0];
            addFlicker(this, 50, 200, "#e9e9e9", "#f3f3f3");
            if (this.className == cn)
            {
                ckb.checked = true;
                this.className = cns;
            } else {
                this.className = cn;
            }
        }
    }
}
function addFlicker(element, offer1, offer2, str1, str2) {
    setTimeout(function() {
            element.style.backgroundColor = str1;
        },
        offer1);
    setTimeout(function() {
            element.style.backgroundColor = str2;
        },
        offer2);
}
/**
 * [clickRadio 自定义单选按钮]
 * @return {[type]} [description]
 */
function clickRadio(){
	var rg = "gender";
	clickRadioList(rg,2);
	var ag = "age";
	clickRadioList(ag,6);
}
/**
 * [clickRadioList 添加点击按钮被选中的样式]
 * @param  {[type]} A [不同类型的单选按钮]
 * @param  {[type]} I [不同类型的单选按钮的个数]
 * @return {[type]}   [description]
 */
function clickRadioList(A,I) {
    for (var i = 0; i < I; i++) {
        var fr = document.getElementById("radio-" + A + "-"+(i+1));
    	fr.onclick = function(){
    		clearAllRadioStyle(A);
    		var frcn = this.className;
    		var hasSelected = frcn.indexOf("selected-ga");
            // 判断className中是否有“selected-ga”
            // 没有，就添加" selected-ga"样式

            var checkbox = this.getElementsByTagName("input");
            var ckb = checkbox[0];

    		if(hasSelected == -1){
    			this.className = frcn +" selected-ga";
                ckb.checked = true;
    		}
            else{
                ckb.checked = false;
            }
    	}
    }
}
/**
 * [clearOther 清除所有按钮的样式]
 * @param  {[type]} A [不同类型的单选按钮]
 * @return {[type]}   [description]
 */
function clearAllRadioStyle(A){
	var fs = document.getElementsByClassName("form-radio-"+A);
    for (var i = 0; i < fs.length; i++) {
    	var fscn = fs[i].className;
    	var hasSelected = fscn.indexOf("selected-ga");
        // 判断className中是否有“selected-ga”
        // 有，就删除" selected-ga"的样式
    	if(hasSelected != -1){
    		fscn = fscn.substring(0, (hasSelected-1));
    		fs[i].className = fscn;
    	}
    }
}