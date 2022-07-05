package com.lzj.admin.query;


import lombok.Data;

/**
 * 进销存
 *
 * @author 进销存--lhy
 * @version 1.0
 */
@Data
public class BaseQuery {
    private Integer page=1; //当前页
    private Integer limit=10; //分页大小

}
