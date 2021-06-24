package com.example.demo.domain.response;

/**
 * @description: 返回的错误码code常量类
 * @author: linyeju
 * @date: 2021年04月26日 14:15
 */
public class ResCodeConstant {
    /**
     * 成功对应的code
     */
    public final static String SUC_CODE = "01";
    /**
     * 失败对应的code
     */
    public final static String FAIL_CODE = "02";
    /**
     * 不存在的 异常code
     */
    public final static String ERROR_CODE_201 = "201";
    /**
     * 已存在的 异常code
     */
    public final static String ERROR_CODE_202 = "202";
    /**
     * 目录创建失败
     */
    public final static String ERROR_CODE_203 = "203";
    //----------------材价库编码------------------------------------------
    /**
     * 价格不可小于等于0
     */
    public final static String ERROR_CODE_301 = "301";
    /**
     * 编辑失败，不符合标准化规则
     */
    public final static String ERROR_CODE_501 = "501";
    /**
     * 已存在编辑之后的材价信息！
     */
    public final static String ERROR_CODE_502 = "502";
    //----------------分享库编码------------------------------------------

    /**
     * 审核中不可删除
     */
    public final static String ERROR_CODE_601 = "601";
    /**
     * 已分享不可删除
     */
    public final static String ERROR_CODE_602 = "602";
    /**
     * 该状态不可撤回
     */
    public final static String ERROR_CODE_603 = "603";
    /**
     * 该状态不可提交审核
     */
    public final static String ERROR_CODE_604 = "604";
    /**
     * 该状态不可审核
     */
    public final static String ERROR_CODE_605 = "605";
    /**
     * 存在审核中或已分享的材料，操作失败
     */
    public final static String ERROR_CODE_606 = "606";
    /**
     * 分享的材价存在内容不全材价，请补充完毕后再提交。
     */
    public final static String ERROR_CODE_607 = "607";
    /**
     * 该分享材料已存在
     */
    public final static String ERROR_CODE_608 = "608";
    /**
     * 分享到共享平台异常，审核通过失败！
     */
    public final static String ERROR_CODE_609 = "609";

    //----------------用户相关编码------------------------------------------
    /**
     * 验证码错误
     */
    public final static String ERROR_CODE_701 = "701";
    /**
     * 账号或密码错误
     */
    public final static String ERROR_CODE_702 = "702";
    /**
     * 账号密码错误10次，已被锁定。【】秒后可再试
     */
    public final static String ERROR_CODE_703 = "703";
    /**
     * 登录失效，请重新登录
     */
    public final static String ERROR_CODE_704 = "704";
    /**
     * 没有权限
     */
    public final static String ERROR_CODE_705 = "705";
    /**
     * 该账号已存在
     */
    public final static String ERROR_CODE_706 = "706";
    /**
     * 该角色已存在
     */
    public final static String ERROR_CODE_707 = "707";
    /**
     * 旧密码错误
     */
    public final static String ERROR_CODE_708 = "708";
    /**
     * 账号或姓名不能为空
     */
    public final static String ERROR_CODE_709 = "709";
    /**
     * 角色名称不能为空
     */
    public final static String ERROR_CODE_710 = "710";
    /**
     * 需绑定角色
     */
    public final static String ERROR_CODE_711 = "711";
    /**
     * 账号由6-12位数字或英文组成！
     */
    public final static String ERROR_CODE_712 = "712";
    /**
     * 旧密码不能为空！
     */
    public final static String ERROR_CODE_713 = "713";
    /**
     * 新密码不能为空！
     */
    public final static String ERROR_CODE_714 = "714";

    //----------------系统通用相关编码---------------------------------------
    /**
     * 关联失败，存在二级分类时一级分类不可关联
     */
    public final static String ERROR_CODE_801 = "801";
    /**
     * 关联失败，国标分类【%s】已被关联，不可重复关联
     */
    public final static String ERROR_CODE_802 = "802";
    /**
     * 系统默认项不可操作
     */
    public final static String ERROR_CODE_803 = "803";
    /**
     * 找不到文件下载失败
     */
    public final static String ERROR_CODE_804 = "804";
    /**
     * 该来源存在材价，请删除对应材价才可删除！
     */
    public final static String ERROR_CODE_805 = "805";
    //---------------造价通绑定登录相关-------------------------------------------------------
    /**
     * 账号绑定失败异常
     */
    public final static String ERROR_CODE_1001 = "1001";

    /**
     * 获取微信二维码异常
     */
    public final static String ERROR_CODE_1002 = "1002";

    /**
     * 微信绑定异常
     */
    public final static String ERROR_CODE_1003 = "1003";


    /**
     * 重复设置默认账号
     */
    public final static String ERROR_CODE_1004 = "1004";


    /**
     * 获取手机验证码异常
     */
    public final static String ERROR_CODE_1005 = "1005";
}
