<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>
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
<title>角色管理</title>
</head>

<body>
<div class="page-content">
 <!--角色管理-->
 <form id="form1" name="form1" method="post" action="">
 <div class="Role_Manager_style">
  <%--<div class="Manager_style">
    <div class="title_name">用户角色</div>
    <button  type="button" class="btn btn-primary"  id="Add_Roles_btn">添加角色</button>
     <div id="Add_Roles_style"  style="display:none">
     <div class="user_Manager_style ">
     <div class="add_user_style">
       <ul class="clearfix">
      <li><label class="label_name">角色标识</label> <input name="角色标识" type="text" value="NJ34532" class="text_add"/></li>
      <li><label class="label_name">角色名称</label><input name="角色名称" type="text" value="航道局总部" class="text_add"/></li>
      <li><label class="label_name">角色状态</label>  
          <div class="radio"><label><input name="form-field-radio" type="radio" class="ace" checked="checked" /><span class="lbl">启用</span></label></div>
          <div class="radio"><label><input name="form-field-radio" type="radio" class="ace" /><span class="lbl">停用</span></label></div></li>

      </ul>    
      <div class="Remark"><label class="label_name">备注</label><textarea name="" cols="" rows="" style=" width:456px; height:200px; padding:5px;"></textarea></div>
 <!--     <div class="btn_operating"><button id="submit" type="button" class="btn btn-primary">保存</button><button  type="button" class="btn btn-warning">重置</button></div>-->
    </div>
  </div>
  </div>
  </div>--%>
  <%--<div class="Manager_style">
   <div class="title_name">用户角色2</div>
   </div>--%>
  <!---->
  <div class="Manager_style">
    <div class="title_name">角色列表</div>
    <div class="Role_list">
     <table id="Role_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
      <thead>
       <tr><th>序列号</th><th>角色标识</th><th>角色名称</th><th>角色状态</th></tr>
      </thead>
      <tbody>
       <c:forEach var="role" items="${roles}">
        <tr>
         <td>${role.roleId}</td>
         <td>${role.roleName}</td>
         <td>${role.roleCode}</td>
         <td> <c:if test="${role.state=='USE'}">在用</c:if> <c:if test="${role.state=='INU'}">不在用</c:if></td>
         <%--<td><a class="btn btn-info" href="user/viewuser?userid=${user.userId}">修改</a></td>
         <td>
          <button  type="button" class="btn btn-primary ">修改</button>
          <button  type="button" class="btn btn-warning">删除</button>
          <a class="btn btn-info" href="权限设置.html">权限</a>
         </td>--%>
        </tr>
       </c:forEach>

      </tbody>
     </table>
    </div>
  </div>
 </div>
  </form>
</div>
<!--<script src="/assets/js/jquery.min.js"></script>-->
<script src="/js/jquery-1.8.3.min.js" ></script>
    <!-- <![endif]-->
    <!--[if IE]>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<![endif]-->
    <!--[if !IE]> -->
    <script type="text/javascript">
        window.jQuery || document.write("<script src='/assets/js/jquery-2.0.3.min.js'>"+"<"+"/script>");
    </script>
    <!-- <![endif]-->

    <!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='/assets/js/jquery-1.10.2.min.js'>"+"<"+"/script>");
</script>
<![endif]-->
	<script src="/assets/js/bootstrap.min.js"></script>
      <script src="/assets/layer/layer.js" type="text/javascript"></script>
<script type="text/javascript">
  $('#Add_Roles_btn').on('click', function(){
    layer.open({
        type: 1,
        title: '添加/修改角色',
		maxmin: true, 
		shadeClose: true, //点击遮罩关闭层
        area : ['660px' , ''],
        content:$('#Add_Roles_style'),
		btn:['提交','取消'],
		yes:function(index,layero){	
		 var num=0;
		 var str="";
         $("input[type$='text']").each(function(n){
          if($(this).val()=="")
          {
               
			    layer.alert(str+=""+$(this).attr("name")+"不能为空！\r\n",{
                title: '提示框',				
				icon:0,	
							
          }); 
		     num++;
             return false;            
          } });
		  if(num>0)
     {
        
          return false;
     }
          else{
			  
			  layer.alert('添加成功！',{
               title: '提示框',				
			icon:1,		
			  });
			   layer.close(index);	
		  }		  		     				
		}
    });
});
</script>
</body>
</html>
