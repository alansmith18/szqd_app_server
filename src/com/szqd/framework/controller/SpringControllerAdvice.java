package com.szqd.framework.controller;


import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
@Scope(value = "request")
public class SpringControllerAdvice extends SpringMVCController implements ResponseBodyAdvice<Object>
{
    private static Logger logger = Logger.getLogger(SpringControllerAdvice.class);

    private static final String ERROR_METHOD = "com.szqd.framework.controller.SpringControllerAdvice.handleGlobalException(Exception e)";
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Object handleGlobalException(Exception e,HttpServletRequest request)
    {
        StringBuilder builder = new StringBuilder(ERROR_METHOD);
        builder.append("\n ERROR URL:").append(request.getRequestURI());
        logger.error(builder.toString(), e);

        this.setActionOccurError(true);
        this.setErrorInfo(e.getMessage());
        return this.getResultData();
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass)
    {

        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse)
    {
        if (o != null) {
            boolean isJqueryValidateRemoteRequest = o.getClass().getName().equals(JQueryValidateRemoteValue.class.getName());
            if (isJqueryValidateRemoteRequest)
            {
                JQueryValidateRemoteValue jQueryValidateRemoteValue = (JQueryValidateRemoteValue) o;
                return jQueryValidateRemoteValue.isValue();
            }
        }

        this.setDataObject(o);
        return this.getResultData();

    }


}
