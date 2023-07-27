package org.sky.base.common.sequence.sequence;

import org.sky.base.common.sequence.exception.SequenceException;

/**
 * <p>
 * 序列号生成器
 * </p>
 *
 * @author liusongling
 * @since 2023-07-27 09:28:25
 */
public interface Sequence {

    /**
     * 生成下一个序列号
     *
     * @return long 序列号
     * @throws SequenceException 序列异常
     */
    long nextValue() throws SequenceException;

    /**
     * 生成下一个序号（带格式）
     *
     * @return {@link String}
     * @throws SequenceException 序列异常
     */
    String nextNo() throws SequenceException;
}
