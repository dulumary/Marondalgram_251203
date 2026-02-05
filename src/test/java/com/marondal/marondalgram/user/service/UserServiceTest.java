package com.marondal.marondalgram.user.service;

import com.marondal.marondalgram.user.domain.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    // Mock

    @Test
    public void createUser() {

        // given : 테스트에 필요한 데이터 준비
        String login = "testId";
        String password = "asdf";
        String name = "김인규";
        String email = "lecture@hagulu.com";
        
        // when : 테스트 수행
        boolean result = userService.createUser(login, password, name, email);

        // then : 수행결과 검증
        assertTrue(result);
    }

    @Transactional
    @Test
    public void joinAndLogin() {

        // given : 테스트에 필요한 데이터 준비
        String login = "testId";
        String password = "asdf";
        String name = "김인규";
        String email = "lecture@hagulu.com";

        // when : 테스트 수행
        boolean result = userService.createUser(login, password, name, email);

        // then : 수행결과 검증
        assertTrue(result);

        // when

        User user = userService.getUser(login, password);

        // then

        assertNotNull(user);
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());

    }

}