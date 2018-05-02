package cn.tekin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Servlet1")
public class Servlet1 extends HttpServlet {
    //	http://localhost:8080/requestForward/Login
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        //接收用户名
        String u=request.getParameter("username");
        PrintWriter out = response.getWriter();

        //把u放入request域对象
        //Servlet1接收到数据后， 可以把该数据放入到request域对象中
        request.setAttribute("username", u);
        //表示使用转发的方法， 把request和response对象传递给下一个Servlet[/--/---2]
        //	HTTP Status 404 - /requestForward/requestForward/Servlet2
        //	request.getRequestDispatcher("/requestForward/Servlet2")
        //	request.getRequestDispatcher("http://www.sohu.com")
        request.getRequestDispatcher("/Servlet2")
                .forward(request,response);
        //使用sendRedirect();
        //	response.sendRedirect("http://www.sohu.com");	//独立于以上可以

        //使用sendRedirect();
        //response.sendRedirect("/requestForward/Servlet2");	//不可以，因为Servlet1和Servlet2的Request对象不是同一个对象；
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request,response);
    }
}