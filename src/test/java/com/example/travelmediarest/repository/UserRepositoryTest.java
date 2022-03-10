package com.example.travelmediarest.repository;

import com.example.travelmediarest.model.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Query;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
class UserRepositoryTest {

//    @Autowired
//    @Qualifier("userRepository")
//    private UserRepository userRepository;
//
//    @Test
//    void itShouldCheckUserWithEmailExists() {
//        String mail = "a@a.a";
//        User user = new User();user.setUsername("himon");user.setPassword("1");user.setMail(mail);
//        userRepository.save(user);
//        // when
//        boolean response = userRepository.selectExistsEmail(mail);
//        // then
//        assertThat(response).isTrue();
//    }
//
////    @Test
////    @Disabled
////    void testFindByMail() {
////    }

}