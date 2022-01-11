package com.example.practice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PersistentLogins {
    private String series;

    private String username;

    private String token;

    private LocalDateTime lastUsed;
    
}
