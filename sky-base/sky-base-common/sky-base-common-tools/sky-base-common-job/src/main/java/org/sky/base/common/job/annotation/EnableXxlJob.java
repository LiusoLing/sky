package org.sky.base.common.job.annotation;

import org.sky.base.common.job.XxlJobAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>
 * 开启 xxl-job 支持注解
 * </p>
 *
 * @author liusongling
 * @since 2023-07-26 09:12:14
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({XxlJobAutoConfiguration.class})
public @interface EnableXxlJob {

}
