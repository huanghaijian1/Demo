package com.example.demo.complexImport.example3;

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
//    @ExcelProperty(index = 0)
//    private String xm;
//    @ExcelProperty(index = 1)
//    private String wxh;
//    @ExcelProperty(index = 2)
//    private String sjh;
//    @ExcelIgnore
//    private Integer rowNum;
    @ExcelProperty(index = 0)
    private String a;
    @ExcelProperty(index = 1)
    private String b;
    @ExcelProperty(index = 2)
    private String c;
    @ExcelProperty(index = 3)
    private String d;
    @ExcelProperty(index = 4)
    private String e;
    @ExcelProperty(index = 5)
    private String f;
    @ExcelProperty(index = 6)
    private String g;
    @ExcelProperty(index = 7)
    private String h;
    @ExcelProperty(index = 8)
    private String i;
    @ExcelProperty(index = 9)
    private String j;
    @ExcelProperty(index = 10)
    private String k;
    @ExcelProperty(index = 11)
    private String l;
    @ExcelProperty(index = 12)
    private String m;
    @ExcelProperty(index = 13)
    private String n;

}
