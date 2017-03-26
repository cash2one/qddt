<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <link href="/assets/css/bootstrap.min.css" rel="stylesheet" />
  <link rel="stylesheet" href="/assets/css/font-awesome.min.css" />
  <!--[if IE 7]>
    <link rel="stylesheet" href="/assets/css/font-awesome-ie7.min.css" />
  <![endif]-->
  <link rel="stylesheet" href="/assets/css/jquery-ui-1.10.3.full.min.css">
  <link rel="stylesheet" href="/assets/css/ace.min.css" />
  <link rel="stylesheet" href="/css/style.css"/>
  <link rel="stylesheet" href="/css/jquery.dataTables.min.css" />
  <link rel="stylesheet" href="/css/zTreeStyle/zTreeStyle.css" />
<title>用户管理</title>
</head>

<body>
<div class="page-content">
 <!--用户管理-->
 <div class="user_Manager_style">
 <form id="form1" name="form1" method="post" action="">
  <div class="Manager_style add_user_info">
    <div class="title_name">添加用户</div>
     <button class="btn btn-primary" type="button" id="Add_user_btn">添加用户</button>
     <div id="Add_user_style" style="display:none">
    <div class="add_user_style clearfix">
<!--    <div class="reset_style"><input name="reset" type="reset" value="重置" /class="btn btn-warning"> </div>-->
     <ul class="clearfix">
      <li><label class="label_name">登录手机号</label> <input name="登录手机号" type="text"  class="" id="mobile"/><i style="color:#F60; ">*</i></li>
      <li><label class="label_name">登录密码</label><input name="登录密码" type="password"  class="" id="password"/><i style="color:#F60; ">*</i></li>
      <li><label class="label_name">用户角色</label>
      <select name="用户角色" size="1" id="select_Roles" class="">
       <option selected value="">--请选择--</option>
        <c:forEach var="role" items="${roles}">
            <option value="${role.roleId}">${role.roleName}</option>
        </c:forEach>
      </select>
      <i style="color:#F60; ">*</i>
      </li>
      <li><label class="label_name">CSS工号</label><input name="CSS工号" type="text"  class="" id="css_staff"/><i style="color:#F60; ">*</i></li>
      <li><label class="label_name">用户姓名</label><input name="用户姓名" type="text"  class="" id="user_name"/><i style="color:#F60; ">*</i></li>
      <li><label class="label_name">用户状态</label> 
          <select name="用户状态" size="1" id="select_status">
           <option selected value="">--请选择--</option>
        <option value="USE">启用</option>
        <option value="LCK">锁定</option>
      </select>
       <i style="color:#F60; ">*</i>
      </li>
      <li><label class="label_name">身份证号码</label><input  name="用户姓名" type="text"  class="" id="identified_code"/><i style="color:#F60; ">*</i></li>
      </li>

     </ul>
     <div id="hao"></div>
     <div class="Remark"><label class="label_name">备注</label><textarea  id="remark" name="remark" cols="" rows="" style=" width:700px; height:100px; padding:5px;"></textarea></div>
    </div><!--onclick="return checkNull()"-->
    </div>
    </div>
     </form>
     <div class="">
         <div class="row">
             <div class="col-md-3" id="left_panel" style="height: 100%;">

                 <div class="Manager_style">
                     <div class="title_name">组织列表</div>
                     <ul id="tree" class="ztree" style="width:100%; height:460px; overflow:auto;"></ul>
                 </div>
             </div>

             <div class="col-md-9" id="right_panel" style="height: 100%;">
                 <div class="Manager_style">
                     <div class="title_name">用户列表</div>
                     <table  width="100%" height="380" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover" id="dt_staff">
                         <thead>
                         <tr>
                             <th >工号ID</th>
                             <th>用户名</th>
                             <th >手机号码</th>
                             <th>身份证</th>
                             <th>CSS工号</th>
                             <th>BSS工号</th>
                             <th>状态</th>
                             <th>创建日期</th>
                             <th>操作</th>
                         </tr>
                         </thead>
                         <tbody>
                         </tbody>
                         </table>
                 </div>
             </div>
         </div>
    </div>

 </div>
 </div>
<script src="/js/jquery-3.1.1.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/assets/layer/layer.js"></script>
<script   src="/js/jquery.dataTables.min.js" ></script>
<script   src="/js/jquery.ztree.all.min.js" ></script>
<script   src="/js/jquery.ztree.core.js" ></script>
<script type="text/javascript">
function hide1(){
document.getElementById('ycxz').style.display='';
document.getElementById('gys_name').style.display='none';
  
}
function display1(){
document.getElementById('ycxz').style.display='none';
document.getElementById('gys_name').style.display='';
} 
/***判断用户添加文本**/
 $('#Add_user_btn').on('click', function(){
	   layer.open({
        type: 1,
        title: '添加用户',
		maxmin: true, 
		shadeClose: true, //点击遮罩关闭层
        area : ['860px' , '400px'],
        content:$('#Add_user_style'),		
		btn: ['添加','取消'],
	    yes: function(index, layero){ 	 
		  if ($("#mobile").val()==""){
			  layer.alert('登录用户名不能为空!',{
              title: '提示框',				
				icon:0,
			    
			 });
			return false;
          }  
          if ($("#password").val()==""){
			  layer.alert('密码不能为空!',{
              title: '提示框',				
				icon:0,
			    
			 });
			return false;
          } 
		  
		   if ($("#select_Roles").val()==""){
			  layer.alert('用角色不能为空!',{
              title: '提示框',				
				icon:0,
			    
			 });
			return false;
          }   
		    
		   if ($("#css_staff").val()==""){
			  layer.alert('CSS工号不能为空!',{
              title: '提示框',				
				icon:0,
			    
			 });
			return false;
          }  
		   if ($("#user_name").val()==""){
			  layer.alert('用户名不能为空!',{
              title: '提示框',				
				icon:0,
			    
			 });
			return false;
          }
          if ($("#identified_code").val()==""){
                layer.alert('身份证号码不能为空!',{
                    title: '提示框',
                    icon:0,
                });
                return false;
            }

            if ($("#select_status").val()==""){
			  layer.alert('用户状态能为空!',{
              title: '提示框',				
				icon:0,
			    
			 });
			return false;
          }   

	     else{
                $.ajax({
                    url:"/user/doadduser.html",
                    type:"post",
                    cache:false,
                    data:{'mobile':$("#mobile").val(),'username':$("#user_name").val()
                        ,'password':$("#password").val(),'role':$("#select_Roles").val()
                        ,'cssNumber':$("#css_staff").val(),'status':$("#select_status").val()
                        ,'identifiedCode':$("#identified_code").val(),'remark':$("#remark").val()},
                    dataType:'json',
                    contentType: "application/x-www-form-urlencoded; charset=utf-8",
                    success:function (msg,status) {
                        layer.alert('添加成功！'+msg,{
                            title: '提示框',
                            icon:1,
                        });
                        layer.close(index);
                    },
                    error:function (XMLHttpRequest, textStatus, errorThrown) {
                        layer.alert('添加失败！ajax'+'<br>'+textStatus+'<br>' + XMLHttpRequest+'<br>'+errorThrown,{
                            title: '提示框',
                            icon:0,
                        });
                        layer.close(index);
                    }
                });

		  }	
     } 	, 
	 cancel: function(index){
		 
		   layer.alert('确定退出！',{
               title: '提示框',				
			  icon:1,		
			  });
		
	} 
    });
});
 $('.reset_Password').on('click', function(){
	 layer.confirm('是否重置密码，重置后原密码将失效？', {
  btn: ['重置','取消'] //按钮
}, function(){
  layer.msg('重置成功！', {time: 1000,icon: 1});
});

});
    //设置tree节点
    //var treenodes = ${nodes};
    var dtStaff;
    var treesetting = {
        data: {
            key : {
                name : "name"
            },
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pId"
            }

        },
        check: {
            enable: false,
            chkStyle: "checkbox",
            chkboxType: { "Y": "ps", "N": "ps" }
        },
        async: {
            enable:true,
            url:"/area/doqueryzonenode.html" ,
            type:"get",
            dataFilter: ajaxDataFilter
        },
        callback: {
            onClick: onTreeClick  ,
            onAsyncSuccess: zTreeOnAsyncSuccess
        }
    };
    //var departNodes = makeTreeNodes(departList,"DEPART_ID","PARENT_ID","DEPART_NAME");
    var departNode = null;
    function onTreeClick(event, treeId, treeNode, clickFlag){
        //此处treeNode 为当前节点
        node = clickFlag > 0?treeNode:null;
        if (clickFlag > 0)
            refreshStaff(node.id,node.zoneType);
        //str = getAllChildrenNodes(treeNode,str);
    }

    function  zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
        setDepartmentIcon();
    }

    function getAllChildrenNodes(treeNode,result){
        if (treeNode.isParent) {
            var childrenNodes = treeNode.children;
            if (childrenNodes) {
                for (var i = 0; i < childrenNodes.length; i++) {
                    result += ',' + childrenNodes[i].id;
                    result = getAllChildrenNodes(childrenNodes[i], result);
                }
            }
        }
        return result;
    }

    function ajaxDataFilter(treeId, parentNode, responseData) {
        if (responseData) {
            return responseData;
        }
        else
            return [];
    };

    function initTreeNode(){

        /*$.ajax({
            url: "/user/doadduser.html",
            type: "post",
            cache: false,
            data: {
                'mobile': $("#mobile").val(), 'username': $("#user_name").val()
                , 'password': $("#password").val(), 'role': $("#select_Roles").val()
                , 'cssNumber': $("#css_staff").val(), 'status': $("#select_status").val()
                , 'identifiedCode': $("#identified_code").val(), 'remark': $("#remark").val()
            }
            , dataType: 'json'
            , contentType: "application/x-www-form-urlencoded; charset=utf-8"
            , success: function (data) {
                $.fn.zTree.init($("#tree"), treesetting,data);
            }
        });*/
        $.fn.zTree.init($("#tree"), treesetting);
        var zTree = $.fn.zTree.getZTreeObj("tree");
        setDepartmentIcon();
    }
    function setDepartmentIcon(){
        var zTree = $.fn.zTree.getZTreeObj("tree");
        //zTree.expandAll(true);
        var nodes = zTree.transformToArray(zTree.getNodes());
        $(nodes).each(function(i,n){
            //n.iconSkin = "folder";
            n.isParent = true;
            zTree.updateNode(n);
        });
    }
    function refreshDepartment() {

        var zTree = $.fn.zTree.getZTreeObj("tree");
        zTree.reAsyncChildNodes(null,"refresh",false);
        //zTree.expandAll(true);

    }

    function addTreeListener(){
        $("#tree").delegate("li","click",function(e){

            $("#tree li.selected").removeClass("selected");
            $(this).addClass("selected");
            refreshStaff($(this).attr("id"));
        });
    }
    function refreshStaff(id,type){
        $.ajax({

            type:"GET",
            url:"/user/dofindareauser.html",
            data:{'id':id,'type':type },
            success:function(data){
                //$(dtStaff).fnClearTable();
                //dtStaff.row.cleanData();
                dtStaff.clear();
                //刷新成员列表
                $.each(JSON.parse(data), function(i, n){
                    //alert(n.mobile);
                    dtStaff.row.add(n).draw();
                    //dtStaff.fnAddData(n);
                });

            },
            error : function() {
                alert("温馨提示：查询员工失败 ");

            }
        });
    }

    function initStaffTable() {
        /*dtStaff = $('#dt_staff').dataTable( {
            "bProcessing": false,
            "bJQueryUI": true,
            "sAjaxDataProp": "dataObj",
            "aoColumns": [
                { "mData": "staffId" },
                { "mData": "username" },
                { "mData": "cssStaffNumber" },
                { "mData": "identifiedCode" },
                { "mData": "roleId" },
                { "mData": "state" },
                { "mData": "createDate" }
            ],
            "bProcessing": false,
            "bInfo":true,
            "bPaginate":true,
            "autoWidth":true,
            "iDisplayLength":10,
            //"bServerSide": true,
            "sWidth":"100%",
            "bSearchable" : false,
            "bFilter": true,
            "bSort": true,
            "sScrollX": "100%",
            "oLanguage": {
                "sLengthMenu": "每页显示 _MENU_ 条记录",
                "sZeroRecords": "对不起，查询不到任何相关数据",
                "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
                "sInfoEmtpy": "找不到相关数据",
                "sInfoFiltered": "数据表中共有 _MAX_ 条记录)",
                "sProcessing": "正在加载中...",
                "sSearch": "搜索",
                "oPaginate": {
                    "sFirst":    "第一页",
                    "sPrevious": " 上一页 ",
                    "sNext":     " 下一页 ",
                    "sLast":     " 最后一页 "
                }
            }*/
        dtStaff = $('#dt_staff').DataTable( {
            "columns": [
                { "data": "staffId" },
                { "data": "name" },
                { "data": "mobile" },
                { "data": "identifiedcode" },
                { "data": "cssStaffNumber" },
                { "data": "bssStaffNumber" },
                { "data": "state" },
                { "data": "createDate" }
            ]
            ,columnDefs: [
                {
                    targets: 8,
                    render: function (a, b, c, d) {
                        var context =
                        {
                            func: [
                                {"name": "修改", "fn": "editStaff(\'" + c.staffId + "\',\'" + c.mobile + "\',\'" + c.identifiedcode + "\',\'" + c.cssStaffNumber + "\',\'" + c.bssStaffNumber+ "\',\'" + c.roleId  + "\')", "type": "primary"},
                                {"name": "注销", "fn": "deleteStaff(\'" + c.staffId + "\')", "type": "danger"}
                            ]
                        };
                        var html = '';
                        $.each(context.func,function(i,d){
                            html = html+'<button type="button" class="btn btn-'+d.type+' btn-sm" onclick="'+d.fn+'">'+d.name+'</button>';
                        });
                        return html;
                    }
                }
            ]
        } );


        //dtStaff.fnSetColumnVis( 0, false );
        $('#dt_staff tbody').on( 'click', 'tr', function () {
            $('#dt_staff tbody tr.selected').removeClass('selected');
            $(this).addClass('selected');
            //var d = $('#dt_staff').DataTable().rows('.selected').data()[0];
        } );

        /*$('#dt_staff tbody').on( 'dbclick', 'tr', function () {
            $(this).toggleClass('highlight');
            alert("dbclick ");
        } );*/
        /*$('#dt_staff tbody tr').on("click",  function( e ) {
            if ( $(this).hasClass('highlight') ) {
                $(this).removeClass('highlight');
            }
            else {
                $('#dt_staff tbody tr.highlight').removeClass('highlight');
                $(this).addClass('highlight');
            }
        });*/
        /*$(dtStaff).delegate("tr","dblclick",  function( e ) {
            var rowdata = dtStaff.fnGetData(this);

        });*/


    }

    function editStaff(id,mobile,idCode,cssNumber,bssNumber,roleId) {
        //
        $.ajax({

            type:"POST",
            url:"/user/doupdate.html",
            data:{'staffId':id,'mobile':mobile,'identifiedCode':idCode,'cssNumber':cssNumber,'bssNumber':bssNumber,'roleId':roleId },
            success:function(data){
                /*layer.alert('信息!',{
                    title: '温馨提示：'+data,
                    icon:0,

                });*/
                alert('温馨提示: '+data);

            },
            error : function() {
                /*layer.alert('信息!',{
                    title: '温馨提示：操作失败',
                    icon:0,

                });*/
                alert('温馨提示：操作失败');

            }
        });
    }

    function deleteStaff(id ) {
        //
        //BootstrapDialog.alert('信息提示')
        $.ajax({

            type:"POST",
            url:"/user/dodelete.html",
            data:{'staffId':id  },
            success:function(data){
                /*layer.alert('信息!',{
                    title: '温馨提示：'+data,
                    icon:0,

                });*/
                alert('温馨提示：'+data);

            },
            error : function() {
                /*layer.alert('信息!',{
                    title: '温馨提示：操作失败',
                    icon:0,

                });*/
                alert('温馨提示：操作失败');
            }
        });
    }
    $( document).ready(function(){
        initTreeNode();
        initStaffTable();
        refreshStaff(1,'None');
        //$.fn.zTree.init($("#tree"), treesetting);
        //$('#left_panel').height($('#right_panel').height());
    });

</script>
</body>
</html>
