package com.xylope.gitpolio_server.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountAlreadyExistException extends RuntimeException {
    @Getter
    private final String accountEmail;
}
