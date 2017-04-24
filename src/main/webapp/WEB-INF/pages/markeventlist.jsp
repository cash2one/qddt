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
                    <th data-options="field:'id5',width:100">状态</th>
                    <th data-options="field:'id6',width:100">时间</th>
                    <th  data-options="field:'id7',width:200">操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="event" items="${events}">
                    <tr>
                        <td>${event.eventId}</td>
                        <td>${event.billingCycle}</td>
                        <td>${event.recordNumbers}</td>
                        <td><c:if test="${event.state=='INI'}">初始化</c:if>
                            <c:if test="${event.state=='OPN'}">已上报</c:if>
                            <c:if test="${event.state=='REP'}">已通知</c:if>
                            <c:if test="${event.state=='CLS'}">已关闭(人工)</c:if>
                            <c:if test="${event.state=='END'}">已结束</c:if></td>
                        <td><fmt:formatDate value="${event.stateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>
                            <a class="btn btn-info btn-xs" title="查看上报清单" onclick="doView(${event.eventId})" >查看</a>
                            <c:if test="${event.state=='OPN'}">
                                <a class="btn btn-info btn-xs" title="开始自动打分计算绩效" onclick="doComputeMark(${event.eventId})" >自动打分</a>
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
    function doView(eventid) {
        self.location = "/mark/viewevent.html?eventId="+eventid;
    }
    function doComputeMark(eventid) {
        $.ajax({
            type:"POST",
            url:"/mark/doComputeMark.html",
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
            url:"/mark/docloseevent.html",
            data:{'eventId':eventid  },
            success:function(data){
                $.messager.alert("信息",'温馨提示: '+data,'info');

            },
            error : function() {
                $.messager.alert("信息",'温馨提示：操作失败','error');

            }
        })

    }
</script>
<%@ include file="footer.jsp"%>


