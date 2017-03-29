package com.telecomjs.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.telecomjs.beans.*;
import com.telecomjs.datagrid.AssessmentStateHelper;
import com.telecomjs.datagrid.OperationsRoleHelper;
import com.telecomjs.datagrid.UserHelper;
import com.telecomjs.services.intf.AreaService;
import com.telecomjs.services.intf.AssessmentService;
import com.telecomjs.services.intf.ItemService;
import com.telecomjs.services.intf.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by zark on 17/3/21.
 */
@RequestMapping("item")
@Controller
public class AssessItemController extends BaseController {

    @Autowired
    UserService userService;

    @Autowired
    AreaService areaService;

    @Autowired
    ItemService itemService;

    @Autowired
    AssessmentService assessmentService;

    @RequestMapping("modifyassessmentgroup")
    public ModelAndView modifyAssessmentGroup(){
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole(UserHelper.RoleType.ADMIN.name()) && !subject.hasRole(UserHelper.RoleType.QDADMIN.name()))
            throw new UnsupportedOperationException("非管理员权限！");
        ModelAndView mv = new ModelAndView("modifyassessmentgroup");
        List<ItemCategory> items = itemService.findAllCategories();
        String roleCodes = JSON.toJSONString(OperationsRoleHelper.roleMap);
        mv.getModel().put("items",items);
        mv.getModel().put("roles", roleCodes);
        return mv;
    }

    @RequestMapping("getitemcategoryseq")
    @ResponseBody
    public void getItemCategorySeq(HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        int seq = itemService.getItemCategorySeq();
        out.write(String.valueOf(seq));
    }

    @RequestMapping("getassessmentitemseq")
    @ResponseBody
    public void getAssessmentItemSeq(HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        int seq = itemService.getAssessmentItemSeq();
        out.write(String.valueOf(seq));
    }

    @RequestMapping(value = "dosaveitemcategory",method = RequestMethod.POST)
    @ResponseBody
    public void doSaveItemCategory(@RequestParam("catId")int id
            ,@RequestParam("roleCode") String code
            ,@RequestParam("groupName")String groupName
            , HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole(UserHelper.RoleType.ADMIN.name()) && !subject.hasRole(UserHelper.RoleType.QDADMIN.name()))
            throw new UnsupportedOperationException("非管理员权限！");
        ItemCategory category = new ItemCategory();
        category.setCategoryId(id);
        category.setRoleCode(code);
        category.setName(groupName);
        if (itemService.saveCategory(category) > 0){
            out.write("操作成功!");
        }
        else {
            out.write("操作失败!");
        }
    }

    @RequestMapping("modifyassessmentitem")
    public ModelAndView modifyAssessmentItem(@RequestParam("role")String roleCode){
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole(UserHelper.RoleType.ADMIN.name()) && !subject.hasRole(UserHelper.RoleType.QDADMIN.name()))
            throw new UnsupportedOperationException("非管理员权限！");
        OperationsRoleHelper.RoleType roleType ;
        try {
            roleType=OperationsRoleHelper.RoleType.valueOf(roleCode);
        } catch (Exception e){
            roleType = OperationsRoleHelper.RoleType.CEO;
        }
        ModelAndView mv = new ModelAndView("modifyassessmentitem");
        List<AssessmentItem> items = itemService.findAllItemsByRole(roleType);
        String roleCodes = OperationsRoleHelper.getRoles();
        List<ItemCategory> categories = itemService.findAllCategoriesByRole(roleType);
        String categoryJson = JSON.toJSONString(categories);
        String stateJson = AssessmentStateHelper.getUseOrNotState();
        mv.getModel().put("categories",categoryJson);
        mv.getModel().put("roles", roleCodes);
        mv.getModel().put("items",items);
        mv.getModel().put("state",stateJson);
        mv.getModel().put("roleName",roleType.getName());
        return mv;
    }

    @RequestMapping(value = "dosaveitem",method = RequestMethod.POST)
    @ResponseBody
    public void doSaveItem(@RequestParam("id")int id
            ,@RequestParam("name") String name
            ,@RequestParam("roleCode")String roleCode
            ,@RequestParam("percent")short percent
            ,@RequestParam("score")int score
            ,@RequestParam("detail")String detail
            ,@RequestParam("categoryId")int categoryId
            ,@RequestParam("state")String state
            , HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole(UserHelper.RoleType.ADMIN.name()) && !subject.hasRole(UserHelper.RoleType.QDADMIN.name()))
            throw new UnsupportedOperationException("非管理员权限！");
        AssessmentItem item = new AssessmentItem();
        item.setRoleCode(roleCode);
        item.setStateDate(new Date());
        item.setState(state);
        item.setAssessmentPercent(  percent);
        item.setCriterionDetail(detail);
        item.setItemCategoryId(categoryId);
        item.setCriterionScore(BigDecimal.valueOf(score));
        item.setItemId(id);
        item.setItemName(name);
        if (itemService.saveItem(item) > 0){
            out.write("操作成功!");
        }
        else {
            out.write("操作失败!");
        }
    }

    @RequestMapping(value = "finditems",method = RequestMethod.POST)
    @ResponseBody
    public void findItems(
            @RequestParam("roleCode")String roleCode
            , HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole(UserHelper.RoleType.ADMIN.name()) && !subject.hasRole(UserHelper.RoleType.QDADMIN.name())) {
            out.write("非管理员权限！");
            return;
        }

        OperationsRoleHelper.RoleType roleType ;
        try {
            roleType=OperationsRoleHelper.RoleType.valueOf(roleCode);
        } catch (Exception e){
            roleType = OperationsRoleHelper.RoleType.CEO;
        }

        List<AssessmentItem> items = itemService.findAllItemsByRole(roleType);
        out.write(JSON.toJSONString(items, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty
                ,SerializerFeature.WriteNullListAsEmpty,SerializerFeature.WriteDateUseDateFormat));

    }

    @RequestMapping("modifystaffitem")
    public ModelAndView modifyStaffItem(@RequestParam("id")int staffAssessmentId){

        Subject subject = SecurityUtils.getSubject();
        //只能由片区CEO打分
        if (!subject.hasRole(UserHelper.RoleType.ZCEO.name())) {
            throw new UnsupportedOperationException("非片区CEO权限!");
        }


        StaffAssessment staffAssessment = assessmentService.getStaffAssessment(staffAssessmentId);
        if (staffAssessment == null  )
            throw new UnsupportedOperationException("员工考核记录不存在");
        Staff staff = userService.findStaff((String) subject.getPrincipal());
        if (assessmentService.existsStaffAssessment(Long.valueOf(staff.getCssStaffNumber()),staffAssessment.getAssessmentId())<=0){
            throw new UnsupportedOperationException("所属片区和考核项不符!");
        }
        Assessment assessment = assessmentService.getAssessment(staffAssessment.getAssessmentId());
        if (assessment == null)
            throw new UnsupportedOperationException("本片区当月考核流程已经完成!");
        OperationsRoleHelper.RoleType roleType = OperationsRoleHelper.roleNameOf(staffAssessment.getRoleName());
        if (roleType == null)
            throw  new UnsupportedOperationException("装维类型不正确！"+staffAssessment.getRoleName());
        List<AssessmentItem> items = itemService.findAllItemsByRole(roleType);
        double totalScore = 0;
        int totalPercent = 0;
        for (AssessmentItem item : items){
            totalScore += item.getCriterionScore().doubleValue();
            totalPercent += item.getAssessmentPercent();
        }
        List<ItemCategory> categories = itemService.findAllCategoriesByRole(roleType);
        String categoryJson = JSON.toJSONString(categories);
        ModelAndView mv = new ModelAndView("modifystaffitem");
        mv.getModel().put("items",items);
        mv.getModel().put("categories",categoryJson);
        mv.getModel().put("staffAssessmentId",staffAssessmentId);
        mv.getModel().put("totalScore",totalScore);
        mv.getModel().put("totalPercent",totalPercent);
        return mv;
    }

    @RequestMapping("mystaffitem")
    public ModelAndView ceoStaffItem(@RequestParam("id")int staffAssessmentId){

        Subject subject = SecurityUtils.getSubject();
        //只能由片区CEO打分
        /*if (!subject.hasRole(UserHelper.RoleType.ZCEO.name())) {
            throw new UnsupportedOperationException("非片区CEO权限!");
        }*/


        StaffAssessment staffAssessment = assessmentService.getStaffAssessment(staffAssessmentId);
        if (staffAssessment == null  )
            throw new UnsupportedOperationException("员工考核记录不存在");
        //Staff staff = userService.findStaff((String) subject.getPrincipal());
        /*if (assessmentService.existsStaffAssessment(Long.valueOf(staff.getCssStaffNumber()),staffAssessment.getAssessmentId())<=0){
            throw new UnsupportedOperationException("所属片区和考核项不符!");
        }*/
        Assessment assessment = assessmentService.getAssessment(staffAssessment.getAssessmentId());
        if (assessment == null)
            throw new UnsupportedOperationException("考核项ID不存在!"+staffAssessment.getAssessmentId());
        OperationsRoleHelper.RoleType roleType = OperationsRoleHelper.roleNameOf(staffAssessment.getRoleName());
        if (roleType == null)
            throw  new UnsupportedOperationException("装维类型不正确！"+staffAssessment.getRoleName());
        List<StaffAssessmentItem> items = itemService.findALlStaffItemsByStaffAssessmentId(staffAssessmentId);
        double totalScore = 0;
        int totalPercent = 0;
        double totalMyScore = 0;
        for (StaffAssessmentItem item : items){
            totalScore += item.getCriterionScore().doubleValue();
            totalPercent += item.getAssessmentPercent();
            totalMyScore += item.getScore().doubleValue();
        }
        List<ItemCategory> categories = itemService.findAllCategoriesByRole(roleType);
        String categoryJson = JSON.toJSONString(categories);
        ModelAndView mv = new ModelAndView("mystaffitem");
        mv.getModel().put("items",items);
        mv.getModel().put("categories",categoryJson);
        mv.getModel().put("staffAssessmentId",staffAssessmentId);
        mv.getModel().put("totalScore",totalScore);
        mv.getModel().put("totalPercent",totalPercent);
        mv.getModel().put("totalMyScore",totalMyScore);
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/dosavestaffitems/{staffassessid}",method = RequestMethod.POST )
    public void doSaveStaffItems(@PathVariable("staffassessid")int staffAssessId
            , @RequestBody String req,HttpServletResponse response) throws IOException {


        response.setHeader("Cache-Control","no-cache");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {


            //先判断传入的ID参数是否属于该工号
            StaffAssessment staffAssessment = assessmentService.getStaffAssessment(staffAssessId);
            Subject subject = SecurityUtils.getSubject();
            Staff staff = userService.findStaff((String) subject.getPrincipal());
            if (assessmentService.existsStaffAssessment(Long.valueOf(staff.getCssStaffNumber()),staffAssessment.getAssessmentId())<=0){
                out.write("所属片区和考核项不符！");
                return;
            }

            Assessment assessment = assessmentService.getAssessment(staffAssessment.getAssessmentId());
            if (assessment == null){
                out.write( "{考核ID不存在，反馈分配表失败！}");
                return;
            }
            /*if (!assessment.getState().equals("REP")){
                out.write("{考核ID状态异常，操作失败！}");
                return;
            }*/

            List<HashMap> list = JSON.parseArray (req, HashMap.class);
            List<StaffAssessmentItem> staffItems = new ArrayList<>();
            for (Map map : list){
                StaffAssessmentItem item = new StaffAssessmentItem();
                item.setItemName((String) map.get("name"));
                item.setItemId(Integer.parseInt (String.valueOf(map.get("id"))));
                item.setCriterionScore( new BigDecimal((String) map.get("score")));
                item.setAssessmentPercent(Short.valueOf((String)map.get("percent")));
                item.setCriterionDetail((String) map.get("detail"));
                item.setId(itemService.getStaffItemSeq());
                item.setRoleCode((String) map.get("roleCode"));
                item.setState(AssessmentStateHelper.UseOrNotState.USE.name());
                item.setStateDate(new Date());
                item.setScore( new BigDecimal((String) map.get("myScore")));
                item.setStaffAssessmentId(staffAssessId);
                item.setCategoryId(Short.valueOf((String) map.get("categoryId")));
                //加入list中
                logger.debug(JSON.toJSONString(item));
                staffItems.add(item);
            }
            //logger.debug(JSON.toJSONString(staffItems));
            if (itemService.saveStaffItem(staffItems)>0){
                out.write("{反馈绩效表成功！}");
                return;
            }
            out.write("{反馈绩效表失败！}");
            return;
        }
        catch (Exception e){
            e.printStackTrace();
            //throw new UnsupportedOperationException(e.getMessage());
            out.write("{反馈绩效表失败！}");
            return;
        }

    }


}
