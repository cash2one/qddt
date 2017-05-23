package com.telecomjs.controllers;

import com.telecomjs.beans.*;
import com.telecomjs.datagrid.*;
import com.telecomjs.services.intf.AssessmentService;
import com.telecomjs.services.intf.MarkService;
import com.telecomjs.services.intf.UserService;
import com.telecomjs.utils.HttpResponseHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zark on 17/4/20.
 */

@RequestMapping("/mark")
@Controller
public class MarkController extends BaseController {

    @Autowired
    AssessmentService assessmentService;
    @Autowired
    UserService userService;
    @Autowired
    MarkService markService;

    @RequestMapping("/uploadmarkfile")
    public ModelAndView uploadMark(){
        List<BillingCycle> cycles = assessmentService.findAllCycles();
        ModelAndView mv =  new ModelAndView("uploadmarkfile");
        mv.getModel().put("cycles",cycles);
        return  mv;
    }

    @RequestMapping(value = "/doupload",method = RequestMethod.POST)
    public ModelAndView doUpload(@RequestParam(value = "billingcycleid")  String billingCycleId,
                                 @RequestParam("excelfile") MultipartFile file) throws IOException {
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        Staff staff = userService.findStaff(username);
        if (file==null || file.getSize()==0){
            throw new UnsupportedOperationException("上传文件错误!");
        }
        //生成 event表和assessment 状态是init
        Map map = MarkHelper.parseExcel(file);
        List list = (List) map.get("rows");
        DataColumn[] titleset = (DataColumn[]) map.get("titles");
        int staffId = 0;
        ZoneMarkEvent event = MarkHelper.createEvent(markService.getSequenceOfMarkEvent(),Integer.parseInt(billingCycleId)
                , Long.parseLong(staff.getCssStaffNumber()),list.size()
                , AssessmentStateHelper.AssessmentNode.INI.name());

        if (markService.countEventsByCycle(Integer.parseInt(billingCycleId))>0){
            throw new UnsupportedOperationException("当前账期已经有未关闭的上报记录，请先关闭!");
        }
        int eventId = markService.saveEventAndUpload(event,list);
        //返回给前台显示
        DataSet dataSet = new DataSet(list.toArray());
        return  new ModelAndView("redirect:verifyevent?eventId="+eventId);
    }

    @RequestMapping("/verifyevent")
    public ModelAndView verifyEvent(@RequestParam(value = "eventId" )int eventId,
                                    @RequestParam(value = "readonly",defaultValue = "false") String readonly){
        ModelAndView view  = new ModelAndView("verifyevent");
        List<ZoneMarkUpload> uploads = markService.findUploadByEvent(eventId);
        view.getModel().put("rows",uploads);
        view.getModel().put("readonly",readonly);
        view.getModel().put("eventid",eventId);
        return view;
    }

    @RequestMapping("/eventlist")
    public ModelAndView allEvents(){
        ModelAndView view = new ModelAndView("markeventlist");
        List<ZoneMarkEvent> events = markService.findALlEvents();
        view.getModel().put("events",events);
        return view;
    }

    @RequestMapping("/viewevent")
    public ModelAndView viewEvent(@RequestParam(value = "eventId" )int eventId){
        return  new ModelAndView("forward:verifyevent?eventId="+eventId+"&readonly=true");
    }

    @ResponseBody
    @RequestMapping(value = "/docloseevent",method = RequestMethod.POST)
    public void doCloseEvent(@RequestParam("eventId") int eventId, HttpServletResponse response) throws IOException {

        PrintWriter out = HttpResponseHelper.getUtf8Writer(response);
        ZoneMarkEvent event  = markService.getEvent(eventId);
        if (event == null ) {
            out.write("操作失败!");
            return;
        }
        if (!event.getState().equals(AssessmentStateHelper.AssessmentNode.INI.name()) && !event.getState().equals(AssessmentStateHelper.AssessmentNode.OPN.name())){
            out.write("当前上报事件的状态不允许关闭!");
            return;
        }
        if ( markService.closeEvent(eventId)>0) {
            out.write("结束上报流程成功!");
            return;
        }
        out.write("操作失败!");
    }

    @ResponseBody
    @RequestMapping(value = "/docommit",method = RequestMethod.POST)
    public void commitEvent(@RequestParam("eventId") int eventId, HttpServletResponse response) throws IOException {

        PrintWriter out = HttpResponseHelper.getUtf8Writer(response);
        ZoneMarkEvent event  = markService.getEvent(eventId);
        if (event == null ) {
            out.write("event不存在!");
            return;
        }
        if (!event.getState().equals(AssessmentStateHelper.AssessmentNode.INI.name()) ){
            out.write("当前上报事件的状态不正确!");
            return;
        }
        if ( markService.commitEvent(eventId)>0) {
            out.write("结束上报流程成功!");
            return;
        }
        out.write("操作失败!");
    }

    //创建完账期，初始化账期基础数据
    @ResponseBody
    @RequestMapping("/doopencycle")
    public void openCycleAndMark(@RequestParam("billingCycle")int billingCycle,HttpServletResponse response) throws IOException {
        billingCycle = 201704; //for test
        PrintWriter out = HttpResponseHelper.getUtf8Writer(response);
        Subject subject = SecurityUtils.getSubject();
        if (subject.hasRole(UserHelper.RoleType.ADMIN.name())){
            if (markService.openCycleAndCreateMark(billingCycle)>0){
                out.write("操作成功!");
                return;
            }
        }
        out.write("操作失败!");
    }

    /*//渠道上传完数据，创建新账期的考核数据
    @ResponseBody
    @RequestMapping("/doopencycle")
    public void openBillingCycle(@RequestParam("billingCycle")int billingCycle,HttpServletResponse response) throws IOException {
        billingCycle = 201704; //for test
        PrintWriter out = HttpResponseHelper.getUtf8Writer(response);
        Subject subject = SecurityUtils.getSubject();
        if (subject.hasRole(UserHelper.RoleType.ADMIN.name())){
            if (markService.openBillingCycle(billingCycle)>0){
                out.write("操作成功!");
                return;
            }
        }
        out.write("操作失败!");
    }*/

    //渠道审阅完考核数据，提交
    @ResponseBody
    @RequestMapping("/docommitcycle")
    public void commitBillingCycle(@RequestParam("billingCycle")int billingCycle,HttpServletResponse response) throws IOException {
        billingCycle = 201704; //for test
        PrintWriter out = HttpResponseHelper.getUtf8Writer(response);
        Subject subject = SecurityUtils.getSubject();
        if (subject.hasRole(UserHelper.RoleType.ADMIN.name())){
            if (markService.commitBillingCycle(billingCycle)>0){
                out.write("操作成功!");
                return;
            }
        }
        out.write("操作失败!");
    }


    @RequestMapping("/areaauditview")
    public ModelAndView areaAuditView(@RequestParam String areaName,@RequestParam int billingCycle){
        ModelAndView view  = new ModelAndView("areaauditview");
        List<BillingCycle> cycles = assessmentService.findAllCycles();
        view.getModel().put("cycles",cycles);

        long areaId = assessmentService.getAreaIdByName(areaName);
        billingCycle=201703;
        areaId=250002656l;
        List<Assessment> assessments = billingCycle == 0 ?   new ArrayList<Assessment>() :
                assessmentService.findAssessmentByArea (Long.valueOf(areaId),billingCycle);
        Map assessmentMap = new HashMap();
        for (Assessment assessment : assessments){
            List list = (List) assessmentMap.get(assessment.getDistrictName());
            if (list == null){
                list = new ArrayList();
            }
            list.add(assessment);
            assessmentMap.put(assessment.getDistrictName(), list);
        }
        view.getModel().put("map",assessmentMap);
        return view;
    }
    @RequestMapping("/adminauditview")
    public ModelAndView adminAuditView( @RequestParam int billingCycle){
        ModelAndView view  = new ModelAndView("adminauditview");
        List<BillingCycle> cycles = assessmentService.findAllCycles();
        view.getModel().put("cycles",cycles);

        //测试数据
        billingCycle=201703;
        List<Assessment> assessments = billingCycle == 0 ?   new ArrayList<Assessment>() :
                assessmentService.findAllAreaAssessments ( billingCycle);
        view.getModel().put("assessments",assessments);
        return view;
    }
}
