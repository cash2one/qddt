package com.telecomjs.mappers;

import com.telecomjs.beans.AuditLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuditLogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AUDIT_LOG
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Short auditId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AUDIT_LOG
     *
     * @mbggenerated
     */
    int insert(AuditLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AUDIT_LOG
     *
     * @mbggenerated
     */
    int insertSelective(AuditLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AUDIT_LOG
     *
     * @mbggenerated
     */
    AuditLog selectByPrimaryKey(Short auditId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AUDIT_LOG
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(AuditLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AUDIT_LOG
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(AuditLog record);

    int getPrimatyKey();
    List<AuditLog> findAuditLogById(@Param("assessmentId") int assessmentId);

    List<AuditLog> findAuditLogByCssNumber(@Param("cssStaffNumber") Long cssStaffNumber);

    int insertByDistricts(@Param("billingCycle") int billingCycle, @Param("districtIds") List<Long> districtIds, @Param("auditLog") AuditLog auditLog);

    int insertAllByCycle(@Param("billingCycle") int billingCycle, @Param("auditLog") AuditLog auditLog);
}