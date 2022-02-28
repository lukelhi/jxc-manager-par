package com.lzj.admin.service;

import com.lzj.admin.pojo.Customer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzj.admin.query.CustomerQuery;

import java.util.Map;

/**
 * <p>
 * 客户表 服务类
 * </p>
 *
 * @author 李
 * @since 2022-02-16
 */
public interface ICustomerService extends IService<Customer> {

    Map<String, Object> customerList(CustomerQuery customerQuery);

    void saveCustomer(Customer customer);

    void updateCustomer(Customer customer);

    void deleteCustomer(Integer[] ids);

    Customer findCustomerByName(String name);
}
