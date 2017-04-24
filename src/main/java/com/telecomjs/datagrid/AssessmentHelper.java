package com.telecomjs.datagrid;

import com.alibaba.fastjson.JSON;
import com.telecomjs.beans.*;
import com.telecomjs.utils.PoiExcelReader;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by zark on 17/3/9.
 */
public class AssessmentHelper {
    public static Map parseExcel(MultipartFile file ){
        try {
            //生成 event表和assessment 状态是init
            InputStream is = file.getInputStream();
            short flag = (short) (file.getOriginalFilename().endsWith("xlsx")?1:0);
            PoiExcelReader reader = new PoiExcelReader(is,flag);
            reader.open();
            String[] titles = reader.readExcelTitle();
            Iterator iterator = reader.iterator();
            List list = new ArrayList();
            int rowNumber = 1;
            while (iterator.hasNext()) {
                String[] row = (String[]) iterator.next();
                list.add(row);
                rowNumber++;
            }
            //logger.info("INFO ROWS = [%d]: " + rowNumber);
            if (reader != null)
                reader.close();

            //取系统模板化的标题
            DataColumn[] titleset={
                    new DataColumn("areaName","区县")
                    ,new DataColumn("districtName","支局")
                    ,new DataColumn("zoneName","片区名称")
                    ,new DataColumn("zoneId","营销编码")
                    ,new DataColumn("zoneAttr","片区属性")
                    ,new DataColumn("zoneChannelCode","渠道ID")
                    ,new DataColumn("channelName","门店名称")
                    ,new DataColumn("score","当月考核得分")
                    ,new DataColumn("criterion","对投标准(单位：元）")
                    ,new DataColumn("lastReward","当月应发对投")
                    ,new DataColumn("lastForwardReward","当月预发对投")
                    ,new DataColumn("lastSettlement","当月对投清算")
                    ,new DataColumn("forwardReward","下月对投预发")
                    ,new DataColumn("reward","本期对投实发")
            };

            List rows = new ArrayList();
            for (Object row : list){
                /*System.out.print(JSON.toJSONString(row));
                Map map = new HashMap();
                for (int i=0;i<titleset.length;i++){
                    map.put(titleset[i].field,((String[])row)[i]);
                }
                rows.add(map);*/
                rows.add(parseAssessment((String[]) row));
            }

            Map map = new HashMap();
            map.put("titles",titleset);
            map.put("rows",rows);
            return map;
        } catch (IOException e){
            throw  new RuntimeException("解析excel文件失败!");
        }
    }

    public static Assessment parseAssessment(String[] arr){
        //按数组下标对应
        Assessment assessment = new Assessment();
        assessment.setAreaName(arr[0]);
        assessment.setDistrictName(arr[1]);
        assessment.setZoneName(arr[2]);
        assessment.setZoneId(Long.parseLong(arr[3]));
        assessment.setZoneAttr(arr[4]);
        assessment.setZoneChannelCode(Long.parseLong(arr[5]));
        assessment.setChannelName(arr[6]);
        assessment.setScore(arr[7].length()>0?new BigDecimal(arr[7]):new BigDecimal(0));
        assessment.setCriterion(arr[8].length()>0?new BigDecimal(arr[8]):new BigDecimal(0));
        assessment.setLastReward(arr[9].length()>0?new BigDecimal(arr[9]):new BigDecimal(0));
        assessment.setLastForwardReward(arr[10].length()>0?new BigDecimal(arr[10]):new BigDecimal(0));
        assessment.setLastSettlement(arr[11].length()>0?new BigDecimal(arr[11]):new BigDecimal(0));
        assessment.setForwardReward(arr[12].length()>0?new BigDecimal(arr[12]):new BigDecimal(0));
        assessment.setReward(arr[13].length()>0?new BigDecimal(arr[13]):new BigDecimal(0));
        assessment.setDoubleReward( new BigDecimal(assessment.getReward().doubleValue()*2 ));
        assessment.setState("INI");
        assessment.setCreateDate(new Date());
        return assessment;
    }

    //创建event实体
    public static AssessmentEvent createEvent(String filename ,int eventId ,int staffId,int billingCycleId ,int count){
        AssessmentEvent event = new AssessmentEvent();
        event.setCreateDate(new Date());
        event.setState("INI");
        event.setStateDate(new Date());
        event.setStaffId(staffId);
        event.setAmount(new BigDecimal("0"));
        event.setBillingCycleId(billingCycleId);
        event.setFileName(filename);
        event.setRecordRow(count);
        event.setEventId(eventId);
        return event;
    }

    public static  boolean equalsReward(double num1,double num2)
    {
        if((num1-num2>-0.000001)&&(num1-num2)<0.000001)return true;
        else return false;
    }

    public static Map makeSummaryWithDetail(List<AssessmentWithDetail> assessments){
        double totalReward=0,averageScore=0,personalReward=0;
        for (AssessmentWithDetail assessment:assessments){
            totalReward += assessment.getDoubleReward().doubleValue();
            averageScore += assessment.getScore().doubleValue();
            for (StaffAssessment sa :assessment.getStaffAssessmentList())
                personalReward += sa.getPersonalAmount().doubleValue();
        }
        averageScore = assessments.size()>0?averageScore/assessments.size():averageScore;
        Map map  = new HashMap();
        map.put("totalReward",totalReward);
        map.put("averageScore",averageScore);
        map.put("personalReward",personalReward);
        return map;
    }

    public static Map makeSummary(List<Assessment> assessments){
        double totalReward=0,totalDoubleReward=0;
        for (Assessment assessment:assessments){
            totalDoubleReward += assessment.getDoubleReward().doubleValue();
            totalReward += assessment.getReward().doubleValue();
        }
        Map map  = new HashMap();
        map.put("totalDoubleReward",totalDoubleReward);
        map.put("totalReward",totalReward);
        return map;
    }

    //public static AssessmentLog makeAssessmentLog(int id,int assessmentId,)
    public static AssessmentLog makeAssessmentLog(Integer logId, Integer assessmentId, String operation,
                                                  Long cssStaffId, String staffName, String preState,
                                                  String nextState, Date stateDate) {
        AssessmentLog log = new AssessmentLog();
        log.setLogId(logId);
        log.setAssessmentId(assessmentId);
        log.setOperation(operation);
        log.setCssStaffId(cssStaffId);
        log.setStaffName(staffName);
        log.setPreState(preState);
        log.setNextState(nextState);
        log.setStateDate(new Date());
        return log;
    }

}
