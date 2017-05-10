package com.telecomjs.marks;

import com.telecomjs.beans.ZoneMark;
import com.telecomjs.beans.ZoneMarkItem;
import com.telecomjs.beans.ZoneMarkWithItem;
import com.telecomjs.datagrid.AssessmentHelper;

import java.math.BigDecimal;

/**
 * Created by zark on 17/4/30.
 */
public class InstallationCalculation extends CalculationBase  implements CalculationIntf {
    public InstallationCalculation(ZoneMarkItem item) {
        super(item);
    }

    @Override
    public double calc(ZoneMark mark) {
        if (mark == null)
            return 0;

        if (AssessmentHelper.equalsZero(mark.getLastVal().doubleValue())){
            return 0;
        }
        double markValue ;
        markValue = mark.getVal().doubleValue()/mark.getLastVal().doubleValue()*getItem().getWeight().doubleValue();
        mark.setWeightMark(BigDecimal.valueOf(markValue));
        mark.setMark(BigDecimal.valueOf(mark.getVal().doubleValue()/mark.getLastVal().doubleValue()*100.0));
        return markValue;
    }
}
