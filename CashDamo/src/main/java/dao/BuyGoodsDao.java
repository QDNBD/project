package dao;

import bean.Goods;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class BuyGoodsDao {
    public int updateAfterPayDao(Goods goods, int buyGoodsNum) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            String sql = "update goods set stock=? where id=?";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);
            ps.setInt(1, goods.getStock() - buyGoodsNum);
            ps.setInt(2, goods.getId());

            return ps.executeUpdate();

        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            DBUtil.close(connection, ps, null);
        }
        return 0;
    }


}
