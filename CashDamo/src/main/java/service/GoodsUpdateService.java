package service;

import bean.Goods;
import dao.GoodsUpdateDao;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GoodsUpdateService {
    GoodsUpdateDao goodsUpdateDao = new GoodsUpdateDao();

    //功能：找到goodsId的商品
    public Goods getGoods(int goodsId) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        Goods goods = null;

        try {
            String sql = "select * from goods where id = ?";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);
            ps.setInt(1, goodsId);

            rs = ps.executeQuery();

            if (rs.next()) {
                goods = new Goods();
                goods.setId(rs.getInt("id"));
                goods.setName(rs.getString("name"));
                goods.setIntroduce(rs.getString("introduce"));
                goods.setStock(rs.getInt("stock"));
                goods.setUnit(rs.getString("unit"));
                goods.setPrice(rs.getInt("price"));
                goods.setDiscount(rs.getInt("discount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, ps, rs);
        }
        return goods;
    }

    //将新的数据 写回到数据库当中
    public boolean modify(Goods goods) {
        int ret = goodsUpdateDao.UpDate(goods);

        return ret != 0;
    }
}
