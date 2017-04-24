package com.telecomjs.mappers;

import com.telecomjs.beans.ZoneMarkUpload;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZoneMarkUploadMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ZONE_MARK_UPLOAD
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long markId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ZONE_MARK_UPLOAD
     *
     * @mbggenerated
     */
    int insert(ZoneMarkUpload record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ZONE_MARK_UPLOAD
     *
     * @mbggenerated
     */
    int insertSelective(ZoneMarkUpload record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ZONE_MARK_UPLOAD
     *
     * @mbggenerated
     */
    ZoneMarkUpload selectByPrimaryKey(Long markId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ZONE_MARK_UPLOAD
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ZoneMarkUpload record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ZONE_MARK_UPLOAD
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ZoneMarkUpload record);

    Long getPrimaryKey();

    List<ZoneMarkUpload> findByEvent(@Param("eventId") int eventId);
}