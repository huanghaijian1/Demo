package com.example.demo.controller;
//import cn.hutool.core.date.DateUtil;
//import cn.hutool.core.lang.Console;
//import cn.hutool.json.JSONObject;
//import com.example.demo.domain.response.esIndex.GoodsIndex;
//import org.elasticsearch.action.bulk.BulkRequest;
//import org.elasticsearch.action.bulk.BulkResponse;
//import org.elasticsearch.action.delete.DeleteRequest;
//import org.elasticsearch.action.delete.DeleteResponse;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.action.support.master.AcknowledgedResponse;
//import org.elasticsearch.action.update.UpdateRequest;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.client.indices.CreateIndexRequest;
//import org.elasticsearch.client.indices.CreateIndexResponse;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.unit.TimeValue;
//import org.elasticsearch.common.xcontent.XContentType;
//import org.elasticsearch.index.query.*;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.SearchHits;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.elasticsearch.search.sort.FieldSortBuilder;
//import org.elasticsearch.search.sort.SortOrder;
//import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
//import org.springframework.web.bind.annotation.*;
//import java.io.IOException;
//import java.util.*;
//import java.util.concurrent.TimeUnit;
//import cn.hutool.core.collection.CollUtil;
//import org.elasticsearch.action.admin.indices.alias.Alias;
//import org.elasticsearch.client.indices.*;
//import org.elasticsearch.common.xcontent.XContentBuilder;
//import org.elasticsearch.common.xcontent.XContentFactory;


import com.example.demo.dao.EsBlogRepository;
import com.example.demo.domain.Person;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/elasticsearch")
public class EsDemoController {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

//    @Autowired
//    private EsBlogRepository esBlogRepository;

    @GetMapping("/find")
    public void tesFindDoc(){
//        elasticsearchRestTemplate.queryForObject()
        Person documentId = elasticsearchRestTemplate.queryForObject(GetQuery.getById("1005"),Person.class);
        System.out.println(documentId);


        //1.创建QueryBuilder(即设置查询条件)这儿创建的是组合查询(也叫多条件查询),后面会介绍更多的查询方法
        /*组合查询BoolQueryBuilder
         * must(QueryBuilders)   :AND
         * mustNot(QueryBuilders):NOT
         * should:               :OR
         */
//        BoolQueryBuilder builder = QueryBuilders.boolQuery();
//        //builder下有must、should以及mustNot 相当于sql中的and、or以及not
//
//        //设置模糊搜索,博客的简诉中有学习两个字
//        builder.must(QueryBuilders.fuzzyQuery("name", "张三"));
//
//        //设置要查询博客的标题中含有关键字
//        builder.must(new QueryStringQueryBuilder("27").field("age"));
//
//        //按照博客的评论数的排序是依次降低
//        FieldSortBuilder sort = SortBuilders.fieldSort("id").order(SortOrder.DESC);
//
//        //设置分页(从第一页开始，一页显示10条)
//        //注意开始是从0开始，有点类似sql中的方法limit 的查询
//        PageRequest page2 = PageRequest.of(0, 10);

        //2.构建查询
//        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
//
//        //将搜索条件设置到构建中
//        nativeSearchQueryBuilder.withQuery(builder);
//        //将分页设置到构建中
//        nativeSearchQueryBuilder.withPageable(page2);
//        //将排序设置到构建中
//        nativeSearchQueryBuilder.withSort(sort);
//        //生产NativeSearchQuery
//        NativeSearchQuery query = nativeSearchQueryBuilder.build();
        //3.执行方法1
//        Page<Person> page = elasticsearchRestTemplate.search(query);
//        //4.获取总条数(用于前端分页)
//        int total = (int) page.getTotalElements();
//
//        //5.获取查询到的数据内容（返回给前端）
//        List<Person> content = page.getContent();

        //执行方法2：注意，这儿执行的时候还有个方法那就是使用elasticsearchTemplate
        //执行方法2的时候需要加上注解
        //@Autowired
        //private ElasticsearchTemplate elasticsearchTemplate;
        PageRequest page = PageRequest.of(0, 10000);
//        FieldSortBuilder sort = SortBuilders.fieldSort("id").order(SortOrder.DESC);
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
               .must(QueryBuilders.prefixQuery("name.keyword", "11463"));
//                .mustNot(QueryBuilders.termQuery("name", "张三"));
//                .should(QueryBuilders.termQuery("age", "32"));
//                .should(QueryBuilders.termQuery("email", "qq.com"));
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
               .withQuery(queryBuilder)
//               .withQuery(QueryBuilders.matchQuery("hobby","qq"))
                .withTypes("_doc")
                .withPageable(page)
                .build();
        long time = System.currentTimeMillis();
        System.out.println(time);
        List<Person> result = elasticsearchRestTemplate.queryForList(searchQuery, Person.class);
        System.out.println(result);
        System.out.println(result.size());
        long time2 = System.currentTimeMillis();
        System.out.println(time2-time);


    }

    @GetMapping("/create")
    public void testCreateDoc(){
        List<IndexQuery> list = new ArrayList<>(10000);
        for(int i=100;i<20000000;i++){
            Person person = new Person(i+"", i+"-"+UUID.randomUUID().toString(),23+(int)(1+Math.random()*10),i+"@qq.com","提"+i);
            IndexQuery indexQuery = new IndexQueryBuilder()
                    .withId(person.getId())
                    .withObject(person)
                    .build();
            list.add(indexQuery);
            if(list.size()==10000){
                elasticsearchRestTemplate.bulkIndex(list);
                list = new ArrayList<>();
            }
//            elasticsearchRestTemplate.index(indexQuery);
//            String documentId = elasticsearchRestTemplate.index(indexQuery);
//            System.out.println(documentId);
        }
//        elasticsearchRestTemplate.index

    }


    @GetMapping("/update")
    public void testUpdateDoc() throws Exception{
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("name", "张三");
        UpdateRequest updateRequest = new UpdateRequest().doc(jsonMap);
        UpdateQuery query = new UpdateQueryBuilder()
                .withClass(Person.class)
                .withId("1005")
                .withUpdateRequest(updateRequest)
                .build();
        elasticsearchRestTemplate.update(query);

    }

    @GetMapping("/delete")
    public void testDeleteDoc() throws Exception{
        String documentId = elasticsearchRestTemplate.delete(Person.class,"1005");
        System.out.println(documentId);
    }


//
//    private final RestHighLevelClient highLevelClient;
//
//    private final ElasticsearchOperations elasticsearchOperations;
//
//
//
//    public EsDemoController(ElasticsearchOperations elasticsearchOperations,RestHighLevelClient highLevelClient) {
//        this.elasticsearchOperations = elasticsearchOperations;
//        this.highLevelClient = highLevelClient;
//    }
//
//    @GetMapping("/findAll")
//    public void findAll(){
//
//    }
//
//
//    @GetMapping("/template")
//    public void putTemplate()throws IOException{
//        PutIndexTemplateRequest request = new PutIndexTemplateRequest("goods_template");
//        //别名，所有根据该模版创建的index 都会添加这个别名。查询时可查询别名，就可以同时查询多个名称不同的index，根据此方法可实现index每天或每月生成等逻辑。
//        request.alias(new Alias("goods_index"));
//        request.order(10);
//        //匹配哪些index。在创建index时会生效。
//        request.patterns(CollUtil.newArrayList("goods_index*"));
//        request.settings(Settings.builder()
//                //数据插入后多久能查到，实时性要求高可以调低
//                .put("index.refresh_interval", "1s")
//                //传输日志，对数据安全性要求高的 设置 request，默认值:request
//                .put("index.translog.durability", "async")
//                .put("index.translog.sync_interval", "120s")
//                //分片数量
//                .put("index.number_of_shards", "5")
//                //副本数量
//                .put("index.number_of_replicas", "0")
//                //单次最大查询数据的数量。默认10000。不要设置太高，如果有导出需求可以根据查询条件分批次查询。
//                .put("index.max_result_window", "100000"));
//        //使用官方提供的工具构建json。可以直接拼接一个json字符串，也可以使用map嵌套。
//        XContentBuilder jsonMapping = XContentFactory.jsonBuilder();
//        //所有数据类型 看官方文档:https://www.elastic.co/guide/en/elasticsearch/reference/7.4/mapping-types.html#_core_datatypes
//        jsonMapping.startObject().startObject("properties")
//                .startObject("goodsName").field("type", "text").field("analyzer", "ik_max_word").endObject()
//                .startObject("goodsId").field("type", "integer").endObject()
//                //keyword类型不会分词存储
//                .startObject("connet").field("type", "text").endObject()
//                //指定分词器
//                .endObject().endObject();
//        request.mapping(jsonMapping);
//        //设置为true只强制创建，而不是更新索引模板。如果它已经存在，它将失败
//        request.create(false);
//        AcknowledgedResponse response = highLevelClient.indices().putTemplate(request, RequestOptions.DEFAULT);
//        if (response.isAcknowledged()) {
//            Console.log("创建模版成功!");
//        } else {
//            Console.log("创建模版失败!");
//        }
//    }
//
//
//    @GetMapping("/delete")
//    public void deleteIndex(Integer id) throws IOException {
////        DeleteIndexRequest request = new DeleteIndexRequest("goods_index*");
//        DeleteRequest  request = new DeleteRequest("goods_index_202106",id.toString());
//        DeleteResponse delete = highLevelClient.delete(request,RequestOptions.DEFAULT);
////        AcknowledgedResponse response = highLevelClient.indices().delete(request, RequestOptions.DEFAULT);
//    }
//
//
//
//    @GetMapping("/create")
//    public void createIndex() throws IOException {
//        CreateIndexRequest request = new CreateIndexRequest("goods_index_tmp");
//        CreateIndexResponse createIndexResponse = highLevelClient.indices().create(request, RequestOptions.DEFAULT);
//        if (createIndexResponse.isAcknowledged()) {
//            Console.log("创建index成功!");
//        } else {
//            Console.log("创建index失败!");
//        }
//    }
//
//    @GetMapping("/insert")
//    public void insertIndex() throws IOException {
//        BulkRequest request = new BulkRequest("goods_index_" + DateUtil.format(new Date(), "yyyyMM"));
//        for (int i = 6; i < 10; i++) {
//            GoodsIndex testData = new GoodsIndex();
//            testData.setGoodsId(i);
//            testData.setConnet(i+"号商品简介----------------");
//            testData.setGoodsName("商品---------------"+i);
//            IndexRequest indexRequest = new IndexRequest("goods_index_" + DateUtil.format(new Date(), "yyyyMM"));
//            indexRequest.id(testData.getGoodsId().toString());
//            indexRequest.source(new JSONObject(testData).toString()
//                    , XContentType.JSON);
//            request.add(indexRequest);
//        }
//        BulkResponse response = highLevelClient.bulk(request, RequestOptions.DEFAULT);
//        Console.log("插入状态:{} 数量:{} ",response.status(),response.getItems().length);
//    }
//
//    @GetMapping("/update")
//    public void update(Integer id) throws IOException {
//        UpdateRequest updateRequest = new UpdateRequest("goods_index_" + DateUtil.format(new Date(), "yyyyMM"), id.toString());
//        GoodsIndex goodsIndex=new GoodsIndex();
//        goodsIndex.setGoodsId(id);
//        goodsIndex.setGoodsName("未知商品");
//        goodsIndex.setConnet("未知内容");
//        updateRequest.doc(new JSONObject(goodsIndex).toString()
//                , XContentType.JSON);
//        highLevelClient.update(updateRequest, RequestOptions.DEFAULT);
//    }
//
//    @GetMapping("/query")
//    public void query(String name,Integer age) throws IOException {
//        SearchRequest searchRequest = new SearchRequest("people");
//        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//
//        QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("name", name)
////                .fuzziness(Fuzziness.AUTO)
////                .analyzer("ik_max_word")
////                .operator(Operator.AND)
//                .prefixLength(3)
//                .maxExpansions(10);
//
//        boolQueryBuilder.must(QueryBuilders.termQuery("age", age));
//        boolQueryBuilder.must(matchQueryBuilder);
//
//        searchSourceBuilder.query(boolQueryBuilder);
//        searchSourceBuilder.from(0);
//        searchSourceBuilder.size(5);
//        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
//        searchSourceBuilder.sort(new FieldSortBuilder("age").order(SortOrder.ASC));
//
//        searchRequest.source(searchSourceBuilder);
//
//        SearchResponse searchResponse = highLevelClient.search(searchRequest, RequestOptions.DEFAULT);
//
//        SearchHits hits = searchResponse.getHits();
//        SearchHit[] searchHits = hits.getHits();
//        for (SearchHit hit : searchHits) {
//            Map<String, Object> map = hit.getSourceAsMap();
//            System.out.println(hit);
//
//        }
//    }
}