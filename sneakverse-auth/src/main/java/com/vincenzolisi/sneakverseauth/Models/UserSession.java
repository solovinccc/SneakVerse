package com.vincenzolisi.sneakverseauth.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSession {

    private String token;
    private Integer userId;
    private String email;
    private LocalDateTime createdAt;
}

