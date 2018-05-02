package cn.tekin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Login")
public class Login extends HttpServlet {
    //	http://localhost:8080/requestForward/Login
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        //返回一个界面（html技术）
        out.println("<h1>用户登录</h1>");
        //action 应该这样写  /web应用名/Servlet的url
        out.println("<form action='"+getServletContext().getContextPath()+"/Servlet1' method='post'>");
        out.println("用户名：<input type='text' name='username'/><br/><br/>");
        out.println("密　码：<input type='password' name='password'/><br/><br/>");
        out.println("<input type='submit' value='登录'/><br/>");
        out.println("</form>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request,response);
    }
}