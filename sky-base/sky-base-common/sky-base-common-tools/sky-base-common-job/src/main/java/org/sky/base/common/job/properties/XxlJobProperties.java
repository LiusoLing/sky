package org.sky.base.common.job.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>
 * xxl-job 属性
 * </p>
 *
 * @author liusongling
 * @since 2023-07-26 09:22:58
 */
@Data
@Component
@ConfigurationProperties(prefix = "xxl.job")
public class XxlJobProperties {
    /**
     * xxl-job 控制台
     */
    private XxlJobAdminProperties admin = new XxlJobAdminProperties();
    /**
     * xxl-job 执行器
     */
    private XxlJobExecutorProperties executor = new XxlJobExecutorProperties();
}
