package org.sky.base.common.job;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.sky.base.common.job.properties.XxlJobProperties;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * xxl-job 自动配置
 * </p>
 *
 * @author liusongling
 * @since 2023-07-26 09:11:01
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan("org.sky.base.common.job.properties")
public class XxlJobAutoConfiguration {

    /**
     * xxl-job Spring 执行器
     *
     * @param xxlJobProperties xxl-job 属性
     * @return {@link XxlJobSpringExecutor}
     */
    @Bean
    public XxlJobSpringExecutor xxlJobSpringExecutor(XxlJobProperties xxlJobProperties) {
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(xxlJobProperties.getAdmin().getAddress());
        xxlJobSpringExecutor.setAppname(xxlJobProperties.getExecutor().getAppname());
        xxlJobSpringExecutor.setAddress(xxlJobProperties.getExecutor().getAddress());
        xxlJobSpringExecutor.setIp(xxlJobProperties.getExecutor().getIp());
        xxlJobSpringExecutor.setPort(xxlJobProperties.getExecutor().getPort());
        xxlJobSpringExecutor.setAccessToken(xxlJobProperties.getExecutor().getAccessToken());
        xxlJobSpringExecutor.setLogPath(xxlJobProperties.getExecutor().getLogPath());
        xxlJobSpringExecutor.setLogRetentionDays(xxlJobProperties.getExecutor().getLogRetentionDays());

        return xxlJobSpringExecutor;
    }
}
