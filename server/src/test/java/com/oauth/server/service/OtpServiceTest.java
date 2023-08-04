package com.oauth.server.service;

import com.oauth.server.common.exception.AuthException;
import com.oauth.server.database.model.User;
import com.oauth.server.database.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class OtpServiceTest {

    @Autowired
    private OtpService otpService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findUserOtp() {
        var user = new User();
        user = userRepository.findById(1L).get();

        otpService.save(user);
        var secondOTP = otpService.save(user);

        var loadOTP = otpService.findOTPByUserPhoneNumber(user);
        Assertions.assertAll(() ->
                Assertions.assertEquals(secondOTP, loadOTP.getOtp())
        );
    }

    @Test
    public void findOtpForUnknownUser() {
        var user = new User();
        user.setPhoneNumber("09332137957");
        Assertions.assertThrows(AuthException.class, () -> otpService.findOTPByUserPhoneNumber(user));
    }

    @Test
    public void validateOTPWithCorrectOtpCode() {
        var user = new User();
        user = userRepository.findById(1L).get();

        var otp = otpService.save(user);
        Assertions.assertEquals(true, otpService.validateOTP(user, otp));
    }

    @Test
    public void twiceVerifyOtp() {
        var user = userRepository.findById(1L).get();

        var otp = otpService.save(user);
        otpService.validateOTP(user,otp);
        Assertions.assertThrows(AuthException.class, () -> otpService.validateOTP(user, otp));
    }

    @Test
    public void validateOTPWithInCorrectOtpCode() {
        var user = new User();
        user = userRepository.findById(1L).get();

        otpService.save(user);
        var incorrectOtp = "123456";
        Assertions.assertEquals(false, otpService.validateOTP(user, incorrectOtp));
    }
}