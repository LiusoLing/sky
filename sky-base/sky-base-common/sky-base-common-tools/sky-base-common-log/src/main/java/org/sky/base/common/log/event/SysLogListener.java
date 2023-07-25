package org.sky.base.common.log.event;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sky.admin.api.domain.log.SysLog;
import org.sky.admin.api.feign.RemoteLogApi;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

/**
 * <p>
 * 异步监听系统日志事件，保存日志
 * </p>
 *
 * @author liusongling
 * @since 2023-07-25 09:27:38
 */
@Slf4j
@AllArgsConstructor
public class SysLogListener {

    private final RemoteLogApi remoteLogApi;

    @Async
    @Order
    @EventListener(SysLogEvent.class)
    public void asyncSaveLog(SysLogEvent sysLogEvent) {
        SysLog sysLog = sysLogEvent.getSysLog();
        remoteLogApi.saveLog(sysLog);
    }
}
