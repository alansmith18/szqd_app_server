$(function(){
	$("[name='easyTip']").each(function(){
		$(this).addClass("onShow");
	});
	$("[reg]").blur(function(){
		validate($(this));
	});
	
	$("[reg]").click(function(){
		$(this).nextAll("[name='easyTip']").eq(0).removeClass();
		$(this).nextAll("[name='easyTip']").eq(0).addClass("onFocus");				   
	});
	
	$("form").submit(function(){
		var isSubmit = true;
		$("[reg]").each(function(){
			if(!validate($(this))){
				isSubmit = false;
			}
		});
		return isSubmit;
	});
	
});

function validate(obj){
	var reg = new RegExp(obj.attr("reg"));
	var objValue = obj.attr("value");
	var tipObj = obj.nextAll("[name='easyTip']").eq(0);
	tipObj.removeClass();
	if(!reg.test(objValue)){
		tipObj.addClass("onError");
		return false;
	}else{
		tipObj.addClass("onCorrect");
		return true;
	}
}

function countChar(textareaObj, textObj, len){  
  var val = textareaObj.value;
  if (typeof len === 'undefined') {
    len = 500;
  }
  var slen = len - val.length;
  if(slen < 0) {
    textObj.className = 'overlen';
    textObj.innerHTML = '已超出 ' + (-slen).toString() + ' 字';  
  } else{
    textObj.className = '';
    textObj.innerHTML = '还能输入 ' + slen.toString() + ' 字';
  }  
}  
$('input,select,textarea').focus(function(e) {
  $(this).css('border-color', '#CDD8E9');
}).blur(function(e) {
  $(this).css('border-color', '#ccc');
});
var dTextarea = $('textarea');
dTextarea.each(function(index, obj) {
  obj.textObj = $(obj).next().children('span').get(0);
});
dTextarea.keyup(function(e) {
  countChar(this, this.textObj);
});
setInterval(function() {
  dTextarea.each(function(index, obj) {
    countChar(obj, obj.textObj);
  });
}, 500);



