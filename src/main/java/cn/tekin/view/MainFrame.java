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

    static String CPATH="/";

    @Override
    public void init() throws ServletException {
        this.CPATH=getServletContext().getInitParameter("CPATH");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        //session
        User user=(User)request.getSession().getAttribute("userObj");
        if (null == user){
            System.out.println("redirect....");
            response.sendRedirect(getServletContext().getContextPath()+"/Login.do");
        }else {
//SESSION
            PrintWriter out = response.getWriter();
            out.println("<title>JAVA Servlet用户管理系统 学习项目</title>+" +
                "<link href=\"https://cdn.bootcss.com/bootstrap/4.1.0/css/bootstrap.min.css\" rel=\"stylesheet\">+" +
                "<div class='container'>");
            out.println("<img src='images/bajner.jpg' style='width:100%;height:100px;'/><hr/>");
            out.println("<hr/><img src='./images/user.png'> 您好，" + user.getName() + " 欢迎登陆本系统！<br/>");
            out.println("<h1>用户控制中心</h1>");
            out.println("<a href='" + CPATH + "ManageUsers' class='btn btn-default'>用户管理</a><br>");
            out.println("<a href='" + CPATH + "AddUserServlet'>添加用户</a><br>");
            out.println("<a href='" + CPATH + "ManageUsers'>查找用户</a><br>");
            out.println("<a href='" + CPATH + "Login.do'>退出系统</a><br>");
            out.println("</div>");
            out.close();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }
}
