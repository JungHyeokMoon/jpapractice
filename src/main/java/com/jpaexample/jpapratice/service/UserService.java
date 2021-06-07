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
import java.util.Set;

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
        if(ObjectUtils.allNull(user.getName(),user.getPassword())){
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

    public void changePassword(String userName, String newPassword) {
        Optional<User> byName = findByName(userName);
        User user = byName.orElseThrow();
        Set<String> passwordHistory = user.getPasswordHistory();
        for(var pastPassword : passwordHistory){
            if(passwordEncoder.matches(newPassword,pastPassword))
                throw new BusinessException(ErrorCode.INVALID_PASSWORD);
        }
        String encode = passwordEncoder.encode(newPassword);
        user.setPassword(encode);
        passwordHistory.add(encode);
        userRepository.save(user); // 없어도 트랜잭션끝나면 저장이되는지..
    }

    private void validateDuplicateUser(User user){
        Optional<User> byName = findByName(user.getName());
        if(byName.isPresent())
            throw new BusinessException(ErrorCode.DUPLICATE_USER);
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> findOne(Long userId){
        return userRepository.findById(userId);
    }

    private Optional<User> findByName(String userName){
        return userRepository.findByName(userName);
    }

}
