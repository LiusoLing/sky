package org.sky.base.common.log.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.sky.base.common.log.annotation.BizLog;
import org.springframework.context.ApplicationEventPublisher;

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

    @Around("@annotation(bizLog)")
    public Object around(ProceedingJoinPoint point, BizLog bizLog) {
        String className = point.getTarget().getClass().getName();
        String methodName = point.getSignature().getName();
        log.debug("[类名]：{}, [方法]：{}", className, methodName);

        return null;
    }
}
