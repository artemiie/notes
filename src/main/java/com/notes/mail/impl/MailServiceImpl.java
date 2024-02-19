package com.notes.mail.impl;

import com.notes.mail.MailService;
import com.notes.mail.MailType;
import freemarker.template.Configuration;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static com.notes.mail.MailTemplateConstants.ACTIVATION_SUBJECT;
import static com.notes.mail.MailTemplateConstants.ACTIVATION_TEMPLATE;
import static com.notes.mail.MailTemplateConstants.LOGIN_SUBJECT;
import static com.notes.mail.MailTemplateConstants.LOGIN_TEMPLATE;
import static com.notes.mail.MailTemplateConstants.RESTORE_SUBJECT;
import static com.notes.mail.MailTemplateConstants.RESTORE_TEMPLATE;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

  private final Configuration configuration;
  private final JavaMailSender mailSender;

  @Override
  @SneakyThrows
  public void sendEmail(final MailType type, final Properties properties) {
    MimeMessage mimeMessage = mailSender.createMimeMessage();

    switch (type) {
      case LOGIN -> buildEmail(
          mimeMessage,
          properties,
          LOGIN_TEMPLATE,
          LOGIN_SUBJECT,
          "recipientName");
      case ACTIVATION -> buildEmail(
          mimeMessage,
          properties,
          ACTIVATION_TEMPLATE,
          ACTIVATION_SUBJECT,
          "recipientName",
          "activationToken");
      case RESTORE -> buildEmail(
          mimeMessage,
          properties,
          RESTORE_TEMPLATE,
          RESTORE_SUBJECT,
          "recipientName",
          "restoreToken");
    }

    mailSender.send(mimeMessage);
  }

  @SneakyThrows
  private void buildEmail(
      final MimeMessage mimeMessage,
      final Properties properties,
      final String templateName,
      final String subjectName,
      final String... params) {

    Map<String, String> model = buildDataModel(properties, params);

    StringWriter writer = new StringWriter();
    configuration.getTemplate(templateName).process(model, writer);

    String emailContext = writer.getBuffer().toString();

    MimeMessageHelper helper =
        new MimeMessageHelper(mimeMessage, false, "UTF-8");
    helper.setSubject(subjectName);
    helper.setText(emailContext, true);
    helper.setTo(properties.getProperty("recipientEmail"));
  }

  private Map<String, String> buildDataModel(
      final Properties properties, final String... params) {
    Map<String, String> dataModel = new HashMap<>();
    for (String param : params) {
      dataModel.put(param, properties.getProperty(param));
    }
    return dataModel;
  }
}
