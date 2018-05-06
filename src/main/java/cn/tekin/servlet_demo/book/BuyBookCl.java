package cn.tekin.servlet_demo.book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;

@WebServlet("/demo/book/BuyBookCl")
public class BuyBookCl extends HttpServlet {
    //	http://localhost:8080/myCart/BuyBookCl
    String HOME_URL = "";
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        //	response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        HOME_URL = request.getServletContext().getInitParameter("HOME_URL");

        //接收用户要购买书的名字
        //	String bookname=request.getParameter("name");

        String id = request.getParameter("id");
        //	System.out.println("id="+id);
        Book bookinfo = DB.getBookById(id);
        //	System.out.println("bookinfo.getName()="+bookinfo.getName());

        //只能买一本书
        //request.setAttribute("book",bookname);
	/*	HttpSession session=request.getSession();
		session.setAttribute("mybooks",bookname);
		//跳转到显示购物车的页面
		request.getRequestDispatcher("/ShowMyCart").forward(request,response);
		*/

        HttpSession session = request.getSession();

        HashMap<String, Book> hm = (HashMap<String, Book>) session.getAttribute("mybooks");

        //第一次购买 hm=null
        if (hm == null) {
            hm = new HashMap<String, Book>();
            //按照HashMap中的数据的顺序输出
            hm = new LinkedHashMap<String, Book>();

            //构建一个Book对象
            Book book = new Book();
            book.setId(id);
            //	book.setName(bookname);
            book.setName(bookinfo.getName());
            book.setNum(1);
            hm.put(id, book);
            session.setAttribute("mybooks", hm);    //必不可少
        }
        else {
            //判断hm中是否有该书
            if (hm.containsKey(id)) {
                //表示该书已经购买过一次 取出并加1
                Book book = hm.get(id);
                //	System.out.println("id="+id);
                int bookNum = book.getNum();
                book.setNum(bookNum + 1);
            }
            else {
                Book book = new Book();
                book.setId(id);
                //	book.setName(bookname);
                book.setName(bookinfo.getName());
                book.setNum(1);
                hm.put(id, book);
            }
            session.setAttribute("mybooks", hm);
        }

		/*
		 ArrayList al=(ArrayList) session.getAttribute("mybooks");

	//	ArrayList al=new ArrayList();	//只能买一本书		28:00

		if(al==null){
			al=new ArrayList();
			al.add(bookname);
		// 更新al 可以省略这句话，因为al引用的是地址
		//	session.setAttribute("mybooks",al);

			//构建一个Book对象
			Book book=new Book();
			book.setId(id);
			book.setName(bookname);
			al.add(book);

		}else{
		//	al.add(bookname);

			//遍历ArrayList， 看是否有重复

			//构建一个Book对象
			Book book=new Book();
			book.setId(id);
			book.setName(bookname);
			al.add(book);
		}
		 */

        //跳转到显示购物车的页面
        request.getRequestDispatcher("/demo/book/ShowMyCart").forward(request, response);

		/*
		//创建一个session， 并放入一个属性
		HttpSession session=request.getSession();
		//给该session放入属性
		session.setAttribute("username","狄成浩");
		//默认30分钟
		//把该session id保存到cookie 在保存id时，一定按照规范命名 必须大写
		Cookie ckie=new Cookie("JSESSIONID",session.getId());
		ckie.setMaxAge(60*30);
		response.addCookie(ckie);
		out.println("创建session并放入 username=狄成浩");	*/
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
