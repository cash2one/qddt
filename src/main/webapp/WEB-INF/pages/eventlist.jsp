<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ include file="header.jsp"%>
<div class="container" >


    <div class="panel panel-default">
        <!-- Default panel contents -->

        <!-- Table -->
        <table id="dg" class="easyui-datagrid" title="考核上报列表" >
            <thead>
                <tr>
                    <th data-options="field:'id1',width:100">ID</th>
                    <th data-options="field:'id2',width:100">考核年月</th>
                    <th data-options="field:'id3',width:100">记录数</th>
                    <th data-options="field:'id4',width:100">总金额</th>
                    <th data-options="field:'id5',width:100">状态</th>
                    <th data-options="field:'id6',width:100">时间</th>
                    <th  data-options="field:'id7',width:200">操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="event" items="${events}">
                    <tr>
                        <td>${event.eventId}</td>
                        <td>${event.billingCycleId}</td>
                        <td>${event.recordRow}</td>
                        <td>${event.amount}</td>
                        <td><c:if test="${event.state=='INI'}">初始化</c:if>
                            <c:if test="${event.state=='OPN'}">已上报</c:if>
                            <c:if test="${event.state=='REP'}">已通知</c:if>
                            <c:if test="${event.state=='CLS'}">已关闭(人工)</c:if>
                            <c:if test="${event.state=='END'}">已结束</c:if></td>
                        <td><fmt:formatDate value="${event.stateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>
                            <c:if test="${event.state=='OPN'}">
                                <a class="btn btn-info btn-xs" title="下发并通知CEO分配绩效" onclick="doSubscriber(${event.eventId})" >通知CEO</a>
                                <a class="btn btn-danger btn-xs" title="关闭本次上报" onclick="doCloseEvent(${event.eventId})" >关闭上报</a>
                            </c:if>
                            <c:if test="${event.state=='INI'}">
                                <a class="btn btn-danger btn-xs" title="关闭本次上报" onclick="doCloseEvent(${event.eventId})" >关闭上报</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

</div>
<%--<script type="text/javascript" src="/js/datagrid-filter.js" />--%>
<script type="text/javascript" >
    $(function() {
        var dg = $('#dg');
        dg.datagrid();	// create datagrid
        //dg.datagrid('enableFilter');
    });
    function doSubscriber(eventid) {
        $.ajax({
            type:"POST",
            url:"/assessment/dosubscribe.html",
            data:{'eventid':eventid  },
            success:function(data){
                /*layer.alert('信息!',{
                 title: '温馨提示：'+data,
                 icon:0,

                 });*/
                $.messager.alert("信息",'温馨提示: '+data,'info');
                //alert('温馨提示: '+data);

            },
            error : function() {
                /*layer.alert('信息!',{
                 title: '温馨提示：操作失败',
                 icon:0,

                 });*/
                //alert('温馨提示：操作失败');
                $.messager.alert("信息",'温馨提示：操作失败','error');

            }
        })

    }

    function doCloseEvent(eventid) {
        $.ajax({
            type:"POST",
            url:"/assessment/docloseevent.html",
            data:{'eventid':eventid  },
            success:function(data){
                /*layer.alert('信息!',{
                 title: '温馨提示：'+data,
                 icon:0,

                 });*/
                alert('温馨提示: '+data);

            },
            error : function() {
                /*layer.alert('信息!',{
                 title: '温馨提示：操作失败',
                 icon:0,

                 });*/
                alert('温馨提示：操作失败');

            }
        })

    }
</script>
<%@ include file="footer.jsp"%>


