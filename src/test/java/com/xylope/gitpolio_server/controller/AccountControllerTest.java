package com.xylope.gitpolio_server.controller;

import com.thedeanda.lorem.LoremIpsum;
import com.xylope.gitpolio_server.dto.AccountDto;
import com.xylope.gitpolio_server.entity.Account;
import com.xylope.gitpolio_server.exception.AccountAlreadyExistException;
import com.xylope.gitpolio_server.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountControllerTest {
    private LoremIpsum lorem;
    private AccountController accountController;
    private AccountRepository accountRepository;

    @BeforeEach
    public void init() {
        lorem = LoremIpsum.getInstance();
        accountRepository = mock(AccountRepository.class);
        accountController = new AccountController(accountRepository);
    }

    @Test
    public void testSignUp() {
        AccountDto account = getRandomAccount();

        when(accountRepository.existsById(account.getEmail())).thenReturn(false);

        accountController.signUp(account);

        verify(accountRepository).save(new Account(account.getName(), account.getEmail(), account.getPassword()));
    }

    @Test
    public void testSignUpFailure() {
        AccountDto account = getRandomAccount();

        when(accountRepository.existsById(account.getEmail())).thenReturn(true);

        assertThrows(AccountAlreadyExistException.class, () -> accountController.signUp(account));
    }

    private AccountDto getRandomAccount() {
        String name = lorem.getName();
        String email = lorem.getEmail();
        String password = lorem.getWords(1);

        return AccountDto.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
    }
}
