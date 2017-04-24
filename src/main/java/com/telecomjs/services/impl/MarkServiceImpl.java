package com.telecomjs.services.impl;

import com.telecomjs.beans.ZoneMarkEvent;
import com.telecomjs.beans.ZoneMarkUpload;
import com.telecomjs.datagrid.AssessmentStateHelper.AssessmentNode;
import com.telecomjs.datagrid.MarkHelper;
import com.telecomjs.mappers.*;
import com.telecomjs.services.intf.MarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            markManualMapper.insertByUpload(eventId, MarkHelper.MarkItemCode.JCDF.name());
            //3.2区域打分
            markManualMapper.insertByUpload(eventId, MarkHelper.MarkItemCode.QYDF.name());
            //4.计算自动部分
            return eventId;
        }
        return 0;
    }
}
