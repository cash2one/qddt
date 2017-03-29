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
        <table  id="dg" class="easyui-datagrid" title="我的考核片区列表" style="width:100%;height:400px"
               data-options="singleSelect:true,collapsible:true  ">
            <thead>
                <tr>
                    <th data-options="field:'billingCycle',width:80">考核年月</th>
                    <th data-options="field:'areaName',width:90">区域</th>
                    <th data-options="field:'score',width:90,resizable:true,sortable:true">考核得分</th>
                    <th data-options="field:'lastReward',width:90,resizable:true,sortable:true">上期预发</th>
                    <th data-options="field:'lastForwardReward',width:120,resizable:true,sortable:true">上期实际应发奖励</th>
                    <th data-options="field:'lastSettlement',width:90,resizable:true,sortable:true">上期结算</th>
                    <th data-options="field:'forwardReward',width:90,resizable:true,sortable:true">本期预发</th>
                    <th data-options="field:'reward',width:90,resizable:true,sortable:true">本期应发</th>
                    <th data-options="field:'doubleReward',width:120,resizable:true,sortable:true">双倍激励金额</th>
                    <th data-options="field:'state',width:80">状态</th>
                    <th data-options="field:'stateDate',width:180">时间</th>
                    <%--<th data-options="field:'operation',width:150">操作</th>--%>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="assessment" items="${assessments}">
                    <tr>
                        <td>${assessment.billingCycle}</td>
                        <td>${assessment.areaName}</td>
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
                        <%--<td>--%>

                            <%--<a class="btn btn-info btn-xs" title="查看分配详情" onclick="doViewDetail(${assessment.assessmentId})" >分配详情</a>--%>
                            <%--<a class="btn btn-info btn-xs" title="审核日志" onclick="doViewAudit(${assessment.assessmentId})" >审核日志</a>--%>
                            <%--<a class="btn btn-info btn-xs" title="签收表" onclick="doViewSignature(${assessment.assessmentId})" >签收</a>--%>

                                <%--&lt;%&ndash;<a class="btn btn-info btn-xs" title="上传签收表" onclick="doViewSignature(${assessment.assessmentId})" >查看签收</a>&ndash;%&gt;--%>
                        <%--</td>--%>
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
    var gareaNames = [];
    var gcycles = [];
    $.each("${areaNames}".split(','),function (i,n) {
        gareaNames.push({text:n,value:n});
    });
    $.each("${cycles}".split(','),function (i,n) {
        gcycles.push({text:n,value:n});
    });
    $(function () {
        var dg=$('#dg').datagrid({
            fit: false, //datagrid自适应宽度
            fitColumn: false, //列自适应宽度
            striped: true, //行背景交换
            nowrap: false //列内容多时自动折至第二行
            ,rownumbers:true
            ,filterBtnIconCls:'icon-filter'
                    ,remoteSort:false,
                    multiSort:true

            ,onLoadSuccess: function () {
                $.resizeGrid();
            }
            ,toolbar:[
                    { text: '保存', iconCls: 'icon-save'
                        ,handler: function () {
                            var state = $('#dg').data('datagrid');
                            var stringRows=JSON.stringify(state.data.rows);
                            var columns = state.options.columns[0];
                            var titles = new Array();
                            $.each(columns,function (i,n) {
                                titles.push({'title':n.title,'field':n.field});
                            });
                            var data = {'data':stringRows,'titles':JSON.stringify(titles),'filename':'区域对投资金清单.xlsx'};
                            downloadExcel("/assessment/downloadexcel.html",data,'POST');
                        }
                    },'-',
                    {text: '过滤', iconCls: 'icon-filter',
                        handler: function () {
                            $.toggleFilterPanel();
                        }
                    }
                    ]
        });
        //dg.datagrid('enableFilter');
        $('#dg').datagrid().datagrid('enableFilter',[
            $.makeNumberFilter('score',2)
            ,$.makeSelectFilter(dg,'billingCycle',gcycles)
            ,$.makeSelectFilter(dg,'areaName',gareaNames)
            ,$.makeNumberFilter('lastReward',2)
            ,$.makeNumberFilter('lastForwardReward',2)
            ,$.makeNumberFilter('lastSettlement',2)
            ,$.makeNumberFilter('forwardReward',2)
            ,$.makeNumberFilter('reward',2)
            ,$.makeNumberFilter('doubleReward',2)
        ]
        ).datagrid('resize');
    })



    function downloadExcel(url, data, method){    // 获得url和data
        if( url && data ){
            // data 是 string 或者 array/object
            //data = typeof data == 'string' ? data : $.param(data);        // 把参数组装成 form的  input
            var inputs = "";
            $.each(data, function(n,d){
                //var pair = this.split('=');
                inputs+="<input type='hidden' name='"+n+"' value='"+ d +"' />";
            });
            // request发送请求
            //console.log(inputs);
            $("<form action='"+ url +"' method='"+ (method||'POST') +"' >"+inputs+"</form>")
                    .appendTo('body').submit().remove();
        };
    };

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


