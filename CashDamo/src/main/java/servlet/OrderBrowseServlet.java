package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import bean.Account;
import bean.Order;
import service.OrderBrowseService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Writer;
import java.util.List;


@WebServlet("/orderbrowse")
public class OrderBrowseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        OrderBrowseService orderBrowseService = new OrderBrowseService();

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=utf-8");

        Writer writer = resp.getWriter();

        HttpSession session=req.getSession();
        Account account = (Account) session.getAttribute("user");

        List<Order> orders = orderBrowseService.queryOrderByAccount(account.getId());
        System.out.println("订单明细："+orders);

        if(orders == null || orders.isEmpty()) {
            System.out.println("你还没有产生订单！"+account.getId());
            writer.write("<h2> 你还没有产生订单！"+account.getId()+"</h2>");
        }else {
            //将后端的数据  转换为json字符串
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                //将list转换为json字符串，并将该字符串写到流当中
                objectMapper.writeValue(writer,orders);
                System.out.println("json字符串："+writer.toString());
                //推到前端
                writer.write(writer.toString());
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
}
