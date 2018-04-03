package com.firefly.es.service.handler;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.firefly.es.service.dto.EntityObject;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.stereotype.Service;

/**
 *@author walter
 */
@Service
public class EsQueryHandler {

    private TransportClient client = EsClientFactory.getClient();

    private String indexName;
    private String docType;


    public EsQueryHandler(String indexName,String docType){
        this.indexName = indexName;
        this.docType = docType;
    }


    /***
     * 全标title匹配
     * @param title
     * @return
     */
    public String queryByfullTitle(String title){

        if(StringUtils.isEmpty(title))return null;

        JSONArray jsonArray = new JSONArray();
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery("full_title",title));

        SearchResponse response = client.prepareSearch(indexName)
                .setTypes(docType).setQuery(queryBuilder).get();

        for (SearchHit searchHit : response.getHits()) {
            jsonArray.add(searchHit.getSourceAsString());
        }
        return JSONObject.toJSONString(jsonArray);


    }

    /**
     * 关键词搜索
     * @param keyword
     * @return
     */
    public String queryByKeyword(String keyword){

        JSONArray jsonArray = new JSONArray();
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery("full_title",keyword));

        SearchResponse response = client.prepareSearch(indexName)
                .setTypes(docType).setFrom(0).setSize(10).setQuery(queryBuilder).get();

        for (SearchHit searchHit : response.getHits()) {
            jsonArray.add(searchHit.getSourceAsString());
        }
        return JSONObject.toJSONString(jsonArray);


    }


    /**
     * 全文搜索
     * @param entityObject
     * @return
     */
    public String matchAll(EntityObject entityObject){
        JSONArray jsonArray = new JSONArray();
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery("province",entityObject.getProvince()))
                .filter(QueryBuilders.termQuery("department",entityObject.getDepartment()))
                .must(QueryBuilders.multiMatchQuery(entityObject.getKeyword(),"title","context"));

        SearchResponse response = client.prepareSearch(indexName)
                .setTypes(docType).setFrom(0).setSize(10).setQuery(queryBuilder).get();

        for (SearchHit searchHit : response.getHits()) {
            jsonArray.add(searchHit.getSourceAsString());
        }
        return JSONObject.toJSONString(jsonArray);
    }

    /**
     * 过虑搜索
     * @param entityObject
     * @param indexName
     * @param docType
     */
    public String queryByFilter(EntityObject entityObject, String indexName, String docType){

        JSONArray jsonArray = new JSONArray();
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery("province",entityObject.getProvince()))
                .filter(QueryBuilders.termQuery("department",entityObject.getDepartment()))
                .filter(QueryBuilders.rangeQuery("date").format("yyyy-MM-dd").gte("2018-01-05"));

        SearchResponse response = client.prepareSearch(indexName)
                .setTypes(docType).setFrom(entityObject.getStart()).setSize(entityObject.getEnd()).setQuery(queryBuilder).get();

        for (SearchHit searchHit : response.getHits()) {
            jsonArray.add(searchHit.getSourceAsString());
        }
        return JSONObject.toJSONString(jsonArray);
    }


    /**
     * 关词词搜索
     * @param entityObject
     * @param indexName
     * @param docType
     */
    public String termQuery(EntityObject entityObject, String indexName, String docType){


        if(StringUtils.isEmpty(entityObject.getKeyword()))return null;
        if(StringUtils.isEmpty(entityObject.getProvince()))return null;

        JSONArray jsonArray = new JSONArray();
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery("province",entityObject.getProvince()))
                .filter(QueryBuilders.termQuery("department",entityObject.getDepartment()))
                .filter(QueryBuilders.rangeQuery("date").format("yyyy-MM-dd").lt(entityObject.getDate()))
                .should(QueryBuilders.multiMatchQuery(entityObject.getKeyword(),"title","context"));

        SearchResponse response = client.prepareSearch(indexName)
                .setTypes(docType).setFrom(entityObject.getStart()).setSize(entityObject.getEnd()).setQuery(queryBuilder).get();

        for (SearchHit searchHit : response.getHits()) {
            jsonArray.add(searchHit.getSourceAsString());
        }
        return JSONObject.toJSONString(jsonArray);
    }
}
