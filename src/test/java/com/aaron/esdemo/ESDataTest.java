package com.aaron.esdemo;

import com.aaron.esdemo.entity.Article;
import com.aaron.esdemo.service.IEsService;
import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
public class ESDataTest {

    @Autowired
    private IEsService esService;
    @Test
    public void testFindById(){
        System.out.println(JSON.toJSONString(esService.queryByIds("blog", Arrays.asList("2","3"), Article.class)));
    }
    @Test
    public void testFindBy(){
        System.out.println(JSON.toJSONString(esService.search("blog",null, Article.class)));
    }

}