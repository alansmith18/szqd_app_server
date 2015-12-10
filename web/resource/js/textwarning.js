function countChar(textareaObj, textObj, len){  
if (textObj == undefined) return;
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

function startCount() {
$('input,select,textarea').focus(function(e) {
  $(this).css('border-color', '#CDD8E9');
}).blur(function(e) {
  $(this).css('border-color', '#ccc');
});
var dTextarea = $('textarea');
dTextarea.each(function(index, obj) {
  obj.textObj = $(obj).next().children('span').get(0);
});
dTextarea.bind("keyup", function(e, p) {
  countChar(this, this.textObj);
});
dTextarea.each(function(index, obj) {
    countChar(obj, obj.textObj);
  });

}