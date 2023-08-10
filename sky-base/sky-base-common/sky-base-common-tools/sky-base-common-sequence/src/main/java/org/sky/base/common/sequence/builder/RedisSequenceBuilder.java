package org.sky.base.common.sequence.builder;

import lombok.Data;
import org.sky.base.common.sequence.range.BizName;
import org.sky.base.common.sequence.range.impl.redis.RedisSequenceRangeMgr;
import org.sky.base.common.sequence.sequence.Sequence;
import org.sky.base.common.sequence.sequence.impl.DefaultRangeSequence;

/**
 * <p>
 * 基于redis取步长，序列号生成器构建器
 * </p>
 *
 * @author liusongling
 * @since 2023-08-10 22:26:08
 */
@Data
public class RedisSequenceBuilder implements SequenceBuilder {
    /**
     * 连接redis的IP[必选]
     */
    private String ip;

    /**
     * 连接redis的port[必选]
     */
    private int port;

    /**
     * 业务名称[必选]
     */
    private BizName bizName;

    /**
     * 认证权限，看redis是否配置了需要密码auth[可选]
     */
    private String auth;

    /**
     * 获取range步长[可选，默认：1000]
     */
    private int step = 1000;

    /**
     * 序列号分配起始值[可选：默认：0]
     */
    private long stepStart = 0;

    public static RedisSequenceBuilder create() {
        return new RedisSequenceBuilder();
    }

    @Override
    public Sequence build() {
        // 利用Redis获取区间管理器
        RedisSequenceRangeMgr redisSequenceRangeMgr = new RedisSequenceRangeMgr();
        redisSequenceRangeMgr.setIp(this.ip);
        redisSequenceRangeMgr.setPort(this.port);
        redisSequenceRangeMgr.setAuth(this.auth);
        redisSequenceRangeMgr.setStep(this.step);
        redisSequenceRangeMgr.setStepStart(stepStart);
        redisSequenceRangeMgr.init();
        // 构建序列号生成器
        DefaultRangeSequence sequence = new DefaultRangeSequence();
        sequence.setName(this.bizName);
        sequence.setSequenceRangeMgr(redisSequenceRangeMgr);
        return sequence;
    }
}
