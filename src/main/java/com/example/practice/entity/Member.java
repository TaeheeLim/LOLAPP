package com.example.practice.entity;

import com.sun.istack.internal.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member{
    @NotNull
    private String memId;
    @NotNull
    private String memNick;
    @NotNull
    private String memPassword;
    @NotNull
    private String memEmail;
    @NotNull
    private String memCell;
    @NotNull
    private String memRole;
    @NotNull
    private LocalDate signDate;
    @NotNull
    private LocalDate lastLoginDate;
    @NotNull
    private int loginFailCount;
}
