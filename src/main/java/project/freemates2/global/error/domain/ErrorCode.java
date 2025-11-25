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
   * 2XXX -> Diagnosis 에러
   */

  DIAGNOSIS_JOB_NOT_FOUND(2001, "Diagnosis Job Not Found", HttpStatus.NOT_FOUND),

  DIAGNOSIS_IMAGE_PAYLOAD_TOO_LARGE(2002, "Diagnosis Image Payload Too Large", HttpStatus.PAYLOAD_TOO_LARGE),

  DIAGNOSIS_IMAGE_UNSUPPORTED_MEDIA_TYPE(2003, "Diagnosis Image Unsupported Media Type", HttpStatus.UNSUPPORTED_MEDIA_TYPE),

  DIAGNOSIS_IMAGE_NOT_FOUND(2004, "Diagnosis Image Not Found", HttpStatus.NOT_FOUND)





  ;

  private final int code;
  private final String message;
  private final HttpStatus httpStatus;
}
