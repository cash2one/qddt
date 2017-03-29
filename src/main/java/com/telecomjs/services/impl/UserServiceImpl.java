package com.telecomjs.services.impl;

import com.telecomjs.beans.AppYdbpAreaZwstaff;
import com.telecomjs.beans.BillingCycle;
import com.telecomjs.beans.Role;
import com.telecomjs.beans.Staff;
import com.telecomjs.datagrid.AreaTreeNode;
import com.telecomjs.mappers.AppYdbpAreaZwstaffMapper;
import com.telecomjs.mappers.BillingCycleMapper;
import com.telecomjs.mappers.RoleMapper;
import com.telecomjs.mappers.StaffMapper;
import com.telecomjs.services.intf.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.telecomjs.datagrid.AreaTreeNode.NodeType.All;

/**
 * Created by zark on 17/3/2.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private StaffMapper staffMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private AppYdbpAreaZwstaffMapper ydbpAreaZwstaffMapper;

    @Autowired
    private BillingCycleMapper billingCycleMapper;

    public List<Staff> getAllStaff() {
        return null;
    }

    public boolean checkCssNumber(String cssNumber) {
        return false;
    }

    public boolean addStaff(Staff staff){
        return staffMapper.insert(staff) > 0;
    }

    public Integer getPrimaryKey(){
        return staffMapper.getPrimaryKey();
    }

    public Staff getStaff(int staffId){
        return staffMapper.selectByPrimaryKey(staffId);
    }

    public int deleteStaff(int staffId){
        return staffMapper.safeDeleteByStaffId(staffId);
    }

    public int updateStaff(Staff staff){
        return staffMapper.updateByPrimaryKeySelective(staff);
    }
    public Staff findStaff(String mobile){
        return staffMapper.findStaffByMobile(mobile);
    }

    @Override
    public List<Role> findAllRoles() {
        return roleMapper.findAll();
    }

    @Override
    public List<Staff> findAreaStaff(long id,AreaTreeNode.NodeType type) {
        switch (type){
            case All:
                return staffMapper.findAll();
            case None:
                return staffMapper.findStaffWithoutCss();
            case Area:
                return staffMapper.findStaffByArea(id);
            case District:
                return staffMapper.findStaffByDistrict(id);
            case Zone:
                return staffMapper.findStaffByZone(id);
        }
        return null;
    }


    /**
     * 根据css工号返回所挂载的片区ID列表
     *
     *  * 从返回单个zone改为一个String List
     *  * modified by zark on 2017/3/27
     * @param cssNumber
     * @return
     */
    @Override
    public List<String> findZoneByStaff(String cssNumber) {
        return staffMapper.findZoneByStaff(cssNumber);
    }

    /**
     * 根据css工号得到分局信息，分局CEO用
     * @param cssNumber
     * @return
     */
    @Override
    public String findDistrictByStaff(String cssNumber) {
        return staffMapper.findDistrictByStaff(cssNumber);
    }

    /**
     * 分局CEO根据分局查找装维工号
     * @param districtId
     * @return
     */
    @Override
    public List<AppYdbpAreaZwstaff> findZWStaffByDistrict(long districtId) {
        return ydbpAreaZwstaffMapper.findZWStaffByDistrict(districtId);
    }


    /**
     * 根据工号得到区局信息，区局支撑
     * @param cssStaffNumber
     * @return
     */
    @Override
    public String findAreaByStaff(String cssStaffNumber) {
        return staffMapper.findAreaByStaff(cssStaffNumber);
    }

    /**
     * 查询区域内所有装维人员清单
     * @param areaId
     * @return
     */
    @Override
    public List<AppYdbpAreaZwstaff> findZWStaffByArea(long areaId) {
        return ydbpAreaZwstaffMapper.findZWStaffByArea(areaId);
    }

    @Override
    public List<AppYdbpAreaZwstaff> findStaffByCssNumber(String cssNumber) {
        return ydbpAreaZwstaffMapper.findStaffByCssNumber(cssNumber);
    }

    @Override
    public List<AppYdbpAreaZwstaff> findStaffbyZone(Long zoneId) {
        return ydbpAreaZwstaffMapper.findStaffByZone(zoneId);
    }

    @Override
    public List<Staff> findZoneStaffById(Integer staffId) {
        return staffMapper.findZoneStaffById(staffId);
    }

    @Override
    public List<AppYdbpAreaZwstaff> findZWStaffById(Integer staffId) {
        return ydbpAreaZwstaffMapper.findZWStaffByStaffId(staffId);
    }

}
