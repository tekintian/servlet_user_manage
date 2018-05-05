package cn.tekin.servlet_demo;

public class Demo {

    /**
     * 测试主函数
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception {

        String mystr = "c47d5f232e93f88d332c7725a6fa27df";
        mystr=mystr.replaceAll("(\\w{5})(\\w{15})(\\w{5})(\\w+)","*****$2*****$4");
        System.out.println(mystr);

    }
}
