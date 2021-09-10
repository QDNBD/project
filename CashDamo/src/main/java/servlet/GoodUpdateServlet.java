package servlet;

import bean.Goods;
import service.GoodsUpdateService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;


@WebServlet("/updategoods")
public class GoodUpdateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        GoodsUpdateService goodsUpdateService = new GoodsUpdateService();

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

        String goodsIdString = req.getParameter("goodsID");
        int goodsId = Integer.parseInt(goodsIdString);

        Writer writer = resp.getWriter();

        //1、看是否存在 goodsId 这样的商品，如果有拿到这个商品。
        Goods goods = goodsUpdateService.getGoods(goodsId);

        if (goods == null) {
            writer.write("<h2> 没有该商品" + goodsId + "</h2>");
            log("没有该商品");
        } else {
            //goods  存储的就是需要更新的商品
            goods.setName(name);
            goods.setIntroduce(introduce);
            goods.setStock(Integer.valueOf(stock));
            goods.setUnit(unit);
            goods.setPrice(realPrice);
            goods.setDiscount(Integer.valueOf(discount));

            //将新的数据 写回到数据库当中
            boolean flg = goodsUpdateService.modify(goods);
            if (flg) {
                writer.write("<h2> 商品更新成功" + goodsId + "</h2>");
                log("商品更新成功");
                resp.sendRedirect("goodsbrowse.html");
            } else {
                writer.write("<h2> 商品更新失败" + goodsId + "</h2>");
                System.out.println("商品更新失败");

            }
        }
    }
}
