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
    private String xh;

    //名称及规格
    private String mcjkg;
    //名称
    private String mc;
    //规格
    private String ge;

    //单位
    private String dw;

    //数量
    private String sl;

    //市场价/单价
    private String scj;

    //合计
    private String hj;

    private List<ErrorRecord> errorRecordList;

}
