package project.freemates2.api.user.exception;

import project.freemates2.global.error.domain.ErrorCode;

public class UnsupportedProviderException extends RuntimeException {

  public static final UnsupportedProviderException INSTANCE = new UnsupportedProviderException();

  private UnsupportedProviderException() {
    super(ErrorCode.UNSUPPORTED_PROVIDER.getMessage());
  }

}
