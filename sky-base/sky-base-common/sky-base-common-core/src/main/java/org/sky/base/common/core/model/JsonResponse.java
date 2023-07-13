package org.sky.base.common.core.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.sky.base.common.core.enums.BaseEnum;
import org.sky.base.common.core.enums.BizCode;

import java.util.Objects;

/**
 * <p>
 * 通用Json响应
 * </p>
 *
 * @author liusongling
 * @since 2023-07-07 15:01:33
 */
@Data
public final class JsonResponse<E> implements BaseResponse {
    private static final long serialVersionUID = -6267721325824983378L;
    public static final int CODE_SUCCESS = 200;
    public static final int CODE_ERROR = 500;

    /**
     * 响应Code
     */
    @Setter(AccessLevel.PRIVATE)
    private Integer code;
    /**
     * 响应描述
     */
    @Setter(AccessLevel.PRIVATE)
    private String msg;
    /**
     * 响应结果
     */
    @Setter(AccessLevel.PRIVATE)
    private E result;

    public JsonResponse() {
    }

    public JsonResponse(Integer code, String msg, E result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

    // ============================  构建  ==================================
    // 构建成功
    public static <E> JsonResponse<E> success() {
        return new JsonResponse<>(CODE_SUCCESS, "操作成功", null);
    }
    public static <E> JsonResponse<E> success(int code) {
        return new JsonResponse<>(code, null, null);
    }
    public static <E> JsonResponse<E> success(String msg) {
        return new JsonResponse<>(CODE_SUCCESS, msg, null);
    }
    public static <E> JsonResponse<E> success(E result) {
        return new JsonResponse<>(CODE_SUCCESS, "操作成功", result);
    }

    // 构建失败
    public static <E> JsonResponse<E> error() {
        return new JsonResponse<>(CODE_ERROR, "操作失败", null);
    }
    public static <E> JsonResponse<E> error(int code) {
        return new JsonResponse<>(code, "操作失败", null);
    }
    public static <E> JsonResponse<E> error(String msg) {
        return new JsonResponse<>(CODE_ERROR, msg, null);
    }
    public static <E> JsonResponse<E> error(E result) {
        return new JsonResponse<>(CODE_ERROR, "操作失败", result);
    }

    // 构建指定状态码
    public static <E> JsonResponse<E> get(int code, String msg, E result) {
        return new JsonResponse<>(code, msg, result);
    }

    // 是否是成功响应
    public boolean isSuccess() {
        return Objects.equals(CODE_SUCCESS, code);
    }
}
