package com.telecomjs.marks;

import com.telecomjs.beans.ZoneMark;
import com.telecomjs.beans.ZoneMarkItem;
import com.telecomjs.beans.ZoneMarkWithItem;

import java.math.BigDecimal;

/**
 * Created by zark on 17/4/30.
 */
public class BandCalculation extends CalculationBase implements CalculationIntf  {
    public BandCalculation(ZoneMarkItem item) {
        super(item);
    }

    @Override
    public double calc(ZoneMark mark) {
        if (mark == null)
            return 0;
        double delta = mark.getVal().doubleValue() - mark.getLastVal().doubleValue();
        double deltaMark = 14+calcDelta(delta);
        mark.setMark(BigDecimal.valueOf(deltaMark));
        mark.setWeightMark(BigDecimal.valueOf(deltaMark));
        return deltaMark;
    }

    public double calcDelta(double d){
        double deltaMark = 0;
        if (d >  0 ){
            deltaMark = Math.min(Math.rint( d / 0.001 )*0.5,5);
        }
        else {
            deltaMark = Math.max(Math.rint( d / 0.001 )*0.5,-5);
        }
        return deltaMark;
    }
}
