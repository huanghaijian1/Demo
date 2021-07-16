package com.example.demo.domain;

import com.example.demo.complexImport.example3.util.ImportCheckUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * 单位工程招标控制价汇总表
 */
@Data
public class TenderControlPrice {

    //序号
    private String serialNum;

    //汇总内容
    private String content;

    //金额
    private String amount;

    //备注
    private String remarks;

    //数据错误信息
    private String errorMessage;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof TenderControlPrice) {
            TenderControlPrice s = (TenderControlPrice) obj;
            if(StringUtils.equals(content,s.getContent())){
//                String a = ImportCheckUtil.checkNumber(amount) ? amount : "0";
//                String b = ImportCheckUtil.checkNumber(s.getAmount()) ? s.getAmount() : "0";
//                s.setAmount(String.valueOf(new BigDecimal(a).add(new BigDecimal(b))));
                s.setErrorMessage("同一excel文档中出现多个“"+content+"”数据（已获取最前面的那条数据作为标准）");
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (content == null ? 0 : content.hashCode());
        return result;
    }

}
