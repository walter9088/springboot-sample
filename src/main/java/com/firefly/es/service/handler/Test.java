package com.firefly.es.service.handler;

import com.firefly.es.service.dto.EntityObject;

import java.util.Date;

public class Test {


    public static void main(String[] args) {

        EsQueryHandler esQueryHandler = new EsQueryHandler("zbindex","zhaobiao");
        EsIndexHandler esIndexHandler = new EsIndexHandler();

        //esIndexHandler.deleIndex("zbindex");

        //esIndexHandler.createIndex("zbindex","zhaobiao");

        EntityObject eo = new EntityObject();
        eo.setProvince("国家");
        eo.setDepartment("农业部");
        eo.setTitle("农业部社会发展科技司关于征集种植产品的通知");
        eo.setLink("http://www.most.gov.cn/tztg/201801/t20180102_137309.htm");
        eo.setContext("为进一步落实《关于促进医药产业健康发展的指导意见》（国办发〔2016〕11号），加大对创新医疗器械产品的宣传力度，促进医药产业持续健康发展，现启动创新医疗器械产品遴选工作，面向社会征集创新产品。经评审认定列入《创新医疗器械产品目录》的产品，优先推荐进入国产创新医疗器械产品应用示范工程中应用推广等。现将有关事宜通知如下：\n" +
                "    一、创新医疗器械推荐增补产品的条件\n" +
                "    选择同时符合下述条件的创新医疗器械产品成果，且在国内已取得产品注册证的医疗器械产品：\n" +
                "    （一）国际原创、国内首创，且关键技术和核心部件具有发明专利；或通过技术创新在功能性能、性价比、可靠性、用户体验等方面有重大提升");
        eo.setDate(new Date());
        eo.setKeyword("科技");

        eo.setStart(0);
        eo.setEnd(2);
        //
        //System.out.println(esIndexHandler.storeDocument(eo,"zbindex","zhaobiao"));
        //System.out.println(str);


       // String str = esQueryHandler.queryByfullTitle("科技部社会发展科技司关于征集《创新医疗器械产品目录》产品的通知");
//
//
        String str = esQueryHandler.queryByFilter(eo,"zbindex","zhaobiao");
        System.out.println(str);

    }
}
