package com.lzj.admin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lzj.admin.pojo.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lzj.admin.query.GoodsQuery;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author 李
 * @since 2022-02-19
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    IPage<Goods> queryGoodsByParams(IPage<Goods> page, GoodsQuery goodsQuery);
}
