package org.sky.base.common.sequence.range.impl.redis;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.sky.base.common.sequence.exception.SequenceException;
import org.sky.base.common.sequence.range.SequenceRange;
import org.sky.base.common.sequence.range.SequenceRangeMgr;
import redis.clients.jedis.Jedis;

/**
 * <p>
 * redis 发号器区间管理器
 * </p>
 *
 * @author liusongling
 * @since 2023-07-27 11:22:52
 */
@Data
public class RedisSequenceRangeMgr implements SequenceRangeMgr {
    /**
     * 前缀防止key重复
     */
    private final static String KEY_PREFIX = "sky:x_sequence_";

    /**
     * redis客户端
     */
    private Jedis jedis;

    /**
     * IP
     */
    private String ip;

    /**
     * PORT
     */
    private Integer port;

    /**
     * 验证权限
     */
    private String auth;

    /**
     * 区间步长
     */
    private int step = 1000;

    /**
     * 区间起始位置，真实从stepStart+1开始
     */
    private long stepStart = 0;

    /**
     * 标记业务key是否存在，如果false，在取nextRange时，会取check一把 这个boolean只为提高性能，不用每次都取redis check
     */
    private volatile boolean keyAlreadyExist;

    @Override
    public SequenceRange nextRange(String name) throws SequenceException {
        if (!keyAlreadyExist) {
            boolean isExists = jedis.exists(getRealKey(name));
            if (!isExists) {
                // 第一次不存在，进行初始化,setnx 不存在就set，存在就忽略
                jedis.setnx(getRealKey(name), String.valueOf(stepStart));
            }
            keyAlreadyExist = true;
        }

        long max = jedis.incrBy(getRealKey(name), step);
        long min = max - step + 1;
        return new SequenceRange(min, max);
    }

    @Override
    public void init() {
        checkParam();
        jedis = new Jedis(ip, port);
        if (StrUtil.isNotBlank(auth)) {
            jedis.auth(auth);
        }
    }

    private void checkParam() {
        if (StrUtil.isBlank(ip)) {
            throw new SequenceException("[RedisSeqRangeMgr-checkParam] ip is empty.");
        }
        if (null == port) {
            throw new SequenceException("[RedisSeqRangeMgr-checkParam] port is null.");
        }
    }

    private String getRealKey(String name) {
        return KEY_PREFIX + name;
    }
}
