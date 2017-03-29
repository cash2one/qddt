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
        <h4>分配表详情</h4>
    </div>

    <div class="panel panel-default  ">
        <!-- Default panel contents -->

        <!-- Table -->
        <table title="分配表详情" id="dg" class="easyui-datagrid"  style="width:100%;height:400px"
               data-options="singleSelect:true,collapsible:true  ">
            <thead>
                <tr>
                    <th data-options="field:'cssNumber',width:150">CSS工号</th>
                    <th data-options="field:'areaName',width:150">区域</th>
                    <th data-options="field:'districtName',width:150">分局</th>
                    <th data-options="field:'zoneName',width:150">片区</th>
                    <th data-options="field:'username',width:150">姓名</th>
                    <th data-options="field:'operation',width:150">操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="staff" items="${staffs}">
                    <tr>
                        <td>${staff.assistNum}</td>
                        <td >${staff.areaName}</td>
                        <td  >${staff.areaNameL2}</td>
                        <td  >${staff.areaNameTm}</td>
                        <td  >${staff.staffName}</td>
                        <td  >
                            <a class="btn btn-info btn-xs" title="查看工号详情" onclick="doViewDetail(${staff.assistNum} )" >详情</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </div>

</div>
<%--<script type="text/javascript" src="/js/datagrid-filter.js" ></script>--%>
<script type="text/javascript" >

    $( document).ready(function(){
        var dg = $('#dg');
        /*dg.datagrid({
            iconCls: 'icon-save', //图标
            pagination: true, //显示分页
            pageSize: 15, //页大小
            pageList: [15, 30, 45, 60], //页大小下拉选项此项各value是pageSize的倍数
            fit: true, //datagrid自适应宽度
            fitColumn: true, //列自适应宽度
            striped: true, //行背景交换
            nowap: true, //列内容多时自动折至第二行
            emptyMsg: '无记录',
            border: false,
        });*/	// create datagrid
        //dg.datagrid('enableFilter');
    });

    function doViewDetail(cssNumber) {
        $.messager.alert('温馨提示','查看员工详情','info');
        /*$.ajax({
            type:"POST",
            url:"/assessment/docloseevent.html",
            data:{'eventid':eventid  },
            success:function(data){
                /!*layer.alert('信息!',{
                 title: '温馨提示：'+data,
                 icon:0,

                 });*!/
                alert('温馨提示: '+data);

            },
            error : function() {
                /!*layer.alert('信息!',{
                 title: '温馨提示：操作失败',
                 icon:0,

                 });*!/
                alert('温馨提示：操作失败');

            }
        })*/
        //查看记录
    }
</script>
<%@ include file="footer.jsp"%>


