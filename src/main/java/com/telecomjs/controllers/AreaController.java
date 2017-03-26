package com.telecomjs.controllers;

import com.alibaba.fastjson.JSON;
import com.sun.deploy.util.SystemUtils;
import com.telecomjs.beans.AppYdbpArea;
import com.telecomjs.beans.AppYdbpAreaChannel;
import com.telecomjs.datagrid.AreaTreeNode;
import com.telecomjs.datagrid.AreaZoneHelper;
import com.telecomjs.mappers.AppYdbpAreaChannelMapper;
import com.telecomjs.mappers.AppYdbpAreaMapper;
import com.telecomjs.mappers.AppYdbpAreaZwstaffMapper;
import com.telecomjs.services.intf.AreaService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zark on 17/3/11.
 */
@RequestMapping("/area")
@Controller
public class AreaController extends BaseController {
    @Autowired
    AreaService areaService;

    @RequestMapping(value = "/doqueryzonenode",method = RequestMethod.GET)
    @ResponseBody
    public void doAddUser(HttpServletResponse response) throws IOException {
        Subject subject = SecurityUtils.getSubject();
        List<AppYdbpArea> list =new ArrayList<>();
        /*if (subject.isAuthenticated())
            list = areaService.findAllArea();*/
        list = areaService.findAll();
        AreaTreeNode[] nodes = AreaZoneHelper.transform2TreeNode(list);
        response.setHeader("Cache-Control","no-cache");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.write(JSON.toJSONString(nodes));
    }
}
