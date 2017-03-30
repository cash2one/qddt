package com.telecomjs.services.intf;

import com.telecomjs.beans.AppYdbpAreaZwstaff;
import com.telecomjs.beans.Role;
import com.telecomjs.beans.Staff;
import com.telecomjs.datagrid.AreaTreeNode;

import java.util.List;

/**
 * Created by zark on 17/3/2.
 */

public interface UserService {


    public List<Staff> getAllStaff();

    public boolean checkCssNumber(String cssNumber);

    public boolean addStaff(Staff staff);

    public Integer getPrimaryKey();

    public Staff getStaff(int staffId);

    public int deleteStaff(int staffId);

    public int updateStaff(Staff staff);
    public Staff findStaff(String mobile);

    List<Role> findAllRoles();

    List<Staff> findAreaStaff(long id,AreaTreeNode.NodeType type);

    List<String> findZoneByStaff(String cssNumber);

    List<AppYdbpAreaZwstaff> findStaffByCssNumber(String cssNumber);

    List<AppYdbpAreaZwstaff> findStaffbyZone(Long zoneId);

    List<Staff> findZoneStaffById(Integer staffId);

    List<AppYdbpAreaZwstaff> findZWStaffById(Integer staffId);


    //分局管理员 查询分局信息
    List<String> findDistrictByStaff(String cssNumber);
    List<AppYdbpAreaZwstaff> findZWStaffByDistrict(long districtId);
    //区局支撑用 查询区域信息
    String findAreaByStaff(String cssStaffNumber);

    //区局支撑用 查看所有装维人员清单
    List<AppYdbpAreaZwstaff> findZWStaffByArea(long areaId);
}
