package com.xylope.gitpolio_server.controller;

import com.thedeanda.lorem.LoremIpsum;
import com.xylope.gitpolio_server.dto.AccountDto;
import com.xylope.gitpolio_server.entity.Account;
import com.xylope.gitpolio_server.exception.AccountAlreadyExistException;
import com.xylope.gitpolio_server.exception.LoginFailureException;
import com.xylope.gitpolio_server.repository.AccountRepository;
import com.xylope.gitpolio_server.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountServiceTest {
    private LoremIpsum lorem;
    private AccountService accountService;
    private AccountRepository accountRepository;

    @BeforeEach
    public void init() {
        lorem = LoremIpsum.getInstance();
        accountRepository = mock(AccountRepository.class);
        accountService = new AccountService(accountRepository);
    }

    @Test
    public void testSignUp() {
        AccountDto account = getRandomAccount();

        when(accountRepository.existsById(account.getEmail())).thenReturn(false);
        when(accountRepository.save(any())).thenAnswer(invocation -> {
            Account target = invocation.getArgument(0);

            assertEquals(account.getName(), target.getName());
            assertEquals(account.getEmail(), target.getEmail());
            assertEquals(account.getPassword(), target.getPassword());
            return null;
        });

        accountService.signUp(account);
    }

    @Test
    public void testSignUpFailure() {
        AccountDto account = getRandomAccount();

        when(accountRepository.existsById(account.getEmail())).thenReturn(true);

        assertThrows(AccountAlreadyExistException.class, () -> accountService.signUp(account));
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

    @Test
    public void testLogin() {
        AccountDto account = getRandomAccount();

        when(accountRepository.existsById(account.getEmail())).thenReturn(true);
        when(accountRepository.getById(account.getEmail()))
                .thenReturn(new Account(account.getName(), account.getEmail(), account.getPassword()));

        AccountDto result = accountService.login(account.getEmail(), account.getPassword());
        assertEquals(account, result);
    }

    @Test
    public void testLoginFailure() {
        AccountDto account = getRandomAccount();

        when(accountRepository.existsById(account.getEmail())).thenReturn(false);

        assertThrows(LoginFailureException.class, () -> accountService.login(account.getEmail(), account.getPassword()));
    }

    @Test
    public void testLoginFailure2() {
        AccountDto account = getRandomAccount();

        when(accountRepository.existsById(account.getEmail())).thenReturn(true);
        when(accountRepository.getById(account.getEmail()))
                .thenReturn(new Account(account.getName(), account.getEmail(), account.getPassword() + "_"));

        assertThrows(LoginFailureException.class, () -> accountService.login(account.getEmail(), account.getPassword()));
    }
}
