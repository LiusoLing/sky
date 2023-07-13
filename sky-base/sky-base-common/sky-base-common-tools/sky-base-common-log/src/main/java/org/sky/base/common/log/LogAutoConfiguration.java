package org.sky.base.common.log;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * <p>
 * log 日志自动配置
 * </p>
 *
 * @author liusongling
 * @since 2023-07-11 16:27:29
 */
@EnableAsync
@Configuration
@AllArgsConstructor
@ConditionalOnWebApplication
public class LogAutoConfiguration {


}
