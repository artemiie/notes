package com.notes.mail.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailInfoActivation extends MailInfo {
  private String activationToken;

  @Builder
  public MailInfoActivation(final String activationToken,
                            final String recipientName,
                            final String recipientEmail) {
    this.mailType = MailType.ACTIVATION;
    this.activationToken = activationToken;
    this.recipientEmail = recipientEmail;
    this.recipientName = recipientName;
  }

}
