package org.sky.admin.api.infrastructure.enums.log;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 日志-执行状态枚举
 * </p>
 *
 * @author liusongling
 * @since 2023-07-13 17:14:57
 */
@Getter
@AllArgsConstructor
public enum LogStatus {
    /**
     * 成功：SUCCESS
     */
    SUCCESS("SUCCESS", "成功"),

    /**
     * 失败：FAIL
     */
    FAIL("FAIL", "失败");

    private final String code;
    private final String value;
}
