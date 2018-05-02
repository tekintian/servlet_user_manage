package cn.tekin.servlet_demo;

/**
 * 服务器进程demo
 */
public class CheckMyTaskThread extends Thread {
    @Override
    public void run() {
        int i=0;
        try {
            while(true){
                //每休眠一分钟，就去扫表sendmail, 看看那份信件应当被发出
                Thread.sleep(1000*60*1);//每隔 1分钟 执行一次 【默认单位 毫秒， 1秒等于1000毫秒 】，
                System.out.println(" 第 "+(++i)+" 次检查任务");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
