package com.example.demo.complexImport;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.CellExtraTypeEnum;

import com.example.demo.complexImport.example3.UploadDataListener;
import com.example.demo.complexImport.example3.analysisEntity.DistModel;
import com.example.demo.complexImport.example3.analysisEntity.PriceList;
import com.example.demo.complexImport.example3.analysisEntity.TalentAndMachine;
import com.example.demo.complexImport.example3.analysisEntity.TenderControlPrice;
import com.example.demo.complexImport.example3.util.Commonstrings;
import com.example.demo.complexImport.example3.util.ExcelModel;
import com.example.demo.domain.response.InternalResponse;
import com.example.demo.service.BasicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Api(tags = {"测试easyExcel"})
@RestController
public class ImportController {

    private final BasicService basicService;

    public ImportController(BasicService basicService){
        this.basicService = basicService;
    }



    /**
     * 导入数据量很大时，分批保存到数据库
     * @param
     * @return
     * @throws IOException
     */
    @ApiOperation("2-测试easyExcel")
    @GetMapping(value = "/importBasic")
    public InternalResponse importBasic() throws IOException {
//    public InternalResponse importBasic(@ApiParam("上传文件") MultipartFile file) throws IOException {

        UploadDataListener basicExcelListener = new UploadDataListener(0);
        InputStream input = new FileInputStream(new File("d://test22.xls"));

        EasyExcel.read(input, ExcelModel.class, basicExcelListener).extraRead(CellExtraTypeEnum.MERGE).ignoreEmptyRow(true).headRowNumber(0).doReadAll();
        Map<String,Object> map = basicExcelListener.allAnalysisAndVerification();


        Map<String,Object> result = new HashMap<>();
        result.put("message","导入成功");
        result.put("code","200");
        result.put("data",map);

        return InternalResponse.success().withBody(result);

    }

}
