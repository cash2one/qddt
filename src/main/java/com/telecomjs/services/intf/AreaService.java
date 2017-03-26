package com.telecomjs.services.intf;

import com.telecomjs.beans.AppYdbpArea;
import com.telecomjs.beans.BillingCycle;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zark on 17/3/9.
 */
public interface AreaService {
    public boolean checkCssNumber(String cssNumber);
    List<AppYdbpArea> findAll();
    List<AppYdbpArea> findAllAreas();

}
