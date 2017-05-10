package com.telecomjs.datagrid;

/**
 * Created by zark on 17/5/3.
 */
public enum BillingCycleStateEnum {
    INITING("正在初始化"),
    INI("初始化"),
    OPENING("正在打开"),
    OPN("已上传"),
    REP("已发布"),
    END("已结束");

    private String value;

    BillingCycleStateEnum(String value) {
        this.value = value;
    }

    public String getValue() {

        return value;
    }
}
