package org.sky.base.common.sequence.range.impl.name;

import lombok.AllArgsConstructor;
import org.sky.base.common.sequence.range.BizName;

/**
 * <p>
 * 返回传入的bizName
 * </p>
 *
 * @author liusongling
 * @since 2023-07-27 11:22:29
 */
@AllArgsConstructor
public class DefaultBizName implements BizName {

    private String bizName;

    @Override
    public String create() {
        return bizName;
    }
}
