package com.telecomjs.services.impl;

import com.telecomjs.beans.AppYdbpArea;
import com.telecomjs.beans.BillingCycle;
import com.telecomjs.mappers.AppYdbpAreaChannelMapper;
import com.telecomjs.mappers.AppYdbpAreaMapper;
import com.telecomjs.mappers.AppYdbpAreaZwstaffMapper;
import com.telecomjs.services.intf.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zark on 17/3/3.
 */
@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    AppYdbpAreaZwstaffMapper appYdbpAreaZwstaffMapper;

    @Autowired
    AppYdbpAreaMapper appYdbpAreaMapper;

    @Autowired
    AppYdbpAreaChannelMapper appYdbpAreaChannelMapper;


    public boolean checkCssNumber(String cssNumber){
        List list = appYdbpAreaZwstaffMapper.selectByCss(cssNumber);
        return  list.size()>0;
    }

    @Override
    public List<AppYdbpArea> findAll() {
        return appYdbpAreaMapper.findAll();
    }

    /**
     * 查找所有的大区，且只返回大区
     * @return
     */
    @Override
    public List<AppYdbpArea> findAllAreas() {
        return appYdbpAreaMapper.findAllAreas();
    }


}
