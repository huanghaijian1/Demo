package com.example.demo.complexImport.example3.util;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Map;

@Data
public class ExcelModel {

    @ExcelProperty(index = 0)
    private String a = "";
    @ExcelProperty(index = 1)
    private String b = "";
    @ExcelProperty(index = 2)
    private String c = "";
    @ExcelProperty(index = 3)
    private String d = "";
    @ExcelProperty(index = 4)
    private String e = "";
    @ExcelProperty(index = 5)
    private String f = "";
    @ExcelProperty(index = 6)
    private String g = "";
    @ExcelProperty(index = 7)
    private String h = "";
    @ExcelProperty(index = 8)
    private String i = "";
    @ExcelProperty(index = 9)
    private String j = "";
    @ExcelProperty(index = 10)
    private String k = "";
    @ExcelProperty(index = 11)
    private String l = "";
    @ExcelProperty(index = 12)
    private String m = "";
    @ExcelProperty(index = 13)
    private String n = "";
    @ExcelProperty(index = 14)
    private String o = "";
    @ExcelProperty(index = 15)
    private String p = "";
    @ExcelProperty(index = 16)
    private String q = "";
    @ExcelProperty(index = 17)
    private String r = "";
    @ExcelProperty(index = 18)
    private String s = "";
    @ExcelProperty(index = 19)
    private String t = "";
    @ExcelProperty(index = 20)
    private String u = "";
    @ExcelProperty(index = 21)
    private String v = "";
    @ExcelProperty(index = 22)
    private String w = "";
    @ExcelProperty(index = 23)
    private String x = "";
    @ExcelProperty(index = 24)
    private String y = "";
    @ExcelProperty(index = 25)
    private String z = "";


    /**
     *  键值对 表头名称：对应列单元格(内容再下)
     * @param t1
     * @param t2
     * @param map
     */
    public void setHeaderMap(ExcelModel t1, ExcelModel t2, Map<String,String> map){
        if(t1==null && t2!=null){
            replaceKg(t2);
            map.put(t2.getA(), Commonstrings.getA);
            map.put(t2.getB(),Commonstrings.getB);
            map.put(t2.getC(),Commonstrings.getC);
            map.put(t2.getD(),Commonstrings.getD);
            map.put(t2.getE(),Commonstrings.getE);
            map.put(t2.getF(),Commonstrings.getF);
            map.put(t2.getG(),Commonstrings.getG);
            map.put(t2.getH(),Commonstrings.getH);
            map.put(t2.getI(),Commonstrings.getI);
            map.put(t2.getJ(),Commonstrings.getJ);
            map.put(t2.getK(),Commonstrings.getK);
            map.put(t2.getL(),Commonstrings.getL);
            map.put(t2.getM(),Commonstrings.getM);
            map.put(t2.getN(),Commonstrings.getN);
            map.put(t2.getO(),Commonstrings.getO);
            map.put(t2.getP(),Commonstrings.getP);
            map.put(t2.getQ(),Commonstrings.getQ);
            map.put(t2.getR(),Commonstrings.getR);
            map.put(t2.getS(),Commonstrings.getS);
            map.put(t2.getT(),Commonstrings.getT);
            map.put(t2.getU(),Commonstrings.getU);
            map.put(t2.getV(),Commonstrings.getV);
            map.put(t2.getW(),Commonstrings.getW);
            map.put(t2.getX(),Commonstrings.getX);
            map.put(t2.getY(),Commonstrings.getY);
            map.put(t2.getZ(),Commonstrings.getZ);
        }else if(t1!=null && t2!=null){
            replaceKg(t1);
            replaceKg(t2);
            map.put(t1.getA()+t2.getA(),Commonstrings.getA);
            map.put(t1.getB()+t2.getB(),Commonstrings.getB);
            map.put(t1.getC()+t2.getC(),Commonstrings.getC);
            map.put(t1.getD()+t2.getD(),Commonstrings.getD);
            map.put(t1.getE()+t2.getE(),Commonstrings.getE);
            map.put(t1.getF()+t2.getF(),Commonstrings.getF);
            map.put(t1.getG()+t2.getG(),Commonstrings.getG);
            map.put(t1.getH()+t2.getH(),Commonstrings.getH);
            map.put(t1.getI()+t2.getI(),Commonstrings.getI);
            map.put(t1.getJ()+t2.getJ(),Commonstrings.getJ);
            map.put(t1.getK()+t2.getK(),Commonstrings.getK);
            map.put(t1.getL()+t2.getL(),Commonstrings.getL);
            map.put(t1.getM()+t2.getM(),Commonstrings.getM);
            map.put(t1.getN()+t2.getN(),Commonstrings.getN);
            map.put(t1.getO()+t2.getO(),Commonstrings.getO);
            map.put(t1.getP()+t2.getP(),Commonstrings.getP);
            map.put(t1.getQ()+t2.getQ(),Commonstrings.getQ);
            map.put(t1.getR()+t2.getR(),Commonstrings.getR);
            map.put(t1.getS()+t2.getS(),Commonstrings.getS);
            map.put(t1.getT()+t2.getT(),Commonstrings.getT);
            map.put(t1.getU()+t2.getU(),Commonstrings.getU);
            map.put(t1.getV()+t2.getV(),Commonstrings.getV);
            map.put(t1.getW()+t2.getW(),Commonstrings.getW);
            map.put(t1.getX()+t2.getX(),Commonstrings.getX);
            map.put(t1.getY()+t2.getY(),Commonstrings.getY);
            map.put(t1.getZ()+t2.getZ(),Commonstrings.getZ);
        }

    }

    /**
     * 键值对 表头名称：对应列单元格(内容再右边)
     * @param t
     * @param map
     */
    public void setHeaderMap2(ExcelModel t, Map<String,String> map){
        replaceKg(t);
        map.put(t.getA(),Commonstrings.getB);
        map.put(t.getB(),Commonstrings.getC);
        map.put(t.getC(),Commonstrings.getD);
        map.put(t.getD(),Commonstrings.getE);
        map.put(t.getE(),Commonstrings.getF);
        map.put(t.getF(),Commonstrings.getG);
        map.put(t.getG(),Commonstrings.getH);
        map.put(t.getH(),Commonstrings.getI);
        map.put(t.getI(),Commonstrings.getJ);
        map.put(t.getJ(),Commonstrings.getK);
        map.put(t.getK(),Commonstrings.getL);
        map.put(t.getL(),Commonstrings.getM);
        map.put(t.getM(),Commonstrings.getN);
        map.put(t.getN(),Commonstrings.getO);
        map.put(t.getO(),Commonstrings.getP);
        map.put(t.getP(),Commonstrings.getQ);
        map.put(t.getQ(),Commonstrings.getR);
        map.put(t.getR(),Commonstrings.getS);
        map.put(t.getS(),Commonstrings.getT);
        map.put(t.getT(),Commonstrings.getU);
        map.put(t.getU(),Commonstrings.getV);
        map.put(t.getV(),Commonstrings.getW);
        map.put(t.getW(),Commonstrings.getX);
        map.put(t.getX(),Commonstrings.getY);
        map.put(t.getY(),Commonstrings.getZ);
    }

    /**
     * 根据表头名称获取对应信息
     * @param map
     * @param tableHeaderName
     * @return
     */
    public String setTableData(Map<String,String> map, ExcelModel t, String[] tableHeaderName){

        try{
            for(String name : tableHeaderName){
                String methodName = map.get(name);
                if(StringUtils.isNotBlank(methodName)){
                    Method method = t.getClass().getMethod(methodName);
                    String value = (String) method.invoke(t);
                    return value;
                }
            }
        }catch (Exception e){
            e.getMessage();
        }
        return StringUtils.EMPTY;
    }

    /**
     * 根据表头名称获取对应信息 管理费与利润 管理费+利润
     * @param map
     * @param tableHeaderName
     * @param tableHeaderName1
     * @return
     */

    public String setTableData_glylr(Map<String,String> map, ExcelModel t, String[] tableHeaderName, String[]... tableHeaderName1){
        try{
            for(String name : tableHeaderName){
                String methodName = map.get(name);
                if(StringUtils.isNotBlank(methodName)){
                    Method method = t.getClass().getMethod(methodName);
                    String value = (String) method.invoke(t);
                    return value;
                }
            }
        }catch (Exception e){
            e.getMessage();
        }
        BigDecimal sum = new BigDecimal(0);
        for(int i =0; i < tableHeaderName1.length; i++){
            String str = setTableData(map,t,tableHeaderName1[i]);
            BigDecimal big = new BigDecimal(Double.parseDouble(StringUtils.isBlank(str)?"0":str));
            sum.add(big);
        }
        return String.valueOf(sum);
    }

    /**
     * 表头 -- 去掉换行符与首尾空格
     */
    public void replaceKg(ExcelModel t){
        t.setA(myReplace(t.getA()));
        t.setB(myReplace(t.getB()));
        t.setC(myReplace(t.getC()));
        t.setD(myReplace(t.getD()));
        t.setE(myReplace(t.getE()));
        t.setF(myReplace(t.getF()));
        t.setG(myReplace(t.getG()));
        t.setH(myReplace(t.getH()));
        t.setI(myReplace(t.getI()));
        t.setJ(myReplace(t.getJ()));
        t.setK(myReplace(t.getK()));
        t.setL(myReplace(t.getL()));
        t.setM(myReplace(t.getM()));
        t.setN(myReplace(t.getN()));
        t.setO(myReplace(t.getO()));
        t.setP(myReplace(t.getP()));
        t.setQ(myReplace(t.getQ()));
        t.setR(myReplace(t.getR()));
        t.setS(myReplace(t.getS()));
        t.setT(myReplace(t.getT()));
        t.setU(myReplace(t.getU()));
        t.setV(myReplace(t.getV()));
        t.setW(myReplace(t.getW()));
        t.setX(myReplace(t.getX()));
        t.setY(myReplace(t.getY()));
        t.setZ(myReplace(t.getZ()));
    }

    private String myReplace(String str){
        return str.replace("\n", "").replace("\r", "").replace("\\s*", "");
    }

    /**
     * 多表头excel 表头位置判断
     * @param headerName
     * @return
     */
    public boolean tableHeaderFlag(String headerName){
        String name = headerName == null ? "":headerName;
        if(name.equals(this.getA())||name.equals(this.getB())||name.equals(this.getC())||name.equals(this.getD())||name.equals(this.getE())||
                name.equals(this.getF())||name.equals(this.getG())||name.equals(this.getH())||name.equals(this.getI())||name.equals(this.getJ())||
                name.equals(this.getK())||name.equals(this.getL())||name.equals(this.getM())||name.equals(this.getN())||name.equals(this.getO())||
                name.equals(this.getP())||name.equals(this.getQ())||name.equals(this.getR())||name.equals(this.getS())||name.equals(this.getT())||
                name.equals(this.getU())||name.equals(this.getV())||name.equals(this.getW())||name.equals(this.getX())||name.equals(this.getY())||
                name.equals(this.getZ())){
            return true;
        }
        return false;
    }

}
