package com.telecomjs.marks;

import com.telecomjs.beans.ZoneMarkItem;

/**
 * Created by zark on 17/5/10.
 */
public class CalculationBase {
    private ZoneMarkItem item;

    public CalculationBase(ZoneMarkItem item) {
        this.item = item;
    }

    public ZoneMarkItem getItem() {
        return item;
    }
}
