package org.example.shopvue.service;

public interface CaptchaService {
    void storeCaptchaTextWithExpiration(String uuid, String captchaText, long expirationInSeconds);
}
