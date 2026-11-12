
package com.todaywhat.auth.service;

import com.todaywhat.auth.dto.SignUpRequest;
import com.todaywhat.auth.entity.User;
import com.todaywhat.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void signUp(SignUpRequest request) {

        // 아이디 중복 검사
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 사용자 생성
        User user = new User(request.getUsername(), encodedPassword);

        // DB 저장
        userRepository.save(user);
    }
}
