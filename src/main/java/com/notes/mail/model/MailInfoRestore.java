package com.notes.mail.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailInfoRestore extends MailInfo {
  private String restoreToken;

  @Builder
  public MailInfoRestore(final String restoreToken,
                         final String recipientName,
                         final String recipientEmail) {
    this.mailType = MailType.RESTORE;
    this.restoreToken = restoreToken;
    this.recipientEmail = recipientEmail;
    this.recipientName = recipientName;
  }
}
