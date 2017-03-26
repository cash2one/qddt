<%--
  Created by IntelliJ IDEA.
  User: zark
  Date: 17/3/25
  Time: 下午8:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ page isELIgnored="false"%>
<%@ include file="header.jsp"%>
<div class="container" >
    <div class="col-md-6 col-sm-12 col-xs-12">
        <div class="x_panel">
            <div class="x_title">
                <h2>Form Buttons <small>Sessions</small></h2>
                <ul class="nav navbar-right panel_toolbox">
                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                    </li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
                        <ul class="dropdown-menu" role="menu">
                            <li><a href="#">Settings 1</a>
                            </li>
                            <li><a href="#">Settings 2</a>
                            </li>
                        </ul>
                    </li>
                    <li><a class="close-link"><i class="fa fa-close"></i></a>
                    </li>
                </ul>
                <div class="clearfix"></div>
            </div>
            <div class="x_content">

                <form class="form-horizontal form-label-left">

                    <div class="form-group">
                        <label class="col-sm-3 control-label">Button addons</label>

                        <div class="col-sm-9">
                            <div class="input-group">
                                <span class="input-group-btn">
                                                  <button type="button" class="btn btn-primary">Go!</button>
                                              </span>
                                <input type="text" class="form-control">
                            </div>
                            <div class="input-group">
                                <input type="text" class="form-control">
                                <span class="input-group-btn">
                                                  <button type="button" class="btn btn-primary">Go!</button>
                                              </span>
                            </div>
                        </div>
                    </div>
                    <div class="divider-dashed"></div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label">Button addons</label>

                        <div class="col-sm-9">
                            <div class="input-group">
                                <input type="text" class="form-control" aria-label="Text input with dropdown button">
                                <div class="input-group-btn">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">Action <span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu dropdown-menu-right" role="menu">
                                        <li><a href="#">Action</a>
                                        </li>
                                        <li><a href="#">Another action</a>
                                        </li>
                                        <li><a href="#">Something else here</a>
                                        </li>
                                        <li class="divider"></li>
                                        <li><a href="#">Separated link</a>
                                        </li>
                                    </ul>
                                </div>
                                <!-- /btn-group -->
                            </div>
                            <div class="input-group">
                                <input type="text" class="form-control">
                                <span class="input-group-btn">
                                  <button type="button" class="btn btn-primary">Go!</button>
                                </span>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div><!--container-->

<%@ include file="footer.jsp"%>
