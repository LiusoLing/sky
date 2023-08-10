package org.sky.base.common.sequence.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 发号器DB配置属性
 * </p>
 *
 * @author liusongling
 * @since 2023-08-10 21:30:48
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Component
@ConfigurationProperties(prefix = "sky.sequence.db")
public class SequenceDbProperties extends BaseSequenceProperties {
    /**
     * 表名称
     */
    private String tableName = "sky_sequence";

    /**
     * 重试次数
     */
    private int retryTimes = 1;
}
