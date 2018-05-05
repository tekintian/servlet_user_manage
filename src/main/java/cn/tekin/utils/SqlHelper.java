package cn.tekin.utils;

import cn.tekin.domain.User;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class SqlHelper {

    static Properties pconf = null;
    //	static FileInputStream fis=null;
    static InputStream ism = null;    //.getClassLoader().getResourceAsStream("dbinfo.properties")
    //定义需要的变量
    private static Connection ct = null;
    private static PreparedStatement ps = null;    //不用Statement 防sql注入

    //连接数据库参数
    // 定义数据库连接参数
//	private static final String url="jdbc:mysql://127.0.0.1:3306/javatest";
//	private static final String user="root";
//	private static final String password="888888";
//	private static final String DRIVER="com.mysql.jdbc.Driver";
    private static ResultSet rs = null;
    private static CallableStatement cs = null;
    private static String url = "";
    private static String user = "";
    private static String password = "";
    private static String DRIVER = "";

    //加载驱动，只需要一次, 声明为 static 只想要加载一次
    static {
        try {
            //从配置文件dbinfo.properties文件中读取信息
            pconf=new Properties();

            //当我们使用java web时， 读取文件应该使用类加载器[类加载器在读取资源时
            // 默认的主目录是src] com/hsp/---/dbinfo.properties
            ism = SqlHelper.class.getClassLoader().getResourceAsStream("dbinfo.properties");

            pconf.load(ism);
            url = pconf.getProperty("url");
            user = pconf.getProperty("user");
            password = pconf.getProperty("password");
            DRIVER = pconf.getProperty("DRIVER");

//            System.out.println(DRIVER);
//            System.out.println(url);
//            System.out.println(user);
//            System.out.println(password);

            Class.forName(DRIVER);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭ism
                ism.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            ism = null;
        }
    }

    //得到连接的方法
    public static Connection getConnection() {
        try {
            ct = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ct;
    }

    //分页问题？
    public static ResultSet executeQuery2() {
        return null;
    }

    //调用存储过程（返回ResultSet）
    //sql 格式  {call 过程(?,?,?)}
    public static CallableStatement callPro2
    (String sql, String[] inparameters, String[] outparameters) {
        try {
            ct = getConnection();
            cs = ct.prepareCall(sql);
            //? 赋值
            if (inparameters != null) {
                for (int i = 0; i < inparameters.length; i++) {
                    cs.setObject(i + 1, inparameters[i]);
                }
            }
            //	cs.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
            //给out参数复制
            if (outparameters != null) {
                for (int i = 0; i < outparameters.length; i++) {
                    //		cs.registerOutParameter(inparameters.length+1+i, outparameters[i]);		//Error
                    cs.registerOutParameter(inparameters.length + 1 + i, Integer.parseInt(outparameters[i]));
                }
            }

            cs.execute();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            //不需要关闭
        }
        return cs;
    }


    //调用存储过程（没有返回值）
    //sql 格式  {call 过程(?,?,?)}
    public static void callPro1(String sql, String[] parameters) {
        try {
            ct = getConnection();
            cs = ct.prepareCall(sql);

            //? 赋值
            if (parameters != null) {
                for (int i = 0; i < parameters.length; i++) {
                    cs.setObject(i + 1, parameters[i]);
                }
            }
            cs.execute();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            close(rs, cs, ct);
        }
    }


    //统一的select语句
    public static ResultSet executeQuery(String sql, String[] parameters) {
        try {
            ct = getConnection();
            ps = ct.prepareStatement(sql);
            if (parameters != null && !parameters.equals("")) {
                for (int i = 0; i < parameters.length; i++) {
                    ps.setString(i + 1, parameters[i]);
                }
            }
            rs = ps.executeQuery();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            //close(rs,ps,ct);
        }
        return rs;
    }


    //如果有多个update/delete/insert【需要考虑事务】
    public static void executeUpdate2(String sql[], String[][] parameters) {
        try {
            //核心
            //获得连接
            ct = getConnection();
            //	ps=ct.prepareStatement(sql[]);
            //因为这时 用户传入的是多个sql语句
            ct.setAutoCommit(false);
            //...
            for (int i = 0; i < sql.length; i++) {
                if (parameters[i] != null) {
                    ps = ct.prepareStatement(sql[i]);
                    for (int j = 0; j < parameters[i].length; j++) {
                        ps.setString(j + 1, parameters[i][j]);
                    }
                    ps.executeUpdate();
                }
            }
            //	int x= 9/0;	//by zero

            ct.commit();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            //回滚
            try {
                ct.rollback();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            throw new RuntimeException(e.getMessage());
        } finally {
            close(rs, ps, ct);
        }
    }

    //方法——修改update/delete/insert
    //sql格式——update 表名 set 字段名=? where 字段=?
    //parameters应该是{"abc","23"};
    public static void executeUpdate(String sql, String[] parameters) {
        //创建一个ps
        try {
            ct = getConnection();
            ps = ct.prepareStatement(sql);
            //给?赋值
            if (parameters != null) {
                for (int i = 0; i < parameters.length; i++) {
                    ps.setString(i + 1, parameters[i]);
                }
            }
            //执行
            ps.executeUpdate();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace(); //开发阶段
            //抛出异常  运行式异常，给调用者一个处理的机会(可以处理，也可以忽略)
            throw new RuntimeException(e.getMessage());
        } finally {
            //关闭资源
            close(rs, ps, ct);
        }
    }

    //对查询语句升级executeQuery3	用于获取结果集语句(eg: select * from table)
    //好处：哪里使用资源哪里及时关闭
    // @param sql 	@param parms 	@return ResultSet 	@throws RuntimeException
    public static ArrayList ExecuteReader(String sql, String[] parms) {
        PreparedStatement pstmt = null;
        Connection conn = null;
        ResultSet rs = null;
        //	ArrayList list = null;
        try {
            conn = getConnection();    //conn=getConnect();
            pstmt = conn.prepareStatement(sql);
//			prepareCommand(pstmt,parms);	//封装到函数中了
            //	给问号赋值
            if (parms != null && !parms.equals("")) {
                for (int i = 0; i < parms.length; i++) {
                    pstmt.setString(i + 1, parms[i]);
                    //	ps.setObject(i+1, parms[i]);
                }
            }

            rs = pstmt.executeQuery();

            ArrayList al = new ArrayList();
            ResultSetMetaData rsmd = rs.getMetaData();
            int column = rsmd.getColumnCount();    //返回列的数量

            while (rs.next()) {
                Object[] ob = new Object[column];    //对象数组 代表1行记录
                for (int i = 1; i <= column; i++) {
                    ob[i - 1] = rs.getObject(i);
                }
                al.add(ob);
            }
            //	System.out.println("-----升级executeQuery3-----");
            //	rs.close();	//不如在下面关闭
            return al;
        } catch (Exception e) {
            //	Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, ex);
            e.printStackTrace();
            throw new RuntimeException("executeSqlResultSet方法出错： " + e.getMessage());
        } finally {
            //	closeConn(); //关闭资源
            try {
                close(rs, pstmt, conn);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("executeSqlResultSet方法出错： " + e.getMessage());
            }
        }
    }

    //关闭资源函数 后打开的先关闭
    public static void close(ResultSet rs, Statement ps, Connection ct) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (ps != null) {
            try {
                ps.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            ps = null; //使用垃圾回收
        }

        if (ct != null) {
            try {
                ct.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            ct = null;
        }
    }

    public static Connection getCt() {
        return ct;
    }

    public static PreparedStatement getPs() {
        return ps;
    }

    public static ResultSet getRs() {
        return rs;
    }

    public static CallableStatement getCs() {
        return cs;
    }

    //按照分页来获取用户		49讲采用升级executeQuery3
    //公司返回处理方式  ResultSet -> User对象 -> ArrayList(集合)
    public ArrayList getUsersByPage(int pageNow, int pageSize) {

        ArrayList<User> al = new ArrayList<User>();
        //查询sql for mssql and oracle
//        String sql="select top "+pageSize+"* from users where id not in (select top "+pageSize*(pageNow-1) +" id from users order by id desc) order by id desc";

        //for mysql
        String sql = "select * from users order by id asc limit " + pageSize * (pageNow - 1) + "," + pageSize;

        //	二次封装  ResultSet -> User对象 -> ArrayList(集合)
        //	ResultSet rs=SqlHelper.executeQuery(sql,null);

        //	二次封装  ArrayList[对象数组 - 每个元素都是对象] -> User对象 -> ArrayList(集合)
        ArrayList al2 = SqlHelper.ExecuteReader(sql, null);
        try {
            for (int i = 0; i < al2.size(); i++) {
                Object[] objs = (Object[]) al2.get(i);
                User user = new User();
                user.setId(objs[0].toString());
                //	user.setId((Integer)objs[0]);
                user.setName(objs[1].toString());
                user.setEmail(objs[2].toString());
                user.setGrade((String) objs[3]);
                user.setPwd(objs[4].toString());
                user.setUnknow(objs[5].toString());
                user.setRemark(objs[6].toString());
                al.add(user);
            }

		/*	while(rs.next()){
				User u=new User();  //此句放到while外面 就完蛋了——ArrayList中只有最后那条记录
				u.setId(rs.getInt(1));
				u.setName(rs.getString(2));
				u.setEmail(rs.getString(3));
				u.setGrade(rs.getInt(4));
				u.setPwd(rs.getString(5));
				u.setUnknowx(rs.getString(6));
				u.setRemarkx(rs.getString(7));
				//千万不要忘记 u-> arraylist
				al.add(u);
			}	*/
            //	} catch (SQLException e) {
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }/*finally{
			SqlHelper.close(rs,SqlHelper.getPs(),SqlHelper.getCt());
		}	*/
        return al;
    }
}
