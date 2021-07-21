package com.example.demo.aop;


import com.example.demo.annotation.EnableAop;
import com.example.demo.domain.response.InternalResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.javassist.*;
import org.apache.ibatis.javassist.bytecode.CodeAttribute;
import org.apache.ibatis.javassist.bytecode.LocalVariableAttribute;
import org.apache.ibatis.javassist.bytecode.MethodInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


@Component
@Aspect
@Slf4j
public class ControllerAccessLogAspect {

//    @Autowired
//    private RedisCache redisCache;

    @Pointcut("@annotation(com.example.demo.annotation.EnableAop)")
    public void controllerAccessLog() {
    }

    @Around("controllerAccessLog() && @annotation(deploy)")
    public Object doAround(ProceedingJoinPoint jp, EnableAop deploy) throws Throwable {
        long startTime = System.currentTimeMillis();
        String className = jp.getTarget().getClass().getName();
        String simpleName = jp.getTarget().getClass().getSimpleName();
        String methodName = jp.getSignature().getName();
        String[] paramNames = getFieldsName(className, methodName);
//        UserInfoRes userInfo = LocalThreadUserUtils.getCurUserInfo();
//        String userName = userInfo == null ? "游客" : userInfo.getUserName();
        String userName = "游客";
        if (deploy.enableLog())
            log.info("用户【{}】调用【{}】的【{}】方法开始.参数=【{}】", userName, simpleName, methodName, logParam(paramNames, jp.getArgs()));
        if (deploy.enableExProcess()) {
            InternalResponse internalResponse = null;
            try {
                internalResponse = (InternalResponse) jp.proceed(jp.getArgs());
            } catch (InternalException ie) {
                internalResponse = ie.getParams() == null ? InternalResponse.fail(ie.getErrorCode()) :
                        InternalResponse.fail(ie.getErrorCode(), ie.getParams());
            } catch (Throwable e) {
                log.info("用户【{}】使用参数=【{}】调用【{}】的【{}】发生异常【{}】", userName, logParam(paramNames, jp.getArgs()), simpleName, methodName, e.getMessage(), e);
                internalResponse = InternalResponse.fail();
            } finally {
                if (deploy.enableLog())
                    log.info("用户【{}】调用【{}】的【{}】方法结束.用时【{}】毫秒,返回值=【{}】", userName, simpleName, methodName, System.currentTimeMillis() - startTime, new ObjectMapper().writeValueAsString(internalResponse));
            }
            return internalResponse;
        } else {
            Object result = jp.proceed(jp.getArgs());
            if (deploy.enableLog())
                log.info("用户【{}】调用【{}】的【{}】方法结束.用时【{}】毫秒,返回值=【{}】", userName, simpleName, methodName, System.currentTimeMillis() - startTime, new ObjectMapper().writeValueAsString(result));
            return result;
        }
    }

    /**
     * 使用javassist来获取方法参数名称
     *
     * @param class_name  类名
     * @param method_name 方法名
     * @return 参数数组
     * @throws Exception exception
     */
    private String[] getFieldsName(String class_name, String method_name) throws Exception {
        Class<?> clazz = Class.forName(class_name);
        String clazz_name = clazz.getName();
        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(clazz);
        pool.insertClassPath(classPath);

        CtClass ctClass = pool.get(clazz_name);
        CtMethod ctMethod = ctClass.getDeclaredMethod(method_name);
        MethodInfo methodInfo = ctMethod.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
            return null;
        }
        String[] paramsArgsName = new String[ctMethod.getParameterTypes().length];
        int pos = Modifier.isStatic(ctMethod.getModifiers()) ? 0 : 1;
        for (int i = 0; i < attr.tableLength(); i++) {
            if (attr.index(i) >= pos && attr.index(i) < paramsArgsName.length + pos)
                paramsArgsName[attr.index(i) - pos] = attr.variableName(i);
        }
        return paramsArgsName;
    }

    /**
     * 获取方法参数值  基本类型直接打印，非基本类型需要重写toString方法
     *
     * @param paramsArgsName  方法参数名数组
     * @param paramsArgsValue 方法参数值数组
     */
    private String logParam(String[] paramsArgsName, Object[] paramsArgsValue) {
        StringBuffer params = new StringBuffer();
        if (ArrayUtils.isEmpty(paramsArgsName) || ArrayUtils.isEmpty(paramsArgsValue)) {
            params.append("该方法没有参数");
        } else {
            for (int i = 0; i < paramsArgsName.length; i++) {
                //参数名
                String name = paramsArgsName[i];
                //参数值
                Object value = paramsArgsValue.length > i ? paramsArgsValue[i] : null;
                params.append(name + " = ");
                params.append(value + "  ,");
            }
        }
        return params.toString();
    }

    //先预留，到时需ip再用
    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /*public UserExtendVO currUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        UserExtendVO user = redisCache.getValue(request.getHeader("token"), UserExtendVO.class);
        return user;
    }*/

}
