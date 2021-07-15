package com.example.demo.complexImport;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.CellExtraTypeEnum;

import com.example.demo.complexImport.example3.UploadDataListener;
import com.example.demo.complexImport.example3.util.ExcelModel;
import com.example.demo.domain.response.InternalResponse;
import com.example.demo.service.BasicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Api(tags = {"测试easyExcel"})
@RestController
public class ImportController {

    private final BasicService basicService;

    public ImportController(BasicService basicService){
        this.basicService = basicService;
    }



    /**
     *
     * @param
     * @return
     * @throws IOException
     */
    @ApiOperation("2-测试easyExcel")
    @PostMapping(value = "/importBasic")
//    public InternalResponse importBasic() throws IOException {
    public InternalResponse importBasic(@ApiParam("上传文件") MultipartFile file, HttpServletRequest request) throws IOException {
        Map<String,Object> result = new HashMap<>();

//        UploadDataListener basicExcelListener = new UploadDataListener(0);
//        EasyExcel.read(file.getInputStream(), ExcelModel.class, basicExcelListener).extraRead(CellExtraTypeEnum.MERGE).ignoreEmptyRow(true).headRowNumber(0).doReadAll();
//        Map<String,Object> map = basicExcelListener.allAnalysisAndVerification();
//        result.put("message","导入成功");
//        result.put("code","200");
//        result.put("data",map);
        List<File> list = operationZip(file.getInputStream());

        if(list != null && list.size()>0){
            for(File f : list){
                System.out.println(f.getName());
                f.delete();
            }
        }
        return InternalResponse.success().withBody(result);

    }

    /**
     *   stream 压缩包流 --> List<File>
     * @param stream 压缩包流
     * @return 压缩包内fileList
     */
    private List<File> operationZip(InputStream stream){
        ZipInputStream zis = null;
        List<File> fileList = new ArrayList<>();
        try {
            zis = new ZipInputStream(stream);
            ZipEntry ze;
            while ((ze = zis.getNextEntry()) != null) {
                File tempFile = new File(ze.getName());
                inputstreamtofile(zis, tempFile);
                fileList.add(tempFile);
            }
            return fileList;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(zis != null){
                try {
                    zis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileList;
    }
    /**
     *InputStream --> file
     * @param ins
     * @param file
     */
    private void inputstreamtofile(InputStream ins,File file){
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = ins.read(buffer, 0, 1024)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(os!=null){
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
