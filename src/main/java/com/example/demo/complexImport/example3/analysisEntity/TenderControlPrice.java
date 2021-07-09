package com.example.demo.complexImport.example3.analysisEntity;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 单位工程招标控制价汇总表
 */
@Data
public class TenderControlPrice {

    //序号
    private String xh;

    //汇总内容
    private String hznr;

    //金额
    private String je;

    //备注
    private String bz;

    //数据错误标志 这里有错时只会是金额字段不为数据
    private boolean errorFlag = false;

}
