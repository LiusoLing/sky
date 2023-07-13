package org.sky.base.common.core.model;

import lombok.Data;

import java.util.Map;

/**
 * <p>
 * 通用分页请求
 * </p>
 *
 * @author liusongling
 * @since 2023-07-07 15:24:18
 */
@Data
public class PageRequest<T> implements BaseRequest {
    private static final long serialVersionUID = -5741859411902518893L;

    /**
     * 页
     */
    private int page;
    /**
     * 页大小
     */
    private int pageSize;
    /**
     * 包装对象
     */
    private T bean;
    /**
     * 排序规则
     */
    private Map<String, String> sorts;
}
