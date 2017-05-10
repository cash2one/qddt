package com.telecomjs.marks;

import com.telecomjs.beans.ZoneMarkWithItem;
import com.telecomjs.services.intf.MarkService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zark on 17/4/29.
 */
public abstract class MarkBuilder {
    protected Map parameters;
    public abstract ZoneMarkWithItem buildJF();
    public abstract ZoneMarkWithItem buildKD();
    public abstract ZoneMarkWithItem buildZW();
    public abstract ZoneMarkWithItem buildBZ();
    public abstract ZoneMarkWithItem buildQJ();

    public Map getMark(long zoneId,int billingCycle){
        parameters = new HashMap();
        parameters.put("zoneID",zoneId);
        parameters.put("billingCycle",billingCycle);
        Map map = new HashMap();
        map.put(MarkItemCodeEnum.JFDF.name(),buildJF());
        map.put(MarkItemCodeEnum.STLDF.name(),buildKD());
        map.put(MarkItemCodeEnum.ZWDF.name(),buildZW());
        map.put(MarkItemCodeEnum.JCDF.name(),buildBZ());
        map.put(MarkItemCodeEnum.QYDF.name(),buildQJ());

        /*buildJF();
        buildKD();
        buildZW();
        buildBZ();
        buildQJ();*/
        return map;
    }
}
