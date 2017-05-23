package com.telecomjs.services.impl;

import com.github.pagehelper.PageHelper;
import com.telecomjs.beans.*;
import com.telecomjs.datagrid.AssessmentHelper;
import com.telecomjs.datagrid.AssessmentStateHelper;
import com.telecomjs.datagrid.AuditNodeHelper;
import com.telecomjs.datagrid.BillingCycleStateEnum;
import com.telecomjs.mappers.*;
import com.telecomjs.services.intf.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.telecomjs.datagrid.AssessmentStateHelper.AssessmentNode;
import com.telecomjs.datagrid.AssessmentStateHelper.AssessmentOperation;
import com.telecomjs.datagrid.AuditNodeHelper.SuggestionType;

import java.math.BigDecimal;
import java.util.Date;
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
    @Autowired
    AssessmentLogMapper assessmentLogMapper;
    @Autowired
    AppYdbpAreaMapper appYdbpAreaMapper;

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
    public List<Assessment> findAllDistrictAssessments(long areaId, int billingCycle) {
        return assessmentMapper.findDistrictAssessmentsByAreaAndCycle(areaId,billingCycle);
    }

    @Override
    public List<Assessment> findAllAreaAssessments() {
        return assessmentMapper.findAreaAssessments();
    }

    /**
     * 根据账期查询每个账期的信息
     * @param billingCycle
     * @return
     */
    @Override
    public List<Assessment> findAllAreaAssessments(int billingCycle) {
        return assessmentMapper.findAreaAssessmentsByCycle(billingCycle);
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

    /**
     * 分局长对单个片区审核审阅
     * @param assessments
     * @param fromState
     * @param toState
     * @param operation
     * @param auditLog
     * @return
     */
    private int auditAllWithOperation(List<Assessment> assessments,
                                              String fromState,String toState,
                                              String operation,AuditLog auditLog){
        if (assessments == null)
            return 0;
        int count = 0;
        for (Assessment assessment : assessments){
            if (assessment.getState().equals(fromState)) {
                count++;
                auditWithOperation(assessment.getAssessmentId(), fromState, toState, operation, auditLog);
            }
        }
        return count;
    }


    /**
     * 内部方法，对单个片区原子操作
     * @param assessmentId
     * @param fromState
     * @param toState
     * @param operation
     * @param log
     * @return
     */
    private int auditWithOperation(int assessmentId,
                                   String fromState,String toState,
                                   String operation,AuditLog log){
        Assessment assessment = assessmentMapper.selectByPrimaryKey(assessmentId);
        if (assessment.getState().equals(fromState)) {
            //update assessment
            assessmentMapper.updateByIdAndState(assessment.getAssessmentId(), toState);
            //Insert AuditLog
            log.setState(assessment.getState());
            log.setAssessmentId(assessment.getAssessmentId());
            log.setAuditId(auditLogMapper.getPrimatyKey());
            log.setStateDate(new Date());
            auditLogMapper.insert(log);
            //Insert assessment_log
            AssessmentLog assessmentLog = AssessmentHelper.makeAssessmentLog(
                    assessmentLogMapper.getPrimaryKey(),
                    assessment.getAssessmentId(),
                    operation,
                    log.getNodeStaff(),
                    log.getStaffName(),
                    fromState,
                    toState,
                    new Date());
            assessmentLogMapper.insert(assessmentLog);
            //第一次审阅 退回状态 要失效掉以前的上报表

            //第二次审阅 上传的签收表无效

            //提报给第三方平台后，固话员工上报表

            //全部完成后，签收表固化

            return assessmentId;
        }
        return 0;
    }


    /**
     * 区域领导对本区的所有片区进行审阅
     * @param billingCycle
     * @param areaId
     * @param auditLog
     * @return
     */
    @Override
    public int auditAssessmentByArea(int billingCycle, int areaId, AuditLog auditLog) {
        List<Assessment> assessments = assessmentMapper.findAssessmentForAreaAndCycle(areaId,billingCycle);
        return auditAllWithOperation(assessments,
                AssessmentNode.AUD.name(),AssessmentNode.AAU.name(),
                AssessmentOperation.AUDITING2.getOperationName(),auditLog);
    }

    @Override
    public int unauditAssessmentByDistrict(int billingCycle, int districtId, AuditLog auditLog) {
        List<Assessment> assessments = assessmentMapper.findAssessmentForDistrictAndCycle(districtId,billingCycle);
        return auditAllWithOperation(assessments,
                AssessmentNode.AUD.name(),AssessmentNode.FED.name(),
                AssessmentOperation.AUDITING2.getOperationName(),auditLog);
    }

    /**
     * 渠道完成相应账期的所有片区审核
     * @param billingCycle
     * @param auditLog
     * @return
     */
    @Override
    public int auditAllAssessment(int billingCycle, AuditLog auditLog) {
        List<Assessment> assessments = assessmentMapper.findAssessmentForCycle(billingCycle);
        return auditAllWithOperation(assessments,
                AssessmentNode.AAU.name(),AssessmentNode.QAU.name(),
                AssessmentOperation.AUDITING3.getOperationName(),auditLog);
    }


    /**
     * 区域完成第二次审阅
     * @param billingCycle
     * @param areaId
     * @param auditLog
     * @return
     */
    @Override
    public int commitAssessmentByArea(int billingCycle, int areaId, AuditLog auditLog) {
        List<Assessment> assessments = assessmentMapper.findAssessmentForAreaAndCycle(areaId,billingCycle);
        return auditAllWithOperation(assessments,
                AssessmentNode.DRV.name(),AssessmentNode.ARV.name(),
                AssessmentOperation.REVIEWING2.getOperationName(),auditLog);
    }

    /**
     * 渠道完成相应账期的所有片区审核(第二次)
     * @param billingCycle
     * @param auditLog
     * @return
     */
    @Override
    public int commitAllAssessment(int billingCycle, AuditLog auditLog) {
        List<Assessment> assessments = assessmentMapper.findAssessmentForCycle(billingCycle);
        return auditAllWithOperation(assessments,
                AssessmentNode.ARV.name(),AssessmentNode.END.name(),
                AssessmentOperation.ENDING.getOperationName(),auditLog);
    }


    /**
     * 分局长审阅片区考核表
     * @param assessmentId
     * @param suggestion
     * @param auditLog
     * @return
     */
    @Override
    public int auditAssessment(int assessmentId, AuditNodeHelper.SuggestionType suggestion,AuditLog auditLog) {
        if (suggestion == AuditNodeHelper.SuggestionType.Agree) {
            if (assessmentMapper.auditAssessmentWithAgree(assessmentId) > 0) {
                //刷新subscriber表
                assessmentSubscriberMapper.changeState(assessmentId,AssessmentNode.FED.name(),AssessmentNode.AUD.name());

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
                assessmentSubscriberMapper.changeState(assessmentId,AssessmentNode.FED.name(),AssessmentNode.REP.name());
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

    /**
     * 分局长第二次审阅签收表 同意 片区范围
     * @param assessmentId
     * @param auditLog
     * @return
     */
    @Override
    public int commitAssessment(int assessmentId, AuditLog auditLog) {
        int ret = auditWithOperation(assessmentId,
                AssessmentNode.SGN.name(),AssessmentNode.DRV.name(),
                AssessmentOperation.REVIEWING1.getOperationName(),auditLog);
        //刷新通知表
        assessmentSubscriberMapper.changeState(assessmentId,AssessmentNode.SGN.name(),AssessmentNode.DRV.name());
        //将绩效细表状态刷新为已审批
        staffAssessmentMapper.auditAssessmentWithState(assessmentId, AssessmentNode.DRV.name());
        //签收表状态刷为失效
        assessmentSignatureMapper.updateSignatureWithState(assessmentId, AssessmentNode.DRV.name());
        return ret;
    }

    /**
     * 分局长第二次审阅签收表 不同意 片区范围
     * @param assessmentId
     * @param auditLog
     * @return
     */
    @Override
    public int uncommitAssessment(int assessmentId, AuditLog auditLog) {
        int ret = auditWithOperation(assessmentId,
                AssessmentNode.SGN.name(),AssessmentNode.QAU.name(),
                AssessmentOperation.REVIEWING1.getOperationName(),auditLog);
        //刷新通知表
        assessmentSubscriberMapper.changeState(assessmentId,AssessmentNode.SGN.name(),AssessmentNode.QAU.name());
        //将绩效细表状态刷新为已审批
        staffAssessmentMapper.auditAssessmentWithState(assessmentId, AssessmentNode.CLS.name());
        //签收表状态刷为失效
        assessmentSignatureMapper.updateSignatureWithState(assessmentId, AssessmentNode.CLS.name());
        return  ret;
    }

    @Override
    public int addBillingCycle(int billingCycle) {
        BillingCycle cycle = new BillingCycle();
        cycle.setBillingCycleId(billingCycle);
        cycle.setState(BillingCycleStateEnum.INITING.name());
        return billingCycleMapper.insert(cycle);
    }

    @Override
    public int notifyBillingCycle(int billingCycle) {
        if (!hasBillingCycle(billingCycle,BillingCycleStateEnum.OPN.name()))
            if ( billingCycleMapper.changeState(billingCycle,BillingCycleStateEnum.REP.name()) > 0){
                assessmentMapper.insertByCycle(billingCycle);
                assessmentMapper.changeState(billingCycle,AssessmentNode.REP.name());
                return  1;
            }
        return 0;
    }

    @Override
    public boolean areaHasAllDistricts(Long aLong, List<Long> districtIds) {
        return true;
    }

    @Override
    public int auditDistricts(int billingCycle,List<Long> districtIds, SuggestionType suggestionType, AuditLog auditLog, AssessmentNode node) {
        AssessmentNode newNode = suggestionType==SuggestionType.Agree
                ?AssessmentStateHelper.nextNode(node)
                :AssessmentStateHelper.prevNode(node);
        assessmentMapper.changeStateFromTo(billingCycle,districtIds,node.name(),newNode.name());
        auditLogMapper.insertByDistricts(billingCycle,districtIds,auditLog);
        return districtIds.size();
    }

    @Override
    public int reAuditAssessment(int assessmentId, SuggestionType suggestionType, String remark,AssessmentNode node,Staff staff) {
        Assessment assessment = assessmentMapper.selectByPrimaryKey(assessmentId);
        AssessmentNode nextNode = suggestionType==SuggestionType.Agree
                ?AssessmentStateHelper.nextNode(node)
                :AssessmentStateHelper.prevNode(node);
        if (assessment != null){
            //刷新subscriber表
            assessmentSubscriberMapper.changeState(assessmentId,node.name(),nextNode.name());
            String suggestionRemark = remark==null?suggestionType.getSuggestion():remark;
            AuditLog auditLog = AuditNodeHelper.makeAuditLog(getSequenceOfAuditLog(),
                    assessment.getAssessmentId(),
                    suggestionRemark,
                    suggestionType.getSuggestion(),
                    assessment.getAreaName(),
                    assessment.getDistrictName(),
                    assessment.getZoneName(),
                    Long.valueOf(staff.getCssStaffNumber()),
                    staff.getName());
            //记录日志
            auditLogMapper.insert(auditLog);
            //更新assessement状态
            assessmentMapper.updateByIdAndState(assessmentId,nextNode.name());
            return assessmentId;
        }
        return 0;
    }

    @Override
    public int auditAll(int billingCycle, SuggestionType suggestionType, String remark,  AssessmentNode node, Staff staff) {
        AssessmentNode newNode = suggestionType==SuggestionType.Agree
                ?AssessmentStateHelper.nextNode(node)
                :AssessmentStateHelper.prevNode(node);

        String suggestionRemark = remark==null?suggestionType.getSuggestion():remark;
        AuditLog auditLog = AuditNodeHelper.makeAuditLog(0,
                0,
                suggestionRemark,
                suggestionType.getSuggestion(),
                null,
                null,
                null,
                Long.valueOf(staff.getCssStaffNumber()),
                staff.getName());
        assessmentMapper.changeAllState(billingCycle,node.name(),newNode.name());
        auditLogMapper.insertAllByCycle(billingCycle,auditLog);
        return 0;
    }

    @Override
    public long getDistrictIdByName(String name) {
        return appYdbpAreaMapper.getDistrictId(name);
    }

    @Override
    public long getAreaIdByName(String name) {
        return appYdbpAreaMapper.getAreaId(name);
    }


    private boolean hasBillingCycle(int billingCycle,String preState){
        BillingCycle cycle = billingCycleMapper.selectByPrimaryKey(billingCycle);
        if (cycle == null || !cycle.getState().equals(preState)){
            return false;
        }
        return true;
    }

}
