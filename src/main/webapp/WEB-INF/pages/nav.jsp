<%--
  Created by IntelliJ IDEA.
  User: zark
  Date: 16/11/27
  Time: 下午11:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">EOP</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">主页</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">用户管理 <span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="<%=apppath%>/user/add">增加用户</a></li>
                        <li><a href="<%=apppath%>/user/list">用户列表</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">应用管理 <span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="<%=apppath%>/app/addapp">增加应用</a></li>
                        <li><a href="<%=apppath%>/app/list">应用列表</a></li>
                        <li><a href="<%=apppath%>/app/onlinelist">在线列表</a></li>
                        <li class="divider"></li>
                        <li><a href="<%=apppath%>/app/limitlist">应用访问控制列表</a></li>
                        <li><a href="<%=apppath%>/app/addlimit">增加应用授权</a></li>
                        <%--<li><a href="#">修改应用授权</a></li>--%>
                        <%--<li class="divider"></li>--%>
                        <%--<li><a href="#">One more separated link</a></li>--%>
                    </ul>
                </li>
            </ul>
            <!--搜索-->
            <%--<form class="navbar-form navbar-left" role="search">--%>
                <%--<div class="form-group">--%>
                    <%--<input type="text" class="form-control" placeholder="Search">--%>
                <%--</div>--%>
                <%--<button type="submit" class="btn btn-default">Submit</button>--%>
            <%--</form>--%>

            <!-- 导航栏右侧菜单 -->
            <%--<ul class="nav navbar-nav navbar-right">--%>
                <%--<li><a href="#">Link</a></li>--%>
                <%--<li class="dropdown">--%>
                    <%--<a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <span class="caret"></span></a>--%>
                    <%--<ul class="dropdown-menu" role="menu">--%>
                        <%--<li><a href="#">Action</a></li>--%>
                        <%--<li><a href="#">Another action</a></li>--%>
                        <%--<li><a href="#">Something else here</a></li>--%>
                        <%--<li class="divider"></li>--%>
                        <%--<li><a href="#">Separated link</a></li>--%>
                    <%--</ul>--%>
                <%--</li>--%>
            <%--</ul>--%>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>

