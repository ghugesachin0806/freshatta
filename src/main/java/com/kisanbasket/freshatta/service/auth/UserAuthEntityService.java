package com.kisanbasket.freshatta.service.auth;

import com.kisanbasket.freshatta.DTO.auth.UserDTO;
import com.kisanbasket.freshatta.entity.auth.UserAuthEntity;
import com.kisanbasket.freshatta.exception.CustomException;
import com.kisanbasket.freshatta.repository.auth.UserAuthRepository;
import com.kisanbasket.freshatta.utils.JWTUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class UserAuthEntityService implements UserDetailsService {

    private final UserAuthRepository userAuthRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    @Value("${spring.login.otp.resend}")
    private int resendOtpValidity;

    @Value("${spring.login.otp.verifytime}")
    private int verifytimeOTP;

    public UserAuthEntityService(UserAuthRepository userAuthRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, JWTUtil jwtUtil) {
        this.userAuthRepository = userAuthRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userAuthRepository.findByMobileNumber(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with mobile-number: " + username));
    }

    public void generateOTP(UserDTO userDTO) {

        UserAuthEntity user = userAuthRepository.findByMobileNumber(userDTO.getMobileNumber())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with mobile number: " + userDTO.getMobileNumber()));

        Date currentTime = new Date();
        Date prevOTPTime = user.getLoginAttemptTimeStamp() != null
                ? user.getLoginAttemptTimeStamp()
                : new Date(0);

        long timeDifferenceMillis = currentTime.getTime() - prevOTPTime.getTime();
        long timeDifferenceMinutes = timeDifferenceMillis / (60 * 1000);

        if (timeDifferenceMinutes < resendOtpValidity)
            throw new CustomException(
                    HttpStatus.BAD_REQUEST,
                    "OTP already sent recently. Please wait " + (resendOtpValidity - timeDifferenceMinutes) + " more minute(s) before requesting a new one.");

        String otp = String.valueOf(1000 + new SecureRandom().nextInt(9000));
        String encodeOtp = passwordEncoder.encode(otp);
        user.setLoginOtp(encodeOtp);
        user.setLoginAttemptTimeStamp(currentTime);
        userAuthRepository.save(user);

        // -------------- call the OTP service --------------------

        // --------------------------------------------------------

        System.out.println("\n" + "OTP : " + otp + "\n");
    }

    public void verifyOTP(UserDTO userDTO) {

        String mobileNumber = userDTO.getMobileNumber();
        String OTP = userDTO.getLoginOtp();

        if (mobileNumber == null || mobileNumber.trim().isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Mobile number must not be empty");
        }

        if (OTP == null || OTP.trim().isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "OTP must not be empty");
        }

        String userOTPEncode = new BCryptPasswordEncoder().encode(OTP);

        UserAuthEntity userAuthEntity = userAuthRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with mobile number: " + mobileNumber));

        if (userAuthEntity.getLoginAttemptTimeStamp() == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "OTP not requested or expired");
        }

        Date currentTime = new Date();
        Date otpGeneratedTime = userAuthEntity.getLoginAttemptTimeStamp();

        long timeDifferenceMillis = currentTime.getTime() - otpGeneratedTime.getTime();
        long timeDifferenceMinutes = timeDifferenceMillis / (60 * 1000);

        if (timeDifferenceMinutes > verifytimeOTP) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "OTP has expired");
        }

        String savedEncodedOtp = userAuthEntity.getLoginOtp();
        if (savedEncodedOtp == null || !new BCryptPasswordEncoder().matches(OTP, savedEncodedOtp)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid OTP");
        }

        userAuthEntity.setIsVerified(true);
        userAuthEntity.setLoginOtp(null);
        userAuthEntity.setLoginAttemptTimeStamp(null);
        userAuthRepository.save(userAuthEntity);
    }

    public UserDTO registerUser(UserDTO userDTO) {

        Optional<UserAuthEntity> userAuthEntityOptional = userAuthRepository.findByMobileNumber(userDTO.getMobileNumber());

        if (userAuthEntityOptional.isPresent())
            throw new CustomException(HttpStatus.BAD_REQUEST, "User already exists with mobile number: " + userDTO.getMobileNumber());

        UserAuthEntity userAuthEntity = modelMapper.map(userDTO, UserAuthEntity.class);
        userAuthEntity.setIsVerified(false);

        UserAuthEntity updatedUserAuthEntity = userAuthRepository.save(userAuthEntity);
        UserDTO updatedUserDTO = modelMapper.map(updatedUserAuthEntity, UserDTO.class);
        return updatedUserDTO;
    }

    public void generateJWTToken(UserDTO userDTO, HttpServletResponse response) {
        String token = jwtUtil.generateAccessToken(userDTO.getMobileNumber());
        response.setHeader("Authorization", "Bearer " + token);

        Cookie refreshCookie = jwtUtil.generateRefreshTokenCookie(userDTO.getMobileNumber());
        response.addCookie(refreshCookie);
    }
}
