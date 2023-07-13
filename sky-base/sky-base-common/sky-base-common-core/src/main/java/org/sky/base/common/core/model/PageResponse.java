package org.sky.base.common.core.model;

import lombok.Data;

import java.util.List;

/**
 * <p>
 * 通用分页响应
 * </p>
 *
 * @author liusongling
 * @since 2023-07-07 15:33:31
 */
@Data
public class PageResponse<T> implements BaseResponse {
    private static final long serialVersionUID = 1846167152039431456L;

    /**
     * 总数
     */
    private Long total;
    /**
     * 总页数
     */
    private Long totalPages;
    /**
     * 每页大小
     */
    private Long pageSize;
    /**
     * 页码
     */
    private Long pageNumber;
    /**
     * 页内容
     */
    private List<T> results;

    public PageResponse() {
    }

    public PageResponse(List<T> results, Long total, Long pageSize, Long pageNumber) {
        this.results = results;
        this.total = total;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
    }

    public PageResponse(List<T> results, Long total, Integer pageSize, Integer pageNumber) {
        this.results = results;
        this.total = total;
        this.pageSize = pageSize.longValue();
        this.pageNumber = pageNumber.longValue();
    }

    public static <T> PageResponse<T> of(List<T> results, Long total, Long pageSize, Long pageNumber) {
        return new PageResponse<>(results, total, pageSize, pageNumber);
    }

    public static <T> PageResponse<T> of(List<T> results, Long total, Integer pageSize, Integer pageNumber) {
        return new PageResponse<>(results, total, pageSize, pageNumber);
    }
}
