package org.sky.base.common.sequence.sequence.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import lombok.Data;
import org.sky.base.common.sequence.exception.SequenceException;
import org.sky.base.common.sequence.sequence.Sequence;

/**
 * <p>
 * 雪花算法序列号生成实现
 * 一个 long 类型的数据，64位，以下为每一位的具体含义。<br>
 * snowflake的结构如下(每部分用-分开):<br>
 * 符号位（1bit）- 时间戳相对值（41bit）- 数据中心标志（5bit）- 机器标志（5bit）- 递增序号（12bit） <br>
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000 <br>
 * <li>第一位为未使用</li>
 * <li>接下来的41位为毫秒级时间(41位的长度可以使用69年)</li>
 * <li>5位datacenterId</li>
 * <li>5位workerId</li>
 * <li>最后12位是毫秒内的计数（12位的计数顺序号支持每个节点每毫秒产生4096个ID序号）</li>
 * <li>一共加起来刚好64位，为一个Long型。(转换成字符串长度为19)</li>
 *
 * <p></p>
 * 参考：
 * <a href="http://www.cnblogs.com/relucent/p/4955340.html">Twitter的分布式自增ID算法snowflake (Java版)</a><br>
 * <a href="https://blog.csdn.net/unifirst/article/details/80408050">关于长度是18还是19的问题</a><br>
 * </p>
 *
 * @author liusongling
 * @since 2023-07-27 09:52:52
 */
@Data
public class SnowflakeSequence implements Sequence {
    /**
     * 工作机器ID(0~31)
     */
    private long workerId = 0L;
    /**
     * 数据中心ID(0~31)
     */
    private long datacenterId = 0L;
    /**
     * 单例的Twitter的Snowflake 算法生成器对象
     */
    private Snowflake snowflake = IdUtil.getSnowflake(workerId, datacenterId);

    @Override
    public long nextValue() throws SequenceException {
        return snowflake.nextId();
    }

    @Override
    public String nextNo() throws SequenceException {
        return String.valueOf(nextValue());
    }

}
