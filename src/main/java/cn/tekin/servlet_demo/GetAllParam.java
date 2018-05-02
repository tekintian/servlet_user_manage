package cn.tekin.servlet_demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

@WebServlet(name = "GetAllParam", value = {"/get_all_param.html","/getInitParam.html"})
public class GetAllParam extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");

        PrintWriter out = response.getWriter();

        //获取所有的 param 参数
        Enumeration<String> names=this.getServletContext().getInitParameterNames();
        if (names == null) {
            out.println(" param 参数没有配置！");
        }else{
            out.println("<title>Servlet init-param 参数获取示例</title>");
            out.println("<h1>Servlet init-param 参数获取示例</h1>");
            while (names.hasMoreElements()){
                String name=names.nextElement(); //获取配置参数名称
                out.println("name: "+name+"<br>");
                out.println("value: "+this.getServletContext().getInitParameter(name)+"<hr>");
                //控制台输出
                System.out.println("name: "+name);
                System.out.println("value: "+getServletContext().getInitParameter(name));//获取配置参数的值

            }
        }

        out.close();
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
