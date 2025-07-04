package org.example.database.common.enums;
import lombok.Getter;



@Getter
public enum ResultCodeEnum {
    SUCCESS("200", "成功"),

    PARAM_ERROR("400", "参数异常"),
    TOKEN_INVALID_ERROR("401", "无效的token"),
    TOKEN_CHECK_ERROR("401", "token验证失败，请重新登录"),
    PARAM_LOST_ERROR("4001", "参数缺失"),

    SYSTEM_ERROR("500", "系统异常"),
    USER_EXIST_ERROR("5001", "用户名已存在"),
    USER_NOT_LOGIN("5002", "用户未登录"),
    USER_ACCOUNT_ERROR("5003", "账号或密码错误"),
    USER_NOT_EXIST_ERROR("5004", "用户不存在"),
    PARAM_PASSWORD_ERROR("5005", "原密码输入错误"),
    PARAM_NAME_EXISTED("5006", "名称已存在"),
    INSERT_ERROR("5011","插入失败"),
    DELETE_ERROR("5012","删除失败"),
    UPDATE_ERROR("5013","更新失败"),
    SELECT_ERROR("5014","查询失败"),

    ID_NOT_EXIST_ERROR("5021", "ID不存在"),
    ID_EXIST_ERROR("5022", "ID已存在"),

    NO_AUTH("403", "无权限访问"),
    NO_GOODS("404", "信息不存在"),
    LOGIN_ERROR("405","登录失败" ),
    NO_PERMISSION("406", "权限不足");

    public final String code;
    public final String msg;

    ResultCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
