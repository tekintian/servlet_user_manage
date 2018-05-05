package cn.tekin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "失败提示页面", value = "/Fail")
public class Fail extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        out.println("Sorry，操作失败");

        //定义返回URL
        String backurl = null!=request.getParameter("backurl") ? request.getParameter("backurl") :getServletContext().getInitParameter("HOME_URL") + getServletContext().getContextPath();

        out.println("<BR><a href='"+ backurl +"'>点此返回</a>");

        out.close();

    }
}
