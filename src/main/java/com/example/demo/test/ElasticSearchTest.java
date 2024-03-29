package com.example.demo.test;

import com.example.demo.DemoApplication;
import com.example.demo.domain.Person;
import com.example.demo.domain.TenderControlPrice;
import org.apache.poi.ss.usermodel.DateUtil;
import org.elasticsearch.action.update.UpdateRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;


public class ElasticSearchTest {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Test
    public void testOK(){
        System.out.println("this is OK!");
    }

    @Test
    public void testCreateDoc(){
        Person person = new Person("1005","陈立志",23,"2345631234@qq.com","跳舞、踢足球、下围棋");
        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId(person.getId())
                .withObject(person)
                .build();
        String documentId = elasticsearchRestTemplate.index(indexQuery);
        System.out.println(documentId);
    }

    @Test
    public void tesFindDoc(){
//        elasticsearchRestTemplate.queryForObject()
        Person documentId = elasticsearchRestTemplate.queryForObject(GetQuery.getById("1005"),Person.class);
        System.out.println(documentId);
    }

    @Test
    public void testUpdateDoc() throws Exception{
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("name", "张三");
        UpdateRequest updateRequest = new UpdateRequest("jwang01", "_doc", "1005").doc(jsonMap);
        UpdateQuery query = new UpdateQueryBuilder()
                .withClass(Person.class)
                .withId("1005")
                .withUpdateRequest(updateRequest)
                .build();
        elasticsearchRestTemplate.update(query);

    }

    @Test
    public void testDeleteDoc() throws Exception{
        String documentId = elasticsearchRestTemplate.delete(Person.class,"1005");
        System.out.println(documentId);
    }


    public static void main(String[] args) {
        TenderControlPrice a = new TenderControlPrice();
        a.setSerialNum("1");
        a.setContent("哈哈哈");
        a.setAmount("1122.22");

        TenderControlPrice b = new TenderControlPrice();
        b.setSerialNum("2");
        b.setContent("哈哈哈");
        b.setAmount("1112.22");
        TenderControlPrice c = new TenderControlPrice();
        c.setSerialNum("2");
        c.setContent("哈哈");
        c.setAmount("11162.22");
        Set<TenderControlPrice> set = new HashSet<>();
        set.add(a);
        set.add(b);
        set.add(c);

        System.out.println(set);
    }
}