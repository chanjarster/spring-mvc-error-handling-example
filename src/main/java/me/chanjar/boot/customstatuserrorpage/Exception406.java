package me.chanjar.boot.customstatuserrorpage;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

/**
 * Created by qianjia on 2017/7/5.
 */
@ResponseStatus(NOT_ACCEPTABLE)
public class Exception406 extends RuntimeException {
}
