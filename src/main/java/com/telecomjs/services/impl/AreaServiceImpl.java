package com.telecomjs.services.impl;

import com.telecomjs.beans.AppYdbpArea;
import com.telecomjs.beans.BillingCycle;
import com.telecomjs.mappers.AppYdbpAreaChannelMapper;
import com.telecomjs.mappers.AppYdbpAreaMapper;
import com.telecomjs.mappers.AppYdbpAreaZwstaffMapper;
import com.telecomjs.services.intf.AreaService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

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

    @Autowired
    private ApplicationContext appContext;

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

    @Override
    public int reloadArea() {

        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) appContext.getBean("sqlSessionFactory2");
        if (!sqlSessionFactory.getConfiguration().hasMapper(AppYdbpAreaMapper.class))
            sqlSessionFactory.getConfiguration().addMapper(AppYdbpAreaMapper.class);
        SqlSession session = sqlSessionFactory.openSession(false);
        AppYdbpAreaMapper mapper = session.getMapper(AppYdbpAreaMapper.class);
        List<AppYdbpArea> areas = mapper.findAll4t();
        if (areas.size() > 0){
            appYdbpAreaMapper.clearAll();
            for (AppYdbpArea area : areas)
                appYdbpAreaMapper.insert4t(area);
        }
        session.close();
        return 1;
    }


}
