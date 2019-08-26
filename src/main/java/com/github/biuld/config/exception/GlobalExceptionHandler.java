package com.github.biuld.config.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huowolf on 2019/2/28.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     *  拦截Exception类的异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Map<String,Object> exceptionHandler(Exception e){
        Map<String,Object> result = new HashMap<>();

        if (e instanceof BusinessException) {
            result.put("code", ((BusinessException) e).getCode());
        } else {
            e.printStackTrace();
            result.put("code", "50000");
        }

        result.put("msg", e.getMessage());

        return result;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,Object> handleBindException(MethodArgumentNotValidException ex) {

        BindingResult bindingResult=ex.getBindingResult();

        StringBuilder errorStr=new StringBuilder();

        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors=bindingResult.getFieldErrors();

            for (FieldError fieldError:fieldErrors) {
                errorStr.append(fieldError.getField());
                errorStr.append(":");
                errorStr.append(fieldError.getDefaultMessage());

                log.info("参数校验异常:{}({})", fieldError.getDefaultMessage(),fieldError.getField());
            }
        }

        Map<String,Object> result = new HashMap<String,Object>();
        result.put("code", "51002");
        result.put("msg", errorStr.toString());
        return result;
    }



    @ExceptionHandler(BindException.class)
    public Map<String,Object> handleBindException(BindException ex) {
        //校验 除了 requestbody 注解方式的参数校验 对应的 bindingresult 为 BeanPropertyBindingResult
        FieldError fieldError = ex.getBindingResult().getFieldError();
        log.info("必填校验异常:{}({})", fieldError.getDefaultMessage(),fieldError.getField());
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("code", "51002");
        result.put("msg", fieldError.getDefaultMessage());
        return result;
    }
}
