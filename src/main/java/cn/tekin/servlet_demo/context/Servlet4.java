package cn.tekin.servlet_demo.context;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

@WebServlet("/demo/context/Servlet4")
public class Servlet4 extends HttpServlet {
    //	http://localhost:8080/serContext/Servlet4
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        //	response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();

        InputStream is = this.getServletContext().getResourceAsStream("dbinfo.properties");
        //创建properties
        Properties pp = new Properties();
        pp.load(is);
        out.println("name=" + pp.getProperty("username"));

        InputStream iis = Servlet4.class.getClassLoader().getResourceAsStream("dbinfo.properties");

        //获取文件全路径  tomcat的主目录 \apache-tomcat-6.0.20\webapps\serContext\imgs\Sunset.jpg
        String path = this.getServletContext().getRealPath("/imgs/a.jpg");
        out.println("path=" + path);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
