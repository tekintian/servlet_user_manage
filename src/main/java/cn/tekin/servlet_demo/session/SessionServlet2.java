package cn.tekin.servlet_demo.session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/demo/SessionServlet2")
public class SessionServlet2 extends HttpServlet {
    //	http://localhost:8080/session2/Servlet2
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        //	response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();

        //读取session的属性
        String uname = (String) request.getSession().getAttribute("username");
        out.println("name = " + uname);
        out.println("读取Servlet1 放入的Cookie<BR>");

        Cookie cookies[] = request.getCookies();
        if (cookies != null) {
            //            for (Cookie cc: cookies ) {
            //                if (cc.getName().equals("JSESSIONID")) out.println("Cookie JSESSIONID="+cc.getValue());
            //            }
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals("JSESSIONID"))
                    out.println("Cookie JSESSIONID_" + i + "=" + cookies[i].getValue() + "<BR>");
            }
        }


    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
