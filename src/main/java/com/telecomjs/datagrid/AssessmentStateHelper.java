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
        REP,//渠道通知片区CEO
        FED,//片区CEO上报
        AUD,//分局长审核
        AAU,//区域审核
        QAU,//渠道审阅
        SGN,//签名
        DRV,//分局长审阅
        ARV,//区域审阅
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

    public static enum AssessmentOperation{
        OPENING("生成考核项"),
        REPORTING("通知CEO上报"),
        FEEDBACKING("片区CEO上报"),
        AUDITING1("分局长完成所辖片区考核表一次审阅"),
        AUDITING2("区域管理层完成所辖片区考核表一次审阅"),
        AUDITING3("渠道管理员完成所有片区一次审阅，开始提报数据给第三方平台"),
        SIGNATURING("片区CEO完成签名上传"),
        REVIEWING1("分局CEO完成所辖片区二次审阅"),
        REVIEWING2("区域管理层完成所辖片区二次审阅"),
        ENDING("渠道管理员完成考核流程");

        private String operationName;


        AssessmentOperation(String name) {
            this.operationName = name;
        }

        public String getOperationName() {
            return operationName;
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

    public static AssessmentNode nextNode(AssessmentNode node){
        switch (node){
            case OPN:return AssessmentNode.REP;
            case REP:return AssessmentNode.AUD;
            case AUD:return AssessmentNode.AAU;
            case AAU:return AssessmentNode.QAU;
            case QAU:return AssessmentNode.SGN;
            case SGN:return AssessmentNode.DRV;
            case DRV:return AssessmentNode.ARV;
            case ARV:return AssessmentNode.END;
        }
        throw new UnsupportedOperationException("节点状态错误!");
    }

    public static AssessmentNode prevNode(AssessmentNode node){
        switch (node){
            case END:return AssessmentNode.ARV;
            case ARV:return AssessmentNode.DRV;
            case DRV:return AssessmentNode.SGN;
            case SGN:return AssessmentNode.QAU;
            case QAU:return AssessmentNode.AAU;
            case AAU:return AssessmentNode.AUD;
            case AUD:return AssessmentNode.FED;
            case FED:return AssessmentNode.REP;
            case REP:return AssessmentNode.OPN;

        }
        throw new UnsupportedOperationException("节点状态错误!");
    }
}
