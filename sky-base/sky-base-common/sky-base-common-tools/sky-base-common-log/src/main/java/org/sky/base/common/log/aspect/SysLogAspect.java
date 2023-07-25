package org.sky.base.common.log.aspect;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.sky.admin.api.domain.log.SysLog;
import org.sky.admin.api.infrastructure.enums.log.LogCategory;
import org.sky.admin.api.infrastructure.enums.log.LogStatus;
import org.sky.admin.api.infrastructure.utils.CommonServletUtil;
import org.sky.admin.api.infrastructure.utils.SysLogUtil;
import org.sky.base.common.log.annotation.BizLog;
import org.sky.base.common.log.event.SysLogEvent;
import org.slf4j.MDC;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * <p>
 * 日志切面
 * </p>
 *
 * @author liusongling
 * @since 2023-07-13 14:40:21
 */
@Slf4j
@Aspect
@RequiredArgsConstructor
public class SysLogAspect {
    private final ApplicationEventPublisher applicationEventPublisher;
    private static final ThreadLocal<Long> startTime = new ThreadLocal<>();
    private static final String REQUEST_ID_HEADER = "X-Web-Request-Id";
    private static final String REQUEST_ID = "requestId";

    /**
     * 切面前置处理
     *
     * @param bizLog 业务日志
     */
    @Before("@annotation(bizLog)")
    public void before(BizLog bizLog) {
        startTime.set(System.currentTimeMillis());
        HttpServletRequest request = CommonServletUtil.getRequest();
        String requestId = request.getHeader(REQUEST_ID_HEADER);
        if (requestId == null || requestId.length() == 0) {
            requestId = IdUtil.objectId();
        }
        MDC.put(REQUEST_ID, requestId);
        log.info("\\n =======> 请求路径: {}", request.getRequestURI());
    }

    /**
     * 切面后置处理
     *
     * @param point  点
     * @param result 结果
     */
    @AfterReturning(pointcut = "@annotation(org.sky.base.common.log.annotation.BizLog)", returning = "result")
    public void doAfterReturning(JoinPoint point, Object result) {
        String className = point.getTarget().getClass().getName();
        String methodName = point.getSignature().getName();
        log.debug("[类名]：{}, [方法]：{}", className, methodName);

        //获取@BizLog注解内容
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        if (!methodSignature.getMethod().isAnnotationPresent(BizLog.class)) {
            log.error(">>> 获取注解@BizLog失败");
            return;
        }
        BizLog bizLog = methodSignature.getMethod().getAnnotation(BizLog.class);
        Long start = startTime.get();
        SysLog sysLog = SysLogUtil.getSysLog();
        sysLog.setTitle(bizLog.value());
        sysLog.setCategory(bizLog.category());
        sysLog.setExeStatus(LogStatus.SUCCESS);
        sysLog.setCreateTime(LocalDateTimeUtil.of(start));
        sysLog.setOpTime(System.currentTimeMillis() - start);
        sysLog.setClassName(className);
        sysLog.setMethodName(methodName);
        sysLog.setParamJson(getJoinPointJsonArgs(point));
        sysLog.setResultJson(JSONUtil.toJsonStr(result));
        sysLog.setSignData(MDC.get(REQUEST_ID));

        applicationEventPublisher.publishEvent(new SysLogEvent(sysLog));
        MDC.clear();
    }

    /**
     * 切面后置异常处理
     *
     * @param point     点
     * @param exception 异常
     */
    @AfterThrowing(pointcut = "@annotation(org.sky.base.common.log.annotation.BizLog)", throwing = "exception")
    public void doAfterThrowing(JoinPoint point, Exception exception) {
        String className = point.getTarget().getClass().getName();
        String methodName = point.getSignature().getName();
        log.debug("[类名]：{}, [方法]：{}", className, methodName);

        //获取@BizLog注解内容
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        if (!methodSignature.getMethod().isAnnotationPresent(BizLog.class)) {
            log.error(">>> 获取注解@BizLog失败");
            return;
        }
        BizLog bizLog = methodSignature.getMethod().getAnnotation(BizLog.class);
        Long start = startTime.get();
        SysLog sysLog = SysLogUtil.getSysLog();
        sysLog.setTitle(bizLog.value());
        sysLog.setCategory(LogCategory.EXCEPTION);
        sysLog.setExeStatus(LogStatus.FAIL);
        sysLog.setCreateTime(LocalDateTimeUtil.of(start));
        sysLog.setOpTime(System.currentTimeMillis() - start);
        sysLog.setClassName(className);
        sysLog.setMethodName(methodName);
        sysLog.setParamJson(getJoinPointJsonArgs(point));
        sysLog.setResultJson(ExceptionUtil.stacktraceToString(exception));
        sysLog.setSignData(MDC.get(REQUEST_ID));

        applicationEventPublisher.publishEvent(new SysLogEvent(sysLog));
        MDC.clear();
    }

    /**
     * 获取切点的 json args 参数
     *
     * @param joinPoint 连接点
     * @return {@link String}
     */
    private String getJoinPointJsonArgs(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        //参数名数组
        String[] parameterNames = ((MethodSignature) signature).getParameterNames();
        HashMap<String, Object> map = MapUtil.newHashMap();
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (ObjectUtil.isNotEmpty(args[i]) && isUserFulParam(args[i])) {
                if (JSONUtil.isTypeJSON(StrUtil.toString(args[i]))) {
                    map.put(parameterNames[i], JSONUtil.parseObj(args[i]));
                } else {
                    map.put(parameterNames[i], JSONUtil.toJsonStr(args[i]));
                }
            }
        }
        return JSONUtil.toJsonStr(map);
    }

    /**
     * 判断是否为需要拼接的参数，过滤 MultipartFile 、HttpServletRequest 和  HttpServletResponse
     *
     * @param arg 参数
     * @return boolean
     */
    private boolean isUserFulParam(Object arg) {
        return !(arg instanceof MultipartFile) &&
                !(arg instanceof HttpServletRequest) &&
                !(arg instanceof HttpServletResponse);
    }
}
