package com.github.biuld.config.exception;

import com.github.biuld.util.Result;

public class BusinessException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	private Integer code;  //错误码

	public BusinessException(Result.ErrCode errCode) {
		super(errCode.getMsg());
		this.code = errCode.getCode();
	}

	Integer getCode() {
		return code;
	}
}