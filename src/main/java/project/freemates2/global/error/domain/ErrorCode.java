package project.freemates2.global.error.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

  /**
   * 1XXX -> Common 에러
   */

  INTERNAL_SERVER_ERROR(1000, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR),

  BAD_REQUEST(1001, "Bad Request", HttpStatus.BAD_REQUEST),

  /**
   * 2XXX -> Place 에러
   */






  ;

  private final int code;
  private final String message;
  private final HttpStatus httpStatus;
}
