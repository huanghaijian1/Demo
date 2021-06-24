package com.example.demo.domain.response.esIndex;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

@Data
@Document(indexName = "javashop")
public class GoodsIndex implements Serializable {


    @Id
    private Integer goodsId;



    @Field(analyzer = "ik_max_word")
    private String goodsName;

    /**
     * 农贸市场ID
     * */
    private String connet;


}
