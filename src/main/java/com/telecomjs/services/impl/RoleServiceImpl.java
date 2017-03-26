package com.telecomjs.services.impl;

import com.telecomjs.mappers.RoleMapper;
import com.telecomjs.services.intf.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zark on 17/3/3.
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleMapper roleMapper;


}
