package org.sky.base.common.sequence.range;

import org.sky.base.common.sequence.exception.SequenceException;

/**
 * <p>
 * 区间管理器
 * </p>
 *
 * @author liusongling
 * @since 2023-07-27 09:35:51
 */
public interface SequenceRangeMgr {

    /**
     * 获取指定区间名的下一个区间
     *
     * @param name 区间名
     * @return {@link SequenceRange} 返回下一个区间
     * @throws SequenceException 序列异常
     */
    SequenceRange nextRange(String name) throws SequenceException;

    /**
     * 初始化
     */
    void init();
}
