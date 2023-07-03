package com.ahut;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class WmsServerApplicationTests {

    @Test
    void contextLoads() {
        List<String> list = new ArrayList<>();
        System.out.println(list.isEmpty());
    }

}
