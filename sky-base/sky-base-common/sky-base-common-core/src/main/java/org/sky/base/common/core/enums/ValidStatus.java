package org.sky.base.common.core.enums;

import lombok.AllArgsConstructor;

/**
 * <p>
 * 是否有效枚举
 * </p>
 *
 * @author liusongling
 * @since 2023-07-07 11:08:13
 */
public enum ValidStatus implements BaseEnum<ValidStatus> {
    /**
     * 1：valid：有效
     */
    VALID("1", "valid", "有效"),
    /**
     * 0：invalid：无效
     */
    INVALID("0", "invalid", "无效");

    private final String code;
    private final String name;
    private final String desc;

    ValidStatus(String code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public static ValidStatus of(String code) {
        return BaseEnum.match(ValidStatus.class, code);
    }
}
