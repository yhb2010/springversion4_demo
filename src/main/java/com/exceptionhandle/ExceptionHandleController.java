package com.exceptionhandle;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**spring mvc统一异常处理
 * @author DELL
 *
 */
@ControllerAdvice
public class ExceptionHandleController {

	@ExceptionHandler(value = Exception.class)
	public Object handle(Exception e, HttpServletRequest request){
		String uri = request.getRequestURI();
		if(uri != null){

		}
		return new Object();
	}

}
