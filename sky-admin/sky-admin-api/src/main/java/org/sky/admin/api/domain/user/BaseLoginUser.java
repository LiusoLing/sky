package org.sky.admin.api.domain.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 基础的核心登录对象，可继承此类自行扩展
 * </p>
 *
 * @author liusongling
 * @since 2023-07-15 14:49:47
 */
@Data
@Schema(name = "BaseLoginUser", description = "核心登录对象")
public abstract class BaseLoginUser {
    /**
     * 登录用户ID
     */
    @Schema(description = "登录用户ID")
    private Long id;
    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    private Long tenantId;
    /**
     * 组织ID
     */
    @Schema(description = "组织ID")
    private Long orgId;
    /**
     * 部门ID
     */
    @Schema(description = "部门ID")
    private Long deptId;
    /**
     * 账号
     */
    @Schema(description = "账号")
    private String account;
    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String nickname;
    /**
     * 密码
     */
    @Schema(description = "密码")
    private String password;
    /**
     * 随机盐值
     */
    @Schema(description = "随机盐值")
    private String salt;
    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phone;
    /**
     * 微信openid
     */
    @Schema(description = "微信openid")
    private String wxOpenid;
    /**
     * 微信小程序openid
     */
    @Schema(description = "微信小程序openid")
    private String miniOpenid;
    /**
     * QQ openid
     */
    @Schema(description = "QQ openid")
    private String qqOpenid;
    /**
     * 角色码集合
     */
    @Schema(description = "角色码集合")
    private List<String> roleCodeList;
    /**
     * 按钮码集合
     */
    @Schema(description = "按钮码集合")
    private List<String> buttonCodeList;
    /**
     * 权限码集合
     */
    @Schema(description = "权限码集合")
    private List<String> permissionCodeList;
}
