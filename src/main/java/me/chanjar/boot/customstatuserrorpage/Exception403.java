package me.chanjar.boot.customstatuserrorpage;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class Exception403 extends RuntimeException {
}
