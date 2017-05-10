<%--
  Created by IntelliJ IDEA.
  User: zark
  Date: 16/11/26
  Time: 下午5:46
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ include file="header.jsp"%>
<div class="container" >


    <div class="panel panel-default">
        <!-- Default panel contents -->
        <%--<div class="panel-heading information btn-info">${message}</div>--%>
        <form action="/mark/docommit" method="POST">
            <input type="hidden" name="eventId" value="${eventid}"/>
            <div>
                <button type="submit" class="btn btn-default"  >确认提交以下数据</button>
            </div>
        </form>
        <%--<div><p>${rows}</p></div>--%>
        <!-- Table -->
        <table  id="dg" class="easyui-datagrid" title="上传数据确认" style="width:100%;height:400px"
        data-options="singleSelect:true,collapsible:true  ">
            <thead>
            <tr>

                <th data-options="field:'billingCycle',width:100">账期</th>
                <th data-options="field:'zoneName',width:100">片区</th>
                <th data-options="field:'zoneId',width:100">片区编码</th>
                <th data-options="field:'delegateType',width:100">委托类型</th>
                <th data-options="field:'channelType',width:100">门店类型</th>
                <th data-options="field:'standardMark',width:100">标准化打分</th>
                <th data-options="field:'areaMark',width:100">区域打分</th>
                <th data-options="field:'criterion',width:100">评分标准</th>
                <th data-options="field:'pointGoal',width:100">积分占比目标</th>
                <th data-options="field:'stateDate',width:150">时间</th>



            </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${rows}">
                    <tr>
                    <td>${item.billingCycle}</td>
                    <td>${item.zoneName}</td>
                    <td>${item.zoneId}</td>
                    <td>${item.delegateType}</td>
                    <td>${item.channelType}</td>
                    <td>${item.standardMark}</td>
                    <td>${item.areaMark}</td>
                    <td>${item.criterion}</td>
                    <td>${item.pointGoal}</td>
                    <td><fmt:formatDate value="${item.stateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <script type="text/javascript">


            //alert(data.total);
            $('#dg').datagrid({
                /*width:810,*/
                height:500,
                title:"门店本期人工打分基表",
                nowrap: false,
                striped: true,
                //fit: true,//自动大小
                rowNumbers:true,
                pagination:true,
                //showFooter: true,
                singleSelect:true,
                pageSize: 20,
                pageList:[10,20,50],
                //columns:titles,
            });

            //$('#dg').datagrid('loadData',data);
        </script>
    </div>

</div>
<%@ include file="footer.jsp"%>


