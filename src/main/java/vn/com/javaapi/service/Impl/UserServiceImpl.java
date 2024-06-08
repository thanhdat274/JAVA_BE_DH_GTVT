package vn.com.javaapi.service.Impl;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.com.javaapi.bean.ProductsResponse;
import vn.com.javaapi.bean.UserResponse;
import vn.com.javaapi.dto.UserDTO;
import vn.com.javaapi.entity.Users;
import vn.com.javaapi.repository.UserRepository;
import vn.com.javaapi.service.Mapper.UserMapper;
import vn.com.javaapi.service.UserService;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final Gson gson;

    @Override
    public UserResponse getOneUser(Long userId) {
        var startTime = System.currentTimeMillis();
        log.info("Begin get detail one user: {}", userId);
        Optional<Users> optUsers = userRepository.findById(userId);
        if (optUsers.isEmpty()) {
            log.info("User not found at DB.");
            UserResponse.builder()
                .code("01")
                .message("User does not exist")
                .build();
        }
        Users users = optUsers.get();
        UserDTO userDTO = userMapper.toDTO(users);
        log.info("End get user with response: {} and Time handler: {} ms.", gson.toJson(userDTO), (System.currentTimeMillis() - startTime));
        return UserResponse.builder().code("00").message("Success").data(userDTO).build();
    }
}
