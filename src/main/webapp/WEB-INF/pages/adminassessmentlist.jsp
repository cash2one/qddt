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
        <h3>全区考核清单</h3>
    </div>

    <div class="panel panel-default">
        <!-- Default panel contents -->

        <!-- Table -->
        <table  id="dg" class="easyui-datagrid" title="考核区域列表" style="width:100%;height:400px"
               data-options="singleSelect:true,collapsible:true  ">
            <thead>
                <tr>
                    <th data-options="field:'assessmentId',width:100">考核编号</th>
                    <th data-options="field:'billingCycle',width:100">考核年月</th>
                    <th data-options="field:'areaname',width:100,sortable:true">区域</th>
                    <th data-options="field:'districtName',width:100,sortable:true">分局</th>
                    <th data-options="field:'zoneName',width:100,sortable:true">片区</th>
                    <th data-options="field:'criterion',width:100,sortable:true,sorter:numberSort">考核标准</th>
                    <th data-options="field:'score',width:100,sortable:true,sorter:numberSort">考核得分</th>
                    <th data-options="field:'lastReward',width:100,sortable:true,sorter:numberSort">上期预发</th>
                    <th data-options="field:'lastForwardReward',width:140,sortable:true,sorter:numberSort">上期实际应发奖励</th>
                    <th data-options="field:'lastSettlement',width:100,sortable:true,sorter:numberSort">上期结算</th>
                    <th data-options="field:'forwardReward',width:100,sortable:true,sorter:numberSort">本期预发</th>
                    <th data-options="field:'reward',width:100,sortable:true,sorter:numberSort">本期应发</th>
                    <th data-options="field:'doubleReward',width:100,sortable:true,sorter:numberSort">双倍激励金额</th>
                    <th data-options="field:'state',width:100,sortable:true">状态</th>
                    <th data-options="field:'stateDate',width:150,sortable:true">时间</th>
                    <th data-options="field:'operation',width:150">操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="assessment" items="${assessments}">
                    <tr>
                        <td>${assessment.assessmentId}</td>
                        <td>${assessment.billingCycle}</td>
                        <td>${assessment.areaName}</td>
                        <td>${assessment.districtName}</td>
                        <td>${assessment.zoneName}</td>
                        <td>${assessment.criterion}</td>
                        <td>${assessment.score}</td>
                        <td>${assessment.lastReward}</td>
                        <td>${assessment.lastForwardReward}</td>
                        <td>${assessment.lastSettlement}</td>
                        <td>${assessment.forwardReward}</td>
                        <td>${assessment.reward}</td>
                        <td>${assessment.doubleReward}</td>
                        <td><c:if test="${assessment.state=='INI'}">初始化</c:if>
                            <c:if test="${assessment.state=='OPN'}">已上报</c:if>
                            <c:if test="${assessment.state=='REP'}">已通知</c:if>
                            <c:if test="${assessment.state=='FED'}">已反馈</c:if>
                            <c:if test="${assessment.state=='AUD'}">已审核</c:if>
                            <c:if test="${assessment.state=='CLS'}">已关闭(人工)</c:if>
                            <c:if test="${assessment.state=='END'}">已结束</c:if></td>
                        <%--<td>${assessment.createDate}</td>--%>
                        <td><fmt:formatDate value="${assessment.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>

                            <a class="btn btn-info btn-xs" title="查看分配详情" onclick="doViewDetail(${assessment.assessmentId})" >分配详情</a>
                            <a class="btn btn-info btn-xs" title="审核日志" onclick="doViewAudit(${assessment.assessmentId})" >审核日志</a>
                            <a class="btn btn-info btn-xs" title="签收表" onclick="doViewSignature(${assessment.assessmentId})" >签收</a>

                                <%--<a class="btn btn-info btn-xs" title="上传签收表" onclick="doViewSignature(${assessment.assessmentId})" >查看签收</a>--%>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div><!-- 主要内容区 -->


</div>
<script type="text/javascript" src="/js/datagrid-filter.js" ></script>
<script type="text/javascript" src="/js/datagrid-helper.js" ></script>
<script type="text/javascript" >
    var gassessmentid = 0;

    $(function () {
        var dg=$('#dg').datagrid({
            fit: false, //datagrid自适应宽度
            fitColumn: false, //列自适应宽度
            striped: true, //行背景交换
            nowap: false //列内容多时自动折至第二行
            , rownumbers: true
            , filterBtnIconCls: 'icon-filter'
                    ,remoteSort:false
                    ,multiSort:true
            , onLoadSuccess: function(){$.resizeGrid();}
            ,toolbar:[
                { text: '保存', iconCls: 'icon-save'
                    ,handler: function() {
                        $.saveGridToExcel("/assessment/downloadexcel.html"
                            , $('#dg').data('datagrid'), '片区对投资金清单.xlsx', 'POST');
                    }
                }
                ,'-',
                {text: '过滤', iconCls: 'icon-filter',
                    handler: function() {
                        $.toggleFilterPanel();
                    }
                }
            ]
        });
        dg.datagrid().datagrid('enableFilter'
                , [$.makeNumberFilter('reward',2)
                    ,$.makeSelectFilter(dg,'billingCycle',[{text:"201703",value:'201703'},{text:"201704",value:'201704'}])
                ]
        ).datagrid('resize');
    });

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

</script>
<%@ include file="footer.jsp"%>


