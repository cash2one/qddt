package com.telecomjs.controllers;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zark on 17/3/9.
 */
public class BaseController {
    protected Logger logger = Logger.getLogger(this.getClass());

    @ExceptionHandler
    public ModelAndView error(Exception e){
        e.printStackTrace();
        ModelAndView mv = new ModelAndView("error");
        mv.getModel().put("exception",e.getMessage());
        return mv;
    }
}
