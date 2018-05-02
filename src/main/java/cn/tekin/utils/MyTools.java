package cn.tekin.utils;

public class MyTools {
    public static String getNewString(String str) {
        String newString="";
        try {
            //老版本的tomcat使用， 新版本tomcat默认utf-8编码，无需此方法
            //	newString=new String(str.getBytes("iso-8859-1"),"utf-8");	//中文传到浏览器是乱码
            newString=new String(str.getBytes("iso-8859-1"),"gb2312");
        } catch (Exception e) {
            e.printStackTrace();
            // 把iso-8859-1 转换成 utf-8
        }
        return newString;
    }
}
