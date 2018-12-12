package com.version4.mvc.chapter56.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//正常情况下抛出500异常
//用@ResponseStatus注解后，可以定义http状态码
@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Spittle Not Found")
public class SpittleNotFoundException extends RuntimeException {

}
