package cn.tekin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/GetRequestHeader")
public class GetRequestHeader extends HttpServlet {
    //	http://localhost:8080/servletPro3/GetInfoServlet
    //	http://localhost:8080/servletPro3/GetInfoServlet?username=hsp& pwd=456&email=789
    public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        //getHeader  通过消息头 来获取信息  比如要获取Http请求的Host  类似于request.getRemoteHost
        //	accept : text/html, application/xhtml+xml, */*
		/*	 accept-language : zh-CN
			 user-agent : Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0)
			 accept-encoding : gzip, deflate
			 host : localhost:8080
			 dnt : 1
			 connection : Keep-Alive		*/
        String host=request.getHeader("Host");
        out.println("  host=  "+host+"<br/>");
        out.println("*****************<br/>");
        //getHeaderNames  需求：把http请求的消息全部获取
        Enumeration<String> headers=request.getHeaderNames();
        while(headers.hasMoreElements()){
            //取出消息头的名字
            String headername=headers.nextElement();
            out.println(headername+" : "+request.getHeader(headername)+"<br/>");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request,response);
    }
}
