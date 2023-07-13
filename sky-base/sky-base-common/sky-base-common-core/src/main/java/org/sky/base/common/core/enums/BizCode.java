package org.sky.base.common.core.enums;

/**
 * <p>
 * 基本异常提示常量枚举<br/>
 * 接口常量或静态类常量存在以下问题：
 * <li>无法限制开发人员继承/实现接口</li>
 * <li>开发人员可在子接口继续添加常量，新的常量可能得不到父层的支持</li>
 * <li>常量作为参数时，String、int等弱类型，开发人员可以传入没有在常量接口内定义的值，无法通过编译器发现</li>
 * <li>开发人员可直接写常量值，不能通过 == 比较，无法优化性能</li>
 * <li>没有参考的情况下，可能不知道某个int型参数到底赋值什么内容</li>
 * <li>编译时，直接把常量的值编译到类的二进制代码中，常量的值升级变化后，需要重新编译所有应用常量的类，因为代码中的是旧值</li>
 * </p>
 *
 * @author liusongling
 * @since 2023-07-07 14:35:13
 */
public enum BizCode implements BaseEnum<BizCode> {
    /**
     * 1：系统提示：操作成功
     */
    Success(1, "系统提示", "操作成功"),
    /**
     * 0：系统提示：操作失败
     */
    Fail(0, "系统提示", "操作失败"),
    /**
     * 10001：业务提示：未查询到信息
     */
    NotFindError(10001, "业务提示", "未查询到信息"),
    /**
     * 10002：业务提示：保存信息失败
     */
    SaveError(10002, "业务提示","保存信息失败"),
    /**
     * 10003：业务提示：更新信息失败
     */
    UpdateError(10003, "业务提示","更新信息失败"),
    /**
     * 10004：数据校验提示：数据校验失败
     */
    ValidateError(10004, "数据校验提示", "数据检验失败"),
    /**
     * 10005：状态校验提示：状态已被启用
     */
    StatusHasValid(10005, "状态校验提示","状态已经被启用"),
    /**
     * 10006：状态校验提示：状态已被禁用
     */
    StatusHasInvalid(10006, "状态校验提示","状态已经被禁用"),
    /**
     * 10007：系统提示：系统异常
     */
    SystemError(10007, "系统提示", "系统异常"),
    /**
     * 10008：系统提示：业务异常
     */
    BusinessError(10008, "系统提示", "业务异常"),
    /**
     * 10009：系统提示：参数设置非法
     */
    ParamSetIllegal(10009, "系统提示", "参数设置非法"),
    /**
     * 10010：系统提示：当前状态不正确，请勿重复提交
     */
    TransferStatusError(10010, "系统提示", "当前状态不正确，请勿重复提交"),
    /**
     * 10011：系统提示：没有操作该功能的权限，请联系管理员
     */
    NotGrant(10011, "系统提示","没有操作该功能的权限，请联系管理员");

    private final Integer code;
    private final String name;
    private final String desc;

    BizCode(Integer code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    @Override
    public String getCode() {
        return code.toString();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public Integer getIntCode() {
        return code;
    }

    public static BizCode of(String code) {
        return BaseEnum.match(BizCode.class, code);
    }
}
