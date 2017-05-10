package com.telecomjs.marks;

import com.telecomjs.beans.ZoneMark;
import com.telecomjs.beans.ZoneMarkItem;
import com.telecomjs.beans.ZoneMarkWithItem;

/**
 * Created by zark on 17/4/30.
 */
public class CalculationContext {

    public static double getMark(ZoneMarkWithItem mark){
        CalculationIntf calculation = CalculationFactory.getCalculation(mark.getItem());
        return calculation.calc(mark);
    }

    public static double getMark(ZoneMark mark,ZoneMarkItem item){
        CalculationIntf calculation = CalculationFactory.getCalculation(item);
        return calculation.calc(mark);
    }

}
