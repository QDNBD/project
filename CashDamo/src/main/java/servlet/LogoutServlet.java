package servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //判断是否登录
        if (req.getSession().getAttribute("user") != null) {
            //如果是登陆状态则抹除session中的USER_SESSION数据
            req.getSession().removeAttribute("user");
            //重定向回登陆界面
            resp.sendRedirect("/CashDamoWar/login.html");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        doGet(req, resp);
    }
}
