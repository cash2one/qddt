
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ page isELIgnored="false"%>
<%@ include file="header.jsp"%>
<div class="container" >

    <div class="panel-heading" >
        <h4>分配表详情</h4>
    </div>

    <div class="panel panel-default  ">
        <!-- Default panel contents -->

        <!-- Table -->
        <table id="dg" class="easyui-datagrid"  title="分配表详情"  style="width:100%;height:400px"
               data-options="singleSelect:true,collapsible:true  ">
            <thead>
                <tr>
                    <th data-options="field:'id',hidden:true,width:100">ID</th>
                    <th data-options="field:'assid',width:100">考核编号</th>
                    <th data-options="field:'billingCycle',width:100">考核年月</th>
                    <th data-options="field:'zoneName',width:100">片区</th>
                    <th data-options="field:'username',width:100">姓名</th>
                    <th data-options="field:'cssNumber',width:150">CSS工号</th>
                    <th data-options="field:'roleName',width:100">职位</th>
                    <th data-options="field:'assessmentAmount',width:80">片区考核金额</th>
                    <th data-options="field:'amount',width:100">考核金额</th>
                    <th data-options="field:'state',width:100">状态</th>
                    <th data-options="field:'state_date',width:100">时间</th>
                    <th data-options="field:'operation',width:100">操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="assessment" items="${staffAssessments}">
                    <tr>
                        <td>${assessment.id}</td>
                        <td>${assessment.assessmentId}</td>
                        <td >${assessment.billingCycle}</td>
                        <td  >${zoneNames[assessment.assessmentId]}</td>
                        <td  >${assessment.staffName}</td>
                        <td  >${assessment.cssStaffId}</td>
                        <td  >${assessment.roleName}</td>
                        <td  >${assessment.assessmentAmount}</td>
                        <td  >${assessment.personalAmount}</td>
                        <td  ><c:if test="${assessment.state=='INI'}">初始化</c:if>
                            <c:if test="${assessment.state=='OPN'}">已上报</c:if>
                            <c:if test="${assessment.state=='REP'}">已通知</c:if>
                            <c:if test="${assessment.state=='FED'}">已反馈</c:if>
                            <c:if test="${assessment.state=='AUD'}">已审核</c:if>
                            <c:if test="${assessment.state=='CLS'}">已关闭(人工)</c:if>
                            <c:if test="${assessment.state=='END'}">已结束</c:if>
                        </td>
                        <td  >${assessment.stateDate}</td>
                        <td  >
                                <a class="btn btn-info btn-xs" title="考核打分" onclick="doFeedback(${assessment.id})" >打分</a>
                                <a class="btn btn-info btn-xs" title="查看考核得分" onclick="doViewDetail(${assessment.id})" >详情</a>

                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </div>

</div>
<script type="text/javascript" >

    $( document).ready(function(){
        var dg = $('#dg').datagrid({
            iconCls: 'icon-save', //图标
            pagination: false, //显示分页
            fit: true, //datagrid自适应宽度
            fitColumn: false, //列自适应宽度
            striped: true, //行背景交换
            nowap: true, //列内容多时自动折至第二行
            emptyMsg: '无记录',
            border: true,
            onDblClickRow: function (rowIndex, rowData) {
                //双击开启编辑行
                doViewDetail(rowData.id);
            },
        });// create datagrid
        //dg.datagrid('enableFilter');
    });
    function doFeedback(staffAssessmentId) {
        //跳转到新窗口
        self.location = "/item/modifystaffitem.html?id="+staffAssessmentId;
    }

    function doViewDetail(staffAssessmentId) {
        self.location = "/item/mystaffitem.html?id="+staffAssessmentId;

    }
</script>
<%@ include file="footer.jsp"%>


