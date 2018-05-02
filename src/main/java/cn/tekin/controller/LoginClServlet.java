package cn.tekin.controller;

import cn.tekin.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebServlet(name = "LoginClServlet", value = "/LoginClServlet")
public class LoginClServlet extends HttpServlet {
    private String cpath;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        //获取部属路径 Context Path
        cpath = request.getContextPath();

        String username=request.getParameter("username");
        String password=request.getParameter("password");

        //referer check
        String referer = request.getHeader("Referer");
        if(null ==referer || !referer.startsWith("http://localhost")){
            out.println("Hacking Attampt?");
            return;
        }

        System.out.println("Referer:"+ referer);

        if ("admin".equals(username)&&"123".equals(password)){
            out.println("Login OK!");

            //将用户对象存入session
            User user=new User();
            user.setName(username);
            user.setPwd(password);
            request.getSession().setAttribute("userObj",user);




            response.setStatus(302);
            response.setHeader("location", cpath+"/index.jsp");
//            response.sendRedirect(cpath+"/index.jsp");
        }else{

//            response.setHeader("Refresh","2;url="+cpath+"/index.jsp");

            out.println("<script>alert('认证失败');location.href='" + getServletContext().getInitParameter("CPATH") + "/Login.do';</script>");

            //out.println("<script>alert('认证失败');window.setTimeout(\"location.href='" + cpath + "/Login.do'\", 2000);</script>");
        }



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }


    @Override
    public void destroy() {
        super.destroy();
    }
}
