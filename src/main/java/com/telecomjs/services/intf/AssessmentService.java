package com.telecomjs.services.intf;

import com.telecomjs.beans.*;
import com.telecomjs.datagrid.AuditNodeHelper;

import java.util.List;

/**
 * Created by zark on 17/3/8.
 */
public interface AssessmentService {

    public int getSequenceOfEvent();
    public int getSequenceOfAssessment();

    //读取excel文件，存入assessment_temp表
    public int initEventAndAssessment(AssessmentEvent event,List assessmentList);

    //确认考核表，并发起生成任务 成功返回任务ID，失败返回0
    public int startEventAndAssessment(int eventId);

    //片区ceo报告考核详表,成功返回考核表ID，错误返回0
    public int reportAssessment(int assessmentId);

    //片区ceo报告考核详表,成功返回考核表ID，错误返回0
    public int feedbackAssessment(int assessmentId, List<StaffAssessment> list);


    public int reportEvent(int eventId);

    public AssessmentEvent getEvent(int eventId);

    List<AssessmentEvent> findAllEvents();

    /**
     * 关闭上报事件
     * 事件表关闭状态CLS，assessment 删除记录,assessment_temp ，删除记录
     * @param eventId
     * @return
     */
    int closeEvent(int eventId);

    int subscribeEvent(int eventId);

    //查询片区内的所有考核项
    List<Assessment> findAssessmentByZone(long zoneId);
    //查询所有考核项（管理员)
    List<Assessment> findAllAssessment();

    Assessment getAssessment(int assessmentId);

    int getSequenceOfStaffAssessment();

    List<StaffAssessment> findAssessmentById(int assessmentId);
    List<StaffAssessment> findStaffAssessmentByZone(long zoneId);

    Integer getSequenceOfSignature();

    int saveSignature(AssessmentSignature signature);

    AssessmentSignature findSignature(int assessmentId);

    AssessmentSignature getSignature(int signatureId);

    List<Assessment> findAssessmentByStaff(Long cssStaffNumber);

    boolean districtHasAssessment(Long districtId, int assessmentId);

    int auditAssessment(int assessmentId, AuditNodeHelper.SuggestionType suggestion, AuditLog auditLog);

    int getSequenceOfAuditLog();

    List<AuditLog> findAuditLogById(int assessmentId);

    List<AuditLog> findAuditLogByCssNumber(Long cssStaffNumber);

    //根据账期统计非关闭状态的事件数量
    int countEventsByCycle(int billingCycleId);


    List<Assessment> findAllDistrictAssessments();

    List<Assessment> findAllAreaAssessments();

    List<BillingCycle> findAllCycles();

    StaffAssessment getStaffAssessment(int staffAssessId);

    int existsZoneAssessment(long zone, Integer assessmentId);

    int existsStaffAssessment(Long cssStaffId, Integer assessmentId);

    List<AssessmentWithDetail> findAssessmentByCycle(int cycle);

    List<Assessment> findDCEOToDoAssessments(long districtId);

    List<Assessment> findAssessmentByDistrict(long districtId, int billingCycle);

    int endAssessment(int assessmentId);

    List<AssessmentWithDetail> findStaffAssessmentForDistrictAndCycle(long districtId, int cycle);

    List<AssessmentWithDetail> findStaffAssessmentForAreaAndCycle(long areaId, int cycle);

    //区局查询考核项
    List<Assessment> findAssessmentByArea(long areaId, int billingCycle);

    int existsAssessmentSignature(int assessmentId);
}
