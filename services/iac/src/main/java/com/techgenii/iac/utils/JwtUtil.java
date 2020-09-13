package com.techgenii.iac.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techgenii.iac.rqrs.TokenDTO;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.compression.DefaultCompressionCodecResolver;
import io.jsonwebtoken.impl.compression.GzipCompressionCodec;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

@Component
public class JwtUtil {

    public static final int DEFAULT_EXPIRATION_DAYS = 1;
    public static final int MAX_HTTP_HEADER_SIZE_BYTES = 8 * 1000; // This value is valid for UTF-8 encoding which is default

    @Value("${iac.jwtsecret}")
    public String JWT_SECRET;

    @Autowired
    private ObjectMapper objectMapper;

    public TokenDTO generateToken(Object payload) throws JsonProcessingException {
        Date expiration = new DateTime().plusDays(DEFAULT_EXPIRATION_DAYS).toDate();
        return generateToken(payload, expiration);
    }

    public TokenDTO generateToken(Object payload, Date expiration) throws JsonProcessingException {
        String subject = objectMapper.writeValueAsString(payload);
        JwtBuilder jwtBuilder = Jwts
                .builder()
                .setClaims(new HashMap<>())
                .setSubject(subject)
                .setExpiration(expiration)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET);

        //Compress the subject is subject size is greater
        if (subject.length() > (MAX_HTTP_HEADER_SIZE_BYTES / 2)) {
            jwtBuilder.compressWith(new GzipCompressionCodec());
        }

        String token = jwtBuilder.compact();

        return TokenDTO.builder().token(token).expiry(expiration.getTime()).build();

    }


    public Object parseToken(String token){
        return Jwts
                .parser()
                .setSigningKey(JWT_SECRET)
                .setCompressionCodecResolver(new DefaultCompressionCodecResolver())
                .parse(token)
                .getBody();
    }


}
