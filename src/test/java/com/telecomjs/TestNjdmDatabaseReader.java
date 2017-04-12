package com.telecomjs;

import com.alibaba.fastjson.JSON;
import com.telecomjs.beans.AppYdbpArea;
import com.telecomjs.mappers.AppYdbpAreaMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by zark on 17/4/5.
 */
public class TestNjdmDatabaseReader {
    @Test()
    public void testDatabaseReader(){
        try {
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{
                    "classpath*:applicationContext-db.xml"
            });
            context.start();

            SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) context.getBean("sqlSessionFactory2");
            SqlSessionFactory destFactory = (SqlSessionFactory) context.getBean("sqlSessionFactory");
            sqlSessionFactory.getConfiguration().addMapper(AppYdbpAreaMapper.class);
            SqlSession session = sqlSessionFactory.openSession(false);
            SqlSession destSession = destFactory.openSession(false);
            AppYdbpAreaMapper mapper = session.getMapper(AppYdbpAreaMapper.class);
            AppYdbpAreaMapper destMapper = destSession.getMapper(AppYdbpAreaMapper.class);
            List<AppYdbpArea> areas = mapper.findAll4t();
            if (areas.size() > 0){
                destMapper.clearAll();
                for (AppYdbpArea area : areas)
                    destMapper.insert4t(area);
            }
            System.out.println(String.valueOf(areas.size()));
            session.close();
            destSession.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
