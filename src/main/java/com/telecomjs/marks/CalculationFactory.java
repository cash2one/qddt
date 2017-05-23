package com.telecomjs.marks;

import com.telecomjs.beans.ZoneMarkItem;

/**
 * Created by zark on 17/4/30.
 */
public class CalculationFactory {
    public static CalculationIntf getCalculation(ZoneMarkItem item){
        MarkItemCodeEnum codeEnum = MarkItemCodeEnum.valueOf(item.getCode());
        switch (codeEnum){
            case JFDF: return getJfdf(item);
            case STLDF: return getStldf(item);
            case ZWDF:return getZwdf(item);
            case JCDF:return getJcdf(item);
            case QYDF:return getQydf(item);
        }
        throw new UnsupportedOperationException("类型不匹配!");
    }

    static volatile CalculationIntf jfdf;
    static volatile CalculationIntf stddf;
    static volatile CalculationIntf zwdf;
    static volatile CalculationIntf jcdf;
    static volatile CalculationIntf qydf;
    static CalculationIntf getJfdf(ZoneMarkItem item){
        if (jfdf == null)
            jfdf = new PointCalculation(item);
        return jfdf;
    }

    public static CalculationIntf getStldf(ZoneMarkItem item) {
        if (stddf == null)
            stddf = new BandCalculation(item);
        return stddf;
    }

    public static CalculationIntf getZwdf(ZoneMarkItem item) {
        if (zwdf == null)
            zwdf = new InstallationCalculation(item);
        return zwdf;
    }

    public static CalculationIntf getJcdf(ZoneMarkItem item) {
        if (jcdf == null)
            jcdf = new ScalarCalculation(item);
        return jcdf;
    }

    public static CalculationIntf getQydf(ZoneMarkItem item) {
        if (qydf == null)
            qydf = jcdf;
        return qydf;
    }
}
