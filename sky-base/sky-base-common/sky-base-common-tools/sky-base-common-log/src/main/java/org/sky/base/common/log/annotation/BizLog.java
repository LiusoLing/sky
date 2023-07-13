package org.sky.base.common.log.annotation;

import java.lang.annotation.*;

/**
 * <p>
 * 自定义操作日志注解
 * </p>
 *
 * @author liusongling
 * @since 2023-07-13 14:18:13
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BizLog {

    /**
     * 操作日志名称，如："创建账号"
     *
     * @return {@link String}
     */
    String value() default "未知";
}
