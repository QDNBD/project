package service;

import bean.Goods;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShopCartService {

    Connection connection = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<Goods> selectGoods() {
        List<Goods> goodsList = new ArrayList<>();

        try {
            String sql = "select id,name,introduce,stock,unit,price,discount from `shop`";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Goods goods = new Goods();
                goods.setId(rs.getInt("id"));
                goods.setName(rs.getString("name"));
                goods.setIntroduce(rs.getString("introduce"));
                goods.setStock(rs.getInt("stock"));
                goods.setUnit(rs.getString("unit"));
                goods.setPrice(rs.getInt("price"));
                goods.setDiscount(rs.getInt("discount"));
                goodsList.add(goods);
            }
            System.out.println("商品列表：");
            System.out.println(goodsList);

            return goodsList;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBUtil.close(connection, ps, rs);
        }
        return goodsList;
    }
}
