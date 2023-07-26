package org.sky.base.common.job.properties;

import lombok.Data;

/**
 * <p>
 * xxl-job 执行器属性
 * </p>
 *
 * @author liusongling
 * @since 2023-07-26 09:22:32
 */
@Data
public class XxlJobExecutorProperties {
    /**
     * 执行器appName [选填]，执行器心跳注册分组依据，为空自动关闭注册
     */
    private String appname = "xxl-job-executor";
    /**
     * 服务注册地址，优先使用该配置作为注册地址，为空时使用内嵌服务 "IP:PORT" 作为注册地址，灵活支持容器类型执行器动态IP和动态映射端口问题
     */
    private String address;
    /**
     * 执行器IP [选填]，默认为空自动获取IP，多网卡时可手动设置指定IP，该IP不会绑定host，仅作为通讯使用，地址信息用于 "执行器注册" 和
     * "调度中心请求并触发任务"
     */
    private String ip;
    /**
     * 执行器端口号 [选填]，小于等于0则自动获取，默认端口 9999，单机部署多个执行器时，注意配置不同的执行器端口
     */
    private Integer port = 0;
    /**
     * 执行器通讯TOKEN [选填]，非空时启用
     */
    private String accessToken;
    /**
     * 执行器运行日志存储磁盘路径 [选填]，需对该路径有读写权限，为空使用默认路径
     */
    private String logPath = "logs/applogs/xxl-job/jobhandler";
    /**
     * 执行器日志保存天数 [选填]，值大于3时生效，启动执行器日志文件定期清理功能，否则无效
     */
    private Integer logRetentionDays = 30;
}
