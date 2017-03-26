package com.telecomjs.datagrid;

import java.io.Serializable;

/**
 * Created by zark on 17/3/11.
 */
public class AreaTreeNode implements Serializable {
    public long id;
    public long pId;
    public NodeType zoneType;//0--area;1--district;2--zone;
    public String name;

    public AreaTreeNode(long id,long pId,NodeType type,String name){
        this.id = id;
        this.pId = pId;
        this.zoneType = type;
        this.name = name;
    }
    public enum NodeType{
        Area,District,Zone,None,All
    }
}
