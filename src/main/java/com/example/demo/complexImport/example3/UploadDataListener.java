package com.example.demo.complexImport.example3;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 模板的读取类
 *
 */
public class UploadDataListener<T> extends AnalysisEventListener<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadDataListener.class);
    /**
     * 解析的数据
     */
    List<DistModel> list = new ArrayList<>();

    DistModel distModel = new DistModel();

    //定额解析标志
    boolean quotaFlag = false;
    //材料费明细解析标志
    boolean detailsFlag = false;

    //跨页解析-定额列表
    boolean pageQuotaFlag = false;
    //跨页解析-材料费明细
    boolean pagedetailsFlag = false;


    /**
     * 正文起始行
     */
//    private Integer headRowNumber;
    /**
     * 合并单元格
     */
//    private List<CellExtra> extraMergeInfoList = new ArrayList<>();

//    public UploadDataListener(Integer headRowNumber) {
//        this.headRowNumber = headRowNumber;
//    }

    /**
     * excel 这个每一行数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context context
     */
    @Override
    public void invoke(T data, AnalysisContext context) {
        TestModel t = (TestModel)data;
        if("项目编码".equals(t.getA())){
            if(StringUtils.isNotBlank(distModel.getProjectNum())){
                list.add(distModel);
                distModel = new DistModel();
                //清单综合单价组成明细解析标志
                quotaFlag = false;
                //材料费明细解析标志
                detailsFlag = false;
                //跨页解析标志
                pageQuotaFlag = false;
                pagedetailsFlag = false;

            }
            distModel.setProjectNum(t.getC());
            distModel.setProjectName(t.getF());
            distModel.setCountUnit(t.getK());
            distModel.setWord(t.getN());
        }

        //小计
        if("人工单价".equals(t.getA())){
            distModel.setSubtotalLaborCost(t.getJ());
            distModel.setSubtotalMaterialCost(t.getK());
            distModel.setSubtotalMachineryCost(t.getM());
            distModel.setSubtotalProfit(t.getN());
            //清单综合单价组成明细解析标志
            quotaFlag = false;
            //跨页解析标志
            pageQuotaFlag = false;
//            pagedetailsFlag = false;
        }

        if("未计价材料费".equals(t.getC())){
            //人工单价
            distModel.setLaborPrice(t.getA());
            //未计价材料费
            distModel.setUnpricedMaterialCost(t.getJ());
        }

        if("清单项目综合单价".equals(t.getA())){
            distModel.setAllInUnitRate(t.getJ());
        }



        if("其他材料费".equals(t.getB()) && StringUtils.isBlank(t.getG())){
            distModel.setOtherUnitPrice(t.getJ());
            distModel.setOtherTotalPrice(t.getK());
            distModel.setOtherEstUnitPrice(t.getM());
            distModel.setOtherEstTotalPrice(t.getN());
            //材料费明细解析标志
            detailsFlag = false;
            //跨页解析标志
//            pageQuotaFlag = false;
            pagedetailsFlag = false;
        }


        //一般 材料费小计 为一个清单的结束
        if("材料费小计".equals(t.getB())){
            distModel.setTotalUnitPrice(t.getJ());
            distModel.setTotalTotalPrice(t.getK());
            distModel.setTotalEstUnitPrice(t.getM());
            distModel.setTotalEstTotalPrice(t.getN());
            list.add(distModel);
            distModel = new DistModel();
            //材料费明细解析标志
            detailsFlag = false;
            //跨页解析标志
//            pageQuotaFlag = false;
            pagedetailsFlag = false;
        }

        if(StringUtils.isNotBlank(t.getA()) && t.getA().contains("注：1.如不使用省级或行业建设主管部门发布的计价依据，可不填定额编码、名称等；")){
            if(quotaFlag){
                //清单综合单价组成明细解析标志
                quotaFlag = false;
                pageQuotaFlag = true;
            }
            if(detailsFlag){
                //材料费明细解析标志
                detailsFlag = false;
                pagedetailsFlag = true;
            }
            //不是跨页解析
            if(!(pageQuotaFlag || pagedetailsFlag)){
                if(StringUtils.isNotBlank(distModel.getProjectNum())){//特殊情况，此时也为一个清单的结束
                    list.add(distModel);
                    distModel = new DistModel();
                    quotaFlag = false;
                    detailsFlag = false;
                }
            }
        }

        //清单综合单价组成明细 定额部分解析
        if(quotaFlag){
            Quota quota = new Quota();
            quota.setQuotaNum(t.getA());
            quota.setQuotaName(t.getB());
            quota.setQuotaUnit(t.getC());
            quota.setQuotaCount(t.getD());
            quota.setLaborCost(t.getE());
            quota.setMaterialCost(t.getF());
            quota.setMachineryCost(t.getG());
            quota.setProfit(t.getI());
            quota.setTotalLaborCost(t.getJ());
            quota.setTotalMaterialCost(t.getK());
            quota.setTotalMachineryCost(t.getM());
            quota.setTotalProfit(t.getN());
            distModel.getQuotaList().add(quota);
        }

        //材料费明细
        if(detailsFlag && StringUtils.isNotBlank(t.getB())){
            MaterialCostDetails details = new MaterialCostDetails();
            details.setInformation(t.getB());
            details.setUnit(t.getG());
            details.setCount(t.getH());
            details.setUnitPrice(t.getJ());
            details.setTotalPrice(t.getK());
            details.setEstUnitPrice(t.getM());
            details.setEstTotalPrice(t.getN());
            distModel.getMaterialCostDetailsList().add(details);
        }


        if("人工费".equals(t.getE())){
            //清单综合单价组成明细解析标志 下一行为清单综合单价组成明细
            quotaFlag = true;
        }

        if("主要材料名称、规格、型号".equals(t.getB())){
            //材料费明细解析标志 下一行为材料费明细
            detailsFlag = true;
        }

        if((pageQuotaFlag||pagedetailsFlag) && (StringUtils.isNotBlank(t.getA()) && t.getA().contains("工程名称"))){//每页开始
            if(pageQuotaFlag){//是否为跨页解析定额
                quotaFlag = true;
            }
            if(pagedetailsFlag){//是否为跨页解析材料费明细
                detailsFlag = true;
            }
        }


    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("--------------------------------------------------------------------------------------------------");
        for(DistModel t : list){
            System.out.println(t);
        }
        System.out.println("--------------------------------------------------------------------------------------------------");
    }

    /**
     * 加上存储数据库
     */
    public List<DistModel> getData() {
        return list;
    }

//    @Override
//    public void extra(CellExtra extra, AnalysisContext context) {
//        LOGGER.info("读取到了一条额外信息:{}", JSON.toJSONString(extra));
//        switch (extra.getType()) {
//            case COMMENT: {
//                LOGGER.info("额外信息是批注,在rowIndex:{},columnIndex;{},内容是:{}", extra.getRowIndex(), extra.getColumnIndex(),
//                        extra.getText());
//                break;
//            }
//            case HYPERLINK: {
//                if ("Sheet1!A1".equals(extra.getText())) {
//                    LOGGER.info("额外信息是超链接,在rowIndex:{},columnIndex;{},内容是:{}", extra.getRowIndex(),
//                            extra.getColumnIndex(), extra.getText());
//                } else if ("Sheet2!A1".equals(extra.getText())) {
//                    LOGGER.info(
//                            "额外信息是超链接,而且覆盖了一个区间,在firstRowIndex:{},firstColumnIndex;{},lastRowIndex:{},lastColumnIndex:{},"
//                                    + "内容是:{}",
//                            extra.getFirstRowIndex(), extra.getFirstColumnIndex(), extra.getLastRowIndex(),
//                            extra.getLastColumnIndex(), extra.getText());
//                } else {
////                    Assert.fail("Unknown hyperlink!");
//                    LOGGER.error("Unknown hyperlink!");
//                }
//                break;
//            }
//            case MERGE: {
//                LOGGER.info(
//                        "额外信息是合并单元格,而且覆盖了一个区间,在firstRowIndex:{},firstColumnIndex;{},lastRowIndex:{},lastColumnIndex:{}",
//                        extra.getFirstRowIndex(), extra.getFirstColumnIndex(), extra.getLastRowIndex(),
//                        extra.getLastColumnIndex());
//                if (extra.getRowIndex() >= headRowNumber) {
//                    extraMergeInfoList.add(extra);
//                }
//                break;
//            }
//            default: {
//            }
//        }
//    }
//
//    public List<CellExtra> getExtraMergeInfoList() {
//        return extraMergeInfoList;
//    }
//
//    /**
//     * 处理合并单元格
//     *
//     * @param data               解析数据
//     * @param extraMergeInfoList 合并单元格信息
//     * @param headRowNumber      起始行
//     * @return 填充好的解析数据
//     */
//    private List<T> explainMergeData(List<T> data, List<CellExtra> extraMergeInfoList, Integer headRowNumber) {
////        循环所有合并单元格信息
//        extraMergeInfoList.forEach(cellExtra -> {
//            int firstRowIndex = cellExtra.getFirstRowIndex() - headRowNumber;
//            int lastRowIndex = cellExtra.getLastRowIndex() - headRowNumber;
//            int firstColumnIndex = cellExtra.getFirstColumnIndex();
//            int lastColumnIndex = cellExtra.getLastColumnIndex();
////            获取初始值
//            Object initValue = getInitValueFromList(firstRowIndex, firstColumnIndex, data);
////            设置值
//            for (int i = firstRowIndex; i <= lastRowIndex; i++) {
//                for (int j = firstColumnIndex; j <= lastColumnIndex; j++) {
//                    setInitValueToList(initValue, i, j, data);
//                }
//            }
//        });
//        return data;
//    }
//    /**
//     * 设置合并单元格的值
//     *
//     * @param filedValue  值
//     * @param rowIndex    行
//     * @param columnIndex 列
//     * @param data        解析数据
//     */
//    public void setInitValueToList(Object filedValue, Integer rowIndex, Integer columnIndex, List<T> data) {
//        T object = data.get(rowIndex);
//
//        for (Field field : object.getClass().getDeclaredFields()) {
//            //提升反射性能，关闭安全检查
//            field.setAccessible(true);
//            ExcelProperty annotation = field.getAnnotation(ExcelProperty.class);
//            if (annotation != null) {
//                if (annotation.index() == columnIndex) {
//                    try {
//                        field.set(object, filedValue);
//                        break;
//                    } catch (IllegalAccessException e) {
//                        throw new RuntimeException("解析数据时发生异常!");
//                    }
//                }
//            }
//        }
//    }
//
//
//    /**
//     * 获取合并单元格的初始值
//     * rowIndex对应list的索引
//     * columnIndex对应实体内的字段
//     *
//     * @param firstRowIndex    起始行
//     * @param firstColumnIndex 起始列
//     * @param data             列数据
//     * @return 初始值
//     */
//    private Object getInitValueFromList(Integer firstRowIndex, Integer firstColumnIndex, List<T> data) {
//        Object filedValue = null;
//        T object = data.get(firstRowIndex);
//        for (Field field : object.getClass().getDeclaredFields()) {
//            //提升反射性能，关闭安全检查
//            field.setAccessible(true);
//            ExcelProperty annotation = field.getAnnotation(ExcelProperty.class);
//            if (annotation != null) {
//                if (annotation.index() == firstColumnIndex) {
//                    try {
//                        filedValue = field.get(object);
//                        break;
//                    } catch (IllegalAccessException e) {
//                        throw new RuntimeException("解析数据时发生异常!");
//                    }
//                }
//            }
//        }
//        return filedValue;
//    }


}
