package com.xylope.gitpolio_server.controller;

import com.thedeanda.lorem.LoremIpsum;
import com.xylope.gitpolio_server.dto.AccountDto;
import com.xylope.gitpolio_server.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AccountControllerTest {
    private LoremIpsum lorem;
    private AccountController accountController;
    private AccountService accountService;

    @BeforeEach
    public void init() {
        lorem = LoremIpsum.getInstance();
        accountService = mock(AccountService.class);
        accountController = new AccountController(accountService);
    }

    @Test
    public void testSignUp() {
        AccountDto account = getRandomAccount();

        accountController.signUp(account);

        verify(accountService).signUp(account);
    }

    @Test
    public void testLogin() {
        AccountDto account = getRandomAccount();

        when(accountService.login(account.getEmail(), account.getPassword())).thenReturn(account);

        ResponseEntity<AccountDto> responseEntity = accountController.login(account.getEmail(), account.getPassword());

        verify(accountService).login(account.getEmail(), account.getPassword());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseEntity.getBody(), account);
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
