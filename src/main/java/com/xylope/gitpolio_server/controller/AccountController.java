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
        Account account = accountRepository.getById(email);
        //TODO 지인호 | 나중에 FailureReason Enum 작성 후 OR 연산자 분할 | 2021.08.05
        if(!accountRepository.existsById(email) ||
                !account.getPassword().equals(password)) throw new LoginFailureException();

        return ResponseEntity.ok(AccountDto.builder()
                .name(account.getName())
                .email(account.getEmail())
                .password(account.getPassword())
                .build());
    }
}
