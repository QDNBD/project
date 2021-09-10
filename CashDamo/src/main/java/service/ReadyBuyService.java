package service;

import bean.Goods;
import dao.ReadyBuyDao;


public class ReadyBuyService {

    ReadyBuyDao readyBuyDao = new ReadyBuyDao();

    public Goods getGoods(int goodsId) {

        return readyBuyDao.seleteGoods(goodsId);
    }
}
