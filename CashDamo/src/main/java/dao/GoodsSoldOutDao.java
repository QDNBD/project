package dao;

import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class GoodsSoldOutDao {

    public int delete(int id) {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            String sql = "delete from goods where id=?";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);
            ps.setInt(1,id);

            return ps.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection, ps,null);
        }
        return 0;
    }
}
