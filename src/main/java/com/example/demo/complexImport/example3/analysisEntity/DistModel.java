package com.example.demo.complexImport.example3.analysisEntity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DistModel {
    /**解析第一部分*/
    //序号 特殊格式的字段
    private String orderNum;

    //项目编码
    private String projectNum;

    //项目名称
    private String projectName;

    //项目特征 特殊格式的字段
    private String features;

    //计量单位
    private String countUnit;

    //工程量
    private String word;


    /**解析第二部分*/
    // 清单综合单价组成明细 定额
    private List<Quota> quotaList = new ArrayList<>();

    //合价-人工费 特殊格式的字段
    private String totalLaborCost;

    //合价-材料费 特殊格式的字段
    private String totalMaterialCost;

    //合价-机具费 特殊格式的字段
    private String totalMachineryCost;

    //合价-管理和利润 特殊格式的字段
    private String totalProfit;

    //小计-人工费
    private String subtotalLaborCost;

    //小计-材料费
    private String subtotalMaterialCost;

    //小计-机具费
    private String subtotalMachineryCost;

    //小计-管理和利润
    private String subtotalProfit;

    //人工单价
    private String laborPrice;

    //未计价材料费
    private String unpricedMaterialCost;

    //清单项目综合单价
    private String allInUnitRate;


   /**解析第三部分*/
    //材料费明细
    private List<MaterialCostDetails> materialCostDetailsList = new ArrayList<>();

    //其他材料费
    //单价（元）
    private String otherUnitPrice;

    //合价（元）
    private String otherTotalPrice;

    //暂估单价（元）
    private String otherEstUnitPrice;

    //暂估合价（元）
    private String otherEstTotalPrice;

    //材料费小计
    //单价（元）
    private String totalUnitPrice;

    //合价（元）
    private String totalTotalPrice;

    //暂估单价（元）
    private String totalEstUnitPrice;

    //暂估合价（元）
    private String totalEstTotalPrice;










}
