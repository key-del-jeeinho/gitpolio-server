package com.xylope.gitpolio_server.controller;

import com.xylope.gitpolio_server.dto.AccountDto;
import com.xylope.gitpolio_server.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/sign-up")
    public void signUp(@RequestParam AccountDto account) {
        accountService.signUp(account);
    }

    @PostMapping("/login")
    public ResponseEntity<AccountDto> login(@RequestBody String email, @RequestBody String password) {
        AccountDto accountDto = accountService.login(email, password);
        return ResponseEntity.ok(accountDto);
    }
}
