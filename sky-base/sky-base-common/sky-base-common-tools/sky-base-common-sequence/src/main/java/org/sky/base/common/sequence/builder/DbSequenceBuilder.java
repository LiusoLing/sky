package org.sky.base.common.sequence.builder;

import lombok.Data;
import org.sky.base.common.sequence.range.BizName;
import org.sky.base.common.sequence.range.impl.db.DbSequenceRangeMgr;
import org.sky.base.common.sequence.sequence.Sequence;
import org.sky.base.common.sequence.sequence.impl.DefaultRangeSequence;

import javax.sql.DataSource;

/**
 * <p>
 * 基于DB取步长，序列号生成器构建器
 * </p>
 *
 * @author liusongling
 * @since 2023-08-10 22:20:25
 */
@Data
public class DbSequenceBuilder implements SequenceBuilder {
    /**
     * 数据库数据源[必选]
     */
    private DataSource dataSource;

    /**
     * 业务名称[必选]
     */
    private BizName bizName;

    /**
     * 存放序列号步长的表[可选：默认：sequence]
     */
    private String tableName = "sky_sequence";

    /**
     * 并发是数据使用了乐观策略，这个是失败重试的次数[可选：默认：100]
     */
    private int retryTimes = 100;

    /**
     * 获取range步长[可选：默认：1000]
     */
    private int step = 1000;

    /**
     * 序列号分配起始值[可选：默认：0]
     */
    private long stepStart = 0;

    public static DbSequenceBuilder create() {
        return new DbSequenceBuilder();
    }

    @Override
    public Sequence build() {
        // 利用DB获取区间管理器
        DbSequenceRangeMgr dbSequenceRangeMgr = new DbSequenceRangeMgr();
        dbSequenceRangeMgr.setDataSource(this.dataSource);
        dbSequenceRangeMgr.setTableName(this.tableName);
        dbSequenceRangeMgr.setRetryTimes(this.retryTimes);
        dbSequenceRangeMgr.setStep(this.step);
        dbSequenceRangeMgr.setStepStart(stepStart);
        dbSequenceRangeMgr.init();

        // 构建序列号生成器
        DefaultRangeSequence sequence = new DefaultRangeSequence();
        sequence.setName(this.bizName);
        sequence.setSequenceRangeMgr(dbSequenceRangeMgr);
        return sequence;
    }
}
