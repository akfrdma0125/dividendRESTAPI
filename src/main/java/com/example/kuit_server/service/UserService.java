package com.example.kuit_server.service;

import com.example.kuit_server.common.exception.DatabaseException;
import com.example.kuit_server.common.exception.UserException;
import com.example.kuit_server.dao.UserDao;
import com.example.kuit_server.dto.user.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.kuit_server.common.response.status.BaseExceptionResponseStatus.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public PostUserRes signUp(PostUserReq postUserRequest) {
        log.info("[UserService.createUser]");

        // TODO: 1. validation (중복 검사)
        validateEmail(postUserRequest.getEmail());
        validateNickname(postUserRequest.getNickname());

        // TODO: 2. password 암호화
        String encodedPassword = passwordEncoder.encode(postUserRequest.getPassword());
        postUserRequest.resetPassword(encodedPassword);

        // TODO: 3. DB insert & userId 반환
        int userId = userDao.createUser(postUserRequest);

        return new PostUserRes(userId);
    }

    public PostLoginRes login(PostLoginReq postLoginRequest, int userId) {
        log.info("[UserService.login]");

        // TODO: 1. 비밀번호 일치 확인
        validatePassword(postLoginRequest.getPassword(), userId);

        return new PostLoginRes(userId, null);
    }

    public void modifyNickname(String nickname, int userId) {
        //Todo: 1. 닉네임 일치 확인
        validateNickname(nickname);

        int affectedRows = userDao.modifyNickname(userId, nickname);

        if(affectedRows != 1) throw new DatabaseException(DATABASE_ERROR);
    }

    public void modifyPassword(PatchUserPasswordReq patchUserPasswordReq, int userId) {
        //Todo: 1. 기존 비밀번호 확인
        validatePassword(patchUserPasswordReq.getPrevPassword(),userId);
        //Todo: 2. 새 비밀번호와 확인하기 위한 비밀번호 동일한지 확인
        validatePasswordInput(patchUserPasswordReq.getNewPassword(), patchUserPasswordReq.getCheckPassword());
        //Todo: 3. 새 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(patchUserPasswordReq.getNewPassword());
        patchUserPasswordReq.resetPassword(encodedPassword);
        //Todo: 4. 테이블 업데이트
        int affectedRows = userDao.modifyPassword(userId, patchUserPasswordReq.getNewPassword());
        if(affectedRows != 1) throw new DatabaseException(DATABASE_ERROR);
    }

    private void validateEmail(String email) {
        if(userDao.hasDuplicateEmail(email)){
            throw new UserException(DUPLICATE_EMAIL);
        }
    }

    private void validateNickname(String nickname) {
        if(userDao.hasDuplicateNickName(nickname)){
            throw new UserException(DUPLICATE_NICKNAME);
        }
    }

    private void validatePassword(String password, int userId) {
        String encodedPassword = userDao.getPasswordByUserId(userId);
        if (!passwordEncoder.matches(password, encodedPassword)) {
            throw new UserException(PASSWORD_NO_MATCH);
        }
    }

    private void validatePasswordInput(String newPassword, String checkPassword) {
        if(!newPassword.equals(checkPassword)){
            throw new UserException(INVALID_PASSWORD);
        }
    }
}
