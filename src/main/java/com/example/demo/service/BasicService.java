package com.example.demo.service;

import com.example.demo.complexImport.TestModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BasicService {

    public Boolean saveData(List<TestModel> basics) {
        //业务逻辑
        //将解析数据存储数据库
        return true;
    }
}
