package com.example.practice.controller;

import com.example.practice.Role;
import com.example.practice.entity.Member;
import com.example.practice.mapper.MemberMapper;
import com.example.practice.service.MemberService;
import com.example.practice.service.EmailService;
import com.example.practice.service.impl.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final MemberService accountService;
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

        int result = accountService.insertMember(member);

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
        System.out.println("--------");
        System.out.println(code);
        System.out.println(EmailServiceImpl.key);
        System.out.println("-------");

        int result = 0;
        if(EmailServiceImpl.key.equals(code)){
            result = 1;
        }
        return result;
    }

    @PostMapping("/sign/signUp")
    public Map<String, Object> signUpMember(@RequestBody Map<String, Object> map){
        System.out.println(">>>>>>");
        System.out.println(map.toString());
        map.put("Ok", "Ok");
        return map;
    }
}
