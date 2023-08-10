package org.sky.base.common.sequence.range.impl.db;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.sky.base.common.sequence.exception.SequenceException;
import org.sky.base.common.sequence.range.SequenceRange;
import org.sky.base.common.sequence.range.SequenceRangeMgr;

import javax.sql.DataSource;

/**
 * <p>
 * Db 发号器区间管理器
 * </p>
 *
 * @author liusongling
 * @since 2023-07-27 11:22:00
 */
@Data
public class DbSequenceRangeMgr implements SequenceRangeMgr {

    /**
     * 区间步长
     */
    private int step = 1000;

    /**
     * 区间起始位置，真实从stepStart+1开始
     */
    private long stepStart = 0;

    /**
     * 获取区间失败重试次数
     */
    private int retryTimes = 100;

    /**
     * DB来源
     */
    private DataSource dataSource;

    /**
     * 表名，默认range
     */
    private String tableName = "sky_range";

    @Override
    public SequenceRange nextRange(String name) throws SequenceException {
        if (StrUtil.isBlank(name)) {
            throw new SequenceException("[DbSequenceRangeMgr-nextRange] name is empty.");
        }

        Long oldValue;
        Long newValue;
        for (int i = 0; i < getRetryTimes(); i++) {
            oldValue = BaseDbHelper.selectRange(getDataSource(), getTableName(), name, getStepStart());
            if (null == oldValue) {
                // 区间不存在，重试
                continue;
            }

            newValue = oldValue + getStep();
            if (BaseDbHelper.updateRange(getDataSource(), getTableName(), newValue, oldValue, name)) {
                return new SequenceRange(oldValue + 1, newValue);
            }
            // else 失败重试
        }

        throw new SequenceException("Retried too many times, retryTimes = " + getRetryTimes());
    }

    @Override
    public void init() {
        checkParam();
        BaseDbHelper.createTable(getDataSource(), getTableName());
    }

    private void checkParam() {
        if (step <= 0) {
            throw new SequenceException("[DbSequenceRangeMgr-checkParam] step must greater than 0.");
        }
        if (stepStart < 0) {
            throw new SequenceException("[DbSequenceRangeMgr-checkParam] stepStart < 0.");
        }
        if (retryTimes <= 0) {
            throw new SequenceException("[DbSequenceRangeMgr-checkParam] retryTimes must greater than 0.");
        }
        if (null == dataSource) {
            throw new SequenceException("[DbSequenceRangeMgr-checkParam] dataSource is null.");
        }
        if (StrUtil.isBlank(tableName)) {
            throw new SequenceException("[DbSequenceRangeMgr-checkParam] tableName is empty.");
        }
    }
}
