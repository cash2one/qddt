package com.telecomjs.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by zark on 17/4/22.
 */
public class HttpResponseHelper {
    public static PrintWriter getUtf8Writer(HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control","no-cache");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        return response.getWriter();
    }
}
