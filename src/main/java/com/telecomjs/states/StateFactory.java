package com.telecomjs.states;

import com.telecomjs.datagrid.AssessmentStateHelper;

/**
 * Created by zark on 17/4/12.
 */
public class StateFactory {
    public State createState(String state){
        State s = null;
        AssessmentStateHelper.AssessmentNode node = AssessmentStateHelper.AssessmentNode.valueOf(state);
        switch (node) {
            case QAU://渠道部完成审阅，开始第二阶段，上传签名
                s = new SignaturingState();
                break;
            case SGN://片区承包人已上传签名
                s = new DistrictReviewingState();
                break;
            case DRV://分局CEO已二次审阅签名
                s = new AreaReviewingState();
                break;
            case ARV://区域领导二次审阅签名
                s = new EndingState();
                break;
            default:
                throw new UnsupportedOperationException("未能处理的状态"+state);
        }
        return s;
    }
}
