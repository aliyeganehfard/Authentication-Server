package com.oauth.server.service;

import com.oauth.server.common.exception.AuthException;
import com.oauth.server.database.model.OTP;
import com.oauth.server.database.model.User;
import com.oauth.server.database.repository.OTPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class OtpService {

    private final OTPRepository otpRepository;

    public static final Long EXPIRATION_TIME = 2 * 60 * 1000L;
    public static final Integer OTP_LENGTH = 6;

    @Autowired
    public OtpService(OTPRepository otpRepository) {
        this.otpRepository = otpRepository;
    }

    public String save(User user) {
        var otp = new OTP();
        otp.setUser(user);
        otp.setOtp(generateOTPCode());
        otp.setExpirationDate(new Date(System.currentTimeMillis() + EXPIRATION_TIME));
        otp.setIsUsed(false);
        return otpRepository.save(otp).getOtp();
    }

    public void update(OTP otp){
        otpRepository.save(otp);
    }

    public Boolean validateOTP(User user, String otpCode) {
        var otp = findOTPByUserPhoneNumber(user);

        if (new Date().after(otp.getExpirationDate()))
            throw new AuthException(String.format("otp code was expired earlier for user with phone number {%s}", user.getPhoneNumber()));
        if (otp.getIsUsed())
            throw new AuthException("otp code used earlier");

        if (otp.getOtp().equals(otpCode)){
            otp.setIsUsed(true);
            this.update(otp);
            return true;
        }
        return false;
    }

    public OTP findOTPByUserPhoneNumber(User user) {
        OTP otp = otpRepository.findFirstByUserPhoneNumberOrderByCreateDateDesc(user.getPhoneNumber());
        if (otp == null)
            throw new AuthException(String.format("did not find any otp for user with phone number = {%s}", user.getPhoneNumber()));
        return otp;
    }

    private String generateOTPCode() {
        Random random = new Random();
        StringBuilder otp = new StringBuilder(OTP_LENGTH);

        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10));
        }

        return otp.toString();
    }

}
