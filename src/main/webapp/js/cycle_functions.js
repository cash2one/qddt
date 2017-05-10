
function numberSort(a,b){
    var number1 = parseFloat(a);
    var number2 = parseFloat(b);

    return (number1 > number2 ? 1 : -1);
}
/*
 INITING("正在初始化"),
 INI("初始化"),
 OPENING("正在打开"),
 OPN("已上传"),
 REP("已发布"),
 END("已结束");
 */

var nodes = {'INITING':1,'INI':2,'OPENING':3,'OPN':4,'REP':5,'END':6};
var nodeNames = {'INITING':'正在初始化','INI':'初始化','OPENING':'创建中','OPN':'已创建','REP':'已发布','END':'已结束'};

function formatterBar(state) {
    var nodeHtml = '';
    $.each(nodes,function (k,v) {
        switch(Math.sign(nodes[state]-v+1)){
            case 1:
                nodeHtml += doneNode(k,v);
                break;
            case 0:
                nodeHtml += selectedNode(k,v)
                break;
            case -1:
                nodeHtml += disabledNode(k,v)
                break;
        }
    });
    //$(nodeHtml).appendTo('#state');
    //$(selector).html(nodeHtml);
    //alert(nodeHtml);
    return formatterSmartBar(nodeHtml);
}
function formatterSmartBar() {
    var html = function () {
        /**
         <div   class="wizard_horizontal ">
         <ul    class="wizard_steps anchor">
         %1
         </ul>
         </div>
         **/
    }.getMultilines();
    var args = arguments;
    var pattern = new RegExp("%([1-" + arguments.length + "])", "g");
    return String(html).replace(pattern, function(match, index) {
        return args[index-1];
    });
}
function makeBaseNode() {
    var html = function () {
        /**
         <li>
         <a href="javascript:void(0);" class="%1" isdone="%2" rel="%3">
         <span class="step_no">%4</span>
         <span class="step_descr">
         <small>%5</small>
         </span>
         </a>
         </li>
         **/
    }.getMultilines();
    //return html;
    var args = arguments;
    var pattern = new RegExp("%([1-" + arguments.length + "])", "g");
    return String(html).replace(pattern, function(match, index) {
        return args[index-1];
    });
}

function doneNode(name,index) {
    return makeBaseNode('done',index,index,index,nodeNames[name]);
}
function selectedNode(name,index) {
    return makeBaseNode('selected',index,index,index,nodeNames[name]);
}
function disabledNode(name,index) {
    return makeBaseNode('disabled',index,index,index,nodeNames[name]);
}

Function.prototype.getMultilines = function()
{
    var lines = new String(this);
    lines = lines.substring(lines.indexOf("/**") + 3, lines.lastIndexOf("**/"));
    return lines;
}