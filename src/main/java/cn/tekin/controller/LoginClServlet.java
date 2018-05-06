package cn.tekin.controller;

import cn.tekin.domain.User;
import cn.tekin.service.UsersService;
import cn.tekin.utils.HashEncrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;


@WebServlet(name = "LoginClServlet", value = "/LoginClServlet")
public class LoginClServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        String DATE_FORMAT = getServletContext().getInitParameter("DATE_FORMAT");

        HttpSession session = request.getSession();//创建session 会话



        //获取时间戳
        long longTime = new Date().getTime();

        PrintWriter out = response.getWriter();
        int atcount = -1;


        String username = request.getParameter("username");
        String passwd = request.getParameter("passwd");
        String isRember = request.getParameter("isRember");

        //密码尝试次数限制
        // 所有的 cookie
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            // 修改 Cookie，更新用户的访问次数
            Cookie visitTimesCookie = new Cookie("atcount", Integer.toString(++atcount));
            visitTimesCookie.setMaxAge(3600);
            response.addCookie(visitTimesCookie);
        }
        else {
            for (int i = 0; i < cookies.length; i++) {
                if ("atcount".equals(cookies[i].getName())) atcount = Integer.parseInt(cookies[i].getValue());
            }
        }

        if (atcount > 4) {
            out.println("密码错误次数超限，你已被禁止登陆本系统！");
            response.setHeader("Refresh", "5;url=http://www.yunnan.ws/");
            out.close();
        }
        else {
            //创建UserService对象，完成到数据库的验证
            UsersService userservice = new UsersService();
            //创建user对象
            User usr = new User();
            //将验证的数据放入USER对象
            usr.setName(username);
            usr.setPwd(HashEncrypt.encryptDiy(passwd, getServletContext().getInitParameter("pwSalt"), null));

            //使用userservice 验证
            // if (userservice.checkUser(usr)) {//验证成功
            if (userservice.checkLogin(usr) != null) {//验证成功
                //将用户对象放入session中
                //                request.getSession().setAttribute("userObj", userservice.checkLogin(usr));
                session.setAttribute("userObj", userservice.checkLogin(usr));

                System.out.println("Longtime:" + Long.toString(longTime));

                //日期中有空格，所以必须使用""包裹着
                Cookie lcookie = new Cookie("lastLoginTime", Long.toString(longTime));
                //保存一周
                lcookie.setMaxAge(3600 * 24 * 7);
                response.addCookie(lcookie);


                //使用COOKIE记住用户名
                if (isRember.equals("1")) {
                    Cookie imcookie = new Cookie("username", username);
                    imcookie.setMaxAge(3600 * 24 * 7);
                    imcookie.setPath("/");
                    response.addCookie(imcookie);
                }
                else {
                    //清除cookie
                    Cookie[] delCookies = request.getCookies();
                    if (delCookies != null) {
                        for (int i = 0; i < delCookies.length; i++) {
                            if (delCookies[i].getName().equals("username")) {
                                delCookies[i].setMaxAge(0);
                                delCookies[i].setPath("/");
                                response.addCookie(delCookies[i]);
                                System.out.println("delete cookie" + i);
                            }
                        }

                    }
                }
                //记住用户名end

                //能用请求转发就不要使用 重定向
                request.getRequestDispatcher("/MainFrame").forward(request, response);
            }
            else {


                if (cookies != null) {
                    // 遍历所有的 Cookie 寻找 用户帐号信息与登录次数信息
                    for (int i = 0; i < cookies.length; i++) {
                        Cookie cookie = cookies[i];
                        if ("atcount".equals(cookie.getName())) {
                            atcount = Integer.parseInt(cookie.getValue());
                            cookie.setValue("" + ++atcount);

                        }
                    }

                }

                Cookie visitTimesCookie = new Cookie("atcount", Integer.toString(atcount));
                visitTimesCookie.setMaxAge(3600);
                response.addCookie(visitTimesCookie);


                System.out.println("尝试次数：" + atcount);

                request.setAttribute("error", "登录失败,用户名或者密码错误");
                request.getRequestDispatcher("/Login.do").forward(request, response);
            }
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Cookie cookies[] = req.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                cookies[i].setMaxAge(0);
                resp.addCookie(cookies[i]);
            }
        }
        //删除session
        req.getSession().removeAttribute("username");

        resp.sendRedirect(req.getServletContext().getInitParameter("HOME_URL") + "Login.do");
    }
}
