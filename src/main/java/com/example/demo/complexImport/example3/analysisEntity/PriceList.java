package com.example.demo.complexImport.example3.analysisEntity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 分部分项工程和单价措施项目清单与计价表
 */
@Data
public class PriceList {

    //序号
    private String xh;

    //项目编码
    private String xmbm;

    //项目名称
    private String xmmc;

    //项目特征描述
    private String xmtzms;

    //计量单位
    private String jldw;

    //工程量
    private String gcl;

    //综合单价
    private String zhdj;

    //合价
    private String hj;

    //暂估价
    private String zgj;

    private List<ErrorRecord> errorRecordList;

}
