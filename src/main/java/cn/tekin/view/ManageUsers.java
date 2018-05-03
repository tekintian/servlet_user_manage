package cn.tekin.view;

import cn.tekin.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/ManageUsers")
public class ManageUsers extends HttpServlet {
    //	http://localhost:8080/UsersManager2/LoginServlet
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        //	response.setCharacterEncoding("utf-8");
        //session
        User user=(User)request.getSession().getAttribute("userObj");
        if (null == user){
            System.out.println("redirect....");
            response.sendRedirect(getServletContext().getContextPath()+"/Login.do");
        }else{
//SESSION


        System.out.println("user:"+user);

        PrintWriter out = response.getWriter();
        out.println("欢迎 "+ user.getName() +" 登录 <a href='" + getServletContext().getContextPath() + "/MainFrame'>返回主界面</a> <a href='"+ getServletContext().getContextPath() +"/LoginServlet'>安全退出</a> <hr/>");
        out.println("<h1>管理用户</h1>");

        //从数据库中取出用户信息  并显示
        //到数据库中取验证
        Connection ct = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        //定义分页需要的变量
        int pageNow=1; //当前页面

        //接收用户提交的pageNow
        String sPageNow= request.getParameter("pageNow");
        if (sPageNow != null) {
            pageNow=Integer.parseInt(sPageNow);
        }

        int pageSize=3; //每页多少条记录
        int pageCount=1; //表示共有多少页 计算出来的
        int rowCount=1; //数据库记录数

//        if (rowCount%pageSize==0){
//            pageCount=rowCount/pageSize;
//        }else{
//            pageCount=rowCount/pageSize+1;//JAVA整数运算小数会自动取整，所以要加1
//        }

        //三目运算
//        pageCount = rowCount%pageSize==0 ? rowCount/pageSize : rowCount/pageSize+1 ;

        //更简洁的算法
//        pageCount=(rowCount-1)/pageSize+1;

        try {
            //1加载驱动
            //	Class.forName("oracle.jdbc.driver.OracleDriver");
//            Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
            Class.forName("com.mysql.jdbc.Driver");
            //2.得到连接
            //	ct=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:ORCLHSP","scott","tiger");
//            ct=DriverManager.getConnection("jdbc:microsoft:sqlserver://127.0.0.1:49167;databaseName=javaee20141216","sa","sa");
            ct = DriverManager.getConnection("jdbc:mysql://localhost:3357/servlet_user_manage?characterEncoding=utf8&useSSL=false", "servlet_user_manage", "888888");
            //3.创建PreparedSatement
            //查询记录数
            ps = ct.prepareStatement("select count(*) from users");
            //3.执行操作
            rs = ps.executeQuery();
            rs.next();	//把游标向下移动一行，否则为空报错
            rowCount=rs.getInt(1);//获取总记录数


            //计算总页数
            pageCount = rowCount%pageSize==0 ? rowCount/pageSize : rowCount/pageSize+1 ;


            System.out.println("rowCount："+rowCount);
            System.out.println("pageSize："+pageSize);
            System.out.println("pageCount："+pageCount);

            String sql="select * from users order by id asc limit "+pageSize*(pageNow-1) +","+pageSize;

            ps=ct.prepareStatement(sql);

            //3.执行操作
            rs=ps.executeQuery();

            out.println("<table border=1px bordercolor=green sellspacing=0 width=500px>");
            out.println("<tr><th>id</th><th>用户名</th><th>email</th><th>级别</th><th>密码</th><th>备注</th></tr>");
            //循环显示所有用户信息
            while (rs.next()) {
                out.println("<tr><td>" + rs.getInt("id"));
                out.println("</td><td>" + rs.getString("username") );
                out.println( "</td><td>" + rs.getString("email") );
                out.println("</td><td>" + rs.getInt("grade") );
                out.println("</td><td>" + rs.getString("passwd") );
                out.println("</td><td>" + rs.getString("remark") );
                out.println("</td></tr>");
            }
            out.println("</table>");

           if (pageNow!=1) {
               //显示上一页
               out.println("<a href='" + getServletContext().getContextPath() + "/ManageUsers?pageNow=" + (pageNow-1) + "'>上一页</a> ");
           }
            //显示分页
            for(int i = 1; i <=pageCount ; i++) {

                out.println("<a href='"+getServletContext().getContextPath()+"/ManageUsers?pageNow="+i+"'> "+i+" </a>");

            }
            if (pageNow!=pageCount) {
                //显示上一页
                out.println("<a href='" + getServletContext().getContextPath() + "/ManageUsers?pageNow=" + (pageNow + 1) + "'>上一页</a> ");
            }

            //显示分页信息
            out.println("&nbsp;&nbsp;&nbsp;当前页"+pageNow+"/总页数"+pageCount+"<br/><br/>");
            out.println("跳转到<input type='text' id='pageNow' name='pageNow'/><input type='button' onClick='gotoPageNow()' value='跳'/>");

// JS
            out.println("<script type='text/javascript' language='javascript'>");
            //	out.println("function gotoPageNow(){var pageNow=pageNow.value;window.alert('pageNow='+pageNow);}");
            out.println("function gotoPageNow(){var pageNow=document.getElementById('pageNow');window.alert('pageNow='+pageNow.value);window.open('" + getServletContext().getContextPath() + "/ManageUsers?pageNow='+pageNow.value,'_self');}");
            out.println("</script>");

//            for(int i=1;i<=pageCount;i++) out.println("<a href='/UsersManager2/ManageUsers?pageNow="+i+"'><"+i+">  </a>");



        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        } finally {
            //关闭资源
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                rs = null;
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                ps = null;
            }
            if (ct != null) {
                try {
                    ct.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                ct = null;
            }
        }
        out.println("<hr/><img src='./images/webstore-consumer.jpg'>");

        //SESSION END
        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request, response);
    }
}