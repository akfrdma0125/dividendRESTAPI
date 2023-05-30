package com.example.kuit_server.service;

import com.example.kuit_server.common.exception.UserException;
import com.example.kuit_server.common.response.BaseResponse;
import com.example.kuit_server.dao.UserDao;
import com.example.kuit_server.dto.PostUserReq;
import com.example.kuit_server.dto.PostUserRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.kuit_server.common.response.status.BaseExceptionResponseStatus.DUPLICATE_EMAIL;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public PostUserRes signUp(PostUserReq postUserRequest) {
        log.info("[UserService.createUser]");

        // TODO: 1. validation (중복 검사)
        if(userDao.hasDuplicateEmail(postUserRequest.getEmail())){
            throw new UserException(DUPLICATE_EMAIL);
        }

        // TODO: 2. password 암호화
        String encodedPassword = passwordEncoder.encode(postUserRequest.getPassword());
        postUserRequest.resetPassword(encodedPassword);

        // TODO: 3. DB insert & userId 반환
        int userId = userDao.createUser(postUserRequest);

        return new PostUserRes(userId);
    }

}
