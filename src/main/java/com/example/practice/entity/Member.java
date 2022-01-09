package com.example.practice.entity;

import com.sun.istack.internal.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
public class Member {
    @NotNull
    private String memIdx;
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
}
