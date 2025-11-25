package project.freemates2.global.error.dto;

import phytolens.global.error.domain.ErrorCode;

public record ErrorResponseDto(int code, String message) {

  public ErrorResponseDto(final ErrorCode errorCode) {
    this(errorCode.getCode(), errorCode.getMessage());
  }
}
