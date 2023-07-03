package com.ahut.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author : Scott Chen
 * @create 2023/6/28 10:52
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<E> {

    private Integer code;
    private String msg;

    private E data;



    //成功的四种重载

    public static <E> Result<E> success(){
        return new Result<E>(200,"success",null);
    }

    public static <E> Result<E> success(E data){
        return new Result<E>(200,"success",data);
    }

    public static <E> Result<E> success(String msg,E data){
        return new Result<E>(200,"success",data);
    }

    public static <E> Result<E> success(String msg){
        return new Result<E>(200,"success",null);
    }


    //失败的四种重载
    public static <E> Result<E> fail(){
        return new Result<E>(400,"fail",null);
    }

    public static <E> Result<E> fail(E data){
        return new Result<E>(400,"fail",data);
    }

    public static <E> Result<E> fail(String msg){
        return new Result<E>(400,msg,null);
    }

    public static <E> Result<E> fail(String msg,E data){
        return new Result<E>(400,"fail",data);
    }

}
