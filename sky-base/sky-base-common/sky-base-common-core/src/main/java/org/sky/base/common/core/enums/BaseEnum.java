package org.sky.base.common.core.enums;

/**
 * <p>
 * 枚举基础方法
 * </p>
 *
 * @author liusongling
 * @since 2023-07-07 10:56:04
 */
public interface BaseEnum<T extends Enum<T> & BaseEnum<T>> extends CodeEnum, DescEnum {

    /**
     * 枚举是否匹配
     *
     * @param code 枚举Code值
     * @return boolean ture匹配，false不匹配
     */
    default boolean match(String code) {
        if (code == null) {
            return false;
        }
        return code.equals(getCode());
    }

    /**
     * 根据枚举Code匹配枚举
     *
     * @param cls  枚举类
     * @param code 枚举Code值
     * @return {@link T} 匹配的枚举
     */
    static <T extends Enum<T> & BaseEnum<T>> T match(Class<T> cls, String code) {
        for (T t : cls.getEnumConstants()) {
            if (t.getCode().equals(code)) {
                return t;
            }
        }
        return null;
    }
}
