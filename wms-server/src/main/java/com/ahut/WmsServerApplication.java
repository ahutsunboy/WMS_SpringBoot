package com.ahut;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ahut.mapper") //必须要扫描mapper包，否则在使用到mapper包中文件的时候会报错
public class WmsServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WmsServerApplication.class, args);
    }

}
