package com.example.demo.complexImport;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class TestModel extends BaseRowModel{

//    @ExcelProperty(value = "姓名", index = 0)
//    private String xm;
//    @ExcelProperty(value = "微信号", index = 1)
//    private String wxh;
//    @ExcelProperty(value = "手机号", index = 2)
//    private String sjh;
//    @ExcelIgnore
//    private Integer rowNum;

// 含合并行的表头
//               联系方式
//    姓名    微信号     手机号
//
    @ExcelProperty(index = 0)
    private String xm;
    @ExcelProperty(index = 1)
    private String wxh;
    @ExcelProperty(index = 2)
    private String sjh;
    @ExcelIgnore
    private Integer rowNum;


}
