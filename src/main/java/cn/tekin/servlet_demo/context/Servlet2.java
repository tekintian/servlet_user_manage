package cn.tekin.servlet_demo.context;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/demo/context/Servlet2")
public class Servlet2 extends HttpServlet {
    //	http://localhost:8080/serContext/Servlet2
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        //	response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();

        //取出ServletContext的某个属性
        //1. 首先获取ServletContext
        ServletContext servletContext = this.getServletContext();
        //2. 取出属性，这个属性值 对应什么类型就转成相应的类型
        String val = (String) servletContext.getAttribute("uname");
        out.println("	删除前 val = " + val + "<br/>");

        servletContext.removeAttribute("uname");
        val = (String) servletContext.getAttribute("uname");
        out.println("	删除后 val = " + val);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}

