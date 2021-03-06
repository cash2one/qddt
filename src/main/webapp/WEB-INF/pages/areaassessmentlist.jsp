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
        <h3>区域片区清单</h3>
    </div>

    <div class="panel panel-default">
        <!-- Default panel contents -->

        <form action="/assessment/areaassessmentlist.html" type="GET">
            <div class="input-group">
                <%--<input type="text" class="form-control">--%>
                <select name="billingcycle" id="billingcycle" class="col-sm-6">
                    <c:forEach var="cycle" items="${cycles}">
                        <option value="${cycle.billingCycleId}">${cycle.billingCycleId}</option>
                    </c:forEach>
                </select>
                <span class="input-group-btn ">
              <button type="submit" class="btn btn-primary smaller ">查询</button>
            </span>
            </div>
        </form>
        <div><h6></h6></div>
        <!-- Table -->
        <table  id="dg" class="easyui-datagrid" title="区域片区清单" style="width:100%;height:400px"
               data-options="singleSelect:true,collapsible:true  ">
            <thead>
                <tr>
                    <th data-options="field:'assessmentId',width:100">考核编号</th>
                    <th data-options="field:'billingCycle',width:100">考核年月</th>
                    <th data-options="field:'districtName',width:100,sortable:true">分局</th>
                    <th data-options="field:'zoneName',width:100,sortable:true">片区</th>
                    <th data-options="field:'doubleReward',width:100,sortable:true,sorter:numberSort">总金额</th>
                    <th data-options="field:'state',width:100,sortable:true">状态</th>
                    <th data-options="field:'stateDate',width:150">时间</th>
                    <th data-options="field:'operation',width:150">操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="assessment" items="${assessments}">
                    <tr>
                        <td>${assessment.assessmentId}</td>
                        <td>${assessment.billingCycle}</td>
                        <td>${assessment.districtName}</td>
                        <td>${assessment.zoneName}</td>
                        <td>${assessment.doubleReward}</td>
                        <td><c:if test="${assessment.state=='INI'}">初始化</c:if>
                            <c:if test="${assessment.state=='OPN'}">已上报</c:if>
                            <c:if test="${assessment.state=='REP'}">已通知</c:if>
                            <c:if test="${assessment.state=='FED'}">已反馈</c:if>
                            <c:if test="${assessment.state=='AUD'}">已审核</c:if>
                            <c:if test="${assessment.state=='CLS'}">已关闭(人工)</c:if>
                            <c:if test="${assessment.state=='END'}">已结束</c:if></td>
                        <td><fmt:formatDate value="${assessment.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>

                            <c:if test="${assessment.state!='REP'}">
                                <a class="btn btn-info btn-xs" title="查看分配详情" onclick="doViewDetail(${assessment.assessmentId})" >详情</a>
                                <%--<a class="btn btn-info btn-xs" title="打分表" onclick="doViewStaffItem(${assessment.assessmentId})" >打分表</a>--%>
                            </c:if>
                            <c:if test="${assessment.state=='FED'}" >
                                <a class="btn btn-info btn-xs" title="审阅" onclick="doAudit(${assessment.assessmentId})" >审阅</a>
                            </c:if>
                            <c:if test="${assessment.state=='END'}" >
                                <a class="btn btn-info btn-xs" title="审阅日志" onclick="doViewAudit(${assessment.assessmentId})" >审核日志</a>
                            </c:if>
                            <c:if test="${assessment.state=='AUD'}" >
                                <a class="btn btn-info btn-xs" title="审核日志" onclick="doViewAudit(${assessment.assessmentId})" >审核日志</a>
                                <a class="btn btn-info btn-xs" title="签收表" onclick="doViewSignature(${assessment.assessmentId})" >签收</a>
                            </c:if>
                                <%--<a class="btn btn-info btn-xs" title="上传签收表" onclick="doViewSignature(${assessment.assessmentId})" >查看签收</a>--%>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div><!-- 主要内容区 -->
    <div id='dd' class="easyui-window " style="width:500px;height:350px;" >
        <div class="panel-heading" ></div>
        <div class="form-group" >
            <div class="row">

                <label for="suggestion" class="control-label col-sm-3 ">请选择意见</label>
                <div class="controls">
                    <select class=" col-sm-6"  width="*"  id="suggestion" name="suggestion" required >
                        <option value="Agree" selected>已阅</option>
                        <option value="Disagree">退回</option>
                    </select>
                </div>
            </div>
        </div>

        <div class="form-group" >
            <div class="row">
                <label class="control-label col-sm-3">请输入意见备注 </label>
                <input class="easyui-textbox col-sm-3" width="*"  data-options="multiline:true" id="remark" name="remark" placeholder="请输入备注信息" />
            </div>
        </div>
    </div>  <!-- 对话框 -->

</div>
<script type="text/javascript" src="/js/datagrid-filter.js"></script>
<script type="text/javascript" src="/js/datagrid-helper.js"></script>
<script type="text/javascript" >
    var gassessmentid = 0;
    $(function(){
        $('#dd').dialog({
            title: '审核意见',
            width: 500,
            height: 300,
            closed: true,
            cache: false,
            modal: true,
            buttons:[{
                text:'确定',
                iconCls:'icon-ok',
                handler:function(){
                    doDialogOK();
                }
            },{
                text:'关闭',
                handler:function(){
                    $('#dd').dialog('close');
                }
            }]
        });

        //设置table的footer
        var dg=$('#dg').datagrid({
            fit: false, //datagrid自适应宽度
            fitColumn: true, //列自适应宽度
            striped: true, //行背景交换
            nowrap: false //列内容多时自动折至第二行
            , rownumbers: true
            , filterBtnIconCls: 'icon-filter'
            ,remoteSort:false
            ,multiSort:true
            ,showFooter:true
        });
        dg.datagrid('enableFilter');
        dg.datagrid('reloadFooter',
                [{"doubleReward": '<fmt:formatNumber value="${totalReward}" pattern="0.00"/> ',"assessmentId":"合计:"}]);

    });

    function doViewStaffItem(assessmentId) {

        self.location = "/item/mystaffitem.html?assessmentid="+assessmentId;
    }
    function doFeedback(assessmentId) {
        //跳转到新窗口
        self.location = "/assessment/feedbackstaffassessment.html?assessmentid="+assessmentId;
    }
    function doViewAudit(assessmentId) {
        self.location = "/assessment/myauditlog.html?assessmentid="+assessmentId;
    }

    function doViewDetail(assessmentId) {
        self.location = "/assessment/mystaffassessment.html?assessmentid="+assessmentId;
        /**/
        //查看记录
    }

    function doViewSignature(assessmentId) {
        self.location = "/assessment/myassessmentsignature.html?assessmentid="+assessmentId;

    }
    function doAudit(assessmentId) {
        gassessmentid = assessmentId;

        $('#dd').dialog('open');
    }

    function doDialogOK() {

        $('#dd').dialog('close');//先关闭对话框
        //ajax 调用
        $.ajax({
            type:"POST",
            url:"/assessment/doaudit",
            data:{'assessmentid':gassessmentid,'suggestion':$('#suggestion').val(),'remark':$('#remark').val()},
            success:function(data){
                $.messager.alert('信息','温馨提示: '+data,'info');
                //self.location.reload();
            },
            error : function() {

                $.messager.alert('信息','操作失败!','error');

            }
        });
    }
    function doDialogCancel() {


    }
</script>
<%@ include file="footer.jsp"%>


