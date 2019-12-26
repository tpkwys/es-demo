package com.aaron.esdemo.service.impl;

import com.aaron.esdemo.service.IEsService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 声明:ES6时，官方提到S7会删除type，并且ES6时已经规定每一个index只能有一个type。
 * 在ES7中使用默认的_doc作为type,官方说在8.x版本会彻底移除type.所以在代码里面就不要再出现typeName的字样了哦
 * 当尝试创建两个或以上个类型时，会报错O: Rejecting mapping update to [blog] as the final mapping would have more than 1 type
 * 说白了，es7或以上版本一个index只允许有一个type 噢噢噢噢(单库单表)
 */

@Service
public class EsServiceImpl implements IEsService {
    private static final Logger logger = LoggerFactory.getLogger(EsServiceImpl.class);
    @Autowired
    private RestHighLevelClient highLevelClient;

    @Override
    public boolean createIndex(String indexName) {
        //index名称必须小写，否则会报错
        try {
            CreateIndexRequest request = new CreateIndexRequest(indexName);
            Map<String,Object> properties = new HashMap<>();
            Map<String,Object> propertie = new HashMap<>();
            propertie.put("type","text");
            propertie.put("index",true);
            propertie.put("analyzer","ik_max_word");
            properties.put("field_name",propertie);

            XContentBuilder builder = JsonXContent.contentBuilder();
            builder.startObject()
                    .startObject("mappings")
                    .field("properties",properties)
                    .endObject()
                    .startObject("settings")
                    .field("number_of_shards",3)
                    .field("number_of_replicas",1)
                    .endObject()
                    .endObject();
            request.source(builder);
            highLevelClient.indices().create(request,RequestOptions.DEFAULT);
            return true;
        } catch (IOException e) {
            logger.error(String.format("【创建索引失败】", indexName), e.fillInStackTrace());
        }
        return false;
    }


    @Override
    public boolean checkIndexExist(String index) {
        try{
            GetIndexRequest request=new GetIndexRequest(index);
            Boolean exists= highLevelClient.indices().exists(request,RequestOptions.DEFAULT);
            return  exists;
        }catch (Exception e){
            logger.error(String.format("【未知异常%s】",e));
        }
        return false;
    }

    @Override
    public boolean deleteIndex(String indexName) {
        try{
            DeleteIndexRequest request=new DeleteIndexRequest(indexName);
            highLevelClient.indices().delete(request,RequestOptions.DEFAULT);
            return true;
        }catch (Exception e){
            logger.error(String.format("【未知异常%s】",e));
        }
        return false;
    }

    @Override
    public boolean delete(String indexName, String id) {
        try{
            DeleteRequest deleteRequest=new DeleteRequest(indexName,id);
            DeleteResponse respone= highLevelClient.delete(deleteRequest,RequestOptions.DEFAULT);
            logger.info(JSON.toJSONString(respone));
            DocWriteResponse.Result result= respone.getResult();
            if(result== DocWriteResponse.Result.NOOP||result== DocWriteResponse.Result.NOT_FOUND){
                return false;
            }
            return  true;
        }catch (Exception e){
            logger.error(String.format("【删除数据失败%s】",e.getMessage()));
        }
        return false;
    }

    @Override
    public <T> List<T> queryByIds(String indexName, List<String> ids, Class<T> c) {
        List<T> result=new ArrayList<>();
        try{
            SearchRequest searchRequest=new SearchRequest(indexName); //并指定index
            searchRequest.source().query(QueryBuilders.idsQuery().addIds(ids.toArray(new String[ids.size()])));
            SearchResponse response=highLevelClient.search(searchRequest,RequestOptions.DEFAULT);
            fetchResult(response,result,c);
            return  result;
        }catch (Exception e){
            logger.error(String.format("【查询数据异常,%s】",e.getMessage()));
        }
        return result;
    }


    @Override
    public <T> List<T> search(String index, SearchSourceBuilder builder, Class<T> c) {
        List<T> result=new ArrayList<>();
        SearchRequest searchRequest=new SearchRequest(index);
        searchRequest.source(builder);
        try{
            SearchResponse response=highLevelClient.search(searchRequest,RequestOptions.DEFAULT);
            fetchResult(response,result,c);
        }catch (Exception e){
            logger.error(String.format("【搜索失败,%s】",e.getMessage()));
        }
        return result;
    }
    private <T> void fetchResult(SearchResponse response,List<T> result,Class<T> c){
        SearchHit[]hits=response.getHits().getHits();
        for(SearchHit searchHit:hits){
            result.add(JSON.parseObject(searchHit.getSourceAsString(),c));
        }
    }
}
