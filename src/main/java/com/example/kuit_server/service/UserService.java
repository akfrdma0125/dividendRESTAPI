package com.example.kuit_server.service;

import com.example.kuit_server.common.exception.DatabaseException;
import com.example.kuit_server.common.exception.UserException;
import com.example.kuit_server.dao.UserDao;
import com.example.kuit_server.dto.user.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.example.kuit_server.common.response.status.BaseExceptionResponseStatus.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    public PostUserRes login(PostUserReq postUserReq) {
        log.info("[UserService.login]");
        hasUserInfo(postUserReq);
        return userDao.getUserByUserEmail(postUserReq.getEmail());
    }

    public void deleteUser(int userId){
        log.info("[UserService.deleteUser]");
        validateUserId(userId);
        int affectedRow = userDao.deleteUser(userId);
        if(affectedRow!=1){
            throw new DatabaseException(DATABASE_ERROR);
        }
    }

    public Double getUserHoldingDollar(int userId){
        log.info("[UserService.getUserHoldingDollar]");
        validateUserId(userId);
        return userDao.getUserHoldingDollarByUserId(userId);
    }

    //TODO : 보유 달러 값이 증가 혹은 감소하는 기능
    public void updateHoldingDollar(HoldingDollarInfo holdingDollarInfo){
        log.info("[UserService.updateHoldingDollar]");
        int affectedRow = userDao.updateHoldingDollar(holdingDollarInfo);
        if(affectedRow!=1){
            throw new DatabaseException(DATABASE_ERROR);
        }
    }

    private void hasUserInfo(PostUserReq postUserReq){
        if (!userDao.hasDuplicateEmail(postUserReq.getEmail())){
            int id = userDao.createUser(postUserReq);
            log.info("[UserService.login.hasUserInfo] : userID = {}",id);
        }
    }

    public void validateUserId(int userId){
        if(!userDao.hasUserInfobyUserId(userId)){
            throw new UserException(USER_NOT_FOUND);
        }
    }
}
