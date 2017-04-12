package com.telecomjs.jobs;

import com.telecomjs.services.intf.AreaService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zark on 17/4/5.
 */
public class RefreshAreaJob {
    @Autowired
    AreaService areaService;

    private static final Logger logger = Logger.getLogger(RefreshAreaJob.class);
    public void run() {
        if (logger.isInfoEnabled()) {

            logger.info("数据转换任务线程开始执行!");
            areaService.reloadArea();
            logger.info("数据转换任务线程结束!");

        }
    }
}
