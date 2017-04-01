<%--
  Created by IntelliJ IDEA.
  User: zark
  Date: 16/11/27
  Time: 下午11:05
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