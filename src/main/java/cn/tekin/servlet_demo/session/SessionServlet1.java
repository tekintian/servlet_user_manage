package cn.tekin.servlet_demo.session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/demo/SessionServlet1")
public class SessionServlet1 extends HttpServlet {
    //	http://localhost:8080/session2/Servlet1
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        //	response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();

        //创建一个session， 并放入一个属性
        HttpSession session = request.getSession();
        //给该session放入属性
        session.setAttribute("username", "狄成浩");
        //默认30分钟
        //把该session id保存到cookie 在保存id时，一定按照规范命名 必须大写
        Cookie ckie = new Cookie("JSESSIONID", session.getId());
        ckie.setMaxAge(60 * 30);
        response.addCookie(ckie);
        out.println("创建session并放入 " + session.getId());
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
