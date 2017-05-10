package com.telecomjs.marks;

import com.telecomjs.beans.ZoneMark;
import com.telecomjs.beans.ZoneMarkItem;
import com.telecomjs.beans.ZoneMarkWithItem;

import java.math.BigDecimal;

/**
 * Created by zark on 17/4/30.
 */
public class ScalarCalculation extends CalculationBase  implements CalculationIntf {
    public ScalarCalculation(ZoneMarkItem item) {
        super(item);
    }

    @Override
    public double calc(ZoneMark  mark) {
        if (mark == null)
            return 0;
        mark.setWeightMark(mark.getVal());
        mark.setMark(BigDecimal.valueOf(mark.getVal().doubleValue()*100.0/getItem().getWeight().doubleValue()));
        return mark.getVal().doubleValue();
    }
}
