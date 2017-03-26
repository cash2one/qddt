package com.telecomjs.datagrid;

import java.io.Serializable;

/**
 * Created by zark on 17/3/8.
 */
public class DataColumn implements Serializable {
    public  String field;
    public  String title;
    public String align;
    public String width;

    public DataColumn(String field,String title,String align,String width){
        this.field = field;
        this.title = title;
        this.align = align;
        this.width = width;
    }

    public DataColumn(String field){
        this(field,field,"center","100");
    }

    public DataColumn(String field,String title){
        this(field,title,"center","100");
    }
}
