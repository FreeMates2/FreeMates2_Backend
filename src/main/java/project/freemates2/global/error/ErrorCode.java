package project.freemates2.global.error;

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
   * 2XXX -> User 에러
   */

  UNSUPPORTED_PROVIDER(2001, "Unsupported Auth Provider", HttpStatus.BAD_REQUEST),

  USER_NOT_FOUND(2002, "User Not Found", HttpStatus.NOT_FOUND),

  /**
   * 3XXX -> Token 에러
   */

  INVALID_TOKEN(3001, "Invalid Token", HttpStatus.UNAUTHORIZED),

  TOKEN_MISSING(3002, "Token Missing", HttpStatus.UNAUTHORIZED)









  ;

  private final int code;
  private final String message;
  private final HttpStatus httpStatus;
}
