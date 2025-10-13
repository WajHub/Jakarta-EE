package org.example.pokemon.exception;

public class NotFoundException extends HttpRequestException {

    public NotFoundException(String message) {
        super(message, HttpRequestException.BAD_REQUEST);
    }
}
