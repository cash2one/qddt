package com.telecomjs.mappers;

import com.telecomjs.beans.ZoneMarkManual;
import org.apache.ibatis.annotations.Param;

public interface ZoneMarkManualMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ZONE_MARK_MANUAL
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long markId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ZONE_MARK_MANUAL
     *
     * @mbggenerated
     */
    int insert(ZoneMarkManual record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ZONE_MARK_MANUAL
     *
     * @mbggenerated
     */
    int insertSelective(ZoneMarkManual record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ZONE_MARK_MANUAL
     *
     * @mbggenerated
     */
    ZoneMarkManual selectByPrimaryKey(Long markId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ZONE_MARK_MANUAL
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ZoneMarkManual record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ZONE_MARK_MANUAL
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ZoneMarkManual record);

    int insertByUpload(@Param("eventId") int eventId, @Param("code") String code);
}