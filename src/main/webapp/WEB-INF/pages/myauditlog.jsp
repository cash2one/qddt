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
        <h4>审核日志</h4>
    </div>

    <div class="panel panel-default  ">
        <!-- Default panel contents -->

        <!-- Table -->
        <table title="审核日志表" id="dg" class="easyui-datagrid"   style="width:100%;height:400px"
               data-options="singleSelect:true,collapsible:true  ">
            <thead>
                <tr>
                    <th data-options="field:'auditId',width:100">审核号</th>
                    <th data-options="field:'assessmentId',width:100">考核项ID</th>
                    <th data-options="field:'nodeName',width:200">审批节点信息</th>
                    <th data-options="field:'nodeStaff',width:100">审批人</th>
                    <th data-options="field:'submission',width:150">意见</th>
                    <th data-options="field:'remark',width:80">详情</th>
                    <th data-options="field:'stateDate',width:100">时间</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="audit" items="${audits}">
                    <tr>
                        <td>${audit.auditId}</td>
                        <td >${audit.assessmentId}</td>
                        <td  >${audit.nodeName}</td>
                        <td  >${audit.nodeStaff}</td>
                        <td  >${audit.submission}</td>
                        <td  >${audit.remark}</td>
                        <td  ><fmt:formatDate value="${audit.stateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </div>

</div>
<script type="text/javascript" src="/js/datagrid-filter.js" ></script>
<script type="text/javascript" >

    $( document).ready(function(){
        var dg = $('#dg');
        dg.datagrid({nowrap: false,
            fit: true, //datagrid自适应宽度
            fitColumn: false, //列自适应宽度
            striped: true, //行背景交换
            nowap: false, //列内容多时自动折至第二行
            emptyMsg: '无记录',
            border: true });
    });

</script>
<%@ include file="footer.jsp"%>


