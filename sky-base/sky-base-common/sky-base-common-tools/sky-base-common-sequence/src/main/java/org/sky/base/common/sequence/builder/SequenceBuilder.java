package org.sky.base.common.sequence.builder;

import org.sky.base.common.sequence.sequence.Sequence;

/**
 * <p>
 * 序列号生成器构造器
 * </p>
 *
 * @author liusongling
 * @since 2023-08-10 22:20:52
 */
public interface SequenceBuilder {

    /**
     * 构建序列号生成器
     *
     * @return {@link Sequence}
     */
    Sequence build();
}
