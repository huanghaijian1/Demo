package com.example.demo.complexImport.example3;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
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
    List<T> list = new ArrayList<>();
    List<List<T>> list2 = new ArrayList<>();
    boolean flag = true;

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
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context context
     */
    @Override
    public void invoke(T data, AnalysisContext context) {
//        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
        String json = JSON.toJSONString(data);
//        if(json.contains("工程名称")){
//            flag = true;
//
//        }
        list.add(data);
        if(json.contains("注：1.如不使用省级或行业建设主管部门发布的计价依据，可不填定额编码、名称等；")){
//            flag=false;
            list2.add(list);
            list = new ArrayList<>();
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
        for(List t : list2){
            System.out.println(t);
        }
        System.out.println("--------------------------------------------------------------------------------------------------");
//        List<T> result = explainMergeData(list,extraMergeInfoList,0);
//        System.out.println("--------------------------------------------------------------------------------------------------");
//        for(T t : result){
//            System.out.println(t);
//        }
//        System.out.println("--------------------------------------------------------------------------------------------------");
//        LOGGER.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    public List<T> getData() {
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
