package cn.tekin.common;

import cn.tekin.servlet_demo.CheckMyTaskThread;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class MyInitServlet extends HttpServlet {

    //重写servlet 初始化方法
    @Override
    public void init() throws ServletException {

        System.out.println("MyInitServlet1 的init被调用..");
        //完成一些初始化任务
        System.out.println("创建数据库，表，读取参数");

        //开启服务器线程
        CheckMyTaskThread myst = new CheckMyTaskThread();
        myst.start();
        //  start 开启线程
    }


}
