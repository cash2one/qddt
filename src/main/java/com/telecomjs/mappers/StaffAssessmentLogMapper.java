package com.telecomjs.mappers;

import com.telecomjs.beans.StaffAssessmentLog;

public interface StaffAssessmentLogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STAFF_ASSESSMENT_LOG
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer logId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STAFF_ASSESSMENT_LOG
     *
     * @mbggenerated
     */
    int insert(StaffAssessmentLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STAFF_ASSESSMENT_LOG
     *
     * @mbggenerated
     */
    int insertSelective(StaffAssessmentLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STAFF_ASSESSMENT_LOG
     *
     * @mbggenerated
     */
    StaffAssessmentLog selectByPrimaryKey(Integer logId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STAFF_ASSESSMENT_LOG
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(StaffAssessmentLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STAFF_ASSESSMENT_LOG
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(StaffAssessmentLog record);
}