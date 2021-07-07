package com.example.demo.complexImport.example3;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.fastjson.JSON;
import com.example.demo.complexImport.example3.analysisEntity.DistModel;
import com.example.demo.complexImport.example3.analysisEntity.MaterialCostDetails;
import com.example.demo.complexImport.example3.analysisEntity.Quota;
import com.example.demo.complexImport.example3.util.Commonstrings;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.complexImport.example3.util.ExcelModel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.alibaba.excel.enums.CellExtraTypeEnum.MERGE;

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

    //是否为特殊模板
    boolean specialFlag = false;
    //特殊模板跨页解析 是否为解析内容标志
    boolean specialTextFlag = false;

    //定额解析标志
    boolean quotaFlag = false;
    //材料费明细解析标志
    boolean detailsFlag = false;

    //跨页解析-定额列表
    boolean pageQuotaFlag = false;
    //跨页解析-材料费明细
    boolean pagedetailsFlag = false;

    //表头
    //项目编码
    Map<String,String> headerMap1 = null;
    //清单综合单价组成明细
    Map<String,String> headerMap2 = null;
    ExcelModel headerModel2 = null;
    //材料费明细
    Map<String,String> headerMap3 = null;

    //特殊模板表头
    Map<String,String> headerMap4 = null;

    /**
     * 正文起始行
     */
    private Integer headRowNumber;

    /**
     * 当前sheet页数据
     */
    List<ExcelModel> currSheetList = new ArrayList<>();

    /**
     * 所有sheet页数据
     */
    Map<String,List<ExcelModel>> allSheetMap =new HashMap<>();

    /**
     * 所有sheet页合并单元格信息
     */
    private Map<String,List<CellExtra>> extraMergeInfoMap = new HashMap<>();
    /**
     * 当前sheet页合并单元格信息
     */
    private List<CellExtra> extraMergeInfoList = new ArrayList<>();



    public UploadDataListener(Integer headRowNumber) {
        this.headRowNumber = headRowNumber;
    }

    /**
     * excel 这个每一行数据解析都会来调用
     * @param data
     * @param context
     */
    @Override
    public void invoke(T data, AnalysisContext context) {
        currSheetList.add((ExcelModel) data);
    }

    /**
     * 合并单元格
     * @param extra
     * @param context
     */
    @Override
    public void extra(CellExtra extra, AnalysisContext context) {
        if(extra.getType() == MERGE){
            if (extra.getRowIndex() >= headRowNumber) {
                extraMergeInfoList.add(extra);
            }
        }
    }

    /**
     * 每个sheet的所有数据解析完成后 都会来调用
     *
     * @param context context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if(currSheetList != null && currSheetList.size()>0){
            //读取前十行数据 用作判断当前sheet页是哪个sheet页
            String nr = JSON.toJSONString(currSheetList.subList(0,currSheetList.size()<10?currSheetList.size():10));
            String type = judgeSheet(nr);
            if(StringUtils.isNotBlank(type)){
                allSheetMap.put(type,currSheetList);//把每个sheet页的数据加进来
                extraMergeInfoMap.put(type,extraMergeInfoList);//每个sheet页的合并单元格信息加进来
            }
            currSheetList = new ArrayList<>();
            extraMergeInfoList = new ArrayList<>();
        }
    }

    /**
     * 用作判断当前sheet页是哪个sheet页
     * @param nr
     * @return
     */
    private String judgeSheet(String nr){
        //综合单价分析表
        int count1 = 0;
        int sum1 = Commonstrings.zhdjfxbbz.length;
        for(int x=0;x<sum1;x++){
            if(nr.contains(Commonstrings.zhdjfxbbz[x])){
                count1++;
            }
        }
        if(count1 == sum1){
            return Commonstrings.sheetMapKey_zhdjfxb;
        }
        //单位工程招标控制价汇总表
        int count2 = 0;
        int sum2 = Commonstrings.zbkzjhzbbz.length;
        for(int x=0;x<sum2;x++){
            if(nr.contains(Commonstrings.zbkzjhzbbz[x])){
                count2++;
            }
        }
        if(count2 == sum2){
            return Commonstrings.sheetMapKey_zbkzjhzb;
        }
        //分部分项工程和单价措施项目清单与计价表
        int count3 = 0;
        int sum3 = Commonstrings.qdyjjbbz.length;
        for(int x=0;x<sum3;x++){
            if(nr.contains(Commonstrings.qdyjjbbz[x])){
                count3++;
            }
        }
        if(count3 == sum3){
            return Commonstrings.sheetMapKey_qdyjjb;
        }
        //单位工程人材机汇总表
        int count4 = 0;
        int sum4 = Commonstrings.rcjhzbbz.length;
        for(int x=0;x<sum4;x++){
            if(nr.contains(Commonstrings.rcjhzbbz[x])){
                count4++;
            }
        }
        if(count4 == sum4){
            return Commonstrings.sheetMapKey_rcjhzb;
        }
        return null;
    }

    //Summary table  汇总表

    //Price list 计价表

    /**
     * 综合单价分析表-解析数据
     * @return
     */
    public List<DistModel> analysisData(){
        List<ExcelModel> modelList = allSheetMap.get(Commonstrings.sheetMapKey_zhdjfxb);
        List<CellExtra> cellExtraList = extraMergeInfoMap.get(Commonstrings.sheetMapKey_zhdjfxb);
        List<ExcelModel> data = explainMergeData(modelList, cellExtraList, headRowNumber);
        for(ExcelModel t : data){
            if(Commonstrings.xh.equals(t.getA()) && Commonstrings.xmbm.equals(t.getB())){
                specialFlag = true;
            }
            if(specialFlag){//特殊格式  -极少用的格式
                specialTemplate(t);
            } else {//一般格式  -常用的有诸多出入但大致一样的格式
                template1(t);
            }
        }
        return list;
    }


    /**
     * 特殊格式
     * @param t
     */
    public void specialTemplate(ExcelModel t){

        if(Commonstrings.kyjx.equals(t.getA())){//识别到这里是不为正文内容
            specialTextFlag = false;
        }

        if(specialTextFlag){//正文内容
           distModel.setOrderNum(t.setTableData(headerMap4,t,Commonstrings.tsxhbt));
           distModel.setProjectNum(t.setTableData(headerMap4,t,Commonstrings.tsxmbmbt));
           distModel.setProjectName(t.setTableData(headerMap4,t,Commonstrings.tsxmmcbt));
           distModel.setFeatures(t.setTableData(headerMap4,t,Commonstrings.tsxmtzbt));
           distModel.setTotalLaborCost(t.setTableData(headerMap4,t,Commonstrings.tsrgfbt));
           distModel.setTotalMaterialCost(t.setTableData(headerMap4,t,Commonstrings.tsclfbt));
           distModel.setTotalMachineryCost(t.setTableData(headerMap4,t,Commonstrings.tsjlfbt));
           distModel.setTotalProfit(t.setTableData_glylr(headerMap4,t,Commonstrings.tsglylrbt,Commonstrings.tsglfbt,Commonstrings.tslrbt));
           distModel.setAllInUnitRate(t.setTableData(headerMap4,t,Commonstrings.tszhdjbt));
           list.add(distModel);
           distModel = new DistModel();
        }

        if(Commonstrings.rgf.equals(t.getE())){
            specialTextFlag = true;//识别到这里下一行正文内容
            if(headerMap4 == null){
                //第一次识别表头
                headerMap4 = new HashMap<>(32);
                t.setHeaderMap(null,t,headerMap4);
            }
        }
    }

    /**
     * 一般格式
     * @param t
     */
    public void template1(ExcelModel t){
        if(Commonstrings.xmbm.equals(t.getA())){//项目编码
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
            if(headerMap1 == null){//第一次识别headerMap1表头
                headerMap1 = new HashMap<>(64);
                t.setHeaderMap2(t,headerMap1);
            }
            distModel.setProjectNum(t.setTableData(headerMap1,t,Commonstrings.xmbmbt));
            distModel.setProjectName(t.setTableData(headerMap1,t,Commonstrings.xmmcbt));
            distModel.setCountUnit(t.setTableData(headerMap1,t,Commonstrings.jldwbt));
            distModel.setWord(t.setTableData(headerMap1,t,Commonstrings.gclbt));
        }

        //小计
        if(Commonstrings.rgdj.equals(t.getA())){
            //列单元格对应于 headerMap2 的 合价人工费，合价材料费，合价机械费，合价管理费与利润
            distModel.setSubtotalLaborCost(t.setTableData(headerMap2,t,Commonstrings.hjrgf));
            distModel.setSubtotalMaterialCost(t.setTableData(headerMap2,t,Commonstrings.hjclf));
            distModel.setSubtotalMachineryCost(t.setTableData(headerMap2,t,Commonstrings.hjjxf));
            distModel.setSubtotalProfit(t.setTableData_glylr(headerMap2,t,Commonstrings.hjglfylr,Commonstrings.hjglf,Commonstrings.hjlr));
            //清单综合单价组成明细解析标志
            quotaFlag = false;
            //跨页解析标志
            pageQuotaFlag = false;
//            pagedetailsFlag = false;
        }

        if(Commonstrings.wjjclf.equals(t.getC())){//未计价材料费
            //列单元格对应于 headerMap2 的 定额编号，合价人工费
            //人工单价
            distModel.setLaborPrice(t.setTableData(headerMap2,t,Commonstrings.debh));
            //未计价材料费
            distModel.setUnpricedMaterialCost(t.setTableData(headerMap2,t,Commonstrings.hjrgf));
        }

        if(Commonstrings.qdxmzhdj.equals(t.getA())){//清单项目综合单价
            distModel.setAllInUnitRate(t.setTableData(headerMap2,t,Commonstrings.hjrgf));
        }

        //其他材料费
        if(Commonstrings.qtclf.equals(t.getB()) && Commonstrings.qtclf.equals(t.setTableData(headerMap3,t,Commonstrings.cldw))){
            //列单元格对应于 headerMap3 的 单价（元），合价（元），暂估单价（元），暂估合价（元）
            distModel.setOtherUnitPrice(t.setTableData(headerMap3,t,Commonstrings.cldj));
            distModel.setOtherTotalPrice(t.setTableData(headerMap3,t,Commonstrings.clhj));
            distModel.setOtherEstUnitPrice(t.setTableData(headerMap3,t,Commonstrings.clzgdj));
            distModel.setOtherEstTotalPrice(t.setTableData(headerMap3,t,Commonstrings.clzghj));
            //材料费明细解析标志
            detailsFlag = false;
            //跨页解析标志
//            pageQuotaFlag = false;
            pagedetailsFlag = false;
        }


        //一般 材料费小计 为一个清单的结束
        if(Commonstrings.clfxj.equals(t.getB())){//"材料费小计"
            //列单元格对应于 headerMap3 的 单价（元），合价（元），暂估单价（元），暂估合价（元）
            distModel.setTotalUnitPrice(t.setTableData(headerMap3,t,Commonstrings.cldj));
            distModel.setTotalTotalPrice(t.setTableData(headerMap3,t,Commonstrings.clhj));
            distModel.setTotalEstUnitPrice(t.setTableData(headerMap3,t,Commonstrings.clzgdj));
            distModel.setTotalEstTotalPrice(t.setTableData(headerMap3,t,Commonstrings.clzghj));
            list.add(distModel);
            distModel = new DistModel();
            //材料费明细解析标志
            detailsFlag = false;
            //跨页解析标志
//            pageQuotaFlag = false;
            pagedetailsFlag = false;
        }

        if(StringUtils.isNotBlank(t.getA()) && t.getA().contains(Commonstrings.kyjx)){//注：1.如不使用省级或行业建设主管部门发布的计价依据，可不填定额编码、名称等；
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
            quota.setQuotaNum(t.setTableData(headerMap2,t,Commonstrings.debh));
            quota.setQuotaName(t.setTableData(headerMap2,t,Commonstrings.dexmmc));
            quota.setQuotaUnit(t.setTableData(headerMap2,t,Commonstrings.dedw));
            quota.setQuotaCount(t.setTableData(headerMap2,t,Commonstrings.desl));

            quota.setLaborCost(t.setTableData(headerMap2,t,Commonstrings.djrgf));
            quota.setMaterialCost(t.setTableData(headerMap2,t,Commonstrings.djclf));
            quota.setMachineryCost(t.setTableData(headerMap2,t,Commonstrings.djjxf));
            quota.setProfit(t.setTableData_glylr(headerMap2,t,Commonstrings.djglfylr,Commonstrings.djglf,Commonstrings.djlr));

            quota.setTotalLaborCost(t.setTableData(headerMap2,t,Commonstrings.hjrgf));
            quota.setTotalMaterialCost(t.setTableData(headerMap2,t,Commonstrings.hjclf));
            quota.setTotalMachineryCost(t.setTableData(headerMap2,t,Commonstrings.hjjxf));
            quota.setTotalProfit(t.setTableData_glylr(headerMap2,t,Commonstrings.hjglfylr,Commonstrings.hjglf,Commonstrings.hjlr));

            distModel.getQuotaList().add(quota);
        }

        //材料费明细
        if(detailsFlag && StringUtils.isNotBlank(t.getB())){
            MaterialCostDetails details = new MaterialCostDetails();
            details.setInformation(t.setTableData(headerMap3,t,Commonstrings.clmc));
            details.setUnit(t.setTableData(headerMap3,t,Commonstrings.cldw));
            details.setCount(t.setTableData(headerMap3,t,Commonstrings.clsl));
            details.setUnitPrice(t.setTableData(headerMap3,t,Commonstrings.cldj));
            details.setTotalPrice(t.setTableData(headerMap3,t,Commonstrings.clhj));
            details.setEstUnitPrice(t.setTableData(headerMap3,t,Commonstrings.clzgdj));
            details.setEstTotalPrice(t.setTableData(headerMap3,t,Commonstrings.clzghj));
            distModel.getMaterialCostDetailsList().add(details);
        }

        if(Commonstrings.debhbz.equals(t.getA())){
            if(headerModel2 == null){//第一次识别项目定额表头
                headerModel2 = t;
            }
        }
        if(Commonstrings.rgf.equals(t.getE())){//人工费
            //清单综合单价组成明细解析标志 下一行为清单综合单价组成明细
            quotaFlag = true;
            //第一次识别项目定额表头
            if(headerMap2 == null){
                headerMap2 = new HashMap<>(64);
                t.setHeaderMap(headerModel2,t,headerMap2);
            }


        }

        if(Commonstrings.clfmx.equals(t.getB())){//主要材料名称、规格、型号
            //材料费明细解析标志 下一行为材料费明细
            detailsFlag = true;
            if(headerMap3 == null){//第一次识别材料费明细表头
                headerMap3 = new HashMap<>(32);
                t.setHeaderMap(null,t,headerMap3);
            }

        }

        if((pageQuotaFlag||pagedetailsFlag) && t.getA().contains(Commonstrings.gcmc)){//工程名称 每页开始
            if(pageQuotaFlag){//是否为跨页解析定额
                quotaFlag = true;
            }
            if(pagedetailsFlag){//是否为跨页解析材料费明细
                detailsFlag = true;
            }
        }

    }

    /**
     * 处理合并单元格
     *
     * @param data               解析数据
     * @param extraMergeInfoList 合并单元格信息
     * @param headRowNumber      起始行
     * @return 填充好的解析数据
     */
    private List<ExcelModel> explainMergeData(List<ExcelModel> data, List<CellExtra> extraMergeInfoList, Integer headRowNumber) {
//        循环所有合并单元格信息
        extraMergeInfoList.forEach(cellExtra -> {
            int firstRowIndex = cellExtra.getFirstRowIndex() - headRowNumber;
            int lastRowIndex = cellExtra.getLastRowIndex() - headRowNumber;
            int firstColumnIndex = cellExtra.getFirstColumnIndex();
            int lastColumnIndex = cellExtra.getLastColumnIndex();
//            获取初始值
            Object initValue = getInitValueFromList(firstRowIndex, firstColumnIndex, data);
//            设置值
            for (int i = firstRowIndex; i <= lastRowIndex; i++) {
                for (int j = firstColumnIndex; j <= lastColumnIndex; j++) {
                    setInitValueToList(initValue, i, j, data);
                }
            }
        });
        return data;
    }
    /**
     * 设置合并单元格的值
     *
     * @param filedValue  值
     * @param rowIndex    行
     * @param columnIndex 列
     * @param data        解析数据
     */
    private void setInitValueToList(Object filedValue, Integer rowIndex, Integer columnIndex, List<ExcelModel> data) {
        ExcelModel object = data.get(rowIndex);

        for (Field field : object.getClass().getDeclaredFields()) {
            //提升反射性能，关闭安全检查
            field.setAccessible(true);
            ExcelProperty annotation = field.getAnnotation(ExcelProperty.class);
            if (annotation != null) {
                if (annotation.index() == columnIndex) {
                    try {
                        field.set(object, filedValue);
                        break;
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("解析数据时发生异常!");
                    }
                }
            }
        }
    }


    /**
     * 获取合并单元格的初始值
     * rowIndex对应list的索引
     * columnIndex对应实体内的字段
     *
     * @param firstRowIndex    起始行
     * @param firstColumnIndex 起始列
     * @param data             列数据
     * @return 初始值
     */
    private Object getInitValueFromList(Integer firstRowIndex, Integer firstColumnIndex, List<ExcelModel> data) {
        Object filedValue = null;
        ExcelModel object = data.get(firstRowIndex);
        for (Field field : object.getClass().getDeclaredFields()) {
            //提升反射性能，关闭安全检查
            field.setAccessible(true);
            ExcelProperty annotation = field.getAnnotation(ExcelProperty.class);
            if (annotation != null) {
                if (annotation.index() == firstColumnIndex) {
                    try {
                        filedValue = field.get(object);
                        break;
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("解析数据时发生异常!");
                    }
                }
            }
        }
        return filedValue;
    }


}
