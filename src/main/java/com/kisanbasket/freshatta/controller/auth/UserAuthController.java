package com.kisanbasket.freshatta.controller.auth;

import com.kisanbasket.freshatta.DTO.auth.UserDTO;
import com.kisanbasket.freshatta.service.auth.UserAuthEntityService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserAuthController {

    private final UserAuthEntityService userAuthEntityService;

    public UserAuthController(UserAuthEntityService userAuthEntityService) {
        this.userAuthEntityService = userAuthEntityService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        UserDTO updatedUserDTO = userAuthEntityService.registerUser(userDTO);
        return ResponseEntity.ok(updatedUserDTO);
    }

    @PostMapping("/generate-otp")
    public ResponseEntity<?> generateOTP(@RequestBody UserDTO userDTO) {
        userAuthEntityService.generateOTP(userDTO);
        return ResponseEntity.ok("OTP has been sent successfully");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOTP(@RequestBody UserDTO userDTO, HttpServletResponse response) {
        // verify OTP
        userAuthEntityService.verifyOTP(userDTO);
        // generate jwt-token
        userAuthEntityService.generateJWTToken(userDTO, response);
        return ResponseEntity.ok("OTP verified successfully");
    }
}