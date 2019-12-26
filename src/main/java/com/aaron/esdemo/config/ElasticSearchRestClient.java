package com.aaron.esdemo.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Objects;

@Configuration
public class ElasticSearchRestClient {
    private final Logger logger= LoggerFactory.getLogger(ElasticSearchRestClient.class);
    private static final String HTTP_SCHEME="http";
    private static final int ADDRESS_LENGTH=2;

    @Value("${es.ip}")
    String[]ipAddress;

    @Bean
    public RestClientBuilder restClientBuilder(){
        HttpHost[]hosts= Arrays.stream(ipAddress)
                .map(this::makeHttpHost)
                .filter(Objects::nonNull)
                .toArray(HttpHost[]::new);
        logger.debug("hosts{}",Arrays.toString(hosts));
        return RestClient.builder(hosts);
    }
    @Bean(name = "highLevelClient")
    public RestHighLevelClient highLevelClient(@Autowired RestClientBuilder restClientBuilder){
       return new RestHighLevelClient(restClientBuilder);
    }

    private HttpHost makeHttpHost(String s){
        assert StringUtils.isNotBlank(s);
        String[]address=s.split(":");
        if(address.length==ADDRESS_LENGTH){
            String ip=address[0];
            int port=Integer.parseInt(address[1]);
            return new HttpHost(ip,port,HTTP_SCHEME);
        }else{
            return  null;
        }
    }
}
