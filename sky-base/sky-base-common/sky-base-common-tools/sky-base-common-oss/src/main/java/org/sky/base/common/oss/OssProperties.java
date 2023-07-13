package org.sky.base.common.oss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * <p>
 * aws 配置属性
 * </p>
 *
 * @author liusongling
 * @since 2023-07-07 16:53:56
 */
@Data
@ConfigurationProperties(prefix = "oss")
public class OssProperties implements Serializable {
    private static final long serialVersionUID = -4220321765003270834L;
    /**
     * 访问站点
     */
    private String endPoint;
    /**
     * 自定义域名
     */
    private String domain;
    /**
     * true path-style nginx 反向代理和S3默认支持 pathStyle {http://endpoint/bucketname} false
     * supports virtual-hosted-style 阿里云等需要配置为 virtual-hosted-style
     * 模式{http://bucketname.endpoint}
     */
    private Boolean pathStyleAccess = true;
    /**
     * 前缀
     */
    private String prefix;
    /**
     * ACCESS_KEY
     */
    private String accessKey;
    /**
     * SECRET_KEY
     */
    private String secretKey;
    /**
     * 存储空间名
     */
    private String bucketName;
    /**
     * 存储区域
     */
    private String region;
    /**
     * 最大线程数，默认： 100
     */
    private Integer maxConnections = 100;
}
