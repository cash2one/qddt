<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="/assets/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="/assets/css/ace.min.css" />
    <link rel="stylesheet" href="/assets/css/ace-rtl.min.css" />
    <link rel="stylesheet" href="/assets/css/ace-skins.min.css" />
    <link rel="stylesheet" href="css/style.css"/>
    <title>无标题文档</title>
</head>
<!--[if lte IE 8]>
<link rel="stylesheet" href="/assets/css/ace-ie.min.css" />
<![endif]-->
<script src="/assets/js/ace-extra.min.js"></script>
<!--[if lt IE 9]>
<script src="/assets/js/html5shiv.js"></script>
<script src="/assets/js/respond.min.js"></script>
<![endif]-->
<!--[if !IE]> -->
<script src="/assets/js/jquery.min.js"></script>
<!-- <![endif]-->
<style type="text/css">
    html { overflow-x:hidden; }
</style>
<body >
<div class="page-content" id="tt">
    <div class="page-header">
        <h1>代理商对投资金管理系统<small></small></h1>
    </div><!-- /.page-header -->
    <div class="row">
        <div class="col-xs-12">
            <div class="index_style">
                <!--询价订单样式-->
                <div class="frame xjdd_style">
                    <span  class="title_name">考核管理</span>
                    <div class="content">
                        <ul class="xj_list clearfix">
                            <li><label class="label_name"><a href="/assessment/myassessmentlist.html">我的考核片区</a></label><%--<div class="xinxi"><b>10</b>个</div>--%></li>
                            <li><label class="label_name"><a href="/assessment/mystaffassessment.html?assessmentid=0">查看考核记录</a></label><%--<div class="xinxi"><b>10</b>个</div>--%></li>
                        </ul>
                    </div>
                </div>
                <!--结束-->
                <!--订单管理样式-->
                <div class="frame ddgl_style">
                    <span  class="title_name">我的查询</span>
                    <div class="content">
                        <ul class="xj_list clearfix">
                            <li><label class="label_name"><a href="/assessment/mystaffassessment.html?assessmentid=0">查询片区绩效</a></label><%--<div class="xinxi"><b>10</b>个</div>--%></li>
                            <li><label class="label_name"><a href="/user/myzonestaff.html">查询装维人员</a></label><%--<div class="xinxi"><b>10</b>个</div>--%></li>
                        </ul>
                    </div>
                </div>
                <!--结束-->
            </div>
            <!-- 首页样式结束 -->
        </div><!-- /.col -->
    </div><!-- /.row -->

</div>
</body>
</html>
