package com.example.demo.complexImport.example3;

import lombok.Data;

/**
 * 材料费明细
 */
@Data
public class MaterialCostDetails {

    //主要材料名称、规格、型号
    private String information;

    //单位
    private String unit;

    //数量
    private String count;

    //单价（元）
    private String unitPrice;

    //合价（元）
    private String totalPrice;

    //暂估单价（元）
    private String estUnitPrice;

    //暂估合价（元）
    private String estTotalPrice;


}
