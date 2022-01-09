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
        int result = memberService.insertMember(member);

        if(result != 0){
            Member signedMember = memberService.selectMember(member.getMemIdx());
            model.addAttribute("member", signedMember);
        }
        return "welcome";
    }



}
