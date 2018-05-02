package cn.tekin.controller;

import cn.tekin.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;


@WebServlet(name = "LoginClServlet", value = "/LoginClServlet")
public class LoginClServlet extends HttpServlet {
//    private String cpath;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        PrintWriter out = response.getWriter();
        //获取部属路径 Context Path
//        cpath = request.getContextPath();

        String id=request.getParameter("id");
        String password=request.getParameter("password");

        //referer check
//        String referer = request.getHeader("Referer");
//        if(null ==referer || !referer.startsWith("http://localhost")){
//            out.println("Hacking Attampt?");
//            return;
//        }

//        System.out.println("Referer:"+ referer);

//        if ("admin".equals(username)&&"123".equals(password)){
//            out.println("Login OK!");
//
//            //将用户对象存入session
//            User user=new User();
//            user.setName(username);
//            user.setPwd(password);
//            request.getSession().setAttribute("userObj",user);
//
//
//
//
//            response.setStatus(302);
//            response.setHeader("location", cpath+"/index.jsp");
////            response.sendRedirect(cpath+"/index.jsp");
//        }else{
//
////            response.setHeader("Refresh","2;url="+cpath+"/index.jsp");
//
//            out.println("<script>alert('认证失败');location.href='" + getServletContext().getInitParameter("CPATH") + "/Login.do';</script>");
//
//            //out.println("<script>alert('认证失败');window.setTimeout(\"location.href='" + cpath + "/Login.do'\", 2000);</script>");
//        }

        Connection ct=null;
        ResultSet rs=null;
        PreparedStatement ps=null;
        try {
            //1加载驱动
            //	Class.forName("oracle.jdbc.driver.OracleDriver");
//            Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
            Class.forName("com.mysql.jdbc.Driver");
            //2.得到连接
            //	ct=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:ORCLHSP","scott","tiger");
//            ct=DriverManager.getConnection("jdbc:microsoft:sqlserver://127.0.0.1:49167;databaseName=javaee20141216","sa","sa");
            ct=DriverManager.getConnection("jdbc:mysql://localhost:3357/servlet_user_manage?characterEncoding=utf8&useSSL=false","servlet_user_manage","888888");
            //3.创建PreparedSatement
            ps=ct.prepareStatement("select * from users where id=? and passwd=?");
            //给? 赋值
            ps.setObject(1, id);
            ps.setObject(2, password);

            //4.执行操作
            rs=ps.executeQuery();
            //5.根据结果左处理
            if(rs.next()){

//                将用户对象存入session
            User user=new User();
            user.setId(id);
            user.setPwd(password);
            request.getSession().setAttribute("userObj",user);
                //说明该用户合法:  能用请求转发就不要使用 重定向
                request.getRequestDispatcher("/MainFrame").forward(request, response);

            }else{
                request.setAttribute("error","登录失败,用户名或者密码错误");
                request.getRequestDispatcher("/Login.do").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }finally{
            //关闭资源
            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                rs=null;
            }
            if(ps!=null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                ps=null;
            }
            if(ct!=null) {
                try {
                    ct.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                ct = null;
            }

        }
    }

}
