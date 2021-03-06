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


import com.example.demo.config.HighlightResultMapper;
import com.example.demo.dao.EsBlogRepository;
import com.example.demo.domain.Person;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.search.MatchQuery;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.DefaultResultMapper;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.*;

@RestController
@RequestMapping("/elasticsearch")
public class EsDemoController {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

//    @Autowired
//    private EsBlogRepository esBlogRepository;

    @GetMapping("/find")
    public Object tesFindDoc(String query){
//        elasticsearchRestTemplate.queryForObject()
        Person documentId = elasticsearchRestTemplate.queryForObject(GetQuery.getById("1005"),Person.class);
        System.out.println(documentId);


        //1.??????QueryBuilder(?????????????????????)??????????????????????????????(?????????????????????),????????????????????????????????????
        /*????????????BoolQueryBuilder
         * must(QueryBuilders)   :AND
         * mustNot(QueryBuilders):NOT
         * should:               :OR
         */
//        BoolQueryBuilder builder = QueryBuilders.boolQuery();
//        //builder??????must???should??????mustNot ?????????sql??????and???or??????not
//
//        //??????????????????,????????????????????????????????????
//        builder.must(QueryBuilders.fuzzyQuery("name", "??????"));
//
//        //????????????????????????????????????????????????
//        builder.must(new QueryStringQueryBuilder("27").field("age"));
//
//        //????????????????????????????????????????????????
//        FieldSortBuilder sort = SortBuilders.fieldSort("id").order(SortOrder.DESC);
//
//        //????????????(?????????????????????????????????10???)
//        //??????????????????0?????????????????????sql????????????limit ?????????
//        PageRequest page2 = PageRequest.of(0, 10);

        //2.????????????
//        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
//
//        //?????????????????????????????????
//        nativeSearchQueryBuilder.withQuery(builder);
//        //???????????????????????????
//        nativeSearchQueryBuilder.withPageable(page2);
//        //???????????????????????????
//        nativeSearchQueryBuilder.withSort(sort);
//        //??????NativeSearchQuery
//        NativeSearchQuery query = nativeSearchQueryBuilder.build();
        //3.????????????1
//        Page<Person> page = elasticsearchRestTemplate.search(query);
//        //4.???????????????(??????????????????)
//        int total = (int) page.getTotalElements();
//
//        //5.???????????????????????????????????????????????????
//        List<Person> content = page.getContent();

        //????????????2???????????????????????????????????????????????????????????????elasticsearchTemplate
        //????????????2???????????????????????????
        //@Autowired
        //private ElasticsearchTemplate elasticsearchTemplate;
        PageRequest page = PageRequest.of(0, 20);
//        FieldSortBuilder sort = SortBuilders.fieldSort("id").order(SortOrder.DESC);
        QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(matchQuery("name",query));
//                .must(matchQuery("name",query));
//                .must(prefixQuery("name.keyword", "11463"));
//                .must(rangeQuery("date").gte(time.getTime()));
//                .must(fuzzyQuery("name","value"));
//                .must(queryStringQuery("date"));
//                .mustNot(termQuery("name", "??????"));
//                .should(termQuery("age", "32"));
//                .should(termQuery("email", "qq.com"));
        HighlightBuilder.Field highlightField = new HighlightBuilder.Field("name")
                .preTags("<span style='color: red'>")
                .postTags("</span>");

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
               .withQuery(queryBuilder)
                .withHighlightFields(highlightField)
//               .withQuery(QueryBuilders.matchQuery("hobby","qq"))
                .withTypes("_doc")
                .withPageable(page)
                .build();
        long time = System.currentTimeMillis();
//        List<Person> result = elasticsearchRestTemplate.queryForList(searchQuery, Person.class);
        AggregatedPage<Person> lawRegulationResultEs = elasticsearchRestTemplate.queryForPage(searchQuery, Person.class, new HighlightResultMapper());
        List<Person> resultEsList = lawRegulationResultEs.getContent();

        System.out.println(resultEsList);
        System.out.println(resultEsList.size());
        long time2 = System.currentTimeMillis();
        System.out.println(time2-time);

        return resultEsList;

    }

    // ?????????????????????????????? ???es??????
    /**
     * //                .must(matchQuery("name",query));
     * //                .must(prefixQuery("name.keyword", "11463"));
     * //                .must(rangeQuery("date").gte(time.getTime()));
     * //                .must(fuzzyQuery("name","value"));
     * //                .must(queryStringQuery("date"));
     * //                .mustNot(termQuery("name", "??????"));
     * //                .should(termQuery("age", "32"));
     * //                .should(termQuery("email", "qq.com"));
     * @param query
     * @param relationSiteNames
     * @return
     */
    @GetMapping("/find2")
    public Object resultSearch(String query, String relationSiteNames) {
        // ???a or b??? and c ????????????
        BoolQueryBuilder filterBuilder = QueryBuilders.boolQuery();

        BoolQueryBuilder filterCaseBuilder = QueryBuilders.boolQuery();
        filterCaseBuilder.should(QueryBuilders.matchQuery("name", query));
//        filterCaseBuilder.should(QueryBuilders.termQuery("hobby", query));
//        filterCaseBuilder.should(QueryBuilders.matchQuery("age", Integer.parseInt(query)));

        BoolQueryBuilder filterPhoneBuilder = QueryBuilders.boolQuery();
        if (StringUtils.isNotBlank(relationSiteNames)) {
            // ????????????
            MatchPhraseQueryBuilder relationSiteNames1 = QueryBuilders.matchPhraseQuery("hobby", relationSiteNames);
            filterPhoneBuilder.must(relationSiteNames1);
        }
        // ???a or b??? and c ????????????
        filterBuilder.must(filterCaseBuilder).must(filterPhoneBuilder);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(filterBuilder);

        // ????????????
        HighlightBuilder.Field message = new HighlightBuilder.Field("name").preTags("<span style=\"color:red\">").postTags("</span>");
        HighlightBuilder.Field message2 = new HighlightBuilder.Field("email").preTags("<span style=\"color:red\">").postTags("</span>");
        // ????????????
        Pageable of = PageRequest.of(0, 10);
        // ??????????????????
        NativeSearchQuery query2 = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withPageable(of)
                .withHighlightFields(message,message2)
                .build();
        // ????????????
        AggregatedPage<Person> lawRegulationResultEs = elasticsearchRestTemplate.queryForPage(query2, Person.class, new HighlightResultMapper());
        List<Person> resultEsList = lawRegulationResultEs.getContent();
        return resultEsList;
    }

    @GetMapping("/create")
    public void testCreateDoc(){
        List<IndexQuery> list = new ArrayList<>(10000);
        for(int i=100;i<10200;i++){
            Person person = new Person(i+"", i+"-"+UUID.randomUUID().toString(),23+(int)(1+Math.random()*10),i+"@qq.com","???"+i);
            IndexQuery indexQuery = new IndexQueryBuilder()
                    .withId(person.getId())
                    .withObject(person)
                    .build();
            list.add(indexQuery);
            if(list.size()==10000||i==10200){
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
        jsonMap.put("name", "??????");
        jsonMap.put("age",17);
        jsonMap.put("hobby","?????????");
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
        QueryBuilder builder = QueryBuilders.boolQuery().must(prefixQuery("name.keyword", "11463"));
        DeleteQuery deleteQuery = new DeleteQuery();
        deleteQuery.setQuery(builder);
        elasticsearchRestTemplate.delete(deleteQuery);
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
//        //???????????????????????????????????????index ????????????????????????????????????????????????????????????????????????????????????????????????index???????????????????????????index?????????????????????????????????
//        request.alias(new Alias("goods_index"));
//        request.order(10);
//        //????????????index????????????index???????????????
//        request.patterns(CollUtil.newArrayList("goods_index*"));
//        request.settings(Settings.builder()
//                //???????????????????????????????????????????????????????????????
//                .put("index.refresh_interval", "1s")
//                //????????????????????????????????????????????? ?????? request????????????:request
//                .put("index.translog.durability", "async")
//                .put("index.translog.sync_interval", "120s")
//                //????????????
//                .put("index.number_of_shards", "5")
//                //????????????
//                .put("index.number_of_replicas", "0")
//                //??????????????????????????????????????????10000???????????????????????????????????????????????????????????????????????????????????????
//                .put("index.max_result_window", "100000"));
//        //?????????????????????????????????json???????????????????????????json???????????????????????????map?????????
//        XContentBuilder jsonMapping = XContentFactory.jsonBuilder();
//        //?????????????????? ???????????????:https://www.elastic.co/guide/en/elasticsearch/reference/7.4/mapping-types.html#_core_datatypes
//        jsonMapping.startObject().startObject("properties")
//                .startObject("goodsName").field("type", "text").field("analyzer", "ik_max_word").endObject()
//                .startObject("goodsId").field("type", "integer").endObject()
//                //keyword????????????????????????
//                .startObject("connet").field("type", "text").endObject()
//                //???????????????
//                .endObject().endObject();
//        request.mapping(jsonMapping);
//        //?????????true????????????????????????????????????????????????????????????????????????????????????
//        request.create(false);
//        AcknowledgedResponse response = highLevelClient.indices().putTemplate(request, RequestOptions.DEFAULT);
//        if (response.isAcknowledged()) {
//            Console.log("??????????????????!");
//        } else {
//            Console.log("??????????????????!");
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
//            Console.log("??????index??????!");
//        } else {
//            Console.log("??????index??????!");
//        }
//    }
//
//    @GetMapping("/insert")
//    public void insertIndex() throws IOException {
//        BulkRequest request = new BulkRequest("goods_index_" + DateUtil.format(new Date(), "yyyyMM"));
//        for (int i = 6; i < 10; i++) {
//            GoodsIndex testData = new GoodsIndex();
//            testData.setGoodsId(i);
//            testData.setConnet(i+"???????????????----------------");
//            testData.setGoodsName("??????---------------"+i);
//            IndexRequest indexRequest = new IndexRequest("goods_index_" + DateUtil.format(new Date(), "yyyyMM"));
//            indexRequest.id(testData.getGoodsId().toString());
//            indexRequest.source(new JSONObject(testData).toString()
//                    , XContentType.JSON);
//            request.add(indexRequest);
//        }
//        BulkResponse response = highLevelClient.bulk(request, RequestOptions.DEFAULT);
//        Console.log("????????????:{} ??????:{} ",response.status(),response.getItems().length);
//    }
//
//    @GetMapping("/update")
//    public void update(Integer id) throws IOException {
//        UpdateRequest updateRequest = new UpdateRequest("goods_index_" + DateUtil.format(new Date(), "yyyyMM"), id.toString());
//        GoodsIndex goodsIndex=new GoodsIndex();
//        goodsIndex.setGoodsId(id);
//        goodsIndex.setGoodsName("????????????");
//        goodsIndex.setConnet("????????????");
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