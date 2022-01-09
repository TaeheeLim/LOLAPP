package com.example.practice.controller;

import com.example.practice.service.EmailService;
import com.example.practice.service.MemberService;
import com.example.practice.service.impl.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sign")
@RequiredArgsConstructor
public class AccountRestController {

    private final MemberService memberService;
    private final EmailService emailService;

    @PostMapping("/email")
    public void emailConfirm(String email){
        try{
            emailService.sendSimpleMessage(email);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @PostMapping("/verifyCode")
    public int verifyCode(String code){
        int result = 0;
        if(EmailServiceImpl.key.equals(code)){
            result = 1;
        }
        return result;
    }

    @PostMapping("/idCheck")
    public boolean checkId(String memId){
        return memberService.checkIsMemberIdExist(memId);
    }

    @PostMapping("/nickCheck")
    public boolean checkNick(String memNick){
        return memberService.checkIsMemberNickExist(memNick);
    }
}
