## JAVA中,equal和==的区别
一、比较对象为基本数据类型（byte,short,char,int,long,float,double,boolean）
比较两个基本数据类型是否相等用==，因为只有类才会有equals方法。
备注：String不是基本数据类型

二、比较对象为引用数据类型
euqals和==本质上都是比较比较的是两个对象的引用（内存地址）是否相同。equals()是Object类的方法 ，object类是所有类的基类，所以每个类都会继承equals（）方法。
但在String,Integer,Date在这些类当中重写了equals方法，而不再是比较对象在堆内存中的存放地址了，而是比较它们指向的实体（内容）是否相同。


==比较的是对象引用的地址，也就是是否是同一个对象； 
equal比较的是对象的值。 
例如：
Integer r1 = new Integer(900);//定义r1整型对象 
Integer r2 = new Integer(900);//定义r2整型对象 
System.out.println(r1==r2);//返回false 
System.out.println(r1.equal(r2));//返回true

在String比较字符串的时候一定要使用equal，否则会发现字符串一样也判断不等，这是初学者容易出错的地方。

##java 时间戳获取
```java
 long timestamp=new Date().getTime();
 SimpleDateFormat simpleDateFormat=new SimpleDateFormat(getServletContext().getInitParameter("Y-m-d HH:mm:ss E"));
 
simpleDateFormat.format(timestamp);

```
       
## cookie操作

```java
//获取表达传递来的值
String isRember = request.getParameter("isRember");

//使用COOKIE记住用户名
if (isRember.equals("1")) {
    //增加cookie
    Cookie imcookie=new Cookie("username", username);
    imcookie.setMaxAge(3600*24*7);
    imcookie.setPath("/");
    response.addCookie(imcookie);
}else{
    //清除cookie
    Cookie []delCookies=request.getCookies();
    if (delCookies != null) {
        for (int i = 0; i <delCookies.length ; i++) {
            if (delCookies[i].getName().equals("username")){
                delCookies[i].setMaxAge(0);
                delCookies[i].setPath("/");
                response.addCookie(delCookies[i]);
                System.out.println("delete cookie"+i);
            }
        }

    }
}
//记住用户名end


```
- 使用cookie记录登陆时间需要特别注意，不能直接记录 带有空格的时间，需要使用时间戳

- 取出时间戳格式的时候需要转换类型为Long
```java
 SimpleDateFormat simpleDateFormat=new SimpleDateFormat(getServletContext().getInitParameter("Y-m-d HH:mm:ss"));


String lastLoginTime="";
//从cookie中取出时间戳并格式化
Cookie []cookies=request.getCookies();
if (cookies != null) {
    for(int i=0; i<cookies.length; i++){
        if("lastLoginTime".equals(cookies[i].getName())) {
            lastLoginTime = simpleDateFormat.format(Long.parseLong(cookies[i].getValue()));
        }
    }
}

//输出格式后的时间
System.out.println(lastLoginTime);

```



## http响应消息头详解
Location: http://www.baidu.org/index.jsp  【让浏览器重新定位到url】
Server:apache tomcat 【告诉浏览器我是tomcat】
Content-Encoding: gzip 【告诉浏览器我使用 gzip】
Content-Length: 80  【告诉浏览器会送的数据大小80节】
Content-Language: zh-cn 【支持中文】
Content-Type: text/html; charset=GB2312 [内容格式text/html; 编码gab2312]
Last-Modified: Tue, 11 Jul 2000 18:23:51 GMT 【告诉浏览器，该资源上次更新时间】
Refresh: 1;url=http://www.baidu.com 【过多久去，刷新到 http://www.baidu.com】
Content-Disposition: attachment; filename=aaa.zip 【告诉浏览器，有文件下载】
Transfer-Encoding: chunked 校验盒 [传输的编码]
Set-Cookie:SS=Q0=5Lb_nQ; path=/search[后面详讲]
Expires: -1[告诉浏览器如何缓存页面IE]
Cache-Control: no-cache  [告诉浏览器如何缓存页面火狐]
Pragma: no-cache   [告诉浏览器如何缓存页面]
Connection: close/Keep-Alive   [保持连接 1.1是Keep-Alive]
Date: Tue, 11 Jul 2000 18:23:51 GMT

①定时刷新Refresh使用
 response.setHeader("Refresh", "5;url=/servletPro/Servlet2");

②文件下载 Content-Disposition 
public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		//PrintWriter out = response.getWriter();
		
		//演示下载文件, 没有这一句 只能在页面上显示图片
		response.setHeader("Content-Disposition", "attachment; filename=winter.jpg");
		
		//打开文件.说明一下web 站点下载文件的原理
		//1.获取到要下载文件的全路径
		String path=this.getServletContext().getRealPath("/images/Winter.jpg");
		//System.out.println("path="+path);
		//2创建文件输入流
		FileInputStream fis=new FileInputStream(path);
		//做一个缓冲字节数组
		byte buff[]=new byte[1024];
		int len=0;//表示实际每次读取了多个个字节
		OutputStream os=response.getOutputStream();
		while((len=fis.read(buff))>0){
			
			os.write(buff, 0, len);
		}
		//缺点: 没有进度条./图标/
		
		//关闭
		os.close();
		fis.close();
	}


 ③缓存讲解
提出问题：浏览器默认情况下，会缓存我们的页面，这样出现一个问题：如果我们的用户习惯把光标停留在地址栏，然后回车来取页面，就会默认调用cache中取数据。
（1）有些网站要求及时性很高，因此要求我们不缓存页面
代码：
//指定该页面不缓存 Ie
		response.setDateHeader("Expires", -1);//【针对IE浏览器设置不缓存】
		//为了保证兼容性.
		response.setHeader("Cache-Control", "no-cache");//【针对火狐浏览器等】
		response.setHeader("Pragma", "no-cache");//【其他浏览器】
（2）有些网站要求网页缓存一定时间,比如缓存一个小时
response.setDateHeader("Expires", System.currentTimeMillis()+3600*1000*24);	//后面一个参数表示设置的缓存保持时间，-1表示永远缓存


HttpServletResponse的再说明
getWriter()
getOutputStream();

区别
1.getWriter() 用于向客户机回送字符数据
2.getOutputStream() 返回的对象，可以回送字符数据，也可以回送字节数据(二进制数据)
OutputStream os=response.getOutputStream();
os.write("hello,world".getBytes());

如何选择:
如果我们是回送字符数据，则使用  PrintWriter对象 ,效率高
如果我们是回送字节数据(binary date) ,则只能使用 OutputStream
☞ 这两个流不能同时使用.

说明:  基本格式:
response.sendRedirect(“servlet的地址?参数名=参数值&参数名=参数值...”);

☞ 参照值是String , 参数名应当使用 字母组合

使用sendRedirect()	
response.sendRedirect("/UsersManager/MainFrame?uname="+username+"&pwd="+password);
在接受数据的Servlet中：
String 参数=request.getParameter(“参数名”);

3.使用session 传递[后面讲]			这里，我们先预热.
②使用session()来传递字符参数和对象
A.传递字符串  //3. 使用session来传递数据给下个页面
放入session   request.getSession ().setAttribute("loginUser",username); 
取出session	 在JSP中通过session取出 request.getSession.getAttribute("loginUser");
//3. 使用session来传递数据给下个页面
request.getSession().setAttribute("loginUser",username2);		
String username3=(String) request.getSession().getAttribute("loginUser");
out.println("<br/>  SessionUsername= "+username3+"<br/>");

B．传递对象
User user= new User();
user.setName(“xiaoli”);
user.setPassWord(“123”);

下载提示框中文乱码
补充一个知识点: 当我们下载文件的时候，可能提示框是中文乱码 
String temp=java.net.URLEncoder.encode("传奇.mp3","utf-8");
response.setHeader("Content-Disposition","attachment; filename="+temp);
String temp=java.net.URLEncoder.encode(“传奇.mp3”,”utf-8”);		//统一资源定位
Response.setHeader(“Content-Disposition”,”attachment;filename=”+temp);


response细节
1. getOutStream和getWriter方法分别用于得到输出二进制数据、文本数据的ServletOutputStream、PrintWriter对象。
2. 在同一个response对象中getOutputStream和getWriter这两个方法互相排斥，调用其中一个方法后，就不能再调用另外一个方法。
3. Servlet程序向ServletOutputSteam或PrintWriter对象中写入的数据将被Servlet引擎从response里面获取，Servlet引擎将这些数据当作相应消息的正文，然后再与相应状态行和各相应头组合后输出到客户端。
4.Servlet的service方法结束后，Servlet引擎将检查getWriter或getOutputSteam方法返回的输出流对象是否已经调用close方法，如果没有，Servlet引擎将调用之关闭输出流对象。

HttpServletRequest对象的详解					该对象表示浏览器的请求(http请求), 当web 服务器得到该请求后，会把请求信息封装成一个HttpServletRequest 对象
•getRequestURL方法返回客户端发出请求时的完整URL。
•getRequestURI方法返回请求行中的资源名部分。
•getQueryString 方法返回请求行中的参数部分(参数名+值)。
该函数可以获取请求部分的数据 比如  http://localhost/web名?username=abc&pwd=123
request.getQueryString(); 就会得到  username=abc&pwd=123

getRemoteAddr方法返回发出请求的客户机的IP地址
getRemoteHost方法返回发出请求的客户机的完整主机名  如果客户机没有在dns上注册(一般上网，要提供一个域名让别人去访问) 则返回ip地址
pw.println("你的IP="+req.getRemoteAddr()+"<br>");
pw.println("你的机器名是"+req.getRemoteHost()+"<br>");
getRemotePort方法返回客户机所使用的网络端口号
客户机的端口号是随机选择的，web服务器的端口号是一定的
getLocalPort方法返回web服务器所使用的网络端口号
getLocalAddr方法返回WEB服务器的IP地址。
getLocalName方法返回WEB服务器的主机名
url 和 uri 的区别
比如：
	Url=http://localhost:8088/servletPort3/GetinfoServlet 完整的请求
	Uri=/servletPort3/GetinfoServlet web应用的名称+资源的名称

请求转发requeset.getRequestDispatcher(资源地址).forward(request,response);
资源地址：不需要项目名。因为它只是在WEB服务器内部转发。
Request.getRequestDispatcher(资源地址).forward(request,response);
我们现在使用 请求转发的方法来实现上次我们使用 response.sendRedirect() 实现效果
使用 request提供的转发方法.

Request中的Attribute在一次请求有效。一次请求：没有返回到浏览器，就为一次请求。

1.使用 forward 不能转发到 该web应用外的 url
2.因为 forward 是发生在web服务器，所以 Servlet1 和 Servlet 2使用的是用一个request 和response.
使用sendRedirect() 方法不能通过request.setAttribute() 把 属性传递给下一个Servlet

比较sendRedirect()和request.getRequestDispatcher().forward(request,response)
请问  sendRedirect() 和 forward 的区别是什么		答:
(1)叫法:   sendRedirect() 重定向,转发  forward() 叫转向
(2)实际发生的位置不一样:  sendRedirect 发生 浏览器;  forward 发生 web服务器.
(3)用法不一样
request.getRequestDispatcher(“/资源URI”).forward(request,response)
response.sendRedirect(“/web应用/资源URI”);
(4)能够去URL 范围不一样
sendRedirect 可以去 外边URL;   forward 只能去当前的WEB应用的资源

小练习:
使用uml 软件，画出  forward 和 sendRedirect() 的流程.

☞　如果转发多次，我们的浏览器地址栏，保留的是第一次 转向的那个Servlet Url

☞ 什么是一次 http请求:
只要没有停止，也没有回到浏览器重定向，就算一次			比如；



create table users (id int primary key,
 username varchar(32) not null,
 email varchar(64) not null,
 grade int default 1,
 passwd varchar(32) not null,
 unknow varchar(32) null,
 remark varchar(32) null
 )

insert into users values(1,'aaaaa1','aaaa1@sohu.com',1,'123','','');
insert into users values(2,'aaaaa2','aaaa2@sohu.com',1,'123','','');
insert into users values(3,'aaaaa3','aaaa3@sohu.com',1,'123','','');
insert into users values(4,'aaaaa4','aaaa4@sohu.com',1,'123','','');
insert into users values(5,'aaaaa5','aaaa5@sohu.com',5,'123','','');


