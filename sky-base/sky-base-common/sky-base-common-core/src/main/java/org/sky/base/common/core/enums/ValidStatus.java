package org.sky.base.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 是否有效枚举
 * </p>
 *
 * @author liusongling
 * @since 2023-07-07 11:08:13
 */
@Getter
@AllArgsConstructor
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
    private final String value;
    private final String desc;

    public static ValidStatus of(String code) {
        return BaseEnum.match(ValidStatus.class, code);
    }

    public static void main(String[] args) {
        String code = "1";
        System.out.println(ValidStatus.of(code));
    }
}
