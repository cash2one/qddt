package com.telecomjs.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zark on 17/3/2.
 */
@Controller
public class HomeController extends BaseController {

    @RequestMapping("/")
    public String index(){
        return "login";
    }

    @RequestMapping("index")
    public String index2(){ return "index";}

    @RequestMapping("ceoindex")
    public String ceoindex(){ return "ceoindex";}

    @RequestMapping("dceoindex")
    public String dceoindex(){ return "dceoindex";}

    @RequestMapping("areaindex")
    public String areaindex(){ return "areaindex";}

    @RequestMapping("/show")
    public ModelAndView viewUser(@RequestParam("hellomessage") String mess) {
        ModelAndView mav = new ModelAndView("show");
        mav.getModel().put("message", mess);
        return mav;
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/home")
    public String home(){
        return "home";
    }

    @RequestMapping("/blank")
    public String blank(){
        return "blank";
    }

    @RequestMapping("/ceohome")
    public String ceohome(){
        return "ceohome";
    }

    @RequestMapping("/aa")
    public String aa(){
        return "aa";
    }




}
