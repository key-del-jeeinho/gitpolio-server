package com.xylope.gitpolio_server.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor @Getter
public class LoginFailureException extends RuntimeException {
    private final Reason reason;

    @Getter @RequiredArgsConstructor
    public enum Reason {
        ID_NOT_FOUND(400), WRONG_PASSWORD(401);

        private final int id;
    }
}