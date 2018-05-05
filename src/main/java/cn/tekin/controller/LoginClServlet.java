package cn.tekin.controller;

import cn.tekin.domain.User;
import cn.tekin.service.UsersService;
import cn.tekin.utils.HashEncrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "LoginClServlet", value = "/LoginClServlet")
public class LoginClServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String passwd = request.getParameter("passwd");

        //创建UserService对象，完成到数据库的验证
        UsersService userservice = new UsersService();
        //创建user对象
        User usr = new User();
        //将验证的数据放入USER对象
        usr.setName(username);
        usr.setPwd(HashEncrypt.encryptDiy(passwd,getServletContext().getInitParameter("pwSalt"),null));

//        System.out.println("带验证的对象："+usr);
//        System.out.println("密码：123 加扰字符："+ getServletContext().getInitParameter("pwSalt") +"  密文:"+HashEncrypt.encryptDiy("123",getServletContext().getInitParameter("pwSalt"),null));


        //使用userservice 验证
       // if (userservice.checkUser(usr)) {//验证成功
        if (userservice.checkLogin(usr)!=null){//验证成功
            //将用户对象放入session中
            request.getSession().setAttribute("userObj", userservice.checkLogin(usr));
            //说明该用户合法:  能用请求转发就不要使用 重定向
            request.getRequestDispatcher("/MainFrame").forward(request, response);
        } else {
            request.setAttribute("error", "登录失败,用户名或者密码错误");
            request.getRequestDispatcher("/Login.do").forward(request, response);
        }

    }

}
