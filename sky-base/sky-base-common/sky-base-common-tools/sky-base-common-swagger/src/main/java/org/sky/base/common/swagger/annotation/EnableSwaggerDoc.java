package org.sky.base.common.swagger.annotation;

import org.sky.base.common.swagger.config.SwaggerAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>
 * 决定开启swagger文档注解
 * </p>
 *
 * @author liusongling
 * @since 2023-07-15 17:07:59
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({ SwaggerAutoConfiguration.class })
public @interface EnableSwaggerDoc {
}
