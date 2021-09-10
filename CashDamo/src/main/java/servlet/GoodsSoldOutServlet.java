package servlet;

import dao.GoodsSoldOutDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;


@WebServlet("/delGoods")
public class GoodsSoldOutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GoodsSoldOutDao goodsSoldOutDao = new GoodsSoldOutDao();

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=utf-8");

        Writer writer = resp.getWriter();
        String str = req.getParameter("id");
        log(str);

        Integer id = Integer.parseInt(str.trim());
        log("id" + id);

        int ret = goodsSoldOutDao.delete(id);
        if (ret == 1) {
            writer.write("<h2> 删除成功：" + id + "</h2>");
        } else {
            writer.write("<h2> 下架失败：" + id + "</h2>");
        }

    }

}
