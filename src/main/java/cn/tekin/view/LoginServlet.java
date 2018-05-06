package cn.tekin.view;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//多个url映射，采用数值方式传递URL即可
@WebServlet(name = "LoginServlet", value = {"/LoginServlet", "/Login.do"})
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        PrintWriter out = response.getWriter();
//        out.println("Hello World!");
        //response.setContentType("text/html");
        //改乱码的另外一种方式, 明确告诉浏览器使用的字符编码方式，默认使用的
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        //记住用户名
        String username = "";
        boolean isRname = false;
        Cookie[] cookies = request.getCookies();
        //如果有cookie 则读取
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals("username")) {
                    username = cookies[i].getValue();
                    isRname = true;
                }
            }
        }
        String check = "";
        String unCheck = "";
        if (isRname) {
            check = " checked='checked'";
        }
        else {
            unCheck = " checked='checked'";
        }


        //记住用户名end

        PrintWriter out = response.getWriter();
        //返回一个界面（html技术）
        out.println("<title>用户列表</title>" + "<link href=\"https://cdn.bootcss.com/bootstrap/4.1.0/css/bootstrap.min" +
            ".css\" rel=\"stylesheet\">" +
            "<div class='container'>");
        out.println("<h1>用户登录</h1>");
        //action 应该这样写  /web应用名/Servlet的url
        out.println("<form action='"+ request.getContextPath() +"/LoginClServlet' method='post'>");
        out.println("用户名：<input type='text' name='username' value='" + username + "'/><br/><br/>");
        out.println("密　码：<input type='password' name='passwd'/><br/><br/>");
        out.println("记住用户名：<input type='radio' name='isRember' value='1' " + check + "/>记住 &nbsp;");
        out.println("<input type='radio' name='isRember' value='0' " + unCheck + "/>不记住<br/>");
        out.println("<input type='submit' value='登录'/><br/>");
        out.println("</form>");
        String errorInfo = (String) request.getAttribute("error");
        if (errorInfo != null) {
            out.println("<font color=red>"+errorInfo+"</front>");
        }

        out.println("</div>");
    }

}
