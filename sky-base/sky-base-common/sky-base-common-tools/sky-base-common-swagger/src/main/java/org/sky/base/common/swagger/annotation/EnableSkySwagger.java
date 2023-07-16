package org.sky.base.common.swagger.annotation;

import org.sky.base.common.swagger.config.SwaggerAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>
 *
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
public @interface EnableSkySwagger {
}
