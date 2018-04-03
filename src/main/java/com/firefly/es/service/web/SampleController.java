package com.firefly.es.service.web;

import com.firefly.es.service.dto.EntityObject;
import com.firefly.es.service.handler.EsIndexHandler;
import com.firefly.es.service.handler.EsQueryHandler;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;


@Controller
public class SampleController {

    @Inject
    private EsIndexHandler indexHandler;

    @Inject
    private EsQueryHandler queryHandler;

    private static final Logger log = LoggerFactory.getLogger(SampleController.class);

    @RequestMapping("/test.html")
    @ResponseBody
    String createIndex() {

         String indexName = "zbindex";
         String docType = "zhaobiao";

        EntityObject entityObject = new EntityObject();
        entityObject.setProvince("国家");
        entityObject.setDepartment("科技");
        entityObject.setKeyword("招聘");
        entityObject.setLink("");
        entityObject.setContext("招聘");

        EsIndexHandler indexHandler = new EsIndexHandler();



        //indexHandler.deleIndex(indexName);


        //indexHandler.createIndex(indexName,docType);


        //indexHandler.storeDocument(new EntityObject(),indexName,docType);

        queryHandler.termQuery(entityObject,indexName,docType);

        return "Hello World!";

    }


    @ResponseBody
    @RequestMapping("/store.html")
    String store(EntityObject entityObject){
        String indexName = "zbindex";
        String docType = "zhaobiao";
        EsIndexHandler indexHandler = new EsIndexHandler();
        return indexHandler.storeDocument(entityObject,indexName,docType);



    }

    @ResponseBody
    @RequestMapping("/query.html")
    String queryTerm(EntityObject entityObject){
        String indexName = "zbindex";
        String docType = "zhaobiao";

       return queryHandler.termQuery(entityObject,indexName,docType);

    }



}
