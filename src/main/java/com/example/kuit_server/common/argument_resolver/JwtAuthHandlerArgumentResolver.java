package com.example.kuit_server.common.argument_resolver;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

//어노테이션 의 구현 내용
@Slf4j
@Component
public class JwtAuthHandlerArgumentResolver implements HandlerMethodArgumentResolver {

    //가능한지?
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("[JwtAuthHandlerArgumentResolver.supportsParameter]");
        boolean hasAnnotation = parameter.hasParameterAnnotation(PreAuthorize.class);
        boolean hasType = long.class.isAssignableFrom(parameter.getParameterType());
        return hasAnnotation && hasType;
    }

    //가능하다면 구현 내용: 토큰에 있는 id 반환
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        log.info("[JwtAuthHandlerArgumentResolver.resolveArgument]");
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        return request.getAttribute("userId");
    }

}