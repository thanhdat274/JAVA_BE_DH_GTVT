package vn.com.javaapi.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vn.com.javaapi.bean.AuthResponse;

import java.util.Date;
import java.util.HashMap;

@Slf4j
@Component
public class CheckToken {
    @Value("${app.jwt.secret}")
    private String SECRET_KEY;

    public AuthResponse tokenResetPassword(String token) {
        log.info("isValidToken: " + token);
        try {
            log.info("Secret key: " + SECRET_KEY);

            // Decode the token and retrieve claims (payload) from the token.
            Claims claims = Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .parseClaimsJws(token)
                .getBody();
            log.info("Claims: " + claims);

            // Check the expiration time of the token.
            Date expirationDate = claims.getExpiration();
            Date currentDate = new Date();
            log.info("Current date: " + currentDate);

            if (expirationDate.before(currentDate)) {
                // Token has expired, not valid.
                return AuthResponse.builder()
                    .code("01")
                    .message("Token đặt lại mật khẩu không hợp lệ hoặc đã hết hạn, Vui lòng gửi lại yêu cầu đặt " +
                        "lại mật khẩu!")
                    .build();
            }
            int id = Integer.parseInt(claims.getSubject());
            log.info("Id: " + id);
            return AuthResponse.builder()
                .code("00")
                .message("Token is valid!")
                .data(new HashMap<>() {{
                    put("authId", id);
                }})
                .build();
        } catch (ExpiredJwtException ex) {
            log.error("Token has expired. Please log in again! ", ex);
            // Handle the exception if the token has expired.
            return AuthResponse.builder()
                .code("01")
                .message("Token đặt lại mật khẩu không hợp lệ hoặc đã hết hạn, Vui lòng gửi lại yêu cầu đặt " +
                    "lại mật khẩu!")
                .build();
        } catch (Exception ex) {
            log.error("Error while authenticating the token! ", ex);
            // Handle other exceptions if there is an issue with token authentication.
            return AuthResponse.builder()
                .code("02")
                .message("Error while authenticating the token!")
                .build();
        }
    }
}
