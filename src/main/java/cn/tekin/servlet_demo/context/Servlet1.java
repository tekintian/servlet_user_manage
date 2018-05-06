package cn.tekin.servlet_demo.context;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/demo/context/Servlet1")
public class Servlet1 extends HttpServlet {
    //	http://localhost:8080/serContext/Servlet1
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        //	response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();

        //获取ServletContext 对象引用
        //1.通过this直接获取 this 来自HttpServlet的GenericServlet
        ServletContext servletContext = this.getServletContext();

        //2. 通过ServletConfig获取
        ServletContext servletContext2 = this.getServletConfig().getServletContext();

        servletContext.setAttribute("uname", "狄成浩");
        out.println("创建serContext并放入 uname=狄成浩");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
