package com.skrivet.plugins.security.jwt.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.impl.PublicClaims;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skrivet.components.jwt.subject.Action;
import com.skrivet.components.jwt.subject.Subject;
import com.skrivet.core.common.exception.BizExp;
import com.skrivet.core.common.exception.UnknownExp;
import com.skrivet.core.common.exception.account.AccountAuthExpireExp;
import com.skrivet.core.common.exception.account.AccountNotLoginExp;
import com.skrivet.core.common.exception.account.IllegalTokenExp;
import com.skrivet.plugins.security.core.entity.User;
import com.skrivet.plugins.security.core.service.PermissionSetService;

import java.util.Date;

public class JWTUtil {

    public static DecodedJWT decodeToken(String token, String signKey) throws BizExp {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(signKey)).build();
        try {
            jwtVerifier.verify(token);
        } catch (TokenExpiredException e) {
            throw new AccountAuthExpireExp();
        } catch (JWTVerificationException e) {
            throw new IllegalTokenExp();
        }
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt;
        } catch (Exception j) {
            throw new UnknownExp().copyStackTrace(j);
        }
    }

    public static String createToken(String userId, String userName, String signKey, long timeout) throws BizExp {
        try {
            JWTCreator.Builder builder = JWT.create().withAudience(userId, userName);
            if (timeout > 0) {
                builder.withExpiresAt(new Date(System.currentTimeMillis() + timeout));
            }
            return builder.sign(Algorithm.HMAC256(signKey));
        } catch (Exception e) {
            throw new UnknownExp().copyStackTrace(e);
        }
    }
}
