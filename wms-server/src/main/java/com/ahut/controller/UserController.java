package com.ahut.controller;

import com.ahut.pojo.Menu;
import com.ahut.pojo.User;
import com.ahut.service.MenuService;
import com.ahut.service.UserService;
import com.ahut.vo.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : Scott Chen
 * @create 2023/6/27 18:06
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/getAll")
    public Result<?> getAll(){
        List<User> list = userService.list();
        Map<Object, Object> data = new HashMap<>();
        data.put("userList",list);
        return Result.success(data);
    }

    @GetMapping("/pageList")
    public Result<?> pageQuery(@RequestParam(value = "sex",required = false) String sex,
                               @RequestParam(value = "name",required = false) String name,
                               @RequestParam(value = "roleId",required = false) String roleId,
                               @RequestParam Integer pageNum,
                               @RequestParam Integer pageSize){
        //查询条件
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if(roleId.equals("1")){
            wrapper.eq(StringUtils.isNotBlank(sex),User::getSex,sex)
                    .like(StringUtils.isNotBlank(name),User::getName,name)
                    .eq(StringUtils.isNotBlank(roleId),User::getRoleId,roleId)
                    .or().eq(StringUtils.isNotBlank(roleId),User::getRoleId,0);

        }else{
            wrapper.eq(StringUtils.isNotBlank(sex),User::getSex,sex)
                    .like(StringUtils.isNotBlank(name),User::getName,name)
                    .eq(StringUtils.isNotBlank(roleId),User::getRoleId,roleId);
        }

        //分页条件
        Page<User> userPage = new Page<>(pageNum, pageSize);
        Page<User> page = userService.page(userPage, wrapper);

        long total = page.getTotal();
        List<User> userList = page.getRecords();

        HashMap<String, Object> data = new HashMap<>();
        data.put("total",total);
        data.put("userList",userList);

        return Result.success(data);
    }

    @PostMapping("/addUser")
    public Result<?> addUser(@RequestBody User user){
        boolean save = userService.save(user);
        if(!save){
            return Result.fail();
        }
        return Result.success();
    }

    @GetMapping("/findByNo")
    public Result<?> findByNo( String no){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getNo,no);
        List<User> userList = userService.list(wrapper);
        if(userList.isEmpty()){
            return Result.fail();
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put("userList",userList);
        return Result.success(data);
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Integer id){
        User user = userService.getById(id);
        return Result.success(user);
    }

    @PutMapping()
    public Result<?> updateById(@RequestBody User user){
        boolean b = userService.updateById(user);
        if(b){
            return Result.success();
        }
        return Result.fail();
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteById(@PathVariable Integer id){
        boolean b = userService.removeById(id);
        if(b){
            return Result.success();
        }
        return Result.fail();
    }

    @Resource
    private MenuService menuService;

    //登录
    @PostMapping("/login")
    public Result<?> login(@RequestBody User user){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getNo,user.getNo())
                .eq(User::getPassword,user.getPassword());
        List<User> userList = userService.list(wrapper);
        if(userList.isEmpty()){
            return Result.fail("账号或密码错误！");
        }
        Map<String,Object> data = new HashMap<>();
        //密码置空
        userList.get(0).setPassword(null);

        Integer roleId = userList.get(0).getRoleId();
        LambdaQueryWrapper<Menu> wrapper1 = new LambdaQueryWrapper<Menu>();
        wrapper1.like(Menu::getMenuright,roleId);
        List<Menu> menus = menuService.list(wrapper1);

        data.put("userList",userList);
        data.put("menu",menus);
        return Result.success(data);
    }
}
