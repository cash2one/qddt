package com.telecomjs.mappers;

import com.telecomjs.beans.AppYdbpAreaZwstaff;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppYdbpAreaZwstaffMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table APP_YDBP_AREA_ZWSTAFF
     *
     * @mbggenerated
     */
    int insert(AppYdbpAreaZwstaff record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table APP_YDBP_AREA_ZWSTAFF
     *
     * @mbggenerated
     */
    int insertSelective(AppYdbpAreaZwstaff record);

    /**
     * 查找某个CSS工号
     */
    List selectByCss(@Param("cssNumber") String cssNumber);

    List<AppYdbpAreaZwstaff> findStaffByZone(@Param("zoneId") Long zoneId);

    AppYdbpAreaZwstaff findStaffByCssNumber(@Param("cssNumber") String cssNumber);

    List<AppYdbpAreaZwstaff> findZWStaffByStaffId(@Param("staffId") Integer staffId);

    List<AppYdbpAreaZwstaff> findZWStaffByDistrict(@Param("districtId") long districtId);

    List<AppYdbpAreaZwstaff> findZWStaffByArea(@Param("areaId") long areaId);
}