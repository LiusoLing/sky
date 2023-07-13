package org.sky.admin.api.domain;


import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 日志表
 * </p>
 *
 * @author liusongling
 * @since 2023-07-13 14:51:22
 */
@Data
@TableName("sys_log")
public class SysLog implements Serializable {
    private static final long serialVersionUID = 4840972184631954991L;

    /**
     * 主键ID
     */
    private Long id;
    /**
     * 日志类型
     */
    private String type;
    /**
     * 日志标题
     */
    private String title;
    /**
     * 创建人ID
     */
    private Long createBy;
    /**
     * 创建人
     */
    private String creatorName;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 操作IP地址
     */
    private String remoteAddr;
    /**
     * 用户代理
     */
    private String userAgent;
    /**
     * 请求URI
     */
    private String requestUri;
    /**
     * 操作方式
     */
    private String method;
    /**
     * 操作提交数据
     */
    private String params;
    /**
     * 操作耗时
     */
    private Long time;
    /**
     * 异常信息
     */
    private String exception;
    /**
     * 服务ID
     */
    private String serviceId;
    /**
     * 是否删除，0否1是
     */
    @TableLogic
    private Integer isDelete;
}
