package com.example.demo.service;

import com.example.demo.complexImport.example1.ExcelModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasicService {

    public Boolean saveData(List<ExcelModel> basics) {
        //业务逻辑
        //将解析数据存储数据库
        return true;
    }
}
