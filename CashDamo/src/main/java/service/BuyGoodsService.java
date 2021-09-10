package service;

import bean.Goods;
import bean.Order;
import bean.OrderItem;
import dao.BuyGoodsDao;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BuyGoodsService {

    BuyGoodsDao buyGoodsDao = new BuyGoodsDao();

    //更新库存
    public boolean updateAfterPay(Goods goods, int buyGoodsNum) {
        int ret = buyGoodsDao.updateAfterPayDao(goods, buyGoodsNum);

        if (ret == 0) {
            return false;
        }
        return true;
    }


    //提交订单
    public boolean commitOrder(Order order) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            String insertOrderSql = "insert into `order` (id, account_id,create_time, finish_time," +
                    "actual_amount,total_money,order_status,account_name) " +
                    "values (?,?,now(),now(),?,?,?,?)";

            connection = DBUtil.getConnection(false);
            ps = connection.prepareStatement(insertOrderSql);
            ps.setString(1, order.getId());
            ps.setInt(2, order.getAccount_id());
            ps.setInt(3, order.getActualAmountInt());
            ps.setInt(4, order.getTotalMoneyInt());
            ps.setInt(5, order.getOrder_status().getFlg());
            ps.setString(6, order.getAccount_name());

            int ret = ps.executeUpdate();
            if (ret == 0) {
                throw new RuntimeException("插入订单失败！");
            }

            String insertOrderItemSql = "insert into order_item(order_id, goods_id, goods_name," +
                    "goods_introduce, goods_num, goods_unit," +
                    " goods_price, goods_discount) " +
                    "values (?,?,?,?,?,?,?,?)";
            ps = connection.prepareStatement(insertOrderItemSql);

            for (OrderItem orderItem : order.getOrderItemList()) {
                ps.setString(1, orderItem.getOrderId());
                ps.setInt(2, orderItem.getGoodsId());
                ps.setString(3, orderItem.getGoodsName());
                ps.setString(4, orderItem.getGoodsIntroduce());
                ps.setInt(5, orderItem.getGoodsNum());
                ps.setString(6, orderItem.getGoodsUnit());
                ps.setInt(7, orderItem.getGoodsPriceInt());
                ps.setInt(8, orderItem.getGoodsDiscount());
                ps.addBatch();// 缓存
            }

            int[] effect = ps.executeBatch();//批量的插入
            for (int i : effect) {
                if (i == 0) {
                    throw new RuntimeException("插入订单明细失败！");
                }
            }
            //批量插入没有发生任何的异常
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            //判断链接是否断开
            if (connection != null) {
                try {
                    //回滚
                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            return false;
        } finally {
            DBUtil.close(connection, ps, null);
        }
        return true;
    }

}
