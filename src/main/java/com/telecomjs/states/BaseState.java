package com.telecomjs.states;

import com.telecomjs.beans.Assessment;
import com.telecomjs.services.intf.AssessmentService;

import java.util.Map;

/**
 * Created by zark on 17/4/12.
 */
public abstract class BaseState implements State {

    public abstract int doService(Map map);
    private AssessmentService service;

    @Override
    public int handleState(Map map) {
        Object obj = map.get("service");
        if (obj instanceof AssessmentService){
            this.service = (AssessmentService) obj;
        }
        return doService(map);
    }
}
