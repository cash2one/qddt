package com.telecomjs.datagrid;

import com.alibaba.fastjson.JSON;

import java.util.EnumMap;

/**
 * Created by zark on 17/3/21.
 */
public class OperationsRoleHelper {

    public static EnumMap<RoleType,String> roleMap = new EnumMap(RoleType.class);
    public static enum RoleType{
        CEO("CEO"),
        Engineer("工程师"),
        Assistant("营销员");

        private String name ;

        private RoleType(String name){
            this.name = name;
        }

        public String getName(){return  this.name;}
    }

    static {
        roleMap.put(RoleType.CEO,RoleType.CEO.getName());
        roleMap.put(RoleType.Engineer,RoleType.Engineer.getName());
        roleMap.put(RoleType.Assistant,RoleType.Assistant.getName());
    }

    public static String getRoles(){
        return JSON.toJSONString(roleMap);
    }

    public static RoleType roleNameOf(String name){
        for (RoleType type :roleMap.keySet()){
            if (roleMap.get(type).equals(name))
                return type;
        }
        return null;
    }
}
