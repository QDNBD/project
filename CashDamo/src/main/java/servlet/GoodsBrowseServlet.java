package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import bean.Goods;
import service.GoodsBrowseService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.List;


@WebServlet("/goods")
public class GoodsBrowseServlet extends HttpServlet {

    GoodsBrowseService goodsBrowseService = new GoodsBrowseService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=utf-8");

        try {
            //1、先从数据库当中 找到所有的商品信息
            List<Goods> goodsList = goodsBrowseService.selectGoods();

            //将后端的数据  转换为json字符串
            ObjectMapper objectMapper = new ObjectMapper();

            Writer writer = resp.getWriter();
            //将list转换为json字符串，并将该字符串写到流当中
            objectMapper.writeValue(writer,goodsList);
            //推到前端
            writer.write(writer.toString());

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
