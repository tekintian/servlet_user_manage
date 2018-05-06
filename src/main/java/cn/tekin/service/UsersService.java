package cn.tekin.service;

import cn.tekin.domain.User;
import cn.tekin.utils.SqlHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsersService{

    //获取pageCount
    public int getPageCount(int pageSize){
        String sql="select count(*) from users";
        ResultSet rs=SqlHelper.executeQuery(sql,null);
        int rowCount=0;

        try {
            rs.next();
            rowCount=rs.getInt(1);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            SqlHelper.close(rs,SqlHelper.getPs(),SqlHelper.getCt());
        }
        return (rowCount-1)/pageSize+1;
    }

    //按照分页来获取用户
    //公司返回处理方式  ResultSet -> User对象 -> ArrayList(集合)
//    public ArrayList getUsersByPage(int pageNow,int pageSize){
    public ArrayList getUsersByPage(int pageNow, int pageSize) {
        //使用ArrayList方式更符合面向对象的编程方式 OOP
        //我们通过 ResultSet->user 对象->ArrayList 可以及时关闭数据库资源
       ArrayList<User> al=new ArrayList<User>();

        //查询sql
//        String sql="select top "+pageSize+"* from users where id not in (select top "+pageSize*(pageNow-1) +" id from users order by id desc) order by id desc";
       //for mysql
        String sql="select * from users order by id asc limit "+pageSize*(pageNow-1) +","+pageSize;

        ResultSet rs=SqlHelper.executeQuery(sql,null);

        try {
            //二次封装  ResultSet -> User对象 -> ArrayList(集合)
            while (rs.next()){
                User us=new User();//此句放到while外面 就完蛋了——ArrayList中只有最后那条记录
                us.setId(rs.getString("id"));
                us.setName(rs.getString("username"));
                us.setEmail(rs.getString("email"));
                us.setGrade(rs.getString("grade"));
                us.setPwd(rs.getString("passwd"));
                us.setUnknow(rs.getString("unknow"));
                us.setRemark(rs.getString("remark"));
                //将用户的对象加入集合
                al.add(us);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            SqlHelper.close(rs,SqlHelper.getPs(),SqlHelper.getCt());
        }

        return al;
    }

    //通过id获取用户信息
    public User getUserById(String id){
        User user=new User();
        String sql="select * from users where id=?";
        String parameters[]={id};
        ResultSet rs=SqlHelper.executeQuery(sql,parameters);
        try {
            if(rs.next()){//取一条数据  while(rs.next())取全部数据
                //二次封装
                user.setId(rs.getString("id"));
                user.setName(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setGrade(rs.getString("grade"));
                user.setPwd(rs.getString("passwd"));
                user.setUnknow(rs.getString("unknow"));
                user.setRemark(rs.getString("remark"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            SqlHelper.close(rs,SqlHelper.getPs(),SqlHelper.getCt());
        }
        return user;
    }

    //添加用户
    public boolean addUser(User user){
        boolean b=true;
        //	String sql="insert into users values(user_seq.nextval,?,?,?,?,?,?)"
        //第一个ID 为自动递增，不想要插入
        String sql="insert into users (username,email,grade,passwd,unknow,remark) values(?,?,?,?,?,?)";
        String parameters[]={user.getName(),user.getEmail(),user.getGrade(),user.getPwd(),user.getUnknow(),user.getRemark()};

        try {
            SqlHelper.executeUpdate(sql,parameters);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            b=false;
        }
        return b;
    }
    public boolean checkUserNameExist(String username){
        boolean used=false;

        String sql="select username,email from users  where username=?";
        String params[]={username};

        try {
            ResultSet rs = SqlHelper.executeQuery(sql,params);
            if (rs.next()){
                used=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            used=true;
        }
        return used;

    }

    //修改用户
    public boolean updUser(User user){
        boolean b=true;

        //默认不修改密码
        String sql="update users set username=?,email=?,grade=?,passwd=?,unknow=?,remark=? where id=?";
        String parameters[]={user.getName(),user.getEmail(),user.getGrade(),user.getPwd(),user.getUnknow(),user.getRemark(),user.getId()};

        try {
            SqlHelper.executeUpdate(sql,parameters);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            b=false;
        }
        return b;
    }
    //删除用户
    public boolean delUser(String id){
        boolean b=true;
        String sql="delete from users where id=?";
        String params[]={id};

        try {
            SqlHelper.executeUpdate(sql,params);
        } catch (Exception e) {
            b=false;
        }
        return b;
    }

    public User checkLogin(User userx){
        User u=null;
        //使用sqlHelper来完成查询任务
        String sql="select id,username,email,passwd,grade from users where username=? and passwd=?";
        String parameters[]={userx.getName(),userx.getPwd()};

        ArrayList rs=SqlHelper.ExecuteReader(sql, parameters);

        if(rs.size()==1){
            u=new User();
            Object[] ob= (Object[]) rs.get(0);
            u.setId(ob[0].toString());
            u.setName(ob[1].toString());
            u.setEmail(ob[2].toString());
            u.setGrade(ob[4].toString());
        }

       return u;

    }
    //写一个验证用户是否合法的函数	(用49讲升级版SqlHelper的ExecuteReader)
    public boolean checkUser(User userx){
        //连接数据库；
        boolean isok=false;

        //使用sqlHelper来完成查询任务
        String sql="select * from users where username=? and passwd=?";
        String parameters[]={userx.getName(),userx.getPwd()};

        ArrayList rs=SqlHelper.ExecuteReader(sql, parameters);


        //根据rs来判断该用户是否存在
        try {

            if(rs.size()==1){
                isok=true;
            }


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return isok;
    }

/*	public boolean checkUser(User userx){
		//连接数据库；
		boolean b=false;
		//使用sqlHelper来完成查询任务
		String sql="select * from users where id=? and passwd=?";
		String parameters[]={userx.getId()+"",userx.getPwd()};
		ResultSet rs=SqlHelper.executeQuery(sql,parameters);
		//根据rs来判断该用户是否存在
		try {
			if(rs.next()){
				b=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			SqlHelper.close(rs,SqlHelper.getPs(),SqlHelper.getCt());
		}
		return b;
	}*/
}
