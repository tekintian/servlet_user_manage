package cn.tekin;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/MyInfoForm")
public class MyInfoForm extends HttpServlet {
    //	http://localhost:8080/servletPro3/MyInfoForm
    public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.println("<form action='"+getServletContext().getContextPath()+"/RegisterCl' method='post'><br/>");
        out.println("<input type='hidden' value='abc' name='hidden1'/>");
        out.println("用户名:<input type='text' name='username'/><br/>");
        out.println("密　码:<input type='password' name='pwd'/><br/>");
        out.println("性　别:<input type='radio' name='sex' value='男'/>男 <input type='radio' name='sex' value='女'/>女<br/>");
        out.println("你的爱好:<input type='checkbox' name='hobby' value='音乐'>音乐 <input type='checkbox' name='hobby' value='体育'>体育 <input type='checkbox' name='hobby' value=\"旅游\">旅游<br/>");
        out.println("所在城市:<select name='city'><option value='bj'>北京</option><option value='qd'>青岛</option><option value='qd'>qingdao</option><option value='cq'>重庆</option></select><br/>");
        out.println("你的介绍:<textarea cols='20' rows='10' name='intro' >请输入介绍..</textarea><br/>");
        out.println("提交照片:<input type='file' name='photo'><br/>");
        //什么时候使用hidden传输数据 1.不希望用户看到该数据 2. 不希望影响节目，同时使用该数据
        out.println("<input type='submit' value='提交信息'/>");
        out.println("</form>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        this.doGet(request, response);
    }
}