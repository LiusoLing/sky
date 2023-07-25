package org.sky.admin.api.domain.log;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.sky.admin.api.infrastructure.enums.log.LogCategory;
import org.sky.admin.api.infrastructure.enums.log.LogStatus;

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
     * 日志分类
     */
    private LogCategory category;
    /**
     * 日志标题
     */
    private String title;
    /**
     * 执行状态
     */
    private LogStatus exeStatus;
    /**
     * 操作IP
     */
    private String opIp;
    /**
     * 操作地址
     */
    private String opAddr;
    /**
     * 操作浏览器
     */
    private String opBrowser;
    /**
     * 操作系统
     */
    private String opOs;
    /**
     * 用户代理
     */
    private String userAgent;
    /**
     * 类名称
     */
    private String className;
    /**
     * 方法名称
     */
    private String methodName;
    /**
     * 请求方式
     */
    private String reqMethod;
    /**
     * 请求URL
     */
    private String reqUrl;
    /**
     * 操作提交数据
     */
    private String paramJson;
    /**
     * 返回结果
     */
    private String resultJson;
    /**
     * 异常信息
     */
    private String exception;
    /**
     * 操作耗时
     */
    private Long opTime;
    /**
     * 签名数据
     */
    private String signData;
    /**
     * 操作人员
     */
    private String opUser;
    /**
     * 创建人ID
     */
    private Long createBy;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
