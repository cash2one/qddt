package com.telecomjs.beans;

public class Party {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PARTY.CSS_STAFF_NUMBER
     *
     * @mbggenerated
     */
    private String cssStaffNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PARTY.MOBILE
     *
     * @mbggenerated
     */
    private String mobile;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PARTY.IDENTIFIEDCODE
     *
     * @mbggenerated
     */
    private String identifiedcode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PARTY.STATE
     *
     * @mbggenerated
     */
    private String state;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PARTY.ROLE_ID
     *
     * @mbggenerated
     */
    private Short roleId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PARTY.CSS_STAFF_NUMBER
     *
     * @return the value of PARTY.CSS_STAFF_NUMBER
     *
     * @mbggenerated
     */
    public String getCssStaffNumber() {
        return cssStaffNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PARTY.CSS_STAFF_NUMBER
     *
     * @param cssStaffNumber the value for PARTY.CSS_STAFF_NUMBER
     *
     * @mbggenerated
     */
    public void setCssStaffNumber(String cssStaffNumber) {
        this.cssStaffNumber = cssStaffNumber == null ? null : cssStaffNumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PARTY.MOBILE
     *
     * @return the value of PARTY.MOBILE
     *
     * @mbggenerated
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PARTY.MOBILE
     *
     * @param mobile the value for PARTY.MOBILE
     *
     * @mbggenerated
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PARTY.IDENTIFIEDCODE
     *
     * @return the value of PARTY.IDENTIFIEDCODE
     *
     * @mbggenerated
     */
    public String getIdentifiedcode() {
        return identifiedcode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PARTY.IDENTIFIEDCODE
     *
     * @param identifiedcode the value for PARTY.IDENTIFIEDCODE
     *
     * @mbggenerated
     */
    public void setIdentifiedcode(String identifiedcode) {
        this.identifiedcode = identifiedcode == null ? null : identifiedcode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PARTY.STATE
     *
     * @return the value of PARTY.STATE
     *
     * @mbggenerated
     */
    public String getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PARTY.STATE
     *
     * @param state the value for PARTY.STATE
     *
     * @mbggenerated
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PARTY.ROLE_ID
     *
     * @return the value of PARTY.ROLE_ID
     *
     * @mbggenerated
     */
    public Short getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PARTY.ROLE_ID
     *
     * @param roleId the value for PARTY.ROLE_ID
     *
     * @mbggenerated
     */
    public void setRoleId(Short roleId) {
        this.roleId = roleId;
    }
}