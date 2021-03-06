package com.telecomjs.beans;

import java.math.BigDecimal;

public class ZoneMarkManual {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ZONE_MARK_MANUAL.MARK_ID
     *
     * @mbggenerated
     */
    private Long markId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ZONE_MARK_MANUAL.ZONE_ID
     *
     * @mbggenerated
     */
    private Long zoneId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ZONE_MARK_MANUAL.ITEM_ID
     *
     * @mbggenerated
     */
    private Long itemId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ZONE_MARK_MANUAL.BILLING_CYCLE
     *
     * @mbggenerated
     */
    private Long billingCycle;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ZONE_MARK_MANUAL.WEIGHT_MARK
     *
     * @mbggenerated
     */
    private BigDecimal weightMark;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ZONE_MARK_MANUAL.MARK_ID
     *
     * @return the value of ZONE_MARK_MANUAL.MARK_ID
     *
     * @mbggenerated
     */
    public Long getMarkId() {
        return markId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ZONE_MARK_MANUAL.MARK_ID
     *
     * @param markId the value for ZONE_MARK_MANUAL.MARK_ID
     *
     * @mbggenerated
     */
    public void setMarkId(Long markId) {
        this.markId = markId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ZONE_MARK_MANUAL.ZONE_ID
     *
     * @return the value of ZONE_MARK_MANUAL.ZONE_ID
     *
     * @mbggenerated
     */
    public Long getZoneId() {
        return zoneId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ZONE_MARK_MANUAL.ZONE_ID
     *
     * @param zoneId the value for ZONE_MARK_MANUAL.ZONE_ID
     *
     * @mbggenerated
     */
    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ZONE_MARK_MANUAL.ITEM_ID
     *
     * @return the value of ZONE_MARK_MANUAL.ITEM_ID
     *
     * @mbggenerated
     */
    public Long getItemId() {
        return itemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ZONE_MARK_MANUAL.ITEM_ID
     *
     * @param itemId the value for ZONE_MARK_MANUAL.ITEM_ID
     *
     * @mbggenerated
     */
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ZONE_MARK_MANUAL.BILLING_CYCLE
     *
     * @return the value of ZONE_MARK_MANUAL.BILLING_CYCLE
     *
     * @mbggenerated
     */
    public Long getBillingCycle() {
        return billingCycle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ZONE_MARK_MANUAL.BILLING_CYCLE
     *
     * @param billingCycle the value for ZONE_MARK_MANUAL.BILLING_CYCLE
     *
     * @mbggenerated
     */
    public void setBillingCycle(Long billingCycle) {
        this.billingCycle = billingCycle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ZONE_MARK_MANUAL.WEIGHT_MARK
     *
     * @return the value of ZONE_MARK_MANUAL.WEIGHT_MARK
     *
     * @mbggenerated
     */
    public BigDecimal getWeightMark() {
        return weightMark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ZONE_MARK_MANUAL.WEIGHT_MARK
     *
     * @param weightMark the value for ZONE_MARK_MANUAL.WEIGHT_MARK
     *
     * @mbggenerated
     */
    public void setWeightMark(BigDecimal weightMark) {
        this.weightMark = weightMark;
    }
}