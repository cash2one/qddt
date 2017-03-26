package com.telecomjs;

import com.alibaba.fastjson.JSON;
import com.telecomjs.datagrid.OperationsRoleHelper;
import com.telecomjs.utils.PoiExcelWriter;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zark on 17/3/4.
 */
public class TestOperationRoles {

    @Test()
    public void testExcel()   {
        System.out.println(JSON.toJSONString(OperationsRoleHelper.roleMap.keySet()));
        System.out.println(JSON.toJSONString(OperationsRoleHelper.roleMap.values()));
        System.out.println(JSON.toJSONString(OperationsRoleHelper.roleMap));
    }

}
