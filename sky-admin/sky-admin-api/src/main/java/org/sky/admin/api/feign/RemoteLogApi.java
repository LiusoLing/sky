package org.sky.admin.api.feign;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sky.admin.api.domain.log.SysLog;
import org.sky.base.common.core.model.JsonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 * 系统日志API
 * </p>
 *
 * @author liusongling
 * @since 2023-07-25 09:30:33
 */
@Tag(name = "系统日志API", description = "RemoteLogApi 系统日志API，由其他模块实现")
@ApiResponses(@ApiResponse(responseCode = "200", description = "API请求成功"))
@FeignClient(contextId = "remoteLogApi", value = "sky-admin-api")
public interface RemoteLogApi {

    /**
     * 保存日志
     *
     * @param sysLog 系统日志
     * @return {@link JsonResponse}<{@link Boolean}>
     */
    @Operation(summary = "日志保存", description = "保存系统日志")
    @PostMapping(value = "/log/save", produces = "application/json")
    JsonResponse<Boolean> saveLog(@RequestBody SysLog sysLog);
}
