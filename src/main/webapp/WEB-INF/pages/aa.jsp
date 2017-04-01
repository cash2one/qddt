<%--
  Created by IntelliJ IDEA.
  User: zark
  Date: 17/3/10
  Time: 上午9:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%--<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>--%>

<%
    String apppath = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+apppath+"/";
    request.setAttribute("apppath", apppath);
%>
<%--<c:url value='/' var="root" />--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html lang="zh-CN">
<head>
    <base href="<%=basePath%>">
    <title>对投资金管理</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="对投资金支撑系统">

    <link rel="stylesheet" type="text/css" href="<%=basePath%>/css/custom.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/css/bootstrap-theme.min.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/css//easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/css/easyuiicon.css">


    <script type="text/javascript" src="<%=basePath%>/js/jquery-3.1.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/functions.js"></script>
    <%--<script type="text/javascript" src="<%=basePath%>/js/jquery.datagrid.js"></script>--%>
    <script type="text/javascript">
        /*$(document).ready(function() {
         $('.collapse').collapse();
         });*/
    </script>


</head>
<body>



<!-- Smart Wizard -->
<div id="wizard" class="form_wizard wizard_horizontal col-sm-2">
    <ul class="wizard_steps anchor">
        <li>
            <a href="#step-1" class="done" isdone="1" rel="2">
                <span class="step_no">1</span>
                <span class="step_descr">
                    <%--<br>--%>
                    <small>状态1</small>
                </span>
            </a>
        </li>
        <li>
            <a href="#step-2" class="selected" isdone="1" rel="2">
                <span class="step_no">2</span>
                <span class="step_descr">
                    <%--<br>--%>
                    <small>Step 2</small>
                </span>
            </a>
        </li>
        <li>
            <a href="#step-3" class="disabled" isdone="0" rel="3">
                <span class="step_no">3</span>
                <span class="step_descr">
                    <%--<br>--%>
                    <small>Step 3 </small>
                </span>
            </a>
        </li>
        <li>
            <a href="#step-4" class="disabled" isdone="0" rel="4">
                <span class="step_no">4</span>
                <span class="step_descr">
                    <%--<br>--%>
                    <small>Step 4 </small>
                </span>
            </a>
        </li>
    </ul>


</div>
<div   class="form_wizard wizard_horizontal col-sm-2">
    <ul  id="ulstate1" class="wizard_steps anchor">
    </ul>

</div>
<div   class="form_wizard wizard_horizontal col-sm-2">
    <ul  id="ulstate2" class="wizard_steps anchor">
    </ul>
</div>
<div   class="form_wizard wizard_horizontal col-sm-2">
    <ul  id="ulstate3" class="wizard_steps anchor">
    </ul>
</div>

<script type="text/javascript" >

    var nodes = {'REP':1,'FED':2,'AUD':3,'END':4};
    var nodeNames = {'REP':'通知','FED':'已报','AUD':'已阅','END':'结束'};

    function formatterState(state,selector) {
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
        $(selector).html(nodeHtml);
        //alert(nodeHtml);
    }
    function makeBaseNode() {
        var html = function () {
            /**
             <li>
             <a href="#" class="%1" isdone="%2" rel="%3">
             <span class="step_no">%4</span>
             <span class="step_descr">
             <%--<br>--%>
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



    $(function(){
        formatterState('AUD','#ulstate1');
        formatterState('END','#ulstate2');
        formatterState('FED','#ulstate3');
    });
</script>


</body>
</html>
