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

        PrintWriter out = response.getWriter();

//取出session
        User user=(User)request.getSession().getAttribute("userObj");

        out.println("<img src='images/bajner.jpg' style='width:100%;height:100px;'/><hr/>");
        out.println("<hr/><img src='images/welcome.png'/>SessionUserId:" + user.getId() + "SessionUserName:"+user.getName()+"<br/>");
        out.println("<img src='./images/user.png'><h1>主界面</h1>");
        out.println("<a href='" + CPATH + "/Login.do'>返回重新登录</a><br>");
        out.println("<a href='" + CPATH + "/ManageUsers'>用户管理</a><br>");
        out.println("<a href='" + CPATH + "/Login.do'>添加用户</a><br>");
        out.println("<a href='" + CPATH + "/Login.do'>查找用户</a><br>");
        out.println("<a href='" + CPATH + "/Login.do'>退出系统</a><br>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }
}
