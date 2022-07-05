package com.lzj.admin.query;

import lombok.Data;

import java.util.List;

/**
 * 进销存
 *
 * @author 进销存--lhy
 * @version 1.0
 */
@Data
public class GoodsQuery extends BaseQuery{
    private String goodsName;
    private Integer typeId;

    private List<Integer> typeIds;

    // 查询类型 区分库存量是否大于0查询
    /**
     * 1 库存量=0
     * 2 库存量>0
     */
    private Integer type;

}
