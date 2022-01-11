package com.example.practice.service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface EmailService {

    void sendSimpleMessage(Map<String, String> map) throws MessagingException;
}
