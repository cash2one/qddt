package com.telecomjs.secs;


import com.telecomjs.beans.Role;
import com.telecomjs.beans.Staff;
import com.telecomjs.common.Constants;
import com.telecomjs.mappers.RoleMapper;
import com.telecomjs.mappers.StaffMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by zark on 17/3/2.
 */
@Component("DefaultRealm")
public class DefaultRealm extends AuthorizingRealm {

    @Autowired
    RoleMapper roleMapper;
    @Autowired
    StaffMapper staffMapper;

    public DefaultRealm() {
        setName("DefaultRealm");// This name must match the name in the User
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        if (!StringUtils.isEmpty(username)  ) {
            Staff staff = staffMapper.findStaffByMobile(username) ;
            if (staff == null)
                throw  new UnknownAccountException(username);
            //ByteSource credentialsSalt = ByteSource.Util.bytes(staff.getCssStaffNumber());
            ByteSource credentialsSalt = ByteSource.Util.bytes(Constants.PASSWORD_SALT);
            SimpleAccount account = new SimpleAccount(staff.getMobile(), staff.getPassword(),credentialsSalt, getName());
            return account;
        }
        return null;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        Staff staff = staffMapper.findStaffByMobile(username);
        Role role = roleMapper.findRoleByUser(staff.getStaffId());
        if (role != null) {
            //获取角色权限
            Set<String> roles = new HashSet<>();
            Set<String> stringPermissions = new HashSet<>();
            roles.add(role.getRoleCode());

            /*for (Role role : roleSet) {
                roles.add(role.getRoleName());

                //封装到验证信息
                Set<Permission> permissions = role.getPermissions();
                for (Permission permission : permissions) {
                    stringPermissions.add(permission.getPermissionName());
                }
            }*/

            SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
            authorizationInfo.setRoles(roles);
            authorizationInfo.setStringPermissions(stringPermissions);
            return authorizationInfo;
        }
        return null;
    }


}
