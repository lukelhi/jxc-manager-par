package com.lzj.admin.query;


import lombok.Data;

/**
 * 乐字节  踏实教育 用心服务
 *
 * @author 乐字节--老李
 * @version 1.0
 */
@Data
public class BaseQuery {
    private Integer page=1; //当前页
    private Integer limit=10; //分页大小

}
