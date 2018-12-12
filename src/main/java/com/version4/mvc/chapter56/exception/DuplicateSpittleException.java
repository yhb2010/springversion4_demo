package com.version4.mvc.chapter56.exception;

//重复定义异常
public class DuplicateSpittleException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	private String errCode;
	private String errMsg;

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public DuplicateSpittleException(String errCode, String errMsg) {
		super(errMsg);
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

}
