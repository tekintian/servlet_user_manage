package cn.tekin.view;

import cn.tekin.domain.User;
import cn.tekin.service.UsersService;
import cn.tekin.utils.SqlHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

@WebServlet("/ManageUsers")
public class ManageUsers extends HttpServlet {
    String home_url="/";

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String backurl=request.getRequestURL().toString();
        //	response.setCharacterEncoding("utf-8");
        this.home_url=getServletContext().getInitParameter("HOME_URL");
        //session
        User user=(User)request.getSession().getAttribute("userObj");
        //创建一个session
//        HttpSession hs=request.getSession(true);
//        String myname=hs.getAttribute("username").toString();//获取域中的session
        if (null == user){
            response.sendRedirect(home_url+"Login.do");
        }else{
//SESSION

        PrintWriter out = response.getWriter();
            out.println("<title>用户列表</title>+" +
                "<link href=\"https://cdn.bootcss.com/bootstrap/4.1.0/css/bootstrap.min.css\" rel=\"stylesheet\">+" +
                "<div class='container'>+" +
                "<img src=./images/welcome.png>");
        out.println("Hi "+ user.getName() +"，欢迎登陆用户管理中心  <a href='" + getServletContext().getContextPath() + "/MainFrame'>返回主界面</a> <a href='"+ home_url +"LoginServlet'>安全退出</a> <hr/>");
        out.println("<h1>管理用户</h1>");

        //定义分页需要的变量
        int pageNow=1; //当前页面

        //接收用户提交的pageNow
        String sPageNow= request.getParameter("pageNow");
        if (sPageNow != null) {
            pageNow=Integer.parseInt(sPageNow);
        }

        int pageSize=10; //每页多少条记录
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

            //定义变量
            int beginx=1;
            int endx=1;
            int step=4;//分页显示步长

        try {

            UsersService usersService=new UsersService();

            //获取总页数
            pageCount = usersService.getPageCount(pageSize);

            ArrayList<User> al=usersService.getUsersByPage(pageNow,pageSize);

            out.println("<table width=90%>");
            out.println("<tr><th>id</th><th>用户名</th><th>email</th><th>级别</th><th>密码</th><th>备注</th><th>管理</th></tr>");

            //定义颜色数组
            String []mycolor={"pink","yellow","cyan"};
            int j=0;
            //循环显示所有用户信息: 对密码信息做掩码处理

            for (User u:al) {
                out.println("<tr bgcolor="+mycolor[(++j)%3]+"><td>" + u.getId() +
                 "</td><td>" + u.getName() +
                 "</td><td>" + u.getEmail() +
                "</td><td>" + u.getGrade() +
                "</td><td>" + u.getPwd().replaceAll("(\\w{5})(\\w{15})(\\w{5})(\\w+)","*****$2*****$4") +
                "</td><td>" + u.getRemark() +
                "</td><td><a href='"+ home_url +"EditClServlet?id="+u.getId()+"&backurl="+backurl+"'>编辑</a> | <a href='"+ home_url +"DelClServlet?id="+u.getId()+"&type=del&backurl="+backurl+"' onClick='confirmOper()'>删除</a></td></tr>");
            }

            out.println("</table>");

           if (pageNow!=1) {
               //显示上一页
               out.println("<a href='" + home_url + "ManageUsers?pageNow=" + (pageNow-1) + "'>上一页</a> ");
           }
            //显示分页
            beginx=pageNow;
            endx=pageNow;
            if(pageNow-step>0) beginx=pageNow-step;
            if(pageNow+step<=pageCount) endx=pageNow+step;
            for(int i=beginx;i<=endx;i++) out.println("<a href='"+ home_url +"ManageUsers?pageNow="+i+"'><"+i+"></a>");


           /*
            //显示分页
            for(int i = 1; i <=pageCount ; i++) {

                out.println("<a href='"+home_url+"ManageUsers?pageNow="+i+"'> "+i+" </a>");

            }*/
            if (pageNow!=pageCount) {
                //显示上一页
                out.println("<a href='" + home_url + "ManageUsers?pageNow=" + (pageNow + 1) + "'>下一页</a> ");
            }

            //显示分页信息
            out.println("&nbsp;&nbsp;&nbsp;当前页"+pageNow+"/总页数"+pageCount+"  每页："+ pageSize +"条数据<br/><br/>");
            out.println("跳转到<input type='text' id='pageNow' name='pageNow'/><input type='button' onClick='gotoPageNow()' value='跳'/>");

// JS
            out.println("<script type='text/javascript' language='javascript'>");
            //	out.println("function gotoPageNow(){var pageNow=pageNow.value;window.alert('pageNow='+pageNow);}");
            out.println("function gotoPageNow(){var pageNow=document.getElementById('pageNow');window.alert('pageNow='+pageNow.value);window.open('" + home_url + "ManageUsers?pageNow='+pageNow.value,'_self');}" +
             "function confirmOper(){ return window.confirm('确定要删除吗？'); }");
            out.println("</script>");

//            for(int i=1;i<=pageCount;i++) out.println("<a href='/UsersManager2/ManageUsers?pageNow="+i+"'><"+i+">  </a>");



        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        } finally {
           out.close();
        }
            out.println("<br><br>你的IP="+request.getRemoteAddr()+"<br>");
            out.println("你的机器名是"+request.getRemoteHost()+"<br>");

            out.println("<hr/><img src='./images/webstore-consumer.jpg'></div>");

        //SESSION END
        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request, response);
    }
}