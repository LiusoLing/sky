package org.sky.admin.api.infrastructure.enums.log;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 日志分类枚举
 * </p>
 *
 * @author liusongling
 * @since 2023-07-13 17:14:26
 */
@Getter
@AllArgsConstructor
public enum LogCategory {
    /**
     * 操作日志：OPERATE
     */
    OPERATE("OPERATE", "操作日志"),
    /**
     * 异常日志：EXCEPTION
     */
    EXCEPTION("EXCEPTION", "异常日志"),
    /**
     * 登录日志：LOGIN
     */
    LOGIN("LOGIN", "登录日志"),
    /**
     * 登出日志：LOGOUT
     */
    LOGOUT("LOGOUT", "登出日志");

    private final String code;
    private final String value;
}
