package org.sky.base.common.log.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.sky.admin.api.domain.log.SysLog;

/**
 * <p>
 * 系统日志事件
 * </p>
 *
 * @author liusongling
 * @since 2023-07-25 09:27:25
 */
@Getter
@AllArgsConstructor
public class SysLogEvent {
    /**
     * 系统日志
     */
    private final SysLog sysLog;
}
