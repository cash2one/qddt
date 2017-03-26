package com.telecomjs.mappers;

import com.telecomjs.beans.AssessmentSubscriber;
import com.telecomjs.datagrid.AuditNodeHelper;
import org.apache.ibatis.annotations.Param;

public interface AssessmentSubscriberMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ASSESSMENT_SUBSCRIBER
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer subscriberId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ASSESSMENT_SUBSCRIBER
     *
     * @mbggenerated
     */
    int insert(AssessmentSubscriber record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ASSESSMENT_SUBSCRIBER
     *
     * @mbggenerated
     */
    int insertSelective(AssessmentSubscriber record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ASSESSMENT_SUBSCRIBER
     *
     * @mbggenerated
     */
    AssessmentSubscriber selectByPrimaryKey(Integer subscriberId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ASSESSMENT_SUBSCRIBER
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(AssessmentSubscriber record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ASSESSMENT_SUBSCRIBER
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(AssessmentSubscriber record);

    int insertByEvent(@Param("eventId") int eventId);

    int feedbackAssessment(@Param("assessmentId") int assessmentId);


    int auditAssessmentWithAgree(@Param("assessmentId") int assessmentId );

    int auditAssessmentWithDisagree(@Param("assessmentId") int assessmentId);
}