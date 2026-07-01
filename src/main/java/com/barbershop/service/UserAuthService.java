package com.barbershop.service;

import com.barbershop.dto.LoginRequestDTO;
import com.barbershop.dto.UserRegistrationDTO;
import com.barbershop.dto.UserProfileDTO;
import com.barbershop.dto.AuthResponseDTO;
import com.barbershop.entity.User;
import com.barbershop.exception.BadRequestException;
import com.barbershop.exception.ResourceNotFoundException;
import com.barbershop.repository.UserRepository;
import com.barbershop.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public AuthResponseDTO register(UserRegistrationDTO registrationDTO) {
        if (userRepository.existsByEmail(registrationDTO.getEmail())) {
            throw new BadRequestException("Email already registered");
        }

        User user = new User();
        user.setUserName(registrationDTO.getUserName());
        user.setEmail(registrationDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        user.setPhone(registrationDTO.getPhone());
        user.setGender("Not Selected");
        user.setDob("Not Selected");

        User savedUser = userRepository.save(user);
        String token = jwtTokenProvider.generateToken(savedUser.getEmail(), "USER");

        AuthResponseDTO response = new AuthResponseDTO();
        response.setSuccess(true);
        response.setMessage("User registered successfully");
        response.setToken(token);
        response.setUser(convertToProfileDTO(savedUser));

        return response;
    }

    public AuthResponseDTO login(LoginRequestDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid password");
        }

        String token = jwtTokenProvider.generateToken(user.getEmail(), "USER");

        AuthResponseDTO response = new AuthResponseDTO();
        response.setSuccess(true);
        response.setMessage("Logged in successfully");
        response.setToken(token);
        response.setUser(convertToProfileDTO(user));

        return response;
    }

    public UserProfileDTO getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return convertToProfileDTO(user);
    }

    private UserProfileDTO convertToProfileDTO(User user) {
        UserProfileDTO dto = new UserProfileDTO();
        dto.setId(user.getId());
        dto.setUserName(user.getUserName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setGender(user.getGender());
        dto.setDob(user.getDob());
        dto.setImage(user.getImage());
        return dto;
    }
}
