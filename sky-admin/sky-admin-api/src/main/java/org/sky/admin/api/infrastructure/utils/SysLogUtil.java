package org.sky.admin.api.infrastructure.utils;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.experimental.UtilityClass;
import org.sky.admin.api.domain.log.SysLog;
import org.sky.admin.api.domain.user.BaseLoginUser;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * <p>
 * 系统日志工具类
 * </p>
 *
 * @author liusongling
 * @since 2023-07-25 09:44:16
 */
@UtilityClass
public class SysLogUtil {

    /**
     * 获取系统日志
     *
     * @return {@link SysLog}
     */
    public SysLog getSysLog() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        return getBaseLog(request);
    }

    /**
     * 获取基本系统日志
     *
     * @param request 请求
     * @return {@link SysLog}
     */
    private SysLog getBaseLog(HttpServletRequest request) {
        String username = "未知";
        String loginId;
        try {
            loginId = StpUtil.getLoginIdAsString();
            if (ObjectUtil.isEmpty(loginId)) {
                loginId = "-1";
            }
            BaseLoginUser loginUser = StpLoginUserUtil.getLoginUser();
            if (ObjectUtil.isNotNull(loginUser)) {
                username = loginUser.getNickname();
            }
        } catch (Exception e) {
            loginId = "-1";
        }
        SysLog sysLog = new SysLog();
        String ip = CommonServletUtil.getIp(request);
        sysLog.setOpIp(ip);
        sysLog.setOpAddr(CommonServletUtil.getRegion(ip));
        sysLog.setOpBrowser(CommonServletUtil.getBrowser(request));
        sysLog.setOpOs(CommonServletUtil.getOs(request));
        sysLog.setUserAgent(CommonServletUtil.getParamFromRequest("user-agent"));
        sysLog.setReqMethod(request.getMethod());
        sysLog.setCreateBy(Long.valueOf(loginId));
        sysLog.setOpUser(username);
        return sysLog;
    }
}
