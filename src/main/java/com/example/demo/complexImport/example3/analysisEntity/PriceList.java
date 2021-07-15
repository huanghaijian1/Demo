package com.example.demo.complexImport.example3.analysisEntity;

import lombok.Data;

import java.util.List;

/**
 * 分部分项工程和单价措施项目清单与计价表
 */
@Data
public class PriceList {

    //序号
    private String serialNum;

    //项目编码
    private String projectCode;

    //项目名称
    private String projectName;

    //项目特征描述
    private String description;

    //计量单位
    private String unit;

    //工程量
    private String engineeringAmount;

    //综合单价
    private String cUnitPrice;

    //合价
    private String price;

    //暂估价
    private String pValuation;

    private List<ErrorRecord> errorRecordList;

}
