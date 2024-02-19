package com.notes.mail.model;

import lombok.Builder;


public class MailInfoLogin extends MailInfo {
  @Builder
  public MailInfoLogin(final String recipientName,
                       final String recipientEmail) {
    this.mailType = MailType.LOGIN;
    this.recipientEmail = recipientEmail;
    this.recipientName = recipientName;
  }
}
