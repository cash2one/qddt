package com.telecomjs.datagrid;

import com.telecomjs.beans.AppYdbpArea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zark on 17/3/11.
 */
public class AreaZoneHelper {
    public static AreaTreeNode[] transform2TreeNode(List<AppYdbpArea> list){
        if (list == null || list.size()==0)
            return new AreaTreeNode[0];
        Map areaMap = new HashMap();
        Map districtMap = new HashMap();
        Map zoneMap = new HashMap();
        //List nodex = new ArrayList<>();

        for (AppYdbpArea zone : list){
            //分别创建三个对象 area district zone
            if (!areaMap.containsKey(zone.getAreaId())) {
                AreaTreeNode area = new AreaTreeNode(zone.getAreaId(), 0
                        , AreaTreeNode.NodeType.Area, zone.getAreaName());
                areaMap.put(zone.getAreaId(),area);
            }
            if (!districtMap.containsKey(zone.getAreaIdL2())){
                AreaTreeNode district = new AreaTreeNode(zone.getAreaIdL2(),zone.getAreaId()
                        , AreaTreeNode.NodeType.District,zone.getAreaNameL2());
                districtMap.put(zone.getAreaIdL2(),district);
            }
            AreaTreeNode zoneNode = new AreaTreeNode(zone.getAreaIdTm(),zone.getAreaIdL2()
                    , AreaTreeNode.NodeType.Zone,zone.getAreaNameTm());
            zoneMap.put(zone.getAreaIdTm(),zoneNode);

        }
        //添加一个根节点,构成一个森林结构
        areaMap.put(new Integer(0),new AreaTreeNode(0,-1, AreaTreeNode.NodeType.All,"南京"));
        areaMap.put(new Integer(-100),new AreaTreeNode(-100,0, AreaTreeNode.NodeType.None,"【未认领】"));
        AreaTreeNode[] nodes = new AreaTreeNode[areaMap.size()+districtMap.size()+zoneMap.size()];
        System.arraycopy(areaMap.values().toArray(),0,nodes,0,areaMap.size());
        System.arraycopy(districtMap.values().toArray(),0,nodes,areaMap.size(),districtMap.size());
        System.arraycopy(zoneMap.values().toArray(),0,nodes,areaMap.size()+districtMap.size(),zoneMap.size());
        return nodes;
    }
}
