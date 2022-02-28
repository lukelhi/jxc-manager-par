package com.lzj.admin.service;

import com.lzj.admin.pojo.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzj.admin.query.GoodsQuery;

import java.util.Map;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author 李
 * @since 2022-02-19
 */
public interface IGoodsService extends IService<Goods> {
    Map<String, Object> goodsList(GoodsQuery goodsQuery);

    String genGoodsCode();

    void saveGoods(Goods goods);

    void updateGoods(Goods goods);

    void deleteGoods(Integer id);

    void updateStock(Goods goods);

    void deleteStock(Integer id);
}
