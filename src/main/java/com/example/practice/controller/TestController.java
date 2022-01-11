package com.example.practice.controller;

import com.example.practice.entity.AccountContext;
import com.example.practice.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/test")
    public ModelAndView testURl(ModelAndView mav){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try{
            AccountContext accountContext = ((AccountContext)authentication.getPrincipal());
            System.out.println(accountContext.getUsername());
        }catch(ClassCastException e){
            System.out.println("로그인 안되어있음");
        }
        mav.setViewName("hello");
        return mav;
    }

    @GetMapping("/board")
    public ModelAndView testBoard(ModelAndView mav){
        List<Member> testList = new ArrayList<>();

        for(int i = 0; i < 9; i++){
            Member member = Member.builder()
                    .memId(i+"id")
                    .memNick(i+"nick")
                    .memPassword(i+"pass")
                    .memEmail(i+"mail")
                    .memCell(i+"cell")
                    .memRole(i+"role")
                    .signDate(LocalDate.now())
                    .lastLoginDate(LocalDate.now()).build();
            testList.add(member);
        }
        mav.addObject("testList",testList);
        mav.setViewName("board");
        return mav;
    }

    @PostMapping("/board/event")
    public ModelAndView event(ModelAndView mav){
        List<Member> testList = new ArrayList<>();

        for(int i = 0; i < 5; i++){
            Member member = Member.builder()
                    .memId(i+"id")
                    .memNick(i+"nick")
                    .memPassword(i+"pass")
                    .memEmail(i+"mail")
                    .memCell(i+"cell")
                    .memRole(i+"role")
                    .signDate(LocalDate.now())
                    .lastLoginDate(LocalDate.now()).build();
            testList.add(member);
        }
        mav.addObject("testList",testList);
        mav.setViewName("board :: #board");
        return mav;
    }
}
