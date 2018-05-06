package cn.tekin.servlet_demo.context;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/demo/context/Servlet3")
public class Servlet3 extends HttpServlet {
    //	http://localhost:8080/serContext/Servlet3
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        //	response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();

        String val = this.getServletContext().getInitParameter("name");
        out.println("	val = " + val);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
