package com.barbershop.service;

import com.barbershop.repository.UserRepository;
import com.barbershop.repository.BarberRepository;
import com.barbershop.repository.AppointmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
public class UserAuthServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAuthService userAuthService;

    @Test
    public void testUserRegistration() {
        assertNotNull(userAuthService);
    }
}
