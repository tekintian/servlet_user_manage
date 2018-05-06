package cn.tekin.servlet_demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet(name = "DownloadFile", value = {"/getfile.do", "/DownloadFile"})
public class DownloadFile extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //response.setContentType("image/jpeg");
        response.setContentType("text/html");

        //下载文件
       response.setHeader("Content-Disposition","attachment;filename=demo.jpg");

        //获取文件路径
        String path = this.getServletContext().getRealPath("/images/demo.jpg");
        //创建文件输入流
        FileInputStream fis = new FileInputStream(path);
        //做一个缓冲字节数组
        byte buff[]=new byte[1024];
        int len=0;//表示实际每次读取了多少个字节
        OutputStream os = response.getOutputStream();
        while ((len=fis.read(buff))>0){
            os.write(buff, 0,len);
        }
        //关闭
        os.close();
        fis.close();
    }
}
