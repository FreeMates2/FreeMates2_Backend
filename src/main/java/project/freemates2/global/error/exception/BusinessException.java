package project.freemates2.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import phytolens.global.error.domain.ErrorCode;

@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException {

  private ErrorCode errorCode;

  @Override
  public synchronized Throwable fillInStackTrace() {
    return this;
  }
}
