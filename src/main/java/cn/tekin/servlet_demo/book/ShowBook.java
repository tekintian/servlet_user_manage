package cn.tekin.servlet_demo.book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/demo/book/ShowBook")
public class ShowBook extends HttpServlet {
    //	http://localhost:8080/myCart/ShowBook
    String HOME_URL = "";
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        //	response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        HOME_URL = request.getServletContext().getInitParameter("HOME_URL");
        //先死后活
        out.println("<h1>欢迎购买</h1>");
        //	out.println("Java书<a href='/myCart/BuyBookCl?id=1&name=Java'>点击购买</a><br/>");
        //	out.println("Oracle书<a href='/myCart/BuyBookCl?id=2&name=Oracle'>点击购买</a><br/>");
        //	out.println("C++书<a href='/myCart/BuyBookCl?id=3&name=C++'>点击购买</a><br/>");

        //取出DB中的书籍
        ArrayList<Book> mydb = DB.getDB();

        for (Book book : mydb) {
            //	out.println(book.getName()+"<a href='/myCart/BuyBookCl?id="+book.getId()+"'>点击购买</a><br/>");
            //希望href 的样式是href="/myCart/BuyBookCl?id=x&JSESSION=x21ak"
            String url = response.encodeURL(HOME_URL + "/demo/book/BuyBookCl?id=" + book.getId());
            out.println(book.getName() + "<a href='" + url + "'>点击购买</a><br/>");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}

