package project.freemates2.global.error.presentation;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import project.freemates2.global.error.domain.ErrorCode;
import project.freemates2.global.error.dto.ErrorResponseDto;
import project.freemates2.global.error.exception.BusinessException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


  /**
   * 유효성 검사 실패 시 발생하는 MethodArgumentNotValidException을 처리합니다.
   * 이 메서드는 ResponseEntityExceptionHandler의 handleMethodArgumentNotValid를 오버라이드합니다.
   *
   * @param ex      MethodArgumentNotValidException 예외 객체
   * @param headers HTTP 헤더
   * @param status  HTTP 상태 코드
   * @param request 웹 요청 정보
   * @return 커스텀 에러 응답이 포함된 ResponseEntity 객체
   */
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      final MethodArgumentNotValidException ex,
      final HttpHeaders headers,
      final HttpStatusCode status,
      final WebRequest request
  ) {
    final String validationErrorMessage = ex.getBindingResult()
        .getAllErrors()
        .stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.joining(", "));

    final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, validationErrorMessage);

    return handleExceptionInternal(ex, problemDetail, headers, status, request);
  }

  /**
   * ResponseEntityExceptionHandler가 처리한 예외를 가로채서 커스텀 응답 형식으로 변환합니다.
   *
   * @param body      응답 본문 (ProblemDetail 또는 기타 객체)
   * @param headers   HTTP 헤더
   * @param statusCode HTTP 상태 코드
   * @param request   웹 요청 정보
   * @return 커스텀 에러 응답이 포함된 ResponseEntity 객체
   */
  @Override
  public ResponseEntity<Object> createResponseEntity(
      final @Nullable Object body,
      final HttpHeaders headers,
      final HttpStatusCode statusCode,
      final WebRequest request
  ) {
    // body가 ProblemDetail인 경우에만 처리합니다.
    if (body instanceof ProblemDetail problemDetail) {
      // 유효성 검사 실패는 예상 가능한 에러이므로 INFO 레벨로 로깅합니다.
      log.info("[CommonException] URI: {}, Status: {}, Message: {}",
          request.getDescription(false), statusCode, problemDetail.getDetail());

      return ResponseEntity.status(statusCode)
          .body(new ErrorResponseDto(ErrorCode.BAD_REQUEST.getCode(), problemDetail.getDetail()));
    }

    // 그 외 ResponseEntityExceptionHandler가 처리한 예상치 못한 예외는 ERROR 레벨로 로깅합니다.
    log.error("[UnexpectedException] URI: {}, Message: {}", request.getDescription(false), body);

    // 예측하지 못한 모든 에러는 500 Internal Server Error로 통일합니다.
    return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
        .body(new ErrorResponseDto(ErrorCode.INTERNAL_SERVER_ERROR));
  }

  /**
   * 그 외 모든 예외를 처리합니다. (예: NullPointerException 등)
   *
   * @param exception 발생한 예외 객체
   * @param request   HTTP 요청 정보
   * @return 커스텀 에러 응답이 포함된 ResponseEntity 객체
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponseDto> handleInternalException(
      final Exception exception,
      final HttpServletRequest request
  ) {
    log.error("[UnexpectedException] URI: {}, Message: {}",
        request.getRequestURI(), exception.getMessage(), exception);

    return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
        .body(new ErrorResponseDto(ErrorCode.INTERNAL_SERVER_ERROR));
  }

  /**
   * 비즈니스 로직에서 발생하는 커스텀 예외를 처리합니다.
   *
   * @param businessException 발생한 BusinessException 객체
   * @param request           HTTP 요청 정보
   * @return 커스텀 에러 응답이 포함된 ResponseEntity 객체
   */
  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ErrorResponseDto> handleBusinessException(
      final BusinessException businessException,
      final HttpServletRequest request
  ) {
    log.info("[BusinessException] URI: {}, Code: {}",
        request.getRequestURI(), businessException.getErrorCode());

    return ResponseEntity.status(businessException.getErrorCode().getHttpStatus())
        .body(new ErrorResponseDto(businessException.getErrorCode()));
  }

}
