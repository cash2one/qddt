package com.telecomjs.marks;

import com.telecomjs.beans.ZoneMark;
import com.telecomjs.beans.ZoneMarkWithItem;
import com.telecomjs.services.intf.MarkService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zark on 17/4/29.
 */
public class MarkDirect {


    MarkBuilder builder;

    MarkService service;

    public MarkDirect(MarkService service){
        this.service = service;
        this.builder = new MarkBuilderImpl(service);
    }

    public MarkBuilder getBuilder() {
        return builder;
    }

    protected MarkService getService() {
        return service;
    }

    public Map getMarkMap(long zoneId,int billingCycle){
        return this.builder.getMark(zoneId, billingCycle);
    }

    public List<ZoneMarkWithItem> getMarkList(long zoneId,int billingCycle){
        Map map = this.builder.getMark(zoneId,billingCycle);
        List<ZoneMarkWithItem>  list = new ArrayList<>();
        list.addAll(map.values());
        return list;
    }
}
