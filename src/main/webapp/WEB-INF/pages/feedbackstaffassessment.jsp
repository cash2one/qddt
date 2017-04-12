<%--
  Created by IntelliJ IDEA.
  User: zark
  Date: 17/3/13
  Time: 下午9:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>
<html>
<head>
    <title>Title</title>
    <script src="/js/jquery-3.1.1.min.js" type="text/javascript"></script>
    <script src="/js/jquery.easyui.min.js" type="text/javascript"></script>
    <script src="/js/bootstrap.min.js" type="text/javascript" ></script>
    <link href="/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="/css/easyui.css" rel="stylesheet" type="text/css" />
    <link href="/css/easyuiicon.css" rel="stylesheet"  type="text/css" />

</head>
<body>
    <div style="display: none" id="staffdiv">
        [
        <c:forEach var="staff" items="${staffs}" >
            {"staffName":"${staff.staffName}" ,"assistNum":"${staff.assistNum}" },
        </c:forEach>
        ]
    </div>

    <div style="display: none" id="assessId">
        ${assessment.assessmentId}
    </div>

    <div class="container" >
        <table  id="dg" class="easyui-datagrid"  style="height:400px;">
            <thead></thead>
            <tbody>
            </tbody>
        </table>
    </div>
    <script type="text/javascript">

        /*
         <id column="ID" property="id" jdbcType="DECIMAL" />
         <result column="ASSESSMENT_ID" property="assessmentId" jdbcType="DECIMAL" />
         <result column="BILLING_CYCLE" property="billingCycle" jdbcType="DECIMAL" />
         <result column="CSS_STAFF_ID" property="cssStaffId" jdbcType="DECIMAL" />
         <result column="ASSESSMENT_AMOUNT" property="assessmentAmount" jdbcType="DECIMAL" />
         <result column="PERSONAL_AMOUNT" property="personalAmount" jdbcType="DECIMAL" />
         <result column="ROLE_NAME" property="roleName" jdbcType="VARCHAR" />
         <result column="STATE" property="state" jdbcType="VARCHAR" />
         <result column="STATE_DATE" property="stateDate" jdbcType="TIMESTAMP" />
         */
        $(function () {
            var roles = [];
            var jroles = eval(${roles});
            $.each(jroles,function (i,n) {
                roles.push({name:n});
            });
            var staffs = eval('('+$('#staffdiv').text()+')');
            var assessmentId = $('#assessId').text();
            console.log(staffs);

            //var staffs=${staffs};
            var datagrid; //定义全局变量datagrid
            var editRow = undefined; //定义全局变量：当前编辑的行
            datagrid = $("#dg").datagrid({
                //url: '/assessment/uploadstaffassessment.html', //请求的数据源
                iconCls: 'icon-save', //图标
                pagination: true, //显示分页
                pageSize: 15, //页大小
                pageList: [15, 30, 45, 60], //页大小下拉选项此项各value是pageSize的倍数
                fit: true, //datagrid自适应宽度
                fitColumn: false, //列自适应宽度
                striped: true, //行背景交换
                nowap: true, //列内容多时自动折至第二行
                emptyMsg: '无记录',
                border: false,
                idField: 'ID', //主键
                columns: [[//显示的列
                    {field: 'ID', title: '编号', width: 100},
                    { field: 'cssNumber', title: 'CSS工号', width: 100, sortable: true,

                    },
                    { field: 'username', title: '姓名', width: 100,
                        editor: {type: 'combobox', options: {
                            valueField:'staffName',
                            textField:'staffName',
                            data:staffs,
                            required: true}
                        }
                    },
                    { field: 'assessmentAmount', title: '门店考核奖励', width: 100
                    },
                    { field: 'amount', title: '个人考核奖励', width: 100,
                        editor: { type: 'numberbox', options: {precision:2, required: true} }
                    },
                    {
                        field: 'roleName', title: '装维营销类别', width: 100,
                        editor: {type: 'combobox', options: {
                            valueField:'name',
                            textField:'name',
                            data:roles,
                            required: true}
                        }
                    }
                ]],
                queryParams: { assessmentId: 0 }, //查询参数
                toolbar: [{ text: '添加', iconCls: 'icon-add', handler: function () {//添加列表的操作按钮添加，修改，删除等
                    //添加时先判断是否有开启编辑的行，如果有则把开户编辑的那行结束编辑
                    if (editRow != undefined) {
                        datagrid.datagrid("endEdit", editRow);
                    }
                    //添加时如果没有正在编辑的行，则在datagrid的第一行插入一行
                    if (editRow == undefined) {
                        datagrid.datagrid("insertRow", {
                            index: 0, // index start with 0
                            row: {
                                'assessmentAmount':${assessment.doubleReward}
                            }
                        });
                        //将新插入的那一行开户编辑状态
                        datagrid.datagrid("beginEdit", 0);
                        //给当前编辑的行赋值
                        editRow = 0;
                    }

                }
                }, '-',
                    { text: '删除', iconCls: 'icon-remove', handler: function () {
                        //删除时先获取选择行
                        var rows = datagrid.datagrid("getSelections");
                        //选择要删除的行
                        if (rows.length > 0) {
                            $.messager.confirm("提示", "你确定要删除吗?", function (r) {
                                if (r) {

                                    for (var i = rows.length - 1; i >= 0; i--) {
                                        var index = $('#dg').datagrid('getRowIndex',rows[i]);
                                        $('#dg').datagrid('deleteRow', index);
                                    }
                                    /*var ids = [];
                                    for (var i = 0; i < rows.length; i++) {
                                        ids.push(rows[i].ID);
                                    }
                                    //将选择到的行存入数组并用,分隔转换成字符串，
                                    //本例只是前台操作没有与数据库进行交互所以此处只是弹出要传入后台的id
                                    alert(ids.join(','));*/
                                }
                            });
                        }
                        else {
                            $.messager.alert("提示", "请选择要删除的行", "error");
                        }
                    }
                    }, '-',
                    /*{ text: '修改', iconCls: 'icon-edit', handler: function () {
                        //修改时要获取选择到的行
                        var rows = datagrid.datagrid("getSelections");
                        //如果只选择了一行则可以进行修改，否则不操作
                        if (rows.length == 1) {
                            //修改之前先关闭已经开启的编辑行，当调用endEdit该方法时会触发onAfterEdit事件
                            if (editRow != undefined) {
                                datagrid.datagrid("endEdit", editRow);
                            }
                            //当无编辑行时
                            if (editRow == undefined) {
                                //获取到当前选择行的下标
                                var index = datagrid.datagrid("getRowIndex", rows[0]);
                                //开启编辑
                                datagrid.datagrid("beginEdit", index);
                                //把当前开启编辑的行赋值给全局变量editRow
                                editRow = index;
                                //当开启了当前选择行的编辑状态之后，
                                //应该取消当前列表的所有选择行，要不然双击之后无法再选择其他行进行编辑
                                datagrid.datagrid("unselectAll");
                            }
                        }
                    }
                    }, '-',*/
                    { text: '保存', iconCls: 'icon-save', handler: function () {
                        //保存时结束当前编辑的行，自动触发onAfterEdit事件如果要与后台交互可将数据通过Ajax提交后台
                        if (editRow != undefined) {
                            datagrid.datagrid("endEdit", editRow);
                        }
                        if (editRow != undefined){
                            $.messager.alert("信息","请首先完成当前记录",'info');
                            return;
                        }
                        var rows = datagrid.datagrid('getData');//datagrid.datagrid("getSelections");
                        var    arr=$('#dg').datagrid('getChanges');
                        if (arr.length == 0){
                            $.messager.alert("信息","无考核记录上报!",'info');
                            return;
                        }
                        console.info("getchanges array",arr);
                        $.each(arr,function (i,n) {
                            //console.log("getchanges",n);j
                            $.each(staffs,function (j,staff) {
                                if (arr[i].username == staff.staffName) {
                                    arr[i].cssNumber = staff.assistNum;
                                    //$("#dg").datagrid("getData")[rowIndex].username = n.staffName;
                                }
                            });
                        });
                        $.ajax({
                            type:"POST",
                            url:"/assessment/dofeedback/"+assessmentId.trim()+"?freshid=" + Math.random(),
                            data: JSON.stringify(arr),
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
                        })
                    }
                    }, '-',
                    { text: '取消编辑', iconCls: 'icon-redo', handler: function () {
                        //取消当前编辑行把当前编辑行罢undefined回滚改变的数据,取消选择的行
                        editRow = undefined;
                        datagrid.datagrid("rejectChanges");
                        datagrid.datagrid("unselectAll");
                    }
                    }, '-'],
                onAfterEdit: function (rowIndex, rowData, changes) {
                    //endEdit该方法触发此事件
                    editRow = undefined;
                    /*$.each(staffs,function (i,n) {
                        if (rowData.cssNumber == n.assistNum) {
                            rowData.username = n.staffName
                            //$("#dg").datagrid("getData")[rowIndex].username = n.staffName;
                        }
                    });*/

                    console.info(rowData);
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
                }
            });
        });
    </script>

</body>
</html>
