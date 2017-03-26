<%--
  Created by IntelliJ IDEA.
  User: zark
  Date: 16/11/26
  Time: 下午5:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ include file="header.jsp"%>
<%@ include file="nav.jsp"%>
<div class="container" >

    <!-- form -->
    <form   role="form" action="user/doadd" method="post">
        <h2 class="form-signin-heading">添加用户</h2>
        <div class="form-group">
            <label class="control-label col-sm-3">手机号码</label>
            <div class="col-sm-9">
                <input type="text" name="mobile" class="form-control col-sm-7" placeholder="手机号码" required >
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-sm-3">姓名</label>
            <div class="col-sm-9">
                <input type="text" name="name" class="form-control col-sm-7" placeholder="姓名" required>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-sm-3">CSS工号编号</label>
            <div class="col-sm-9">
                <input type="text" name="cssnumber" class="form-control col-sm-7" placeholder="CSS工号编号" required>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-sm-3">用户密码</label>
            <div class="col-sm-9">
                <input type="password" name="password" class="form-control col-sm-7" placeholder="用户密码" required>
            </div>
        </div>



        <div class="form-group">
            <label class="control-label col-sm-3">确认密码</label>
            <div class="col-sm-9">
                <input type="password" name="password2" class="form-control col-sm-7" placeholder="确认密码" required>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-sm-3">身份证号码</label>
            <div class="col-sm-9">
                <input type="email" name="identifiedcode" class="form-control col-sm-7" placeholder="身份证号码" required >
            </div>
        </div>



        <div class="form-group">
            <label class="control-label col-sm-3">请选择用户类型 </label>
            <select >
                <option value="1"> 管理员</option>
                <option value="2"> 分局CEO</option>
                <option value="3"> 片区管理员</option>
                <option value="4"> 装维工程师</option>
                <option value="5"> 促销员</option>
            </select>
        </div>

        <p>${message}</p>
        <button class="btn btn-lg btn-primary btn-block" type="submit">新增用户</button>
    </form>



</div>

</div>
<%@ include file="footer.jsp"%>
