package com.xylope.gitpolio_server.service;

import com.xylope.gitpolio_server.dto.AccountDto;
import com.xylope.gitpolio_server.entity.Account;
import com.xylope.gitpolio_server.exception.AccountAlreadyExistException;
import com.xylope.gitpolio_server.exception.LoginFailureException;
import com.xylope.gitpolio_server.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public void signUp(AccountDto account) {
        boolean isExist = accountRepository.existsById(account.getEmail());
        if(isExist) throw new AccountAlreadyExistException(account.getEmail());

        accountRepository.save(Account.builder()
                .name(account.getName())
                .email(account.getEmail())
                .password(account.getPassword())
                .build());
    }

    public AccountDto login(String email, String password) {
        if (!accountRepository.existsById(email))
            throw new LoginFailureException(LoginFailureException.Reason.ID_NOT_FOUND);

        Account account = accountRepository.getById(email);
        AccountDto accountDto = AccountDto.builder()
                .name(account.getName())
                .email(account.getEmail())
                .password(account.getPassword())
                .build();

        if (!accountDto.getPassword().equals(password))
            throw new LoginFailureException(LoginFailureException.Reason.WRONG_PASSWORD);

        return accountDto;
    }
}
