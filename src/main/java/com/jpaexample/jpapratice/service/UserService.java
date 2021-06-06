package com.jpaexample.jpapratice.service;

import com.jpaexample.jpapratice.domain.ch05.entity.User;
import com.jpaexample.jpapratice.exception.BusinessException;
import com.jpaexample.jpapratice.exception.ErrorCode;
import com.jpaexample.jpapratice.repository.UserRepository;
import com.jpaexample.jpapratice.util.Regex;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    /**
     * 회원가입
     */

    public Long join(User user){
        if(ObjectUtils.anyNotNull(user.getName(),user.getPassword())){
            throw new BusinessException(ErrorCode.MISSING_REQUIRED_ITEMS);
        }
        validateDuplicateUser(user);
        if(!Regex.isValidPassword(user.getPassword())){
            throw new BusinessException(ErrorCode.INVALID_PASSWORD);
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        User save = userRepository.save(user);
        return save.getId();
    }

    private void validateDuplicateUser(User user){
        List<User> byName = userRepository.findByName(user.getName());
        if(!byName.isEmpty()){
            throw new BusinessException(ErrorCode.DUPLICATE_USER);
        }
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> findOne(Long userId){
        return userRepository.findById(userId);
    }
}
