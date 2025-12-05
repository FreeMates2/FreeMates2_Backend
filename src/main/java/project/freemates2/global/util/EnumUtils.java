package project.freemates2.global.util;

public final class EnumUtils {

  private EnumUtils() {
    // 유틸 클래스라 인스턴스 생성 방지
  }

  /**
   * 대소문자 구분 없이 문자열을 Enum으로 변환.
   * 실패하면 exIfFail 예외를 그대로 던진다.
   */
  public static <E extends Enum<E>> E parseIgnoreCaseOrThrow(
      String raw,
      Class<E> type,
      RuntimeException exIfFail
  ) {
    if (raw == null) {
      throw exIfFail;
    }
    try {
      return Enum.valueOf(type, raw.trim().toUpperCase());
    } catch (IllegalArgumentException e) {
      throw exIfFail;
    }
  }
}

