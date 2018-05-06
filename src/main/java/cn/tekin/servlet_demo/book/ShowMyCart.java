package cn.tekin.servlet_demo.book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;

@WebServlet("/demo/book/ShowMyCart")
public class ShowMyCart extends HttpServlet {
    //	http://localhost:8080/myCart/ShowMyCart
    String HOME_URL = "";
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        //	response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        HOME_URL = request.getServletContext().getInitParameter("HOME_URL");

        //从session中取出购买的书
        //	String bookname=(String) request.getSession().getAttribute("mybooks");

	/*	ArrayList myBooks=(ArrayList) request.getSession().getAttribute("mybooks");

		out.println("你的购物车中有以下书籍： <br/>");
		//	out.println(bookname);

			for(int i=0;i<myBooks.size();i++){
				out.println(myBooks.get(i).toString()+"<br/>");
			}		*/

        HashMap<String, Book> myBooks = (HashMap<String, Book>) request.getSession().getAttribute("mybooks");

        out.println("你的购物车中有以下书籍： <br/>");
        //	out.println(bookname);

        //遍历hashmap
        Iterator iterator = myBooks.keySet().iterator();

        while (iterator.hasNext()) {
            String key = (String) iterator.next();

            Book book = myBooks.get(key);
            out.println(book.getName() + "　　　　　" + book.getNum() + "<br/>");
        }

        out.println("购物车的总价格是 900 元");

        //	out.println("<br/><br/><a href='/myCart/ShowBook'>返回购物大厅</a>");
        //这里也需要地址重写
        String url = response.encodeURL(HOME_URL + "demo/book/ShowBook");
        out.println("<br/><br/><a href='" + url + "'>返回购物大厅</a>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
