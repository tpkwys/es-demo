package com.aaron.esdemo.service;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.List;
import java.util.Map;

public interface IEsService {

    //创建索引
    boolean createIndex(String index);
    //检查索引是否存在
    boolean checkIndexExist(String index);
    //删除索引
     boolean deleteIndex(String indexName);

    //根据id删除数据
    boolean delete (String indexName,String id);
    //根据id查询
    <T>List<T> queryByIds(String indexName,List<String> ids,Class<T> c);
    //搜索
    <T>List<T> search(String index, SearchSourceBuilder builder,Class<T> c);
}
