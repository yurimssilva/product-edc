package org.eclipse.tractusx.ssi.extensions.core.exception;

public class SsiWalletAlreadyExistsException extends SsiException {

  private final String walletIdentifier;

  public SsiWalletAlreadyExistsException(String walletIdentifier) {
    this.walletIdentifier = walletIdentifier;

    // TODO Add nice error message
  }
}
