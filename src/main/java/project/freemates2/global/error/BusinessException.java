package project.freemates2.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException {

  private ErrorCode errorCode;

  @Override
  public synchronized Throwable fillInStackTrace() {
    return this;
  }
}
