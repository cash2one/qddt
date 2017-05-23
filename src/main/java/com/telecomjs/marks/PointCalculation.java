package com.telecomjs.marks;

import com.telecomjs.beans.ZoneMark;
import com.telecomjs.beans.ZoneMarkItem;
import com.telecomjs.beans.ZoneMarkWithItem;
import com.telecomjs.datagrid.AssessmentHelper;

import java.math.BigDecimal;

/**
 * Created by zark on 17/4/30.
 */
public class PointCalculation extends CalculationBase  implements CalculationIntf {
    public PointCalculation(ZoneMarkItem item) {
        super(item);
    }

    @Override
    public double calc(ZoneMark  mark) {
        if (mark == null)
            return 0;
        //门店产能按权重的1.2倍封顶
        double deltaMark = AssessmentHelper.equalsZero(mark.getLastVal().doubleValue()) ? 0.0 : mark.getVal().doubleValue()/mark.getLastVal().doubleValue()*100.0;
        double weightMark = Math.min(getItem().getWeight().doubleValue()*1.2, deltaMark*getItem().getWeight().doubleValue()/100.0);
        mark.setMark(BigDecimal.valueOf(deltaMark));
        mark.setWeightMark(BigDecimal.valueOf(weightMark));
        return weightMark;
    }
}
