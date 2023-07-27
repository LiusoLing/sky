package org.sky.base.common.sequence.sequence.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import org.sky.base.common.sequence.exception.SequenceException;
import org.sky.base.common.sequence.range.BizName;
import org.sky.base.common.sequence.range.SequenceRange;
import org.sky.base.common.sequence.range.SequenceRangeMgr;
import org.sky.base.common.sequence.sequence.RangeSequence;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 * 序列号区间生成器默认实现
 * </p>
 *
 * @author liusongling
 * @since 2023-07-27 09:52:27
 */
public class DefaultRangeSequence implements RangeSequence {
    /**
     * 获取区间时，加一把独占锁防止资源冲突
     */
    private final Lock lock = new ReentrantLock();
    /**
     * 序列号区间管理器
     */
    private SequenceRangeMgr sequenceRangeMgr;
    /**
     * 当前序列号区间
     */
    private volatile SequenceRange currentRange;

    private static Map<String, SequenceRange> sequenceRangeMap = new ConcurrentHashMap<>(8);
    /**
     * 需要获取区间的业务名称
     */
    private BizName bizName;

    @Override
    public void setSequenceRangeMgr(SequenceRangeMgr sequenceRangeMgr) {
        this.sequenceRangeMgr = sequenceRangeMgr;
    }

    @Override
    public void setName(BizName bizName) {
        this.bizName = bizName;
    }

    /**
     * 下一个序列号
     *
     * @return long 序列号值
     * @throws SequenceException 序列异常
     */
    @Override
    public long nextValue() throws SequenceException {
        String name = bizName.create();
        currentRange = sequenceRangeMap.get(name);

        //当前区间不存在，重新获取一个区间
        if (null == currentRange) {
            lock.lock();
            try {
                if (null == currentRange) {
                    currentRange = sequenceRangeMgr.nextRange(name);
                    sequenceRangeMap.put(name, currentRange);
                }
            } finally {
                lock.unlock();
            }
        }

        //value值为-1时，表名区间内的序列号已分配完毕，需要重新获取区间
        long value = currentRange.getAndIncrement();
        if (value == -1) {
            lock.lock();
            try {
                for (; ; ) {
                    if (currentRange.isOver()) {
                        currentRange = sequenceRangeMgr.nextRange(name);
                    }

                    value = currentRange.getAndIncrement();
                    if (value == -1) {
                        continue;
                    }
                    break;
                }
            } finally {
                lock.unlock();
            }
        }

        if (value < 0) {
            throw new SequenceException("Sequence value overflow, value = " + value);
        }
        return value;
    }

    /**
     * 生成下一个序号（带格式）
     *
     * @return {@link String}
     * @throws SequenceException 序列号异常
     */
    @Override
    public String nextNo() throws SequenceException {
        return String.format("%s%05d", DateUtil.format(new Date(), DatePattern.PURE_DATE_FORMAT), nextValue());
    }
}
