package com.telecomjs.marks;

import com.telecomjs.beans.ZoneMarkWithItem;
import com.telecomjs.services.intf.MarkService;


/**
 * Created by zark on 17/4/29.
 */
public class MarkBuilderImpl extends MarkBuilder {
    MarkService service;

    public MarkBuilderImpl(MarkService service){
        this.service = service;
    }

    @Override
    public ZoneMarkWithItem buildJF() {
        return BuildItem(MarkItemCodeEnum.JFDF);
    }

    @Override
    public ZoneMarkWithItem buildKD() {
        return BuildItem(MarkItemCodeEnum.STLDF);
    }

    @Override
    public ZoneMarkWithItem buildZW() {
        return BuildItem(MarkItemCodeEnum.ZWDF);
    }

    @Override
    public ZoneMarkWithItem buildBZ() {
        return BuildItem(MarkItemCodeEnum.JCDF);
    }

    @Override
    public ZoneMarkWithItem buildQJ() {
        return BuildItem(MarkItemCodeEnum.QYDF);
    }



    private ZoneMarkWithItem BuildItem(MarkItemCodeEnum item){
        long zoneID = (long) parameters.get("zoneId");
        int billingCycle = (int) parameters.get("billingCycle");
        return this.service.findMarkWithCode(zoneID,billingCycle,item.name());
    }
}
