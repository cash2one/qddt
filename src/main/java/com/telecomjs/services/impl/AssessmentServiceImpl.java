package com.telecomjs.services.impl;

import com.github.pagehelper.PageHelper;
import com.telecomjs.beans.*;
import com.telecomjs.datagrid.AssessmentStateHelper;
import com.telecomjs.datagrid.AuditNodeHelper;
import com.telecomjs.mappers.*;
import com.telecomjs.services.intf.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zark on 17/3/8.
 */
@Service
public class AssessmentServiceImpl implements AssessmentService{
    @Autowired
    AssessmentEventMapper assessmentEventMapper;
    @Autowired
    AssessmentMapper assessmentMapper;
    @Autowired
    AssessmentSubscriberMapper assessmentSubscriberMapper;
    @Autowired
    BillingCycleMapper billingCycleMapper;
    @Autowired
    StaffAssessmentMapper staffAssessmentMapper;
    @Autowired
    AssessmentSignatureMapper assessmentSignatureMapper;
    @Autowired
    AuditLogMapper auditLogMapper;

    public int getSequenceOfEvent(){
        return assessmentEventMapper.getPrimaryKey();
    }

    public int getSequenceOfAssessment(){
        return assessmentMapper.getPrimaryKey();
    }

    //读取excel文件，存入assessment_temp表
    public int initEventAndAssessment(AssessmentEvent event,List assessmentList){
        if (event == null || assessmentList == null)
            return 0;
        int eventId = event.getEventId();

        //累加得到总金额
        double amount= 0;
        for (Object o:assessmentList){
            amount += ((Assessment)o).getReward().doubleValue();
        }
        event.setAmount(new BigDecimal(amount));
        if (assessmentEventMapper.insert(event) >0 ){
            for (Object o:assessmentList){
                Assessment assessment = (Assessment)o;
                assessment.setAssessmentEventId(eventId);
                assessment.setAssessmentId(assessmentMapper.getPrimaryKey());
                assessment.setBillingCycle(event.getBillingCycleId());
                assessment.setState("OPN");
                assessmentMapper.insertTemp(assessment);
            }
            return eventId;
        }
        return 0;
    }

    //确认考核表，并发起生成任务 成功返回任务ID，失败返回0
    public int startEventAndAssessment(int eventId){
        if (eventId == 0 )
            return 0;
        return 0;
    }


    //片区ceo报告考核详表,成功返回考核表ID，错误返回0
    public int reportAssessment(int assessmentId){
        if (assessmentId == 0 )
            return 0;
        return 0;
    }

    /**
     * 片区ceo报告考核详表,成功返回考核表ID，错误返回0
     * assessment -> FED
     * staff_assessment -> OPN
     * assessment_subscriber->FED
     *
     * @param assessmentId
     * @param list
     * @return
     */

    public int feedbackAssessment(int assessmentId, List<StaffAssessment> list){
        if (assessmentId == 0 || list == null)
            return 0;
        if (assessmentMapper.feedbackAssessment(assessmentId) > 0){
            if (assessmentSubscriberMapper.feedbackAssessment(assessmentId) > 0){
                for (StaffAssessment staffAssessment : list ) {
                    staffAssessmentMapper.insert(staffAssessment);
                }
            }
        }
        return assessmentId;
    }

    /**
     * 将event 状态置为OPEN,关联的assessment入正式表
     * @param eventId
     * @return
     */
    @Override
    public int reportEvent(int eventId) {
        AssessmentEvent event = new AssessmentEvent();
        event.setEventId(eventId);
        event.setState("OPN");
        if (assessmentEventMapper.updateByPrimaryKeySelective(event)>0){
            assessmentMapper.updateTempOpenByEvent(eventId);
            assessmentMapper.insertBySelectEvent(eventId);
            assessmentMapper.deleteTempByEvent(eventId);
            return eventId;
        }
        return 0;
    }


    @Override
    public AssessmentEvent getEvent(int eventId) {
        return assessmentEventMapper.selectByPrimaryKey(eventId);
    }

    @Override
    public List<AssessmentEvent> findAllEvents() {
        return assessmentEventMapper.findAll();
    }

    /**
     * 修改event状态为CLS,刷新state_date字段
     * @param eventId
     * @return
     */
    @Override
    public int closeEvent(int eventId) {

        return assessmentEventMapper.closeEvent(eventId)
                + assessmentMapper.deleteTempByEvent(eventId)
                + assessmentMapper.deleteEvent(eventId);
    }

    /**
     * 1.修改event状态为REP
     * 2.插入subscriber表
     * @param eventId
     * @return
     */
    @Override
    public int subscribeEvent(int eventId) {
        //1.修改event状态为REP
        //2.插入subscriber表
        AssessmentEvent event = assessmentEventMapper.selectByPrimaryKey(eventId);
        if (event == null)
            return 0;
        BillingCycle billingCycle = billingCycleMapper.selectByPrimaryKey(event.getBillingCycleId());
        if (billingCycle == null || ! billingCycle.getState().equals("OPN"))
            return 0;
        if (assessmentEventMapper.reportEvent(eventId)>0){
            assessmentSubscriberMapper.insertByEvent(eventId);
            assessmentMapper.reportEvent(eventId);
            billingCycle.setState("REP");
            billingCycleMapper.updateByPrimaryKey(billingCycle);
            return  1;
        }
        return 0;
    }

    @Override
    public List<Assessment> findAssessmentByZone(long zoneId) {
        return assessmentMapper.findAssessmentByZone(zoneId);
    }

    /**
     * 查询所有考核项列表，全表扫描
     * @return
     */
    @Override
    public List<Assessment> findAllAssessment() {
        return assessmentMapper.findAll();
    }

    @Override
    public Assessment getAssessment(int assessmentId) {
        return assessmentMapper.selectByPrimaryKey(assessmentId);
    }

    @Override
    public int getSequenceOfStaffAssessment() {
        return staffAssessmentMapper.getPrimaryKey();
    }

    @Override
    public List<StaffAssessment> findAssessmentById(int assessmentId) {
        return staffAssessmentMapper.findAssessmentById(assessmentId);
    }

    @Override
    public List<StaffAssessment> findStaffAssessmentByZone(long zoneId) {
        return staffAssessmentMapper.findAssessmentByZone(zoneId);
    }

    @Override
    public Integer getSequenceOfSignature() {
        return assessmentSignatureMapper.getPrimaryKey();
    }

    @Override
    public int saveSignature(AssessmentSignature signature) {
        if (assessmentSignatureMapper.endSignature(signature.getAssessmentId())>=0){
            return assessmentSignatureMapper.insert(signature);
        }
        return  0 ;
    }

    @Override
    public AssessmentSignature findSignature(int assessmentId) {
        return assessmentSignatureMapper.findSignature(assessmentId);
    }

    @Override
    public AssessmentSignature getSignature(int signatureId) {
        return assessmentSignatureMapper.selectByPrimaryKey(signatureId);
    }

    @Override
    public List<Assessment> findAssessmentByStaff(Long cssStaffNumber) {
        return assessmentMapper.findAssessmentByStaff(cssStaffNumber);
    }

    @Override
    public boolean districtHasAssessment(Long districtId, int assessmentId) {
        return assessmentMapper.existsAssessmentForDistrict(districtId,assessmentId) > 0;
    }


    @Override
    public int auditAssessment(int assessmentId, AuditNodeHelper.SuggestionType suggestion,AuditLog auditLog) {
        if (suggestion == AuditNodeHelper.SuggestionType.Agree) {
            if (assessmentMapper.auditAssessmentWithAgree(assessmentId) > 0) {
                //刷新subscriber表
                assessmentSubscriberMapper.auditAssessmentWithAgree(assessmentId);
                //
                //将绩效细表状态刷新为已审批
                staffAssessmentMapper.auditAssessmentWithState(assessmentId, AssessmentStateHelper.AssessmentNode.AUD.name());

                //记录日志
                auditLogMapper.insert(auditLog);
                return assessmentId;
            }
        }
        else if (suggestion == AuditNodeHelper.SuggestionType.Disagree){
            if (assessmentMapper.auditAssessmentWithDisagree(assessmentId) > 0) {
                //刷新subscriber表
                assessmentSubscriberMapper.auditAssessmentWithDisagree(assessmentId);
                //将员工绩效表状态刷为失效
                staffAssessmentMapper.auditAssessmentWithState(assessmentId, AssessmentStateHelper.AssessmentNode.CLS.name());
                //签收表状态刷为失效
                assessmentSignatureMapper.updateSignatureWithState(assessmentId, AssessmentStateHelper.AssessmentNode.CLS.name());

                //记录日志
                auditLogMapper.insert(auditLog);
                return  assessmentId;

            }
        }
        return 0;
    }

    @Override
    public int getSequenceOfAuditLog() {
        return auditLogMapper.getPrimatyKey();
    }

    @Override
    public List<AuditLog> findAuditLogById(int assessmentId) {
        return auditLogMapper.findAuditLogById(assessmentId);
    }

    @Override
    public List<AuditLog> findAuditLogByCssNumber(Long cssStaffNumber) {
        return auditLogMapper.findAuditLogByCssNumber(cssStaffNumber);
    }

    /**
     * 按账期统计非关闭事件有多少
     * @param billingCycleId
     * @return
     */
    @Override
    public int countEventsByCycle(int billingCycleId) {
        return assessmentEventMapper.countEventsByCycle( billingCycleId);
    }

    @Override
    public List<Assessment> findAllDistrictAssessments() {
        return assessmentMapper.findDistrictAssessments();
    }

    @Override
    public List<Assessment> findAllAreaAssessments() {
        return assessmentMapper.findAreaAssessments();
    }

    @Override
    public List<BillingCycle> findAllCycles() {
        return billingCycleMapper.findAllCycles();
    }

    @Override
    public StaffAssessment getStaffAssessment(int staffAssessId) {
        return staffAssessmentMapper.selectByPrimaryKey(staffAssessId);
    }

    @Override
    public int existsZoneAssessment(long zone, Integer assessmentId) {
        return assessmentMapper.existsZoneAssessment(zone,assessmentId);
    }

    @Override
    public int existsStaffAssessment(Long cssStaffId, Integer assessmentId) {
        return assessmentMapper.existsStaffAssessment(cssStaffId,assessmentId);
    }

    /**
     * 管理员根据账期查询员工绩效表,多表关联
     * @param cycle
     * @return
     */
    @Override
    public List<AssessmentWithDetail> findAssessmentByCycle(int cycle) {
        return assessmentMapper.findStaffAssessment(cycle);
    }

    /**
     * 管理员分页查询员工绩效表
     * @param cycle
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<AssessmentWithDetail> findAssessmentWithPageByCycle(int cycle, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return assessmentMapper.findStaffAssessment(cycle);
    }

    /**
     * 管理员分页查询 查询总记录数
     * @param cycle
     * @param startRow
     * @param pageSize
     * @return
     */
    @Override
    public int findAssessmentCountByCycle(int cycle, int startRow, int pageSize) {
        return assessmentMapper.findStaffAssessmentCount(cycle);
    }


    /**
     * 片区CEO完成考核项
     * @param assessmentId
     * @return
     */
    @Override
    public int endAssessment(int assessmentId) {
        Assessment assessment = new Assessment();
        assessment.setAssessmentId(assessmentId);
        assessment.setState(AssessmentStateHelper.AssessmentNode.END.name());
        return assessmentMapper.updateByPrimaryKeySelective(assessment);
    }

    /**
     * 分局CEO视图 待审核列表
     * @param districtId
     * @return
     */
    @Override
    public List<Assessment> findDCEOToDoAssessments(long districtId) {
        return assessmentMapper.findAssessmentForDistrictAndState(districtId,AssessmentStateHelper.AssessmentNode.FED.name());
    }

    /**
     * 分局CEO查找考核项
     * @param districtId
     * @param billingCycle
     * @return
     */
    @Override
    public List<Assessment> findAssessmentByDistrict(long districtId, int billingCycle) {
        return assessmentMapper.findAssessmentForDistrictAndCycle(districtId,billingCycle);
    }

    /**
     * 分局CEO的绩效视图
     * @param districtId
     * @param cycle
     * @return
     */
    @Override
    public List<AssessmentWithDetail> findStaffAssessmentForDistrictAndCycle(long districtId, int cycle) {
        return assessmentMapper.findStaffAssessmentByDistrict(cycle,districtId);
    }

    /**
     * 区局查看绩效视图
     * @param areaId
     * @param cycle
     * @return
     */
    @Override
    public List<AssessmentWithDetail> findStaffAssessmentForAreaAndCycle(long areaId, int cycle) {
        return assessmentMapper.findStaffAssessmentByArea(cycle,areaId);
    }

    /**
     * 区域支撑查找片区列表
     * @param areaId
     * @param billingCycle
     * @return
     */
    @Override
    public List<Assessment> findAssessmentByArea(long areaId, int billingCycle) {
        return assessmentMapper.findAssessmentForAreaAndCycle(areaId,billingCycle);
    }

    /**
     * 片区CEO验证是否存在签收表
     * @param assessmentId
     * @return
     */
    @Override
    public int existsAssessmentSignature(int assessmentId) {
        return assessmentSignatureMapper.existsSignature(assessmentId);
    }


}
