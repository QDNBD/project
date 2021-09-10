package dao;

import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class GoodsPutAwayDao {
    public int Update(String name, String introduce, String stock, String unit, int realPrice, String discount) {
        Connection connection = null;
        PreparedStatement ps = null;
        try{
            String sql = "insert into goods(name,introduce,stock,unit,price,discount) values (?,?,?,?,?,?)";
            connection = DBUtil.getConnection(true);
            assert connection != null;
            ps = connection.prepareStatement(sql);

            ps.setString(1,name);
            ps.setString(2,introduce);
            ps.setInt(3,Integer.parseInt(stock));
            ps.setString(4,unit);
            ps.setInt(5,realPrice);
            ps.setInt(6,Integer.parseInt(discount));

            return ps.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection, ps, null);
        }

        return 0;
    }
}
