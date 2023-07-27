package org.sky.base.common.sequence.range.impl.name;

import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.sky.base.common.sequence.range.BizName;

/**
 * <p>
 * 根据时间重置 bizName
 * </p>
 *
 * @author liusongling
 * @since 2023-07-27 11:22:14
 */
@NoArgsConstructor
@AllArgsConstructor
public class DateBizName implements BizName {

    private String bizName;

    @Override
    public String create() {
        return bizName + DateUtil.today();
    }
}
