package com.telecomjs.datagrid;

import java.io.Serializable;

/**
 * Created by zark on 17/3/8.
 */
public class DataSet implements Serializable {
    public int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Object[] rows;

    public DataSet(Object[] objects){
        this.rows = objects;
        this.total = this.rows==null?0:rows.length;
    }

}
