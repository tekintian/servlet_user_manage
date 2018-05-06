package cn.tekin.servlet_demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/demo/CreateCookie.do")
public class CreateCookie extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        Cookie cookie= new Cookie("name","tekin");
        cookie.setMaxAge(3600);

        Cookie myc=new Cookie("myemail","test@yahoo.com");
        myc.setMaxAge(60);


        response.addCookie(cookie);



    }
}
