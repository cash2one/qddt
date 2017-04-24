package com.telecomjs.beans;

import java.math.BigDecimal;
import java.util.Date;

public class ZoneMarkUpload {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ZONE_MARK_UPLOAD.MARK_ID
     *
     * @mbggenerated
     */
    private Long markId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ZONE_MARK_UPLOAD.ZONE_ID
     *
     * @mbggenerated
     */
    private Long zoneId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ZONE_MARK_UPLOAD.EVENT_ID
     *
     * @mbggenerated
     */
    private Long eventId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ZONE_MARK_UPLOAD.BILLING_CYCLE
     *
     * @mbggenerated
     */
    private Integer billingCycle;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ZONE_MARK_UPLOAD.CHANNEL_TYPE
     *
     * @mbggenerated
     */
    private String channelType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ZONE_MARK_UPLOAD.DELEGATE_TYPE
     *
     * @mbggenerated
     */
    private String delegateType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ZONE_MARK_UPLOAD.CRITERION
     *
     * @mbggenerated
     */
    private BigDecimal criterion;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ZONE_MARK_UPLOAD.STANDARD_MARK
     *
     * @mbggenerated
     */
    private BigDecimal standardMark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ZONE_MARK_UPLOAD.AREA_MARK
     *
     * @mbggenerated
     */
    private BigDecimal areaMark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ZONE_MARK_UPLOAD.STATE
     *
     * @mbggenerated
     */
    private String state;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ZONE_MARK_UPLOAD.STATE_DATE
     *
     * @mbggenerated
     */
    private Date stateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ZONE_MARK_UPLOAD.ZONE_NAME
     *
     * @mbggenerated
     */
    private String zoneName;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ZONE_MARK_UPLOAD.MARK_ID
     *
     * @return the value of ZONE_MARK_UPLOAD.MARK_ID
     *
     * @mbggenerated
     */
    public Long getMarkId() {
        return markId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ZONE_MARK_UPLOAD.MARK_ID
     *
     * @param markId the value for ZONE_MARK_UPLOAD.MARK_ID
     *
     * @mbggenerated
     */
    public void setMarkId(Long markId) {
        this.markId = markId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ZONE_MARK_UPLOAD.ZONE_ID
     *
     * @return the value of ZONE_MARK_UPLOAD.ZONE_ID
     *
     * @mbggenerated
     */
    public Long getZoneId() {
        return zoneId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ZONE_MARK_UPLOAD.ZONE_ID
     *
     * @param zoneId the value for ZONE_MARK_UPLOAD.ZONE_ID
     *
     * @mbggenerated
     */
    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ZONE_MARK_UPLOAD.EVENT_ID
     *
     * @return the value of ZONE_MARK_UPLOAD.EVENT_ID
     *
     * @mbggenerated
     */
    public Long getEventId() {
        return eventId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ZONE_MARK_UPLOAD.EVENT_ID
     *
     * @param eventId the value for ZONE_MARK_UPLOAD.EVENT_ID
     *
     * @mbggenerated
     */
    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ZONE_MARK_UPLOAD.BILLING_CYCLE
     *
     * @return the value of ZONE_MARK_UPLOAD.BILLING_CYCLE
     *
     * @mbggenerated
     */
    public Integer getBillingCycle() {
        return billingCycle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ZONE_MARK_UPLOAD.BILLING_CYCLE
     *
     * @param billingCycle the value for ZONE_MARK_UPLOAD.BILLING_CYCLE
     *
     * @mbggenerated
     */
    public void setBillingCycle(Integer billingCycle) {
        this.billingCycle = billingCycle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ZONE_MARK_UPLOAD.CHANNEL_TYPE
     *
     * @return the value of ZONE_MARK_UPLOAD.CHANNEL_TYPE
     *
     * @mbggenerated
     */
    public String getChannelType() {
        return channelType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ZONE_MARK_UPLOAD.CHANNEL_TYPE
     *
     * @param channelType the value for ZONE_MARK_UPLOAD.CHANNEL_TYPE
     *
     * @mbggenerated
     */
    public void setChannelType(String channelType) {
        this.channelType = channelType == null ? null : channelType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ZONE_MARK_UPLOAD.DELEGATE_TYPE
     *
     * @return the value of ZONE_MARK_UPLOAD.DELEGATE_TYPE
     *
     * @mbggenerated
     */
    public String getDelegateType() {
        return delegateType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ZONE_MARK_UPLOAD.DELEGATE_TYPE
     *
     * @param delegateType the value for ZONE_MARK_UPLOAD.DELEGATE_TYPE
     *
     * @mbggenerated
     */
    public void setDelegateType(String delegateType) {
        this.delegateType = delegateType == null ? null : delegateType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ZONE_MARK_UPLOAD.CRITERION
     *
     * @return the value of ZONE_MARK_UPLOAD.CRITERION
     *
     * @mbggenerated
     */
    public BigDecimal getCriterion() {
        return criterion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ZONE_MARK_UPLOAD.CRITERION
     *
     * @param criterion the value for ZONE_MARK_UPLOAD.CRITERION
     *
     * @mbggenerated
     */
    public void setCriterion(BigDecimal criterion) {
        this.criterion = criterion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ZONE_MARK_UPLOAD.STANDARD_MARK
     *
     * @return the value of ZONE_MARK_UPLOAD.STANDARD_MARK
     *
     * @mbggenerated
     */
    public BigDecimal getStandardMark() {
        return standardMark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ZONE_MARK_UPLOAD.STANDARD_MARK
     *
     * @param standardMark the value for ZONE_MARK_UPLOAD.STANDARD_MARK
     *
     * @mbggenerated
     */
    public void setStandardMark(BigDecimal standardMark) {
        this.standardMark = standardMark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ZONE_MARK_UPLOAD.AREA_MARK
     *
     * @return the value of ZONE_MARK_UPLOAD.AREA_MARK
     *
     * @mbggenerated
     */
    public BigDecimal getAreaMark() {
        return areaMark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ZONE_MARK_UPLOAD.AREA_MARK
     *
     * @param areaMark the value for ZONE_MARK_UPLOAD.AREA_MARK
     *
     * @mbggenerated
     */
    public void setAreaMark(BigDecimal areaMark) {
        this.areaMark = areaMark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ZONE_MARK_UPLOAD.STATE
     *
     * @return the value of ZONE_MARK_UPLOAD.STATE
     *
     * @mbggenerated
     */
    public String getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ZONE_MARK_UPLOAD.STATE
     *
     * @param state the value for ZONE_MARK_UPLOAD.STATE
     *
     * @mbggenerated
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ZONE_MARK_UPLOAD.STATE_DATE
     *
     * @return the value of ZONE_MARK_UPLOAD.STATE_DATE
     *
     * @mbggenerated
     */
    public Date getStateDate() {
        return stateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ZONE_MARK_UPLOAD.STATE_DATE
     *
     * @param stateDate the value for ZONE_MARK_UPLOAD.STATE_DATE
     *
     * @mbggenerated
     */
    public void setStateDate(Date stateDate) {
        this.stateDate = stateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ZONE_MARK_UPLOAD.ZONE_NAME
     *
     * @return the value of ZONE_MARK_UPLOAD.ZONE_NAME
     *
     * @mbggenerated
     */
    public String getZoneName() {
        return zoneName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ZONE_MARK_UPLOAD.ZONE_NAME
     *
     * @param zoneName the value for ZONE_MARK_UPLOAD.ZONE_NAME
     *
     * @mbggenerated
     */
    public void setZoneName(String zoneName) {
        this.zoneName = zoneName == null ? null : zoneName.trim();
    }
}