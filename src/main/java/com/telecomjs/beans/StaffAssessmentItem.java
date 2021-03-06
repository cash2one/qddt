package com.telecomjs.beans;

import java.math.BigDecimal;
import java.util.Date;

public class StaffAssessmentItem {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STAFF_ASSESSMENT_ITEM.ID
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STAFF_ASSESSMENT_ITEM.STAFF_ASSESSMENT_ID
     *
     * @mbggenerated
     */
    private Integer staffAssessmentId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STAFF_ASSESSMENT_ITEM.ITEM_ID
     *
     * @mbggenerated
     */
    private Integer itemId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STAFF_ASSESSMENT_ITEM.ROLE_CODE
     *
     * @mbggenerated
     */
    private String roleCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STAFF_ASSESSMENT_ITEM.ASSESSMENT_PERCENT
     *
     * @mbggenerated
     */
    private Short assessmentPercent;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STAFF_ASSESSMENT_ITEM.ITEM_NAME
     *
     * @mbggenerated
     */
    private String itemName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STAFF_ASSESSMENT_ITEM.CRITERION_SCORE
     *
     * @mbggenerated
     */
    private BigDecimal criterionScore;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STAFF_ASSESSMENT_ITEM.CRITERION_DETAIL
     *
     * @mbggenerated
     */
    private String criterionDetail;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STAFF_ASSESSMENT_ITEM.STATE
     *
     * @mbggenerated
     */
    private String state;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STAFF_ASSESSMENT_ITEM.STATE_DATE
     *
     * @mbggenerated
     */
    private Date stateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STAFF_ASSESSMENT_ITEM.CATEGORY_ID
     *
     * @mbggenerated
     */
    private Short categoryId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STAFF_ASSESSMENT_ITEM.SCORE
     *
     * @mbggenerated
     */
    private BigDecimal score;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STAFF_ASSESSMENT_ITEM.ID
     *
     * @return the value of STAFF_ASSESSMENT_ITEM.ID
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STAFF_ASSESSMENT_ITEM.ID
     *
     * @param id the value for STAFF_ASSESSMENT_ITEM.ID
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STAFF_ASSESSMENT_ITEM.STAFF_ASSESSMENT_ID
     *
     * @return the value of STAFF_ASSESSMENT_ITEM.STAFF_ASSESSMENT_ID
     *
     * @mbggenerated
     */
    public Integer getStaffAssessmentId() {
        return staffAssessmentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STAFF_ASSESSMENT_ITEM.STAFF_ASSESSMENT_ID
     *
     * @param staffAssessmentId the value for STAFF_ASSESSMENT_ITEM.STAFF_ASSESSMENT_ID
     *
     * @mbggenerated
     */
    public void setStaffAssessmentId(Integer staffAssessmentId) {
        this.staffAssessmentId = staffAssessmentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STAFF_ASSESSMENT_ITEM.ITEM_ID
     *
     * @return the value of STAFF_ASSESSMENT_ITEM.ITEM_ID
     *
     * @mbggenerated
     */
    public Integer getItemId() {
        return itemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STAFF_ASSESSMENT_ITEM.ITEM_ID
     *
     * @param itemId the value for STAFF_ASSESSMENT_ITEM.ITEM_ID
     *
     * @mbggenerated
     */
    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STAFF_ASSESSMENT_ITEM.ROLE_CODE
     *
     * @return the value of STAFF_ASSESSMENT_ITEM.ROLE_CODE
     *
     * @mbggenerated
     */
    public String getRoleCode() {
        return roleCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STAFF_ASSESSMENT_ITEM.ROLE_CODE
     *
     * @param roleCode the value for STAFF_ASSESSMENT_ITEM.ROLE_CODE
     *
     * @mbggenerated
     */
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode == null ? null : roleCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STAFF_ASSESSMENT_ITEM.ASSESSMENT_PERCENT
     *
     * @return the value of STAFF_ASSESSMENT_ITEM.ASSESSMENT_PERCENT
     *
     * @mbggenerated
     */
    public Short getAssessmentPercent() {
        return assessmentPercent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STAFF_ASSESSMENT_ITEM.ASSESSMENT_PERCENT
     *
     * @param assessmentPercent the value for STAFF_ASSESSMENT_ITEM.ASSESSMENT_PERCENT
     *
     * @mbggenerated
     */
    public void setAssessmentPercent(Short assessmentPercent) {
        this.assessmentPercent = assessmentPercent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STAFF_ASSESSMENT_ITEM.ITEM_NAME
     *
     * @return the value of STAFF_ASSESSMENT_ITEM.ITEM_NAME
     *
     * @mbggenerated
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STAFF_ASSESSMENT_ITEM.ITEM_NAME
     *
     * @param itemName the value for STAFF_ASSESSMENT_ITEM.ITEM_NAME
     *
     * @mbggenerated
     */
    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STAFF_ASSESSMENT_ITEM.CRITERION_SCORE
     *
     * @return the value of STAFF_ASSESSMENT_ITEM.CRITERION_SCORE
     *
     * @mbggenerated
     */
    public BigDecimal getCriterionScore() {
        return criterionScore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STAFF_ASSESSMENT_ITEM.CRITERION_SCORE
     *
     * @param criterionScore the value for STAFF_ASSESSMENT_ITEM.CRITERION_SCORE
     *
     * @mbggenerated
     */
    public void setCriterionScore(BigDecimal criterionScore) {
        this.criterionScore = criterionScore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STAFF_ASSESSMENT_ITEM.CRITERION_DETAIL
     *
     * @return the value of STAFF_ASSESSMENT_ITEM.CRITERION_DETAIL
     *
     * @mbggenerated
     */
    public String getCriterionDetail() {
        return criterionDetail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STAFF_ASSESSMENT_ITEM.CRITERION_DETAIL
     *
     * @param criterionDetail the value for STAFF_ASSESSMENT_ITEM.CRITERION_DETAIL
     *
     * @mbggenerated
     */
    public void setCriterionDetail(String criterionDetail) {
        this.criterionDetail = criterionDetail == null ? null : criterionDetail.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STAFF_ASSESSMENT_ITEM.STATE
     *
     * @return the value of STAFF_ASSESSMENT_ITEM.STATE
     *
     * @mbggenerated
     */
    public String getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STAFF_ASSESSMENT_ITEM.STATE
     *
     * @param state the value for STAFF_ASSESSMENT_ITEM.STATE
     *
     * @mbggenerated
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STAFF_ASSESSMENT_ITEM.STATE_DATE
     *
     * @return the value of STAFF_ASSESSMENT_ITEM.STATE_DATE
     *
     * @mbggenerated
     */
    public Date getStateDate() {
        return stateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STAFF_ASSESSMENT_ITEM.STATE_DATE
     *
     * @param stateDate the value for STAFF_ASSESSMENT_ITEM.STATE_DATE
     *
     * @mbggenerated
     */
    public void setStateDate(Date stateDate) {
        this.stateDate = stateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STAFF_ASSESSMENT_ITEM.CATEGORY_ID
     *
     * @return the value of STAFF_ASSESSMENT_ITEM.CATEGORY_ID
     *
     * @mbggenerated
     */
    public Short getCategoryId() {
        return categoryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STAFF_ASSESSMENT_ITEM.CATEGORY_ID
     *
     * @param categoryId the value for STAFF_ASSESSMENT_ITEM.CATEGORY_ID
     *
     * @mbggenerated
     */
    public void setCategoryId(Short categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STAFF_ASSESSMENT_ITEM.SCORE
     *
     * @return the value of STAFF_ASSESSMENT_ITEM.SCORE
     *
     * @mbggenerated
     */
    public BigDecimal getScore() {
        return score;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STAFF_ASSESSMENT_ITEM.SCORE
     *
     * @param score the value for STAFF_ASSESSMENT_ITEM.SCORE
     *
     * @mbggenerated
     */
    public void setScore(BigDecimal score) {
        this.score = score;
    }
}