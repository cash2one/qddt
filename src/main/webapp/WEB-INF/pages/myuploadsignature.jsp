<%--
  Created by IntelliJ IDEA.
  User: zark
  Date: 16/11/26
  Time: 下午5:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ include file="headerwithfile.jsp"%>
<%--<%@ include file="nav.jsp"%>--%>
<div class="container" >
    <%--<h4 class="header smaller lighter blue">上报考核</h4>--%>
    <p> <h4></h4></p>
    <div class="table-header"></div>
    <!-- form -->
    <form   role="form" action="/assessment/douploadsignature" method="post" enctype="multipart/form-data">


        <div class="form-group" >
            <label class="control-label "></label>
            <input type="hidden" id="assessmentid" name="assessmentid" value="${assessment.assessmentId}" />
            <label class="control-label">请输入备注信息 </label>
            <input class="easyui-textbox col-sm-6" width="*" data-options="multiline:true" id="remark" name="remark" placeholder="请输入备注信息" />
        </div>
        <div class="form-group" >
            <label class="control-label">选择签名图片</label>
            <input id="picfile" name="picfile" type="file" class="file"  data-show-upload="false" data-show-caption="true">
        </div>
        <p>${message}</p>
        <br>
        <button class="btn btn-lg btn-primary btn-block" type="submit">上传图片</button>
    </form>
</div>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/easyuiicon.css">
<script type="text/javascript" src="<%=basePath%>/js/jquery.easyui.min.js"></script>
<script>
    /*$(document).on('ready', function() {
     $("#input-4").fileinput({showCaption: false});
     });*/
</script>

<%@ include file="footer.jsp"%>
