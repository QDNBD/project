package servlet;

import commen.OrderStatus;
import bean.Goods;
import bean.Order;
import service.BuyGoodsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@WebServlet("/buyGoodsServlet")
public class BuyGoodsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BuyGoodsService buyGoodsdao = new BuyGoodsService();

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=utf-8");

        HttpSession session=req.getSession();
        Order order = (Order) session.getAttribute("order");

        List<Goods> goodsList = (List<Goods>) session.getAttribute("goodsList");

        //将订单内的数据写入数据库当中
        order.setOrder_status(OrderStatus.OK);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        order.setFinish_time(LocalDateTime.now().format(formatter));
        //提交订单
        boolean flg = buyGoodsdao.commitOrder(order);
        /**
         * 1、更新哪些商品的库存？
         *    goodsList
         */
        if(flg) {
            //更新库存
            for ( Goods goods : goodsList) {
                boolean isUpdate = buyGoodsdao.updateAfterPay(goods,goods.getBuyGoodsNum());
                if(isUpdate) {
                    System.out.println("更新库存成功！");
                }else {
                    System.out.println("更新库存失败");
                    return;
                }
            }
        }else {
            System.out.println("插入订单失败！");
            return;
        }
        resp.sendRedirect("buyGoodsSuccess.html");
    }

}
