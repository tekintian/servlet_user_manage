package cn.tekin.controller;

import cn.tekin.domain.User;
import cn.tekin.service.UsersService;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class UserClServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");  //重要 否则输入时即中文乱码
        PrintWriter out = response.getWriter();

        UsersService usersService=new UsersService();

        //接收type
        String type=request.getParameter("type");
        if("del".equals(type)){
            //接收id
            String id=request.getParameter("id");
            //调用UserService完成删除
            //	if(new UsersService().delUser(id+100)){
            if(usersService.delUser(id+100)){
                //	ok forward()
                //	System.out.println("11111111");
                request.setAttribute("userinfo","删除成功");
                request.getRequestDispatcher("/OK").forward(request,response);
            }else request.getRequestDispatcher("/Err").forward(request,response);
        }else if("gotoUpdView".equals(type)){
            //要去用户信息的修改界面  得到id
            String id=request.getParameter("id");
            //通过id获取一个UserBean
            User user=usersService.getUserById(id);
            //为了让下一个Servlet(界面)使用我们的user对象 我们可以把该user对象放入到request对象中
            request.setAttribute("userinfo",user);

            //要使用请求转发
            request.getRequestDispatcher("/UpdateUserView").forward(request,response);
        }else if("update".equals(type)){
            //接收用户新的信息[如果用户提交的数据格式不对，需要客户端、服务器双重验证验证(Struts中用专门的验证函数)]
            String id=request.getParameter("id");
            String username=request.getParameter("username");
            String email=request.getParameter("email");
            String grade=request.getParameter("grade");
            String passwd=request.getParameter("passwd");
            String unknowx=request.getParameter("unknowx");
            String remarkx=request.getParameter("remarkx");
            //	System.out.println("unknowx&remarkx = "+unknowx+remarkx);

            //把接收到的信息，封装成一个User对象
            User u=new User();
            u.setId(id);
            u.setName(username);
            u.setEmail(email);
            u.setGrade(grade);
            u.setPwd(passwd);
            u.setUnknow(unknowx);
            u.setRemark(remarkx);

            //修改用户信息
            if(usersService.updUser(u)){
                request.setAttribute("userinfo","修改成功");
                request.getRequestDispatcher("/OK").forward(request,response);
            }
            else request.getRequestDispatcher("/Err").forward(request,response);

        }else if("gotoAddUser".equals(type)){
            //这里没有什么要处理
            request.getRequestDispatcher("/AddUserView").forward(request,response);
        }else if("add".equals(type)){
//            String id=request.getParameter("id");
            String username=request.getParameter("username");
            String email=request.getParameter("email");
            String grade=request.getParameter("grade");
            String passwd=request.getParameter("passwd");
            String unknowx=request.getParameter("unknowx");
            String remarkx=request.getParameter("remarkx");

            //	构建一个User对象	id是自增的
			User u=new User();
			u.setName(username);
			u.setEmail(email);
			u.setGrade(grade);
			u.setPwd(passwd);
			u.setUnknow(unknowx);
			u.setRemark(remarkx);

//            User u=new User(Integer.parseInt(id),username,email,Integer.parseInt(grade),passwd,unknowx,remarkx);	//Err

            //添加用户信息
            if(usersService.addUser(u)){
                request.setAttribute("userinfo","添加成功");
                request.getRequestDispatcher("/OK").forward(request,response);
            }
            else request.getRequestDispatcher("/Err").forward(request,response);
        }

		/*
		 AddUser(request, response, usersService);	//抽象出的函数

	 // 添加用户的方法 抽象成一个函数 Refactor -> Extract Method
	private void AddUser(HttpServletRequest request,
			HttpServletResponse response, UsersService usersService)
			throws ServletException, IOException {
		String id=request.getParameter("id");
		String username=request.getParameter("username");
		String email=request.getParameter("email");
		String grade=request.getParameter("grade");
		String passwd=request.getParameter("passwd");
		String unknowx=request.getParameter("unknowx");
		String remarkx=request.getParameter("remarkx");

	//	构建一个User对象	韩老师的id是自增的
		User u=new User(Integer.parseInt(id),username,email,Integer.parseInt(grade),passwd,unknowx,remarkx);	//Err

		//添加用户信息
		if(usersService.addUser(u)){
			request.setAttribute("userinfo","添加成功");
			request.getRequestDispatcher("/OK").forward(request,response);
		}
		else request.getRequestDispatcher("/Err").forward(request,response);
		 */
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        this.doGet(request,response);
    }
}
