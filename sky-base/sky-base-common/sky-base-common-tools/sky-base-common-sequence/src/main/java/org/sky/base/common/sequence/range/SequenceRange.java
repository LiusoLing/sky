package org.sky.base.common.sequence.range;

import lombok.ToString;

import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>
 * 序列号区间对象模型
 * </p>
 *
 * @author liusongling
 * @since 2023-07-27 09:35:21
 */
@ToString
public class SequenceRange {
    /**
     * 区间的序列号开始值
     */
    private final long min;
    /**
     * 区间的序列号结束值
     */
    private final long max;
    /**
     * 区间的序列号当前值
     */
    private final AtomicLong value;
    /**
     * 区间的序列号是否分配完毕，每次分配完就会去重新获取一个新的区间
     */
    private volatile boolean over = false;

    public SequenceRange(long min, long max) {
        this.min = min;
        this.max = max;
        this.value = new AtomicLong(min);
    }

    /**
     * 返回并递增下一个序列号
     *
     * @return long 下一个序列号，返回-1表示序列号分配完毕
     */
    public long getAndIncrement() {
        long currentValue = value.getAndIncrement();
        if (currentValue > max) {
            over = true;
            return -1;
        }

        return currentValue;
    }

    public long getMin() {
        return min;
    }

    public long getMax() {
        return max;
    }

    public boolean isOver() {
        return over;
    }
}
