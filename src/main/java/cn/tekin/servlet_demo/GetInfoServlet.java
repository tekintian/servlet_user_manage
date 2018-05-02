package cn.tekin.servlet_demo;

import cn.tekin.utils.MyTools;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class GetInfoServlet extends HttpServlet {
    //	http://localhost:8080/servletPro3/GetInfoServlet
    //http://localhost:8080/servletPro3/GetInfoServlet?username=hsp&pwd=456&email=789
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();

        //得到URL
        String url=request.getRequestURL().toString();
        System.out.println(" url= " +url);
        //得到URI
        String uri=request.getRequestURI();
        System.out.println(" uri= " +uri);
        //得到QueryString	接收的是以get方式提交的参数
        String queryString=request.getQueryString();
        System.out.println(" queryString= " +queryString);
//把接收到的信息通过split函数分割
        String qS[]=queryString.split("&");
        for(String s:qS){
            out.println(s+"<br/>");
            //	out.println(MyTools.getNewString(request.getParameter(s)));
            out.println(MyTools.getNewString(s)+"<br/>");
            String []name_val=s.split("=");
            out.println(name_val[0]+"="+MyTools.getNewString(name_val[1])+"<br/>");
        }

        //得到getRemoteAddr 函数可以获取请求方的ip
        String addr=request.getRemoteAddr();
        System.out.println(" addr= " +addr);
        if(addr.equals("127.0.0.2")){
            response.sendRedirect(getServletContext().getContextPath()+"/Err");
        }
        //得到getRemoteHost 获取请求方的主机名 如果客户机没有在dns上注册(一般上网，要提供一个域名让别人去访问) 则返回ip地址
        String host=request.getRemoteHost();
        System.out.println(" host= " +host);
        //getRemotePort 获取客户机使用的端口
        int port=request.getRemotePort();
        int serverPort=request.getLocalPort();
        System.out.println(" 客户机"+addr+"使用的端口号是 port= " +port);
        System.out.println(" 服务器使用的端口号是 port= " +serverPort);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        this.doGet(request,response);
    }
}
