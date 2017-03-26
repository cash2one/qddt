<%--
  Created by IntelliJ IDEA.
  User: zark
  Date: 17/3/21
  Time: 上午9:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ include file="header.jsp"%>



<div class="container" >

    <div class="panel-heading">
        <h4>打分详情表</h4>
    </div>
    <div class="panel panel-default  ">
        <table  id="dg" title="打分表详情" class="easyui-datagrid"  style="height:400px;width:100%;"
                data-options="singleSelect:true,collapsible:true,showFooter:true  ">
            <thead>
            <tr>
                <th></th>
                <th data-options="field:'id',width:100">考核ID</th>
                <th data-options="field:'categoryId',width:100">考核项分组</th>
                <th data-options="field:'name',width:100">考核名称</th>
                <th data-options="field:'roleCode',width:100">ROLE_CODE</th>
                <th data-options="field:'percent',width:100">考核百分比</th>
                <th data-options="field:'score',width:100">考核总分</th>
                <th data-options="field:'detail',width:100">考核详情</th>
                <th data-options="field:'state',width:100">考核得分</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${items}">
                <tr>
                    <td>${item.id}</td>
                    <td>${item.categoryId}</td>
                    <td>${item.itemName}</td>
                    <td>${item.roleCode}</td>
                    <td>${item.assessmentPercent}</td>
                    <td>${item.criterionScore}</td>
                    <td>${item.criterionDetail}</td>
                    <td>${item.score}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>


</div>
<script type="text/javascript">

    /*var mycolumns = [[
        {field:'id',title:'考核分组ID',width:100,sortable:true},
        {field:'rolecode',title:'角色分类',width:100,resizable:true},
        {field:'name',title:'考核分组名称',width:100,resizable:true}
    ]];*/
    $(function () {

        var jcategories = eval(${categories});

        var datagrid; //定义全局变量datagrid
        datagrid = $("#dg").datagrid({
            //url: '/item/finditems.html', //请求的数据源
            iconCls: 'icon-edit', //图标
            pagination: false, //显示分页
            pageSize: 15, //页大小
            pageList: [15, 30, 45, 60], //页大小下拉选项此项各value是pageSize的倍数
            fit: false, //datagrid自适应宽度
            fitColumn: false, //列自适应宽度
            striped: true, //行背景交换
            nowap: true, //列内容多时自动折至第二行
            emptyMsg: '无记录',
            idField: 'id', //主键
            singleSelect:true,
            remoteSort:false,
            showFooter:true,
            multiSort:true,
            columns: [[//显示的列
                {field: 'id', title: '编号', width: 100},
                { field: 'categoryId', title: '考核项分组', width: 100, sortable: true,
                    formatter:function(value,row){
                        var ifoundit ;
                        $.each(jcategories,function (i,n) {
                            /*console.log(row,value);
                             console.log(nn.name ,nn.categoryId,value,nn.categoryId==value);*/
                            if (value == n.categoryId)
                                ifoundit =  n.name;
                            return;
                        });
                        return (ifoundit||value);
                    }
                },
                { field: 'name', title: '考核名称', width: 100, sortable: true,
                },
                { field: 'roleCode', title: '角色分类', width: 100, sortable: true,

                },
                { field: 'percent', title: '考核项占比', width: 100, sortable: true,
                    formatter:function(value,row){
                        return value+'%';
                    }
                },
                { field: 'score', title: '考核总分', width: 100, sortable: true,

                },
                { field: 'detail', title: '考核详情', width: 100, sortable: true,
                },

                { field: 'myScore', title: '考核得分', width: 100,align:'center', sortable: true,
                },

            ]],

        });
        datagrid.datagrid('reloadFooter',
                [{"score":${totalScore},"percent":"${totalPercent}","myScore":${totalMyScore},"id":"合计:"}]);
    });

</script>


<%@ include file="footer.jsp"%>
