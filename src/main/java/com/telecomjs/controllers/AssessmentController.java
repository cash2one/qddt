package com.telecomjs.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.telecomjs.beans.*;
import com.telecomjs.datagrid.*;
import com.telecomjs.datagrid.AssessmentStateHelper.AssessmentNode;
import com.telecomjs.services.intf.AreaService;
import com.telecomjs.services.intf.AssessmentService;
import com.telecomjs.services.intf.UserService;
import com.telecomjs.utils.HttpResponseHelper;
import com.telecomjs.utils.PoiExcelWriter;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.telecomjs.datagrid.UserHelper.RoleType;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by zark on 17/3/6.
 */
@Controller
@RequestMapping("/assessment")
public class AssessmentController extends BaseController {

    @Autowired
    AssessmentService assessmentService;

    @Autowired
    UserService userService;

    @Autowired
    AreaService areaService;

    @RequestMapping("/upload")
    public ModelAndView upload(){
        List<BillingCycle> cycles = assessmentService.findAllCycles();
        /*List cycles = new ArrayList<String>();
        cycles.add("201703");
        cycles.add("201704");*/
        ModelAndView mv =  new ModelAndView("uploadfile");
        mv.getModel().put("cycles",cycles);
        return  mv;
    }

    //确认上传的event 状态初始为INI
    @RequestMapping(value = "/doupload",method = RequestMethod.POST)
    public ModelAndView doUpload(@RequestParam(value = "billingcycleid")  String billingCycleId,
                         @RequestParam("excelfile") MultipartFile file) throws IOException {
        try {
            if (file==null || file.getSize()==0){
                throw new UnsupportedOperationException("上传文件错误!");
            }
            //生成 event表和assessment 状态是init
            Map map = AssessmentHelper.parseExcel(file);
            List list = (List) map.get("rows");
            DataColumn[] titleset = (DataColumn[]) map.get("titles");
            int staffId = 0;
            AssessmentEvent event = AssessmentHelper.createEvent(file.getOriginalFilename()
                    ,assessmentService.getSequenceOfEvent()
                    ,staffId
                    ,Integer.parseInt(billingCycleId)
                    ,list.size());
            if (assessmentService.countEventsByCycle(Integer.parseInt(billingCycleId))>0){
                throw new UnsupportedOperationException("当前账期已经有未关闭的上报记录，请先关闭!");
            }
            int eventId = assessmentService.initEventAndAssessment(event,list);
            //返回给前台显示
            DataSet dataSet = new DataSet(list.toArray());
            ModelAndView mv = new ModelAndView("verifyrep");
            mv.getModel().put("titles", JSON.toJSONString(titleset));
            mv.getModel().put("rows",JSON.toJSONString(dataSet));
            mv.getModel().put("eventid",eventId);
            return mv;
        } catch (Exception e){
            e.printStackTrace();
            ModelAndView mv = new ModelAndView("error");
            mv.getModel().put("message","解析EXCEL数据文件出错!");
            mv.getModel().put("exception",e.getMessage());
            return mv;
        }
    }

    /**
     * 确认上传的event  发起工作流 E
     *   EVENT 状态从INI 到 OPN
     *   ASSESSMENT 从TEMP迁入正式表,删除TEMP表
     * @param eventId 传入的事件ID
     * @return
     */
    @RequestMapping(value = "/doverify",method = RequestMethod.POST)
    public ModelAndView doVerify(@RequestParam(value = "eventid")  int eventId ) {
        AssessmentEvent event = assessmentService.getEvent(eventId);
        StringBuffer stringBuffer = new StringBuffer();
        if (event == null ){
            stringBuffer.append("传入的EventID参数有误!");
        }
        if (!event.getState().equals("INI")){
            stringBuffer.append("传入的EventID状态正确!");
        }
        if (stringBuffer.length() > 0){
            ModelAndView mv = new ModelAndView("error");
            mv.getModel().put("message",stringBuffer.toString());
            return mv;
        }else {
            assessmentService.reportEvent(eventId);
            ModelAndView mv = new ModelAndView("redirect:eventlist");
            return mv;
        }
    }

    @RequestMapping("/eventlist")
    public ModelAndView viewEvents(){
        List<AssessmentEvent> events = assessmentService.findAllEvents();
        ModelAndView mv  = new ModelAndView("eventlist");
        mv.getModel().put("events",events);
        return mv;
    }

    /**
     * 管理员已上报考核表，可以在通知前关闭考核事件.
     * @param eventId
     * @param response
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/docloseevent",method = RequestMethod.POST)
    public void doCloseEvent(@RequestParam("eventid") int eventId, HttpServletResponse response) throws IOException {

        response.setHeader("Cache-Control","no-cache");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        AssessmentEvent event = assessmentService.getEvent(eventId);
        if (event == null ) {
            out.write("操作失败!");
            return;
        }
        if (!event.getState().equals(AssessmentNode.INI.name()) && !event.getState().equals(AssessmentNode.OPN.name())){
            out.write("当前上报事件的状态不允许关闭!");
            return;
        }
        if ( assessmentService.closeEvent(eventId)>0) {
            out.write("结束上报流程成功!");
            return;
        }
        out.write("操作失败!");
    }

    @ResponseBody
    @RequestMapping(value = "/dosubscribe",method = RequestMethod.POST)
    public void doSubscribe(@RequestParam("eventid") int eventId, HttpServletResponse response) throws IOException {
        AssessmentEvent event = assessmentService.getEvent(eventId);
        response.setHeader("Cache-Control","no-cache");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        if (event != null ) {
            if ( assessmentService.subscribeEvent(eventId)>0) {
                out.write("已通知所有片区，操作成功!");
                return;
            }
        }
        out.write("操作失败!");
    }

    @RequestMapping("/myassessmentlist")
    public ModelAndView ceoAssessmentList(){
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        Staff staff = userService.findStaff(username);
        if (staff == null){
            throw new RuntimeException("用户不存在!");
        }
        List<String> zoneIds = userService.findZoneByStaff(staff.getCssStaffNumber());
        if (zoneIds.size() == 0){
            ModelAndView mv = new ModelAndView("error");
            mv.getModel().put("message","该工号未挂载到相应片区!");
            return mv;
        }
        ModelAndView mv = new ModelAndView("myassessmentlist");
        List<Assessment> assessments = new ArrayList<>();
        for (String zoneId:zoneIds)
            assessments.addAll(assessmentService.findAssessmentByZone(Long.parseLong(zoneId)));
        mv.getModel().put("assessments",assessments);
        return mv;
    }


    /**
     * 管理员查看考核列表
     * @return
     */
    @RequestMapping("/adminassessmentlist")
    public ModelAndView adminAssessmentList(){
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        Staff staff = userService.findStaff(username);
        if (staff == null){
            throw new UnsupportedOperationException("用户不存在!");
        }
        if (!subject.hasRole(RoleType.ADMIN.name()) && !subject.hasRole(RoleType.QDADMIN.name()))
            throw new UnsupportedOperationException("非管理员权限！");

        List<AppYdbpArea> areaList = areaService.findAllAreas();
        String areaNames ="";
        for (AppYdbpArea area: areaList) {
            areaNames = areaNames + area.getAreaName()+",";
        }
        List<BillingCycle> cycleList = assessmentService.findAllCycles();
        String cycles = "";
        for (BillingCycle cycle : cycleList){
            cycles = cycles + cycle.getBillingCycleId()+",";
        }


        ModelAndView mv = new ModelAndView("adminassessmentlist");
        List<Assessment> assessments = assessmentService.findAllAssessment();
        mv.getModel().put("assessments",assessments);
        mv.getModel().put("areaNames",areaNames);
        mv.getModel().put("cycles",cycles);
        return mv;
    }

    /**
     * 管理员查看分局考核列表
     * @return
     */
    @RequestMapping("/admindistrictassessments")
    public ModelAndView adminDistrictAssessment(){
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        Staff staff = userService.findStaff(username) ;
        if (staff == null){
            throw new UnsupportedOperationException("用户不存在!");
        }
        if (!subject.hasRole(RoleType.ADMIN.name()) && !subject.hasRole(RoleType.QDADMIN.name()))
            throw new UnsupportedOperationException("非管理员权限！");

        List<AppYdbpArea> areaList = areaService.findAllAreas();
        String areaNames ="";
        for (AppYdbpArea area: areaList) {
            areaNames = areaNames + area.getAreaName()+",";
        }
        List<BillingCycle> cycleList = assessmentService.findAllCycles();
        String cycles = "";
        for (BillingCycle cycle : cycleList){
            cycles = cycles + cycle.getBillingCycleId()+",";
        }


        ModelAndView mv = new ModelAndView("admindistrictassessments");
        List<Assessment> assessments = assessmentService.findAllDistrictAssessments();
        mv.getModel().put("assessments",assessments);
        mv.getModel().put("areaNames",areaNames);
        mv.getModel().put("cycles",cycles);
        return mv;
    }

    /**
     * 管理员查看区域考核列表
     * @return
     */
    @RequestMapping("/adminareaassessments")
    public ModelAndView adminAreaAssessment(){
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        Staff staff = userService.findStaff(username);
        if (staff == null){
            throw new UnsupportedOperationException("用户不存在!");
        }
        if (!subject.hasRole(RoleType.ADMIN.name()) && !subject.hasRole(RoleType.QDADMIN.name()))
            throw new UnsupportedOperationException("非管理员权限！");


        List<AppYdbpArea> areaList = areaService.findAllAreas();
        String areaNames ="";
        for (AppYdbpArea area: areaList) {
            areaNames = areaNames + area.getAreaName()+",";
        }
        List<BillingCycle> cycleList = assessmentService.findAllCycles();
        String cycles = "";
        for (BillingCycle cycle : cycleList){
            cycles = cycles + cycle.getBillingCycleId()+",";
        }
        ModelAndView mv = new ModelAndView("adminareaassessments");
        List<Assessment> assessments = assessmentService.findAllAreaAssessments();
        mv.getModel().put("assessments",assessments);
        mv.getModel().put("areaNames",areaNames);
        mv.getModel().put("cycles",cycles);
        return mv;
    }

    @RequestMapping("/feedbackstaffassessment")
    public ModelAndView feedbackStaffAssessment(@RequestParam("assessmentid") int assessmentId){

        Assessment assessment = assessmentService.getAssessment(assessmentId);
        if (assessment == null || !assessment.getState().equals("REP")){
            throw  new RuntimeException("要分配的考核项不存在或者已分配!");
        }
        List<AppYdbpAreaZwstaff> staffs = userService.findStaffbyZone(assessment.getZoneId());
        String roleCodes = OperationsRoleHelper.getRoles();
        ModelAndView mv = new ModelAndView("feedbackstaffassessment");
        mv.getModel().put("assessment",assessment);
        mv.getModel().put("staffs",staffs);
        mv.getModel().put("roles",roleCodes);
        return mv;

    }

    @ResponseBody
    @RequestMapping(value = "/dofeedback/{assid}",method = RequestMethod.POST )
    public void doFeedback(@PathVariable("assid")int assId, @RequestBody String req,HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control","no-cache");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");


        try {
            PrintWriter out = response.getWriter();
            Assessment assessment = assessmentService.getAssessment(assId);
            if (assessment == null){
                out.write( "{考核ID不存在，反馈分配表失败！}");
                return;
            }
            if (!assessment.getState().equals("REP")){
                out.write("{考核ID状态异常，操作失败！}");
                return;
            }


            List<HashMap> list = JSON.parseArray (req, HashMap.class);
            List<StaffAssessment> assessmentList = new ArrayList<>();
            double sum = 0;
            double ceoReward = 0;
            String validateString ="";

            for (Map map : list){
                StaffAssessment staffAssessment = new StaffAssessment();
                staffAssessment.setCssStaffId(new Long((String) map.get("cssNumber")));
                staffAssessment.setPersonalAmount(new BigDecimal((String) map.get("amount")));
                staffAssessment.setRoleName((String) map.get("roleName"));
                staffAssessment.setStaffName((String) map.get("username"));
                if (map.get("roleName").equals("CEO"))
                    ceoReward = new BigDecimal((String) map.get("amount")).doubleValue();
                //
                staffAssessment.setAssessmentId(assId);
                staffAssessment.setBillingCycle(assessment.getBillingCycle());
                staffAssessment.setAssessmentAmount(assessment.getDoubleReward());
                staffAssessment.setStateDate(new Date());
                staffAssessment.setState("FED");
                staffAssessment.setId(assessmentService.getSequenceOfStaffAssessment());
                assessmentList.add(staffAssessment);
                sum += new BigDecimal((String) map.get("amount")).doubleValue();
            }

            //if (sum !=  assessment.getDoubleReward().doubleValue())
            //if (!new Double(sum).equals(assessment.getDoubleReward()))
            if (!AssessmentHelper.equalsReward(sum,assessment.getDoubleReward().doubleValue()))
                validateString += "{分配绩效不等于片区应得绩效}";

            if (ceoReward > assessment.getDoubleReward().doubleValue()*0.4d)
                validateString += "{CEO绩效不能超过40%}";
            if (!validateString.equals("")){
                out.write("{反馈绩效表失败！}"+validateString);
                return;
            }

            if (assessmentService.feedbackAssessment(assId,assessmentList)>0){
                out.write("{反馈绩效表成功！}");
                return;
            }
            out.write("{反馈绩效表失败！}");
            return;
        }
        catch (Exception e){
            e.printStackTrace();

        }

    }

    @RequestMapping("/mystaffassessment")
    public ModelAndView viewCeoStaffAssessment(@RequestParam(value = "assessmentid"  ,required=false) int assessmentId){

        Subject subject = SecurityUtils.getSubject();
        Staff staff = userService.findStaff((String) subject.getPrincipal());
        if (staff == null  ){
            ModelAndView error = new ModelAndView("error");
            error.getModel().put("message","无权限操作!");
            return error;
        }
        if (assessmentId == 0){
            List<AppYdbpAreaZwstaff> zwstaffs = userService.findStaffByCssNumber(staff.getCssStaffNumber());
            if (zwstaffs.size() == 0 ){
                ModelAndView error = new ModelAndView("error");
                error.getModel().put("message","工号无对应片区!");
                return error;
            }
            //查看全片区的考核表
            Map zoneMap = new HashMap();
            List<StaffAssessment> staffs = new ArrayList<>();
            for (AppYdbpAreaZwstaff zwstaff : zwstaffs) {
                List<StaffAssessment> list = assessmentService.findStaffAssessmentByZone(zwstaff.getAreaIdTm());
                staffs.addAll(list);
                for (StaffAssessment staffAssessment:list){
                    zoneMap.put(staffAssessment.getAssessmentId(),zwstaff.getAreaNameTm());
                }
            }
            ModelAndView mv = new ModelAndView("mystaffassessment");
            double personalReward=0;
            for (StaffAssessment sa :staffs)
                personalReward += sa.getPersonalAmount().doubleValue();

            mv.getModel().put("zoneNames",zoneMap);
            mv.getModel().put("staffAssessments",staffs);
            mv.getModel().put("personalReward",personalReward);
            return mv;
        }
        Assessment assessment = assessmentService.getAssessment(assessmentId);
        if (assessment == null  ){
            ModelAndView error = new ModelAndView("error");
            error.getModel().put("message","要分配的考核项不存在或者已分配!");
            return error;
        }
        List<StaffAssessment> staffs = assessmentService.findAssessmentById(assessmentId);
        double personalReward=0;
        for (StaffAssessment sa :staffs)
            personalReward += sa.getPersonalAmount().doubleValue();
        ModelAndView mv = new ModelAndView("mystaffassessment");
        mv.getModel().put("zoneName",assessment.getZoneName());
        mv.getModel().put("staffAssessments",staffs);
        mv.getModel().put("personalReward",personalReward);
        return mv;

    }

    @RequestMapping("myuploadsignature")
    public ModelAndView ceoUploadSignature(@RequestParam("assessmentid")int assessmentId){
        Assessment assessment = assessmentService.getAssessment(assessmentId);
        if (assessment == null || assessment.getState().equals("END")){
            /*ModelAndView mv = new ModelAndView("error");
            mv.getModel().put("message","找不到相应考核项");
            return mv;*/
            throw  new RuntimeException("找不到相应考核项");
        }

        ModelAndView mv = new ModelAndView("myuploadsignature");
        mv.getModel().put("assessment",assessment);
        return mv;
    }

    @RequestMapping(value = "douploadsignature",method = RequestMethod.POST)
    public ModelAndView doceoUploadSignature(@RequestParam(value = "assessmentid")  int assessmentId,
                                             @RequestParam(value = "remark") String remark,
                                 @RequestParam("picfile") MultipartFile file) throws IOException {
        Assessment assessment = assessmentService.getAssessment(assessmentId);
        if (assessment == null || assessment.getState().equals("END")){
            throw  new RuntimeException("找不到相应考核项");
        }
        if (file.getSize()==0) {
            throw new RuntimeException("上传文件格式错误!");
        }
        InputStream is = file.getInputStream();
        byte[] photoData = new byte[(int) file.getSize()];
        is.read(photoData);
        AssessmentSignature signature = new AssessmentSignature();
        signature.setState("OPN");
        signature.setStateDate(new Date());
        signature.setAssessmentId(assessmentId);
        signature.setFilename(file.getOriginalFilename());
        signature.setRemark(remark);
        signature.setPic(photoData);
        signature.setSignatureId(assessmentService.getSequenceOfSignature());
        if (assessmentService.saveSignature(signature)>0){
            return new ModelAndView("forward:myassessmentsignature");
        }
        else {
            throw new RuntimeException("上传图片错误!");
        }
    }

    @RequestMapping("myassessmentsignature")
    public ModelAndView myceoAssessmentSignature(@RequestParam("assessmentid") int assessmentId){
        Subject subject = SecurityUtils.getSubject();
        Staff staff = userService.findStaff((String) subject.getPrincipal());
        Assessment assessment = assessmentService.getAssessment(assessmentId);

        if (assessment == null  ){
            throw  new UnsupportedOperationException("找不到相应考核项");
            //return new ModelAndView("forward:myuploadsignature");
        }
        AssessmentSignature signature = assessmentService.findSignature(assessmentId);
        if (signature == null){
            if (assessmentService.existsStaffAssessment(Long.valueOf(staff.getCssStaffNumber()),assessmentId)>0){
                //自己是片区CEO，直接转上传界面
                return new ModelAndView("forward:myuploadsignature");
            }
            else {
                throw new UnsupportedOperationException("签收表未上传!"+HtmlHelper.HTML_GO_BACK);
            }

        }
        List<StaffAssessment> staffs = assessmentService.findAssessmentById(assessmentId);

        ModelAndView mv = new ModelAndView("myassessmentsignature");
        mv.getModel().put("signature",signature);
        mv.getModel().put("zoneName",assessment.getZoneName());
        mv.getModel().put("staffAssessments",staffs);
        return mv;
    }


    @RequestMapping(value = "getPhotoById")
    public void getPhotoById (@RequestParam("signatureid") int signatureId, final HttpServletResponse response) throws IOException {
        AssessmentSignature signature = assessmentService.getSignature(signatureId);
        byte[] data = signature.getPic();
        response.setContentType("image/jpeg");
        response.setCharacterEncoding("UTF-8");
        OutputStream outputSream = response.getOutputStream();
        InputStream in = new ByteArrayInputStream(data);
        int len = 0;
        byte[] buf = new byte[1024];
        while ((len = in.read(buf, 0, 1024)) != -1) {
            outputSream.write(buf, 0, len);
        }
        outputSream.close();
    }

    @RequestMapping("/ceohome")
    public ModelAndView ceohome(){
        Subject subject= SecurityUtils.getSubject();
        Staff staff = userService.findStaff((String) subject.getPrincipal());
        if (staff == null)
            throw new RuntimeException("用户不存在或者未登陆");
        List<Assessment> assessments = assessmentService.findAssessmentByStaff(Long.valueOf(staff.getCssStaffNumber()));
        String hasAssessments = (assessments==null?"0":"1");
        ModelAndView mv = new ModelAndView("ceohome");
        mv.getModel().put("assessments",assessments);
        mv.getModel().put("hasAssessments",hasAssessments);
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/doaudit",method = RequestMethod.POST )
    public void doAudit(@RequestParam("assessmentid")int assessmentId
                        ,@RequestParam("suggestion") String suggestion
                        ,@RequestParam("remark") String remark
            ,HttpServletResponse response) throws IOException {

        response.setHeader("Cache-Control","no-cache");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {
            AuditNodeHelper.SuggestionType suggestionType = AuditNodeHelper.SuggestionType.valueOf(suggestion);
            Subject subject = SecurityUtils.getSubject();
            Staff staff = userService.findStaff((String) subject.getPrincipal());
            List<String> districtIds = userService.findDistrictByStaff(staff.getCssStaffNumber());
            boolean hasOwnedZone = false;
            for (String districtId : districtIds){
                hasOwnedZone = assessmentService.districtHasAssessment(Long.valueOf(districtId),assessmentId);
                if (hasOwnedZone)
                    break;
            }

            if (staff == null || !hasOwnedZone){
                out.write("当前账号无权限操作!");
                return;
            }

            Assessment assessment = assessmentService.getAssessment(assessmentId);
            if (assessment == null){
                out.write("考核ID不存在，操作失败！");
                return;
            }
            if (!assessment.getState().equals("FED")){
                out.write("考核ID状态异常，操作失败！");
                return;
            }
            String suggestionRemark = remark==null?suggestionType.getSuggestion():remark;
            AuditLog auditLog = AuditNodeHelper.makeAuditLog(assessmentService.getSequenceOfAuditLog(),
                    assessment.getAssessmentId(),
                    suggestionRemark,
                    suggestionType.getSuggestion(),
                    assessment.getAreaName(),
                    assessment.getDistrictName(),
                    assessment.getZoneName(),
                    Long.valueOf(staff.getCssStaffNumber()),
                    staff.getName());

            if (assessmentService.auditAssessment(assessmentId,suggestionType,auditLog)>0){
                out.write("操作成功！");
                return;
            }
            out.write("操作失败！");
            return;
        }
        catch (Exception e){
            e.printStackTrace();
            out.write("操作失败！");
            return;
        }

    }

    @ResponseBody
    @RequestMapping(value = "/doend",method = RequestMethod.POST )
    public void doEnd(@RequestParam("assessmentid")int assessmentId
            ,HttpServletResponse response) throws IOException {

        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {
            //判断用户当前角色
            Subject subject = SecurityUtils.getSubject();
            Staff staff = userService.findStaff((String) subject.getPrincipal());
            boolean hasOwnedZone = assessmentService.existsStaffAssessment (Long.valueOf(staff.getCssStaffNumber()), assessmentId)>0;
            if (staff == null || !hasOwnedZone) {
                out.write("当前账号无权限操作!");
                return;
            }

            if (assessmentService.existsAssessmentSignature(assessmentId)==0){
                out.write("考核ID还为签收，操作失败！");
                return;
            }
            Assessment assessment = assessmentService.getAssessment(assessmentId);
            if (assessment == null) {
                out.write("考核ID不存在，操作失败！");
                return;
            }
            if (assessmentService.endAssessment(assessmentId) > 0) {
                out.write("操作成功！");
                return;
            }
            out.write("操作失败！");
            return;
        } catch (Exception e){
            out.write("操作失败！");
            return;
        }
    }

    @RequestMapping("myauditlog")
    public ModelAndView ceoAuditLog(@RequestParam("assessmentid")int assessmentId){
        Subject subject = SecurityUtils.getSubject();
        Staff staff = userService.findStaff((String) subject.getPrincipal());
        if (staff == null){
            throw new RuntimeException("当前用户无权操作");
        }
        Assessment assessment = assessmentService.getAssessment(assessmentId);
        if (assessmentId > 0 && assessment == null ){
            throw  new RuntimeException("找不到相应考核项");
        }

        ModelAndView mv = new ModelAndView("myauditlog");
        List<AuditLog> audits=null;
        if (assessmentId > 0)
            audits = assessmentService.findAuditLogById(assessmentId);
        else
            audits = assessmentService.findAuditLogByCssNumber(Long.valueOf(staff.getCssStaffNumber()));
        mv.getModel().put("audits",audits);
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/downloadexcel",method = RequestMethod.POST )
    public void downloadExcel(@RequestParam("data")String json
            ,@RequestParam("titles") String titles
            ,@RequestParam("filename") String fileName
            ,HttpServletResponse response) throws IOException {

        logger.info(json);
        response.reset();
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        List<HashMap> list = JSON.parseArray (json, HashMap.class);
        JSONArray excelTitles = JSON.parseArray(titles);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PoiExcelWriter writer = new PoiExcelWriter(os,"xlsx");
        writer.open();
        String[] textTitles = new String[excelTitles.size()];
        String[] idTitles = new String[excelTitles.size()];
        for (int i = 0;i<excelTitles.size();i++){
            idTitles[i]=excelTitles.getJSONObject(i).getString("field");
            textTitles[i]=excelTitles.getJSONObject(i).getString("title");
        }
        writer.writeTitle(textTitles);
        //writer.writeAll(list);
        List<Object[]> rows = new ArrayList();
        for (Map m : list){
            String[] row = new String[idTitles.length];
            int j = 0;
            for (String key : idTitles){
                row[j++] = ((String) m.get(key)).trim();
            }
            rows.add(row);
        }
        writer.writeAll(rows);
        writer.close();

        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"");
        response.addHeader("Content-Length", "" + os.size());
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.getOutputStream().write(os.toByteArray());
        /*OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        outputStream.write(os.toByteArray());
        outputStream.flush();
        outputStream.close();*/
        return;
    }

    @RequestMapping("adminqueryform")
    public ModelAndView adminQueryForm(){
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole(RoleType.ADMIN.name()) && !subject.hasRole(RoleType.QDADMIN.name()))
            throw new UnsupportedOperationException("非管理员权限！");
        ModelAndView mv = new ModelAndView("adminqueryform");
        return mv;
    }

    @RequestMapping("adminstaffassessment")
    public ModelAndView adminStaffAssessment(@RequestParam(value = "cycle",required = false) int cycle){
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole(RoleType.ADMIN.name()) && !subject.hasRole(RoleType.QDADMIN.name()))
            throw new UnsupportedOperationException("非管理员权限！");
        ModelAndView mv = new ModelAndView("adminstaffassessment");
        List<AssessmentWithDetail> assessments ;
        List<BillingCycle> cycles = assessmentService.findAllCycles();
        if (cycle > 0)
            assessments = assessmentService.findAssessmentByCycle(cycle);
        else
            assessments = new ArrayList<>();
        mv.getModel().put("assessments",assessments);
        mv.getModel().put("cycles",cycles);
        return mv;
    }

    @RequestMapping("adminstaffassessmentpage")
    public ModelAndView adminStaffAssessmentPage(@RequestParam(value = "cycle",required = false) int cycle) {
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole(RoleType.ADMIN.name()) && !subject.hasRole(RoleType.QDADMIN.name()))
            throw new UnsupportedOperationException("非管理员权限！");
        ModelAndView mv = new ModelAndView("adminstaffassessmentpage");
        List<BillingCycle> cycles = assessmentService.findAllCycles();
        mv.getModel().put("cycles",cycles);
        mv.getModel().put("total","0");
        return mv;
    }

    @RequestMapping("findstaffassessmentbypage")
    @ResponseBody
    public void findStaffAssessmentByPage(@RequestParam(value = "cycle",required = false) int cycle
            ,@RequestParam("pageNum") int pageNum
            ,@RequestParam("pageSize") int pageSize
            ,HttpServletResponse response) throws IOException {
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole(RoleType.ADMIN.name()) && !subject.hasRole(RoleType.QDADMIN.name()))
            throw new UnsupportedOperationException("非管理员权限！");
        response.reset();
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        List list =  assessmentService.findAssessmentWithPageByCycle(cycle,pageNum,pageSize);
        out.write(JSON.toJSONString(list));
    }

    /**
     * CEO 和 渠道管理员视图
     */
    @RequestMapping("dceoassessmenttodo")
    public ModelAndView dceoAssessmentTodo(){
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        Staff staff = userService.findStaff(username);
        if (staff == null){
            throw new RuntimeException("用户不存在!");
        }
        List<String> districtIds = userService.findDistrictByStaff (staff.getCssStaffNumber());
        if (districtIds.size() == 0){
            throw new UnsupportedOperationException("该工号未挂载到相应分局!");
        }

        ModelAndView mv = new ModelAndView("dceoassessmenttodo");
        List<Assessment> assessments = new ArrayList<>();
        for (String districtId : districtIds) {
            assessments.addAll( assessmentService.findDCEOToDoAssessments(Long.parseLong(districtId)));
        }
        double totalReward=0,totalDoubleReward=0;
        for (Assessment assessment:assessments){
            totalDoubleReward += assessment.getDoubleReward().doubleValue();
            totalReward += assessment.getReward().doubleValue();
        }
        mv.getModel().put("assessments",assessments);
        mv.getModel().put("totalReward",totalReward);
        mv.getModel().put("totalDoubleReward",totalDoubleReward);

        return mv;
    }

    /**
     * 分局CEO视图，查看片区考核项视图
     * @param billingCycle
     * @return
     */
    @RequestMapping("dceoassessmentlist")
    public ModelAndView dceoAssessmentTodo(@RequestParam(value = "billingcycle",required = false) int billingCycle){
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        Staff staff = userService.findStaff(username);
        if (staff == null){
            throw new RuntimeException("用户不存在!");
        }
        List<Assessment> assessments = new ArrayList<Assessment>() ;
        if (billingCycle > 0) {

            List < String > districtIds = userService.findDistrictByStaff(staff.getCssStaffNumber());
            if (districtIds.size() == 0) {
                throw new UnsupportedOperationException("该工号未挂载到相应分局!");
            }
            for (String districtId : districtIds) {
                assessments.addAll(assessmentService.findAssessmentByDistrict(Long.parseLong(districtId), billingCycle));
            }
        }
        ModelAndView mv = new ModelAndView("dceoassessmentlist");
        mv.getModel().put("assessments",assessments);
        List<BillingCycle> cycles = assessmentService.findAllCycles();
        mv.getModel().put("cycles",cycles);
        Map totalMap = AssessmentHelper.makeSummary(assessments);
        mv.getModel().put("totalReward",totalMap.get("totalReward"));
        mv.getModel().put("totalDoubleReward",totalMap.get("totalDoubleReward"));
        return mv;
    }

    @RequestMapping("dceostaffassessment")
    public ModelAndView dceoStaffAssessment(@RequestParam(value = "billingcycle",required = false) int cycle){
        Subject subject = SecurityUtils.getSubject();
        Staff staff = userService.findStaff((String) subject.getPrincipal());
        List<String> districtIds = userService.findDistrictByStaff(staff.getCssStaffNumber());
        if (districtIds.size() == 0){
            throw new UnsupportedOperationException("该工号未挂载到相应分局!");
        }
        ModelAndView mv = new ModelAndView("dceostaffassessment");
        List<AssessmentWithDetail> assessments = new ArrayList<>();
        List<BillingCycle> cycles = assessmentService.findAllCycles();

        if (cycle > 0) {
            for (String districtId : districtIds) {
                assessments.addAll( assessmentService.findStaffAssessmentForDistrictAndCycle(Long.parseLong(districtId), cycle));
            }
        }
        mv.getModel().put("assessments",assessments);
        mv.getModel().put("cycles",cycles);
        Map totalMap = AssessmentHelper.makeSummaryWithDetail(assessments);
        mv.getModel().put("totalReward",totalMap.get("totalReward"));
        mv.getModel().put("averageScore",totalMap.get("averageScore"));
        mv.getModel().put("personalReward",totalMap.get("personalReward"));
        return mv;
    }

    /**
     * 区域支撑视图
     * @param cycle
     * @return
     */
    @RequestMapping("areastaffassessment")
    public ModelAndView areaStaffAssessment(@RequestParam(value = "billingcycle",required = false) int cycle){
        Subject subject = SecurityUtils.getSubject();
        Staff staff = userService.findStaff((String) subject.getPrincipal());
        String areaId = userService.findAreaByStaff(staff.getCssStaffNumber());
        if (areaId == null){
            throw new UnsupportedOperationException("该工号未挂载到相应分局!");
        }
        ModelAndView mv = new ModelAndView("areastaffassessment");
        List<AssessmentWithDetail> assessments ;
        List<BillingCycle> cycles = assessmentService.findAllCycles();
        if (cycle > 0)
            assessments = assessmentService.findStaffAssessmentForAreaAndCycle(Long.parseLong(areaId),cycle) ;
        else
            assessments = new ArrayList<>();

        mv.getModel().put("assessments",assessments);
        mv.getModel().put("cycles",cycles);
        Map totalMap = AssessmentHelper.makeSummaryWithDetail(assessments);
        mv.getModel().put("totalReward",totalMap.get("totalReward"));
        mv.getModel().put("averageScore",totalMap.get("averageScore"));
        mv.getModel().put("personalReward",totalMap.get("personalReward"));
        return mv;
    }

    /**
     * 区域支撑 查看区域内考核片区列表
     * @param billingCycle
     * @return
     */
    @RequestMapping("areaassessmentlist")
    public ModelAndView areaAssessmentList(@RequestParam(value = "billingcycle",required = false) int billingCycle){
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        Staff staff = userService.findStaff(username);
        if (staff == null){
            throw new RuntimeException("用户不存在!");
        }
        String areaId = userService.findAreaByStaff(staff.getCssStaffNumber());
        if (areaId == null){
            throw new UnsupportedOperationException("该工号未挂载到相应分局!");
        }

        ModelAndView mv = new ModelAndView("areaassessmentlist");
        List<Assessment> assessments = billingCycle == 0 ?   new ArrayList<Assessment>() :
                assessmentService.findAssessmentByArea (Long.parseLong(areaId),billingCycle);
        double totalReward = 0;
        for (Assessment assessment:assessments){
            totalReward += assessment.getDoubleReward().doubleValue();
        }
        mv.getModel().put("assessments",assessments);
        List<BillingCycle> cycles = assessmentService.findAllCycles();
        mv.getModel().put("cycles",cycles);
        mv.getModel().put("totalReward",totalReward);
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/doaddcycle",method = RequestMethod.POST )
    public void doAddCycle(@RequestParam("billingCycle")int billingCycle,HttpServletResponse response) throws IOException {

        PrintWriter out = HttpResponseHelper.getUtf8Writer(response);
        if (BillingCycleHelper.parseBillingCycle(billingCycle) &&  assessmentService.addBillingCycle(billingCycle)>0){
            out.write("处理成功!");
        }
        else {
            out.write("处理失败!");
        }
    }

    @RequestMapping("/billingcycle")
    public ModelAndView modifyBillingCycle(){
        ModelAndView view  = new ModelAndView("adminbillingcycle");
        List<BillingCycle> cycles = assessmentService.findAllCycles();
        view.getModel().put("cycles",cycles);
        return view;
    }

    //渠道审阅完数据，通知ceo分配考核数据
    @ResponseBody
    @RequestMapping("/donotifycycle")
    public void notifyBillingCycle(@RequestParam("billingCycle")int billingCycle,HttpServletResponse response) throws IOException {
        billingCycle = 201704; //for test
        PrintWriter out = HttpResponseHelper.getUtf8Writer(response);
        Subject subject = SecurityUtils.getSubject();
        if (subject.hasRole(UserHelper.RoleType.ADMIN.name())){
            if (assessmentService.notifyBillingCycle(billingCycle)>0){
                out.write("操作成功!");
                return;
            }
        }
        out.write("操作失败!");
    }

    @RequestMapping("/adminauditview")
    public ModelAndView areaAuditView(@RequestParam long areaId,@RequestParam int billingCycle){
        ModelAndView view  = new ModelAndView("areaauditview");
        List<BillingCycle> cycles = assessmentService.findAllCycles();
        view.getModel().put("cycles",cycles);
        return view;
    }


    /**
     * 区域管理层对分局的上报结果进行审阅
     * @param assIds
     * @param suggestion
     * @param remark
     * @param response
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/doauditDistricts",method = RequestMethod.POST )
    public void doAreaAuditByArea(@RequestParam("assessmentIds")String assIds
            ,@RequestParam("suggestion") String suggestion
            ,@RequestParam("remark") String remark
            ,@RequestParam("billingCycle") int billingCycle
            ,HttpServletResponse response) throws IOException {

        PrintWriter out = HttpResponseHelper.getUtf8Writer(response);

        try {
            AuditNodeHelper.SuggestionType suggestionType = AuditNodeHelper.SuggestionType.valueOf(suggestion);
            Subject subject = SecurityUtils.getSubject();
            Staff staff = userService.findStaff((String) subject.getPrincipal());
            String areaId = userService.findAreaByStaff(staff.getCssStaffNumber());
            if (!subject.hasRole(RoleType.QADMIN.name())){
                out.write("无权限操作相应的分局！");
                return;
            }

            List<Long> districtIds = new ArrayList();
            for (String id : assIds.split(",")){
                districtIds.add(Long.valueOf(id));
            }
            if (assessmentService.areaHasAllDistricts(Long.valueOf(areaId),districtIds)){
                out.write("无权限操作相应的分局！");
                return;
            }

            String suggestionRemark = remark==null?suggestionType.getSuggestion():remark;
            AuditLog auditLog = AuditNodeHelper.makeAuditLog(0,
                    0,
                    suggestionRemark,
                    suggestionType.getSuggestion(),
                    null,
                    null,
                    null,
                    Long.valueOf(staff.getCssStaffNumber()),
                    staff.getName());
            if (assessmentService.auditDistricts(billingCycle,districtIds,suggestionType,auditLog,AssessmentNode.DRV)>0){
                out.write("操作成功！");
                return;
            }

            out.write("操作失败！");
            return;
        }
        catch (Exception e){
            e.printStackTrace();
            out.write("操作失败！"+e.getMessage());
            return;
        }

    }

    /**
     * 区域管理层对分局的上报签收表进行审核
     * @param assIds
     * @param suggestion
     * @param remark
     * @param response
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/doreviewDistricts",method = RequestMethod.POST )
    public void doAreaReviewByArea(@RequestParam("assessmentIds")String assIds
            ,@RequestParam("suggestion") String suggestion
            ,@RequestParam("remark") String remark
            ,@RequestParam("billingCycle") int billingCycle
            ,HttpServletResponse response) throws IOException {

        PrintWriter out = HttpResponseHelper.getUtf8Writer(response);

        try {
            AuditNodeHelper.SuggestionType suggestionType = AuditNodeHelper.SuggestionType.valueOf(suggestion);
            Subject subject = SecurityUtils.getSubject();
            Staff staff = userService.findStaff((String) subject.getPrincipal());
            String areaId = userService.findAreaByStaff(staff.getCssStaffNumber());
            if (!subject.hasRole(RoleType.QADMIN.name())){
                out.write("无权限操作相应的分局！");
                return;
            }

            List<Long> districtIds = new ArrayList();
            for (String id : assIds.split(",")){
                districtIds.add(Long.valueOf(id));
            }
            if (assessmentService.areaHasAllDistricts(Long.valueOf(areaId),districtIds)){
                out.write("无权限操作相应的分局！");
                return;
            }

            String suggestionRemark = remark==null?suggestionType.getSuggestion():remark;
            AuditLog auditLog = AuditNodeHelper.makeAuditLog(0,
                    0,
                    suggestionRemark,
                    suggestionType.getSuggestion(),
                    null,
                    null,
                    null,
                    Long.valueOf(staff.getCssStaffNumber()),
                    staff.getName());
            if (assessmentService.auditDistricts(billingCycle,districtIds,suggestionType,auditLog,AssessmentNode.AUD)>0){
                out.write("操作成功！");
                return;
            }

            out.write("操作失败！");
            return;
        }
        catch (Exception e){
            e.printStackTrace();
            out.write("操作失败！"+e.getMessage());
            return;
        }

    }

    /**
     * 分局长审阅片区数据
     * @param assessmentId
     * @param suggestion
     * @param remark
     * @param response
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/doreview",method = RequestMethod.POST )
    public void doReview(@RequestParam("assessmentid")int assessmentId
            ,@RequestParam("suggestion") String suggestion
            ,@RequestParam("remark") String remark
            ,HttpServletResponse response) throws IOException {
        PrintWriter out = HttpResponseHelper.getUtf8Writer(response);
        try {
            AuditNodeHelper.SuggestionType suggestionType = AuditNodeHelper.SuggestionType.valueOf(suggestion);
            Subject subject = SecurityUtils.getSubject();
            Staff staff = userService.findStaff((String) subject.getPrincipal());
            List<String> districtIds = userService.findDistrictByStaff(staff.getCssStaffNumber());
            boolean hasOwnedZone = false;
            for (String districtId : districtIds){
                hasOwnedZone = assessmentService.districtHasAssessment(Long.valueOf(districtId),assessmentId);
                if (hasOwnedZone)
                    break;
            }

            if (staff == null || !hasOwnedZone){
                out.write("当前账号无权限操作!");
                return;
            }

            Assessment assessment = assessmentService.getAssessment(assessmentId);
            if (assessment == null){
                out.write("考核ID不存在，操作失败！");
                return;
            }
            if (!assessment.getState().equals("FED")){
                out.write("考核ID状态异常，操作失败！");
                return;
            }

            if (assessmentService.reAuditAssessment(assessmentId,suggestionType,remark,AssessmentNode.SGN,staff)>0){
                out.write("操作成功！");
                return;
            }
            out.write("操作失败！");
            return;
        }
        catch (Exception e){
            e.printStackTrace();
            out.write("操作失败！");
            return;
        }

    }

    @ResponseBody
    @RequestMapping(value = "/doreviewall",method = RequestMethod.POST )
    public void doReviewAll(
            @RequestParam("suggestion") String suggestion
            ,@RequestParam("remark") String remark
            ,@RequestParam("billingCycle") int billingCycle
            ,HttpServletResponse response) throws IOException {

        PrintWriter out = HttpResponseHelper.getUtf8Writer(response);

        try {
            AuditNodeHelper.SuggestionType suggestionType = AuditNodeHelper.SuggestionType.valueOf(suggestion);
            Subject subject = SecurityUtils.getSubject();
            Staff staff = userService.findStaff((String) subject.getPrincipal());
            String areaId = userService.findAreaByStaff(staff.getCssStaffNumber());
            if (!subject.hasRole(RoleType.ADMIN.name())){
                out.write("无权限操作！");
                return;
            }

            if (assessmentService.auditAll(billingCycle,suggestionType,remark,AssessmentNode.ARV,staff)>0){
                out.write("操作成功！");
                return;
            }

            out.write("操作失败！");
            return;
        }
        catch (Exception e){
            e.printStackTrace();
            out.write("操作失败！"+e.getMessage());
            return;
        }

    }

    @ResponseBody
    @RequestMapping(value = "/doauditall",method = RequestMethod.POST )
    public void doAuditAll(
            @RequestParam("suggestion") String suggestion
            ,@RequestParam("remark") String remark
            ,@RequestParam("billingCycle") int billingCycle
            ,HttpServletResponse response) throws IOException {

        PrintWriter out = HttpResponseHelper.getUtf8Writer(response);

        try {
            AuditNodeHelper.SuggestionType suggestionType = AuditNodeHelper.SuggestionType.valueOf(suggestion);
            Subject subject = SecurityUtils.getSubject();
            Staff staff = userService.findStaff((String) subject.getPrincipal());
            String areaId = userService.findAreaByStaff(staff.getCssStaffNumber());
            if (!subject.hasRole(RoleType.ADMIN.name())){
                out.write("无权限操作！");
                return;
            }

            if (assessmentService.auditAll(billingCycle,suggestionType,remark,AssessmentNode.AAU,staff)>0){
                out.write("操作成功！");
                return;
            }

            out.write("操作失败！");
            return;
        }
        catch (Exception e){
            e.printStackTrace();
            out.write("操作失败！"+e.getMessage());
            return;
        }

    }

}
