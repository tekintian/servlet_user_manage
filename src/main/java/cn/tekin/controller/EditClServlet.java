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

@WebServlet(name = "编辑用户", value = "/EditClServlet")
public class EditClServlet extends HttpServlet {
    //定义放回URL
    String backurl = "/";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        String type=request.getParameter("type");
        String id=request.getParameter("id");

        this.backurl=getServletContext().getInitParameter("HOME_URL");

        if (type.equals("edit")) {
            User usr=new User();
            usr.setId(request.getParameter("id"));
            usr.setName(request.getParameter("username"));
            usr.setEmail(request.getParameter("email"));
            usr.setGrade(request.getParameter("grade"));
            if (request.getParameter("passwd").length()>0&&request.getParameter("passwd").length()<30) {
                //判断用户是否修改了密码
               //修改密码
                usr.setPwd(HashEncrypt.encryptDiy(request.getParameter("passwd"),getServletContext().getInitParameter("pwSalt"),null));
            }else{
                usr.setPwd(request.getParameter("passwd"));
            }
            usr.setUnknow(request.getParameter("unknow"));
            usr.setRemark(request.getParameter("remark"));

            UsersService usersService=new UsersService();
            if(usersService.updUser(usr)){
                request.getRequestDispatcher("/Success?backurl="+backurl).forward(request,response);
            }else{
                request.getRequestDispatcher("/Fail?backurl="+backurl).forward(request,response);
            }

        }else{
            request.getRequestDispatcher("/Fail?backurl="+backurl+"ManageUsers").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String id=request.getParameter("id");
        this.backurl=request.getParameter("backurl");

        if (id != null) {

            UsersService usersService=new UsersService();

            User usr=usersService.getUserById(id);

            PrintWriter out = response.getWriter();
            out.println("<title>用户信息编辑</title>+" +
                "<link href=\"https://cdn.bootcss.com/bootstrap/4.1.0/css/bootstrap.min.css\" rel=\"stylesheet\">+" +
                "<div class='container'>");
            out.println("<div><a href='javascript:history.go(-1)' class='btn btn-primary btn-lg btn-block'>返回上一页</a></div>");
            out.println("<form action='"+getServletContext().getContextPath()+"/EditClServlet?type=edit' method='post'><br/>");
            out.println("<input type='hidden' name='id' value='"+id+"' />");
            out.println("用户名:<input type='text' name='username' value='"+ usr.getName() +"' readonly='readonly'/><br/>");
            out.println("密　码:<input type='password' name='passwd' id='passwd' onfocus=\"this.value='';\" value='"+ usr.getPwd() +"' required='required' /><br/>");
            out.println("邮件:<input type='text' name='email' value='"+ usr.getEmail() +"'  required='required' /><br/>");
            out.println("级别:<input type='text' name='grade' value='"+ usr.getGrade() +"'/><br/>");
            out.println("未知项目:<input type='text' name='unknow' value='"+ usr.getUnknow() +"'/><br/>");
    //        out.println("级别:<select name='grade'><option value='1'>初级</option><option value='2'>中级</option><option value='3'>高级</option></select><br/>");
            out.println("备注:<textarea cols='10' rows='5' name='remark' >"+ usr.getRemark() +"</textarea><br/>");
            out.println("<input type='submit' value='确定修改' onclick='checkform()'/>");
            out.println("</form>");


            out.println("<script type='text/javascript'>function checkform(){ var pwd=getElementById('passwd').value;if(pwd==''){ alert('用户密码不能为空！'); return false;}else{return true;}}</script>");
            out.println("</div>");
        }else{
            request.getRequestDispatcher("/Fail?backurl="+backurl).forward(request,response);
        }
    }
}
