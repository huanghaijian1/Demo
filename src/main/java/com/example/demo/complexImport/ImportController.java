package com.example.demo.complexImport;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.example.demo.complexImport.example1.BigDateExcelListener;
import com.example.demo.complexImport.example2.EasyExcelUtil;
import com.example.demo.complexImport.example3.DistModel;
import com.example.demo.complexImport.example3.UploadDataListener;
import com.example.demo.domain.response.InternalResponse;
import com.example.demo.service.BasicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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


    //导入excel
    @ApiOperation("1-测试easyExcel")
    @PostMapping(value = "excelImport")
    public String excelImport(HttpServletRequest request,@ApiParam("1-上传文件")MultipartFile[] files) throws Exception {
        File file2 = new File("d://test2.xls");

        Map<String, Object> result = EasyExcelUtil.readExcel(file2, new TestModel(), 1);
        Boolean flag = (Boolean) result.get("flag");
        if (flag) {
            List<Object> list = (List<Object>) result.get("datas");
            if (list != null && list.size() > 0) {
                for (Object o : list) {
                    TestModel xfxx = (TestModel) o;
                    System.out.println(xfxx.getXm() + "/" + xfxx.getSjh() + "/" + xfxx.getSjh());
                }
            }
        } else {
            System.out.println("表头格式错误");
        }

        if (files != null && files.length > 0) {
            MultipartFile file = files[0];

//            File file2 = new File("d://test2.xls");
//
//            Map<String, Object> result = EasyExcelUtil.readExcel(file2, new TestModel(), 1);
//            Boolean flag = (Boolean) result.get("flag");
//            if (flag) {
//                List<Object> list = (List<Object>) result.get("datas");
//                if (list != null && list.size() > 0) {
//                    for (Object o : list) {
//                        TestModel xfxx = (TestModel) o;
//                        System.out.println(xfxx.getXm() + "/" + xfxx.getSjh() + "/" + xfxx.getSjh());
//                    }
//                }
//            } else {
//                System.out.println("表头格式错误");
//            }
        }
        return "index";

    }


    /**
     * 导入数据量很大时，分批保存到数据库
     * @param file
     * @return
     * @throws IOException
     */
    @ApiOperation("2-测试easyExcel")
    @GetMapping(value = "/importBasic")
    public InternalResponse importBasic() throws IOException {
//    public InternalResponse importBasic(@ApiParam("上传文件") MultipartFile file) throws IOException {
//        BigDateExcelListener basicExcelListener=new BigDateExcelListener(basicService);
        UploadDataListener basicExcelListener = new UploadDataListener();
        InputStream input = new FileInputStream(new File("d://test22.xls"));
//        EasyExcel.read(file.getInputStream(), TestModel.class, basicExcelListener).headRowNumber(1).sheet().doRead();
//        EasyExcel.read(input, HashMap.class, basicExcelListener).headRowNumber(0).sheet().doRead();
//        EasyExcel.read(input,com.example.demo.complexImport.example3.TestModel.class, basicExcelListener).extraRead(CellExtraTypeEnum.MERGE).sheet("表-09 综合单价分析表").headRowNumber(0).doReadSync();
        EasyExcel.read(input,com.example.demo.complexImport.example3.TestModel.class, basicExcelListener).sheet("表-09 综合单价分析表").headRowNumber(0).doReadSync();
        List<DistModel> list = basicExcelListener.getData();
        input.close();
        Map<String,String> result = new HashMap<>();
        result.put("message","");
        result.put("code","200");
        result.put("data","导入成功");

        return InternalResponse.success().withBody(result);

    }

}
