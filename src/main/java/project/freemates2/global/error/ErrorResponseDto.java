package project.freemates2.global.error;


public record ErrorResponseDto(int code, String message) {

  public ErrorResponseDto(final ErrorCode errorCode) {
    this(errorCode.getCode(), errorCode.getMessage());
  }
}
