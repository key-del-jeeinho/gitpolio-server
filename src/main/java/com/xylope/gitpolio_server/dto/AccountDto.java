package com.xylope.gitpolio_server.dto;

import lombok.*;

@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString @Builder
@Getter
public class AccountDto {
    private String name, email, password;
}
