package de.telran.myshop.controllers;

import de.telran.myshop.dto.UserDetailCreateDto;
import de.telran.myshop.dto.UserDetailDto;
import de.telran.myshop.entity.User;
import de.telran.myshop.entity.UserDetail;
import de.telran.myshop.errors.UserDetailException;
import de.telran.myshop.errors.UserException;
import de.telran.myshop.mappers.UserDetailMapper;
import de.telran.myshop.repository.UserDetailsRepository;
import de.telran.myshop.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserDetailsController {
//    HOMEWORK
//    Добавьте логгинг в UserDetailsController
//    чтобы при запуске каждого метода в лог в уровнем INFO выводились название метода
//    и значения всех входных параметров

    Logger logger = LoggerFactory.getLogger(UserDetailsController.class);

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private UserDetailMapper userDetailMapper;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/users/{userId}/details")
    public UserDetailDto createUserDetail(
            @PathVariable Long userId,
            @RequestBody UserDetailCreateDto userDetailRequest
    ) {
        logger.info("createUserDetail called with userId: {} and userDetailRequest: {}", userId, userDetailRequest);

        User user = userRepository.findById(userId).orElseThrow(
                () -> {
                    logger.error("User with id {} not found", userId);
                    return new UserException("User with id " + userId + " not found", userId);
                }
        );

        UserDetail detail = userDetailMapper.toEntity(userDetailRequest);

        detail.setUser(user);
        UserDetailDto result = userDetailMapper.toDto(userDetailsRepository.save(detail));
        logger.info("UserDetail created successfully for userId: {}", userId);
        return result;
    }

    @GetMapping("/users/details/{id}")
    public UserDetailDto getUserDetailsById(
            @PathVariable Long id
    ) {
        logger.info("getUserDetailsById called with id: {}", id);

        UserDetail userDetail = userDetailsRepository.findById(id).orElseThrow(
                () -> {
                    logger.error("UserDetail with id {} not found", id);
                    return new UserDetailException("UserDetail with id " + id + " not found", id);
                }
        );

        UserDetailDto result = userDetailMapper.toDto(userDetail);
        logger.info("UserDetail fetched successfully for id: {}", id);
        return result;
    }
}
