package com.ahut.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 账号
     */
    private String no;

    /**
     * 名字
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 
     */
    private Integer age;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 
     */
    private String phone;

    /**
     * 角色 0超级管理员 1管理员 2普通账号
     */
    private Integer roleId;

    /**
     * 是否有效，Y有效，其他无效
     */
    private String isValid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}