package com.example.demo.complexImport;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.example.demo.complexImport.example1.BigDateExcelListener;
import com.example.demo.complexImport.example2.EasyExcelUtil;
import com.example.demo.complexImport.example3.UploadDataListener;
import com.example.demo.service.BasicService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@RestController
public class ImportController {

    private final BasicService basicService;

    public ImportController(BasicService basicService){
        this.basicService = basicService;
    }


    //导入excel
    @RequestMapping(value = "excelImport", method = {RequestMethod.GET, RequestMethod.POST })
    public String excelImport(HttpServletRequest request,MultipartFile[] files) throws Exception {
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
    @RequestMapping(value = "/importBasic")
    public String importBasic(MultipartFile file) throws IOException {
//        BigDateExcelListener basicExcelListener=new BigDateExcelListener(basicService);
        UploadDataListener basicExcelListener = new UploadDataListener();
        InputStream input = new FileInputStream(new File("d://test22.xls"));
//        EasyExcel.read(file.getInputStream(), TestModel.class, basicExcelListener).headRowNumber(1).sheet().doRead();
//        EasyExcel.read(input, HashMap.class, basicExcelListener).headRowNumber(0).sheet().doRead();
//        EasyExcel.read(input,com.example.demo.complexImport.example3.TestModel.class, basicExcelListener).extraRead(CellExtraTypeEnum.MERGE).sheet("表-09 综合单价分析表").headRowNumber(0).doReadSync();
        EasyExcel.read(input,com.example.demo.complexImport.example3.TestModel.class, basicExcelListener).sheet("表-09 综合单价分析表").headRowNumber(0).doReadSync();

        input.close();
        return "导入成功";
    }

}
