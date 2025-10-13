package org.example.pokemon.exception;

import lombok.Getter;

public class HttpRequestException extends RuntimeException {

    @Getter
    private final int responseCode;

    public static final int BAD_REQUEST = 404;

    public HttpRequestException(String message, int responseCode) {
        super(message);
        this.responseCode = responseCode;
    }
}
