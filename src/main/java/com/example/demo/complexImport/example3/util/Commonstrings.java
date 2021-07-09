package com.example.demo.complexImport.example3.util;

public class Commonstrings {

    /**
     * 校验导入文件时的错误信息提示文本
     */
    public final static String error_xmbm = "项目编码有误";
    public final static String error_xmzdz = "项目名称为空";
    public final static String error_xmtzms = "项目特征描述为空";
    public final static String error_jldw = "计量单位为空";
    public final static String error_gcl = "工程量不为数字或空值";
    public final static String error_zhdj = "综合单价不为数字或空值";
    public final static String error_hj = "合计不为数字或空值";
    public final static String error_clmc = "材料名称为空";
    public final static String error_dw = "材料单位为空";
    public final static String error_sl = "材料数量不为数字或空值";
    public final static String error_scj = "材料单价/市场价不为数字或空值";
    public final static String error_xmbmbdy = "综合单价分析表里的项目编码在分部分项工程和单价措施项目清单与计价表里找不到";
    public final static String error_dexx = "综合单价分析表信息有误";

    /**
     * 分部分项工程和单价措施项目清单与计价表
     */
    //sheet页相关标志
    public final static String sheetMapKey_qdyjjb = "分部分项工程和单价措施项目清单与计价表";
    public final static String qdyjjbbz[] = {"序号","项目编码","项目名称","计量单位","项目特征","工程量"};
    //重要标志
    //    public final static String xh = "序号";
    public final static String zgj = "暂估价";
    public final static String kyjx_qdyjjb = "为计取规费等的使用"; //为计取规费等的使用，可在表中增设其中：“定额人工费”
    //表头
    public final static String xhbt_qdyjjb[] = {"序号"};
    public final static String xmbm_qdyjjb[] = {"项目编码"};
    public final static String xmmc_qdyjjb[] = {"项目名称"};
    public final static String jldw_qdyjjb[] = {"计量单位"};
    public final static String xmtz_qdyjjb[] = {"项目特征","项目特征描述"};
    public final static String gcl_qdyjjb[] = {"工程量"};
    public final static String zhdj_qdyjjb[] = {"综合单价"};
    public final static String hj_qdyjjb[] = {"合价"};
    public final static String zgj_qdyjjb[] = {"暂估价"};


    /**
     * 单位工程人材机汇总表
     */
    //sheet页相关标志
    public final static String sheetMapKey_rcjhzb1 = "单位工程人材机汇总表";
    //单位工程人材机汇总表 /主要材料设备价格表/主要材料价格表/发包人提供材料和工程设备一览表/承包人提供主要材料和工程设备一览表
    public final static String rcjhzbbz1[] = {"单位工程人材机汇总表 ","主要材料设备价格表","主要材料价格表","发包人提供材料和工程设备一览表","承包人提供主要材料和工程设备一览表"};
    public final static String rcjhzbbz2[] = {"序号","名称及规格","单位","数量","市场价","合计"};
    public final static String rcjhzbbz3[] = {"材料名称","规格","型号","特殊要求","单位","数量","单价"};
    //重要标志
//    public final static String xh = "序号";
    public final static String bzr = "编制人";
    //表头一
    public final static String xhbt_rcjhzb[] = {"序号"};
    public final static String mcjkg_rcjhzb[] = {"名称及规格"};
    public final static String dw_rcjhzb[] = {"单位"};
    public final static String sl_rcjhzb[] = {"数量"};
    public final static String scj_rcjhzb[] = {"市场价","单价"};
    public final static String hj_rcjhzb[] = {"合计","市场价合计"};
    //sheet页相关标志
    public final static String sheetMapKey_rcjhzb2 = "主要材料设备价格表";
    //表头二
//    public final static String xhbt_rcjhzb[] = {"序号"};
//    public final static String dw_rcjhzb[] = {"单位"};
//    public final static String sl_rcjhzb[] = {"数量"};
//    public final static String scj_rcjhzb[] = {"市场价","单价"};
    public final static String clmc_rcjhzb[] = {"材料名称"};
    public final static String kgxhd_rcjhzb[] = {"规格、型号等特殊要求","规格","型号","特殊要求"};



    /**
     * 单位工程招标控制价汇总表表头
     */
    //sheet页相关标志
    public final static String sheetMapKey_zbkzjhzb = "单位工程招标控制价汇总表";
    public final static String zbkzjhzbbz[] = {"序号","汇总内容","金额","备注"};
    //重要标志
//    public final static String xh = "序号";
    public final static String kyjx_zbkzjhzb = "本表适用于单位工程招标控制价或投标报价的汇总";
    //分部分项合计/分部分项、措施合计、其他项目、规费、税金、总造价
    public final static String[] keywordArr_zbkzjhzb = {"分部分项合计","分部分项","措施合计","其他项目","规费","税金","总造价"};

    //表头
    public final static String xhbt[] = {"序号"};
    public final static String hznrbt[] = {"汇总内容"};
    public final static String jebt[] = {"金额(元)","金额（元）"};
    public final static String bzbt[] = {"备注"};


    /**
     * 综合单价分析表
     */
    //sheet页相关标志
    public final static String sheetMapKey_zhdjfxb = "综合单价分析表";
    public final static String zhdjfxbbz[] = {"综合单价分析表","项目编码","项目名称","人工费","材料费"};

    //重要标志
    public final static String gcmc = "工程名称";
    public final static String xh = "序号";
    public final static String xmbm = "项目编码";
    public final static String debhbz = "定额编号";
    public final static String rgf = "人工费";
    public final static String rgdj = "人工单价";
    public final static String wjjclf = "未计价材料费";
    public final static String qdxmzhdj = "清单项目综合单价";
    public final static String clfmx = "主要材料名称、规格、型号";
    public final static String qtclf = "其他材料费";
    public final static String clfxj = "材料费小计";
    public final static String kyjx = "如不使用省级或行业建设主管部门发布的计价依据";

    //项目编码等横表头
    public final static String xmbmbt[] = {"项目编码"};
    public final static String xmmcbt[] = {"项目名称"};
    public final static String jldwbt[] = {"计量单位"};
    public final static String gclbt[] = {"工程量"};
    //清单综合单价组成明细 表头
    public final static String debh[] = {"定额编号定额编号"};
    public final static String dexmmc[] = {"定额项目名称定额项目名称"};
    public final static String dedw[] = {"定额单位定额单位"};
    public final static String desl[] = {"数量数量"};
    public final static String djrgf[] = {"单价人工费"};
    public final static String djclf[] = {"单价材料费"};
    public final static String djjxf[] = {"单价机械费","单价机具费","单价机械使用费"};
    public final static String djglfylr[] = {"单价管理费和利润"};
    public final static String djglf[] = {"单价管理费"};
    public final static String djlr[] = {"单价利润"};
    public final static String hjrgf[] = {"合价人工费"};
    public final static String hjclf[] = {"合价材料费"};
    public final static String hjjxf[] = {"合价机械费","合价机具费","合价机械使用费"};
    public final static String hjglfylr[] = {"合价管理费和利润"};
    public final static String hjglf[] = {"合价管理费"};
    public final static String hjlr[] = {"合价利润"};
    //材料费明细 表头
    public final static String clmc[] = {"主要材料名称、规格、型号"};
    public final static String cldw[] = {"单位"};
    public final static String clsl[] = {"数量"};
    public final static String cldj[] = {"单价（元）","单价(元)"};
    public final static String clhj[] = {"合价（元）","合价(元)"};
    public final static String clzgdj[] = {"暂估单价（元）","暂估单价(元)"};
    public final static String clzghj[] = {"暂估合价（元）","暂估合价(元)"};
    //特殊模板表头
    public final static String tsxhbt[] = {"序号"};
    public final static String tsxmbmbt[] = {"项目编码"};
    public final static String tsxmmcbt[] = {"项目名称"};
    public final static String tsxmtzbt[] = {"项目特征","项目特征描述"};
    public final static String tsrgfbt[] = {"人工费"};
    public final static String tsclfbt[] = {"材料费"};
    public final static String tsjlfbt[] = {"机械使用费","机械费","机具费"};
    public final static String tsglylrbt[] = {"管理费与利润"};
    public final static String tsglfbt[] = {"管理费"};
    public final static String tslrbt[] = {"利润"};
    public final static String tszhdjbt[] = {"综合单价"};

    public final static String getA = "getA";

    public final static String getB = "getB";

    public final static String getC = "getC";

    public final static String getD = "getD";

    public final static String getE = "getE";

    public final static String getF = "getF";

    public final static String getG = "getG";

    public final static String getH = "getH";

    public final static String getI = "getI";

    public final static String getJ = "getJ";

    public final static String getK = "getK";

    public final static String getL = "getL";

    public final static String getM = "getM";

    public final static String getN = "getN";

    public final static String getO = "getO";

    public final static String getP = "getP";

    public final static String getQ = "getQ";

    public final static String getR = "getR";

    public final static String getS = "getS";

    public final static String getT = "getT";

    public final static String getU = "getU";

    public final static String getV = "getV";

    public final static String getW = "getW";

    public final static String getX = "getX";

    public final static String getY = "getY";

    public final static String getZ = "getZ";

}
