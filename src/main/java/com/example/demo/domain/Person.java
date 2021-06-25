package com.example.demo.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName="jwang01",type = "_doc")
public class Person implements Serializable{

    @Field(name="id")
    private String id;
    @Field(name="name")
    private String name;
    @Field(name="age")
    private Integer age;
    @Field(name="email")
    private String email;
    @Field(name="hobby")
    private String hobby;

}
