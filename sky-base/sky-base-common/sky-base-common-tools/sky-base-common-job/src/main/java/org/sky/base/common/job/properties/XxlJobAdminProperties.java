package org.sky.base.common.job.properties;

import lombok.Data;

/**
 * <p>
 * xxl-job 控制台属性
 * </p>
 *
 * @author liusongling
 * @since 2023-07-26 09:21:44
 */
@Data
public class XxlJobAdminProperties {
    /**
     * 调度中心部署根地址[选填]，调度中心集群部署在多个地址用逗号分隔，执行器将会使用该地址进行 "执行器心跳注册" 和 "任务结果回调"，
     * 为空则关闭自动注册
     */
    private String address = "http://127.0.0.1:8080/xxl-job-admin";
}
