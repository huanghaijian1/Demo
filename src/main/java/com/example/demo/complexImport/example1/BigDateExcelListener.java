package com.example.demo.complexImport.example1;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.example.demo.complexImport.TestModel;
import com.example.demo.service.BasicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
/*
 * 监听器批处理数据
 * */
@Service
public class BigDateExcelListener extends AnalysisEventListener<Map<Integer, String>> {


    private final BasicService basicService;

    public BigDateExcelListener(BasicService basicService) {
        this.basicService = basicService;
    }


    /**
     * 批处理阈值2000
     */
    private static final int BATCH_COUNT = 2000;
    private List<TestModel> list = new ArrayList<>();
    private Boolean flag = false;

    @Override
    public void invoke(Map<Integer, String> basic, AnalysisContext analysisContext) {
        //获取对应的行数
        Object o = analysisContext.readRowHolder().getCurrentRowAnalysisResult();
        int num = analysisContext.readRowHolder().getRowIndex();
//        basic.setRowNum(num + 1);
        //list.add(basic);
        if (list.size() >= BATCH_COUNT) {
            saveData();
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveData();
        // log.info("所有数据解析完成！");

    }

    private void saveData() {
        //调用saveData()方法
        basicService.saveData(list);
    }
}