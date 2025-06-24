package org.example.database.exception;

import lombok.Getter;
import lombok.Setter;
import org.example.database.common.enums.ResultCodeEnum;

@Getter
@Setter
public class CustomException extends RuntimeException {
    private String code;
    private String msg;

    public CustomException(ResultCodeEnum resultCodeEnum) {
        this.code = resultCodeEnum.code;
        this.msg = resultCodeEnum.msg;
    }

    public CustomException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
