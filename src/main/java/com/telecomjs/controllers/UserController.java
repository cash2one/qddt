package com.telecomjs.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.telecomjs.beans.AppYdbpAreaZwstaff;
import com.telecomjs.beans.Staff;
import com.telecomjs.common.Constants;
import com.telecomjs.datagrid.AreaTreeNode;
import com.telecomjs.datagrid.AssessmentStateHelper;
import com.telecomjs.datagrid.UserHelper;
import com.telecomjs.services.intf.AreaService;
import com.telecomjs.services.intf.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

/**
 * Created by zark on 17/3/2.
 *
 * 提供的方法有：
 * + 增加用户
 * + 删除用户（安全删除，实际是改状态）
 * + 更新用户（修改用户的号码、身份证、姓名信息、角色）
 * + 锁定用户 (将状态置为LOCKED,其他不变)
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    AreaService areaService;
    @Autowired
    UserService userService;
    @Autowired
    DefaultPasswordService defaultPasswordService;


    /**
     * 登录方法，根据登录账号的角色重定向到不同的视图
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/dologin")
    public ModelAndView login(@RequestParam("username") String username,@RequestParam("password") String password ){
        Staff staff = userService.findStaff(username);
        if (staff != null) {
            StringBuffer stringBuffer=new StringBuffer();
            UsernamePasswordToken token = new UsernamePasswordToken(username,password,true);
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.login(token);
                if (subject.isAuthenticated()) {
                    if (subject.hasRole(UserHelper.RoleType.ADMIN.name()) || subject.hasRole(UserHelper.RoleType.QDADMIN.name()))
                        return new ModelAndView("redirect:/index");
                    else if (subject.hasRole(UserHelper.RoleType.ZCEO.name()))
                        return new ModelAndView("redirect:/ceoindex");
                    else if (subject.hasRole(UserHelper.RoleType.DCEO.name()))
                        return new ModelAndView("redirect:/dceoindex");
                    else if (subject.hasRole(UserHelper.RoleType.QADMIN.name()))
                        return new ModelAndView("redirect:/areaindex");
                    else
                        return new ModelAndView("redirect:error");
                } else {
                    return  new ModelAndView("redirect:/login");
                }
            } catch (IncorrectCredentialsException e) {
                stringBuffer.append(  "登录密码错误. Password for account " + token.getPrincipal() + " was incorrect.");

            } catch (ExcessiveAttemptsException e) {
                stringBuffer.append( "登录失败次数过多");
            } catch (LockedAccountException e) {
                stringBuffer.append( "帐号已被锁定. The account for username " + token.getPrincipal() + " was locked.");

            } catch (DisabledAccountException e) {
                stringBuffer.append("帐号已被禁用. The account for username " + token.getPrincipal() + " was disabled.");

            } catch (ExpiredCredentialsException e) {
                stringBuffer.append("帐号已过期. the account for username " + token.getPrincipal() + "  was expired.");

            } catch (UnknownAccountException e) {
                stringBuffer.append( "帐号不存在. There is no user with username of " + token.getPrincipal());

            } catch (UnauthorizedException e) {
                stringBuffer.append("您没有得到相应的授权！" + e.getMessage());
            }
            ModelAndView mv = new ModelAndView("redirect:/login");
            mv.getModel().put("message",stringBuffer.toString());
            return mv;
        }
        else {
            ModelAndView mv = new ModelAndView("redirect:/login");
            mv.getModel().put("message","用户名不存在!");
            return mv;
        }
    }

    @RequestMapping("/dologout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        if (subject != null){
            subject.logout();
        }
        return "redirect:/login.html";
    }

    @RequestMapping("/adduser")
    public ModelAndView addUser(){
        ModelAndView mav = new ModelAndView("adduser");
        mav.getModel().put("message","欢迎您使用!");
        return  mav;
    }

    @RequestMapping(value = "/doadduser",method = RequestMethod.POST)
    @ResponseBody
    public void doAddUser(@RequestParam("mobile") String mobile
            , @RequestParam("password") String password
            /*,@RequestParam("password")String password2*/
            , @RequestParam("username") String name
            , @RequestParam("cssNumber") String cssNumber
            , @RequestParam("identifiedCode") String idCode
            , @RequestParam("status") String status
            , @RequestParam("role") short roleId
            , HttpServletResponse response) throws IOException {

        try {
            StringBuffer stringBuffer = new StringBuffer();
            DefaultPasswordService passwordService = new DefaultPasswordService();
            Staff staff = new Staff();

            long uid = System.currentTimeMillis() / 1000;
            //password salt : CEO
            String newPassword = new Md5Hash(password, Constants.PASSWORD_SALT, 2).toString();
            //String newPassword = passwordService.encryptPassword(password + salt);
            staff.setPassword(newPassword);
            staff.setCssStaffNumber(cssNumber);
            staff.setIdentifiedcode(idCode);
            staff.setCreateDate(new Date());
            staff.setMobile(mobile);
            staff.setName(name);
            staff.setRoleId(roleId);
            staff.setState(status);
            staff.setStateDate(new Date());
            staff.setCssStaffNumber(cssNumber);
            staff.setStaffId(userService.getPrimaryKey());
            /*
             * 根据bss工号获取css工号
             *
             */
            /*if (!areaService.checkCssNumber(cssNumber)){
                stringBuffer.append("用户的CSS工号不存在！添加用户失败!");
                return stringBuffer.toString();
            }*/

            if (userService.addStaff(staff)) {
                stringBuffer.append("增加用户成功!success");
            } else {
                stringBuffer.append("添加用户失败!failed");
            }
            response.setHeader("Cache-Control","no-cache");
            response.setContentType("text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.write(JSON.toJSONString(stringBuffer.toString()));
        } catch (Exception e){
            e.printStackTrace();
            response.setHeader("Cache-Control","no-cache");
            response.setContentType("text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.write(JSON.toJSONString("后台处理发生异常错误，添加用户失败! !"));
        }
    }

    @RequestMapping(value = "/dofindareauser",method = RequestMethod.GET)
    @ResponseBody
    public void doFindAreaUser(@RequestParam("id") long id
            , @RequestParam("type") String type
            , HttpServletResponse response) throws IOException{
        List<Staff> list;
        if (id == 0)
            list = userService.findAreaStaff(id, AreaTreeNode.NodeType.All);
        else
            list = userService.findAreaStaff(id, AreaTreeNode.NodeType.valueOf (type));

        response.setHeader("Cache-Control","no-cache");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.write(JSON.toJSONString(list,SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty
                ,SerializerFeature.WriteNullListAsEmpty,SerializerFeature.WriteDateUseDateFormat));
    }

    @ResponseBody
    @RequestMapping(value = "/dodelete",method = RequestMethod.POST)
    public void doDeleteUser(@RequestParam("staffId") int staffId, HttpServletResponse response) throws IOException {
        Staff staff = userService.getStaff(staffId);
        response.setHeader("Cache-Control","no-cache");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        if (staff != null ) {
            if (userService.deleteStaff(staffId) >0) {
                out.write("注销工号成功!");
                return;
            }
        }
        out.write("工号已失效!");
    }

    @RequestMapping(value = "/dochangepassword",method = RequestMethod.POST)
    public void doChangePassword(@RequestParam("oldpassword") String oldPassword
                    ,@RequestParam("newpassword") String newPassword
                    ,@RequestParam("newpassword2") String newPassword2
                    , HttpServletResponse response) throws IOException {
        Subject subject = SecurityUtils.getSubject();
        Staff staff = userService.findStaff((String) subject.getPrincipal());
        response.setHeader("Cache-Control","no-cache");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {
            if (!newPassword2.equals(newPassword)){
                out.write("修改失败!");
                return;
            }
            String oldEncodedPassword = new Md5Hash(oldPassword, Constants.PASSWORD_SALT, 2).toString();
            if (oldEncodedPassword.equals(staff.getPassword())){
                String newEncodedPassword = new Md5Hash(newPassword, Constants.PASSWORD_SALT, 2).toString();
                Staff newStaff = new Staff();
                newStaff.setStaffId( staff.getStaffId());
                newStaff.setPassword(newEncodedPassword);
                newStaff.setStateDate(new Date());
                if (userService.updateStaff(newStaff) > 0){
                    out.write("修改成功!");
                    return;
                }
            }
            out.write("修改失败!");
            return;
        }
        catch (Exception e){
            e.printStackTrace();
            out.write("修改密码失败!");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/doupdate",method = RequestMethod.POST)
    public void doUpdateUser(@RequestParam("mobile") String mobile
            /*,@RequestParam("name") String name*/
            ,@RequestParam("cssNumber") String cssNumber
            ,@RequestParam("bssNumber") String bssNumber
            ,@RequestParam("identifiedCode") String idCode
            ,@RequestParam("roleId") short roleId
            ,@RequestParam("staffId") int staffId
            , HttpServletResponse response) throws IOException {
        //Subject subject = SecurityUtils.getSubject();
        Staff staff = userService.getStaff(staffId);
        response.setHeader("Cache-Control","no-cache");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        if (staff != null ){
            staff.setStateDate(new Date());
            staff.setMobile(mobile);
            staff.setCssStaffNumber(cssNumber);
            staff.setBssStaffNumber(bssNumber);
            staff.setIdentifiedcode(idCode);
            //staff.setRoleId(roleId);
            if (userService.updateStaff(staff)>0){
                out.write("修改工号成功!");
                return;
            }
        }
        out.write("修改工号失败!");

    }

    @ResponseBody
    @RequestMapping(value = "/dolock",method = RequestMethod.POST)
    public void doLockUser(@RequestParam("staffid") int staffId, HttpServletResponse response) throws IOException {
        Subject subject = SecurityUtils.getSubject();
        String username = (String)subject.getPrincipal();

        Staff staff = userService.getStaff(staffId);
        response.setHeader("Cache-Control","no-cache");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.write("锁定工号成功!");

    }


    @RequestMapping("userlist")
    public ModelAndView userlist(){
        ModelAndView mv = new ModelAndView("userlist");
        mv.getModel().put("roles",userService.findAllRoles());
        return mv;
    }

    /**
     * 片区CEO查看员工列表
     * @return
     */
    @RequestMapping("myzonestaff")
    public ModelAndView zoneStaff(){
        try {
            Subject subject = SecurityUtils.getSubject();
            Staff staff = userService.findStaff((String) subject.getPrincipal());
            List<AppYdbpAreaZwstaff> staffs = userService.findZWStaffById(staff.getStaffId());
            ModelAndView mv = new ModelAndView("myzonestaff");
            mv.getModel().put("staffs",staffs);
            return mv;
        } catch (Exception e){
            e.printStackTrace();
            ModelAndView mv = new ModelAndView("myzonestaff");
            mv.getModel().put("message","无权限操作!");
            return mv;
        }
    }

    /**
     * 分局CEO查看工号列表
     */
    @RequestMapping("dceozonestaff")
    public ModelAndView dceoZoneStaff(){
        try {
            Subject subject = SecurityUtils.getSubject();
            Staff staff = userService.findStaff((String) subject.getPrincipal());
            String districtId = userService.findDistrictByStaff(staff.getCssStaffNumber());
            List<AppYdbpAreaZwstaff> staffs = userService.findZWStaffByDistrict(Long.parseLong(districtId));
            ModelAndView mv = new ModelAndView("dceozonestaff");
            mv.getModel().put("staffs",staffs);
            return mv;
        } catch (Exception e){
            e.printStackTrace();
            throw  new UnsupportedOperationException("无权限操作!");
        }
    }

    /**
     * 分局内所有装维人员列表
     * @return
     */
    @RequestMapping("areazonestaff")
    public ModelAndView areaZoneStaff(){
        try {
            Subject subject = SecurityUtils.getSubject();
            Staff staff = userService.findStaff((String) subject.getPrincipal());
            String areaId = userService.findAreaByStaff(staff.getCssStaffNumber());
            List<AppYdbpAreaZwstaff> staffs = userService.findZWStaffByArea(Long.parseLong(areaId));
            ModelAndView mv = new ModelAndView("dceozonestaff");
            mv.getModel().put("staffs",staffs);
            return mv;
        } catch (Exception e){
            e.printStackTrace();
            throw  new UnsupportedOperationException("无权限操作!");
        }
    }

}
