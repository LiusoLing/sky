package org.sky.base.common.sequence.builder;

import lombok.Data;
import org.sky.base.common.sequence.sequence.Sequence;
import org.sky.base.common.sequence.sequence.impl.SnowflakeSequence;

/**
 * <p>
 * 基于雪花算法，序列号生成器构建器
 * </p>
 *
 * @author liusongling
 * @since 2023-08-10 22:35:25
 */
@Data
public class SnowflakeSequenceBuilder implements SequenceBuilder {
    /**
     * 数据中心ID，值的范围在[0,31]之间，一般可以设置机房的IDC[必选]
     */
    private long datacenterId;

    /**
     * 工作机器ID，值的范围在[0,31]之间，一般可以设置机器编号[必选]
     */
    private long workerId;

    public static SnowflakeSequenceBuilder create() {
        return new SnowflakeSequenceBuilder();
    }

    @Override
    public Sequence build() {
        SnowflakeSequence sequence = new SnowflakeSequence();
        sequence.setDatacenterId(this.datacenterId);
        sequence.setWorkerId(this.workerId);
        return sequence;
    }

    public SnowflakeSequenceBuilder datacenterId(long datacenterId) {
        this.datacenterId = datacenterId;
        return this;
    }

    public SnowflakeSequenceBuilder workerId(long workerId) {
        this.workerId = workerId;
        return this;
    }
}
