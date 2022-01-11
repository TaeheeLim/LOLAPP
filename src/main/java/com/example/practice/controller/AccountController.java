package com.example.practice.controller;

import com.example.practice.Role;
import com.example.practice.entity.Member;
import com.example.practice.mapper.MemberMapper;
import com.example.practice.service.MemberService;
import com.example.practice.service.EmailService;
import com.example.practice.service.impl.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sign")
public class AccountController {

    private final MemberService memberService;

    @PostMapping("/signUp")
    public String signUpMember(Member member, Model model){
        System.out.println(member.toString());
        int result = memberService.insertMember(member);

        if(result != 0){
            Member signedMember = memberService.selectMember(member.getMemId());
            model.addAttribute("member", signedMember);
        }
        return "welcome";
    }

    @GetMapping("/forgotId")
    public String forgotId(){
        return "forgot/forgotId";
    }

    @GetMapping("/forgotPassword")
    public String forgotPassword(){
        return "forgot/forgotPassword";
    }



}
