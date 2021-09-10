package servlet;

import dao.GoodsPutAwayDao;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;


@WebServlet("/inbound")
public class GoodsPutAwayServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        GoodsPutAwayDao goodsPutAwayDao = new GoodsPutAwayDao();

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=utf-8");

        String name = req.getParameter("name");
        String stock = req.getParameter("stock");
        String introduce = req.getParameter("introduce");
        String unit = req.getParameter("unit");
        String price = req.getParameter("price");//89.9

        double doublePrice = Double.parseDouble(price);//89.9  8990.0

        int realPrice = new Double(100 * doublePrice).intValue();//8990

        String discount = req.getParameter("discount");

        Writer writer = resp.getWriter();

        int ret = goodsPutAwayDao.Update(name, introduce, stock, unit, realPrice, discount);

        if (ret == 0) {
            writer.write("<h2> 商品上架失败</h2>");
        } else {
            writer.write("<h2> 商品上架成功</h2>");
            System.out.println("商品上架成功");
            resp.sendRedirect("index.html");
        }

    }
}
