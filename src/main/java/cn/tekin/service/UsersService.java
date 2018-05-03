package cn.tekin.service;

import cn.tekin.domain.User;

import java.sql.*;

public class UsersService{
    Connection ct=null;
    PreparedStatement ps=null;
    ResultSet rs=null;
    //写一个验证用户是否合法的函数
    public boolean checkUser(User userx){
        //连接数据库；
        boolean b=false;
        try {
//            Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
//            ct=DriverManager.getConnection("jdbc:microsoft:sqlserver://127.0.0.1:49167;databaseName=javaee20141216","sa","sa");
//
            Class.forName("com.mysql.jdbc.Driver");
            ct = DriverManager.getConnection("jdbc:mysql://localhost:3357/servlet_user_manage?characterEncoding=utf8&useSSL=false", "servlet_user_manage", "888888");

            ps=ct.prepareStatement("select * from users where id=? and passwd=?");
            //给? 赋值
            ps.setObject(1, userx.getId());
            ps.setObject(2, userx.getPwd());
            rs=ps.executeQuery();
            if(rs.next()) b=true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
        return b;
    }
}
