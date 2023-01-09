package com.quizz.lessonservice.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.quizz.lessonservice.dto.UserInfo;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.Map;

@Log4j2
public class TokenUtility {

    public static UserInfo getBearerTokenInfo() {
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        UserInfo userInfo = null;
        try {
            userInfo = TokenUtility.decodeJWT(token);
        } catch (Exception e) {
            log.error(e);
        }
        return userInfo;
    }

    public static UserInfo decodeJWT(String jwtToken) throws Exception {
        System.out.println("------------ Decode JWT ------------");
        if (jwtToken == null) return null;
        String[] split_string = jwtToken.split("\\.");
        String base64EncodedHeader = split_string[0];
        String base64EncodedBody = split_string[1];
        String base64EncodedSignature = split_string[2];

        System.out.println("~~~~~~~~~ JWT Header ~~~~~~~");
        Base64 base64Url = new Base64(true);
        String header = new String(base64Url.decode(base64EncodedHeader));
        System.out.println("JWT Header : " + header);


        System.out.println("~~~~~~~~~ JWT Body ~~~~~~~");
        String body = new String(base64Url.decode(base64EncodedBody));
        System.out.println("JWT Body : " + body);
        Map<String, Object> mapping = new ObjectMapper().readValue(body, HashMap.class);
        UserInfo userInfo = new UserInfo();
        userInfo.setId((String) mapping.get("sub"));
        userInfo.setUsername((String) mapping.get("name"));
        return userInfo;
    }
}
