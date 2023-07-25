package org.sky.admin.api.infrastructure.utils;

import cn.dev33.satoken.stp.StpUtil;
import lombok.experimental.UtilityClass;
import org.sky.admin.api.domain.user.BaseLoginUser;

/**
 * <p>
 * 登录用户工具类
 * </p>
 *
 * @author liusongling
 * @since 2023-07-25 09:44:01
 */
@UtilityClass
public class StpLoginUserUtil {

    /**
     * 获取登录用户
     *
     * @return {@link BaseLoginUser}
     */
    public BaseLoginUser getLoginUser() {
        return (BaseLoginUser) StpUtil.getTokenSession().get(StpUtil.getLoginIdAsString());
    }
}
