package cn.tekin.servlet_demo.book;

import java.util.ArrayList;

final public class DB {
    //用ArrayList 模拟一个内存数据库
    private static ArrayList al = null;

    static {
        al = new ArrayList<Book>();

        Book book1 = new Book();
        book1.setId("1");
        book1.setName("Java");
        //	book1.setPrice(23.4f);

        Book book2 = new Book();
        book2.setId("2");
        book2.setName("Oracle");

        Book book3 = new Book();
        book3.setId("3");
        book3.setName("Linux++");

        Book book4 = new Book();
        book4.setId("4");
        book4.setName("C++");

        al.add(book1);
        al.add(book2);
        al.add(book3);
        al.add(book4);
    }

    private DB() {}    //构造函数 单态

    public static ArrayList getDB() {
        return al;
    }

    public static Book getBookById(String id) {
        Book book = null;
        boolean b = false;
        for (int i = 0; i < al.size(); i++) {
            book = (Book) al.get(i);
            //book.getId()==id  判断地址是否相同
            if (book.getId().equals(id)) {
                b = true;
                //	System.out.println("========");
                break;
            }
        }
        //	System.out.println("id="+book.getName()+b);
        if (b) return book;
        else return null;
    }
}

