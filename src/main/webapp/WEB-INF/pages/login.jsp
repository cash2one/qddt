<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登录</title>

<link href="css/login.css" rel="stylesheet" type="text/css">

</head>

<body class="login">
	<div class="login_logo" style="margin-top:68px;"><img src="images/logo.png"></div>
	<form action="/user/dologin.html" method="post" >
<div class="login_m">
	<div class="login_boder">
		<div class="login_padding">
			<h2>用户名</h2>
			<label>
				<input type="text" id="username" name="username" class="txt_input txt_input2">
			</label>
			<h2>密码</h2>
			<label>
				<input type="password" name="password" id="password" class="txt_input">
			</label>
			<div class="rem_sub">
				<div class="rem_sub_l">
					<input type="checkbox" name="checkbox" id="save_me">
					<label for="checkbox">记住</label>
				</div>
				<label>
					<input type="submit" class="sub_button" name="button" id="button" value="登录" style="opacity: 0.7;">
				</label>
			</div>
		</div>
	</div><!--login_boder end-->
</div><!--login_m end-->
	</form><!--form end-->
<br />
<br />

<p align="center">版权所有：南京电信</p>

</body>
</html>