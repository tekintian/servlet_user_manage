package cn.tekin.view;

import cn.tekin.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "MainFrame", value = "/MainFrame")
public class MainFrame extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        PrintWriter out = response.getWriter();

//        取出session
        User user=(User)request.getSession().getAttribute("userObj");
        out.println("<br/>  SessionObjectUsername= "+user.getName()+"   "+user.getPwd()+"<br/>");





        out.println("<h1>主界面</h1>");
        out.println("<a href='" + request.getContextPath() + "/Login.do'>返回重新登录</a>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }
}
