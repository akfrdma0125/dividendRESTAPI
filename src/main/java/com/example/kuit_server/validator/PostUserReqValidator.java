package com.example.kuit_server.validator;


import com.example.kuit_server.dto.user.PostUserReq;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PostUserReqValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return PostUserReq.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PostUserReq postUserReq = (PostUserReq) target;
        //검증 로직
//        if(postUserReq.getEmail() : 형식을 지키지 않은 경우){
//            errors.rejectValue("postUserReq","email","이메일 형식이 아닙니다.");
//        }
    }
}
