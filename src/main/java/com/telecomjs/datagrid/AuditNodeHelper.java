package com.telecomjs.datagrid;

import com.telecomjs.beans.AuditLog;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zark on 17/3/13.
 */
public class AuditNodeHelper {
    public static enum NodeType {
        AreaAudit,DistrictAudit,ZoneAudit
    }
    public static String makeNode(NodeType type,String areaName,String districtName,String zoneName){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = "[%s]->[%s]->[%s]";
        String sDate = sdf.format(new Date());
        switch (type){
            case AreaAudit:
                return String.format(format,sDate,areaName,"区域审计");
            case DistrictAudit:
                return String.format(format,sDate,areaName+",@"+districtName,"分局审计");
            default:
                return String.format(format,sDate,areaName+",@"+districtName+",@"+zoneName,"片区区域");
        }
    }

    public static enum SuggestionType{
        Agree("已阅"),
        Disagree("退回");
        // 枚举对象的 RSS 地址的属性
        private String suggestion;

        // 枚举对象构造函数
        private SuggestionType(String suggestion) {
            this.suggestion = suggestion;
        }

        // 枚举对象获取 RSS 地址的方法
        public String getSuggestion() {
            return this.suggestion;
        }
    }

    public static AuditLog makeAuditLog(int id,int assessmentId,String remark,String suggestion
            ,String areaName,String districtName,String zoneName,Long cssNumber,String staffName){
        AuditLog auditLog = new AuditLog();
        auditLog.setAssessmentId(assessmentId);
        auditLog.setRemark(remark);
        auditLog.setNodeName(AuditNodeHelper.makeNode(NodeType.ZoneAudit,areaName,districtName,zoneName));
        auditLog.setNodeStaff(cssNumber);
        auditLog.setAuditId(id);
        auditLog.setStaffName(staffName);
        auditLog.setSubmission(suggestion);
        auditLog.setStateDate(new Date());

        return  auditLog;
    }


}
