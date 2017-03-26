<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ page isELIgnored="false"%>
<%@ include file="header.jsp"%>
<div class="container" >
    <div class="panel-heading" >
        <h3>代理商对投资金管理系统</h3>
    </div>
    <c:if test="${hasAssessments=='1'}">
        <c:forEach var="assessment" items="${assessments}">
        <div class="easyui-tabs col-sm-11" >
            <div title="${assessment.zoneName} ( ${assessment.billingCycle} )" style="padding:10px">
                <ul>
                    <li><label class="label_name"><a href="/assessment/feedbackstaffassessment.html?assessmentid=${assessment.assessmentId}">上报考核表</a></label></li>
                    <li><label class="label_name"><a href="/assessment/mystaffassessment.html?assessmentid=${assessment.assessmentId}">查看考核表</a></label></li>
                    <li><label class="label_name"><a href="/assessment/myassessmentsignature.html?assessmentid=${assessment.assessmentId}">签收表</a></label></li>
                    <li><label class="label_name"><a href="/user/myzonestaff.html">装维员工</a></label></li>
                </ul>
            </div>
        </div> <!-- end div tab -->
        </c:forEach> <!-- end foreach -->
    </c:if><!-- end if -->
    <c:if test="${hasAssessments!='1'}">
        <div class="easyui-tabs col-sm-11" >
            <div title="考核管理" style="padding:10px">
                <ul>
                    <li><label class="label_name"><a href="/assessment/myassessmentlist.html">我的考核片区</a></label></li>
                    <li><label class="label_name"><a href="/assessment/mystaffassessment.html?assessmentid=0">查看考核表</a></label></li>
                    <li><label class="label_name"><a href="/user/myzonestaff.html">装维员工</a></label></li>
                </ul>
            </div>
        </div> <!-- end div tab -->
    </c:if>
</div>
<script type="text/javascript" >

</script>

<%@ include file="footer.jsp"%>
