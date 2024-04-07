// package com.edu.boardback.provider;

// import java.nio.charset.StandardCharsets;
// import java.security.Key;
// import java.time.Instant;
// import java.time.temporal.ChronoUnit;
// import java.util.Date;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Component;

// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;
// import io.jsonwebtoken.security.Keys;

// @Component
// public class JwtProvider {

//     @Value("${secret-key}")
//     private String secretKey;

//     public String create(String email){
//         Date expiredDate = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
//         // 유효기간 설정, 현재시간 + 1시간
//         Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
//         String jwt = Jwts.builder()
//             .signWith(key, SignatureAlgorithm.ES384)//알고리즘과 key 설정
//             .setSubject(email)//주체설정
//             .setIssuedAt(new Date())//발급시각
//             .setExpiration(expiredDate)//만료시각
//             .compact();
//         return jwt;
//     }

//     public String validate(String jwt){
//         Claims claims = null;
//         Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
//         try {
//            claims = Jwts.parserBuilder()
//             .setSigningKey(key).build()
//             .parseClaimsJws(jwt).getBody();
//         } catch (Exception e) {
//             e.printStackTrace();
//             return null;
//         }
//         return claims.getSubject();
//     }
// }
