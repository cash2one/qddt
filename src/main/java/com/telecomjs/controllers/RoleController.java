package com.telecomjs.controllers;

import com.sun.xml.internal.rngom.parse.host.Base;
import com.telecomjs.mappers.RoleMapper;
import com.telecomjs.services.intf.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.telecomjs.beans.Role;

import java.util.List;

/**
 * Created by zark on 17/3/10.
 */
@RequestMapping("/role")
@Service
public class RoleController extends BaseController {
    @Autowired
    UserService userService;

    @RequestMapping("/rolelist")
    public ModelAndView roles(){
        ModelAndView mv = new ModelAndView("rolelist");
        List<Role> roles =   userService.findAllRoles();
        mv.getModel().put("roles",roles);
        //mv.getModel().put("message","hello");//for test
        return mv;
    }
}
