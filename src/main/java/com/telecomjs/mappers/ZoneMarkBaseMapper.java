package com.telecomjs.mappers;

import com.telecomjs.beans.ZoneMarkBase;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZoneMarkBaseMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ZONE_MARK_BASE
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long markId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ZONE_MARK_BASE
     *
     * @mbggenerated
     */
    int insert(ZoneMarkBase record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ZONE_MARK_BASE
     *
     * @mbggenerated
     */
    int insertSelective(ZoneMarkBase record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ZONE_MARK_BASE
     *
     * @mbggenerated
     */
    ZoneMarkBase selectByPrimaryKey(Long markId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ZONE_MARK_BASE
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ZoneMarkBase record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ZONE_MARK_BASE
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ZoneMarkBase record);

    List<ZoneMarkBase> findAllByCycle(@Param("billingCycle") int billingCycle);

    int insertByUpload(@Param("eventId") int eventId);

    ZoneMarkBase getByIdAndCycle(@Param("zoneId") Long zoneId, @Param("billingCycle") Long billingCycle);
}