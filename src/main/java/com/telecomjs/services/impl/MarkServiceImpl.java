package com.telecomjs.services.impl;

import com.telecomjs.beans.*;
import com.telecomjs.common.Constants;
import com.telecomjs.datagrid.AssessmentStateHelper.AssessmentNode;
import com.telecomjs.datagrid.BillingCycleStateEnum;
import com.telecomjs.datagrid.MarkHelper;
import com.telecomjs.mappers.*;
import com.telecomjs.marks.CalculationContext;
import com.telecomjs.marks.MarkDirect;
import com.telecomjs.marks.MarkItemCodeEnum;
import com.telecomjs.services.intf.MarkService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zark on 17/4/20.
 */
@Service
public class MarkServiceImpl implements MarkService {

    @Autowired
    ZoneMarkEventMapper markEventMapper;

    @Autowired
    ZoneMarkUploadMapper markUploadMapper;

    @Autowired
    ZoneMarkBaseMapper markBaseMapper;

    @Autowired
    ZoneMarkManualMapper markManualMapper;

    @Autowired
    ZoneMarkMapper markMapper;

    @Autowired
    BillingCycleMapper billingCycleMapper;

    @Autowired
    ZoneMarkItemMapper itemMapper;

    @Autowired
    ZoneKpiValueMapper kpiValueMapper;

    @Autowired
    ZoneMarkAssessmentMapper zoneMarkAssessmentMapper;

    @Autowired
    AssessmentMapper assessmentMapper;

    @Autowired
    private ApplicationContext appContext;

    /**
     * 查询事件表的主键序号
     * @return
     */
    @Override
    public long getSequenceOfMarkEvent() {
        return markEventMapper.getPrimaryKey();
    }

    /**
     * 查询指定账期下在用的事件
     * @param billingCycle
     * @return
     */
    @Override
    public int countEventsByCycle(int billingCycle) {
        return markEventMapper.findEventsByCycle(billingCycle).size();
    }

    /**
     * 将上传的结果入库
     * @param event
     * @param uploadList
     * @return
     */
    @Override
    public int saveEventAndUpload(ZoneMarkEvent event, List<ZoneMarkUpload> uploadList) {
        BillingCycle cycle = billingCycleMapper.selectByPrimaryKey(event.getBillingCycle());
        if (cycle == null || !cycle.getState().equals(BillingCycleStateEnum.INITING.name()))
            return 0;
        if (billingCycleMapper.changeState(cycle.getBillingCycleId(),BillingCycleStateEnum.INI.name())<0)
            return 0;
        if (markEventMapper.insert(event)>0) {
            for (ZoneMarkUpload upload : uploadList){
                upload.setEventId(event.getEventId());
                upload.setBillingCycle(event.getBillingCycle());
                upload.setMarkId(markUploadMapper.getPrimaryKey());
                markUploadMapper.insert(upload);
            }
            return event.getEventId().intValue();
        }
        return 0;
    }

    @Override
    public List<ZoneMarkUpload> findUploadByEvent(int eventId) {
        return markUploadMapper.findByEvent(eventId);
    }

    @Override
    public List<ZoneMarkEvent> findALlEvents() {
        return markEventMapper.findAll();
    }

    @Override
    public ZoneMarkEvent getEvent(int eventId) {
        return markEventMapper.selectByPrimaryKey((long) eventId);
    }

    @Override
    public int closeEvent(int eventId) {
        return markEventMapper.updateState(eventId, AssessmentNode.CLS.name());
    }

    /**
     * 确认上报数据，开始生成片区考核基表
     * @param eventId
     * @return
     */
    @Override
    public int commitEvent(int eventId) {
        //1.修改event状态，ini->opn

        if (markEventMapper.updateState(eventId, AssessmentNode.OPN.name())>0) {
            //2.生成base表数据
            markBaseMapper.insertByUpload(eventId);
            //3.生成manual数据
            //3.1标准化打分
            markManualMapper.insertByUpload(eventId, MarkItemCodeEnum.JCDF.name());
            //3.2区域打分
            markManualMapper.insertByUpload(eventId, MarkItemCodeEnum.QYDF.name());
            //4.计算自动部分
            return eventId;
        }
        return 0;
    }

    @Override
    public ZoneMarkWithItem findMarkWithCode(long zoneId, int billingCycle, String code) {
        return markMapper.findMarkWithCode(zoneId,billingCycle,code);
    }

    /**
     * 打开账期，从后台取数自动计算考核表
     * 从zone_mark 到 mark_assessment
     * @param billingCycle
     * @return
     */
    @Override
    public int openCycleAndCreateMark(int billingCycle) {
        if (!hasBillingCycle(billingCycle,BillingCycleStateEnum.INI.name()))
            return 0;
        //删除
        markMapper.deleteByCycle( billingCycle);
        //创建base数据
        if (billingCycleMapper.changeState(billingCycle,BillingCycleStateEnum.OPENING.name()) >0) {
            //billingCycle=201703;

            SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) appContext.getBean("sqlSessionFactory2");
            if (!sqlSessionFactory.getConfiguration().hasMapper(ZoneMarkMapper.class))
                sqlSessionFactory.getConfiguration().addMapper(ZoneMarkMapper.class);
            SqlSession session = sqlSessionFactory.openSession(false);
            ZoneMarkMapper mapper = session.getMapper(ZoneMarkMapper.class);
            List<ZoneMark> markList = new ArrayList();
            ZoneMarkItem pointItem = itemMapper.getByCode(MarkItemCodeEnum.JFDF.name());
            for (ZoneMark mark : mapper.findMDCN(201703)){
                ZoneMarkBase base = markBaseMapper.getByIdAndCycle(mark.getZoneId(),201704l);
                if (base == null)
                    continue;
                mark.setLastVal(base.getPointGoal());
                //mark.setMarkId(markMapper.getPrimaryKey());
                mark.setBillingCycle((long) billingCycle);
                mark.setItemId(pointItem.getItemId());
                CalculationContext.getMark(mark,pointItem);
                markList.add(mark);
                //markMapper.insert(mark);
            }
            markMapper.insertBatch(markList);

            markList.clear();
            ZoneMarkItem item = itemMapper.getByCode(MarkItemCodeEnum.STLDF.name());
            for (ZoneMark mark : mapper.findSTLTS(billingCycle)){
                //mark.setMarkId(markMapper.getPrimaryKey());
                mark.setBillingCycle((long) billingCycle);
                mark.setItemId(item.getItemId());
                CalculationContext.getMark(mark,item);
                markList.add(mark);
                //markMapper.insert(mark);
            } //渗透率
            markMapper.insertBatch(markList);

            markList.clear();
            ZoneMarkItem zwItem = itemMapper.getByCode(MarkItemCodeEnum.ZWDF.name());
            String key = Constants.KEY_COUNT_PER_ASSIST;//暂时硬代码写死
            String countPerAssist = kpiValueMapper.getValueWithCycle(key,billingCycle);
            for (ZoneMark mark : mapper.findZWYX(billingCycle)){
                //mark.setMarkId(markMapper.getPrimaryKey());
                mark.setBillingCycle((long) billingCycle);
                mark.setItemId(zwItem.getItemId());
                mark.setLastVal(BigDecimal.valueOf(mark.getLastVal().doubleValue()*Double.parseDouble(countPerAssist)));
                CalculationContext.getMark(mark,zwItem);
                markList.add(mark);
                //markMapper.insert(mark);
            } //装维得分
            markMapper.insertBatch(markList);

            markMapper.insertManual(billingCycle);
            zoneMarkAssessmentMapper.deleteByCycle(billingCycle);
            markMapper.insertAssessment(billingCycle);
            session.close();
            //createAssessments(billingCycle);
            return billingCycle;
        }
        else
            return 0;
    }

    /**
     * 打开账期，从INI -> OPENING
     * @param billingCycle
     * @return
     */
    /*@Override
    public int openBillingCycle(int billingCycle) {
        if (!hasBillingCycle(billingCycle,BillingCycleStateEnum.INI.name()))
            return 0;
        if (markEventMapper.countEventsByCycle(billingCycle,AssessmentNode.OPN.name()) >0){
            if (billingCycleMapper.changeState(billingCycle,BillingCycleStateEnum.OPENING.name())>0){
                //创建mark_assessment记录
                return createAssessments(billingCycle);
            }
        }
        return 0;
    }*/

    /**
     * 渠道审阅完自动打分表，确认并提交结果 OPENING-OPN
     * 从mark_assessment 到 assessment
     * @param billingCycle
     * @return
     */
    @Override
    public int commitBillingCycle(int billingCycle) {
        if (!hasBillingCycle(billingCycle,BillingCycleStateEnum.OPENING.name()))
            if ( billingCycleMapper.changeState(billingCycle,BillingCycleStateEnum.OPN.name()) > 0)
                return createAssessments(billingCycle);
        return 0;
    }

    /**
     * 通知片区CEO
     * @param billingCycle
     * @return
     */
    /*@Override
    public int notifyBillingCycle(int billingCycle) {
        if (!hasBillingCycle(billingCycle,BillingCycleStateEnum.OPENING.name()))
            if ( billingCycleMapper.changeState(billingCycle,BillingCycleStateEnum.OPN.name()) > 0)
                return createAssessments(billingCycle);
        return 0;
    }*/


    /**
     * 根据zone_mark_assessment 创建assessment表
     * @param billingCycle
     * @return
     */
    private int createAssessments(int billingCycle){
        return assessmentMapper.insertAssessmentsByCycle(billingCycle);
    }


    private boolean hasBillingCycle(int billingCycle,String preState){
        BillingCycle cycle = billingCycleMapper.selectByPrimaryKey(billingCycle);
        if (cycle == null || !cycle.getState().equals(preState)){
            return false;
        }
        return true;
    }
}
