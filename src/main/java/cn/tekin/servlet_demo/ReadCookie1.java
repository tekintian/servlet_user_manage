package cn.tekin.servlet_demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/demo/ReadCookie.do")
public class ReadCookie1 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out=response.getWriter();

        //读取所有COOKIE，在选中需要的cookie
        Cookie []cookies=request.getCookies();

        if (cookies != null) {
            for (Cookie cname:cookies ) {
                out.println("Cookie名字："+cname.getName()+" Cookie值："+cname.getValue()+"<br>");
                System.out.println("名字："+cname.getName()+" 值："+cname.getValue());
            }
        }else {
            out.println("没有获取到cookie, <br> YOur User-Agent:"+request.getHeader("User-Agent"));
        }

    }
}
