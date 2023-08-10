package org.sky.base.common.sequence.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 发号器redis配置属性
 * </p>
 *
 * @author liusongling
 * @since 2023-08-10 21:34:10
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Component
@ConfigurationProperties(prefix = "sky.sequence.redis")
public class SequenceRedisProperties extends BaseSequenceProperties {

}
