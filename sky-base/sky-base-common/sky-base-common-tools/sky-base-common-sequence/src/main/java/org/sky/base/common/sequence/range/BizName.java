package org.sky.base.common.sequence.range;

/**
 * <p>
 * 名称生成器
 * </p>
 *
 * @author liusongling
 * @since 2023-07-27 09:34:20
 */
public interface BizName {

    /**
     * 生成名称
     *
     * @return {@link String} 返回文本序号
     */
    String create();
}
