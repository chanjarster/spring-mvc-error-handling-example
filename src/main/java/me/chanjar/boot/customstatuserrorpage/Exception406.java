package me.chanjar.boot.customstatuserrorpage;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

@ResponseStatus(NOT_ACCEPTABLE)
public class Exception406 extends RuntimeException {
}
