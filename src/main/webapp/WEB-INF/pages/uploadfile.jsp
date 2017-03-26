<%--
  Created by IntelliJ IDEA.
  User: zark
  Date: 16/11/26
  Time: 下午5:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ include file="headerwithfile.jsp"%>
<%--<%@ include file="nav.jsp"%>--%>
<div class="container" >
    <%--<h4 class="header smaller lighter blue">上报考核</h4>--%>
    <p> <h4></h4></p>
    <div class="table-header"></div>
    <!-- form -->
    <form   role="form" action="/assessment/doupload" method="post" enctype="multipart/form-data">


        <div class="form-group" >
            <label class="control-label "></label>
            <label class="control-label">请选择考核年月 </label>
            <select class="form-control" name="billingcycleid" required >
                <c:forEach var="cycle" items="${cycles}">
                    <option value="${cycle.billingCycleId}">
                            ${cycle.billingCycleId}
                    </option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group" >
        <label class="control-label">选择文件</label>
        <input id="excelfile" name="excelfile" type="file" class="file"  data-show-upload="false" data-show-caption="true">
        </div>
        <p>${message}</p>
        <br>
        <button class="btn btn-lg btn-primary btn-block" type="submit">上传文件</button>
    </form>

    <script>
        /*$(document).on('ready', function() {
            $("#input-4").fileinput({showCaption: false});
        });*/
    </script>

</div>

</div>
<%@ include file="footer.jsp"%>
