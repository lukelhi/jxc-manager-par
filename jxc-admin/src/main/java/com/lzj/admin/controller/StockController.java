package com.lzj.admin.controller;

import com.lzj.admin.model.RespBean;
import com.lzj.admin.pojo.Goods;
import com.lzj.admin.query.GoodsQuery;
import com.lzj.admin.service.IGoodsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 期初库存
 * 乐字节  踏实教育 用心服务
 *
 * @author 乐字节--老李
 * @version 1.0
 */
@Controller
@RequestMapping("stock")
public class StockController {

    @Resource
    private IGoodsService goodsService;

    /**
     * 期初库存主页
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "stock/stock";
    }


    /**
     * 库存量为0 的商品记录
     * @param goodsQuery
     * @return
     */
    @RequestMapping("listNoInventoryQuantity")
    @ResponseBody
    public Map<String,Object> listNoInventoryQuantity(GoodsQuery goodsQuery){
        goodsQuery.setType(1);
        return goodsService.goodsList(goodsQuery);
    }

    /**
     * 库存量为0 的商品记录
     * @param goodsQuery
     * @return
     */
    @RequestMapping("listHasInventoryQuantity")
    @ResponseBody
    public Map<String,Object> listHasInventoryQuantity(GoodsQuery goodsQuery){
        goodsQuery.setType(2);
        return goodsService.goodsList(goodsQuery);
    }


    @RequestMapping("toUpdateGoodsInfoPage")
    public String toUpdateGoodsInfoPage(Integer gid, Model model){
        model.addAttribute("goods",goodsService.getById(gid));
        return "stock/goods_update";
    }

    @RequestMapping("updateStock")
    @ResponseBody
    public RespBean updateGoods(Goods goods){
        goodsService.updateStock(goods);
        return RespBean.success("商品记录更新成功!");
    }


    @RequestMapping("deleteStock")
    @ResponseBody
    public RespBean deleteStock(Integer id){
        goodsService.deleteStock(id);
        return RespBean.success("商品记录删除成功!");
    }

}
