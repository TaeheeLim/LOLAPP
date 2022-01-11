package com.example.practice.controller;

import com.example.practice.entity.Member;
import com.example.practice.service.EmailService;
import com.example.practice.service.MemberService;
import com.example.practice.service.impl.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.Map;

@RestController
@RequestMapping("/sign")
@RequiredArgsConstructor
public class AccountRestController {

    private final MemberService memberService;
    private final EmailService emailService;

    @PostMapping("/email")
    public void emailConfirm(@RequestBody Map<String, String> map){
        try{
            emailService.sendSimpleMessage(map);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @PostMapping("/verifyCode")
    public int verifyCode(String code, String email){

        int result = 0;
        if(EmailServiceImpl.keyMap.get(email).equals(code)){
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

    @PostMapping("/emailCheck")
    public boolean checkEmail(String email){
        return memberService.checkIsMemberEmailExist(email);
    }

    @PostMapping("/isMemberExist")
    public boolean forgotPassword(Member member){
        return memberService.forgotPassword(member);
    }

    @PostMapping("/findPassword")
    public boolean findPassword(@RequestBody Map<String, String> map){
        Member member = new Member();
        member.setMemNick(map.get("nick"));
        member.setMemId(map.get("id"));
        member.setMemEmail(map.get("email"));

        boolean result = memberService.forgotPassword(member);

        if(result){
            try {
                emailService.sendSimpleMessage(map);
                return true;
            } catch (MessagingException e) {
                return false;
            }
        }
        return false;
    }

//    public boolean test() {
//
//        boolean isMember = memberService.isExistMember(id, nick, email);
//
//        if(isMember) {
//            return send(); // sendMail or noSendMail
//        }
//
//        return "noMember";
//    }
}
