package com.example.demo.complexImport.example3.util;

import com.example.demo.complexImport.example3.analysisEntity.*;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


/**
 * 导入excel 数据校验工具类
 */
public class ImportCheckUtil {

    /**
     * 解析单位工程招标控制价汇总表时 同时根据关键字符筛选必要数据，并记录已获取到该必要数据
     * recodeList
     * @param keywordArr
     * @return
     */
    public static boolean filterData(String keyword, String[] keywordArr, List<String> recordList){
           if(StringUtils.isNotBlank(keyword)){
               for(String str : keywordArr){
                   if(str.contains(keyword)){
                       recordList.add(str);
                       return true;
                   }
               }
           }
           return false;
    }

    /**
     * 校验字符串数字（>0） 空值返回false
     * @param str
     * @return
     */
    public static boolean checkNumber(String str){
        if(StringUtils.isBlank(str)){
            return false;
        }
        Pattern pattern = Pattern.compile("^[\\d]*[\\.]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 校验字符串数字（>0） 空值返回true
     * @param str
     * @return
     */
    public static boolean checkNumber2(String str){
        if(StringUtils.isBlank(str)){
            return true;
        }
        Pattern pattern = Pattern.compile("^[\\d]*[\\.]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 校验字符串是否为清单编码
     * @param str
     * @return
     */
    public static boolean checkProjectNum(String str){
        if(StringUtils.isBlank(str)){
            return false;
        }
        Pattern pattern = Pattern.compile("^[\\d]{12}$");
        return pattern.matcher(str).matches();
    }

    /**
     * 分部分项工程和单价措施项目清单与计价表
     * @param pl
     */
    public static void checkpriceList(PriceList pl) {
        List<ErrorRecord> errorRecordList = new ArrayList<>();
        if(StringUtils.isBlank(pl.getProjectName())){
            ErrorRecord errorRecord = new ErrorRecord(Commonstrings.error_xmzdz);
            errorRecordList.add(errorRecord);
        }
        if(StringUtils.isBlank(pl.getDescription())){
            ErrorRecord errorRecord = new ErrorRecord(Commonstrings.error_xmtzms);
            errorRecordList.add(errorRecord);
        }
        if(StringUtils.isBlank(pl.getUnit())){
            ErrorRecord errorRecord = new ErrorRecord(Commonstrings.error_jldw);
            errorRecordList.add(errorRecord);
        }
        if(!checkNumber(pl.getEngineeringAmount())){
            ErrorRecord errorRecord = new ErrorRecord(Commonstrings.error_gcl);
            errorRecordList.add(errorRecord);
        }
        if(StringUtils.isBlank(pl.getCUnitPrice())){
            ErrorRecord errorRecord = new ErrorRecord(Commonstrings.error_zhdj);
            errorRecordList.add(errorRecord);
        }
        if(StringUtils.isBlank(pl.getPrice())){
            ErrorRecord errorRecord = new ErrorRecord(Commonstrings.error_hj);
            errorRecordList.add(errorRecord);
        }
        pl.setErrorRecordList(errorRecordList);
    }

    /**
     * 单位工程人材机汇总表/主要材料设备价格表/主要材料价格表/发包人提供材料和工程设备一览表/承包人提供主要材料和工程设备一览表
     * @param pam
     */
    public static void checkTalentAndMachine(TalentAndMachine pam) {
        List<ErrorRecord> errorRecordList = new ArrayList<>();
        if(StringUtils.isBlank(pam.getName())){
            ErrorRecord errorRecord = new ErrorRecord(Commonstrings.error_clmc);
            errorRecordList.add(errorRecord);
        }
        if(StringUtils.isBlank(pam.getUnit())){
            ErrorRecord errorRecord = new ErrorRecord(Commonstrings.error_dw);
            errorRecordList.add(errorRecord);
        }
        if(!checkNumber(pam.getCount())){
            ErrorRecord errorRecord = new ErrorRecord(Commonstrings.error_sl);
            errorRecordList.add(errorRecord);
        }
        if(!checkNumber(pam.getUnitPrice())){
            ErrorRecord errorRecord = new ErrorRecord(Commonstrings.error_scj);
            errorRecordList.add(errorRecord);
        }
        pam.setErrorRecordList(errorRecordList);
    }

    /**
     *  综合单价分析表数据校验(及纠正) 定额所有费用为0时，需通过材料费明细纠正
     * @param cunitPriceList 综合单价分析表
     * @param priceListList 分部分项工程和单价措施项目清单与计价表 为空时不做联合校验
     */
    public static void checkCunitPrice(List<CunitPrice> cunitPriceList, List<PriceList> priceListList) {
        //联合校验
        boolean jvFlag = true;
        if(priceListList == null || priceListList.size()==0){
            jvFlag = false;
        }
        ErrorRecord errorRecord = new ErrorRecord(Commonstrings.error_dexx);
        for(int y = 0; y<cunitPriceList.size(); y++){
            List<ErrorRecord> errorRecordList = new ArrayList<>();
            CunitPrice cp = cunitPriceList.get(y);
            //联合校验
            PriceList priceList = null;
            if(jvFlag){
                for(int i = 0; i<priceListList.size();i++){
                    PriceList pl = priceListList.get(i);
                    if(StringUtils.equals(pl.getProjectCode(),cp.getProjectNum())){
                        priceList = pl;
                        i = priceListList.size();
                    }
                }
                if(priceList == null){//综合单价分析表里的编码在分部分项里找不到
                    errorRecordList.add(errorRecord);
                    cp.setErrorRecordList(errorRecordList);
                    continue;
                }
            }

            //综合单价分析表内校验
            if(!checkProjectNum(cp.getProjectNum())
                    ||StringUtils.isBlank(cp.getCountUnit())
                    ||!checkNumber(cp.getWord())
                    ||!checkNumber2(cp.getSubtotalLaborCost())
                    ||!checkNumber2(cp.getSubtotalMachineryCost())
                    ||!checkNumber2(cp.getSubtotalMaterialCost())
                    ||!checkNumber2(cp.getSubtotalProfit())
                    ||!checkNumber2(cp.getUnpricedMaterialCost())
                    ||!checkNumber2(cp.getOtherTotalPrice())
                    ||!checkNumber2(cp.getOtherEstTotalPrice())
                    ||!checkNumber2(cp.getTotalTotalPrice())
                    ||!checkNumber2(cp.getTotalEstTotalPrice())
            ){
                errorRecordList.add(errorRecord);
                cp.setErrorRecordList(errorRecordList);
                continue;
            }
            //材料费明细列表
            List<MaterialCostDetails> materialCostDetailsList = cp.getMaterialCostDetailsList();
            long dErrorCount = materialCostDetailsList.stream().filter(a->
                    StringUtils.isBlank(a.getInformation())||StringUtils.isBlank(a.getUnit())
                            || !checkNumber(a.getCount()) ||!checkNumber(a.getUnitPrice())||!checkNumber(a.getTotalPrice())
            ).count();
            if(dErrorCount>0){
                errorRecordList.add(errorRecord);
                cp.setErrorRecordList(errorRecordList);
                continue;
            }

            List<Quota> quotaList = cp.getQuotaList();
            BigDecimal rgfjsSum = new BigDecimal(0);//人工费计算
            BigDecimal clfSum = new BigDecimal(0);//材料费计算
            BigDecimal jxfSum = new BigDecimal(0);//机械费计算
            BigDecimal glhlrSum = new BigDecimal(0);//管理费和利润计算
            boolean flag1 = false;
            for(int x =0; x<quotaList.size(); x++){
                Quota i = quotaList.get(x);
                if(StringUtils.isBlank(i.getQuotaNum())
                        || StringUtils.isBlank(i.getQuotaName())
                        || StringUtils.isBlank(i.getQuotaUnit())
                        || !checkNumber(i.getQuotaCount())
                        || !checkNumber2(i.getLaborCost())
                        || !checkNumber2(i.getMachineryCost())
                        || !checkNumber2(i.getMaterialCost())
                        || !checkNumber2(i.getProfit())
                        || !checkNumber2(i.getTotalLaborCost())
                        || !checkNumber2(i.getTotalMachineryCost())
                        || !checkNumber2(i.getTotalMaterialCost())
                        || !checkNumber2(i.getTotalProfit())
                        || equalityCheck(i.getQuotaCount(),i.getLaborCost(),i.getTotalLaborCost())
                        || equalityCheck(i.getQuotaCount(),i.getMachineryCost(),i.getTotalMachineryCost())
                        || equalityCheck(i.getQuotaCount(),i.getMaterialCost(),i.getTotalMaterialCost())
                        || equalityCheck(i.getQuotaCount(),i.getProfit(),i.getTotalProfit())
                ){
                    flag1 = true;
                }else{
                    BigDecimal rgfhj = new BigDecimal(StringUtils.isBlank(i.getTotalLaborCost())?"0":i.getTotalLaborCost());
                    BigDecimal clfhj = new BigDecimal(StringUtils.isBlank(i.getTotalMaterialCost())?"0":i.getTotalMaterialCost());
                    BigDecimal jxfhj = new BigDecimal(StringUtils.isBlank(i.getTotalMachineryCost())?"0":i.getTotalMachineryCost());

                    BigDecimal glhlrhj = new BigDecimal(StringUtils.isBlank(i.getTotalProfit())?"0":i.getTotalProfit());
                    rgfjsSum = rgfjsSum.add(rgfhj);
                    clfSum = clfSum.add(clfhj);
                    jxfSum = jxfSum.add(jxfhj);
                    glhlrSum = glhlrSum.add(glhlrhj);
                }
                //数据纠正
                //判断是否需要数据纠正 等额列表里费用字段全部为0
                if((StringUtils.isBlank(i.getLaborCost())||"0".equals(i.getLaborCost()))
                        &&(StringUtils.isBlank(i.getMachineryCost())||"0".equals(i.getMachineryCost()))
                        &&(StringUtils.isBlank(i.getMaterialCost())||"0".equals(i.getMaterialCost()))
                        &&(StringUtils.isBlank(i.getProfit())||"0".equals(i.getProfit()))
                        &&(StringUtils.isBlank(i.getTotalLaborCost())||"0".equals(i.getTotalLaborCost()))
                        &&(StringUtils.isBlank(i.getTotalMachineryCost())||"0".equals(i.getTotalMachineryCost()))
                        &&(StringUtils.isBlank(i.getTotalMaterialCost())||"0".equals(i.getTotalMaterialCost()))
                        &&(StringUtils.isBlank(i.getTotalProfit())||"0".equals(i.getTotalProfit()))
                ){//材料明细里的对应材料的 等额-单价-材料费=单价*数量、等额-合价-材料费=合价*数量，材料明细上面已校验
                    for(int t = 0;t<materialCostDetailsList.size();t++){
                        MaterialCostDetails details = materialCostDetailsList.get(t);
                        if(StringUtils.equals(i.getQuotaName(),details.getInformation())){
                            BigDecimal clsl = new BigDecimal(details.getCount());
                            BigDecimal cldj = new BigDecimal(details.getUnitPrice());
                            BigDecimal clhj = new BigDecimal(details.getTotalPrice());
                            i.setMaterialCost(String.valueOf(clsl.multiply(cldj).setScale(2,BigDecimal.ROUND_HALF_UP)));
                            i.setTotalMaterialCost(String.valueOf(clsl.multiply(clhj).setScale(2,BigDecimal.ROUND_HALF_UP)));
                            BigDecimal totalMaterialCost = new BigDecimal(i.getTotalMaterialCost());
                            clfSum = clfSum.add(totalMaterialCost);
                            cp.setSubtotalMaterialCost(String.valueOf(new BigDecimal(StringUtils.isBlank(cp.getSubtotalMaterialCost())?"0":cp.getSubtotalMaterialCost()).add(totalMaterialCost)));
                            cp.setAllInUnitRate(String.valueOf(new BigDecimal(StringUtils.isBlank(cp.getAllInUnitRate())?"0":cp.getAllInUnitRate()).add(totalMaterialCost)));
                            t = materialCostDetailsList.size();
                        }
                    }
                }
            }


            if(flag1){//等额部分校验有异常
                errorRecordList.add(errorRecord);
                cp.setErrorRecordList(errorRecordList);
                continue;
            }
            if(rgfjsSum.compareTo(new BigDecimal(StringUtils.isBlank(cp.getSubtotalLaborCost())?"0":cp.getSubtotalLaborCost()))!=0
                    ||jxfSum.compareTo(new BigDecimal(StringUtils.isBlank(cp.getSubtotalMachineryCost())?"0":cp.getSubtotalMachineryCost()))!=0
                    ||clfSum.compareTo(new BigDecimal(StringUtils.isBlank(cp.getSubtotalMaterialCost())?"0":cp.getSubtotalMaterialCost()))!=0
                    ||glhlrSum.compareTo(new BigDecimal(StringUtils.isBlank(cp.getSubtotalProfit())?"0":cp.getSubtotalProfit()))!=0
            ){ //人工费等小计验算
                errorRecordList.add(errorRecord);
                cp.setErrorRecordList(errorRecordList);
                continue;
            }
            //有一种格式会拿不到综合单价，这里拿分部分项里面的综合单价
            if(!checkNumber(cp.getAllInUnitRate())){
                if(jvFlag){//做跨联合校验
                    cp.setAllInUnitRate(priceList.getCUnitPrice());
                }else {
                    errorRecordList.add(errorRecord);
                    cp.setErrorRecordList(errorRecordList);
                    continue;
                }
            }
            if(rgfjsSum.add(clfSum).add(jxfSum).add(glhlrSum)
                    .add(new BigDecimal(StringUtils.isBlank(cp.getUnpricedMaterialCost())?"0":cp.getUnpricedMaterialCost()))
                    .compareTo(new BigDecimal(cp.getAllInUnitRate()))!=0){//综合单价验算
                errorRecordList.add(errorRecord);
                cp.setErrorRecordList(errorRecordList);
            }
        }

    }

    //相等校验
    private static boolean equalityCheck(String str1,String str2,String str3){
        BigDecimal big1 = new BigDecimal(StringUtils.isBlank(str1)?"0":str1);
        BigDecimal big2 = new BigDecimal(StringUtils.isBlank(str2)?"0":str2);
        BigDecimal big3 = new BigDecimal(StringUtils.isBlank(str3)?"0":str3);
        int flag = big1.multiply(big2).setScale(2,BigDecimal.ROUND_HALF_UP).compareTo(big3);
        return flag !=0;
    }


}
