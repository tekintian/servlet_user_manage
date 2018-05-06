package cn.tekin.servlet_demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/demo/VisitTime")
public class VisitTime extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
        IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
        IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        int visitTimes = 0;

        // 所有的 cookie
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            // 修改 Cookie，更新用户的访问次数
            Cookie visitTimesCookie = new Cookie("visitTimes", Integer.toString(++visitTimes));
            visitTimesCookie.setMaxAge(3600);
            response.addCookie(visitTimesCookie);
        }
        else {
            // 遍历所有的 Cookie 寻找 用户帐号信息与登录次数信息
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if ("visitTimes".equals(cookie.getName())) {
                    visitTimes = Integer.parseInt(cookie.getValue());
                    cookie.setValue("" + ++visitTimes);
                    visitTimes = Integer.parseInt(cookie.getValue());
                    out.println("登录:" + visitTimes + " 次");
                }
            }

            Cookie visitTimesCookie = new Cookie("visitTimes", Integer.toString(visitTimes));
            visitTimesCookie.setMaxAge(3600);
            response.addCookie(visitTimesCookie);
        }

    }
}
