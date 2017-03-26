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
    <div>
        <table  id="dg" class="easyui-datagrid col-sm-8"  style="height:400px;width:500px;">
            <thead>
            <tr>
                <th></th>
                <th data-options="field:'id',width:100">考核ID</th>
                <th data-options="field:'categoryId',width:100">考核项分组ID</th>
                <th data-options="field:'name',width:100">考核名称</th>
                <th data-options="field:'roleCode',width:100">ROLE_CODE</th>
                <th data-options="field:'percent',width:100">考核百分比</th>
                <th data-options="field:'score',width:100">考核总分</th>
                <th data-options="field:'detail',width:100">考核详情</th>
                <th data-options="field:'myScore',width:100">考核得分</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${items}">
                <tr>
                    <td>${item.itemId}</td>
                    <td>${item.itemCategoryId}</td>
                    <td>${item.itemName}</td>
                    <td>${item.roleCode}</td>
                    <td>${item.assessmentPercent}</td>
                    <td>${item.criterionScore}</td>
                    <td>${item.criterionDetail}</td>
                    <td>0</td>
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
        var editRow = undefined; //定义全局变量：当前编辑的行
        datagrid = $("#dg").datagrid({
            //url: '/item/finditems.html', //请求的数据源
            iconCls: 'icon-edit', //图标
            width: '100%',
            pagination: false, //显示分页
            pageSize: 15, //页大小
            pageList: [15, 30, 45, 60], //页大小下拉选项此项各value是pageSize的倍数
            fit: true, //datagrid自适应宽度
            fitColumn: true, //列自适应宽度
            striped: true, //行背景交换
            nowap: true, //列内容多时自动折至第二行
            emptyMsg: '无记录',
            idField: 'id', //主键
            singleSelect:true,
            remoteSort:false,
            multiSort:true,
            showFooter:true,
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
                        return value+"%";
                    }
                },
                { field: 'score', title: '考核总分', width: 100, sortable: true,

                },
                { field: 'detail', title: '考核详情', width: 100, sortable: true,
                },

                { field: 'myScore', title: '考核得分', width: 100,align:'center', sortable: true,
                    editor: { type: 'numberbox', options: {precision:0,  required: true} }
                },

            ]],
            //queryParams: { assessmentId: 0 }, //查询参数
            toolbar: [
                { text: '保存', iconCls: 'icon-save', handler: function () {
                    //保存时结束当前编辑的行，自动触发onAfterEdit事件如果要与后台交互可将数据通过Ajax提交后台
                    datagrid.datagrid("endEdit", editRow);
                    var rows = datagrid.datagrid("getData");
                    //var    arr=$('#dg').datagrid('getChanges');
                    console.info("rows.rows = ",rows.rows);
                    var errorMessage;
                    var infoMessage;
                    $.each(rows.rows,function (i,n) {
                        if (Number(n.myScore) > Number(n.score)){
                            errorMessage = '{'+n.name+'}'+"填报的数值不能超过评分标准!";
                            return;
                        }
                        if (Number(n.myScore) == 0){
                            infoMessage = '{'+n.name+'}'+"填报的数值为零分!";
                        }
                        var reg=/(\d+)/g;
                        //rows.rows[i].percent.replace(reg,'$1');
                        //console.info(n.percent.match(reg));
                    });
                    if (errorMessage){
                        $.messager.alert("错误",errorMessage,'error');
                        return;
                    }
                    /*if (infoMessage){
                        $.messager.alert("信息",infoMessage,"info");
                    }*/

                    console.log(rows.rows);
                    $.ajax({
                        type:"POST",
                        url:"/item/dosavestaffitems/${staffAssessmentId}.html",
                        data: JSON.stringify(rows.rows),
                        dataType:'text',
                        contentType: "application/json;charset=utf-8",
                        success:function(data){

                            $.messager.alert('信息','温馨提示：'+data,'info');
                            //location.reload();//重新加载页面
                            //self.location = "/assessment/mystaffassessment.html?assessmentid="+assessmentId;

                        },
                        error:function(XMLHttpRequest, textStatus, errorThrown){
                            $.messager.alert('错误','操作失败!','alert');
                        }
                    });

                }
                }, '-',
                { text: '取消编辑', iconCls: 'icon-redo', handler: function () {
                    //取消当前编辑行把当前编辑行罢undefined回滚改变的数据,取消选择的行
                    editRow = undefined;
                    datagrid.datagrid("rejectChanges");
                    datagrid.datagrid("unselectAll");
                }
                }],
            onAfterEdit: function (rowIndex, rowData, changes) {
                //endEdit该方法触发此事件
                editRow = undefined;

                var rows = datagrid.datagrid("getData");
                var totalMyScore = 0;
                $.each(rows.rows,function (i,n) {
                    totalMyScore += Number(n.myScore);

                });
                $('#dg').datagrid('reloadFooter',
                        [{"score":${totalScore},"percent":"${totalPercent}","myScore":totalMyScore,"id":"Total:"}]);

                /*$.each(staffs,function (i,n) {
                 if (rowData.cssNumber == n.assistNum) {
                 rowData.username = n.staffName
                 //$("#dg").datagrid("getData")[rowIndex].username = n.staffName;
                 }
                 });*/

                //console.info(rowData);
            },
            onDblClickRow: function (rowIndex, rowData) {
                //双击开启编辑行
                if (editRow != undefined) {
                    datagrid.datagrid("endEdit", editRow);
                }
                if (editRow == undefined) {
                    datagrid.datagrid("beginEdit", rowIndex);
                    editRow = rowIndex;
                }
            },
            onClickRow: function (rowIndex, rowData) {
                //双击开启编辑行
                if (editRow != undefined) {
                    datagrid.datagrid("endEdit", editRow);
                }
                if (editRow == undefined) {
                    datagrid.datagrid("beginEdit", rowIndex);
                    editRow = rowIndex;
                }
            },

        });
        /*datagrid.datagrid('load', {
            name: 'easyui',
            address: 'ho'
        });*/
        datagrid.datagrid('reloadFooter', [{"score":${totalScore},"percent":"${totalPercent}","myScore":0.00,"id":"Total:"}]);
    });

</script>


<%@ include file="footer.jsp"%>
