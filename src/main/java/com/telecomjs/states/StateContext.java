package com.telecomjs.states;

import java.util.Map;

/**
 * Created by zark on 17/4/12.
 */
public class StateContext {
    private State state;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int handle(Map map){
        if (state != null){
            return this.state.handleState(map);
        }
        return 0;
    }
}
