package com.telecomjs.beans;

import java.util.Date;

public class AuditLog {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AUDIT_LOG.AUDIT_ID
     *
     * @mbggenerated
     */
    private Integer auditId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AUDIT_LOG.ASSESSMENT_ID
     *
     * @mbggenerated
     */
    private Integer assessmentId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AUDIT_LOG.NODE_NAME
     *
     * @mbggenerated
     */
    private String nodeName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AUDIT_LOG.NODE_STAFF
     *
     * @mbggenerated
     */
    private Long nodeStaff;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AUDIT_LOG.SUBMISSION
     *
     * @mbggenerated
     */
    private String submission;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AUDIT_LOG.REMARK
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AUDIT_LOG.STATE_DATE
     *
     * @mbggenerated
     */
    private Date stateDate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AUDIT_LOG.AUDIT_ID
     *
     * @return the value of AUDIT_LOG.AUDIT_ID
     *
     * @mbggenerated
     */
    public Integer getAuditId() {
        return auditId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AUDIT_LOG.AUDIT_ID
     *
     * @param auditId the value for AUDIT_LOG.AUDIT_ID
     *
     * @mbggenerated
     */
    public void setAuditId(Integer auditId) {
        this.auditId = auditId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AUDIT_LOG.ASSESSMENT_ID
     *
     * @return the value of AUDIT_LOG.ASSESSMENT_ID
     *
     * @mbggenerated
     */
    public Integer getAssessmentId() {
        return assessmentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AUDIT_LOG.ASSESSMENT_ID
     *
     * @param assessmentId the value for AUDIT_LOG.ASSESSMENT_ID
     *
     * @mbggenerated
     */
    public void setAssessmentId(Integer assessmentId) {
        this.assessmentId = assessmentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AUDIT_LOG.NODE_NAME
     *
     * @return the value of AUDIT_LOG.NODE_NAME
     *
     * @mbggenerated
     */
    public String getNodeName() {
        return nodeName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AUDIT_LOG.NODE_NAME
     *
     * @param nodeName the value for AUDIT_LOG.NODE_NAME
     *
     * @mbggenerated
     */
    public void setNodeName(String nodeName) {
        this.nodeName = nodeName == null ? null : nodeName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AUDIT_LOG.NODE_STAFF
     *
     * @return the value of AUDIT_LOG.NODE_STAFF
     *
     * @mbggenerated
     */
    public Long getNodeStaff() {
        return nodeStaff;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AUDIT_LOG.NODE_STAFF
     *
     * @param nodeStaff the value for AUDIT_LOG.NODE_STAFF
     *
     * @mbggenerated
     */
    public void setNodeStaff(Long nodeStaff) {
        this.nodeStaff = nodeStaff;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AUDIT_LOG.SUBMISSION
     *
     * @return the value of AUDIT_LOG.SUBMISSION
     *
     * @mbggenerated
     */
    public String getSubmission() {
        return submission;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AUDIT_LOG.SUBMISSION
     *
     * @param submission the value for AUDIT_LOG.SUBMISSION
     *
     * @mbggenerated
     */
    public void setSubmission(String submission) {
        this.submission = submission == null ? null : submission.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AUDIT_LOG.REMARK
     *
     * @return the value of AUDIT_LOG.REMARK
     *
     * @mbggenerated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AUDIT_LOG.REMARK
     *
     * @param remark the value for AUDIT_LOG.REMARK
     *
     * @mbggenerated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AUDIT_LOG.STATE_DATE
     *
     * @return the value of AUDIT_LOG.STATE_DATE
     *
     * @mbggenerated
     */
    public Date getStateDate() {
        return stateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AUDIT_LOG.STATE_DATE
     *
     * @param stateDate the value for AUDIT_LOG.STATE_DATE
     *
     * @mbggenerated
     */
    public void setStateDate(Date stateDate) {
        this.stateDate = stateDate;
    }
}