package org.sky.base.common.log.annotation;

import org.sky.admin.api.infrastructure.enums.log.LogCategory;

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
@Inherited
public @interface BizLog {

    /**
     * 日志操作名称，如："创建账号"
     *
     * @return {@link String}
     */
    String value() default "未知";

    /**
     * 系统日志类型
     *
     * @return {@link LogCategory}
     */
    LogCategory category();
}
