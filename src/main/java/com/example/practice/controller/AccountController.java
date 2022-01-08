package com.example.practice.controller;

import com.example.practice.Role;
import com.example.practice.entity.Member;
import com.example.practice.mapper.MemberMapper;
import com.example.practice.service.MemberService;
import com.example.practice.service.EmailService;
import com.example.practice.service.impl.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final MemberService memberService;
    private final MemberMapper memberMapper;
    private final EmailService emailService;

    @GetMapping("/create")
    public Member create(){
        Member member = new Member();
        member.setMemId("sjsj1123");
        member.setMemPassword("mf4158kl00");
        member.setMemNick("임태희");
        member.setMemEmail("sjsj1123@korea.com");
        member.setMemCell("01097176807");
        member.setMemRole(Role.ROLE_USER.getCode());

        int result = memberService.insertMember(member);

        if(result != 0){
            return memberMapper.selectMember(member.getMemIdx());
        }

        return null;
    }

    @PostMapping("/sign/email")
    public void emailConfirm(String email){
        try{
            emailService.sendSimpleMessage(email);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @PostMapping("/sign/verifyCode")
    public int verifyCode(String code){
        int result = 0;
        if(EmailServiceImpl.key.equals(code)){
            result = 1;
        }
        return result;
    }

    @PostMapping("/sign/signUp")
    public Map<String, Object> signUpMember(Member member){
        System.out.println(member.toString());
        Map<String, Object> map = new HashMap<>();

        map.put("Ok", "Ok");
        return map;
    }

    @PostMapping("/sign/idCheck")
    public boolean checkId(String memId){
        return memberService.checkIsMemberIdExist(memId);
    }

    @PostMapping("/sign/nickCheck")
    public boolean checkNick(String memNick){
        return memberService.checkIsMemberNickExist(memNick);
    }

}
