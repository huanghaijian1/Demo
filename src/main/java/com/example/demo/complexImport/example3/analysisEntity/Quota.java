package com.example.demo.complexImport.example3.analysisEntity;

import lombok.Data;

/**
 * 综合单价分析表  清单综合单价组成明细 定额
 */
@Data
public class Quota {

    //定额编码
    private String quotaNum;

    //定额名称
    private String quotaName;

    //定额单位
    private String quotaUnit;

    //数量
    private String quotaCount;

    //单价-人工费
    private String laborCost;

    //单价-材料费
    private String materialCost;

    //单价-机具费
    private String machineryCost;

    //单价-管理和利润
    private String profit;

    //合价-人工费
    private String totalLaborCost;

    //合价-材料费
    private String totalMaterialCost;

    //合价-机具费
    private String totalMachineryCost;

    //合价-管理和利润
    private String totalProfit;

}
