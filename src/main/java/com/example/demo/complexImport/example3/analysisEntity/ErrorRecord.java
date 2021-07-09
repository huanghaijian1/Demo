package com.example.demo.complexImport.example3.analysisEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 导入错误信息实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorRecord {
    private String errorFieldName;
    private String message;

    public ErrorRecord(String message){
         this.message = message;
    }
}
