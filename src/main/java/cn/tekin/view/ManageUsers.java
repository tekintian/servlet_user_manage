package cn.tekin.view;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManageUsers extends HttpServlet {
    //	http://localhost:8080/UsersManager2/LoginServlet
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        //	response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        out.println("<img src='imgs/1.GIF'/>欢迎 xx 登录 <a href='"+getServletContext().getContextPath()+"/MainFrame'>返回主界面</a> <a href='/UsersManager2/LoginServlet'>安全退出</a> <hr/>");
        out.println("<h1>管理用户</h1>");

        //从数据库中取出用户信息  并显示
        //到数据库中取验证
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
            ps=ct.prepareStatement("select * from users");
            //3.执行操作
            rs=ps.executeQuery();
            out.println("<table border=1px bordercolor=green sellspacing=0 width=500px>");
            out.println("<tr><th>id</th><th>用户名</th><th>email</th><th>级别</th><th>密码</th><th>备注</th></tr>");
            //循环显示所有用户信息
            while(rs.next()){				out.println("<tr><td>"+rs.getInt(1)+"</td><td>"+rs.getString(2)+"</td><td>"+rs.getString(3)+"</td><td>"+rs.getInt(4)+"</td><td>"+rs.getString(5)+"</td><td>"+rs.getString(7)+"</td></tr>");


            }
            out.println("</table>");
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
            if(ct!=null){
                try {
                    ct.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                ct=null;
            }
        }
        out.println("<hr/><img src='imgs/ mylogo.gif'/>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request,response);
    }
}