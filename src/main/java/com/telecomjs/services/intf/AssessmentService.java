package com.telecomjs.services.intf;

import com.telecomjs.beans.*;
import com.telecomjs.datagrid.AssessmentStateHelper;
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



    int getSequenceOfAuditLog();

    List<AuditLog> findAuditLogById(int assessmentId);

    List<AuditLog> findAuditLogByCssNumber(Long cssStaffNumber);

    //根据账期统计非关闭状态的事件数量
    int countEventsByCycle(int billingCycleId);


    List<Assessment> findAllDistrictAssessments();
    //根据账期和区域查询所有分局的统计结果
    List<Assessment> findAllDistrictAssessments(long areaId,int billingCycle);

    List<Assessment> findAllAreaAssessments();
    //根据账期查询所有区域的统计结果
    List<Assessment> findAllAreaAssessments(int billingCycle);


    List<BillingCycle> findAllCycles();

    StaffAssessment getStaffAssessment(int staffAssessId);

    int existsZoneAssessment(long zone, Integer assessmentId);

    int existsStaffAssessment(Long cssStaffId, Integer assessmentId);



    List<Assessment> findDCEOToDoAssessments(long districtId);

    List<Assessment> findAssessmentByDistrict(long districtId, int billingCycle);

    //结束考核表(一个)
    int endAssessment(int assessmentId);

    //管理员查询指定账期下的员工绩效表
    List<AssessmentWithDetail> findAssessmentByCycle(int cycle);

    //管理员分页查询指定账期下的员工绩效
    List<AssessmentWithDetail> findAssessmentWithPageByCycle(int cycle,int startRow,int pageSize);
    //管理员分页查询指定账期下的员工绩效
    int findAssessmentCountByCycle(int cycle,int startRow,int pageSize);



    //管理员查询指定账期下的分局员工绩效表
    List<AssessmentWithDetail> findStaffAssessmentForDistrictAndCycle(long districtId, int cycle);

    //管理员查询制定账期下的区域员工绩效表
    List<AssessmentWithDetail> findStaffAssessmentForAreaAndCycle(long areaId, int cycle);

    //区局查询考核项
    List<Assessment> findAssessmentByArea(long areaId, int billingCycle);

    //检查是否已上传签名
    int existsAssessmentSignature(int assessmentId);


    //区域审核第一次片区的分配表 同意 全部区域
    int auditAssessmentByArea(int billingCycle, int areaId,AuditLog auditLog);
    //区域审核第一次片区的分配表 不同意 某个分局
    int unauditAssessmentByDistrict(int billingCycle, int districtId,AuditLog auditLog);

    //渠道审核第一次片区的分配表（全区范围内的）
    int auditAllAssessment(int billingCycle,AuditLog auditLog );

    //区域审核第二次片区的签收表 同意 区域范围
    int commitAssessmentByArea(int billingCycle, int areaId,AuditLog auditLog);

    //渠道审核第二次片区的签收表（全区范围内的）
    int commitAllAssessment(int billingCycle,AuditLog auditLog );

    //分局长审核片区的考核表（第一次）
    int auditAssessment(int assessmentId, AuditNodeHelper.SuggestionType suggestion, AuditLog auditLog);

    //分局长二次审阅片区的签收表 同意 片区范围
    int commitAssessment(int assessmentId,  AuditLog auditLog);
    //分局长二次审阅片区的签收表 不同意 片区范围
    int uncommitAssessment(int assessmentId,   AuditLog auditLog);

    int addBillingCycle(int billingCycle);

    int notifyBillingCycle(int billingCycle);

    //判断区域是否有权对分局做操作
    boolean areaHasAllDistricts(Long aLong, List<Long> districtIds);

    public int auditDistricts(int billingCycle,List<Long> districtIds, AuditNodeHelper.SuggestionType suggestionType, AuditLog auditLog , AssessmentStateHelper.AssessmentNode node);

    public int reAuditAssessment(int assessmentId, AuditNodeHelper.SuggestionType suggestionType, String remark, AssessmentStateHelper.AssessmentNode node, Staff staff);

    int auditAll(int billingCycle, AuditNodeHelper.SuggestionType suggestionType, String remark, AssessmentStateHelper.AssessmentNode aud, Staff staff);
    //根据名称查询区域ID和分局ID
    public long getDistrictIdByName(String name);
    public long getAreaIdByName(String name);
}
