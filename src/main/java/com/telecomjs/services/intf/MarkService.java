package com.telecomjs.services.intf;

import com.telecomjs.beans.ZoneMarkEvent;
import com.telecomjs.beans.ZoneMarkUpload;

import java.util.List;

/**
 * Created by zark on 17/4/20.
 */
public interface MarkService {
    public long getSequenceOfMarkEvent();
    public int countEventsByCycle(int billingCycle );

    /**
     * 事件相关的方法
     */
    //上传考核基本数据
    public int saveEventAndUpload(ZoneMarkEvent event, List<ZoneMarkUpload> uploadList);
    //根据event查询清单
    List<ZoneMarkUpload> findUploadByEvent(int eventId);
    //查询所有上报列表
    List<ZoneMarkEvent> findALlEvents();
    //根据主键查找event
    ZoneMarkEvent getEvent(int eventId);
    //关闭上报
    int closeEvent(int eventId);
    //开始计算片区的绩效
    int commitEvent(int eventId);
}