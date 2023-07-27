package org.sky.base.common.sequence.sequence;

import org.sky.base.common.sequence.range.BizName;
import org.sky.base.common.sequence.range.SequenceRangeMgr;

/**
 * <p>
 * 序列号区间生成器
 * </p>
 *
 * @author liusongling
 * @since 2023-07-27 09:33:02
 */
public interface RangeSequence extends Sequence {

    /**
     * 设置序列号区间管理器
     *
     * @param sequenceRangeMgr 区间管理器
     */
    void setSequenceRangeMgr(SequenceRangeMgr sequenceRangeMgr);

    /**
     * 设置获取序列号名称
     *
     * @param bizName 名称
     */
    void setName(BizName bizName);
}
