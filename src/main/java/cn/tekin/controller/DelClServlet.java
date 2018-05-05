package cn.tekin.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tekin.service.UsersService;
import cn.tekin.domain.User;
import cn.tekin.service.*;
import cn.tekin.domain.*;

@WebServlet(name = "删除用户", value = "/DelClServlet")
public class DelClServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");

        //接收type
        String type=request.getParameter("type");
        if("del".equals(type)){
            //接收id
            String id=request.getParameter("id");
            //调用UserService完成删除
            if(new UsersService().delUser(id)){
                //ok forward()
                request.getRequestDispatcher("/Success").forward(request,response);
            }else{
                request.getRequestDispatcher("/Fail").forward(request,response);
            }
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request,response);
    }
}
