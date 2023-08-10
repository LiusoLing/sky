package org.sky.base.common.sequence;

import org.sky.base.common.sequence.builder.SnowflakeSequenceBuilder;
import org.sky.base.common.sequence.properties.SequenceSnowflakeProperties;
import org.sky.base.common.sequence.sequence.Sequence;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * 序列号生成器 自动配置
 * </p>
 *
 * @author liusongling
 * @since 2023-07-27 09:26:45
 */
@Configuration
@ComponentScan("org.sky.base.common.sequence")
@ConditionalOnMissingBean(Sequence.class)
public class SequenceAutoConfiguration {

    /**
     * snowflake 算法作为发号器实现
     *
     * @param properties 属性
     * @return {@link Sequence}
     */
    @Bean
    @ConditionalOnBean(SequenceSnowflakeProperties.class)
    public Sequence snowflakeSequence(SequenceSnowflakeProperties properties) {
        return SnowflakeSequenceBuilder.create()
                .datacenterId(properties.getDatacenterId())
                .workerId(properties.getWorkerId())
                .build();
    }
}
