package com.example.demo.complexImport.example3.analysisEntity;

import lombok.Data;

/**
 * 单位工程招标控制价汇总表
 */
@Data
public class TenderControlPrice {

    //序号
    private String serialNum;

    //汇总内容
    private String content;

    //金额
    private String amount;

    //备注
    private String remarks;

    //数据错误标志 这里有错时只会是金额字段不为数据
    private boolean errorFlag = false;

}
