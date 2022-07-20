package io.muenchendigital.digiwf.taskanaconnector.exceptions;

public class SystemException extends RuntimeException {

  public SystemException(String msg) {
    super(msg);
  }

  public SystemException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
