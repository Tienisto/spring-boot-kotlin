package com.example.demo

import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice

/**
 * wraps the JSON response in { success: true, data: <actual response> }
 */

@ControllerAdvice(basePackages = ["com.example.demo"])
class JSONFilter : ResponseBodyAdvice<Any> {

    // only wraps responses which has not been wrapped
    override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>>): Boolean {
        return returnType.genericParameterType != Response.Wrapper::class.java
    }

    override fun beforeBodyWrite(body: Any?, returnType: MethodParameter, selectedContentType: MediaType, selectedConverterType: Class<out HttpMessageConverter<*>>, request: ServerHttpRequest, response: ServerHttpResponse): Any? {
        println("here $body")
        return Response.Wrapper(data = body)  // the actual wrap
    }
}