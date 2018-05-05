package cn.tekin.controller;

import cn.tekin.domain.User;
import cn.tekin.service.UsersService;
import cn.tekin.utils.HashEncrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "新增用户", value = {"/AddUserServlet", "/register.html"})
public class AddUserServlet extends HttpServlet {
    //定义放回URL
    String backurl = "/";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");

        String act=request.getParameter("act");
        this.backurl=request.getServletContext().getInitParameter("HOME_URL");

        if (act.equals("save")) {
            User usr=new User();
            usr.setName(request.getParameter("username"));
            usr.setEmail(request.getParameter("email"));
            usr.setGrade(request.getParameter("grade"));
            usr.setPwd(HashEncrypt.encryptDiy(request.getParameter("passwd"),getServletContext().getInitParameter("pwSalt"),null));
            usr.setUnknow(request.getParameter("unknow"));
            usr.setRemark(request.getParameter("remark"));

            UsersService usersService=new UsersService();


            if (usersService.checkUserNameExist(request.getParameter("username"))){
                request.setAttribute("usernameExist","用户名已经存在，请重新更换用户名");
               // response.sendRedirect(backurl+"AddUserServlet);
                //自动刷新跳转
                //response.setHeader("Refresh", "2;url="+backurl+"AddUserServlet");

                PrintWriter out=response.getWriter();
                out.println("<title>用户注册</title>"+
                "<link href=\"https://cdn.bootcss.com/bootstrap/4.1.0/css/bootstrap.min.css\" rel=\"stylesheet\">"+
                "<div class='container'>");
                out.println("<h1 class='btn btn-warning btn-lg btn-block'>用户名已经存在，请重新更换用户名</h1>");
                out.println("<div><a href='javascript:history.go(-1)' class='btn btn-primary btn-lg btn-block'>返回重试</a></div>"+
                "</div>");
            }else{

                if(usersService.addUser(usr)){
                    request.getRequestDispatcher("/Fail?backurl="+backurl+"ManageUsers").forward(request,response);
                }else{
                    request.getRequestDispatcher("/Fail?backurl="+backurl+"ManageUsers").forward(request,response);
                }

            }
        }else{

            request.getRequestDispatcher("/Fail?backurl="+backurl).forward(request,response);

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");


        this.backurl=getInitParameter("HOME_URL");


            PrintWriter out = response.getWriter();
            out.println("<title>新用户注册</title>+" +
                "<link href=\"https://cdn.bootcss.com/bootstrap/4.1.0/css/bootstrap.min.css\" rel=\"stylesheet\">+" +
                "<div class='container'>");
        out.println("<div ><a href='javascript:history.go(-1)' class='btn btn-primary btn-lg btn-block'>返回上一页</a></div>");
            out.println("<form action='"+getServletContext().getContextPath()+"/AddUserServlet?act=save' method='post'><br/>");
            out.println("用户名:<input type='text' name='username' value='' placeholder='请输入您的用户名称，英文或者数字的组合，不允许有特殊字符' required='required'/><br/>");
            out.println("密　码:<input type='password' name='passwd' id='passwd' onfocus=\"this.value='';\" value='' placeholder='请输入您的用户密码' required='required' /><br/>");
            out.println("邮件:<input type='text' name='email' value=''  required='required' placeholder='请输入您的常用邮箱' /><br/>");
            out.println("级别:<input type='text' name='grade' value=''/><br/>");
            out.println("未知项目:<input type='text' name='unknow' value=''/><br/>");
            out.println("备注:<textarea cols='10' rows='5' name='remark' ></textarea><br/>");
            out.println("<input type='submit' value='新建用户'/>");
            out.println("</form>");

        String usernameExist=getServletContext().getAttribute("usernameExist").toString();
        if (usernameExist != null) {
            out.println("<font color=red>"+usernameExist+"</front>");
        }

            out.println("</div>");

            out.close();
    }
}
