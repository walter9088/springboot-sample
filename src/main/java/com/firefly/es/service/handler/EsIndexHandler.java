package com.firefly.es.service.handler;

import com.firefly.es.service.dto.EntityObject;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 *@author walter
 *@Date 2017-12-23
 */
@Service
public class EsIndexHandler {

    private TransportClient client = EsClientFactory.getClient();


    /**
     * 创建索引
     * @param indexName
     * @param type
     */
    public void createIndex(String indexName,String type){

        CreateIndexRequestBuilder cib=client.admin().indices().prepareCreate(indexName);

        XContentBuilder mapping = null;
        try {
            mapping = XContentFactory.jsonBuilder()
                    .startObject()
                        .startObject("properties")
                                .startObject("province")
                                    .field("type","keyword")
                                .endObject()
                                .startObject("department")
                                    .field("type","keyword") //设置数据类型
                                .endObject()
                                .startObject("title")
                                    .field("type","text")
                                    .field("analyzer","ik_smart")
                                    .field("search_analyzer","ik_smart")
                                    .field("copy_to","full_title")
                                .endObject()
                                .startObject("full_title")
                                    .field("type","keyword")
                                .endObject()
                                .startObject("link")
                                    .field("type","text")
                                .endObject()
                                .startObject("context")
                                    .field("type","text")
                                    .field("analyzer","ik_smart")
                                    .field("search_analyzer","ik_smart")
                                .endObject()
                                .startObject("date")
                                    .field("type","date")  //设置Date类型
                                    .field("format","yyyy-MM-dd") //设置Date的格式
                                .endObject()
                            .endObject()
                    .endObject();

        } catch (IOException e) {
            e.printStackTrace();
        }

       cib.addMapping(type, mapping);

        CreateIndexResponse res=cib.execute().actionGet();


        System.out.println("----------添加映射成功----------");
    }


    /**
     * 删除索引
     * @param indexName
     */
    public void deleIndex(String indexName){

        //可以根据DeleteIndexResponse对象的isAcknowledged()方法判断删除是否成功,返回值为boolean类型.
        DeleteIndexResponse dResponse = client.admin().indices().prepareDelete(indexName)
                .execute().actionGet();
        System.out.println("是否删除成功:"+dResponse.isAcknowledged());

        //如果传人的indexName不存在会出现异常.可以先判断索引是否存在：
        IndicesExistsRequest inExistsRequest = new IndicesExistsRequest(indexName);

        IndicesExistsResponse inExistsResponse = client.admin().indices()
                .exists(inExistsRequest).actionGet();

        //根据IndicesExistsResponse对象的isExists()方法的boolean返回值可以判断索引库是否存在.
        System.out.println("是否删除成功:"+inExistsResponse.isExists());

    }


    /**
     *
     * @param obj
     * @param indexName
     * @param type
     */
    public String storeDocument(EntityObject obj,String indexName,String type){
        IndexResponse response = null;
        try {
            response = client.prepareIndex(indexName, type)
                    .setSource(XContentFactory.jsonBuilder().startObject()
                            .field("province",obj.getProvince())
                            .field("department",obj.getDepartment())
                            .field("title",obj.getTitle())
                            .field("link",obj.getLink())
                            .field("context",obj.getContext())
                            .field("date",new SimpleDateFormat("yyyy-MM-dd").format(obj.getDate()))
                            .endObject())
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "添加索引成功,版本号："+response.getVersion();
    }

}
