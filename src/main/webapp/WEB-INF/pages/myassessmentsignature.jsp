<%--
  Created by IntelliJ IDEA.
  User: zark
  Date: 16/11/26
  Time: 下午5:46
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ page isELIgnored="false"%>
<%@ include file="header.jsp"%>
<div class="container" >

    <div class="panel-heading" >
        <h4>考核表签名情况<span><button class="btn btn-info btn-sm" onclick="doUpload(${signature.assessmentId})"> 重新上传签收表</button></span></h4>
    </div>

    <div class="row" >
        <!-- Default panel contents -->
        <div class="col-sm-6">
            <table title="分配表详情" id="dg" class="easyui-datagrid"  style="width:100%;height:350px;"
                   data-options="singleSelect:true,collapsible:true  ">
                <thead>
                <tr>
                    <th data-options="field:'billingCycle',width:100">考核年月</th>
                    <th data-options="field:'zoneName',width:100">片区</th>
                    <th data-options="field:'username',width:100">姓名</th>
                    <th data-options="field:'roleName',width:100">职位</th>
                    <th data-options="field:'amount',width:100">考核金额</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="assessment" items="${staffAssessments}">
                    <tr>
                        <td >${assessment.billingCycle}</td>
                        <td  >${zoneName}</td>
                        <td  >${assessment.staffName}</td>
                        <td  >${assessment.roleName}</td>
                        <td  >${assessment.personalAmount}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col-sm-6" >
            <img id="photo"  src="/assessment/getPhotoById.html?signatureid=${signature.signatureId}"   height="350px">
        </div>
    </div><!--left-->

</div><!-- container -->

<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/viewer.min.css">

<script type="text/javascript" src="<%=basePath%>/js/viewer.min.js"></script>
<%--<script type="text/javascript" src="/js/datagrid-filter.js" ></script>--%>
<script type="text/javascript" >

    $( document).ready(function(){
        var dg = $('#dg');

        //dg.datagrid('enableFilter');
        //图片
        $('#photo').viewer();
    });
    function doFeedback(assessmentid) {
        //跳转到新窗口
        self.location = "/assessment/aa.html?assessmentid="+assessmentid;
    }

    function doViewDetail(cssNumber) {
        $.messager.alert('温馨提示','查看员工详情','info');

    }
    function doUpload(assessmentId) {
        self.location = "/assessment/myuploadsignature.html?assessmentid="+assessmentId;

    }
</script>
<%@ include file="footer.jsp"%>


