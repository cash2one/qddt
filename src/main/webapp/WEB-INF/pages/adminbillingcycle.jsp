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
    <div class="panel-heading">
        <h3>账期管理</h3>
    </div>

    <div class="panel panel-default">
        <!-- Default panel contents -->
        <button id="btnAdd" class="btn btn-primary" type="button" >创建账期</button>
        <button id="btnUpload" class="btn btn-primary" type="button" >上传基础表</button>
        <button id="btnOpen" class="btn btn-primary" type="button" >生成考核</button>
        <button id="btnCommit" class="btn btn-primary" type="button" >确认账期</button>
        <button id="btnSubscriber" class="btn btn-primary" type="button" >通知CEO</button>
        <button id="btnENd" class="btn btn-primary" type="button" >结束</button>
        <div id="newWindow" class="easyui-window" title="账期管理" data-options="iconCls:'icon-save'" style="width:500px;height:200px;padding:5px;display:none;">
            <div class="easyui-layout" data-options="fit:true">
                <div data-options="region:'center'" style="padding:10px;">
                    <p style="padding:5px;">创建新账期</p>
                    <input id="billingCycle" class="easyui-numberbox" value="0" data-options="precision:0,groupSeparator:''" />
                </div>
                <div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
                    <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="javascript:doadd()" style="width:80px">确定</a>
                    <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:cancel()" style="width:80px">取消</a>
                </div>
            </div>
        </div>
        <!-- Table -->
        <table  id="dg" class="easyui-datagrid" title="账期管理" style="width:100%;height:400px"
               data-options="singleSelect:true,collapsible:true  ">
            <thead>
                <tr>
                    <th data-options="field:'billingCycle',width:100">考核年月</th>
                    <th data-options="field:'state',width:100,sortable:true">状态</th>
                    <th field="flow" formatter='formatterAssessmentState' width="350">流程</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="cycle" items="${cycles}">
                    <tr>
                        <td>${cycle.billingCycleId}</td>
                        <td>
                            <c:if test="${cycle.state=='INITING'}">创建中</c:if>
                            <c:if test="${cycle.state=='INI'}">初始化</c:if>
                            <c:if test="${cycle.state=='OPENING'}">上报中</c:if>
                            <c:if test="${cycle.state=='OPN'}">已上报</c:if>
                            <c:if test="${cycle.state=='REP'}">已通知</c:if>
                            <c:if test="${cycle.state=='END'}">已结束</c:if>
                        </td>
                        <td>${cycle.state}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div><!-- 主要内容区 -->


</div>
<script type="text/javascript" src="/js/datagrid-filter.js" ></script>
<script type="text/javascript" src="/js/datagrid-helper.js" ></script>
<script type="text/javascript" src="/js/cycle_functions.js" ></script>
<script type="text/javascript" >
    var gassessmentid = 0;
    var dg ;
    $(function () {
        cancel();
        dg=$('#dg').datagrid({
            fit: false, //datagrid自适应宽度
            fitColumn: false, //列自适应宽度
            striped: true, //行背景交换
            nowap: false //列内容多时自动折至第二行
            , rownumbers: true
            , filterBtnIconCls: 'icon-filter'
                    ,remoteSort:false
                    ,multiSort:true

        });

        $('#btnAdd').click(function () {
            $('#newWindow').window('open');
        });
        $('#btnUpload').click(function () {
            doUpload();
        });
        $('#btnOpen').click(function () {
            doOpen();
        });
        $('#btnCommit').click(function () {
            doCommit();
        });
        $('#btnSubscriber').click(function () {
            doNotify();
        });
        $('#btnEnd').click(function () {
            doEnd();
        });
    });


    function doadd() {
        $.ajax({
            type:"POST",
            url:"/assessment/doaddcycle.html",
            data:{'billingCycle':$('#billingCycle').val()},
            success:function (msg) {
                $.messager.alert('信息提示','温馨提示:'+msg,'info');
            },
            error:function () {
                $.messager.alert('信息提示','温馨提示:处理失败!','error');
            }
        });
        cancel();
    }
    function doPostCycle(url) {
        //console.log(dg.datagrid('getSelected'));
        row = dg.datagrid('getSelected');
        if (row) {
            $.ajax({
                type:"POST",
                url:url,
                data:{'billingCycle':row.billingCycle},
                success:function (msg) {
                    $.messager.alert('信息提示','温馨提示:'+msg,'info');
                },
                error:function () {
                    $.messager.alert('信息提示','温馨提示:处理失败!','error');
                }
            });
        }
        else {
            $.messager.alert('信息提示','温馨提示:请首先选择需要操作的账期','error');
        }
    }
    function doUpload() {
        self.location = "/mark/uploadmarkfile.html";
    }
    function doOpen() {
        doPostCycle("/mark/doopencycle.html");
    }
    function doCommit() {
        doPostCycle("/mark/docommitcycle.html");
    }
    function doNotify() {
        doPostCycle("/mark/donotifycycle.html");
    }
    function doEnd() {
        doPostCycle("/mark/doendcycle.html");
    }

    
    function cancel() {
        $('#newWindow').window('close');
    }

    function formatterAssessmentState(val){
        //alert(val+row.state);
        return formatterBar(val);
    }

</script>
<%@ include file="footer.jsp"%>


