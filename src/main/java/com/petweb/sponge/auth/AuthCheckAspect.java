package com.petweb.sponge.auth;

import com.petweb.sponge.exception.error.LoginTypeError;
import com.petweb.sponge.utils.AuthorizationUtil;
import com.petweb.sponge.utils.LoginType;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@RequiredArgsConstructor
@Component
public class AuthCheckAspect {

    private final AuthorizationUtil authorizationUtil;

    @Around("@annotation(com.petweb.sponge.auth.UserAuth)")
    public Object processUserAuth(ProceedingJoinPoint joinPoint) throws Throwable {
        String loginType = authorizationUtil.getLoginType();
        if (loginType.equals(LoginType.USER.getLoginType())) {
            return joinPoint.proceed();
        }
        else {
            throw new LoginTypeError();
        }
    }

    @Around("@annotation(com.petweb.sponge.auth.TrainerAuth)")
    public Object processTrainerAuth(ProceedingJoinPoint joinPoint) throws Throwable {
        String loginType = authorizationUtil.getLoginType();
        if (loginType.equals(LoginType.TRAINER.getLoginType())) {
            return joinPoint.proceed();
        }
        else {
            throw new LoginTypeError();
        }
    }
}
