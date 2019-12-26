package com.aaron.esdemo.controller;


import com.aaron.esdemo.service.IEsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/es")
public class ESTestSpi {

    @Autowired
    private IEsService esServiceImpl;

    @GetMapping("/create/{indexName}")
    public Boolean createIndex(@PathVariable("indexName") String indexName){
        return esServiceImpl.createIndex(indexName);
    }
    @GetMapping("/delete/{indexName}")
    public Boolean deleteIndex(@PathVariable("indexName") String indexName){
        return esServiceImpl.deleteIndex(indexName);
    }
    @GetMapping("/exist/{indexName}")
    public Boolean existIndex(@PathVariable("indexName") String indexName){
        return esServiceImpl.checkIndexExist(indexName);
    }

}
