package com.aaron.esdemo;

import com.aaron.esdemo.service.IEsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ESIndexTest {
    @Autowired
    private IEsService esService;
    @Test
    public void createIndex(){
        System.out.println(esService.createIndex("myindex"));
    }
    @Test
    public void existIndex(){
        System.out.println(esService.checkIndexExist("myindex"));
    }
    @Test
    public void deleteIndex(){
        System.out.println(esService.deleteIndex("myindex"));
    }
}
