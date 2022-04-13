//package com.example.se02;
//
//import SE2.user.repository.UserRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.test.annotation.Rollback;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Rollback(value = false)
//
//
//public class UserRepositoryTests {
//
//    @Autowired
//    private UserRepository repo;
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @Test
//    public void testCreatUser(){
//        User user = new User();
//        user.setEmail("1901040218@s.hanu.edu.vn");
//        user.setPassword("123456");
//        user.setUserName("Anne");
//        user.setAddress("Hanoi");
//        user.setPhoneNumber(000000);
//
//        User savedUser = repo.save(user);
//        User existUser = entityManager.find(User.class, savedUser.getId());
//
//        assertThat(user.getEmail()).isEqualTo(existUser.getEmail());
//
//    }
//
//}
