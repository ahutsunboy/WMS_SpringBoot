package com.ahut.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ahut.pojo.Menu;
import com.ahut.service.MenuService;
import com.ahut.mapper.MenuMapper;
import org.springframework.stereotype.Service;

/**
* @author 86187
* @description 针对表【menu】的数据库操作Service实现
* @createDate 2023-07-02 17:29:30
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService{

}




