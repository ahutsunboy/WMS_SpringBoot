package com.ahut.controller;

import com.ahut.pojo.Menu;
import com.ahut.service.MenuService;
import com.ahut.vo.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author : Scott Chen
 * @create 2023/7/2 17:29
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    @GetMapping("/list")
    public Result<?> getMenuList(@RequestParam String roleId){
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<Menu>();
        wrapper.like(Menu::getMenuright,roleId);
        List<Menu> menus = menuService.list(wrapper);

        return Result.success(menus);
    }
}
