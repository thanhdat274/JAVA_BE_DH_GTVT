package vn.com.javaapi.service.Impl;

import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import vn.com.javaapi.bean.AuthResponse;

import vn.com.javaapi.entity.Users;
import vn.com.javaapi.repository.AuthRepository;
import vn.com.javaapi.service.AuthService;
import vn.com.javaapi.utils.CheckToken;
import vn.com.javaapi.utils.HashUtil;
import vn.com.javaapi.utils.ObjectUtil;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final Gson gson;
    @Value("${app.jwt.secret}")
    private String SECRET_KEY;
    private final CheckToken checkToken;
    //private final UserRepo userRepo;
    //private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final AuthRepository authRepository;

    public AuthResponse Signup(Users user) {
        try {
            log.info("Data signup request: {}", ObjectUtil.convertToString(user));
            Optional<Users> users = authRepository.findByEmail(user.getEmail());
            log.info("User signup request: {}", ObjectUtil.convertToString(users));
            if (users.isPresent()) {
                log.info("User email is already registered.");
                return AuthResponse.builder()
                    .code("02")
                    .message("User email is already registered.")
                    .build();
            }
            String userName = user.getEmail().split("@")[0];
            log.info("User name: {}", userName);
            user.setUsername(userName);
            user.setPassword(HashUtil.hash256PassWord(user.getPassword()));
            user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            user.setRoleId(1);
            log.info("Data signup request: {}", ObjectUtil.convertToString(user));
            authRepository.save(user);
            return AuthResponse.builder()
                .code("00")
                .message("Sign-up successful")
                .build();
        } catch (Exception e) {
            log.error("Error while connecting to the database: ", e);
            return AuthResponse.builder()
                .code("99")
                .message("Lỗi hệ thống!")
                .build();
        }
    }

    public AuthResponse Login(Users user) {
        try {
            log.info("Processing login for user: {}", user.getEmail());
            Optional<Users> optionalUser = authRepository.findByEmail(user.getEmail());
            log.info("Data user with request login: {}", ObjectUtil.convertToString(optionalUser));
            if (optionalUser.isEmpty()) {
                log.info("User does not exist ");
                return AuthResponse.builder()
                    .code("03")
                    .message("User does not exist. Please register for an account!")
                    .build();
            }
            Users storedUser = optionalUser.get();
            if (HashUtil.hash256PassWord(user.getPassword()).equalsIgnoreCase(storedUser.getPassword())) {
                Users userLogin = new Users();
                userLogin.setId(storedUser.getId());
                userLogin.setUsername(storedUser.getUsername());
                userLogin.setEmail(storedUser.getEmail());
                userLogin.setRoleId(storedUser.getRoleId());
                log.info("User logged in successfully with email: {}", storedUser.getEmail());
                // Calculate the expiration time for the token (e.g., 30 days from now)
                long expirationTimeMillis = System.currentTimeMillis() + 30 * 24 * 60 * 60 * 1000L;
                Date expirationDate = new Date(expirationTimeMillis);
                log.info("Token expiration date: {}", expirationDate);
                log.info("key: " + SECRET_KEY);
                // Generate the JWT token
                String token = Jwts.builder()
                    .setSubject(String.valueOf(userLogin.getId()))
                    .setExpiration(expirationDate)
                    .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                    .compact();

                log.info("Token created: {} for user: {}", token, ObjectUtil.convertToString(userLogin));
                return AuthResponse.builder()
                    .code("00").message("Login successful")
                    .data(new HashMap<>() {{
                        put("user", userLogin);
                        put("token", token);
                    }})
                    .build();
            } else {
                return AuthResponse.builder()
                    .code("01")
                    .message("Invalid email or password")
                    .build();
            }
        } catch (Exception e) {
            log.error("Error while connecting to the database: ", e);
            return AuthResponse.builder()
                .code("99")
                .message("Lỗi hệ thống!")
                .build();
        }
    }

    //public AuthResponse ForgotPassword(Users users) {
    //    MDC.put("tracking", NanoIdUtils.randomNanoId());
    //    log.info("Forgot Password for email {}", users.getEmail());
    //    Users checkEmailUser = authRepository.forgotPassword(users.getEmail());
    //    log.info("Forgot Password for email {}", checkEmailUser);
    //    if (null != checkEmailUser) {
    //        // Calculate the expiration time for the token (e.g., 30 days from now)
    //        long expirationTimeMillis = System.currentTimeMillis() + 5 * 60 * 1000L;
    //        Date expirationDate = new Date(expirationTimeMillis);
    //        log.info("Token expiration date: {}", expirationDate);
    //        log.info("key: " + SECRET_KEY);
    //        // Generate the JWT token
    //        String token = Jwts.builder()
    //            .setSubject(String.valueOf(checkEmailUser.getId()))
    //            .setExpiration(expirationDate)
    //            .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
    //            .compact();
    //        // Tạo đường dẫn đặt lại mật khẩu
    //        String resetLink = Constant.BaseUrlFE + "/reset-password?token=" + token;
    //        Context context = new Context();
    //        context.setVariable("name", checkEmailUser.getUsername());
    //        context.setVariable("url", resetLink);
    //
    //        String emailContent = templateEngine.process("forgot-password-email", context);
    //        log.info("template engine   " + emailContent);
    //
    //        MimeMessage message = mailSender.createMimeMessage();
    //        log.info("message " + message);
    //        try {
    //            MimeMessageHelper helper = new MimeMessageHelper(message, true);
    //            helper.setTo(checkEmailUser.getEmail());
    //            helper.setSubject("Đặt lại mật khẩu mới cho tài khoản " + checkEmailUser.getEmail());
    //
    //            helper.setText(emailContent, true);
    //            mailSender.send(message);
    //            log.info("Gửi mail thành công: " + message);
    //            return AuthResponse.builder().code("00").message("Gửi email thành công").build();
    //        } catch (MessagingException e) {
    //            log.error("Failed sending email", e);
    //            return AuthResponse.builder().code("02").message("Gửi mail không thành công").build();
    //        }
    //    } else {
    //        return AuthResponse.builder().code("01").message("Tài khoản email không tồn tại").build();
    //    }
    //}

    //public AuthResponse ResetPassword(String token, Users users) {
    //    MDC.put("tracking", NanoIdUtils.randomNanoId());
    //    log.info("token: " + token);
    //    log.info("users: " + users);
    //    try {
    //        AuthResponse checkTokenResponse = checkToken.tokenResetPassword(token);
    //        String SUCCESS_CODE = "00";
    //        if (!SUCCESS_CODE.equals(checkTokenResponse.getCode())) {
    //            log.error("Token is invalid: {}", token);
    //            return checkTokenResponse;
    //        }
    //        Integer authId = (Integer) checkTokenResponse.getData().get("authId");
    //        Users checkUser = userRepo.findByUserId(authId);
    //        if (null == checkUser) {
    //            log.error("Invalid user ID: {}", authId);
    //            return AuthResponse.builder()
    //                .code("01")
    //                .message("Invalid user ID")
    //                .build();
    //        }
    //        return authRepository.resetPassword(checkUser.getEmail(), users);
    //    } catch (Exception e) {
    //        log.error("Error while processing DeleteFolder", e);
    //        return AuthResponse.builder()
    //            .code("02")
    //            .message("Lỗi trong quá trình xử lý")
    //            .build();
    //    }
    //}

}
