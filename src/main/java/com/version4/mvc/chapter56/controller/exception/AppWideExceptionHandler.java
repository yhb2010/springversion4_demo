package com.version4.mvc.chapter56.controller.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.version4.mvc.chapter56.exception.DuplicateSpittleException;

//用@ControllerAdvice注解的类，就不需要再用@Component注解了，并且里面可以添加如下注解的方法：
//@ExceptionHandler
//@InitBinder
//@ModelAttribute
//以上这些方法会应用到整个应用的所有控制器中带有@RequestMapping注解的方法上
@ControllerAdvice
public class AppWideExceptionHandler {

	@ExceptionHandler(DuplicateSpittleException.class)
	public ModelAndView runtimeExceptionHandler(DuplicateSpittleException ex) {
        System.out.println(ex.getMessage());

        ModelAndView model = new ModelAndView("mvc/error");
		model.addObject("errCode", ex.getErrCode());
		model.addObject("errMsg", ex.getErrMsg());
        return model;
    }

}
