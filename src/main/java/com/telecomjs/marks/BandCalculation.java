package com.telecomjs.marks;

import com.telecomjs.beans.ZoneMark;
import com.telecomjs.beans.ZoneMarkItem;
import com.telecomjs.beans.ZoneMarkWithItem;
import com.telecomjs.datagrid.AssessmentHelper;

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
        if (mark.getVal() == null || mark.getLastVal() == null)
            return 0;
        double delta = mark.getVal().doubleValue() - mark.getLastVal().doubleValue();
        BigDecimal d = mark.getVal().subtract(mark.getLastVal()).multiply(BigDecimal.valueOf(1000.0)).setScale(0,BigDecimal.ROUND_DOWN);
        double doubleMark = 0.0;
        if (AssessmentHelper.equalsZero(d.doubleValue()))
            doubleMark = 14.0;
        else if (d.doubleValue() > 0)
            doubleMark = Math.min(20.0,d.multiply(BigDecimal.valueOf(0.5)).add(BigDecimal.valueOf(15)).doubleValue());
        else if (d.doubleValue() < 0)
            doubleMark = Math.max(10.0,d.multiply(BigDecimal.valueOf(0.5)).add(BigDecimal.valueOf(15)).doubleValue());
        mark.setMark(BigDecimal.valueOf(doubleMark));
        mark.setWeightMark(BigDecimal.valueOf(doubleMark));
        return doubleMark;
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
