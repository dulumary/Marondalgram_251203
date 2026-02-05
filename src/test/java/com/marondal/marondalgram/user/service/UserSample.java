package com.marondal.marondalgram.user.service;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Locale;

@SpringBootTest
public class UserSample {

    @Autowired
    private UserService userService;

    @Test
    public void addSample() {

        Faker facker = new Faker();
        Faker fackerKo = new Faker(new Locale("ko"));

        for(int i = 0; i < 10000; i++) {
            // 로그인 아이디, 비밀번호, 이름, 이메일
            userService.createUser(facker.internet().username()
                    , facker.internet().password()
                    , fackerKo.name().fullName()
                    , facker.internet().emailAddress());
        }

    }
}
