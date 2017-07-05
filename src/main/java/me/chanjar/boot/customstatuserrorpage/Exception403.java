package me.chanjar.boot.customstatuserrorpage;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by qianjia on 2017/7/5.
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class Exception403 extends RuntimeException {
}
