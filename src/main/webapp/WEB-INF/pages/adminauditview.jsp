<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ include file="header.jsp"%>
<div class="container" >

    <div class="">

        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panelxxx">
                <div class="x_title">
                    <h2>全区审阅</h2>
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

                    <%--<p>Add small overlays of content for housing secondary information.</p>--%>
                    <!-- start pop-over -->
                    <div class="bs-example-popovers">
                        <select class="easyui-combobox fat" name="language" >

                            <c:forEach var="cycle" items="${cycles}">
                                <option>${cycle.billingCycleId}</option>
                            </c:forEach>
                        </select>
                        <button type="button" class="btn btn-default" data-container="body" data-toggle="popover" data-placement="left" data-content="Vivamus sagittis lacus vel augue laoreet rutrum faucibus." data-original-title="" title="">
                            选择所有
                        </button>
                        <button type="button" class="btn btn-default" data-container="body" data-toggle="popover" data-placement="top" data-content="Vivamus sagittis lacus vel augue laoreet rutrum faucibus." data-original-title="" title="">
                            取消所有
                        </button>
                        <button type="button" class="btn btn-default" data-container="body" data-toggle="popover" data-placement="bottom" data-content="Vivamus sagittis lacus vel augue laoreet rutrum faucibus.">
                            审阅通过
                        </button>
                        <button type="button" class="btn btn-default" data-container="body" data-toggle="popover" data-placement="right" data-content="Vivamus sagittis lacus vel augue laoreet rutrum faucibus.">
                            退回
                        </button>
                    </div>
                    <!-- end pop-over -->
                </div>
            </div>
        </div>
    </div>
    <div class="clearfix"></div>
    <c:forEach items="${assessments}" var="assessment">
        <!-- Default panel contents -->
        <div class="col-md-3 col-sm-3 col-xs-6">
            <div class="x_panel">
                <div class="x_title">
                    <h2><input type="checkbox" class="flat district-select"> <a href="/mark/areaauditview.html?billingCycle=${assessment.billingCycle}&areaName=${assessment.areaName}">${assessment.areaName} </a> </h2>
                        <%--<ul class="nav navbar-right panel_toolbox">--%>
                        <%--<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>--%>
                        <%--</li>--%>
                        <%--&lt;%&ndash;<li class="dropdown">--%>
                        <%--<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>--%>
                        <%--<ul class="dropdown-menu" role="menu">--%>
                        <%--<li><a href="#">Settings 1</a>--%>
                        <%--</li>--%>
                        <%--<li><a href="#">Settings 2</a>--%>
                        <%--</li>--%>
                        <%--</ul>--%>
                        <%--</li>&ndash;%&gt;--%>
                        <%--<li><a class="close-link"><i class="fa fa-close"></i></a>--%>
                        <%--</li>--%>
                        <%--</ul>--%>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">

                    <div class="">
                        <ul class="to_do">
                            <li>
                                <p>片区数: ${assessment.lastSettlement}</p>
                            </li>
                            <li>
                                <p>总金额: ${assessment.doubleReward}</p>
                            </li>

                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>


</div>
<%--<script type="text/javascript" src="/js/datagrid-filter.js" />--%>
<script type="text/javascript" >
    $(function() {
        var dg = $('#dg');
        dg.datagrid();	// create datagrid
        //dg.datagrid('enableFilter');
    });
    function doSubscriber(eventid) {
        $.ajax({
            type:"POST",
            url:"/assessment/dosubscribe.html",
            data:{'eventid':eventid  },
            success:function(data){
                /*layer.alert('信息!',{
                 title: '温馨提示：'+data,
                 icon:0,

                 });*/
                $.messager.alert("信息",'温馨提示: '+data,'info');
                //alert('温馨提示: '+data);

            },
            error : function() {
                /*layer.alert('信息!',{
                 title: '温馨提示：操作失败',
                 icon:0,

                 });*/
                //alert('温馨提示：操作失败');
                $.messager.alert("信息",'温馨提示：操作失败','error');

            }
        })

    }

    function doCloseEvent(eventid) {
        $.ajax({
            type:"POST",
            url:"/assessment/docloseevent.html",
            data:{'eventid':eventid  },
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
        })

    }
</script>
<%@ include file="footer.jsp"%>


