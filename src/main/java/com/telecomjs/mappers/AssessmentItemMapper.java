package com.telecomjs.mappers;

import com.telecomjs.beans.AssessmentItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AssessmentItemMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ASSESSMENT_ITEM
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer itemId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ASSESSMENT_ITEM
     *
     * @mbggenerated
     */
    int insert(AssessmentItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ASSESSMENT_ITEM
     *
     * @mbggenerated
     */
    int insertSelective(AssessmentItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ASSESSMENT_ITEM
     *
     * @mbggenerated
     */
    AssessmentItem selectByPrimaryKey(Integer itemId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ASSESSMENT_ITEM
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(AssessmentItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ASSESSMENT_ITEM
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(AssessmentItem record);

    List<AssessmentItem> findItemsByType(@Param("type") String type);
}