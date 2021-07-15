package com.example.demo.complexImport.example3;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.fastjson.JSON;
import com.example.demo.complexImport.example3.analysisEntity.*;
import com.example.demo.complexImport.example3.util.Commonstrings;
import com.example.demo.complexImport.example3.util.ImportCheckUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.complexImport.example3.util.ExcelModel;
import java.lang.reflect.Field;
import java.util.*;

import static com.alibaba.excel.enums.CellExtraTypeEnum.MERGE;

/**
 * 模板的读取类
 *
 */
public class UploadDataListener<T> extends AnalysisEventListener<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadDataListener.class);
    /**
     * 综合单价分析表
     */
    //解析的数据
    List<CunitPrice> list = new ArrayList<>();

    CunitPrice cunitPrice = new CunitPrice();

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
     * 所有解析与校验
     * @return
     */
    public Map<String,Object> allAnalysisAndVerification(){
        List<Object> objectList = tenderControlPrice();//已完成校验
        List<PriceList> priceListList = priceList();//已完成校验
        List<CunitPrice> cunitPriceList = analysisData();
        ImportCheckUtil.checkCunitPrice(cunitPriceList,priceListList);//综合单价分析表校验
        List<TalentAndMachine> talentAndMachineList = talentAndMachine();//已完成校验
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put(Commonstrings.sheetMapKey_zbkzjhzb,objectList);
        resultMap.put(Commonstrings.sheetMapKey_qdyjjb,priceListList);
        resultMap.put(Commonstrings.sheetMapKey_zhdjfxb,cunitPriceList);
        resultMap.put(Commonstrings.sheetMapKey_rcjhzb1,talentAndMachineList);
        return resultMap;
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
            nr = nr==null?"":nr.replace("\n", "").replace("\r", "").replace("\\s*", "");
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
        int count5 = 0;
        int count6 = 0;
        //标题鉴定
        int sum4 = Commonstrings.rcjhzbbz1.length;
        for(int x=0;x<sum4;x++){
            if(nr.contains(Commonstrings.rcjhzbbz1[x])){
                count4++;
                x = sum4;
            }
        }
        //表头鉴定1
        int sum5 = Commonstrings.rcjhzbbz2.length;
        for(int x=0;x<sum5;x++){
            if(nr.contains(Commonstrings.rcjhzbbz2[x])){
                count5++;
            }
        }
        if(count4 == 1 && sum5 == count5){
            return Commonstrings.sheetMapKey_rcjhzb1;
        }
        //表头鉴定2
        int sum6 = Commonstrings.rcjhzbbz3.length;
        for(int x=0;x<sum6;x++){
            if(nr.contains(Commonstrings.rcjhzbbz3[x])){
                count6++;
            }
        }
        if(count4 == 1 && sum6 == count6){
            return Commonstrings.sheetMapKey_rcjhzb2;
        }

        return null;
    }

    /**
     * 单位工程招标控制价汇总表 解析及校验数据
     * @return 返回值List<Object>
     *  List<TenderControlPrice>第一个元素为读取到的必要数据  errorFlag=true时，金额字段有误
     *  List<String>第二个元素缺失的必要数据
     */
    private List<Object> tenderControlPrice(){
        List<ExcelModel> modelList = allSheetMap.get(Commonstrings.sheetMapKey_zbkzjhzb);
        List<CellExtra> cellExtraList = extraMergeInfoMap.get(Commonstrings.sheetMapKey_zbkzjhzb);
        List<ExcelModel> data = explainMergeData(modelList, cellExtraList, headRowNumber);
        //存放数据集合
        List<TenderControlPrice> result = new ArrayList<>();
        //正文内容标志
        boolean textFlag = false;
        //表头
        Map<String,String> headerMap5 = null;
        List<String> recordList = new ArrayList<>();//记录已获取到的必要数据
        for(ExcelModel t : data){
            String json = t.toString();
            if(json.contains(Commonstrings.kyjx_zbkzjhzb)){//本行数据置为非正文
                textFlag = false;
            }
            //本行数据
            if(textFlag){//为正文数据
                TenderControlPrice tp = new TenderControlPrice();
                String hznr = t.setTableData(headerMap5,t,Commonstrings.hznrbt);
                boolean flag = ImportCheckUtil.filterData(hznr,Commonstrings.keywordArr_zbkzjhzb,recordList);
                if(flag){//只获取指定数据
                    tp.setSerialNum(t.setTableData(headerMap5,t,Commonstrings.xhbt));
                    tp.setContent(hznr);
                    String je = t.setTableData(headerMap5,t,Commonstrings.jebt);
                    if(!ImportCheckUtil.checkNumber(je)){
                        tp.setErrorFlag(true);
                    }
                    tp.setAmount(t.setTableData(headerMap5,t,Commonstrings.jebt));
                    tp.setRemarks(t.setTableData(headerMap5,t,Commonstrings.bzbt));
                    result.add(tp);
                }

            }

            if(json.contains(Commonstrings.xh)){//下一行数据置为正文
                textFlag = true;
                if(headerMap5 == null){
                    headerMap5 = new HashMap<>();
                    t.setHeaderMap(null,t,headerMap5);
                }
            }

        }
        //是否汇总内容为 "分部分项合计"/"分部分项","措施合计","其他项目","规费","税金","总造价"的数据都有
        List<String> defectList = new ArrayList<>();//缺失汇总数据集合
        for(String str1 : Commonstrings.keywordArr_zbkzjhzb){//Arrays.asList()的坑：不支持list.addAll,list.removeAll等
            defectList.add(str1);
        }
        defectList.removeAll(recordList);
        List<Object> resultList = new ArrayList<>();
        resultList.add(result);//读取到的必要数据  errorFlag=true时，金额字段有误
        resultList.add(defectList);//缺失的必要数据
        return resultList;
    }

    /**
     * 分部分项工程和单价措施项目清单与计价表 解析及校验数据
     * @return
     */
    private List<PriceList> priceList(){
        List<ExcelModel> modelList = allSheetMap.get(Commonstrings.sheetMapKey_qdyjjb);
        List<CellExtra> cellExtraList = extraMergeInfoMap.get(Commonstrings.sheetMapKey_qdyjjb);
        List<ExcelModel> data = explainMergeData(modelList, cellExtraList, headRowNumber);
        //存放数据集合
        List<PriceList> result = new ArrayList<>();
        //正文内容标志
        boolean textFlag = false;
        //表头
        Map<String,String> headerMap = null;
        for(ExcelModel t : data){
            String json = t.toString();
            if(json.contains(Commonstrings.kyjx_qdyjjb)){//本行数据置为非正文
                textFlag = false;
            }
            //本行数据
            if(textFlag){//为正文数据
                String xmbm = t.setTableData(headerMap,t,Commonstrings.xmbm_qdyjjb);
                if(ImportCheckUtil.checkProjectNum(xmbm)){
                    PriceList pl = new PriceList();
                    pl.setSerialNum(t.setTableData(headerMap,t,Commonstrings.xhbt_qdyjjb));
                    pl.setProjectCode(xmbm);
                    pl.setProjectName(t.setTableData(headerMap,t,Commonstrings.xmmc_qdyjjb));
                    pl.setDescription(t.setTableData(headerMap,t,Commonstrings.xmtz_qdyjjb));
                    pl.setUnit(t.setTableData(headerMap,t,Commonstrings.jldw_qdyjjb));
                    pl.setEngineeringAmount(t.setTableData(headerMap,t,Commonstrings.gcl_qdyjjb));
                    pl.setCUnitPrice(t.setTableData(headerMap,t,Commonstrings.zhdj_qdyjjb));
                    pl.setPrice(t.setTableData(headerMap,t,Commonstrings.hj_qdyjjb));
                    pl.setPValuation(t.setTableData(headerMap,t,Commonstrings.zgj_qdyjjb));
                    ImportCheckUtil.checkpriceList(pl);//数据校验
                    result.add(pl);
                }

            }

            if(json.contains(Commonstrings.zgj)){//下一行数据置为正文
                textFlag = true;
                if(headerMap == null){
                    headerMap = new HashMap<>();
                    t.setHeaderMap(null,t,headerMap);
                }
            }

        }
        return result;
    }

    /**
     * 单位工程人材机汇总表
     * @return
     */
    private List<TalentAndMachine> talentAndMachine(){
        List<ExcelModel> modelList;
        List<CellExtra> cellExtraList;
        boolean tableType = true;//是否为第一种表头格式
        if(allSheetMap.containsKey(Commonstrings.sheetMapKey_rcjhzb1)){
            modelList = allSheetMap.get(Commonstrings.sheetMapKey_rcjhzb1);
            cellExtraList = extraMergeInfoMap.get(Commonstrings.sheetMapKey_rcjhzb1);
        }else{
            tableType = false;
            modelList = allSheetMap.get(Commonstrings.sheetMapKey_rcjhzb2);
            cellExtraList = extraMergeInfoMap.get(Commonstrings.sheetMapKey_rcjhzb2);
        }
        List<ExcelModel> data = explainMergeData(modelList, cellExtraList, headRowNumber);
        //存放数据集合
        List<TalentAndMachine> result = new ArrayList<>();
        //正文内容标志
        boolean textFlag = false;
        //表头
        Map<String,String> headerMap = null;
        for(ExcelModel t : data){
            String json = t.toString();
            if(json.contains(Commonstrings.bzr)){//本行数据置为非正文
                textFlag = false;
            }
            //本行数据
            if(textFlag){//为正文数据
                TalentAndMachine pam = new TalentAndMachine();
                pam.setSerialNum(t.setTableData(headerMap,t,Commonstrings.xhbt_rcjhzb));
                if(tableType){
                    pam.setName(t.setTableData(headerMap,t,Commonstrings.mcjkg_rcjhzb));
                    pam.setPrice(t.setTableData(headerMap,t,Commonstrings.hj_rcjhzb));
                }else{
                    pam.setName(t.setTableData(headerMap,t,Commonstrings.clmc_rcjhzb));
                    pam.setSpecification(t.setTableData(headerMap,t,Commonstrings.kgxhd_rcjhzb));
                }
                pam.setUnit(t.setTableData(headerMap,t,Commonstrings.dw_rcjhzb));
                pam.setCount(t.setTableData(headerMap,t,Commonstrings.sl_rcjhzb));
                pam.setUnitPrice(t.setTableData(headerMap,t,Commonstrings.scj_rcjhzb));
                //数据校验
                ImportCheckUtil.checkTalentAndMachine(pam);
                result.add(pam);
            }
            if(json.contains(Commonstrings.xh)){//下一行数据置为正文
                textFlag = true;
                if(headerMap == null){
                    headerMap = new HashMap<>();
                    t.setHeaderMap(null,t,headerMap);
                }
            }

        }
        return result;
    }

    /**
     * 综合单价分析表-解析数据
     * @return
     */
    private List<CunitPrice> analysisData(){
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
    private void specialTemplate(ExcelModel t){

        if(Commonstrings.kyjx.equals(t.getA())){//识别到这里是不为正文内容
            specialTextFlag = false;
        }

        if(specialTextFlag){//正文内容
            cunitPrice.setOrderNum(t.setTableData(headerMap4,t,Commonstrings.tsxhbt));
            cunitPrice.setProjectNum(t.setTableData(headerMap4,t,Commonstrings.tsxmbmbt));
            cunitPrice.setProjectName(t.setTableData(headerMap4,t,Commonstrings.tsxmmcbt));
            cunitPrice.setFeatures(t.setTableData(headerMap4,t,Commonstrings.tsxmtzbt));
            cunitPrice.setTotalLaborCost(t.setTableData(headerMap4,t,Commonstrings.tsrgfbt));
            cunitPrice.setTotalMaterialCost(t.setTableData(headerMap4,t,Commonstrings.tsclfbt));
            cunitPrice.setTotalMachineryCost(t.setTableData(headerMap4,t,Commonstrings.tsjlfbt));
            cunitPrice.setTotalProfit(t.setTableData_glylr(headerMap4,t,Commonstrings.tsglylrbt,Commonstrings.tsglfbt,Commonstrings.tslrbt));
            cunitPrice.setAllInUnitRate(t.setTableData(headerMap4,t,Commonstrings.tszhdjbt));
           list.add(cunitPrice);
            cunitPrice = new CunitPrice();
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
    private void template1(ExcelModel t){
        if(Commonstrings.xmbm.equals(t.getA())){//项目编码
            if(StringUtils.isNotBlank(cunitPrice.getProjectNum())){
                list.add(cunitPrice);
                cunitPrice = new CunitPrice();
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
            cunitPrice.setProjectNum(t.setTableData(headerMap1,t,Commonstrings.xmbmbt));
            cunitPrice.setProjectName(t.setTableData(headerMap1,t,Commonstrings.xmmcbt));
            cunitPrice.setCountUnit(t.setTableData(headerMap1,t,Commonstrings.jldwbt));
            cunitPrice.setWord(t.setTableData(headerMap1,t,Commonstrings.gclbt));
        }

        //小计
        if(Commonstrings.rgdj.equals(t.getA())){
            //列单元格对应于 headerMap2 的 合价人工费，合价材料费，合价机械费，合价管理费与利润
            cunitPrice.setSubtotalLaborCost(t.setTableData(headerMap2,t,Commonstrings.hjrgf));
            cunitPrice.setSubtotalMaterialCost(t.setTableData(headerMap2,t,Commonstrings.hjclf));
            cunitPrice.setSubtotalMachineryCost(t.setTableData(headerMap2,t,Commonstrings.hjjxf));
            cunitPrice.setSubtotalProfit(t.setTableData_glylr(headerMap2,t,Commonstrings.hjglfylr,Commonstrings.hjglf,Commonstrings.hjlr));
            //清单综合单价组成明细解析标志
            quotaFlag = false;
            //跨页解析标志
            pageQuotaFlag = false;
//            pagedetailsFlag = false;
        }

        if(Commonstrings.wjjclf.equals(t.getC())){//未计价材料费
            //列单元格对应于 headerMap2 的 定额编号，合价人工费
            //人工单价
            cunitPrice.setLaborPrice(t.setTableData(headerMap2,t,Commonstrings.debh));
            //未计价材料费
            cunitPrice.setUnpricedMaterialCost(t.setTableData(headerMap2,t,Commonstrings.hjrgf));
        }

        if(Commonstrings.qdxmzhdj.equals(t.getA())){//清单项目综合单价
            cunitPrice.setAllInUnitRate(t.setTableData(headerMap2,t,Commonstrings.hjrgf));
        }

        //其他材料费
        if(Commonstrings.qtclf.equals(t.getB()) && Commonstrings.qtclf.equals(t.setTableData(headerMap3,t,Commonstrings.cldw))){
            //列单元格对应于 headerMap3 的 单价（元），合价（元），暂估单价（元），暂估合价（元）
            cunitPrice.setOtherUnitPrice(t.setTableData(headerMap3,t,Commonstrings.cldj));
            cunitPrice.setOtherTotalPrice(t.setTableData(headerMap3,t,Commonstrings.clhj));
            cunitPrice.setOtherEstUnitPrice(t.setTableData(headerMap3,t,Commonstrings.clzgdj));
            cunitPrice.setOtherEstTotalPrice(t.setTableData(headerMap3,t,Commonstrings.clzghj));
            //材料费明细解析标志
            detailsFlag = false;
            //跨页解析标志
//            pageQuotaFlag = false;
            pagedetailsFlag = false;
        }


        //一般 材料费小计 为一个清单的结束
        if(Commonstrings.clfxj.equals(t.getB())){//"材料费小计"
            //列单元格对应于 headerMap3 的 单价（元），合价（元），暂估单价（元），暂估合价（元）
            cunitPrice.setTotalUnitPrice(t.setTableData(headerMap3,t,Commonstrings.cldj));
            cunitPrice.setTotalTotalPrice(t.setTableData(headerMap3,t,Commonstrings.clhj));
            cunitPrice.setTotalEstUnitPrice(t.setTableData(headerMap3,t,Commonstrings.clzgdj));
            cunitPrice.setTotalEstTotalPrice(t.setTableData(headerMap3,t,Commonstrings.clzghj));
            list.add(cunitPrice);
            cunitPrice = new CunitPrice();
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
                if(StringUtils.isNotBlank(cunitPrice.getProjectNum())){//特殊情况，此时也为一个清单的结束
                    list.add(cunitPrice);
                    cunitPrice = new CunitPrice();
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

            cunitPrice.getQuotaList().add(quota);
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
            cunitPrice.getMaterialCostDetailsList().add(details);
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
