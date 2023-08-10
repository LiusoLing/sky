package org.sky.base.common.sequence.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>
 * snowflake 发号器属性
 * </p>
 *
 * @author liusongling
 * @since 2023-08-10 21:36:45
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Component
@ConfigurationProperties(prefix = "sky.sequence.snowflake")
public class SequenceSnowflakeProperties extends BaseSequenceProperties {

    /**
     * 数据中心ID，值的范围在[0,31]之间，一般可以设置机房的IDC[必选]
     */
    private long datacenterId;

    /**
     * 工作机器ID，值的范围在[0,31]之间，一般可以设置机器编号[必选]
     */
    private long workerId;
}
