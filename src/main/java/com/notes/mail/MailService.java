package com.notes.mail;

import java.util.Properties;

public interface MailService {
  void sendEmail(MailType type, Properties params);

}
