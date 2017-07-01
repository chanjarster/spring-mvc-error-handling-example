package me.chanjar.exception;

/**
 * Created by qianjia on 2017/7/1.
 */
public class SomeException extends  Exception {
  public SomeException() {
    super();
  }

  public SomeException(String message) {
    super(message);
  }

  public SomeException(String message, Throwable cause) {
    super(message, cause);
  }

  public SomeException(Throwable cause) {
    super(cause);
  }

  protected SomeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
