package org.sky.base.common.sequence.properties;

import lombok.Data;

/**
 * <p>
 * 发号器通用属性
 * </p>
 *
 * @author liusongling
 * @since 2023-08-10 21:27:53
 */
@Data
public class BaseSequenceProperties {
    /**
     * 获取range步长[可选，默认：1000]
     */
    private int step = 1000;

    /**
     * 序列号分配起始值[可选，默认：0]
     */
    private long stepStart = 0;

    /**
     * 业务名称
     */
    private String bizName = "sky";
}
