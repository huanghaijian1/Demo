package com.example.demo.dao;

import com.example.demo.domain.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;


/**
 * EsBlog Repository接口.
 */
public interface EsBlogRepository /*extends ElasticsearchRepository<Person, String> */{
    //下面是我们根据 spring data jpa 的命名规范额外创建的两个查询方法
    /**
     * 模糊查询(去重)，根据标题，简介，描述和标签查询（含有即可）Containing
     * @param title
     * @param Summary
     * @param content
     * @param tags
     * @param pageable
     * @return
     */
    Page<Person> findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(String title,String Summary,String content,String tags,Pageable pageable);

    /**
     * 根据 Blog 的id 查询 EsBlog
     * @param blogId
     * @return
     */
    Person findByBlogId(Long blogId);
}