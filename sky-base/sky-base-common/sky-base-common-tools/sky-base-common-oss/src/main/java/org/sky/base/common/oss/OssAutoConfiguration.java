package org.sky.base.common.oss;

import lombok.AllArgsConstructor;
import org.sky.base.common.oss.http.OssEndPoint;
import org.sky.base.common.oss.service.S3OssClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * <p>
 * aws 自动配置
 * </p>
 *
 * @author liusongling
 * @since 2023-07-11 11:28:04
 */
@AllArgsConstructor
@EnableConfigurationProperties({OssProperties.class})
public class OssAutoConfiguration {
    private final OssProperties ossProperties;

    /**
     * s3 oss客户端服务
     *
     * @return {@link S3OssClient}
     */
    @Bean
    @ConditionalOnMissingBean(S3OssClient.class)
    @ConditionalOnProperty(name = "oss.enable", havingValue = "true", matchIfMissing = true)
    public S3OssClient s3OssClient() {
        return new S3OssClient(ossProperties);
    }

    /**
     * oss http端点
     *
     * @param s3OssClient s3 oss客户端服务
     * @return {@link OssEndPoint}
     */
    @Bean
    @ConditionalOnProperty(name = "oss.http", havingValue = "true")
    public OssEndPoint ossEndPoint(S3OssClient s3OssClient) {
        return new OssEndPoint(s3OssClient);
    }
}
