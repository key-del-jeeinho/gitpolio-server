package com.xylope.gitpolio_server.controller;

import com.xylope.gitpolio_server.dto.AccountDto;
import com.xylope.gitpolio_server.entity.Account;
import com.xylope.gitpolio_server.exception.AccountAlreadyExistException;
import com.xylope.gitpolio_server.exception.LoginFailureException;
import com.xylope.gitpolio_server.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountRepository accountRepository;

    @PostMapping("/sign-up")
    public void signUp(@RequestParam AccountDto account) {
        boolean isExist = accountRepository.existsById(account.getEmail());
        if(isExist) throw new AccountAlreadyExistException(account.getEmail());

        accountRepository.save(Account.builder()
                .name(account.getName())
                .email(account.getEmail())
                .password(account.getPassword())
                .build());
    }

    @PostMapping("/login")
    public ResponseEntity<AccountDto> login(@RequestBody String email, @RequestBody String password) {
        if(!accountRepository.existsById(email)) throw new LoginFailureException(LoginFailureException.Reason.ID_NOT_FOUND);
        Account account = accountRepository.getById(email);
        if(!account.getPassword().equals(password)) throw new LoginFailureException(LoginFailureException.Reason.WRONG_PASSWORD);

        return ResponseEntity.ok(AccountDto.builder()
                .name(account.getName())
                .email(account.getEmail())
                .password(account.getPassword())
                .build());
    }
}
