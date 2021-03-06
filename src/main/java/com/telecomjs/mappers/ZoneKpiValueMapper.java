package com.telecomjs.mappers;

import com.telecomjs.beans.ZoneKpiValue;
import org.apache.ibatis.annotations.Param;

public interface ZoneKpiValueMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ZONE_KPI_VALUE
     *
     * @mbggenerated
     */

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ZONE_KPI_VALUE
     *
     * @mbggenerated
     */
    int insert(ZoneKpiValue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ZONE_KPI_VALUE
     *
     * @mbggenerated
     */
    int insertSelective(ZoneKpiValue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ZONE_KPI_VALUE
     *
     * @mbggenerated
     */
    ZoneKpiValue selectByPrimaryKey(@Param("key") String key , @Param("billingCycle") String billingCycle);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ZONE_KPI_VALUE
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ZoneKpiValue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ZONE_KPI_VALUE
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ZoneKpiValue record);

    String getValueWithCycle(@Param("key") String key, @Param("billingCycle") int billingCycle);
}