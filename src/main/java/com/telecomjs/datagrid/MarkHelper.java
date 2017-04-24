package com.telecomjs.datagrid;

import com.telecomjs.beans.Assessment;
import com.telecomjs.beans.ZoneMarkEvent;
import com.telecomjs.beans.ZoneMarkUpload;
import com.telecomjs.utils.PoiExcelReader;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by zark on 17/4/20.
 */
public class MarkHelper {
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
                    new DataColumn("zoneNmae","片区名称")
                    ,new DataColumn("zoneId","营销编码")
                    ,new DataColumn("channelType","门店类型")
                    ,new DataColumn("delegateType","承包类型")
                    ,new DataColumn("standardMark","标准化得分")
                    ,new DataColumn("areaMark","区域打分")
                    ,new DataColumn("criterion","对投标准(单位：元）")
            };

            List rows = new ArrayList();
            for (Object row : list){
                rows.add(parseMarkUpload((String[]) row));
            }

            Map map = new HashMap();
            map.put("titles",titleset);
            map.put("rows",rows);
            return map;
        } catch (IOException e){
            throw  new RuntimeException("解析excel文件失败!");
        }
    }
    public static ZoneMarkUpload parseMarkUpload(String[] arr){
        //按数组下标对应
        ZoneMarkUpload one = new ZoneMarkUpload();
        one.setStateDate(new Date());
        one.setState(AssessmentStateHelper.AssessmentNode.INI.name());

        one.setZoneName(arr[0]);
        one.setZoneId(Long.parseLong(arr[1]));
        one.setChannelType(arr[2]);
        one.setDelegateType(arr[3]);
        one.setStandardMark(arr[4].length()>0?new BigDecimal(arr[4]):new BigDecimal(0));
        one.setAreaMark(arr[5].length()>0?new BigDecimal(arr[5]):new BigDecimal(0));
        one.setCriterion(arr[6].length()>0?new BigDecimal(arr[6]):new BigDecimal(0));
        return one;
    }

    public static ZoneMarkEvent createEvent(long id,int billingCycle,long cssNumber,long rows,String state){
        ZoneMarkEvent event = new ZoneMarkEvent();
        event.setState(state);
        event.setStateDate(new Date());
        event.setBillingCycle(billingCycle);
        event.setCssNumber(cssNumber);
        event.setEventId(id);
        event.setRecordNumbers(rows);
        return event;
    }

    public static enum MarkItemCode{
        JFDF,
        STLDF,
        ZWDF,
        JCDF,
        QYDF
    }
}
