package com.lzj.admin.controller;


import com.lzj.admin.exceptions.ParamsException;
import com.lzj.admin.model.RespBean;
import com.lzj.admin.pojo.User;
import com.lzj.admin.query.UserQuery;
import com.lzj.admin.service.IUserService;
import com.sun.xml.internal.ws.resources.HttpserverMessages;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author 李
 */
@Controller
@RequestMapping("/user")
public class UserController {


    @Resource
    private IUserService userService;




    /**
     * 用户信息设置页面
     * @return
     */
    @RequestMapping("setting")
    public String setting(Principal principal, Model model){
        User user = userService.findUserByUserName(principal.getName());
        model.addAttribute("user",user);
        return "user/setting";
    }


    /**
     * 用户信息更新
     * @param user
     * @return
     */

    @RequestMapping("updateUserInfo")
    @ResponseBody
    public RespBean updateUserInfo(User user){
        userService.updateUserInfo(user);
        return RespBean.success("用户信息更新成功");
    }


    /**
     * 用户密码更新页
     * @return
     */
    @RequestMapping("password")
    public String password(){
        return "user/password";
    }


    /**
     * 用户密码更新
     * principal
     * @param oldPassword
     * @param newPassword
     * @param confirmPassword
     * @return
     */
    @RequestMapping("updateUserPassword")
    @ResponseBody
    public RespBean updateUserPassword(Principal principal, String oldPassword, String newPassword, String confirmPassword){
        userService.updateUserPassword(principal.getName(),oldPassword,newPassword,confirmPassword);
        return RespBean.success("用户密码更新成功");
    }

    /**
     * 用户管理主页
     * @return
     */
    @RequestMapping("index")
    @PreAuthorize("hasAnyAuthority('1010')")  //实现后端接资源访问控制
    //springSecurity支持权限码，登录的时候系统会调用SecurityConfig中的loadUserByUsername方法
    public String index(){
        return "user/user";
    }

    /**
     * 用户列表查询接口
     * @param userQuery
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('101003')")
    public Map<String,Object> userList(UserQuery userQuery){
        return userService.userList(userQuery);
    }


    /**
     * 添加|更新用户页
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("addOrUpdateUserPage")
    public String addOrUpdatePage(Integer id,Model model){
        if(null != id){ //更新
            model.addAttribute("user",userService.getById(id));
        }
        return "user/add_update";
    }


    /**
     * 用户记录添加接口
     * @param user
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public RespBean saveUser(User user){
        userService.saveUser(user);
        return RespBean.success("用户记录添加成功!");
    }


    /**
     * 用户记录更新接口
     * @param user
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public RespBean updateUser(User user){
        userService.updateUser(user);
        return RespBean.success("用户记录更新成功!");
    }


    /**
     * 用户记录删除接口
     * @param ids
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public RespBean deleteUser(Integer[] ids){
        userService.deleteUser(ids);
        return RespBean.success("用户记录删除成功!");
    }
}
