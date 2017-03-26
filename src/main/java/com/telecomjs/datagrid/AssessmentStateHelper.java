package com.telecomjs.datagrid;

import com.alibaba.fastjson.JSON;

import java.util.EnumMap;

/**
 * Created by zark on 17/3/16.
 */
public class AssessmentStateHelper {
    public static enum AssessmentNode{
        INI,
        OPN,
        REP,
        FED,
        AUD,
        END,
        CLS
    }

    public static enum UseOrNotState{
        USE("在用"),
        INU("不再用");

        private String state;
        private UseOrNotState(String state){
            this.state = state;
        }

        public String getState(){
            return this.state;
        }
    }

    public static EnumMap<UseOrNotState,String> useOrNotStateStringEnumMap = new EnumMap(UseOrNotState.class);
    static {
        useOrNotStateStringEnumMap.put(UseOrNotState.USE,UseOrNotState.USE.getState());
        useOrNotStateStringEnumMap.put(UseOrNotState.INU,UseOrNotState.INU.getState());
    }

    public static String getUseOrNotState(){
        return JSON.toJSONString(useOrNotStateStringEnumMap);
    }
}
