package com.telecomjs.datagrid;

import com.alibaba.fastjson.JSON;

import java.util.EnumMap;

/**
 * Created by zark on 17/5/4.
 */
public class BillingCycleHelper {
    public static EnumMap<BillingCycleStateEnum,String> stateMap = new EnumMap(BillingCycleStateEnum.class);
    static {
        stateMap.put(BillingCycleStateEnum.INI,BillingCycleStateEnum.INI.getValue());
        stateMap.put(BillingCycleStateEnum.OPN,BillingCycleStateEnum.OPN.getValue());
        stateMap.put(BillingCycleStateEnum.REP,BillingCycleStateEnum.REP.getValue());
        stateMap.put(BillingCycleStateEnum.END,BillingCycleStateEnum.END.getValue());
    }
    public static String getStates(){ return JSON.toJSONString(stateMap);}

    public static boolean parseBillingCycle(int billingCycle){
        int year = 0;
        int month = 0;
        year = billingCycle / 100;
        month = billingCycle % 100;
        boolean f = true;
        if (year < 2017 || year > 2020){
            f = false;
        }
        if (month < 1 || month > 12)
            f = false;
        return f;
    }
}
