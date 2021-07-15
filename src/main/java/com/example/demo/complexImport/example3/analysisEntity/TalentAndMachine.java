package com.example.demo.complexImport.example3.analysisEntity;

import lombok.Data;

import java.util.List;

/**
 * 单位工程人材机汇总表
 */
@Data
public class TalentAndMachine {
    //材料名称、规格型号、单位、数量、单价
    //序号
    private String serialNum;

    //名称(名称及规格)
    private String name;
    //规格
    private String specification;

    //单位
    private String unit;

    //数量
    private String count;

    //市场价/单价
    private String unitPrice;

    //合价
    private String price;

    private List<ErrorRecord> errorRecordList;

}
