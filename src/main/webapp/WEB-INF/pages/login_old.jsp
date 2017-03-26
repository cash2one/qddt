<%--
  Created by IntelliJ IDEA.
  User: zark
  Date: 16/11/27
  Time: 下午11:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <title>EOP用户管理</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="EOP">

    <link rel="stylesheet" type="text/css" href="<%=basePath%>/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/css/bootstrap-theme.min.css">

    <script type="text/javascript" src="<%=basePath%>/js/jquery-3.1.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $('.collapse').collapse();
        });
    </script>

</head>
<body>
<header class="page-header text-center">
    <h1>用户登录页面</h1>
</header>
<div class="container">
    <div class="form-horizontal">
        <div class="form-group">
            <label class="col-sm-2 control-label">用户名</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="" placeholder="请输入用户名">
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label">密码</label>
            <div class="col-sm-10">
                <input type="password" class="form-control" name="" placeholder="请输入密码">
            </div>
        </div>

        <%--<div class="form-group">--%>
            <%--<label class="control-label col-sm-2">--%>
                <%--性别--%>
            <%--</label>--%>
            <%--<div class="col-sm-10">--%>
                <%--<input type="radio" name="sex"> 男--%>
                <%--<input type="radio" name="sex"> 女--%>
            <%--</div>--%>
        <%--</div>--%>

        <div class="form-group">
            <div class="col-sm-2">

            </div>
            <div class="col-sm-10">
                <button class="btn btn-success">注册</button>
                <button class="btn btn-primary" type="submit" class="col-sm-6">登录</button>
            </div>
        </div>
    </div>
</div>

</body>
</html>